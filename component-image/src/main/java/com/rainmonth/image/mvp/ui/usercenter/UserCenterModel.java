package com.rainmonth.image.mvp.ui.usercenter;

import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.image.api.UUserApi;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SiteBean;
import com.rainmonth.image.mvp.model.bean.UserBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class UserCenterModel extends BaseModel implements UserCenterContract.Model {

    @Inject
    public UserCenterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<UserBean> getUserInfo(String username, int w, int h) {
        return mRepositoryManager.obtainRetrofitService(UUserApi.class)
                .getUserInfo(username, w, h);
    }

    @Override
    public Observable<List<PhotoBean>> getUserLikePhotos(String username, int page, int perPage, String orderBy) {
        return mRepositoryManager.obtainRetrofitService(UUserApi.class)
                .getUserLikePhotos(username, page, perPage, orderBy);
    }

    @Override
    public Observable<List<PhotoBean>> getUserPhotos(String username, int page, int perPage,
                                                     String orderBy, boolean stats, String resolutions, int quantity) {
        return mRepositoryManager.obtainRetrofitService(UUserApi.class)
                .getUserPhotos(username, page, perPage, orderBy, stats, resolutions, quantity);
    }

    @Override
    public Observable<List<CollectionBean>> getUserCollections(String username, int page, int perPage) {
        return mRepositoryManager.obtainRetrofitService(UUserApi.class)
                .getUserCollections(username, page, perPage);
    }

    @Override
    public Observable<SiteBean> getUserPersonalSite(String username) {
        return mRepositoryManager.obtainRetrofitService(UUserApi.class)
                .getUserPersonalSite(username);
    }
}
