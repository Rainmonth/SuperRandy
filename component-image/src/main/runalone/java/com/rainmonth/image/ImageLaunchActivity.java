package com.rainmonth.image;

import android.os.Handler;
import android.os.Bundle;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.mvp.ui.activity.ImageMainActivity;

public class ImageLaunchActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_image_launch;
    }

    @Override
    protected void initViewsAndEvents() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                readyGo(ImageMainActivity.class);
            }
        }, 5000);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }
}
