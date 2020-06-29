package com.rainmonth.mvp.model;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.mvp.contract.MainContract;
import com.rainmonth.mvp.ui.widgets.NavigationTabBar;

import java.util.List;

import javax.inject.Inject;

/**
 *
 * Created by RandyZhang on 2018/5/30.
 */
@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {
    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public List<NavigationTabBar.Model> getNavigationListModels() {
        return null;
    }

    @Override
    public List<BaseLazyFragment> getNavigationFragments() {
        return null;
    }
}
