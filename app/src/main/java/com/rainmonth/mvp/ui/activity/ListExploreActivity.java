package com.rainmonth.mvp.ui.activity;

import android.os.Bundle;

import com.rainmonth.R;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;

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
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    public void initToolbar() {
        if (null != mActionBar) {
            mActionBar.setLogo(R.drawable.ic_action_bar_logo);
            mActionBar.setTitle("列表新闻浏览");
//            mActionBar.setBackgroundResource(R.color.bg_home);
        }
    }
}
