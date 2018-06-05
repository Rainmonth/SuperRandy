package com.rainmonth.read.mvp.model;

import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.read.mvp.contract.ReadHomeContract;

import javax.inject.Inject;


@ActivityScope
public class ReadHomeModel extends BaseModel implements ReadHomeContract.Model {

    @Inject
    public ReadHomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);

    }
}