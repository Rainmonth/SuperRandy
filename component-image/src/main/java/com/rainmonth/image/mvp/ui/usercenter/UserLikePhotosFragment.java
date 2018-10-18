package com.rainmonth.image.mvp.ui.usercenter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.ui.adapter.PhotosAdapter;
import com.rainmonth.image.mvp.ui.photo.PhotoDetailActivity;

import java.util.List;

/**
 * 用户喜欢的图片
 */
public class UserLikePhotosFragment extends BaseLazyFragment implements UserCenterContract.View {

    private SwipeRefreshLayout srlContainer;
    private RecyclerView rvUserLikePhotos;
    private PhotosAdapter photosAdapter;
    private boolean isRefresh = false;
    private int perPage = 10;
    private int page;
    private String orderBy = "latest";
    private String username;
    private UserCenterPresenter userCenterPresenter;

    public static UserLikePhotosFragment newInstance(String username) {
        UserLikePhotosFragment fragment = new UserLikePhotosFragment();
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
        userCenterPresenter = new UserCenterPresenter(new UserCenterModel(ComponentUtils.getAppComponent().repositoryManager()), this);
        srlContainer = view.findViewById(R.id.srl_container);
        rvUserLikePhotos = view.findViewById(R.id.rv_user_like_photos);

        srlContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });
//
        photosAdapter = new PhotosAdapter(mContext, R.layout.image_rv_item_photos);
        photosAdapter.setEnableLoadMore(true);
        photosAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                userCenterPresenter.getUserLikePhotos(username, page, perPage, orderBy);
            }
        }, rvUserLikePhotos);

        photosAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // 一次传递一页数据，并传递当前索引及页面，方便数据请求
                int currentPage = position / perPage + 1;
                int currentIndex = position % perPage;
                Bundle bundle = new Bundle();
                List<PhotoBean> currentPagePhotos = photosAdapter.getData()
                        .subList((currentPage - 1) * perPage, currentPage * perPage);
                SparseArray<PhotoBean> beanSparseArray =
                        convertListToSparseArray(currentPagePhotos);
                bundle.putSparseParcelableArray(Consts.PHOTO_LIST, beanSparseArray);
                bundle.putInt(Consts.CURRENT_PAGE, currentPage);
                bundle.putInt(Consts.CURRENT_INDEX, currentIndex);
                bundle.putInt(Consts.PAGE_SIZE, perPage);
                bundle.putString(Consts.ORDER_BY, orderBy);
                bundle.putString(Consts.FROM, Consts.FROM_PHOTO);
                readyGo(PhotoDetailActivity.class, bundle);
            }
        });

        rvUserLikePhotos.setAdapter(photosAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvUserLikePhotos.setLayoutManager(manager);
        doRefresh();
    }

    private void doRefresh() {
        isRefresh = true;
        srlContainer.setRefreshing(true);
        page = 1;
        userCenterPresenter.getUserLikePhotos(username, page, perPage, orderBy);
    }

    private SparseArray<PhotoBean> convertListToSparseArray(List<PhotoBean> photoBeans) {
        SparseArray<PhotoBean> beanSparseArray = new SparseArray<>();
        for (int i = 0; i < photoBeans.size(); i++) {
            beanSparseArray.append(i, photoBeans.get(i));
        }

        return beanSparseArray;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_fragment_user_like_photos;
    }

    @Override
    public void initPhotoList(List<PhotoBean> photoBeans) {
        srlContainer.setRefreshing(false);
        final int size = photoBeans.size();
        if (isRefresh) {
            photosAdapter.setNewData(photoBeans);
            isRefresh = false;
        } else {
            if (size > 0) {
                photosAdapter.addData(photoBeans);
            }
        }
        if (perPage == photoBeans.size()) {
            photosAdapter.loadMoreComplete();
        } else if (perPage > photoBeans.size()) {
            photosAdapter.loadMoreEnd(true);
        }
        page++;
    }

    @Override
    public void initCollectionList(List<CollectionBean> collectionBeans) {

    }
}
