package com.rainmonth.player.activity.other;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import androidx.annotation.IntRange;

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
    int mode = AudioTrack.MODE_STREAM;


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

        mAudioTrack = new AudioTrack(streamType, sampleRateInHz, channelConfig, audioFormat,
                AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat), mode);
    }

    public void start() {
        mAudioTrack.play();
    }

    public void pause() {
        mAudioTrack.pause();
    }

    public void stop() {
        mAudioTrack.stop();
    }

    public void release() {
        mAudioTrack.release();
    }

    static class Builder {
        private int streamType = AudioManager.STREAM_MUSIC;
        private int sampleRateInHz = 44100;
        private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
        private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

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

        public RandyAudioTrack build() {
            return new RandyAudioTrack(this);
        }
    }

}
