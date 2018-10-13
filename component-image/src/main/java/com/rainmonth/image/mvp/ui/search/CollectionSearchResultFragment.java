package com.rainmonth.image.mvp.ui.search;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SearchBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.model.bean.UserBean;
import com.rainmonth.image.mvp.ui.adapter.CollectionsAdapter;

public class CollectionSearchResultFragment extends BaseLazyFragment implements SearchResultContract.View {
    private SwipeRefreshLayout srlContainer;
    private RecyclerView rvCollectionSearchResult;
    private SearchResult<CollectionBean> collectionSearchResult;
    private int page = 2, perPage = 10;// 第一页数据由已Search传入
    private CollectionsAdapter adapter;
    private boolean isRefresh;
    private String searchKeys;

    public static CollectionSearchResultFragment getInstance(String searchKey) {
        CollectionSearchResultFragment collectionSearchResultFragment = new CollectionSearchResultFragment();
        Bundle localBundle = new Bundle();
        localBundle.putString(Consts.SEARCH_KEY, searchKey);
        collectionSearchResultFragment.setArguments(localBundle);
        return collectionSearchResultFragment;
    }

    public void setCollectionSearchResult(SearchResult<CollectionBean> collectionSearchResult) {
        this.collectionSearchResult = collectionSearchResult;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewsAndEvents(View view) {
        if (getArguments() != null) {
            searchKeys = getArguments().getString(Consts.SEARCH_KEY);
        }
        final SearchResultPresenter presenter = new SearchResultPresenter(this);
        adapter = new CollectionsAdapter(mContext, R.layout.image_rv_item_collections);
        rvCollectionSearchResult = view.findViewById(R.id.srl_container);
        rvCollectionSearchResult = view.findViewById(R.id.rv_collection_search_result);
        rvCollectionSearchResult.setLayoutManager(new LinearLayoutManager(mContext));
        rvCollectionSearchResult.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                showProgress();
                presenter.search(Consts.SEARCH_COLLECTIONS, searchKeys, page, perPage, "", "");
            }
        }, rvCollectionSearchResult);
        if (collectionSearchResult != null && collectionSearchResult.getResults() != null) {
            adapter.addData(collectionSearchResult.getResults());
            if (collectionSearchResult.getResults().size() == 0) {
                TextView textView = new TextView(mContext);
                textView.setText("无合集数据");
                adapter.setEmptyView(textView);
            }
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_fragment_collection_search_result;
    }

    @Override
    public void initSearchResult(SearchBean<PhotoBean, CollectionBean, UserBean> searchBean) {

    }

    @Override
    public <T> void initViewWithSearchResult(SearchResult<T> searchResult) {
        SearchResult<CollectionBean> temp = (SearchResult<CollectionBean>) searchResult;
        hideProgress();
        if (page == temp.getTotal_pages()) {
            adapter.loadMoreEnd(true);
        } else {
            adapter.addData(temp.getResults());
            adapter.loadMoreComplete();
        }
        page++;
    }

    @Override
    public void showError(String message) {
        hideProgress();
    }
}
