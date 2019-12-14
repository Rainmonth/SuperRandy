package com.rainmonth.music.core.player.impl;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.rainmonth.music.BuildConfig;
import com.rainmonth.music.core.helper.EventLogger;
import com.rainmonth.music.core.helper.ExoHelper;
import com.rainmonth.music.core.player.BasePlayer;
import com.rainmonth.music.core.player.IPlayer;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * BasePlayer的Exo实现，内部持有ExoPlayer播放器实例
 * ExoPlayer 播放的几大要素：
 * 1. MediaSource
 * 2. Renderer
 * 3. TrackSelector
 * 4. LoadControl
 *
 * @author 张豪成
 * @date 2019-12-05 20:24
 */
public class RandyExoPlayer extends BasePlayer implements Player.EventListener, AnalyticsListener {

    public static final String Tag = RandyExoPlayer.class.getSimpleName();

    private SimpleExoPlayer mInternalPlayer;                    // 内部持有的ExoPlayer对象
    private Context mAppContext;                                // context（最好是application的context）
    private ExoHelper mHelper;                                  // Exo辅助类

    private String mDataSourcePath;                             //
    private String mOverrideExtension;                          //
    private boolean mCacheEnable;                               //
    private File mCacheDir;                                        // 缓存目录
    private Map<String, String> mHeaders = new HashMap<>();     // headers
    private Surface mSurface;
    private int mWidth = 0, mHeight = 0;
    private MappingTrackSelector mTrackSelector;
    private DefaultRenderersFactory mRenderersFactory;
    private EventLogger mEventLogger;
    private LoadControl mLoadControl;
    private PlaybackParameters mSpeedPlaybackParameters;

    private MediaSource mMediaSource;

    public RandyExoPlayer(Context context) {
        mAppContext = context.getApplicationContext();
        mHelper = ExoHelper.newInstance(mAppContext, mHeaders);
    }

