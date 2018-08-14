package com.rainmonth.image.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.utils.RxUtils;
import com.rainmonth.image.mvp.contract.CollectionHomeContract;
import com.rainmonth.image.mvp.contract.PhotoHomeContract;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class CollectionHomePresenter extends BasePresenter<CollectionHomeContract.Model, CollectionHomeContract.View> {

    @Inject
    public CollectionHomePresenter(CollectionHomeContract.Model model, CollectionHomeContract.View rootView) {
        super(model, rootView);
    }

    public void getCollections(int page, int perPage) {
        addSubscribe(mModel.getCollections(page, perPage)
                .compose(RxUtils.getObservableTransformer())
                .subscribeWith(new CommonSubscriber<List<CollectionBean>>(mView) {
                    @Override
                    public void onNext(List<CollectionBean> collectionBeans) {
                        mView.initCollectionList(collectionBeans);
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
