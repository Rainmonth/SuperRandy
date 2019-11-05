package com.rainmonth.video;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;

/**
 * 全局悬浮窗播放
 *
 * @author 张豪成
 * @date 2019-11-01 15:13
 */
public class GlobalVideoPlayerActivity extends BaseActivity {
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.video_activity_global_video_play;
    }

    @Override
    protected void initViewsAndEvents() {

    }
}
