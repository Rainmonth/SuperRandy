package com.rainmonth.mvp.model;

import com.rainmonth.R;
import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.mvp.contract.XunContract;
import com.rainmonth.mvp.model.bean.XunNavigationBean;
import com.rainmonth.mvp.ui.fragment.XunFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by RandyZhang on 2018/5/31.
 */
@FragmentScope
public class XunModel extends BaseModel implements XunContract.Model {
    @Inject
    public XunModel() {
    }

    @Override
    public List<XunNavigationBean> getNavigationBeanList() {
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
