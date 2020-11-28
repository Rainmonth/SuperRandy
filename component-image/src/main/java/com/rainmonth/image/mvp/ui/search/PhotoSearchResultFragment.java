package com.rainmonth.image.mvp.ui.search;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rainmonth.common.adapter.base.BaseQuickAdapter;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SearchBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.model.bean.UserBean;
import com.rainmonth.image.mvp.ui.adapter.PhotosAdapter;

public class PhotoSearchResultFragment extends BaseLazyFragment implements SearchResultContract.View {

    private SwipeRefreshLayout srlContainer;
    private RecyclerView rvPhotoSearchResult;
    private PhotosAdapter photosAdapter;
    private String searchKeys;
    private String orientation = "squarish";
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

    private int page = 2, perPage = 10;
    private boolean isResresh = false;

    @Override
    protected void initViewsAndEvents(View view) {
        if (getArguments() != null) {
            searchKeys = getArguments().getString(Consts.SEARCH_KEY);
        }
        final SearchResultPresenter presenter = new SearchResultPresenter(this);
        photosAdapter = new PhotosAdapter(mContext, R.layout.image_rv_item_photos);
        srlContainer = view.findViewById(R.id.srl_container);
        rvPhotoSearchResult = view.findViewById(R.id.rv_photo_search_result);
        rvPhotoSearchResult.setLayoutManager(new LinearLayoutManager(mContext));
        rvPhotoSearchResult.setAdapter(photosAdapter);
        photosAdapter.setEnableLoadMore(true);
        photosAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isResresh = false;
                showProgress();
                presenter.search(Consts.SEARCH_PHOTOS, searchKeys, page, perPage, "", null);
            }
        }, rvPhotoSearchResult);

        if (photoSearchResult != null && photoSearchResult.getResults() != null) {
            photosAdapter.addData(photoSearchResult.getResults());
            if (photoSearchResult.getResults().size() == 0) {
                TextView textView = new TextView(mContext);
                textView.setText("无照片数据");
                photosAdapter.setEmptyView(textView);
            }
        }

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_fragment_photo_search_result;
    }

    @Override
    public void initSearchResult(SearchBean<PhotoBean, CollectionBean, UserBean> searchBean) {

    }

    @Override
    public <T> void initViewWithSearchResult(SearchResult<T> searchResult) {
        SearchResult<PhotoBean> temp = (SearchResult<PhotoBean>) searchResult;
        hideProgress();
        if (page > temp.getTotal_pages()) {
            photosAdapter.loadMoreEnd(true);
        } else {
            photosAdapter.addData(temp.getResults());
            photosAdapter.loadMoreComplete();
        }
        page++;
    }

    @Override
    public void showError(String message) {
        hideProgress();
    }
}
