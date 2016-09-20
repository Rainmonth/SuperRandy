package com.rainmonth.model;

import android.content.Context;

import com.rainmonth.base.ui.fragment.BaseLazyFragment;
import com.rainmonth.widgets.NavigationTabBar;

import java.util.List;

/**
 * Created by RandyZhang on 16/6/30.
 */
public interface MainModel {
    List<BaseLazyFragment> getNavigationFragments();

    /**
     * 获取底部导航栏models
     *
     * @param context context
     * @return models列表
     */
    List<NavigationTabBar.Model> getNavigationModels(Context context);
}
