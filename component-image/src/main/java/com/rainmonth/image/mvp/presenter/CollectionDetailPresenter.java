package com.rainmonth.image.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.utils.RxUtils;
import com.rainmonth.image.mvp.contract.CollectionDetailContract;
import com.rainmonth.image.mvp.contract.CollectionHomeContract;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class CollectionDetailPresenter extends BasePresenter<CollectionDetailContract.Model,
        CollectionDetailContract.View> {

    @Inject
    public CollectionDetailPresenter(CollectionDetailContract.Model model,
                                     CollectionDetailContract.View rootView) {
        super(model, rootView);
    }

    public void getCollectionPhotos(long collectionId, int page, int perPage) {
        addSubscribe(mModel.getCollectionPhotos(collectionId, page, perPage)
                .compose(RxUtils.<List<PhotoBean>>getObservableTransformer())
                .subscribeWith(new CommonSubscriber<List<PhotoBean>>(mView) {
                    @Override
                    public void onNext(List<PhotoBean> collectionBeans) {
                        mView.initCollectionPhotoList(collectionBeans);
                    }
                }));
    }

    public void getRelatedCollections(long collectionId) {
        addSubscribe(mModel.getRelatedCollections(collectionId)
                .compose(RxUtils.<List<CollectionBean>>getObservableTransformer())
                .subscribeWith(new CommonSubscriber<List<CollectionBean>>(mView) {
                    @Override
                    public void onNext(List<CollectionBean> collectionBeans) {

                    }
                })
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
