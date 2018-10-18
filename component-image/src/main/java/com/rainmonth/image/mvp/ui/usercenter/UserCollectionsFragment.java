package com.rainmonth.image.mvp.ui.usercenter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.ui.adapter.CollectionsAdapter;
import com.rainmonth.image.mvp.ui.collection.CollectionDetailActivity;

import java.util.List;

/**
 * 用户的合集
 */
public class UserCollectionsFragment extends BaseLazyFragment implements UserCenterContract.View {

    RecyclerView rvUserCollections;
    SwipeRefreshLayout srlContainer;
    CollectionsAdapter collectionsAdapter;
    private boolean isRefresh = false;
    private int page, perPage = 10;
    private String username;
    private UserCenterPresenter userCenterPresenter;

    public static UserCollectionsFragment newInstance(String username) {
        UserCollectionsFragment fragment = new UserCollectionsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Consts.USER_NAME, username);
        fragment.setArguments(bundle);
        return fragment;
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
        username = getArguments().getString(Consts.USER_NAME);
        userCenterPresenter = new UserCenterPresenter(this);
        collectionsAdapter = new CollectionsAdapter(mContext, R.layout.image_rv_item_collections);
        srlContainer = view.findViewById(R.id.srl_container);
        srlContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });

        rvUserCollections = view.findViewById(R.id.rv_user_collections);
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        rvUserCollections.setLayoutManager(manager);
        rvUserCollections.setAdapter(collectionsAdapter);
        collectionsAdapter.setEnableLoadMore(true);
        collectionsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                userCenterPresenter.getUserCollections(username, page, perPage);
            }
        }, rvUserCollections);
        collectionsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CollectionBean bean = collectionsAdapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putLong(Consts.COLLECTION_ID, bean.getId());
                readyGo(CollectionDetailActivity.class, bundle);
            }
        });
        doRefresh();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_fragment_user_collections;
    }

    @Override
    public void initPhotoList(List<PhotoBean> photoBeans) {

    }

    private void doRefresh() {
        isRefresh = true;
        srlContainer.setRefreshing(true);
        page = 1;
        userCenterPresenter.getUserCollections(username, page, perPage);
    }

    @Override
    public void initCollectionList(List<CollectionBean> collectionBeans) {
        srlContainer.setRefreshing(false);
        final int size = collectionBeans.size();
        if (isRefresh) {
            collectionsAdapter.setNewData(collectionBeans);
            isRefresh = false;
        } else {
            if (size > 0) {
                collectionsAdapter.addData(collectionBeans);
            }
        }
        if (perPage == collectionBeans.size()) {
            collectionsAdapter.loadMoreComplete();
        } else if (page > collectionBeans.size()) {
            collectionsAdapter.loadMoreEnd(true);
        }
        page++;
    }
}
