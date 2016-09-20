package com.rainmonth.view;

import com.rainmonth.fragment.BaseLazyFragment;
import com.rainmonth.utils.http.BaseView;
import com.rainmonth.widgets.NavigationTabBar;

import java.util.List;

/**
 * Created by RandyZhang on 16/6/30.
 */
public interface MainView extends BaseView{
    void initializeViews(List<NavigationTabBar.Model> models, List<BaseLazyFragment> fragments);
}
