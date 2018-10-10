package com.rainmonth.image.mvp.ui.photo;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.utils.RxUtils;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

@ActivityScope
public class PhotoDetailPresenter extends BasePresenter<PhotoDetailContract.Model, PhotoDetailContract.View> {

    @Inject
    public PhotoDetailPresenter(PhotoDetailContract.Model model, PhotoDetailContract.View rootView) {
        super(model, rootView);
    }

    public void getPrePagePhotos(int page,
                                 int perPage,
                                 long collectionId,
                                 String orderBy,
                                 String from) {
        KLog.d("Image", "request:" + page + " pageSize:" + perPage + " from:" + from);
        addSubscribe(mModel.getPagePhotos(page, perPage, collectionId, orderBy, from)
                .compose(RxUtils.<List<PhotoBean>>getObservableTransformer())
                .subscribeWith(new CommonSubscriber<List<PhotoBean>>(mView) {
                    @Override
                    public void onNext(List<PhotoBean> photoBeans) {
                        mView.refreshViewWithPhotos(photoBeans);
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
