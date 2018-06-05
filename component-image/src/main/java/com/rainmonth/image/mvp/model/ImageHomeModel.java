package com.rainmonth.image.mvp.model;

import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.image.mvp.contract.ImageHomeContract;

import javax.inject.Inject;


@ActivityScope
public class ImageHomeModel extends BaseModel implements ImageHomeContract.Model {
    @Inject
    public ImageHomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

}