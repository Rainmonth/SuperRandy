package com.rainmonth.view;

import com.rainmonth.fragment.BaseLazyFragment;
import com.rainmonth.widgets.NavigationTabBar;

import java.util.List;

/**
 * Created by RandyZhang on 16/6/30.
 */
public interface MainView {
    void initializeViews(List<NavigationTabBar.Model> models, List<BaseLazyFragment> fragments);
}
