package com.rainmonth.model;

import com.rainmonth.R;
import com.rainmonth.bean.RanContentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RanFragmentModel implements IRanFragmentModel {

    @Override
    public List<RanContentBean> getRanContentList() {
        List<RanContentBean> ranContentBeanList = new ArrayList<RanContentBean>();

        ranContentBeanList.add(new RanContentBean(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 7, "2016年09月12日", "一群逗比的欢乐日子", "大学", 123));
        ranContentBeanList.add(new RanContentBean(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 78, "2016年09月12日", "一群逗比的欢乐日子", "大学", 123));
        ranContentBeanList.add(new RanContentBean(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 107, "两天前", "那些年我们是这样疯的...", "大学", 1000));
        ranContentBeanList.add(new RanContentBean(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 79, "2016年09月11日", "流浪猫的毕业旅行", "大学", 123));
        ranContentBeanList.add(new RanContentBean(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 71, "2016年09月12日", "一群逗比的欢乐日子", "大学", 123));
        ranContentBeanList.add(new RanContentBean(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 35, "2016年09月12日", "一群逗比的欢乐日子", "大学", 123));

        return ranContentBeanList;
    }
}
