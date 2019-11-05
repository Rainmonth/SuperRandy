package com.rainmonth.video;

import android.os.Handler;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;

/**
 * 视频模块闪屏页
 *
 * @author 张豪成
 * @date 2019/11/05
 */
public class VideoSplashActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.video_activity_video_splash;
    }

    @Override
    protected void initViewsAndEvents() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            readyGoThenKill(VideoMainActivity.class);
        }, 5000);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }
}
