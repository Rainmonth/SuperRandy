package com.rainmonth.model.impl;

import com.rainmonth.R;
import com.rainmonth.bean.XunNavigationInfo;
import com.rainmonth.fragment.XunFragment;
import com.rainmonth.model.XunFragmentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class XunFragmentModelImpl implements XunFragmentModel {

    @Override
    public List<XunNavigationInfo> getXunNavigationList() {

        List<XunNavigationInfo> xunNavigationInfoList = new ArrayList<XunNavigationInfo>();
        xunNavigationInfoList.add(new XunNavigationInfo(XunFragment.TYPE_ARTICLE, R.drawable.bg_round_purple_rect, "文章"));
        xunNavigationInfoList.add(new XunNavigationInfo(XunFragment.TYPE_IMAGE, R.drawable.bg_round_purple_rect, "图片"));
        xunNavigationInfoList.add(new XunNavigationInfo(XunFragment.TYPE_MUSIC, R.drawable.bg_round_purple_rect, "音乐"));
        xunNavigationInfoList.add(new XunNavigationInfo(XunFragment.TYPE_FILM, R.drawable.bg_round_purple_rect, "电影"));
        xunNavigationInfoList.add(new XunNavigationInfo(XunFragment.TYPE_APP, R.drawable.bg_round_purple_rect, "应用"));
//        xunNavigationInfoList.add(new XunNavigationInfo(R.drawable.bg_round_purple_rect, "文章"));
//        xunNavigationInfoList.add(new XunNavigationInfo(R.drawable.bg_round_purple_rect, "图片"));
//        xunNavigationInfoList.add(new XunNavigationInfo(R.drawable.bg_round_purple_rect, "音乐"));
//        xunNavigationInfoList.add(new XunNavigationInfo(R.drawable.bg_round_purple_rect, "电影"));
//        xunNavigationInfoList.add(new XunNavigationInfo(R.drawable.bg_round_purple_rect, "应用"));
//        xunNavigationInfoList.add(new XunNavigationInfo(R.drawable.bg_round_purple_rect, "文章"));
//        xunNavigationInfoList.add(new XunNavigationInfo(R.drawable.bg_round_purple_rect, "图片"));
//        xunNavigationInfoList.add(new XunNavigationInfo(R.drawable.bg_round_purple_rect, "音乐"));
//        xunNavigationInfoList.add(new XunNavigationInfo(R.drawable.bg_round_purple_rect, "电影"));
//        xunNavigationInfoList.add(new XunNavigationInfo(R.drawable.bg_round_purple_rect, "应用"));
        return xunNavigationInfoList;
    }
}
