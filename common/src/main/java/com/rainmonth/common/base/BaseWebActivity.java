package com.rainmonth.common.base;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.rainmonth.common.R;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.utils.CommonUtils;
import com.rainmonth.common.utils.log.LogUtils;
import com.rainmonth.common.widgets.BrowserLayout;

public class BaseWebActivity extends BaseSwipeBackCompatActivity {

    public static final String BUNDLE_KEY_URL             = "BUNDLE_KEY_URL";
    public static final String BUNDLE_KEY_TITLE           = "BUNDLE_KEY_TITLE";
    public static final String BUNDLE_KEY_SHOW_BOTTOM_BAR = "BUNDLE_KEY_SHOW_BOTTOM_BAR";

    private String  mWebUrl         = null;
    private String  mWebTitle       = null;
    private boolean isShowBottomBar = true;

    private Toolbar       mToolBar       = null;
    private BrowserLayout mBrowserLayout = null;

    @Override
    protected void getBundleExtras(Bundle extras) {
        mWebTitle = extras.getString(BUNDLE_KEY_TITLE);
        mWebUrl = extras.getString(BUNDLE_KEY_URL);
        isShowBottomBar = extras.getBoolean(BUNDLE_KEY_SHOW_BOTTOM_BAR);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_common_web;
    }

    @Override
    protected void initViewsAndEvents() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mBrowserLayout = (BrowserLayout) findViewById(R.id.common_web_browser_layout);
        LogUtils.i("Randy", "title:" + mWebTitle);
        LogUtils.i("Randy", "url:" + mWebTitle);

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
    public void initToolbar(int colorResId) {
        if (null != mToolBar) {
            setSupportActionBar(mToolBar);
            mToolBar.setBackgroundColor(getResources().getColor(colorResId));
            mActionBar = getSupportActionBar();
            if (null != mActionBar) {
                if (!CommonUtils.isEmpty(mWebTitle)) {
                    mToolBar.setTitle(mWebTitle);
                }
                mActionBar.setHomeButtonEnabled(true);
                mActionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

    }
}
