package com.rainmonth.player.activity.other.video;

import android.media.AudioFormat;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;

import com.rainmonth.utils.CloseUtils;
import com.rainmonth.utils.StringUtils;
import com.rainmonth.utils.log.LogUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Locale;
import java.util.Queue;

/**
 * Simplifies the MediaCodec interface by wrapping around the buffer processing operations.
 */
public class MediaCodecWrapper {

    private static final String TAG = "MediaCodecWrapper";

    // Handler to use for {@code OutputSampleListener} and {code OutputFormatChangedListener}
    // callbacks
    private Handler mHandler;


    // Callback when media output format changes.
    public interface OutputFormatChangedListener {
        void outputFormatChanged(MediaCodecWrapper sender, MediaFormat newFormat);
    }

    private OutputFormatChangedListener mOutputFormatChangedListener = null;

    /**
     * Callback for decodes frames. Observers can register a listener for optional stream
     * of decoded data
     */
    public interface OutputSampleListener {
        void outputSample(MediaCodecWrapper sender, MediaCodec.BufferInfo info, ByteBuffer buffer);
    }

    /**
     * The {@link MediaCodec} that is managed by this class.
     */
    private MediaCodec mDecoder;

    // References to the internal buffers managed by the codec. The codec
    // refers to these buffers by index, never by reference so it's up to us
    // to keep track of which buffer is which.
    private ByteBuffer[] mInputBuffers;
    private ByteBuffer[] mOutputBuffers;

    // Indices of the input buffers that are currently available for writing. We'll
    // consume these in the order they were dequeued from the codec.
    private Queue<Integer> mAvailableInputBuffers;

    // Indices of the output buffers that currently hold valid data, in the order
    // they were produced by the codec.
    private Queue<Integer> mAvailableOutputBuffers;

    // Information about each output buffer, by index. Each entry in this array
    // is valid if and only if its index is currently contained in mAvailableOutputBuffers.
    private MediaCodec.BufferInfo[] mOutputBufferInfo;

    private MediaCodecWrapper(MediaCodec codec) {
        mDecoder = codec;
        codec.start();
        mInputBuffers = codec.getInputBuffers();
        mOutputBuffers = codec.getOutputBuffers();
        mOutputBufferInfo = new MediaCodec.BufferInfo[mOutputBuffers.length];
        mAvailableInputBuffers = new ArrayDeque<>(mOutputBuffers.length);
        mAvailableOutputBuffers = new ArrayDeque<>(mInputBuffers.length);
    }

    /**
     * Releases resources and ends the encoding/decoding session.
     */
    public void stopAndRelease() {
        mDecoder.stop();
        mDecoder.release();
        mDecoder = null;
        mHandler = null;
    }

    /**
     * Getter for the registered {@link OutputFormatChangedListener}
     */
    public OutputFormatChangedListener getOutputFormatChangedListener() {
        return mOutputFormatChangedListener;
    }

    /**
     * @param outputFormatChangedListener the listener for callback.
     * @param handler                     message handler for posting the callback.
     */
    public void setOutputFormatChangedListener(final OutputFormatChangedListener
                                                       outputFormatChangedListener, Handler handler) {
        mOutputFormatChangedListener = outputFormatChangedListener;

        // Making sure we don't block ourselves due to a bad implementation of the callback by
        // using a handler provided by client.
        mHandler = handler;
        if (outputFormatChangedListener != null && mHandler == null) {
            if (Looper.myLooper() != null) {
                mHandler = new Handler();
            } else {
                throw new IllegalArgumentException(
                        "Looper doesn't exist in the calling thread");
            }
        }
    }

    /**
     * Constructs the {@link MediaCodecWrapper} wrapper object around the video codec.
     * The codec is created using the encapsulated information in the
     * {@link MediaFormat} object.
     *
     * @param trackFormat The format of the media object to be decoded.
     * @param surface     Surface to render the decoded frames.
     * @return
     */
    public static MediaCodecWrapper fromVideoFormat(final MediaFormat trackFormat,
                                                    Surface surface) throws IOException {
        MediaCodecWrapper result = null;
        MediaCodec videoCodec = null;

        // BEGIN_INCLUDE(create_codec)
        final String mimeType = trackFormat.getString(MediaFormat.KEY_MIME);

        // Check to see if this is actually a video mime type. If it is, then create
        // a codec that can decode this mime type.
        if (mimeType.contains("video/")) {
            videoCodec = MediaCodec.createDecoderByType(mimeType);
            videoCodec.configure(trackFormat, surface, null, 0);

        }

        // If codec creation was successful, then create a wrapper object around the
        // newly created codec.
        if (videoCodec != null) {
            result = new MediaCodecWrapper(videoCodec);
        }
        // END_INCLUDE(create_codec)

        return result;
    }


