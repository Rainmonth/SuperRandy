package com.rainmonth.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.rainmonth.R;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.common.utils.NetworkUtils;

/**
 * 文章
 */
public class ListExploreActivity extends BaseActivity {

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_list_explore;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    public void initToolbar() {
        if (null != mToolbar) {
            mToolbar.setLogo(R.drawable.ic_action_bar_logo);
            mToolbar.setTitle("列表新闻浏览");
            mToolbar.setBackgroundResource(R.color.bg_home);
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }
}
