package com.rainmonth.image.mvp.ui.photo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/***
 * 图片浏览适配器
 */
public class PhotoPagerAdapter extends FragmentStatePagerAdapter {
    private List<PhotoBean> photoBeanList;
    private List<Fragment> fragmentList = new ArrayList<>();

    public PhotoPagerAdapter(FragmentManager fm, List<PhotoBean> photoBeanList) {
        super(fm);
        Iterator localIterator = photoBeanList.iterator();
        while (localIterator.hasNext()) {
            PhotoBean photoBean = (PhotoBean) localIterator.next();
            this.fragmentList.add(PhotoFragment.getInstance(photoBean));
        }
        this.photoBeanList = photoBeanList;
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

    public List<PhotoBean> getPhotoBeanList() {
        return photoBeanList;
    }

    public void setPhotoBeanList(List<PhotoBean> photoBeanList) {
        this.photoBeanList = photoBeanList;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }
}
