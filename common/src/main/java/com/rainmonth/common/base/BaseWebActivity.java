package com.rainmonth.common.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rainmonth.common.R;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.common.utils.CommonUtils;
import com.rainmonth.common.utils.NetworkUtils;
import com.rainmonth.common.widgets.BrowserLayout;

import butterknife.ButterKnife;

public class BaseWebActivity extends BaseSwipeBackCompatActivity {

    public static final String BUNDLE_KEY_URL = "BUNDLE_KEY_URL";
    public static final String BUNDLE_KEY_TITLE = "BUNDLE_KEY_TITLE";
    public static final String BUNDLE_KEY_SHOW_BOTTOM_BAR = "BUNDLE_KEY_SHOW_BOTTOM_BAR";

    private String mWebUrl = null;
    private String mWebTitle = null;
    private boolean isShowBottomBar = true;

    private Toolbar mToolBar = null;
    private BrowserLayout mBrowserLayout = null;

    @Override
    protected void getBundleExtras(Bundle extras) {
        mWebTitle = extras.getString(BUNDLE_KEY_TITLE);
        mWebUrl = extras.getString(BUNDLE_KEY_URL);
        isShowBottomBar = extras.getBoolean(BUNDLE_KEY_SHOW_BOTTOM_BAR);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_common_web;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        setSystemBarTintDrawable(getResources().getDrawable(R.drawable.sr_primary));

        mToolBar = ButterKnife.findById(this, R.id.common_toolbar);
        mBrowserLayout = ButterKnife.findById(this, R.id.common_web_browser_layout);

        if (null != mToolBar) {
            setSupportActionBar(mToolBar);
            mActionBar = getSupportActionBar();
            if (null != mActionBar) {
                mActionBar.setHomeButtonEnabled(true);
                mActionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        if (!CommonUtils.isEmpty(mWebTitle)) {
            setTitle(mWebTitle);
        } else {
            setTitle("网页");
        }

        if (!CommonUtils.isEmpty(mWebUrl)) {
            mBrowserLayout.loadUrl(mWebUrl);
        } else {
            showToast("获取URL地址失败");
        }

        if (!isShowBottomBar) {
            mBrowserLayout.hideBrowserController();
        } else {
            mBrowserLayout.showBrowserController();
        }
    }

    @Override
    protected void onNetworkConnected(NetworkUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isChangeStatusBarColor() {
        return true;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }
}
