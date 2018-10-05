package com.rainmonth.image.mvp.model;

import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.image.api.UCollectionApi;
import com.rainmonth.image.mvp.contract.CollectionDetailContract;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class CollectionDetailModel extends BaseModel implements CollectionDetailContract.Model {
    @Inject
    public CollectionDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<List<PhotoBean>> getCollectionPhotos(long collectionId,
                                                           int page,
                                                           int perPage) {
        return mRepositoryManager.obtainRetrofitService(UCollectionApi.class)
                .getCollectionPhotos(collectionId, page, perPage);
    }

    @Override
    public Observable<List<CollectionBean>> getRelatedCollections(long collectionId) {
        return mRepositoryManager.obtainRetrofitService(UCollectionApi.class)
                .getRelatedCollections(collectionId);
    }
}
