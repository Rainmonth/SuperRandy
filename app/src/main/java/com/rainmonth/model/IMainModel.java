package com.rainmonth.model;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.ui.widgets.NavigationTabBar;

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
