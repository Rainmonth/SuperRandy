package com.rainmonth.presenter.impl;

import com.rainmonth.base.mvp.BasePresenterImpl;
import com.rainmonth.model.MainModel;
import com.rainmonth.model.impl.MainModelImpl;
import com.rainmonth.view.MainView;

/**
 * Created by RandyZhang on 16/6/30.
 */
public class MainPresenter extends BasePresenterImpl<MainView, Object> {

    private MainModel mainModel = null;

    public MainPresenter(MainView mainView) {
        super(mainView);
        if (null == mainView) {
            throw new IllegalArgumentException("View should not be null");
        }
        mainModel = new MainModelImpl();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void initialize() {
        mView.initializeViews(mainModel.getNavigationModels(), mainModel.getNavigationFragments());
    }
}
