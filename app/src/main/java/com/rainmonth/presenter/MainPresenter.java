package com.rainmonth.presenter;

import android.content.Context;

import com.rainmonth.model.MainModel;
import com.rainmonth.model.MainModelImpl;
import com.rainmonth.view.MainView;

/**
 * Created by RandyZhang on 16/6/30.
 */
public class MainPresenter implements BasePresenter{

    private Context context = null;
    private MainView mainView = null;
    private MainModel mainModel = null;

    public MainPresenter(Context context, MainView mainView) {
        if (null == mainView) {
            throw new IllegalArgumentException("View should not be null");
        }
        this.context = context;
        this.mainView = mainView;
        mainModel = new MainModelImpl();
    }

    @Override
    public void initialize() {
        mainView.initializeViews(mainModel.getNavigationModels(context), mainModel.getNavigationFragments());
    }
}
