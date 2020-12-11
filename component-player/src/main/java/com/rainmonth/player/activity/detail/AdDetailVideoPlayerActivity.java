package com.rainmonth.player.activity.detail;

import android.content.res.Configuration;
import android.media.MediaMetadataRetriever;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.player.DemoDataFactory;
import com.rainmonth.player.R;
import com.rainmonth.player.VideoManager;
import com.rainmonth.player.listener.GSYSampleCallBack;
import com.rainmonth.player.model.VideoListBean;
import com.rainmonth.player.utils.OrientationUtils;
import com.rainmonth.player.video.base.VideoPlayer;
import com.rainmonth.player.view.ConfigVideoPlayerView;

import java.util.HashMap;
import java.util.List;

/**
 * 带广告的详情播放
 * - 广告支持跳过
 * - 广告支持显示剩余广告时长
 *
 * @author RandyZhang
 * @date 2020/12/2 6:19 PM
 */
public class AdDetailVideoPlayerActivity extends BaseActivity {

    NestedScrollView mNestedScrollView;
    ConfigVideoPlayerView mDetailPlayer;

    ImageView mCoverImage;
    MediaMetadataRetriever mCoverRetriever;
    OrientationUtils mOrientationUtils;

    private boolean isPlay;
    private boolean isPause;
    private boolean isRelease;

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_activity_config_detail_player_play;
    }

    @Override
    protected void initViewsAndEvents() {
        mNestedScrollView = findViewById(R.id.post_detail_nested_scroll);
        mDetailPlayer = findViewById(R.id.detail_player);


        List<VideoListBean> clarityList = DemoDataFactory.getSwitchClarityPlayList();
        mDetailPlayer.setUp(clarityList, true, "");
        mCoverImage = new ImageView(this);
        mCoverImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mDetailPlayer.setThumbImageView(mCoverImage);

        mDetailPlayer.getTitleTextView().setVisibility(View.GONE);
        mDetailPlayer.getBackButton().setVisibility(View.GONE);

        mOrientationUtils = new OrientationUtils(this, mDetailPlayer);
        mOrientationUtils.setEnable(false);

        mDetailPlayer.setIsTouchWiget(false);// 初始时不能通过滑动改变进度
        mDetailPlayer.setRotateViewAuto(false);// 不能自动旋转
        mDetailPlayer.setShowFullAnimation(true);// 打开全屏动画
        mDetailPlayer.setNeedLockFull(true);
        mDetailPlayer.setSeekRatio(1);
        mDetailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailPlayer.startWindowFullscreen(mContext, true, true);
            }
        });
        mDetailPlayer.setVideoAllCallBack(new GSYSampleCallBack() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                //orientationUtils.setEnable(true);
                mOrientationUtils.setEnable(mDetailPlayer.isRotateWithSystem());
                isPlay = true;
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
                //屏蔽，实现竖屏全屏
                //if (orientationUtils != null) {
                //orientationUtils.backToProtVideo();
                //}
            }
        });

        if (clarityList != null && clarityList.size() > 0) {
            loadFirstFrameCover(clarityList.get(0).getUrl());
        }
    }

    @Override
    protected void onResume() {
        getCurrentPlayer().onVideoResume(true);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onPause() {
        getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRelease = true;
        if (isPlay) {
            getCurrentPlayer().release();
        }
        if (mOrientationUtils != null) {
            mOrientationUtils.releaseListener();
        }
        if (mCoverRetriever != null) {
            mCoverRetriever.release();
            mCoverRetriever = null;
        }

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
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            mDetailPlayer.onConfigurationChanged(this, newConfig, mOrientationUtils, true, true);
        }
        //竖屏全屏
        mOrientationUtils.setEnable(false);
    }

    /**
     * 这里只是演示，并不建议直接这么做
     * MediaMetadataRetriever最好做一个独立的管理器
     * 使用缓存
     * 注意资源的开销和异步等
     *
     * @param url
     */
    public void loadFirstFrameCover(String url) {

        //原始方法
        /*final MediaMetadataRetriever mediaMetadataRetriever = getMediaMetadataRetriever(url);
        //获取帧图片
        if (getMediaMetadataRetriever(url) != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Bitmap bitmap = mediaMetadataRetriever
                            .getFrameAtTime(1000, MediaMetadataRetriever.OPTION_CLOSEST);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bitmap != null && !isRelease) {
                                Debuger.printfLog("time " + System.currentTimeMillis());
                                //显示
                                coverImageView.setImageBitmap(bitmap);
                            }
                        }
                    });
                }
            }).start();
        }*/

        //可以参考Glide，内部也是封装了MediaMetadataRetriever
        Glide.with(this.getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
                                .centerCrop()
                                .error(R.drawable.player_sample_thumb1)
                                .placeholder(R.drawable.player_sample_thumb1))
                .load(url)
                .into(mCoverImage);
    }

    public MediaMetadataRetriever getMediaMetadataRetriever(String url) {
        if (mCoverRetriever == null) {
            mCoverRetriever = new MediaMetadataRetriever();
        }
        mCoverRetriever.setDataSource(url, new HashMap<String, String>());
        return mCoverRetriever;
    }

    private VideoPlayer getCurrentPlayer() {
        if (mDetailPlayer.getFullWindowPlayer() != null) {
            return mDetailPlayer.getFullWindowPlayer();
        }
        return mDetailPlayer;
    }
}
