package com.rainmonth.player.activity.detail;

import android.view.View;
import android.widget.ImageView;

import com.rainmonth.player.R;
import com.rainmonth.player.builder.VideoPlayerConfigBuilder;
import com.rainmonth.player.listener.GSYVideoProgressListener;
import com.rainmonth.player.listener.LockClickListener;
import com.rainmonth.player.video.ADVideoPlayer;
import com.rainmonth.player.video.NormalVideoPlayer;

/**
 * 支持内容视频任意位置播放广告的示例
 */
public class ConfigAdDetailVideoPlayerActivity extends BaseAdDetailVideoPlayerActivity<NormalVideoPlayer, ADVideoPlayer> {
    private NormalVideoPlayer mContentPlayer;
    private ADVideoPlayer mAdPlayer;


    private String urlAd = "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4";
    private String urlAd2 = "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4";
    private String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_activity_config_ad_detail_video_player;
    }

    @Override
    protected void initViewsAndEvents() {
        mContentPlayer = findViewById(R.id.content_player);
        mAdPlayer = findViewById(R.id.ad_player);

        resolveNormalVideoUI();
        initVideoBuilderMode();

        mContentPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (mOrientationUtils != null) {
                    mOrientationUtils.setEnable(!lock);
                }
            }
        });
        mContentPlayer.setStartAfterPrepared(false);
        mContentPlayer.setReleaseWhenLossAudio(false);
        mContentPlayer.setGSYVideoProgressListener(new GSYVideoProgressListener() {
            private int preSecond = 0;
            @Override
            public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                //在5秒的时候弹出中间广告
                int currentSecond = currentPosition / 1000;
                if (currentSecond == 5 && currentSecond != preSecond) {
                    mContentPlayer.getCurrentPlayer().onVideoPause();
                    getAdPlayerConfigBuilder().setUrl(urlAd2).build(mAdPlayer);
                    startAdPlay();
                }
                preSecond = currentSecond;
            }
        });
    }

    @Override
    public NormalVideoPlayer getVideoPlayer() {
        return mContentPlayer;
    }

    @Override
    public VideoPlayerConfigBuilder getVideoPlayerConfigBuilder() {
        //不需要builder的
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.sample_cover_1);
        return getCommonBuilder()
                .setUrl(url)
                .setThumbImageView(imageView);
    }

    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    @Override
    public ADVideoPlayer getAdVideoPlayer() {
        return mAdPlayer;
    }


    @Override
    public VideoPlayerConfigBuilder getAdPlayerConfigBuilder() {
        return getCommonBuilder().setUrl(urlAd);
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    public boolean isNeedAdOnStart() {
        return true;
    }

    /**
     * 公用的视频配置
     */
    private VideoPlayerConfigBuilder getCommonBuilder() {
        return new VideoPlayerConfigBuilder()
                .setCacheWithPlay(true)
                .setVideoTitle(" ")
                .setFullHideActionBar(true)
                .setFullHideStatusBar(true)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)//打开动画
                .setNeedLockFull(true)
                .setSeekRatio(1);
    }

    private void resolveNormalVideoUI() {
        //增加title
        mContentPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        mContentPlayer.getBackButton().setVisibility(View.VISIBLE);
    }
}