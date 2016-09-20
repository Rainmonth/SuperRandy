package com.rainmonth.view;

import com.rainmonth.base.ui.fragment.BaseLazyFragment;
import com.rainmonth.base.mvp.BaseView;
import com.rainmonth.widgets.NavigationTabBar;

import java.util.List;

/**
 * Created by RandyZhang on 16/6/30.
 */
public interface MainView extends BaseView{
    void initializeViews(List<NavigationTabBar.Model> models, List<BaseLazyFragment> fragments);
}
