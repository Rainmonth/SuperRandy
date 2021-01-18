package com.rainmonth.player.activity.other.video;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.rainmonth.common.base.BaseCleanActivity;
import com.rainmonth.player.R;
import com.rainmonth.utils.AppUtils;
import com.rainmonth.utils.FileUtils;
import com.rainmonth.utils.PathUtils;
import com.rainmonth.utils.ResUtil;
import com.rainmonth.utils.StringUtils;
import com.rainmonth.utils.ToastUtils;
import com.rainmonth.utils.log.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 1、使用AudioRecorder或MediaRecorder获取到AAC文件；
 * 2、使用MediaCodec解码生成的AAC文件，解析成PCM数据；
 * 3、使用AudioTrack直接播放解码后生成的PCM数据；
 *
 * @author RandyZhang
 * @date 2020/12/21 4:38 PM
 */
public class VideoMediaCodecApiActivity extends BaseCleanActivity {

    private SurfaceView svPreview;
    private TextView tvRecordAudio;
    private TextView tvRecord, tvStartExtractor, tvStartMuxer;

    private String mOutputDir = getOutputDir();
    private String mOriginFilePath;
    private String mOriginFilePathCopy;

    private String mOriginFileName = "vid_origin.mp4";
    private String mExtractorVideoFileName = "vid_extractor.mp4";
    private String mExtractorAudioFileName = "aud_extractor.aac";
    private String mMuxerFileName = "vid_mix.mp4";

    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private MediaExtractor mMediaExtractor;
    private MediaMuxer mMediaMuxer;

    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private boolean mSurfaceCreated = false;
    private boolean mIsRecording = false;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_activity_media_codec_api;
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        svPreview = findViewById(R.id.sv_preview);
        tvRecordAudio = findViewById(R.id.tv_record_audio);
        tvRecord = findViewById(R.id.tv_record);
        tvStartExtractor = findViewById(R.id.tv_extractor);
        tvStartMuxer = findViewById(R.id.tv_muxer);

        tvRecordAudio.setOnClickListener(v -> onStartRecordAudioClick());
        tvRecord.setOnClickListener(v -> onRecordClick());
        tvStartExtractor.setOnClickListener(v -> onStartExtractorClick());
        tvStartMuxer.setOnClickListener(v -> onStartMuxerClick());

