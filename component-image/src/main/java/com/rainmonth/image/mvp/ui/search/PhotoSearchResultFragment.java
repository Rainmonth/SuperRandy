package com.rainmonth.image.mvp.ui.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.ui.adapter.PhotosAdapter;

public class PhotoSearchResultFragment extends BaseLazyFragment {

    private RecyclerView rvPhotoSearchResult;

    private SearchResult<PhotoBean> photoSearchResult;

    public static PhotoSearchResultFragment getInstance(String searchKey) {
        PhotoSearchResultFragment photoSearchResultFragment = new PhotoSearchResultFragment();
        Bundle localBundle = new Bundle();
        localBundle.putString(Consts.SEARCH_KEY, searchKey);
        photoSearchResultFragment.setArguments(localBundle);
        return photoSearchResultFragment;
    }

    public void setPhotoSearchResult(SearchResult<PhotoBean> photoSearchResult) {
        this.photoSearchResult = photoSearchResult;
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
        rvPhotoSearchResult = view.findViewById(R.id.rv_photo_search_result);
        rvPhotoSearchResult.setLayoutManager(new LinearLayoutManager(mContext));
        PhotosAdapter photosAdapter = new PhotosAdapter(mContext, R.layout.image_rv_item_photos);
        if (photoSearchResult != null && photoSearchResult.getResults() != null) {
            photosAdapter.setNewData(photoSearchResult.getResults());
        }
        rvPhotoSearchResult.setAdapter(photosAdapter);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_fragment_photo_search_result;
    }
}
