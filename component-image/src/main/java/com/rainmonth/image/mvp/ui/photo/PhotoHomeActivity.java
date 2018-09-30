package com.rainmonth.image.mvp.ui.photo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
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
                    imageSrfContainer.setRefreshing(true);
                    isRefresh = true;
                    page = 1;
                    mPresenter.getPhotos(page, perPage, orderBy);
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

            imageRvPhotos.setAdapter(photosAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            imageRvPhotos.setLayoutManager(manager);

            imageSrfContainer.setRefreshing(true);
            isRefresh = true;
            mPresenter.getPhotos(1, 10, orderBy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initPhotoList(List<PhotoBean> photoBeans) {
        photosAdapter.addData(photoBeans);
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
        } else if (page > photoBeans.size()) {
            photosAdapter.loadMoreEnd(true);
        }
        page++;
    }
}
