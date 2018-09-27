package com.rainmonth.app;

import android.os.Bundle;
import android.view.View;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.router.RouterConstant;
import com.rainmonth.router.RouterUtils;

public class AppMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        findViewById(R.id.app_btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterUtils.getInstance().build(RouterConstant.PATH_APP_HOME).navigation();
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
