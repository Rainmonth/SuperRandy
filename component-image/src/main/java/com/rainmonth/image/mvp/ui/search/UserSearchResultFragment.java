package com.rainmonth.image.mvp.ui.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.model.bean.UserBean;
import com.rainmonth.image.mvp.ui.adapter.CollectionsAdapter;
import com.rainmonth.image.mvp.ui.adapter.UserAdapter;

public class UserSearchResultFragment extends BaseLazyFragment {
    private RecyclerView rvUserSearchResult;
    private SearchResult<UserBean> userSearchResult;

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
        rvUserSearchResult = view.findViewById(R.id.rv_user_search_result);
        rvUserSearchResult.setLayoutManager(new LinearLayoutManager(mContext));
        UserAdapter adapter = new UserAdapter(mContext, R.layout.image_rv_item_users);
        if (userSearchResult != null && userSearchResult.getResults() != null) {
            adapter.setNewData(userSearchResult.getResults());
        }
        rvUserSearchResult.setAdapter(adapter);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_fragment_user_search_result;
    }
}