    /**
     * Write a media sample to the decoder.
     * <p>
     * A "sample" here refers to a single atomic access unit in the media stream. The definition
     * of "access unit" is dependent on the type of encoding used, but it typically refers to
     * a single frame of video or a few seconds of audio. {@link android.media.MediaExtractor}
     * extracts data from a stream one sample at a time.
     *
     * @param input              A ByteBuffer containing the input data for one sample. The buffer must be set
     *                           up for reading, with its position set to the beginning of the sample data and its limit
     *                           set to the end of the sample data.
     * @param presentationTimeUs The time, relative to the beginning of the media stream,
     *                           at which this buffer should be rendered.
     * @param flags              Flags to pass to the decoder. See {@link MediaCodec#queueInputBuffer(int,
     *                           int, int, long, int)}
     * @throws MediaCodec.CryptoException
     */
    public boolean writeSample(final ByteBuffer input,
                               final MediaCodec.CryptoInfo crypto,
                               final long presentationTimeUs,
                               final int flags) throws MediaCodec.CryptoException, WriteException {
        boolean result = false;
        int size = input.remaining();

        // check if we have dequed input buffers available from the codec
        if (size > 0 && !mAvailableInputBuffers.isEmpty()) {
            int index = mAvailableInputBuffers.remove();
            ByteBuffer buffer = mInputBuffers[index];

            // we can't write our sample to a lesser capacity input buffer.
            if (size > buffer.capacity()) {
                throw new MediaCodecWrapper.WriteException(String.format(Locale.US,
                        "Insufficient capacity in MediaCodec buffer: "
                                + "tried to write %d, buffer capacity is %d.",
                        input.remaining(),
                        buffer.capacity()));
            }

            buffer.clear();
            buffer.put(input);

            // Submit the buffer to the codec for decoding. The presentationTimeUs
            // indicates the position (play time) for the current sample.
            if (crypto == null) {
                mDecoder.queueInputBuffer(index, 0, size, presentationTimeUs, flags);
            } else {
                mDecoder.queueSecureInputBuffer(index, 0, crypto, presentationTimeUs, flags);
            }
            result = true;
        }
        return result;
    }

    private static MediaCodec.CryptoInfo sCryptoInfo = new MediaCodec.CryptoInfo();

    /**
     * Write a media sample to the decoder.
     * <p>
     * A "sample" here refers to a single atomic access unit in the media stream. The definition
     * of "access unit" is dependent on the type of encoding used, but it typically refers to
     * a single frame of video or a few seconds of audio. {@link android.media.MediaExtractor}
     * extracts data from a stream one sample at a time.
     *
     * @param extractor          Instance of {@link android.media.MediaExtractor} wrapping the media.
     * @param presentationTimeUs The time, relative to the beginning of the media stream,
     *                           at which this buffer should be rendered.
     * @param flags              Flags to pass to the decoder. See {@link MediaCodec#queueInputBuffer(int,
     *                           int, int, long, int)}
     * @throws MediaCodec.CryptoException
     */
    public boolean writeSample(final MediaExtractor extractor,
                               final boolean isSecure,
                               final long presentationTimeUs,
                               int flags) {
        boolean result = false;

        if (!mAvailableInputBuffers.isEmpty()) {
            int index = mAvailableInputBuffers.remove();
            ByteBuffer buffer = mInputBuffers[index];

            // reads the sample from the file using extractor into the buffer
            int size = extractor.readSampleData(buffer, 0);
            if (size <= 0) {
                flags |= MediaCodec.BUFFER_FLAG_END_OF_STREAM;
            }

            // Submit the buffer to the codec for decoding. The presentationTimeUs
            // indicates the position (play time) for the current sample.
            if (!isSecure) {
                mDecoder.queueInputBuffer(index, 0, size, presentationTimeUs, flags);
            } else {
                extractor.getSampleCryptoInfo(sCryptoInfo);
                mDecoder.queueSecureInputBuffer(index, 0, sCryptoInfo, presentationTimeUs, flags);
            }

            result = true;
        }
        return result;
    }

