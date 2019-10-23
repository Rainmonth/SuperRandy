package com.rainmonth.music.videoplayer;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.EventLogger;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.music.R;
import com.socks.library.KLog;

/**
 * @date: 2018-12-20
 * @author: randy
 * @description: 视频播放器
 */
public class VideoPlayerActivity extends BaseActivity implements PlayerControlView.VisibilityListener {
    Player player;
    PlayerView playerView;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_activity_video_player;
    }

    @Override
    protected void initViewsAndEvents() {
        // 2. 创建Player
        initPlayer();
        // 3. Player与PlayerView绑定
        playerView = findViewById(R.id.player_view);
        playerView.setControllerVisibilityListener(this);

        playerView.setPlayer(player);
        // 4. 准备MediaSource


    }

    private void initPlayer() {
//        player = ExoPlayerFactory.newSimpleInstance(
//                        /* context= */ this, renderersFactory, trackSelector, drmSessionManager);
//        player.addListener(new PlayerEventListener());
//        player.setPlayWhenReady(startAutoPlay);
//        player.addAnalyticsListener(new EventLogger(trackSelector));
//        playerView.setPlayer(player);
//        playerView.setPlaybackPreparer(this);
    }

    @Override
    public void onVisibilityChange(int visibility) {

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
}
