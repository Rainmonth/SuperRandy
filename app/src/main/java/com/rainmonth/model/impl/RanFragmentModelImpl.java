package com.rainmonth.model.impl;

import com.rainmonth.R;
import com.rainmonth.bean.RanContentInfo;
import com.rainmonth.model.RanFragmentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RanFragmentModelImpl implements RanFragmentModel {

    @Override
    public List<RanContentInfo> getRanContentList() {
        List<RanContentInfo> ranContentInfoList = new ArrayList<RanContentInfo>();

        ranContentInfoList.add(new RanContentInfo(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 7, "2016年09月12日", "一群逗比的欢乐日子", "大学", 123));
        ranContentInfoList.add(new RanContentInfo(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 78, "2016年09月12日", "一群逗比的欢乐日子", "大学", 123));
        ranContentInfoList.add(new RanContentInfo(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 107, "两天前", "那些年我们是这样疯的...", "大学", 1000));
        ranContentInfoList.add(new RanContentInfo(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 79, "2016年09月11日", "流浪猫的毕业旅行", "大学", 123));
        ranContentInfoList.add(new RanContentInfo(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 71, "2016年09月12日", "一群逗比的欢乐日子", "大学", 123));
        ranContentInfoList.add(new RanContentInfo(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 35, "2016年09月12日", "一群逗比的欢乐日子", "大学", 123));

        return ranContentInfoList;
    }
}
