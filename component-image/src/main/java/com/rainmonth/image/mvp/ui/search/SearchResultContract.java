package com.rainmonth.image.mvp.ui.search;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SearchBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.model.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;

public interface SearchResultContract {
    interface View extends IBaseView {
        void initSearchResult(SearchBean<PhotoBean, CollectionBean, UserBean> searchBean);

        <T> void initViewWithSearchResult(SearchResult<T> searchResult);
    }

    interface Model extends IBaseModel {
        Observable<SearchBean<PhotoBean, CollectionBean, UserBean>> search(String keys, int page, int perPage);

        Observable<SearchResult<CollectionBean>> searchCollections(String keys, int page, int perPage);

        Observable<SearchResult<UserBean>> searchUsers(String keys, int page, int perPage);

        /**
         * @param collections 多个collection id用逗号隔开
         * @param orientation landscape, portrait, and squarish
         */
        Observable<SearchResult<PhotoBean>> searchPhotos(String keys, int page, int perPage,
                                                         String collections,
                                                         String orientation);

    }
}
