package com.rainmonth.common.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author 张豪成
 * @date 2019-11-19 20:53
 */
public class BaseViewPagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {
    private List<T> mListFragments = null;

    public BaseViewPagerAdapter(FragmentManager fm, List<T> fragments) {
        super(fm);
        mListFragments = fragments;
    }

    @Override
    public T getItem(int position) {
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
