package com.rainmonth.mvp.contract;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.mvp.ui.widgets.NavigationTabBar;

import java.util.List;

/**
 * Created by RandyZhang on 2018/5/24.
 */

public interface MainContract {
    interface View extends IBaseView {
        void initializeViews(List<NavigationTabBar.Model> models, List<BaseLazyFragment> fragments);
    }

    interface Model extends IBaseModel {
        List<NavigationTabBar.Model> getNavigationListModels();

        List<BaseLazyFragment> getNavigationFragments();
    }
}
