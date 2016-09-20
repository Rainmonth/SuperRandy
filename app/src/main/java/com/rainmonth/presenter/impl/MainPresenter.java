package com.rainmonth.presenter.impl;

import android.content.Context;

import com.rainmonth.model.MainModel;
import com.rainmonth.model.impl.MainModelImpl;
import com.rainmonth.base.mvp.BasePresenterImpl;
import com.rainmonth.view.MainView;

/**
 * Created by RandyZhang on 16/6/30.
 */
public class MainPresenter extends BasePresenterImpl<MainView, Object> {

    private Context context = null;
    private MainModel mainModel = null;

    public MainPresenter(MainView mainView, Context context) {
        super(mainView);
        if (null == mainView) {
            throw new IllegalArgumentException("View should not be null");
        }
        this.context = context;
        mainModel = new MainModelImpl();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void initialize() {
        mView.initializeViews(mainModel.getNavigationModels(context), mainModel.getNavigationFragments());
    }
}
