package com.rainmonth.player.activity.detail;

import android.content.res.Configuration;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;

import com.google.android.exoplayer2.SeekParameters;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.player.R;
import com.rainmonth.player.VideoManager;
import com.rainmonth.player.base.player.exo.Exo2PlayerManager;
import com.rainmonth.player.builder.VideoPlayerConfigBuilder;
import com.rainmonth.player.listener.GSYSampleCallBack;
import com.rainmonth.player.utils.Debugger;
import com.rainmonth.player.utils.OrientationUtils;
import com.rainmonth.player.video.base.VideoPlayer;
import com.rainmonth.player.view.LandLayoutPlayerView;

import java.util.HashMap;
import java.util.Map;

/**
 * 普通的详情页视频播放
 * <p>
 * 说明：播放视窗在底部，底部可以展示其他内容（如视频列表、视频详情等）
 * 功能：
 * 1、顶部播放视图支持固定；
 * 2、顶部播放视图支持全屏；
 *
 * @author 张豪成
 * @date 2019-11-01 14:46
 */
public class NormalDetailVideoPlayActivity extends BaseActivity {
    NestedScrollView mNestedScrollView;
    LandLayoutPlayerView mDetailPlayer;

    private boolean isPlay;
    private boolean isPause;

    private OrientationUtils mOrientationUtils;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_activity_norma_detail_player_play;
    }

    @Override
    protected void initViewsAndEvents() {
        mNestedScrollView = findViewById(R.id.post_detail_nested_scroll);
        mDetailPlayer = findViewById(R.id.detail_player);

        ImageView thumbImage = new ImageView(this);
        thumbImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbImage.setImageResource(R.drawable.player_sample_thumb1);

        mDetailPlayer.getTitleTextView().setVisibility(View.GONE);
        mDetailPlayer.getBackButton().setVisibility(View.GONE);

        String playUrl = getPlayUrl();

        mOrientationUtils = new OrientationUtils(this, mDetailPlayer);
        mOrientationUtils.setEnable(false);

        Map<String, String> header = new HashMap<>();
        header.put("ee", "33");
        header.put("allowCrossProtocolRedirects", "true");
        VideoPlayerConfigBuilder builder = new VideoPlayerConfigBuilder();
        builder.setThumbImageView(thumbImage)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(playUrl)
                .setMapHeadData(header)
                .setCacheWithPlay(false)
                .setVideoTitle("详情播放测试")
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        Debugger.printfError("***** onPrepared **** " + objects[0]);
                        Debugger.printfError("***** onPrepared **** " + objects[1]);
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        mOrientationUtils.setEnable(mDetailPlayer.isRotateWithSystem());
                        isPlay = true;

                        //设置 seek 的临近帧。
                        if (mDetailPlayer.getGSYVideoManager().getPlayer() instanceof Exo2PlayerManager) {
                            ((Exo2PlayerManager) mDetailPlayer.getGSYVideoManager().getPlayer()).setSeekParameter(SeekParameters.NEXT_SYNC);
                            Debugger.printfError("***** setSeekParameter **** ");
                        }
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        Debugger.printfError("***** onEnterFullscreen **** " + objects[0]);//title
                        Debugger.printfError("***** onEnterFullscreen **** " + objects[1]);//当前全屏player
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                    }

                    @Override
                    public void onClickStartError(String url, Object... objects) {
                        super.onClickStartError(url, objects);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        Debugger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                        Debugger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                        if (mOrientationUtils != null) {
                            mOrientationUtils.backToProtVideo();
                        }
                    }
                })
                .setLockClickListener((v, lock) -> {
                    if (mOrientationUtils != null) {
                        mOrientationUtils.setEnable(!lock);
                    }
                })
                .build(mDetailPlayer);

        mDetailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                mOrientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionBar，第二个true是否需要隐藏statusBar
                mDetailPlayer.startWindowFullscreen(mContext, false, true);
            }
        });

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    public void onBackPressed() {
        if (mOrientationUtils != null) {
            mOrientationUtils.backToProtVideo();
        }
        if (VideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            getCurrentPlayer().release();
        }
        if (mOrientationUtils != null) {
            mOrientationUtils.releaseListener();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isPlay && !isPause) {
            mDetailPlayer.onConfigurationChanged(this, newConfig, mOrientationUtils, true, true);
        }
    }

    private VideoPlayer getCurrentPlayer() {
        if (mDetailPlayer.getFullWindowPlayer() != null) {
            return mDetailPlayer.getFullWindowPlayer();
        }
        return mDetailPlayer;
    }

    private String getPlayUrl() {
        return "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    }
}
