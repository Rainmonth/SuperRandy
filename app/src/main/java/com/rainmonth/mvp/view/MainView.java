package com.rainmonth.mvp.view;

import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.ui.widgets.NavigationTabBar;

import java.util.List;

/**
 * Created by RandyZhang on 16/6/30.
 */
public interface MainView extends IBaseView {
    void initializeViews(List<NavigationTabBar.Model> models, List<BaseLazyFragment> fragments);
}
