package com.rainmonth.image.mvp.ui.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SearchBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.model.bean.UserBean;
import com.rainmonth.image.mvp.ui.adapter.PhotosAdapter;

public class PhotoSearchResultFragment extends BaseLazyFragment implements SearchResultContract.View {

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

    private int page = 1, perPage = 10;
    private boolean isResresh = false;

    @Override
    protected void initViewsAndEvents(View view) {
        searchKeys = getArguments().getString(Consts.SEARCH_KEY);
        SearchResultModel model = new SearchResultModel(ComponentUtils.getAppComponent().repositoryManager());

        final SearchResultPresenter presenter = new SearchResultPresenter(model, this);
        photosAdapter = new PhotosAdapter(mContext, R.layout.image_rv_item_photos);
        rvPhotoSearchResult = view.findViewById(R.id.rv_photo_search_result);
        rvPhotoSearchResult.setLayoutManager(new LinearLayoutManager(mContext));
        rvPhotoSearchResult.setAdapter(photosAdapter);
        photosAdapter.setEnableLoadMore(true);
        photosAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isResresh = false;
                page++;
                presenter.search(Consts.SEARCH_PHOTOS, searchKeys, page, perPage, "", orientation);
            }
        }, rvPhotoSearchResult);

        if (photoSearchResult != null && photoSearchResult.getResults() != null) {
            photosAdapter.setNewData(photoSearchResult.getResults());
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
        if (page == temp.getTotal_pages()) {
            photosAdapter.loadMoreEnd(true);
        } else {
            photosAdapter.addData(temp.getResults());
            photosAdapter.loadMoreComplete();
        }
    }
}
