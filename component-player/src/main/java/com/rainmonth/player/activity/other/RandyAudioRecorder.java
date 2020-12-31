package com.rainmonth.player.activity.other;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.text.TextUtils;

import com.rainmonth.utils.FileUtils;
import com.rainmonth.utils.PathUtils;
import com.rainmonth.utils.ThreadUtils;
import com.rainmonth.utils.log.LogUtils;
import com.rainmonth.utils.MediaUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * AudioRecord 录音封装
 * <p>
 * 使用建造者模式
 *
 * @author RandyZhang
 * @date 2020/12/22 11:29 AM
 */
public class RandyAudioRecorder {

    private static final String TAG = "RandyAudioRecorder";

    private static final int STATE_INIT = 0;                            // 初始化状态
    private static final int STATE_RECORDING = 1;                       // 录制状态
    private static final int STATE_STOP = 2;                            // 停止状态
    private static final int STATE_RELEASE = 3;                         // 释放状态

    private int mRecordState;                                           // 当前的状态

    /**
     * most time is {@link android.media.MediaRecorder.AudioSource#MIC}
     */
    private int audioSource = MediaRecorder.AudioSource.MIC;            // 录音硬件来源
    private int sampleRateInHz = 44100;                                 // 采样率
    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;            // 声道设置
    /**
     * {@link android.media.AudioFormat#ENCODING_PCM_8BIT}
     * {@link android.media.AudioFormat#ENCODING_PCM_16BIT}
     * {@link android.media.AudioFormat#ENCODING_PCM_FLOAT}
     */
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;           // PCM 源文件格式
    private String dirPath = PathUtils.getExternalAppAudioRecordPath(); // PCM 和 WAV 文件目录
    private String srcFileName = "sr_test.pcm";                         // PCM 文件名称
    private String disFileName = "sr_test.wav";                         // WAV 文件名称

    private AudioRecord mAudioRecord;                                   // 用来音频录制的AudioRecord
    private int mBufferedSizeInBytes;                                   // 音频录制的最小缓冲值

