package com.rainmonth.video;

import android.net.Uri;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.RandomTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.socks.library.KLog;

import java.util.Arrays;
import java.util.List;

/**
 * 计划提供以下示例：
 * 1、列表焦点播放；
 * 2、悬浮窗播放
 * 3、抖音式播放
 * 4、列表式播放
 * 5、支持播放视图的比例设置
 *
 * @date: 2018-12-20
 * @author: randy
 * @description: 视频播放器
 */
public class VideoPlayerActivity extends BaseActivity implements PlayerControlView.VisibilityListener,
        PlaybackPreparer {
    SimpleExoPlayer player;
    PlayerView playerView;

    DefaultTrackSelector trackSelector;
    boolean preferExtensionDecoder = false;
    boolean startAutoPlay = true;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.video_activity_video_player;
    }

    @Override
    protected void initViewsAndEvents() {
        // 2. 创建Player
        initPlayer();
        // 3. Player与PlayerView绑定
        playerView = findViewById(R.id.player_view);
        playerView.setControllerVisibilityListener(this);

        playerView.setPlayer(player);
        playerView.setPlaybackPreparer(this);
        // 4. 准备MediaSource
        MediaSource mediaSource = getMediaSource();
        player.prepare(mediaSource);

    }

    /**
     * 初始化Player
     */
    private void initPlayer() {
        if (player == null) {
            TrackSelection.Factory trackSelectionFactory = new RandomTrackSelection.Factory();
            trackSelector = new DefaultTrackSelector(trackSelectionFactory);
//            TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory();
//            String abrAlgorithm = intent.getStringExtra(ABR_ALGORITHM_EXTRA);
//            if (abrAlgorithm == null || ABR_ALGORITHM_DEFAULT.equals(abrAlgorithm)) {
//                trackSelectionFactory = new AdaptiveTrackSelection.Factory();
//            } else if (ABR_ALGORITHM_RANDOM.equals(abrAlgorithm)) {
//                trackSelectionFactory = new RandomTrackSelection.Factory();
//            } else {
//                showToast(R.string.error_unrecognized_abr_algorithm);
//                finish();
//                return;
//            }
            RenderersFactory renderersFactory =
                    buildRenderersFactory(preferExtensionDecoder);
            player = ExoPlayerFactory.newSimpleInstance(
                    /* context= */ this, renderersFactory, trackSelector);
            player.addListener(new PlayerEventListener());
            player.setPlayWhenReady(startAutoPlay);
            player.addAnalyticsListener(new EventLogger(trackSelector));
        }
    }

    private RenderersFactory buildRenderersFactory(boolean preferExtensionDecoders) {
        @DefaultRenderersFactory.ExtensionRendererMode
        int extensionRendererMode = preferExtensionDecoders
                ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
                : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON;

        return new DefaultRenderersFactory(/* context= */ this)
                .setExtensionRendererMode(extensionRendererMode);
    }

    private MediaSource getMediaSource() {
        List<String> mediaList = Arrays.asList(getResources().getStringArray(R.array.video_media_source_list));
//        Uri uri = Uri.parse("https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/audio-141.mp4");
        Uri uri = Uri.parse("https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/video-137.mp4");
        DataSource.Factory dataSource = new DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, mContext.getPackageName()));
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSource).createMediaSource(uri);
//        if (mediaList != null && mediaList.size() > 0) {
//            if (mediaList.size() == 1) {
//
//            } else {
//
//            }
//        }
        return mediaSource;
    }

    @Override
    public void onVisibilityChange(int visibility) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }

    /**
     * 0，网络文件；1，SD卡文件；2，raw目录下的文件，默认网络文件
     *
     * @param sourceType
     * @return
     */
    private MediaSource buildMediaSource(int sourceType) {
        switch (sourceType) {
            case 0:
            default:
                KLog.d("Player", "网络文件");
                break;
            case 1:
                KLog.d("Player", "SD卡文件");
                break;
            case 2:
                KLog.d("Player", "raw文件");
                break;

        }

        return null;
    }

    @Override
    public void preparePlayback() {
        KLog.d("preparePlayback");
    }

    /**
     * 播放器事件监听器
     */
    public class PlayerEventListener implements Player.EventListener {
        @Override
        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
            KLog.d("onTimelineChanged");
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            KLog.d("onTracksChanged");
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            KLog.d("onLoadingChanged");
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            KLog.d("onPlayerStateChanged");
        }

        @Override
        public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
            KLog.d("onPlaybackSuppressionReasonChanged");
        }

        @Override
        public void onIsPlayingChanged(boolean isPlaying) {
            KLog.d("onIsPlayingChanged");
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
            KLog.d("onRepeatModeChanged");
        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            KLog.d("onShuffleModeEnabledChanged");
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            KLog.d("onPlayerError");
        }

        @Override
        public void onPositionDiscontinuity(int reason) {
            KLog.d("onPositionDiscontinuity");
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            KLog.d("onPlaybackParametersChanged");
        }

        @Override
        public void onSeekProcessed() {
            KLog.d("onSeekProcessed");
        }
    }
}
