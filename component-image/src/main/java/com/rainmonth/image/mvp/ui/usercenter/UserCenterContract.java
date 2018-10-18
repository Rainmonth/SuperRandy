package com.rainmonth.image.mvp.ui.usercenter;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SiteBean;
import com.rainmonth.image.mvp.model.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;

public interface UserCenterContract {
    interface View extends IBaseView {
        void initPhotoList(List<PhotoBean> photoBeans);

        void initCollectionList(List<CollectionBean> collectionBeans);
    }

    interface Model extends IBaseModel {
        Observable<UserBean> getUserInfo(String username, int w, int h);

        Observable<List<PhotoBean>> getUserLikePhotos(String username, int page, int perPage,
                                                      String orderBy);

        Observable<List<PhotoBean>> getUserPhotos(String username, int page, int perPage,
                                                  String orderBy, boolean stats, String resolutions,
                                                  int quantity);

        Observable<List<CollectionBean>> getUserCollections(String username, int page, int perPage);

        Observable<SiteBean> getUserPersonalSite(String username);


    }
}