        mSurfaceHolder = svPreview.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                LogUtils.d(TAG, "surfaceCreated");
                mSurfaceCreated = true;
                openCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                LogUtils.d(TAG, "surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                LogUtils.d(TAG, "surfaceDestroyed");
                mSurfaceCreated = false;
                closeCamera();
            }
        });
    }

    private void openCamera() {
        LogUtils.d(TAG, "openCamera");
        if (!mSurfaceCreated || mSurfaceHolder == null) {
            return;
        }
        if (mCamera == null) {
            mCamera = Camera.open(mCameraId);
        }
        if (mCamera == null) {
            LogUtils.w(TAG, "调用Camera.open()后mCamera仍未空");
            return;
        }
        mCamera.setDisplayOrientation(90);

        try {
            Camera.Parameters parameters = mCamera.getParameters();
            // 设置预览照片大小
            parameters.setPreviewSize(svPreview.getWidth(), svPreview.getHeight());
            // 设置预览照片帧数
            parameters.setPreviewFpsRange(4, 10);
            // 设置图片格式
            parameters.setPictureFormat(ImageFormat.JPEG);
            // 设置图片质量
            parameters.setJpegQuality(90);
//        parameters.set("jpeg-quality", 90)
            // 设置照片大小
            parameters.setPictureSize(svPreview.getWidth(), svPreview.getHeight());
            // 设置目标
            mCamera.setPreviewDisplay(mSurfaceHolder);
            // 开始预览
            mCamera.startPreview();
        } catch (IOException e) {
            LogUtils.printStackTrace(TAG, e);
        }

    }

    private void closeCamera() {
        LogUtils.d(TAG, "closeCamera");
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private String mOriginAudioFileName;                // 录制的原始音频文件名称

    private void onStartRecordAudioClick() {

    }

    private void onRecordClick() {
        Log.d(TAG, "onRecordClick, mIsRecording: " + mIsRecording);
        if (mIsRecording) { // 正在录制
            mIsRecording = false;
            tvRecord.setBackground(ResUtil.getDrawable(R.drawable.common_shape_16round_green));
            tvRecord.setText("开始录制");
            if (mMediaRecorder != null) {
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
            LogUtils.d(TAG, "原始视频保存路径：" + mOriginFilePath);
            ToastUtils.showLong("原始视频保存路径：" + mOriginFilePath);
            mOriginFilePathCopy = mOriginFilePath;
            mOriginFilePath = null;
            mCamera.lock();
            mCamera.startPreview();
        } else {
            if (StringUtils.isEmpty(mOriginFilePath)) {
                mOriginFilePath = getOutputDir() + File.separator + mOriginFileName;
            }
            mIsRecording = true;
            tvRecord.setBackground(ResUtil.getDrawable(R.drawable.common_shape_16round_red));
            tvRecord.setText("结束录制");
            mCamera.stopPreview();
            mCamera.unlock();
            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setCamera(mCamera);
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
            mMediaRecorder.setProfile(profile);
            mMediaRecorder.setOutputFile(mOriginFilePath);
            mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
            try {
                mMediaRecorder.prepare();
                mMediaRecorder.start();
            } catch (Exception e) {
                LogUtils.printStackTrace(TAG, e);
            }
        }
    }

    private String getOutputDir() {
        File dir = FileUtils.makeDirs(PathUtils.getExternalDcimPath() + File.separator + AppUtils.getAppPackageName());
        mOutputDir = dir.getPath();
        return mOutputDir;
    }

    private void onStartExtractorClick() {
        LogUtils.d(TAG, "onStartExtractorClick");
        if (StringUtils.isEmpty(mOriginFilePathCopy)) {
            ToastUtils.showLong("原始文件不存在");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = processExtractor();
                LogUtils.d(TAG, "process result: " + result);
            }
        }).start();
    }


    private boolean processExtractor() {
        mMediaExtractor = new MediaExtractor();
        try {
            mMediaExtractor.setDataSource(mOriginFilePathCopy);
            int trackCount = mMediaExtractor.getTrackCount();
            int videoTrackIndex = -1, audioTrackIndex = -1;
            MediaFormat videoMediaFormat = null, audioMediaFormat = null;
            // 获取视频轨道和音频轨道及其对应的格式
            for (int i = 0; i < trackCount; i++) {
                MediaFormat mediaFormat = mMediaExtractor.getTrackFormat(i);
                String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
                LogUtils.d(TAG, "mime: " + mime);
                if (StringUtils.isEmpty(mime)) {
                    continue;
                }
                if (mime.startsWith("video")) {
                    videoTrackIndex = i;
                    videoMediaFormat = mediaFormat;
                    continue;
                }

                if (mime.startsWith("audio")) {
                    audioTrackIndex = i;
                    audioMediaFormat = mediaFormat;
                    continue;
                }
            }

            if (videoMediaFormat == null || audioMediaFormat == null) {
                return false;
            }
            boolean videoExtractResult = extractVideoWithMuxer(mMediaExtractor, videoMediaFormat, videoTrackIndex);
            boolean audioExtractResult = extractAudioWithIOStream(mMediaExtractor, audioMediaFormat, audioTrackIndex);
            mMediaExtractor.release();
            LogUtils.d(TAG, "process videoExtractResult: " + videoExtractResult + ", audioExtractResult: " + audioExtractResult);
            return videoExtractResult || audioExtractResult;
        } catch (Exception e) {
            LogUtils.printStackTrace(TAG, e);
            mMediaExtractor.release();
            return false;
        }
    }

    /**
     * 利用输出流分离视频频（代码测试时使用该方法得到的 mp4文件 再次设置到 MediaExtractor的DataSource时报 can't instance Extractor错误，说明格式 不对
     *
     * @param mediaExtractor   提取器
     * @param videoMediaFormat 提取视频格式
     * @param videoTrackIndex  提取视频轨道号
     * @return true if extract video success
     */
    private boolean extractVideoWithIOStream(MediaExtractor mediaExtractor, MediaFormat videoMediaFormat, int videoTrackIndex) {
        try {
            LogUtils.i(TAG, videoMediaFormat.toString());
            File videoFile = new File(mOutputDir, mExtractorVideoFileName);
            if (videoFile.exists()) {
                boolean result = videoFile.delete();
                LogUtils.d(TAG, "删除已存在的分离视频，result: " + result);
            }
            FileOutputStream videoOutputStream = new FileOutputStream(videoFile);

            int maxVideoBufferCount = videoMediaFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);//获取视频的输出缓存的最大大小
            ByteBuffer videoByteBuffer = ByteBuffer.allocate(maxVideoBufferCount);
            mediaExtractor.selectTrack(videoTrackIndex); // 选择视频轨道
            int len = 0;
            while ((len = mediaExtractor.readSampleData(videoByteBuffer, 0)) != -1) {
                byte[] bytes = new byte[len];
                videoByteBuffer.get(bytes);// 获取字节
                videoOutputStream.write(bytes);// 写入字节
                videoByteBuffer.clear();        // 清空缓存
                mediaExtractor.advance();      // 加载后面的数据
            }
            videoOutputStream.flush();
            videoOutputStream.close();

            mediaExtractor.unselectTrack(videoTrackIndex);// 取消选择音频轨道
            return true;
        } catch (FileNotFoundException e) {
            ToastUtils.showLong("文件不存在，请检查");
            return false;
        } catch (IOException e) {
            LogUtils.printStackTrace(e);
            return false;
        }
    }

    /**
     * 利用 Muxer 分离视频
     *
     * @param mediaExtractor   提取器
     * @param videoMediaFormat 提取视频格式
     * @param videoTrackIndex  提取视频轨道号
     * @return
     */
    private boolean extractVideoWithMuxer(MediaExtractor mediaExtractor, MediaFormat videoMediaFormat, int videoTrackIndex) {
        MediaMuxer mediaMuxer = null;
        try {
            LogUtils.i(TAG, videoMediaFormat.toString());
            File videoFile = new File(mOutputDir, mExtractorVideoFileName);
            if (videoFile.exists()) {
                boolean result = videoFile.delete();
                LogUtils.d(TAG, "删除已存在的分离视频，result: " + result);
            }
            mediaExtractor.selectTrack(videoTrackIndex); // 选择视频轨道
            mediaMuxer = new MediaMuxer(videoFile.getAbsolutePath(), MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            //将视频轨添加到 MediaMuxer，并返回新的轨道
            int trackIndex = mediaMuxer.addTrack(videoMediaFormat);
            int maxVideoBufferCount = videoMediaFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);//获取视频的输出缓存的最大大小
            ByteBuffer byteBuffer = ByteBuffer.allocate(maxVideoBufferCount);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            // 开始合成
            mediaMuxer.start();
            while (true) {
                // 检索当前编码的样本并将其存储在字节缓冲区中
                int readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0);
                //  如果没有可获取的样本则退出循环
                if (readSampleSize < 0) {
                    mediaExtractor.unselectTrack(videoTrackIndex);
                    break;
                }
                // 设置样本编码信息
                bufferInfo.size = readSampleSize;
                bufferInfo.offset = 0;
                bufferInfo.flags = mediaExtractor.getSampleFlags();
                bufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
                //写入样本数据
                mediaMuxer.writeSampleData(trackIndex, byteBuffer, bufferInfo);
                //推进到下一个样本，类似快进
                mediaExtractor.advance();
            }
            LogUtils.i(TAG, "extract video finish, path: " + videoFile.getAbsolutePath());
            ToastUtils.showShort("分离视频完成");
            return true;
        } catch (FileNotFoundException e) {
            ToastUtils.showLong("文件不存在，请检查");
            return false;
        } catch (IOException e) {
            LogUtils.printStackTrace(e);
            return false;
        } finally {
            if (mediaMuxer != null) {
                mediaMuxer.release();
            }
        }
    }

    /**
     * 利用输出流 分离音频
     *
     * @param mediaExtractor   提取器
     * @param audioMediaFormat 提取音频格式
     * @param audioTrackIndex  提取音频轨道号
     * @return true if extract audio success
     */
    private boolean extractAudioWithIOStream(MediaExtractor mediaExtractor, MediaFormat audioMediaFormat, int audioTrackIndex) {
        try {
            LogUtils.i(TAG, audioMediaFormat.toString());
            File audioFile = new File(mOutputDir, mExtractorAudioFileName);
            if (audioFile.exists()) {
                boolean result = audioFile.delete();
                LogUtils.d(TAG, "删除已存在的分离视频，result: " + result);
            }
            FileOutputStream audioOutputStream = new FileOutputStream(audioFile);

            // 分离音频
            int maxAudioBufferCount = audioMediaFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);// 获取音频的输出缓存的最大大小
            ByteBuffer audioByteBuffer = ByteBuffer.allocate(maxAudioBufferCount);
            mediaExtractor.selectTrack(audioTrackIndex);
            int len = 0;
            while ((len = mediaExtractor.readSampleData(audioByteBuffer, 0)) != -1) {
                byte[] bytes = new byte[len];
                audioByteBuffer.get(bytes);

                //  添加adts头
                //  注意! 在分离音频后并没有adts头. 所以这需要我们手动导入. 如果不太了解什么是adts
                //  可以参考 https://www.cnblogs.com/guanxinjing/p/11438181.html
                byte[] adtsData = new byte[len + 7];
                addADTStoPacket(adtsData, len + 7);
                System.arraycopy(bytes, 0, adtsData, 7, len);
                audioOutputStream.write(adtsData);
                audioByteBuffer.clear();
                mediaExtractor.advance();
            }
            audioOutputStream.flush();
            audioOutputStream.close();

            mediaExtractor.unselectTrack(audioTrackIndex);
            return true;
        } catch (FileNotFoundException e) {
            ToastUtils.showLong("文件不存在，请检查");
            return false;
        } catch (IOException e) {
            LogUtils.printStackTrace(TAG, e);
        }
        return false;
    }

    /**
     * 利用 Muxer 提取音频
     *
     * @param mediaExtractor   提取器
     * @param audioMediaFormat 提取音频格式
     * @param audioTrackIndex  提取音频轨道号
     * @return true if extract audio success
     */
    private boolean extractAudioWithMuxer(MediaExtractor mediaExtractor, MediaFormat audioMediaFormat, int audioTrackIndex) {
        MediaMuxer mediaMuxer = null;
        try {
            LogUtils.i(TAG, audioMediaFormat.toString());
            File audioFile = new File(mOutputDir, mExtractorAudioFileName);
            if (audioFile.exists()) {
                boolean result = audioFile.delete();
                LogUtils.d(TAG, "删除已存在的分离音频，result: " + result);
            }
            mediaExtractor.selectTrack(audioTrackIndex);
            // FIXME 这里是否需要修改 格式
            mediaMuxer = new MediaMuxer(audioFile.getAbsolutePath(), MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            //将音频轨添加到 MediaMuxer，并返回新的轨道
            int trackIndex = mediaMuxer.addTrack(audioMediaFormat);
            // 分离音频
            int maxAudioBufferCount = audioMediaFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);// 获取音频的输出缓存的最大大小
            ByteBuffer byteBuffer = ByteBuffer.allocate(maxAudioBufferCount);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            // 开始合成
            mediaMuxer.start();
            while (true) {
                // 检索当前编码的样本并将其存储在字节缓冲区中
                int readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0);
                //  如果没有可获取的样本则退出循环
                if (readSampleSize < 0) {
                    mediaExtractor.unselectTrack(audioTrackIndex);
                    break;
                }
                // 设置样本编码信息
                bufferInfo.size = readSampleSize;
                bufferInfo.offset = 0;
                bufferInfo.flags = mediaExtractor.getSampleFlags();
                bufferInfo.presentationTimeUs = mediaExtractor.getSampleTime();
                //写入样本数据
                mediaMuxer.writeSampleData(trackIndex, byteBuffer, bufferInfo);
                //推进到下一个样本，类似快进
                mediaExtractor.advance();
            }
            LogUtils.i(TAG, "extract audio finish, path: " + audioFile.getAbsolutePath());
            ToastUtils.showShort("分离视频完成");
            return true;
        } catch (FileNotFoundException e) {
            ToastUtils.showLong("文件不存在，请检查");
            return false;
        } catch (IOException e) {
            LogUtils.printStackTrace(TAG, e);
            return false;
        } finally {
            if (mediaMuxer != null) {
                mediaMuxer.release();
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

    /**
     * 音视频混合
     * 利用 MediaExtractor 来提取单独的音频（或单独的视频信息），然后用 MediaMuxer来进行合成
     * <p>
     * 要进行混合，肯定要知道用来混合的音频或视频的信息，而提取音视频信息，就要用到 MediaExtractor
     */
    private void onStartMuxerClick() {
        LogUtils.d(TAG, "onStartMuxerClick");
        String srcVideoPath = mOutputDir + File.separator + mExtractorVideoFileName;
        String srcAudioPath = mOutputDir + File.separator + mExtractorAudioFileName;
        String mixOutputPath = mOutputDir + File.separator + mMuxerFileName;
        LogUtils.d(TAG, "onStartMuxerClick, srcVideoPath: " + srcVideoPath + ", srcAudioPath: " + srcAudioPath + ", mixOutputPath: " + mixOutputPath);
        new Thread(new Runnable() {
            @Override
            public void run() {
                processMux(srcVideoPath, srcAudioPath, mixOutputPath);
            }
        }).start();
    }

    private void processMux(String srcVideoPath, String srcAudioPath, String mixOutputPath) {
        MediaExtractor videoExtractor = new MediaExtractor();
        MediaExtractor audioExtractor = new MediaExtractor();
        MediaMuxer mediaMuxer = null;
        try {

            audioExtractor.setDataSource(srcAudioPath);
            MediaFormat audioFormat = null;
            int audioTrackIndex = -1;
            int audioTrackCount = audioExtractor.getTrackCount();
            for (int i = 0; i < audioTrackCount; i++) {
                MediaFormat mediaFormat = audioExtractor.getTrackFormat(i);
                String mimeType = mediaFormat.getString(MediaFormat.KEY_MIME);
                if (mimeType == null || !mimeType.startsWith("audio")) {
                    continue;
                }
                if (mimeType.startsWith("audio")) {
                    audioTrackIndex = i;
                    audioFormat = mediaFormat;
                    break;
                }
            }

            if (audioFormat == null) {
                return;
            }

            videoExtractor.setDataSource(srcVideoPath);
            MediaFormat videoFormat = null;
            int videoTrackIndex = -1;
            int videoTrackCount = videoExtractor.getTrackCount();
            for (int i = 0; i < videoTrackCount; i++) {
                MediaFormat mediaFormat = videoExtractor.getTrackFormat(i);
                String mimeType = mediaFormat.getString(MediaFormat.KEY_MIME);
                if (mimeType == null || !mimeType.startsWith("video")) {
                    continue;
                }
                if (mimeType.startsWith("video")) {
                    videoTrackIndex = i;
                    videoFormat = mediaFormat;
                    break;
                }
            }
            if (videoFormat == null) {
                return;
            }

            // 音轨及音频格式、视轨及视频格式都准备好了，现在准备进行音视频混合
            mediaMuxer = new MediaMuxer(mixOutputPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            int writeVideoTrackIndex = mediaMuxer.addTrack(videoFormat);
            int writeAudioTrackIndex = mediaMuxer.addTrack(audioFormat);
            mediaMuxer.start();

            // 视频数据写入
            videoExtractor.selectTrack(videoTrackIndex);
            MediaCodec.BufferInfo videoBufferInfo = new MediaCodec.BufferInfo();
            int videoMaxInputSize = videoFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
            ByteBuffer videoByteBuffer = ByteBuffer.allocate(videoMaxInputSize);
            while (true) {
                int readVideoSampleSize = videoExtractor.readSampleData(videoByteBuffer, 0);
                if (readVideoSampleSize < 0) {
                    videoExtractor.unselectTrack(videoTrackIndex);
                    break;
                }
                videoBufferInfo.size = readVideoSampleSize;
                videoBufferInfo.presentationTimeUs = videoExtractor.getSampleTime();
                videoBufferInfo.offset = 0;
                videoBufferInfo.flags = videoExtractor.getSampleFlags();
                mediaMuxer.writeSampleData(writeVideoTrackIndex, videoByteBuffer, videoBufferInfo);
                videoExtractor.advance();
            }

            // 音频数据写入
            audioExtractor.selectTrack(audioTrackIndex);
            MediaCodec.BufferInfo audioBufferInfo = new MediaCodec.BufferInfo();
            int audioMaxInputSize = audioFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);
            ByteBuffer audioByteBuffer = ByteBuffer.allocate(audioMaxInputSize);
            while (true) {
                int readAudioSampleSize = audioExtractor.readSampleData(audioByteBuffer, 0);
                if (readAudioSampleSize < 0) {
                    audioExtractor.unselectTrack(audioTrackIndex);
                    break;
                }
                audioBufferInfo.size = readAudioSampleSize;
                audioBufferInfo.presentationTimeUs = audioExtractor.getSampleTime();
                audioBufferInfo.offset = 0;
                audioBufferInfo.flags = audioExtractor.getSampleFlags();
                mediaMuxer.writeSampleData(writeAudioTrackIndex, audioByteBuffer, audioBufferInfo);
                audioExtractor.advance();
            }
            LogUtils.d(TAG, "muxer media finish, path: " + mixOutputPath);
            ToastUtils.showLong("音视频混合完成");

        } catch (Exception e) {
            LogUtils.printStackTrace(TAG, e);
        } finally {
            if (mediaMuxer != null) {
                mediaMuxer.release();
            }
            videoExtractor.release();
            audioExtractor.release();
        }
    }
}
