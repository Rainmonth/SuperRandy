package com.rainmonth.model.impl;

import com.rainmonth.R;
import com.rainmonth.bean.RenContentInfo;
import com.rainmonth.model.RenFragmentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RenFragmentModelImpl implements RenFragmentModel {
    @Override
    public List<RenContentInfo> getRenContentList() {
        List<RenContentInfo> renContentInfoList = new ArrayList<RenContentInfo>();

        renContentInfoList.add(new RenContentInfo(1, R.drawable.ren_bg_walk, "1", "旅行", "行走的力量"));
        renContentInfoList.add(new RenContentInfo(2, R.drawable.ren_bg_sing, "2", "音乐", "音乐的力量"));
        renContentInfoList.add(new RenContentInfo(3, R.drawable.ren_bg_sport, "3", "运动", "运动的力量"));
        renContentInfoList.add(new RenContentInfo(4, R.drawable.ren_bg_read, "4", "阅读", "阅读的力量"));
        renContentInfoList.add(new RenContentInfo(5, R.drawable.ren_bg_stay, "5", "坚持", "坚持的力量"));
        renContentInfoList.add(new RenContentInfo(6, R.drawable.ren_bg_game, "6", "游戏", "游戏的力量"));
        renContentInfoList.add(new RenContentInfo(7, R.drawable.ren_bg_share, "7", "分享", "分享的力量"));

        return renContentInfoList;
    }
}
