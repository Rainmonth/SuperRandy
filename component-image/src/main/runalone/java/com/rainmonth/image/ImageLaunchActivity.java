package com.rainmonth.image;

import android.os.Handler;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.mvp.ui.common.ImageMainActivity;

public class ImageLaunchActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return -1;
    }

    @Override
    protected void initViewsAndEvents() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                readyGoThenKill(ImageMainActivity.class);
            }
        }, 2000);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }
}
