package com.rainmonth.image.mvp.ui.photo;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

import io.reactivex.Observable;

public interface PhotoDetailContract {
    interface View extends IBaseView {
        void refreshViewWithPhotos(List<PhotoBean> photoBeans);
    }

    interface Model extends IBaseModel {
        Observable<List<PhotoBean>> getPagePhotos(int page, int perPage,
                                                  long collectionId,
                                                  String orderBy,
                                                  String from);

    }

}
