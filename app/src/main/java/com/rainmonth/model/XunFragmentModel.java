package com.rainmonth.model;

import com.rainmonth.R;
import com.rainmonth.bean.XunNavigationBean;
import com.rainmonth.ui.fragment.XunFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class XunFragmentModel implements IXunFragmentModel {

    @Override
    public List<XunNavigationBean> getXunNavigationList() {

        List<XunNavigationBean> xunNavigationBeanList = new ArrayList<XunNavigationBean>();
        xunNavigationBeanList.add(new XunNavigationBean(XunFragment.TYPE_ARTICLE, R.drawable.bg_round_purple_rect, "文章"));
        xunNavigationBeanList.add(new XunNavigationBean(XunFragment.TYPE_IMAGE, R.drawable.bg_round_purple_rect, "图片"));
        xunNavigationBeanList.add(new XunNavigationBean(XunFragment.TYPE_MUSIC, R.drawable.bg_round_purple_rect, "音乐"));
        xunNavigationBeanList.add(new XunNavigationBean(XunFragment.TYPE_FILM, R.drawable.bg_round_purple_rect, "电影"));
        xunNavigationBeanList.add(new XunNavigationBean(XunFragment.TYPE_APP, R.drawable.bg_round_purple_rect, "应用"));
//        xunNavigationBeanList.add(new XunNavigationBean(R.drawable.bg_round_purple_rect, "文章"));
//        xunNavigationBeanList.add(new XunNavigationBean(R.drawable.bg_round_purple_rect, "图片"));
//        xunNavigationBeanList.add(new XunNavigationBean(R.drawable.bg_round_purple_rect, "音乐"));
//        xunNavigationBeanList.add(new XunNavigationBean(R.drawable.bg_round_purple_rect, "电影"));
//        xunNavigationBeanList.add(new XunNavigationBean(R.drawable.bg_round_purple_rect, "应用"));
//        xunNavigationBeanList.add(new XunNavigationBean(R.drawable.bg_round_purple_rect, "文章"));
//        xunNavigationBeanList.add(new XunNavigationBean(R.drawable.bg_round_purple_rect, "图片"));
//        xunNavigationBeanList.add(new XunNavigationBean(R.drawable.bg_round_purple_rect, "音乐"));
//        xunNavigationBeanList.add(new XunNavigationBean(R.drawable.bg_round_purple_rect, "电影"));
//        xunNavigationBeanList.add(new XunNavigationBean(R.drawable.bg_round_purple_rect, "应用"));
        return xunNavigationBeanList;
    }
}
