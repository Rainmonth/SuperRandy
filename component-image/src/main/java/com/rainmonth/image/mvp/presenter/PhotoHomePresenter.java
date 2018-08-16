package com.rainmonth.image.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.utils.RxUtils;
import com.rainmonth.image.mvp.contract.PhotoHomeContract;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class PhotoHomePresenter extends BasePresenter<PhotoHomeContract.Model, PhotoHomeContract.View> {

    @Inject
    public PhotoHomePresenter(PhotoHomeContract.Model model, PhotoHomeContract.View rootView) {
        super(model, rootView);
    }

    public void getPhotos(int page, int perPage, String orderBy) {
        addSubscribe(mModel.getPhotos(page, perPage, orderBy)
                .compose(RxUtils.<List<PhotoBean>>getObservableTransformer())
                .subscribeWith(new CommonSubscriber<List<PhotoBean>>(mView) {
                    @Override
                    public void onNext(List<PhotoBean> photoBeans) {
                        mView.initPhotoList(photoBeans);
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
