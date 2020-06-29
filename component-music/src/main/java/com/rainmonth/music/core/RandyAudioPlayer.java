package com.rainmonth.music.core;

import android.net.Uri;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.Util;
import com.rainmonth.music.config.MusicApplication;
import com.rainmonth.music.core.player.IRandyAudioPlayer;
import com.socks.library.KLog;

/**
 * 音频播放器
 *
 * @author RandyZhang
 * @date 2019-11-19 23:15
 */
public class RandyAudioPlayer implements Player, IRandyAudioPlayer, Player.EventListener {
    private static final String TAG = "RandyAudioPlayer";

    /**
     * ExoPlayer实例（本质是ExoPlayerImpl）
     */
    private ExoPlayer mInternalPlayer;
    private Uri mCurrentUri;

    // 当前状态
    private int mCurrentState = Player.STATE_IDLE;
    // 期望达到的状态
    private int mExpectState = Player.STATE_IDLE;

    // 音频解码器
    private Renderer audioRenderer;

    public RandyAudioPlayer() {
        initPlayer();
    }

    private void initPlayer() {
        // 创建解码器
        audioRenderer = new MediaCodecAudioRenderer(MusicApplication.getInstance(), MediaCodecSelector.DEFAULT);
        // 创建轨道选择器
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        // 最终使用的是ExoPlayerImpl
        mInternalPlayer = ExoPlayerFactory.newInstance(MusicApplication.getInstance(), new Renderer[]{audioRenderer}, trackSelector);
    }

    //<editor-fold> implements of IRandyAudioPlayer


    @Override
    public void prepare(int rawResId) {
        if (rawResId != 0) {
            try {
                Uri uri = RawResourceDataSource.buildRawResourceUri(rawResId);
                prepare(uri);
            } catch (Exception e) {
                KLog.e(TAG, "prepare rawResId " + rawResId + " error, please check");
            }
        }
    }

    @Override
    public void prepare(String resUrl) {
        if (!TextUtils.isEmpty(resUrl)) {
            try {
                Uri uri = Uri.parse(resUrl);
                prepare(uri);
            } catch (Exception e) {
                KLog.e(TAG, "prepare resUrl " + resUrl + " error, please check");
            }
        } else {
            KLog.e(TAG, "prepare a empty resUrl");
        }
    }

