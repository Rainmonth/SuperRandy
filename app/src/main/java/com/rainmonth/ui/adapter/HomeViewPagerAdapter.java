package com.rainmonth.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rainmonth.library.base.BaseLazyFragment;

import java.util.List;

/**
 * Created by RandyZhang on 16/6/30.
 */
public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    private List<BaseLazyFragment> mListFragments = null;

    public HomeViewPagerAdapter(FragmentManager fm, List<BaseLazyFragment> fragments) {
        super(fm);
        mListFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (mListFragments != null && position > -1 && position < mListFragments.size()) {
            return mListFragments.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return null != mListFragments ? mListFragments.size() : 0;
    }
}
