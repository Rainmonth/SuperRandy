package com.rainmonth.image.mvp.ui.photo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/***
 * 图片浏览适配器
 */
public class PhotoPagerAdapter extends FragmentStatePagerAdapter {
    private List<PhotoBean> photoBeanList;
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager fragmentManager;

    public PhotoPagerAdapter(FragmentManager fm, List<PhotoBean> photoBeanList) {
        super(fm);
        fragmentManager = fm;
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

    public void setPhotoList(List<PhotoBean> photoBeans) {
        KLog.d("Image", "adapter add counts:" + photoBeans.size());
        photoBeanList.clear();
        fragmentList.clear();
        KLog.d("Image", "adapter photoBeanList counts after clear:" + photoBeanList.size());
        this.photoBeanList = photoBeans;
        KLog.d("Image", "adapter photoBeanList counts:" + photoBeanList.size());
        for (PhotoBean photoBean : photoBeanList)
            this.fragmentList.add(PhotoFragment.getInstance(photoBean));
        KLog.d("Image", "adapter fragment counts:" + fragmentList.size());
        KLog.d("Image", "adapter photo counts:" + photoBeanList.size());
        notifyDataSetChanged();
        Iterator fragIterator = fragmentList.iterator();
        while (fragIterator.hasNext()) {
            KLog.d("Image", "Fragment mem address:" + fragIterator.next());
        }
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }
}
