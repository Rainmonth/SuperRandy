package com.rainmonth.player.activity.detail;

import android.view.View;
import android.widget.ImageView;

import androidx.core.widget.NestedScrollView;

import com.rainmonth.player.R;
import com.rainmonth.player.builder.VideoPlayerConfigBuilder;
import com.rainmonth.player.listener.LockClickListener;
import com.rainmonth.player.video.ADListVideoPlayer;

import java.util.ArrayList;

/**
 * 带广告的详情播放
 * - 广告支持跳过
 * - 广告支持显示剩余广告时长
 *
 * @author RandyZhang
 * @date 2020/12/2 6:19 PM
 */
public class AdDetailVideoPlayerActivity extends BaseDetailVideoPlayerActivity<ADListVideoPlayer> {

    NestedScrollView mNestedScrollView;
    ADListVideoPlayer mDetailPlayer;

    @Override
    public ADListVideoPlayer getVideoPlayer() {
        return mDetailPlayer;
    }

    @Override
    public VideoPlayerConfigBuilder getVideoPlayerConfigBuilder() {
        return null;
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_activity_ad_detail_player_play;
    }

    @Override
    protected void initViewsAndEvents() {
        mNestedScrollView = findViewById(R.id.post_detail_nested_scroll);
        mDetailPlayer = findViewById(R.id.detail_player);
        //普通模式
        initVideo();

        ArrayList<ADListVideoPlayer.GSYADVideoModel> urls = new ArrayList<>();
        //广告1
        urls.add(new ADListVideoPlayer.GSYADVideoModel("http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4",
                "", ADListVideoPlayer.GSYADVideoModel.TYPE_AD));
        //正式内容1
        urls.add(new ADListVideoPlayer.GSYADVideoModel("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4",
                "正文1标题", ADListVideoPlayer.GSYADVideoModel.TYPE_NORMAL));
        //广告2
        urls.add(new ADListVideoPlayer.GSYADVideoModel("http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4",
                "", ADListVideoPlayer.GSYADVideoModel.TYPE_AD, true));
        //正式内容2
        urls.add(new ADListVideoPlayer.GSYADVideoModel("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4",
                "正文2标题", ADListVideoPlayer.GSYADVideoModel.TYPE_NORMAL));

        mDetailPlayer.setAdUp(urls, true, 0);

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.sample_cover_1);
        mDetailPlayer.setThumbImageView(imageView);

        resolveNormalVideoUI();

        mDetailPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        mDetailPlayer.setRotateViewAuto(false);
        mDetailPlayer.setLockLand(false);
        mDetailPlayer.setShowFullAnimation(false);
        mDetailPlayer.setNeedLockFull(true);

        mDetailPlayer.setVideoAllCallBack(this);

        mDetailPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (mOrientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    mOrientationUtils.setEnable(!lock);
                }
            }
        });
    }

    private void resolveNormalVideoUI() {
        //增加title
        mDetailPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        mDetailPlayer.getBackButton().setVisibility(View.VISIBLE);
    }
}
