package com.rainmonth.image.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.utils.RxUtils;
import com.rainmonth.image.mvp.contract.ImageHomeContract;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class ImageHomePresenter extends BasePresenter<ImageHomeContract.Model, ImageHomeContract.View> {

    @Inject
    public ImageHomePresenter(ImageHomeContract.Model model, ImageHomeContract.View rootView) {
        super(model, rootView);
    }

    public void getPhotos(int page, int perPage, String orderBy) {
        addSubscribe(mModel.getPhotos(page, perPage, orderBy)
                .compose(RxUtils.getObservableTransformer())
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
