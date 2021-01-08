package com.rainmonth.common.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.utils.NetworkUtils;
import com.rainmonth.utils.SafeHandler;

import javax.inject.Inject;

/**
 * Activity 基类
 */
public abstract class BaseActivity<P extends BasePresenter> extends BaseAppCompatActivity
        implements IBaseView, Handler.Callback {

    @Inject
    protected P mPresenter;

    private SafeHandler mHandler = null;

    public SafeHandler getSafeHandler() {
        if (mHandler == null) {
            synchronized (BaseActivity.class) {
                if (mHandler == null) {
                    mHandler = new SafeHandler(Looper.getMainLooper(), this);
                }
            }
        }
        return mHandler;
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent(mAppComponent);
        initViewsAndEvents();
        initToolbar(mStatusBarColor);
    }

    protected void setupActivityComponent(AppComponent appComponent) {

    }

    public void initToolbar(int colorResId) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        this.mPresenter = null;
    }

    @Override
    public void toast(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isChangeStatusBarColor() {
        return true;
    }

    @Override
    protected void onNetworkConnected(NetworkUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected int getOverridePendingTransitionMode() {
        return -1;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
