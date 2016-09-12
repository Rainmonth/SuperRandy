package com.rainmonth.model;

import com.rainmonth.R;
import com.rainmonth.bean.XunNavigationInfo;
import com.rainmonth.fragment.XunFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class XunFragmentModelImpl implements XunFragmentModel {

    @Override
    public List<XunNavigationInfo> getXunNavigationList() {

        List<XunNavigationInfo> xunNavigationInfoList = new ArrayList<XunNavigationInfo>();
        xunNavigationInfoList.add(new XunNavigationInfo(XunFragment.TYPE_VIEW_PAGER, R.drawable.bg_round_purple_rect, "文章"));
        xunNavigationInfoList.add(new XunNavigationInfo(XunFragment.TYPE_LIST, R.drawable.bg_round_purple_rect, "图片"));
        xunNavigationInfoList.add(new XunNavigationInfo(XunFragment.TYPE_VIEW_PAGER, R.drawable.bg_round_purple_rect, "音乐"));
        xunNavigationInfoList.add(new XunNavigationInfo(XunFragment.TYPE_GRID, R.drawable.bg_round_purple_rect, "电影"));
        xunNavigationInfoList.add(new XunNavigationInfo(XunFragment.TYPE_CARD, R.drawable.bg_round_purple_rect, "应用"));
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
