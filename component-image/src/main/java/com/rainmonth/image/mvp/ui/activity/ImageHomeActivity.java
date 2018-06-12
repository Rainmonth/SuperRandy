package com.rainmonth.image.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.di.component.DaggerImageHomeComponent;
import com.rainmonth.image.di.module.ImageHomeModule;
import com.rainmonth.image.mvp.contract.ImageHomeContract;
import com.rainmonth.image.mvp.presenter.ImageHomePresenter;



public class ImageHomeActivity extends BaseActivity<ImageHomePresenter> implements ImageHomeContract.View {

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerImageHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .imageHomeModule(new ImageHomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_main;
    }

    @Override
    protected void initViewsAndEvents() {

    }
}
