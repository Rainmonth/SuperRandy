package com.rainmonth.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.rainmonth.R;
import com.rainmonth.base.ui.activity.BaseActivity;
import com.rainmonth.library.eventbus.EventCenter;
import com.rainmonth.library.utils.NetworkUtils;

/**
 * 文章
 */
public class ListExploreActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_list_explore;
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
    public void initToolbar() {
        if (null != mToolbar) {
            mToolbar.setLogo(R.drawable.ic_action_bar_logo);
            mToolbar.setTitle("列表新闻浏览");
            mToolbar.setBackgroundResource(R.color.bg_home);
        }
    }
}
