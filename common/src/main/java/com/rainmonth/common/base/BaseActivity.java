package com.rainmonth.common.base;

import android.os.Bundle;

import com.rainmonth.common.base.mvp.IBaseView;

/**
 * Activity 基类
 */
public abstract class BaseActivity extends BaseAppCompatActivity implements IBaseView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public abstract void initToolbar();

    @Override
    public void toast(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
