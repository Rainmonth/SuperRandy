package com.rainmonth.read.mvp.ui.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.read.di.component.DaggerReadHomeComponent;
import com.rainmonth.read.di.module.ReadHomeModule;
import com.rainmonth.read.mvp.contract.ReadHomeContract;
import com.rainmonth.read.mvp.presenter.ReadHomePresenter;

import com.rainmonth.read.R;


public class ReadHomeActivity extends BaseActivity<ReadHomePresenter> implements ReadHomeContract.View {

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerReadHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .readHomeModule(new ReadHomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.read_activity_home;
    }

    @Override
    protected void initViewsAndEvents() {

    }
}