    // <editor-fold>IPlayer实现
    @Override
    public void setDisplay(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            setSurface(null);
        } else {
            setSurface(surfaceHolder.getSurface());
        }
    }

    @Override
    public void setSurface(Surface surface) {
        mSurface = surface;
        if (mInternalPlayer != null) {
            if (surface != null && !surface.isValid()) {
                mSurface = null;
            }
            mInternalPlayer.setVideoSurface(surface);
        }
    }

    @Override
    public void setDataSource(Context context, Uri uri) {
        mDataSourcePath = uri.toString();
        // todo 根据uri获取ExoPlayer需要的MediaSource
        mMediaSource = mHelper.getMediaSource(uri.getPath(), mOverrideExtension, mCacheEnable, mCacheDir);
    }

    @Override
    public void setDataSource(String path) {
        setDataSource(mAppContext, Uri.parse(path));
    }

    @Override
    public void setDataSource(Context context, Uri uri, Map<String, String> headers) {
        if (headers != null) {
            mHeaders.clear();
            mHeaders.putAll(headers);
        }
        setDataSource(context, uri);
    }

    @Override
    public void prepare() {
        prepareAsync();
    }

    @Override
    public void prepareAsync() {
        if (mInternalPlayer != null) {
            if (BuildConfig.DEBUG) {
                throw new IllegalStateException("can't prepare a prepared player");
            } else {
                KLog.e(Tag, "can't prepare a prepared player");
                return;
            }
        }
        prepareAsyncInternal();
    }

    /**
     * prepareAsync()内部实现
     */
    private void prepareAsyncInternal() {
        // todo
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (mTrackSelector == null) {
                    mTrackSelector = mHelper.getTrackSelector();
                }
                mEventLogger = new EventLogger(mTrackSelector);

                boolean preferExtensionDecoders = true;
                boolean useExtensionRenderers = true;//是否开启扩展
                @DefaultRenderersFactory.ExtensionRendererMode int extensionRendererMode = useExtensionRenderers
                        ? (preferExtensionDecoders ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
                        : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
                        : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;

                if (mRenderersFactory == null) {
                    mRenderersFactory = new DefaultRenderersFactory(mAppContext);
                    mRenderersFactory.setExtensionRendererMode(extensionRendererMode);
                }
                if (mLoadControl == null) {
                    mLoadControl = mHelper.getLoadControl();
                }

                mInternalPlayer = ExoPlayerFactory.newSimpleInstance(
                        mAppContext, mRenderersFactory, mTrackSelector, mLoadControl, null,
                        Looper.getMainLooper());
                mInternalPlayer.addListener(mEventLogger);
                mInternalPlayer.addAnalyticsListener(RandyExoPlayer.this);
                mInternalPlayer.addListener(RandyExoPlayer.this);

                if (mSpeedPlaybackParameters != null) {
                    mInternalPlayer.setPlaybackParameters(mSpeedPlaybackParameters);
                }

                if (mSurface != null) {
                    mInternalPlayer.setVideoSurface(mSurface);
                }

                mInternalPlayer.prepare(mMediaSource);
                mInternalPlayer.setPlayWhenReady(false);
            }
        });

    }

    @Override
    public void start() {
        if (mInternalPlayer == null) {
            return;
        }
        mInternalPlayer.setPlayWhenReady(true);
    }

    @Override
    public void pause() {
        if (mInternalPlayer == null) {
            return;
        }
        mInternalPlayer.setPlayWhenReady(false);
    }

    @Override
    public void stop() {
        if (mInternalPlayer == null) {
            return;
        }
        mInternalPlayer.release();
    }

    @Override
    public int getVideoWidth() {
        return mWidth;
    }

    @Override
    public int getVideoHeight() {
        return mHeight;
    }

    @Override
    public boolean isPlaying() {
        if (mInternalPlayer == null) {
            return false;
        }
        int state = mInternalPlayer.getPlaybackState();
        switch (state) {
            case Player.STATE_BUFFERING:
            case Player.STATE_READY:
                return mInternalPlayer.getPlayWhenReady();
            case Player.STATE_IDLE:
            case Player.STATE_ENDED:
            default:
                return false;
        }
    }

    @Override
    public void seekTo(long milliseconds) {
        if (mInternalPlayer == null) {
            return;
        }
        mInternalPlayer.seekTo(milliseconds);
    }

    @Override
    public void release() {
        if (mInternalPlayer == null) {
            return;
        }
        mInternalPlayer.release();
    }

    @Override
    public void reset() {
        if (mInternalPlayer != null) {
            mInternalPlayer.release();
            mInternalPlayer = null;
        }
        if (mHelper != null) {
            // todo
        }

        mSurface = null;
        mDataSourcePath = null;

    }

    @Override
    public long getCurrentPosition() {
        if (mInternalPlayer == null)
            return 0;
        return mInternalPlayer.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        if (mInternalPlayer == null) {
            return 0;
        }
        return mInternalPlayer.getDuration();
    }

    @Override
    public void getSourceInfo() {

    }
    //</editor-fold>

    //<editor-fold> Getters & Setters

    public ExoHelper getHelper() {
        return mHelper;
    }

    public String getOverrideExtension() {
        return mOverrideExtension;
    }

    public void setOverrideExtension(String mOverrideExtension) {
        this.mOverrideExtension = mOverrideExtension;
    }

    public boolean isCacheEnable() {
        return mCacheEnable;
    }

    public void setCacheEnable(boolean cacheEnable) {
        this.mCacheEnable = cacheEnable;
    }

    public File getCacheDir() {
        return mCacheDir;
    }

    public void setCacheDir(File cacheDir) {
        this.mCacheDir = cacheDir;
    }

    public PlaybackParameters getSpeedPlaybackParameters() {
        return mSpeedPlaybackParameters;
    }

    public void setSpeedPlaybackParameters(PlaybackParameters playbackParameters) {
        this.mSpeedPlaybackParameters = playbackParameters;
    }
    //</editor-fold>

    //<editor-fold> Player.EventListener
    @Override
    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {

    }

    @Override
    public void onIsPlayingChanged(boolean isPlaying) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        notifyOnError(IPlayer.MEDIA_ERROR_UNKNOWN, IPlayer.MEDIA_ERROR_UNKNOWN);
    }

    @Override
    public void onPositionDiscontinuity(int reason) {
        // todo 注意两个onPositionDiscontinuity的区别
    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {
        notifyOnSeekComplete();
    }
    //</editor-fold>

    //<editor-fold> Player.AnalyticsListener
    private boolean lastReportedPlayWhenReady;
    private int lastPlaybackState;
    private boolean isBuffering, isPreparing;

    @Override
    public void onPlayerStateChanged(EventTime eventTime, boolean playWhenReady, int playbackState) {
        // todo 根据播放状态的改变来进行事件的通知
        if (lastReportedPlayWhenReady != playWhenReady && lastPlaybackState != playbackState) {
            if (isBuffering) {
                switch (playbackState) {
                    case Player.STATE_READY:
                    case Player.STATE_ENDED:
                        notifyOnInfo(IPlayer.MEDIA_INFO_BUFFERING_END, mInternalPlayer.getBufferedPercentage());
                        isBuffering = true;
                        break;
                }
            }
            if (isPreparing) {
                switch (playbackState) {
                    case Player.STATE_READY:
                        notifyOnPrepared();
                        isPreparing = false;
                        break;
                }
            }
            switch (playbackState) {
                case Player.STATE_BUFFERING:
                    notifyOnInfo(IPlayer.MEDIA_INFO_BUFFERING_START, mInternalPlayer.getBufferedPercentage());
                    isBuffering = true;
                    break;
                case Player.STATE_ENDED:
                    notifyOnCompletion();
                    break;
                case Player.STATE_IDLE:
                case Player.STATE_READY:
                default:
                    break;
            }
        }

        lastReportedPlayWhenReady = playWhenReady;
        lastPlaybackState = playbackState;
    }

    @Override
    public void onPlaybackSuppressionReasonChanged(EventTime eventTime, int playbackSuppressionReason) {

    }

    @Override
    public void onIsPlayingChanged(EventTime eventTime, boolean isPlaying) {

    }

    @Override
    public void onTimelineChanged(EventTime eventTime, int reason) {

    }

    @Override
    public void onPositionDiscontinuity(EventTime eventTime, int reason) {
        notifyOnInfo(MEDIA_INFO_POSITION_DISCONTINUITY, reason);
    }

    @Override
    public void onSeekStarted(EventTime eventTime) {

    }

    @Override
    public void onSeekProcessed(EventTime eventTime) {

    }

    @Override
    public void onPlaybackParametersChanged(EventTime eventTime, PlaybackParameters playbackParameters) {

    }

    @Override
    public void onRepeatModeChanged(EventTime eventTime, int repeatMode) {

    }

    @Override
    public void onShuffleModeChanged(EventTime eventTime, boolean shuffleModeEnabled) {

    }

    @Override
    public void onLoadingChanged(EventTime eventTime, boolean isLoading) {

    }

    @Override
    public void onPlayerError(EventTime eventTime, ExoPlaybackException error) {

    }

    @Override
    public void onTracksChanged(EventTime eventTime, TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadStarted(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {

    }

    @Override
    public void onLoadCompleted(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {

    }

    @Override
    public void onLoadCanceled(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData) {

    }

    @Override
    public void onLoadError(EventTime eventTime, MediaSourceEventListener.LoadEventInfo loadEventInfo, MediaSourceEventListener.MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {

    }

    @Override
    public void onDownstreamFormatChanged(EventTime eventTime, MediaSourceEventListener.MediaLoadData mediaLoadData) {

    }

    @Override
    public void onUpstreamDiscarded(EventTime eventTime, MediaSourceEventListener.MediaLoadData mediaLoadData) {

    }

    @Override
    public void onMediaPeriodCreated(EventTime eventTime) {

    }

    @Override
    public void onMediaPeriodReleased(EventTime eventTime) {

    }

    @Override
    public void onReadingStarted(EventTime eventTime) {

    }

    @Override
    public void onBandwidthEstimate(EventTime eventTime, int totalLoadTimeMs, long totalBytesLoaded, long bitrateEstimate) {

    }

    @Override
    public void onSurfaceSizeChanged(EventTime eventTime, int width, int height) {

    }

    @Override
    public void onMetadata(EventTime eventTime, Metadata metadata) {

    }

    @Override
    public void onDecoderEnabled(EventTime eventTime, int trackType, DecoderCounters decoderCounters) {

    }

    @Override
    public void onDecoderInitialized(EventTime eventTime, int trackType, String decoderName, long initializationDurationMs) {

    }

    @Override
    public void onDecoderInputFormatChanged(EventTime eventTime, int trackType, Format format) {

    }

    @Override
    public void onDecoderDisabled(EventTime eventTime, int trackType, DecoderCounters decoderCounters) {

    }

    @Override
    public void onAudioSessionId(EventTime eventTime, int audioSessionId) {

    }

    @Override
    public void onAudioAttributesChanged(EventTime eventTime, AudioAttributes audioAttributes) {

    }

    @Override
    public void onVolumeChanged(EventTime eventTime, float volume) {

    }

    @Override
    public void onAudioUnderrun(EventTime eventTime, int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {

    }

    @Override
    public void onDroppedVideoFrames(EventTime eventTime, int droppedFrames, long elapsedMs) {

    }

    @Override
    public void onVideoSizeChanged(EventTime eventTime, int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        notifyOnVideoSizeChanged((int) (pixelWidthHeightRatio * width), height);
    }

    @Override
    public void onRenderedFirstFrame(EventTime eventTime, @Nullable Surface surface) {

    }

    @Override
    public void onDrmSessionAcquired(EventTime eventTime) {

    }

    @Override
    public void onDrmKeysLoaded(EventTime eventTime) {

    }

    @Override
    public void onDrmSessionManagerError(EventTime eventTime, Exception error) {

    }

    @Override
    public void onDrmKeysRestored(EventTime eventTime) {

    }

    @Override
    public void onDrmKeysRemoved(EventTime eventTime) {

    }

    @Override
    public void onDrmSessionReleased(EventTime eventTime) {

    }

    //</editor-fold>
}
