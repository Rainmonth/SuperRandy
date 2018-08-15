package com.rainmonth.image.mvp.model;

import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.image.api.USearchApi;
import com.rainmonth.image.mvp.contract.SearchContract;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.model.bean.UserBean;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author: Randy Zhang
 * @description: SearchModel
 * @created: 2018/8/15
 **/
@ActivityScope
public class SearchModel extends BaseModel implements SearchContract.Model {
    @Inject
    public SearchModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<SearchResult<PhotoBean>> searchPhotos(String keys,
                                                            int page,
                                                            int perPage,
                                                            String collectionIds,
                                                            String orientation) {
        return mRepositoryManager.obtainRetrofitService(USearchApi.class)
                .searchPhotos(keys, page, perPage, collectionIds, orientation);
    }

    @Override
    public Observable<SearchResult<CollectionBean>> searchCollections(String keys,
                                                                      int page,
                                                                      int perPage) {
        return mRepositoryManager.obtainRetrofitService(USearchApi.class)
                .searchCollections(keys, page, perPage);
    }

    @Override
    public Observable<SearchResult<UserBean>> searchUsers(String keys, int page, int perPage) {
        return mRepositoryManager.obtainRetrofitService(USearchApi.class)
                .searchUser(keys, page, perPage);
    }
}