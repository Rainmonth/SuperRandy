package com.rainmonth.image.mvp.model;

import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.image.api.UPhotoApi;
import com.rainmonth.image.mvp.contract.PhotoHomeContract;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class PhotoHomeModel extends BaseModel implements PhotoHomeContract.Model {
    @Inject
    public PhotoHomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<List<PhotoBean>> getPhotos(int page, int perPage, String orderBy) {
        return mRepositoryManager.obtainRetrofitService(UPhotoApi.class)
                .getPhotos(page, perPage, orderBy);
    }
}