package com.rainmonth.image.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.utils.CommonUtils;
import com.rainmonth.common.utils.RxUtils;
import com.rainmonth.image.mvp.contract.SearchContract;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.model.bean.UserBean;

import javax.inject.Inject;

/**
 * @author: Randy Zhang
 * @description: SearchPresenter
 * @created: 2018/8/15
 **/
public class SearchPresenter extends BasePresenter<SearchContract.Model, SearchContract.View> {

    public static final int SEARCH_TYPE_USER = 0;
    public static final int SEARCH_TYPE_PHOTOS = 1;
    public static final int SEARCH_TYPE_COLLECTIONS = 2;

    @Inject
    public SearchPresenter(SearchContract.Model model, SearchContract.View rootView) {
        super(model, rootView);
    }

    /**
     * @param searchType    0->USER,1->PHOTOS,2->COLLECTIONS
     * @param keys
     * @param page
     * @param perPage
     * @param collectionIds
     * @param orientation
     */
    public void search(int searchType, String keys, int page, int perPage, String collectionIds,
                       String orientation) {
        if (searchType == SEARCH_TYPE_USER) {
            addSubscribe(mModel.searchUsers(keys, page, perPage)
                    .compose(RxUtils.getObservableTransformer())
                    .subscribeWith(new CommonSubscriber<SearchResult<UserBean>>(mView) {
                        @Override
                        public void onNext(SearchResult<UserBean> userBeanSearchResult) {
                            mView.initResultList(userBeanSearchResult);
                        }
                    }));
        } else if (searchType == SEARCH_TYPE_COLLECTIONS) {
            addSubscribe(mModel.searchCollections(keys, page, perPage)
                    .compose(RxUtils.getObservableTransformer())
                    .subscribeWith(new CommonSubscriber<SearchResult<CollectionBean>>(mView) {
                        @Override
                        public void onNext(SearchResult<CollectionBean> collectionBeanSearchResult) {
                            mView.initResultList(collectionBeanSearchResult);
                        }
                    }));
        } else {
            if (CommonUtils.isNullOrEmpty(orientation))
                orientation = "landscape";
            addSubscribe(mModel.searchPhotos(keys, page, perPage, collectionIds, orientation)
                    .compose(RxUtils.getObservableTransformer())
                    .subscribeWith(new CommonSubscriber<SearchResult<PhotoBean>>(mView) {
                        @Override
                        public void onNext(SearchResult<PhotoBean> photoBeanSearchResult) {
                            mView.initResultList(photoBeanSearchResult);
                        }
                    }));
        }
    }
}