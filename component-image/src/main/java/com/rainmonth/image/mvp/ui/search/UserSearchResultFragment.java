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
import com.rainmonth.image.mvp.ui.adapter.UserAdapter;

public class UserSearchResultFragment extends BaseLazyFragment implements SearchResultContract.View {
    private SwipeRefreshLayout srlContainer;
    private RecyclerView rvUserSearchResult;
    private SearchResult<UserBean> userSearchResult;
    private UserAdapter adapter;
    private int page = 2, perPage = 10;
    private String searchKeys;
    private boolean isRefresh = false;

    public static UserSearchResultFragment getInstance(String searchKey) {
        UserSearchResultFragment userSearchResultFragment = new UserSearchResultFragment();
        Bundle localBundle = new Bundle();
        localBundle.putString(Consts.SEARCH_KEY, searchKey);
        userSearchResultFragment.setArguments(localBundle);
        return userSearchResultFragment;
    }

    public void setUserSearchResult(SearchResult<UserBean> userSearchResult) {
        this.userSearchResult = userSearchResult;
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

        srlContainer = view.findViewById(R.id.srl_container);
        adapter = new UserAdapter(mContext, R.layout.image_rv_item_users);
        rvUserSearchResult = view.findViewById(R.id.rv_user_search_result);
        rvUserSearchResult.setLayoutManager(new LinearLayoutManager(mContext));
        rvUserSearchResult.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                showProgress();
                presenter.search(Consts.SEARCH_USER, searchKeys, page, perPage, "", "");
            }
        }, rvUserSearchResult);
        if (userSearchResult != null && userSearchResult.getResults() != null) {
            adapter.setNewData(userSearchResult.getResults());
            if (userSearchResult.getResults().size() == 0) {
                TextView textView = new TextView(mContext);
                textView.setText("无用户数据");
                adapter.setEmptyView(textView);
            }
        }

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_fragment_user_search_result;
    }

    @Override
    public void initSearchResult(SearchBean<PhotoBean, CollectionBean, UserBean> searchBean) {

    }

    @Override
    public <T> void initViewWithSearchResult(SearchResult<T> searchResult) {
        SearchResult<UserBean> temp = (SearchResult<UserBean>) searchResult;
        hideProgress();
        if (page > temp.getTotal_pages()) {
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
