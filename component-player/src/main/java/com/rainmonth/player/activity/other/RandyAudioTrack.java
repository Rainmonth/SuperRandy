package com.rainmonth.player.activity.other;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import androidx.annotation.IntRange;

import com.rainmonth.utils.CloseUtils;
import com.rainmonth.utils.ThreadUtils;
import com.rainmonth.utils.log.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * AudioTrack 封装
 * AudioTrack 可以读取 音频采集生成的 PCM数据 并播放
 *
 * @author randy
 * @date 2021/01/06
 */
public class RandyAudioTrack {
    private static final String TAG = "RandyAudioTrack";

    private AudioTrack mAudioTrack;
    private int streamType = AudioManager.STREAM_MUSIC;
    private int sampleRateInHz = 44100;
    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    /**
     * See {@link AudioTrack#MODE_STATIC} and {@link AudioTrack#MODE_STREAM}.
     */
    private int mode = AudioTrack.MODE_STREAM;

    private File pcmFile;


    public RandyAudioTrack() {
        int minBufferSize = AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes;
            AudioFormat format;
            int sessionId = AudioManager.AUDIO_SESSION_ID_GENERATE;
            audioAttributes = new AudioAttributes.Builder()
                    .setLegacyStreamType(streamType)
                    .build();
            format = new AudioFormat.Builder()
                    .setChannelMask(channelConfig)
                    .setEncoding(audioFormat)
                    .setSampleRate(sampleRateInHz)
                    .build();

            mAudioTrack = new AudioTrack(audioAttributes, format, minBufferSize, mode, sessionId);
        } else {
            mAudioTrack = new AudioTrack(streamType, sampleRateInHz, channelConfig, audioFormat, minBufferSize, mode);
        }
    }

    public RandyAudioTrack(Builder builder) {
        this.streamType = builder.streamType;
        this.sampleRateInHz = builder.sampleRateInHz;
        this.channelConfig = builder.channelConfig;
        this.audioFormat = builder.audioFormat;
        this.mode = builder.mode;

        mAudioTrack = new AudioTrack(streamType, sampleRateInHz, channelConfig, audioFormat,
                AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat), mode);
    }

    /**
     * @param pcmPath pcm 所在目录路径
     * @param pcmName pcm 文件名
     */
    public void start(String pcmPath, String pcmName) {
        start(new File(pcmPath), pcmName);
    }

    /**
     * @param pcmDir  pcm 所在目录
     * @param pcmName pcm 文件名
     */
    public void start(File pcmDir, String pcmName) {
        File pcmFile = new File(pcmDir, pcmName);
        start(pcmFile);
    }

    public void start(File pcmFile) {

        if (pcmFile == null || !pcmFile.exists()) {
            LogUtils.e(TAG, "pcmFile is null or not exists!!!");
            return;
        }
        this.pcmFile = pcmFile;
        LogUtils.d(TAG, "start(), pcmFile: " + pcmFile.getPath());
        if (mode == AudioTrack.MODE_STREAM) {
            playByStream(pcmFile);
        } else {
            playByStatic(pcmFile);
        }
    }

    /**
     * 流模式，数据会从 Java 层传输到底层，并排队阻塞等待播放
     * 1、所以这里需要开启线程独立播放
     * 2、为保证能重复使用，需要对状态进行控制
     *
     * @param pcmFile pcm 文件
     */
    private void playByStream(File pcmFile) {
        this.pcmFile = pcmFile;
        LogUtils.d(TAG, "playByStream()");
        // 先取消
        ThreadUtils.cancel(readPcmFileTask);
        ThreadUtils.executeByIo(readPcmFileTask);
    }


    private ThreadUtils.Task<Object> readPcmFileTask = new ThreadUtils.SimpleTask<Object>() {
        @Override
        public Object doInBackground() throws Throwable {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(pcmFile);
                byte[] buffer = new byte[AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat)];
                int readCount;
                while ((readCount = inputStream.read(buffer)) > 0) {
                    mAudioTrack.play();
                    // 将读取的 pcm 数据写入到AudioTack中，等待播放
                    mAudioTrack.write(buffer, 0, readCount);
                }
                mAudioTrack.stop();
                mAudioTrack.release();
            } catch (Exception e) {
                LogUtils.printStackTrace(e);
            } finally {
                CloseUtils.closeIOQuietly(inputStream);
            }
            return null;
        }

        @Override
        public void onSuccess(Object result) {
            LogUtils.d(TAG, "pcm文件读取完毕");
        }
    };

    /**
     * 一次性读写 PCM 数据到buffer中进行播放
     *
     * @param pcmFile pcm 文件
     */
    private void playByStatic(File pcmFile) {
        LogUtils.d(TAG, "playByStatic()");
        Runnable runnable = () -> {
            FileInputStream inputStream = null;
            ByteArrayOutputStream outputStream = null;

            try {
                inputStream = new FileInputStream(pcmFile);
                outputStream = new ByteArrayOutputStream();
                int len;
                byte[] buffer = new byte[1024];
                // 从 pcm 文件中读取数据 并写到 outputStream 中，
                // 然后 从 outputStream 中获取数据共 AudioTrack 播放
                while ((len = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
                byte[] bytes = outputStream.toByteArray();
                if (mAudioTrack != null) {
                    mAudioTrack.write(bytes, 0, bytes.length);
                    mAudioTrack.play();
                }
            } catch (Exception e) {
                LogUtils.printStackTrace(e);
            } finally {
                CloseUtils.closeIOQuietly(outputStream, inputStream);
            }
        };

        ThreadUtils.getIoPool().execute(runnable);
    }

    public void pause() {
        if (mAudioTrack != null) {
            mAudioTrack.pause();
        }
    }

    public void stop() {
        if (mAudioTrack != null) {
            mAudioTrack.stop();
        }
    }

    public void release() {
        if (mAudioTrack != null) {
            mAudioTrack.release();
        }
    }

    static class Builder {
        private int streamType = AudioManager.STREAM_MUSIC;
        private int sampleRateInHz = 44100;
        private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
        private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        private int mode = AudioTrack.MODE_STREAM;

        public Builder setStreamType(int streamType) {
            this.streamType = streamType;
            return this;
        }

        public Builder setSampleRateInHz(@IntRange(from = 4000, to = 192000) int sampleRateInHz) {
            this.sampleRateInHz = sampleRateInHz;
            return this;
        }

        public Builder setChannelConfig(int channelConfig) {
            this.channelConfig = channelConfig;
            return this;
        }

        public Builder setAudioFormat(int audioFormat) {
            this.audioFormat = audioFormat;
            return this;
        }

        public Builder setMode(int mode) {
            this.mode = mode;
            return this;
        }

        public RandyAudioTrack build() {
            return new RandyAudioTrack(this);
        }
    }

}
