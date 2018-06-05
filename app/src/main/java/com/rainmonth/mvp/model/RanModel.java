package com.rainmonth.mvp.model;

import com.rainmonth.R;
import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.mvp.contract.RanContract;
import com.rainmonth.mvp.model.bean.RanContentBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * RanFragment 数据获取
 * Created by RandyZhang on 2018/5/31.
 */
@FragmentScope
public class RanModel extends BaseModel implements RanContract.Model {
    @Inject
    public RanModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public List<RanContentBean> getRanContentList() {
        List<RanContentBean> ranContentBeanList = new ArrayList<>();

        ranContentBeanList.add(new RanContentBean(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 7, "2016年09月12日", "一群逗比的欢乐日子", "大学", 123));
        ranContentBeanList.add(new RanContentBean(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 78, "2016年09月12日", "一群逗比的欢乐日子", "大学", 123));
        ranContentBeanList.add(new RanContentBean(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 107, "两天前", "那些年我们是这样疯的...", "大学", 1000));
        ranContentBeanList.add(new RanContentBean(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 79, "2016年09月11日", "流浪猫的毕业旅行", "大学", 123));
        ranContentBeanList.add(new RanContentBean(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 71, "2016年09月12日", "一群逗比的欢乐日子", "大学", 123));
        ranContentBeanList.add(new RanContentBean(1, 1001, "RandyZhang", R.drawable.ren_bg_game, 35, "2016年09月12日", "一群逗比的欢乐日子", "大学", 123));

        return ranContentBeanList;
    }

}