    public RandyAudioRecorder() {
        int bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        mAudioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes);
    }

    public RandyAudioRecorder(Builder builder) {
        this.audioSource = builder.audioSource;
        this.sampleRateInHz = builder.sampleRateInHz;
        this.channelConfig = builder.channelConfig;
        this.audioFormat = builder.audioFormat;
        this.dirPath = builder.dirPath;
        this.srcFileName = builder.srcFileName;
        this.disFileName = builder.disFileName;

        mBufferedSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        if (mBufferedSizeInBytes <= 0) {
            throw new IllegalArgumentException("AudioRecord can not be created due to mBufferedSizeInBytes(need great than 0), mBufferedSizeInBytes: " + mBufferedSizeInBytes);
        }
        mAudioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, mBufferedSizeInBytes);
        mRecordState = STATE_INIT;
    }

    /**
     * @param recordFileName pcm 文件名称
     * @param audioSource    音频文件来源（通常是麦克风）
     * @param sampleRateInHz 采样率
     * @param channelConfig  声道设置
     * @param audioFormat    PCM数据格式
     */
    public void createAudioRecord(String recordFileName, int audioSource, int sampleRateInHz, int channelConfig, int audioFormat) {
        mBufferedSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        if (mBufferedSizeInBytes <= 0) {
            throw new IllegalArgumentException("AudioRecord is not available due to mBufferedSizeInBytes: " + mBufferedSizeInBytes);
        }
        mAudioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, mBufferedSizeInBytes);
        int state = mAudioRecord.getState();
        LogUtils.i(TAG, "createAudio state: " + state + ", initialize: " + (state == AudioRecord.STATE_INITIALIZED));
        this.srcFileName = recordFileName;
        this.mRecordState = STATE_INIT;
    }

    /**
     * 不能依靠{@link AudioRecord#getState()} 来进行判断
     */
    public void startRecord() {
        LogUtils.d(TAG, "startRecord()");
        if (mAudioRecord == null || (mRecordState != STATE_INIT && mRecordState != STATE_STOP)) {
            LogUtils.w(TAG, "can not startRecord due to mAudioRecord is null or mRecordState is wrong");
            return;
        }
        mAudioRecord.startRecording();
        mRecordState = STATE_RECORDING;
        ThreadUtils.getIoPool().execute(this::writeAudioDataToFile);
    }

    public void startRecord(String recordFileName) {
        this.srcFileName = recordFileName;
        LogUtils.d(TAG, "startRecord(), mRecordFileName: " + srcFileName);
        startRecord();
    }

    /**
     * 将音频数据写入文件
     *
     * @throws IOException
     */
    private void writeAudioDataToFile() {
        LogUtils.d(TAG, "writeAudioDataToFile()");
        File recordDir = FileUtils.makeDirs(dirPath);
        File file = new File(recordDir, srcFileName);
        if (file.exists()) {
            file.delete();
        }

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            byte[] audioData = new byte[mBufferedSizeInBytes];
            while (mRecordState == STATE_RECORDING) {
                // 从音频采集硬件中读取数据
                int readSize = mAudioRecord.read(audioData, 0, mBufferedSizeInBytes);
                if (readSize > 0) {
                    bos.write(audioData, 0, readSize);
                } else {
                    LogUtils.w(TAG, "readSize: " + readSize);
                }
            }
            bos.flush();
        } catch (Throwable e) {
            LogUtils.printStackTrace(TAG, e);
        }
    }


    public void stopRecord() {
        LogUtils.d(TAG, "stopRecord()");
        if (mAudioRecord == null) {
            LogUtils.w(TAG, "mAudioRecord is null");
            return;
        }
        if (mRecordState == STATE_INIT || mRecordState == STATE_RELEASE) {
            LogUtils.w(TAG, "stopRecord(), error state");
            return;
        }

        this.mRecordState = STATE_STOP;

        pcm2wav("test_" + System.currentTimeMillis() + ".wav");
    }

    public void release() {
        LogUtils.d(TAG, "release()");
        if (mAudioRecord != null) {
            mAudioRecord.release();
            mAudioRecord = null;
        }
        this.mRecordState = STATE_RELEASE;
    }

    public void pcm2wav(String wavFileName) {
        File pcmFile = new File(dirPath, srcFileName);
        File wavFile;
        if (TextUtils.isEmpty(wavFileName)) {
            wavFile = new File(dirPath, disFileName);
        } else {
            wavFile = new File(dirPath, wavFileName);
        }
        ThreadUtils.getIoPool().execute(() -> {
            try {
                MediaUtils.pcm2Wav(pcmFile, wavFile, false, 1, 44100, 16);
            } catch (IOException e) {
                LogUtils.printStackTrace(e);
            }
        });
    }

    static class Builder {
        private int audioSource;            // 录音硬件来源
        private int sampleRateInHz;         // 采样率
        private int channelConfig;          // 声道设置
        private int audioFormat;            // PCM 源文件格式
        private String dirPath;             // PCM 和 WAV 文件目录
        private String srcFileName;         // PCM 文件名称
        private String disFileName;         // WAV 文件名称

        public Builder audioSource(int audioSource) {
            this.audioSource = audioSource;
            return this;
        }

        public Builder sampleRate(int sampleRateInHz) {
            this.sampleRateInHz = sampleRateInHz;
            return this;
        }

        public Builder channel(int channelConfig) {
            this.channelConfig = channelConfig;
            return this;
        }

        public Builder audioFormat(int audioFormat) {
            this.audioFormat = audioFormat;
            return this;
        }

        public Builder dirPath(String dirPath) {
            this.dirPath = dirPath;
            return this;
        }

        public Builder srcFileName(String srcFileName) {
            this.srcFileName = srcFileName;
            return this;
        }

        public Builder disFileName(String disFileName) {
            this.disFileName = disFileName;
            return this;
        }

        public RandyAudioRecorder build() {
            return new RandyAudioRecorder(this);
        }
    }
}
