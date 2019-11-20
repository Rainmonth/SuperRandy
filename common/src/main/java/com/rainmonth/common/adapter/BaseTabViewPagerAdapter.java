package com.rainmonth.common.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/***
 * 通用Fragment适配器
 */
public class BaseTabViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> titleList = new ArrayList<>();
    private FragmentManager fragmentManager;

    public BaseTabViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        fragmentManager = fm;
        this.fragmentList = list;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragmentList != null && fragmentList.size() > 0) {
            return fragmentList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        if (fragmentList != null && fragmentList.size() > 0) {
            return fragmentList.size();
        } else {
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleList.size() > 0 && position < titleList.size()) {
            return titleList.get(position);
        } else {
            return super.getPageTitle(position);
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }
}
