package com.rainmonth.image.mvp.ui.search;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.common.utils.RxUtils;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SearchBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.model.bean.UserBean;

import javax.inject.Inject;

@ActivityScope
public class SearchResultPresenter extends BasePresenter<SearchResultContract.Model, SearchResultContract.View> {

    @Inject
    public SearchResultPresenter(SearchResultContract.Model model, SearchResultContract.View rootView) {
        super(model, rootView);
    }

    public SearchResultPresenter(SearchResultContract.View view) {
        super(null, view);
        mModel = new SearchResultModel(ComponentUtils.getAppComponent().repositoryManager());
    }

    public void search(String searchType, String searchKey, int page, int perPage,
                       String collections,
                       String orientation) {
        if (Consts.SEARCH_COLLECTIONS.equals(searchType)) {
            addSubscribe(mModel.searchCollections(searchKey, page, perPage)
                    .compose(RxUtils.<SearchResult<CollectionBean>>getObservableTransformer())
                    .subscribeWith(new CommonSubscriber<SearchResult<CollectionBean>>(mView) {
                        @Override
                        public void onNext(SearchResult<CollectionBean> collectionSearchResult) {
                            mView.initViewWithSearchResult(collectionSearchResult);
                        }
                    }));
        } else if (Consts.SEARCH_USER.equals(searchType)) {
            addSubscribe(mModel.searchUsers(searchKey, page, perPage)
                    .compose(RxUtils.<SearchResult<UserBean>>getObservableTransformer())
                    .subscribeWith(new CommonSubscriber<SearchResult<UserBean>>(mView) {
                        @Override
                        public void onNext(SearchResult<UserBean> userSearchResult) {
                            mView.initViewWithSearchResult(userSearchResult);
                        }
                    }));
        } else if (Consts.SEARCH_PHOTOS.equals(searchType)) {
            addSubscribe(mModel.searchPhotos(searchKey, page, perPage, collections, orientation)
                    .compose(RxUtils.<SearchResult<PhotoBean>>getObservableTransformer())
                    .subscribeWith(new CommonSubscriber<SearchResult<PhotoBean>>(mView) {
                        @Override
                        public void onNext(SearchResult<PhotoBean> photoSearchResult) {
                            mView.initViewWithSearchResult(photoSearchResult);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            mView.showError("加载错误" + e.getMessage());
                        }
                    }));
        } else {
            addSubscribe(mModel.search(searchKey, page, perPage)
                    .compose(RxUtils.<SearchBean<PhotoBean, CollectionBean, UserBean>>getObservableTransformer())
                    .subscribeWith(new CommonSubscriber<SearchBean<PhotoBean, CollectionBean, UserBean>>(mView) {
                        @Override
                        public void onNext(SearchBean<PhotoBean, CollectionBean, UserBean> result) {
                            mView.initSearchResult(result);
                        }
                    }));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
