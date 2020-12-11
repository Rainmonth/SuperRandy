package com.rainmonth.player;

import android.content.res.Configuration;
import android.view.View;

import com.rainmonth.player.activity.detail.BaseDetailVideoPlayerActivity;
import com.rainmonth.player.builder.VideoPlayerConfigBuilder;
import com.rainmonth.player.listener.GSYSampleCallBack;
import com.rainmonth.player.utils.OrientationOption;
import com.rainmonth.player.utils.OrientationUtils;
import com.rainmonth.player.video.ADVideoPlayer;
import com.rainmonth.player.video.base.BaseVideoPlayer;
import com.rainmonth.player.video.base.VideoPlayer;
import com.rainmonth.player.video.base.VideoView;

/**
 * 广告详情播放基类
 * 实现原理就是 在详情播放的基础上 添加一个播放器，一个用于辅助广告播放旋转的{@link OrientationUtils}，子类需要
 * 提供以下内容：
 * 1. 广告的开始播放的逻辑判断，{@link #isNeedAdOnStart()}；
 * 2. 用于播放广告的播放器，{@link #getAdVideoPlayer()}
 * 3. 如何构建广告播放器，{@link #getAdPlayerConfigBuilder()}
 *
 * @param <T> 主题内容视频播放器，继承自{@link BaseVideoPlayer}
 * @param <R> 广告内容视频播放器，继承自{@link ADVideoPlayer}
 */
public abstract class BaseAdDetailVideoPlayerActivity<T extends BaseVideoPlayer, R extends ADVideoPlayer> extends BaseDetailVideoPlayerActivity<T> {

    protected OrientationUtils mADOrientationUtils;

    @Override
    public void initVideo() {
        super.initVideo();
        //外部辅助的旋转，帮助全屏
        mADOrientationUtils = new OrientationUtils(this, getAdVideoPlayer(), getOrientationOption());
        //初始化不打开外部的旋转
        mADOrientationUtils.setEnable(false);
        if (getAdVideoPlayer().getFullscreenButton() != null) {
            getAdVideoPlayer().getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //直接横屏
                    showADFull();
                    clickForFullScreen();
                }
            });
        }
    }

    /**
     * 选择builder模式
     */
    @Override
    public void initVideoBuilderMode() {
        super.initVideoBuilderMode();
        getAdPlayerConfigBuilder()
                .setVideoAllCallBack(new GSYSampleCallBack() {

                    @Override
                    public void onStartPrepared(String url, Object... objects) {
                        super.onStartPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        mADOrientationUtils.setEnable(getDetailOrientationRotateAuto());
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        //广告结束，释放
                        getAdVideoPlayer().getCurrentPlayer().release();
                        getAdVideoPlayer().onVideoReset();
                        getAdVideoPlayer().setVisibility(View.GONE);
                        //开始播放原视频，根据是否处于全屏状态判断
                        getVideoPlayer().getCurrentPlayer().startAfterPrepared();
                        if (getAdVideoPlayer().getCurrentPlayer().isIfCurrentIsFullscreen()) {
                            getAdVideoPlayer().removeFullWindowViewOnly();
                            if (!getVideoPlayer().getCurrentPlayer().isIfCurrentIsFullscreen()) {
                                showFull();
                                getVideoPlayer().setSaveBeforeFullSystemUiVisibility(getAdVideoPlayer().getSaveBeforeFullSystemUiVisibility());
                            }
                        }
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        //退出全屏逻辑
                        if (mADOrientationUtils != null) {
                            mADOrientationUtils.backToProtVideo();
                        }
                        if (getVideoPlayer().getCurrentPlayer().isIfCurrentIsFullscreen()) {
                            getVideoPlayer().onBackFullscreen();
                        }
                    }

                })
                .build(getAdVideoPlayer());
    }

    /**
     * 正常视频内容的全屏显示
     */
    @Override
    public void showFull() {
        if (mOrientationUtils.getIsLand() != 1) {
            //直接横屏
            mOrientationUtils.resolveByClick();
        }
        getVideoPlayer().startWindowFullscreen(this, hideActionBarWhenFull(), hideStatusBarWhenFull());
    }

    @Override
    public void onBackPressed() {
        if (mADOrientationUtils != null) {
            mADOrientationUtils.backToProtVideo();
        }
        if (VideoADManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        VideoADManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        VideoADManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoADManager.releaseAllVideos();
        if (mADOrientationUtils != null)
            mADOrientationUtils.releaseListener();
    }

    /**
     * orientationUtils 和  detailPlayer.onConfigurationChanged 方法是用于触发屏幕旋转的
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //如果旋转了就全屏
        boolean backUpIsPlay = isPlay;
        if (!isPause && getAdVideoPlayer().getVisibility() == View.VISIBLE) {
            if (isADStarted()) {
                isPlay = false;
                getAdVideoPlayer().getCurrentPlayer().onConfigurationChanged(this, newConfig, mADOrientationUtils, hideActionBarWhenFull(), hideStatusBarWhenFull());
            }
        }
        super.onConfigurationChanged(newConfig);
        isPlay = backUpIsPlay;
    }


    @Override
    public void onStartPrepared(String url, Object... objects) {
        super.onStartPrepared(url, objects);
    }

    @Override
    public void onPrepared(String url, Object... objects) {
        super.onPrepared(url, objects);
        if (isNeedAdOnStart()) {
            startAdPlay();
        }
    }

    @Override
    public void onEnterFullscreen(String url, Object... objects) {
        super.onEnterFullscreen(url, objects);
        //隐藏调全屏对象的返回按键
        VideoPlayer gsyVideoPlayer = (VideoPlayer) objects[1];
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    public void onComplete(String url, Object... objects) {

    }

    protected boolean isADStarted() {
        return getAdVideoPlayer().getCurrentPlayer().getCurrentState() >= 0 &&
                getAdVideoPlayer().getCurrentPlayer().getCurrentState() != VideoView.CURRENT_STATE_NORMAL
                && getAdVideoPlayer().getCurrentPlayer().getCurrentState() != VideoView.CURRENT_STATE_AUTO_COMPLETE;
    }

    /**
     * 显示播放广告
     */
    public void startAdPlay() {
        getAdVideoPlayer().setVisibility(View.VISIBLE);
        getAdVideoPlayer().startPlayLogic();
        if (getVideoPlayer().getCurrentPlayer().isIfCurrentIsFullscreen()) {
            showADFull();
            getAdVideoPlayer().setSaveBeforeFullSystemUiVisibility(getVideoPlayer().getSaveBeforeFullSystemUiVisibility());
        }
    }

    /**
     * 广告视频的全屏显示
     */
    public void showADFull() {
        if (mADOrientationUtils.getIsLand() != 1) {
            mADOrientationUtils.resolveByClick();
        }
        getAdVideoPlayer().startWindowFullscreen(BaseAdDetailVideoPlayerActivity.this, hideActionBarWhenFull(), hideStatusBarWhenFull());
    }

    /**
     * 可配置旋转 OrientationUtils
     */
    public OrientationOption getOrientationOption() {
        return null;
    }


    public abstract R getAdVideoPlayer();

    /**
     * 配置AD播放器
     */
    public abstract VideoPlayerConfigBuilder getAdPlayerConfigBuilder();

    /**
     * 是否播放开始广告
     * 如果返回 false ，setStartAfterPrepared 需要设置为 true
     */
    public abstract boolean isNeedAdOnStart();
}
