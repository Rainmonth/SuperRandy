package com.rainmonth.music.videoplayer;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.music.R;

/**
 * 可配置的视频播放，可配置项：（先实现功能）
 * 1、配置视频源的清晰度（高清、清晰、4K）；
 * 2、左边上下滑动调节明暗，右边上下互动调节声音；
 * 3、可设置倍速播放
 * 4、可设置播放窗口比例
 *
 * @author 张豪成
 * @date 2019-11-01 14:37
 */
public class ConfigVideoPlayActivity extends BaseActivity {
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_activity_config_video_play;
    }

    @Override
    protected void initViewsAndEvents() {

    }
}
