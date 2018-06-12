package com.rainmonth.app.mvp.ui;

import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rainmonth.app.R;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.router.RouterConstant;

@Route(path= RouterConstant.PATH_APP_HOME)
public class CardExploreActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_activity_card_explore;
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewsAndEvents() {
        CardViewPagerFragment fragment = CardViewPagerFragment.getInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void initToolbar() {
        mActionBar.setTitle("最美应用");
        mActionBar.setLogo(R.drawable.ic_action_bar_logo);
    }
}
