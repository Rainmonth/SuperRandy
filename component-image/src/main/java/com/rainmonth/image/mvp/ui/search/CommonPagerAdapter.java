package com.rainmonth.image.mvp.ui.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.ui.photo.PhotoFragment;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/***
 * 图片浏览适配器
 */
public class CommonPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> titleList = new ArrayList<>();
    private FragmentManager fragmentManager;

    public CommonPagerAdapter(FragmentManager fm, List<Fragment> list) {
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