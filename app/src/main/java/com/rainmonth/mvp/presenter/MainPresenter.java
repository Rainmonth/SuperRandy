package com.rainmonth.mvp.presenter;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.mvp.contract.MainContract;
import com.rainmonth.mvp.ui.widgets.NavigationTabBar;

import java.util.List;

import javax.inject.Inject;

/**
 * 主界面Presenter
 * Created by RandyZhang on 16/6/30.
 */
@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View view) {
        super(model, view);
    }

    public void init(List<NavigationTabBar.Model> models, List<BaseLazyFragment> fragments) {
        mView.initializeViews(models, fragments);
    }
}
