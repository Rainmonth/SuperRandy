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

    private Handler mHandler;

    @Override
    protected int getContentViewLayoutID() {
        return -1;
    }

    @Override
    protected void initViewsAndEvents() {
        mHandler = new Handler();
        mHandler.postDelayed(() -> {
            readyGoThenKill(VideoMainActivity.class);
        }, 5000);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }
}
