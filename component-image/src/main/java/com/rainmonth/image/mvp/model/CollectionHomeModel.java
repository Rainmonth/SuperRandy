package com.rainmonth.image.mvp.model;

import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.image.api.UCollectionApi;
import com.rainmonth.image.api.UPhotoApi;
import com.rainmonth.image.mvp.contract.CollectionHomeContract;
import com.rainmonth.image.mvp.contract.PhotoHomeContract;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class CollectionHomeModel extends BaseModel implements CollectionHomeContract.Model {
    @Inject
    public CollectionHomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<List<CollectionBean>> getCollections(int page, int perPage) {
        return mRepositoryManager.obtainRetrofitService(UCollectionApi.class)
                .getCollections(page, perPage);
    }
}