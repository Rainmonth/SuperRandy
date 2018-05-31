package com.rainmonth.app.mvp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rainmonth.app.R;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.common.utils.NetworkUtils;
import com.rainmonth.router.RouterConstant;

@Route(path= RouterConstant.PATH_APP_HOME)
public class CardExploreActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_activity_card_explore;
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
        CardViewPagerFragment fragment = CardViewPagerFragment.getInstance();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout, fragment);
        transaction.commit();
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
        mToolbar.setTitle("最美应用");
        mToolbar.setLogo(R.drawable.ic_action_bar_logo);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }
}
