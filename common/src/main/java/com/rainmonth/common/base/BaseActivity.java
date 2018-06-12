package com.rainmonth.common.base;

import android.os.Bundle;
import android.view.View;

import com.rainmonth.common.R;
import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.common.utils.NetworkUtils;
import com.rainmonth.common.utils.SmartBarUtils;

import javax.inject.Inject;

/**
 * Activity 基类
 */
public abstract class BaseActivity<P extends BasePresenter> extends BaseAppCompatActivity
        implements IBaseView {

    @Inject
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent(mAppComponent);
        initViewsAndEvents();
        initToolbar(R.color.theme_color);
        SmartBarUtils.setStatusBarColor(this, R.color.theme_color);
    }

    protected abstract void setupActivityComponent(AppComponent appComponent);

    public abstract void initToolbar(int colorResId);

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
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
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
