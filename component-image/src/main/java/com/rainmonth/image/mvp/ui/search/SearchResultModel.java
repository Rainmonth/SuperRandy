package com.rainmonth.image.mvp.ui.search;

import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.image.api.USearchApi;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.model.bean.UserBean;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class SearchResultModel extends BaseModel implements SearchResultContract.Model {

    @Inject
    public SearchResultModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<SearchResult<CollectionBean>> searchCollections(String keys, int page, int perPage) {
        return mRepositoryManager.obtainRetrofitService(USearchApi.class)
                .searchCollections(keys, page, perPage);

    }

    @Override
    public Observable<SearchResult<UserBean>> searchUsers(String keys, int page, int perPage) {
        return mRepositoryManager.obtainRetrofitService(USearchApi.class)
                .searchUser(keys, page, perPage);
    }

    @Override
    public Observable<SearchResult<PhotoBean>> searchPhotos(String keys, int page, int perPage,
                                                            String collections,
                                                            String orientation) {
        return mRepositoryManager.obtainRetrofitService(USearchApi.class)
                .searchPhotos(keys, page, perPage, collections, orientation);
    }
}
