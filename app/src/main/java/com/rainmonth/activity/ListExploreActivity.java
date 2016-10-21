package com.rainmonth.activity;

import android.os.Bundle;

import com.rainmonth.R;
import com.rainmonth.base.ui.activity.BaseActivity;

/**
 * 文章
 */
public class ListExploreActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean isApplyTranslucentStatusBar() {
        return false;
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_list_explore;
    }

    @Override
    public void initViewsAndEvent() {
//        CardViewPagerFragment fragment = CardViewPagerFragment.getInstance();
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.frameLayout, fragment);
//        transaction.commit();
    }

    @Override
    public void initToolbar() {
        if (null != mToolbar) {
            mToolbar.setLogo(R.mipmap.ic_launcher);
            mToolbar.setTitle("列表新闻浏览");
            mToolbar.setBackgroundResource(R.color.bg_home);
        }
    }
}
