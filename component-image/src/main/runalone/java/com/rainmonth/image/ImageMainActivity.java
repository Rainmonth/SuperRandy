package com.rainmonth.image;

import android.view.View;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.mvp.ui.activity.ImageHomeActivity;

public class ImageMainActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        findViewById(R.id.tv_welcom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ImageHomeActivity.class);
            }
        });
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }
}
