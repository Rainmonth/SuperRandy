package com.rainmonth.model;

import com.rainmonth.library.base.BaseLazyFragment;
import com.rainmonth.widgets.NavigationTabBar;

import java.util.List;

/**
 * Created by RandyZhang on 16/6/30.
 */
public interface IMainModel {
    List<BaseLazyFragment> getNavigationFragments();

    /**
     * 获取底部导航栏models
     *
     * @return models列表
     */
    List<NavigationTabBar.Model> getNavigationModels();
}
