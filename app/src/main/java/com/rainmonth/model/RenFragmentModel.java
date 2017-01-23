package com.rainmonth.model;

import com.google.gson.Gson;
import com.rainmonth.R;
import com.rainmonth.bean.BannerBean;
import com.rainmonth.bean.RenContentInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RenFragmentModel implements IRenFragmentModel {
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

    @Override
    public List<BannerBean> getHomeBanner() {
        List<BannerBean> bannerInfoList = new ArrayList<BannerBean>();
        String jsonStr = "{\"id\":\"10001\",\"type\":\"1\",\"title\":\"活动主页一\",\"url\":\"https://www.baidu.com\",\"banner_thumb_url\":\"http://pic2.cxtuku.com/00/02/31/b945758fd74d.jpg\"}";
        Gson gson = new Gson();
        for (int i = 0; i < 5; i++) {
            BannerBean bannerInfo = gson.fromJson(jsonStr, BannerBean.class);
            bannerInfoList.add(bannerInfo);
        }

        return bannerInfoList;
    }
}