    /**
     * Performs a peek() operation in the queue to extract media info for the buffer ready to be
     * released i.e. the head element of the queue.
     *
     * @param out_bufferInfo An output var to hold the buffer info.
     * @return True, if the peek was successful.
     */
    public boolean peekSample(MediaCodec.BufferInfo out_bufferInfo) {
        // dequeue available buffers and synchronize our data structures with the codec.
        update();
        boolean result = false;
        if (!mAvailableOutputBuffers.isEmpty()) {
            int index = mAvailableOutputBuffers.peek();
            MediaCodec.BufferInfo info = mOutputBufferInfo[index];
            // metadata of the sample
            out_bufferInfo.set(
                    info.offset,
                    info.size,
                    info.presentationTimeUs,
                    info.flags);
            result = true;
        }
        return result;
    }

    /**
     * Processes, releases and optionally renders the output buffer available at the head of the
     * queue. All observers are notified with a callback. See {@link
     * OutputSampleListener#outputSample(MediaCodecWrapper, android.media.MediaCodec.BufferInfo,
     * java.nio.ByteBuffer)}
     *
     * @param render True, if the buffer is to be rendered on the {@link Surface} configured
     */
    public void popSample(boolean render) {
        // dequeue available buffers and synchronize our data structures with the codec.
        update();
        if (!mAvailableOutputBuffers.isEmpty()) {
            int index = mAvailableOutputBuffers.remove();

            // releases the buffer back to the codec
            mDecoder.releaseOutputBuffer(index, render);
        }
    }

