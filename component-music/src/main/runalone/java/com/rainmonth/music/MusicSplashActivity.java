package com.rainmonth.music;

import android.view.View;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;

public class MusicSplashActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_activity_splash;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        findViewById(R.id.tv_welcome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(MusicMainActivity.class);
            }
        });
    }

    @Override
    public void initToolbar(int colorResId) {

    }
}
