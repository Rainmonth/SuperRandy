package com.rainmonth.image.mvp.ui.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.ui.adapter.CollectionsAdapter;

public class CollectionSearchResultFragment extends BaseLazyFragment {

    private RecyclerView rvCollectionSearchResult;
    private SearchResult<CollectionBean> collectionSearchResult;

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
        rvCollectionSearchResult = view.findViewById(R.id.rv_collection_search_result);
        rvCollectionSearchResult.setLayoutManager(new LinearLayoutManager(mContext));
        CollectionsAdapter adapter = new CollectionsAdapter(mContext, R.layout.image_rv_item_collections);
        if (collectionSearchResult != null && collectionSearchResult.getResults() != null) {
            adapter.setNewData(collectionSearchResult.getResults());
        }
        rvCollectionSearchResult.setAdapter(adapter);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_fragment_collection_search_result;
    }
}