    /**
     * Synchronize this object's state with the internal state of the wrapped
     * MediaCodec.
     */
    private void update() {
        // BEGIN_INCLUDE(update_codec_state)
        int index;

        // Get valid input buffers from the codec to fill later in the same order they were
        // made available by the codec.
        while ((index = mDecoder.dequeueInputBuffer(0)) != MediaCodec.INFO_TRY_AGAIN_LATER) {
            mAvailableInputBuffers.add(index);
        }


        // Likewise with output buffers. If the output buffers have changed, start using the
        // new set of output buffers. If the output format has changed, notify listeners.
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
        while ((index = mDecoder.dequeueOutputBuffer(info, 0)) != MediaCodec.INFO_TRY_AGAIN_LATER) {
            switch (index) {
                case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
                    mOutputBuffers = mDecoder.getOutputBuffers();
                    mOutputBufferInfo = new MediaCodec.BufferInfo[mOutputBuffers.length];
                    mAvailableOutputBuffers.clear();
                    break;
                case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
                    if (mOutputFormatChangedListener != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mOutputFormatChangedListener
                                        .outputFormatChanged(MediaCodecWrapper.this,
                                                mDecoder.getOutputFormat());

                            }
                        });
                    }
                    break;
                default:
                    // Making sure the index is valid before adding to output buffers. We've already
                    // handled INFO_TRY_AGAIN_LATER, INFO_OUTPUT_FORMAT_CHANGED &
                    // INFO_OUTPUT_BUFFERS_CHANGED i.e all the other possible return codes but
                    // asserting index value anyways for future-proofing the code.
                    if (index >= 0) {
                        mOutputBufferInfo[index] = info;
                        mAvailableOutputBuffers.add(index);
                    } else {
                        throw new IllegalStateException("Unknown status from dequeueOutputBuffer");
                    }
                    break;
            }

        }
        // END_INCLUDE(update_codec_state)

    }

    private class WriteException extends Throwable {
        private WriteException(final String detailMessage) {
            super(detailMessage);
        }
    }

    /**
     * 打印设备支持的编码器于解码器组件名称
     */
    public static void printAllSupportEncoderAndDecoder() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            MediaCodecList list = new MediaCodecList(MediaCodecList.REGULAR_CODECS);
            LogUtils.d("Randy", "Decoders:");
            for (MediaCodecInfo codecInfo : list.getCodecInfos()) {
                if (codecInfo.isEncoder()) {
                    continue;
                }
                LogUtils.d("Randy", codecInfo.getName());
            }
            LogUtils.d("Randy", "Encoders:");
            for (MediaCodecInfo codecInfo : list.getCodecInfos()) {
                if (codecInfo.isEncoder()) {
                    LogUtils.d("Randy", codecInfo.getName());
                }
            }
        }
    }

    //<editor-fold> 利用 MediaCodec 将 PCM 文件编码成 AAC 文件

    private int sampleRate = 16000;
    private int channelCount = 1;
    private int bitRate = 64000;
    private int maxInputSize = 2048; // todo check this value

    private BufferedOutputStream bufferedOutputStream;
    private MediaCodec encodeMediaCodec;
    private MediaFormat encodeFormat;
    private ByteBuffer[] encodeInputBuffers;
    private ByteBuffer[] encodeOutputBuffers;
    private MediaCodec.BufferInfo encodeBufferInfo;

    /**
     * 使用MediaCodec将Pcm音频数据编码成AAC格式音频数据
     *
     * @param inputPcmPath  输入pcm数据的路径
     * @param outputAacPath 输出AAC文件路径
     */
    public void encodePcm2AAC(String inputPcmPath, String outputAacPath) {
        if (StringUtils.isEmpty(inputPcmPath) || StringUtils.isEmpty(outputAacPath)) {
            LogUtils.e(TAG, "inputPcmPath or outputAacPath is null or empty!!");
            return;
        }
        File inputPcmFile = new File(inputPcmPath);
        if (!inputPcmFile.exists()) {
            LogUtils.e(TAG, "inputPcm File doesn't exists");
            return;
        }
        File outputAacFile = new File(outputAacPath);
        if (!outputAacFile.exists()) {
            try {
                outputAacFile.getParentFile().mkdir();
                outputAacFile.createNewFile();
            } catch (IOException e) {
                LogUtils.printStackTrace(TAG, e);
            }
        }

        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputAacFile, false));
        } catch (FileNotFoundException e) {
            LogUtils.printStackTrace(TAG, e);
        }

        createAndInitEncodeFormat();
        readPcmFile(inputPcmFile);
    }

    /**
     * 创建并初始化编码用到的MediaFormat
     */
    private void createAndInitEncodeFormat() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                encodeFormat = MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_AAC, sampleRate, channelCount);
            } else {
                encodeFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", sampleRate, channelCount);
            }
            maxInputSize = encodeFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
            encodeFormat.setInteger(MediaFormat.KEY_BIT_RATE, bitRate);
            encodeFormat.setInteger(MediaFormat.KEY_CHANNEL_COUNT, channelCount);
            encodeFormat.setInteger(MediaFormat.KEY_CHANNEL_MASK, AudioFormat.CHANNEL_IN_MONO);
            encodeFormat.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC);
            encodeFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, maxInputSize);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                encodeMediaCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_AUDIO_AAC);
            } else {
                encodeMediaCodec = MediaCodec.createEncoderByType("audio/mp4a-latm");
            }
        } catch (IOException e) {
            LogUtils.printStackTrace(TAG, e);
        }
        if (encodeMediaCodec == null) {
            return;
        }
        encodeMediaCodec.start();
        encodeInputBuffers = encodeMediaCodec.getInputBuffers();
        encodeOutputBuffers = encodeMediaCodec.getOutputBuffers();
        encodeBufferInfo = new MediaCodec.BufferInfo();
    }

    /**
     * 读取 PCM 文件
     *
     * @param pcmFile pcm 文件
     */
    private void readPcmFile(File pcmFile) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(pcmFile);
            outputStream = new ByteArrayOutputStream();
            // 获取音频的输出缓存的最大大小，这个大小必须准确获取，因为后面添加adts头是基于这个获取的buffer大小的
            int maxAudioBufferCount = encodeFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
            byte[] buffer = new byte[maxAudioBufferCount];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                // 此时buffer中的数据就是 PCM文件对应的 部分数据
                encodePcmByteDataToAacData(buffer);
                LogUtils.d(TAG, "current read length: " + len);
            }
        } catch (IOException e) {
            LogUtils.printStackTrace(TAG, e);
        } finally {
            CloseUtils.closeIOQuietly(outputStream);
            CloseUtils.closeIOQuietly(inputStream);
        }
    }

    /**
     * 处理读取到的 pcm 数据
     *
     * @param pcmByteData 读取到的pcm字节数据
     */
    private void encodePcmByteDataToAacData(byte[] pcmByteData) {
        int inputIndex;
        ByteBuffer inputBuffer;
        int outputIndex;
        ByteBuffer outputBuffer;
        int outBitSize;
        int outPacketSize;

        encodeInputBuffers = encodeMediaCodec.getInputBuffers();
        encodeOutputBuffers = encodeMediaCodec.getOutputBuffers();
        encodeBufferInfo = new MediaCodec.BufferInfo();
        inputIndex = encodeMediaCodec.dequeueInputBuffer(0);
        if (inputIndex != -1) {
            // 客户端申请 ByteBuffer
            inputBuffer = encodeInputBuffers[inputIndex];
            inputBuffer.clear();
            inputBuffer.limit(pcmByteData.length);

            // 客户端填充数据
            inputBuffer.put(pcmByteData);
            // 通知编码器编码
            encodeMediaCodec.queueInputBuffer(inputIndex, 0, pcmByteData.length, 0, 0);

            outputIndex = encodeMediaCodec.dequeueOutputBuffer(encodeBufferInfo, 0);
            while (outputIndex > 0) {
                outBitSize = encodeBufferInfo.size;
                outPacketSize = outBitSize + 7;

                outputBuffer = encodeOutputBuffers[outputIndex];
                outputBuffer.position(encodeBufferInfo.offset);
                outputBuffer.limit(encodeBufferInfo.offset + outBitSize);
                byte[] adtsData = new byte[outPacketSize];
                // 此时 adtsData找那个包含了adts头，第七位之后都为空
                addADTStoPacket(adtsData, outPacketSize);
                // 从outputBuffer中拿去数据填充第7位之后数据
                outputBuffer.get(adtsData, 7, outBitSize);

                // 将数据陷入到 bufferedOutputStream
                try {
                    //录制aac音频文件，保存在手机内存中
                    bufferedOutputStream.write(adtsData, 0, adtsData.length);
                    bufferedOutputStream.flush();
                } catch (IOException e) {
                    LogUtils.printStackTrace(TAG, e);
                }

                outputBuffer.position(encodeBufferInfo.offset);
                encodeMediaCodec.releaseOutputBuffer(outputIndex, false);
                outputIndex = encodeMediaCodec.dequeueOutputBuffer(encodeBufferInfo, 0);

            }
        }

    }

    /**
     * 为音频的每一帧添加 adts 头
     *
     * @param packet    帧数据
     * @param packetLen 帧数据长度
     */
    private void addADTStoPacket(byte[] packet, int packetLen) {
        /*
        标识使用AAC级别 当前选择的是LC
        一共有1: AAC Main 2:AAC LC (Low Complexity) 3:AAC SSR (Scalable Sample Rate) 4:AAC LTP (Long Term Prediction)
        */
        int profile = 2;
        int frequencyIndex = 0x04; //设置采样率
        int channelConfiguration = 2; //设置频道,其实就是声道

        // fill in ADTS data
        packet[0] = (byte) 0xFF;
        packet[1] = (byte) 0xF9;
        packet[2] = (byte) (((profile - 1) << 6) + (frequencyIndex << 2) + (channelConfiguration >> 2));
        packet[3] = (byte) (((channelConfiguration & 3) << 6) + (packetLen >> 11));
        packet[4] = (byte) ((packetLen & 0x7FF) >> 3);
        packet[5] = (byte) (((packetLen & 7) << 5) + 0x1F);
        packet[6] = (byte) 0xFC;
    }
    //</editor-fold>


    //<editor-fold>使用MediaCodec将AAC数据解码成PCM数据
    private MediaExtractor mediaExtractor;
    private MediaCodec decodeMediaCodec;
    private ByteBuffer[] decodeInputBuffers;
    private ByteBuffer[] decodeOutputBuffers;
    private MediaCodec.BufferInfo decodeBufferInfo;
    private String mime = "audio/mp4a-latm";
    private MediaFormat decodeMediaFormat;

    private FileOutputStream fileOutputStream;

    /**
     * 使用MediaCodec将AAC数据解码成PCM数据
     *
     * @param inputAacPath  输入aac文件路径
     * @param outputPcmPath 输出pcm文件路径
     */
    public void decodeAac2Pcm(String inputAacPath, String outputPcmPath) {
        File targetFile = new File(inputAacPath);
        File pcmFile = new File(outputPcmPath);
        if (!pcmFile.exists()) {
            try {
                pcmFile.getParentFile().mkdirs();
                pcmFile.createNewFile();
            } catch (Exception e) {
                LogUtils.printStackTrace(TAG, e);
            }
        }
        try {
            fileOutputStream = new FileOutputStream(pcmFile.getAbsoluteFile());
        } catch (Exception e) {
            LogUtils.printStackTrace(TAG, e);
        }
        mediaExtractor = new MediaExtractor();
        try {
            //设置资源
            mediaExtractor.setDataSource(targetFile.getAbsolutePath());
            //获取含有音频的MediaFormat
            decodeMediaFormat = createMediaFormat();
            if (decodeMediaFormat == null) {
                return;
            }
            decodeMediaCodec = MediaCodec.createDecoderByType(mime);
            decodeMediaCodec.configure(decodeMediaFormat, null, null, 0);//当解压的时候最后一个参数为0
            decodeMediaCodec.start();//开始，进入runnable状态
            //只有MediaCodec进入到Runnable状态后，才能过去缓存组
            decodeInputBuffers = decodeMediaCodec.getInputBuffers();
            decodeOutputBuffers = decodeMediaCodec.getOutputBuffers();
            decodeBufferInfo = new MediaCodec.BufferInfo();
        } catch (Exception e) {
            LogUtils.printStackTrace(TAG, e);
        }
    }

    private MediaFormat createMediaFormat() {
        int trackCount = mediaExtractor.getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            MediaFormat mediaFormat = mediaExtractor.getTrackFormat(i);
            String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
            if (!StringUtils.isEmpty(mime) && mime.startsWith("audio")) {
                //todo 选中这个音轨（需要优化）
                mediaExtractor.selectTrack(i);
                return mediaFormat;
            }
        }

        return null;
    }

    public void decode() {
        boolean inputSawEos = false;
        boolean outputSawEos = false;
        long kTimes = 5000;//循环时间
        while (!outputSawEos) {
            if (!inputSawEos) {
                //每5000毫秒查询一次
                int inputBufferIndex = decodeMediaCodec.dequeueInputBuffer(kTimes);
                //输入缓存index可用
                if (inputBufferIndex >= 0) {
                    //获取可用的输入缓存
                    ByteBuffer inputBuffer = decodeInputBuffers[inputBufferIndex];
                    //从MediaExtractor读取数据到输入缓存中，返回读取长度
                    int bufferSize = mediaExtractor.readSampleData(inputBuffer, 0);
                    if (bufferSize <= 0) {//已经读取完
                        //标志输入完毕
                        inputSawEos = true;
                        //做标识
                        decodeMediaCodec.queueInputBuffer(inputBufferIndex, 0, 0, kTimes, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                    } else {
                        long time = mediaExtractor.getSampleTime();
                        //将输入缓存放入MediaCodec中
                        decodeMediaCodec.queueInputBuffer(inputBufferIndex, 0, bufferSize, time, 0);
                        //指向下一帧
                        mediaExtractor.advance();
                    }
                }
            }
            //获取输出缓存，需要传入MediaCodec.BufferInfo 用于存储ByteBuffer信息
            int outputBufferIndex = decodeMediaCodec.dequeueOutputBuffer(decodeBufferInfo, kTimes);
            if (outputBufferIndex >= 0) {
                if ((decodeBufferInfo.flags & MediaCodec.BUFFER_FLAG_CODEC_CONFIG) != 0) {
                    decodeMediaCodec.releaseOutputBuffer(outputBufferIndex, false);
                    continue;
                }
                //有输出数据
                if (decodeBufferInfo.size > 0) {
                    //获取输出缓存
                    ByteBuffer outputBuffer = decodeOutputBuffers[outputBufferIndex];
                    //设置ByteBuffer的position位置
                    outputBuffer.position(decodeBufferInfo.offset);
                    //设置ByteBuffer访问的结点
                    outputBuffer.limit(decodeBufferInfo.offset + decodeBufferInfo.size);
                    byte[] targetData = new byte[decodeBufferInfo.size];
                    //将数据填充到数组中
                    outputBuffer.get(targetData);
                    try {
                        fileOutputStream.write(targetData);
                    } catch (Exception e) {
                        LogUtils.printStackTrace(TAG, e);
                    }
                }
                //释放输出缓存
                decodeMediaCodec.releaseOutputBuffer(outputBufferIndex, false);
                //判断缓存是否完结
                if ((decodeBufferInfo.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
                    outputSawEos = true;
                }
            } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                decodeOutputBuffers = decodeMediaCodec.getOutputBuffers();
            } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                MediaFormat mediaFormat = decodeMediaCodec.getOutputFormat();
                // 打印 mediaFormat改变的相关消息
                LogUtils.e(TAG, "MediaFormat changed, old format: " + decodeMediaFormat.getString(MediaFormat.KEY_MIME)
                        + ", now format: " + mediaFormat.getString(MediaFormat.KEY_MIME));
            }
        }
        //释放资源
        try {
            fileOutputStream.flush();
            fileOutputStream.close();
            decodeMediaCodec.stop();
            decodeMediaCodec.release();
            mediaExtractor.release();
        } catch (Exception e) {
            LogUtils.printStackTrace(TAG, e);
        }
    }


    //</editor-fold>
}