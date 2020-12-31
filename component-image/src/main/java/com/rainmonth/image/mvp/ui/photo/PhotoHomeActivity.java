package com.rainmonth.image.mvp.ui.photo;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.rainmonth.adapter.base.BaseQuickAdapter;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.di.component.DaggerPhotoHomeComponent;
import com.rainmonth.image.di.module.PhotoHomeModule;
import com.rainmonth.image.mvp.contract.PhotoHomeContract;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.presenter.PhotoHomePresenter;
import com.rainmonth.image.mvp.ui.adapter.PhotosAdapter;

import java.util.List;

/**
 * 照片页
 * 功能简介
 * - 显示所有图片
 */
public class PhotoHomeActivity extends BaseActivity<PhotoHomePresenter> implements
        PhotoHomeContract.View {

    SwipeRefreshLayout imageSrfContainer;
    RecyclerView imageRvPhotos;
    PhotosAdapter photosAdapter;
    private int page, perPage = 10;
    private String orderBy = "latest";
    private boolean isRefresh = false;

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPhotoHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .photoHomeModule(new PhotoHomeModule(this))
                .build()
                .inject(this);

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_photo_home_activity;
    }

    @Override
    protected void initViewsAndEvents() {
        try {
            imageSrfContainer = findViewById(R.id.image_srl_container);
            imageRvPhotos = findViewById(R.id.image_rv_photos);

            imageSrfContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                    mPresenter.getPhotos(page, perPage, orderBy);
                }
            }, imageRvPhotos);

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

            imageRvPhotos.setAdapter(photosAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            imageRvPhotos.setLayoutManager(manager);
            doRefresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SparseArray<PhotoBean> convertListToSparseArray(List<PhotoBean> photoBeans) {
        SparseArray<PhotoBean> beanSparseArray = new SparseArray<>();
        for (int i = 0; i < photoBeans.size(); i++) {
            beanSparseArray.append(i, photoBeans.get(i));
        }

        return beanSparseArray;
    }

    private void doRefresh() {
        isRefresh = true;
        imageSrfContainer.setRefreshing(true);
        page = 1;
        mPresenter.getPhotos(page, perPage, orderBy);
    }

    @Override
    public void initPhotoList(List<PhotoBean> photoBeans) {
        imageSrfContainer.setRefreshing(false);
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
}
