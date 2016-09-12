package com.rainmonth.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.rainmonth.R;
import com.rainmonth.fragment.CardViewPagerFragment;

/**
 * 文章
 */
public class GridExploreActivity extends BaseActivity {

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
        return R.layout.activity_grid_explore;
    }

    @Override
    public void initViewsAndEvent() {
//        CardViewPagerFragment fragment = CardViewPagerFragment.getInstance();
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.frameLayout, fragment);
//        transaction.commit();
    }
}
