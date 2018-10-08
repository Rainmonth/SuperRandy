package com.rainmonth.image.mvp.ui.photo;

import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.image.api.UCollectionApi;
import com.rainmonth.image.api.UPhotoApi;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class PhotoDetailModel extends BaseModel implements PhotoDetailContract.Model {

    @Inject
    public PhotoDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<List<PhotoBean>> getPagePhotos(int page, int perPage,
                                                     long collectionId,
                                                     String orderBy,
                                                     String from) {
        if ("collection".equals(from)) {
            return mRepositoryManager.obtainRetrofitService(UCollectionApi.class)
                    .getCollectionPhotos(collectionId, page, perPage);
        } else {
//            if ("photos".equals(from)) {
            return mRepositoryManager.obtainRetrofitService(UPhotoApi.class)
                    .getPhotos(page, perPage, orderBy);
        }
    }
}