    @Override
    public void prepare(Uri resUri) {
        if (null != resUri) {
            if (!(mCurrentState == Player.STATE_IDLE || mCurrentState == STATE_RELEASED)) {

                return;
            }

            if (mCurrentState == STATE_RELEASED) {
                initPlayer();
            }


            this.mCurrentState = STATE_PREPARING;
            this.mExpectState = STATE_PREPARING;

            DataSource.Factory dataSource = new DefaultDataSourceFactory(MusicApplication.getInstance(),
                    Util.getUserAgent(MusicApplication.getInstance(), MusicApplication.getInstance().getPackageName()));
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSource).createMediaSource(resUri);

            mInternalPlayer.prepare(mediaSource);
        }
    }

    /**
     * 根据uri返回合适的MediaSource
     *
     * @param uri 资源uri
     */
    private MediaSource buildMediaSource(Uri uri) {
        int type = Util.inferContentType(uri);
        switch (type) {
            case C.TYPE_DASH:

                break;
            case C.TYPE_HLS:

                break;
            case C.TYPE_SS:

                break;
            case C.TYPE_OTHER:
            default:

                break;

        }
        // todo
        return null;
    }

    @Override
    public void prepare(Uri resUri, MediaSource mediaSource) {
        if (null == resUri || null == mediaSource) {
            KLog.e(TAG, "the resUri or mediaSource is null, Please check!");
            return;
        }

        mCurrentUri = resUri;
        mInternalPlayer.prepare(mediaSource);
        mInternalPlayer.setSeekParameters(SeekParameters.EXACT);
    }

    @Override
    public void startPlay() {
        mExpectState = STATE_PLAYING;
        tryToExpectState();
    }

    @Override
    public void pausePlay() {
        mExpectState = STATE_PAUSED;
        tryToExpectState();
    }

    // 达到期望的状态
    private void tryToExpectState() {
        if (mCurrentState != mExpectState) {
            if (mExpectState == STATE_PLAYING) {
                KLog.d(TAG, "change to play state, start play");
                mCurrentState = STATE_PLAYING;
                setPlayWhenReady(true);
            } else if (mExpectState == STATE_PAUSED) {
                KLog.d(TAG, "change to paused state, pause play");
                mCurrentState = STATE_PAUSED;
                setPlayWhenReady(false);
            } else if (mExpectState == STATE_RELEASED) {
                KLog.d(TAG, "change to released state, release player");
                mCurrentState = STATE_RELEASED;
                mInternalPlayer.release();
            }
        }
    }

    //</editor-fold>

    //<editor-fold> implements of Player.EventListener
    @Override
    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        KLog.d(TAG, "trackGroups size:" + trackGroups.length + ", trackSelections size:" + trackSelections.length);
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
        KLog.d(TAG, "isLoading:" + isLoading);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        KLog.d(TAG, "playWhenReady:" + playWhenReady + ",playbackState:" + playbackState);
    }

    @Override
    public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {

    }

    @Override
    public void onIsPlayingChanged(boolean isPlaying) {
        KLog.d(TAG, "isPlaying:" + isPlaying);
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {
        KLog.d(TAG, "repeatMode:" + repeatMode);
    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        KLog.d(TAG, "shuffleModeEnabled:" + shuffleModeEnabled);
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        KLog.d(TAG, error.getMessage());
    }

    @Override
    public void onPositionDiscontinuity(int reason) {
        KLog.d(TAG, "reason:" + reason);
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    //</editor-fold>

    // <editor-fold> implements of Player

    @Nullable
    @Override
    public AudioComponent getAudioComponent() {
        return mInternalPlayer.getAudioComponent();
    }

    @Nullable
    @Override
    public VideoComponent getVideoComponent() {
        return mInternalPlayer.getVideoComponent();
    }

    @Nullable
    @Override
    public TextComponent getTextComponent() {
        return mInternalPlayer.getTextComponent();
    }

    @Nullable
    @Override
    public MetadataComponent getMetadataComponent() {
        return mInternalPlayer.getMetadataComponent();
    }

    @Override
    public Looper getApplicationLooper() {
        return mInternalPlayer.getApplicationLooper();
    }

    @Override
    public void addListener(EventListener listener) {
        // todo
    }

    @Override
    public void removeListener(EventListener listener) {
        // todo
    }

    /**
     * {@link Player#STATE_IDLE}、
     * {@link Player#STATE_BUFFERING}、
     * {@link Player#STATE_READY}、
     * {@link Player#STATE_ENDED}
     *
     * @return 返回播放状态，通常是辅助用的
     */
    @Override
    public int getPlaybackState() {
        return mInternalPlayer.getPlaybackState();
    }

    @Override
    public int getPlaybackSuppressionReason() {
        return mInternalPlayer.getPlaybackSuppressionReason();
    }

    @Override
    public boolean isPlaying() {
        // todo
        return false;
    }

    @Nullable
    @Override
    public ExoPlaybackException getPlaybackError() {
        return null;
    }

    @Override
    public void setPlayWhenReady(boolean playWhenReady) {
        mInternalPlayer.setPlayWhenReady(playWhenReady);
    }

    @Override
    public boolean getPlayWhenReady() {
        return mInternalPlayer.getPlayWhenReady();
    }

    @Override
    public void setRepeatMode(int repeatMode) {
        mInternalPlayer.setRepeatMode(repeatMode);
    }

    @Override
    public int getRepeatMode() {
        return mInternalPlayer.getRepeatMode();
    }

    @Override
    public void setShuffleModeEnabled(boolean shuffleModeEnabled) {
        mInternalPlayer.setShuffleModeEnabled(shuffleModeEnabled);
    }

    @Override
    public boolean getShuffleModeEnabled() {
        return mInternalPlayer.getShuffleModeEnabled();
    }

    @Override
    public boolean isLoading() {
        return mInternalPlayer.isLoading();
    }

    @Override
    public void seekToDefaultPosition() {
        mInternalPlayer.seekToDefaultPosition();
    }

    @Override
    public void seekToDefaultPosition(int windowIndex) {
        mInternalPlayer.seekToDefaultPosition(windowIndex);
    }

    @Override
    public void seekTo(long positionMs) {
        mInternalPlayer.seekTo(positionMs);
    }

    @Override
    public void seekTo(int windowIndex, long positionMs) {
        mInternalPlayer.seekTo(windowIndex, positionMs);
    }

    @Override
    public boolean hasPrevious() {
        return mInternalPlayer.hasPrevious();
    }

    @Override
    public void previous() {
        // 获取前一个Window的索引，然后滑动到该window的默认播放位置
        mInternalPlayer.previous();
    }

    @Override
    public boolean hasNext() {
        return mInternalPlayer.hasNext();
    }

    @Override
    public void next() {
        // 获取后一个window的索引，然后滑动到改window默认的播放位置
        mInternalPlayer.next();
    }

    @Override
    public void setPlaybackParameters(@Nullable PlaybackParameters playbackParameters) {
        mInternalPlayer.setPlaybackParameters(playbackParameters);
    }

    @Override
    public PlaybackParameters getPlaybackParameters() {
        return mInternalPlayer.getPlaybackParameters();
    }

    @Override
    public void stop() {
        stop(false);
    }

    @Override
    public void stop(boolean reset) {
        // todo
//        mInternalPlayer.stop(reset);
    }

    @Override
    public void release() {
        mExpectState = STATE_RELEASED;
        tryToExpectState();
    }

    @Override
    public int getRendererCount() {
        return mInternalPlayer.getRendererCount();
    }

    @Override
    public int getRendererType(int index) {
        return mInternalPlayer.getRendererType(index);
    }

    @Override
    public TrackGroupArray getCurrentTrackGroups() {
        return mInternalPlayer.getCurrentTrackGroups();
    }

    @Override
    public TrackSelectionArray getCurrentTrackSelections() {
        return mInternalPlayer.getCurrentTrackSelections();
    }

    @Nullable
    @Override
    public Object getCurrentManifest() {
        return mInternalPlayer.getCurrentManifest();
    }

    @Override
    public Timeline getCurrentTimeline() {
        return mInternalPlayer.getCurrentTimeline();
    }

    @Override
    public int getCurrentPeriodIndex() {
        return mInternalPlayer.getCurrentPeriodIndex();
    }

    @Override
    public int getCurrentWindowIndex() {
        return mInternalPlayer.getCurrentWindowIndex();
    }

    @Override
    public int getNextWindowIndex() {
        return mInternalPlayer.getNextWindowIndex();
    }

    @Override
    public int getPreviousWindowIndex() {
        return mInternalPlayer.getPreviousWindowIndex();
    }

    @Nullable
    @Override
    public Object getCurrentTag() {
        return mInternalPlayer.getCurrentTag();
    }

    @Override
    public long getDuration() {
        return mInternalPlayer.getDuration();
    }

    @Override
    public long getCurrentPosition() {
        return mInternalPlayer.getCurrentPosition();
    }

    @Override
    public long getBufferedPosition() {
        return mInternalPlayer.getBufferedPosition();
    }

    @Override
    public int getBufferedPercentage() {
        return mInternalPlayer.getBufferedPercentage();
    }

    @Override
    public long getTotalBufferedDuration() {
        return mInternalPlayer.getTotalBufferedDuration();
    }

    @Override
    public boolean isCurrentWindowDynamic() {
        return mInternalPlayer.isCurrentWindowDynamic();
    }

    @Override
    public boolean isCurrentWindowSeekable() {
        return mInternalPlayer.isCurrentWindowSeekable();
    }

    @Override
    public boolean isPlayingAd() {
        return false;
    }

    @Override
    public int getCurrentAdGroupIndex() {
        return mInternalPlayer.getCurrentAdGroupIndex();
    }

    @Override
    public int getCurrentAdIndexInAdGroup() {
        return mInternalPlayer.getCurrentAdIndexInAdGroup();
    }

    @Override
    public long getContentDuration() {
        return mInternalPlayer.getContentDuration();
    }

    @Override
    public long getContentPosition() {
        return mInternalPlayer.getContentPosition();
    }

    @Override
    public long getContentBufferedPosition() {
        return mInternalPlayer.getContentBufferedPosition();
    }


    // <editor-fold>

}
