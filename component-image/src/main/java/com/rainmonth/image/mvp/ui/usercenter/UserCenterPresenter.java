package com.rainmonth.image.mvp.ui.usercenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.utils.RxUtils;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

import javax.inject.Inject;

@ActivityScope
public class UserCenterPresenter extends BasePresenter<UserCenterContract.Model, UserCenterContract.View> {
    @Inject
    public UserCenterPresenter(UserCenterContract.Model model, UserCenterContract.View rootView) {
        super(model, rootView);
    }

    public UserCenterPresenter(UserCenterContract.View rootView) {
        super(rootView);
        mModel = new UserCenterModel(ComponentUtils.getAppComponent().repositoryManager());
    }

    public void getUserLikePhotos(String username, int page, int perPage, String orderBy) {
        addSubscribe(mModel.getUserLikePhotos(username, page, perPage, orderBy)
                .compose(RxUtils.<List<PhotoBean>>getObservableTransformer())
                .subscribeWith(new CommonSubscriber<List<PhotoBean>>(mView) {
                    @Override
                    public void onNext(List<PhotoBean> photoBeans) {
                        mView.initPhotoList(photoBeans);
                    }
                }));
    }

    public void getUserPhotos(String username, int page, int perPage, String orderBy) {
        addSubscribe(mModel.getUserLikePhotos(username, page, perPage, orderBy)
                .compose(RxUtils.<List<PhotoBean>>getObservableTransformer())
                .subscribeWith(new CommonSubscriber<List<PhotoBean>>(mView) {
                    @Override
                    public void onNext(List<PhotoBean> photoBeans) {
                        mView.initPhotoList(photoBeans);
                    }
                }));
    }

    public void getUserCollections(String username, int page, int perPage) {
        addSubscribe(mModel.getUserCollections(username, page, perPage)
                .compose(RxUtils.<List<CollectionBean>>getObservableTransformer())
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
