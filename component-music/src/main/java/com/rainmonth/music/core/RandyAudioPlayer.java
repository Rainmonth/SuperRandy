package com.rainmonth.music.core;

import android.os.Looper;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.PlayerMessage;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

/**
 * 音频播放器
 *
 * @author RandyZhang
 * @date 2019-11-19 23:15
 */
public class RandyAudioPlayer implements Player.EventListener, IRandyAudioPlayer {

    private ExoPlayer internalPlayer;

    public RandyAudioPlayer() {
        initPlayer();
    }

    private void initPlayer() {
        internalPlayer = ExoPlayerFactory.newInstance()
    }

}
