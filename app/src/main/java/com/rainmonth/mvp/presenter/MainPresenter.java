package com.rainmonth.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.mvp.view.MainView;

/**
 * Created by RandyZhang on 16/6/30.
 */
public class MainPresenter extends BasePresenter<MainView, Object> {

//    private IMainModel mainModel = null;

    public MainPresenter(MainView mainView) {
        super(mainView);
        if (null == mainView) {
            throw new IllegalArgumentException("View should not be null");
        }
//        mainModel = new MainModel();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void initialize() {
//        mView.initializeViews(mainModel.getNavigationModels(), mainModel.getNavigationFragments());
    }
}
