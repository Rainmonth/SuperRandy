package com.rainmonth.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.socks.library.KLog;

import javax.inject.Inject;

/**
 * 懒加载Fragment实现（目前有问题）
 *
 * @param <T>
 */
public abstract class BaseLazyFragment<T extends BasePresenter> extends BaseSupportFragment {
    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isPrepared;

    private boolean isFirstVisibleCalled = false;

    @Inject
    protected T mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        KLog.i("Randy", TAG);
        setupFragmentComponent(mAppComponent);
        initViewsAndEvents(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        KLog.i("Randy", TAG);
        initPrepare();
    }

    @Override
    public void onResume() {
        super.onResume();
        KLog.i("Randy", TAG);
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        KLog.i("Randy", TAG);
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        KLog.i("Randy", TAG + "->" + isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    private synchronized void initPrepare() {
        if (isPrepared && !isFirstVisibleCalled) {
            onFirstUserVisible();
            isFirstVisibleCalled = true;
        } else {
            isPrepared = true;
        }
    }

    /**
     * when fragment is visible for the first time, here we can do some initialized
     * work or refresh data only once
     */
    protected abstract void onFirstUserVisible();

    /**
     * this method like the fragment's lifecycle method onResume()
     */
    protected abstract void onUserVisible();

    /**
     * when fragment is invisible for the first time
     */
    private void onFirstUserInvisible() {
        // here we do not recommend do something
    }

    /**
     * this method like the fragment's lifecycle method onPause()
     */
    protected abstract void onUserInvisible();

    protected abstract void setupFragmentComponent(AppComponent appComponent);

    /**
     * init all views and add events
     */
    protected abstract void initViewsAndEvents(View view);

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}
