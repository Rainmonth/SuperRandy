package com.rainmonth.image.mvp.contract;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.model.bean.UserBean;

import io.reactivex.Observable;

/**
 * @author: Randy Zhang
 * @description: ${DESC}
 * @created: 2018/8/15
 **/
public interface SearchContract {
    interface View extends IBaseView {
        <T> void initResultList(SearchResult<T> searchResult);
    }

    interface Model extends IBaseModel {

        Observable<SearchResult<PhotoBean>> searchPhotos(String keys, int page, int perPage,
                                                         String collectionIds, String orientation);

        Observable<SearchResult<CollectionBean>> searchCollections(String keys, int page, int perPage);

        Observable<SearchResult<UserBean>> searchUsers(String keys, int page, int perPage);
    }
}
