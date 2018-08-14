package com.rainmonth.image.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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


public class PhotoHomeActivity extends BaseActivity<PhotoHomePresenter> implements
        PhotoHomeContract.View {

    SwipeRefreshLayout srfContainer;
    RecyclerView rvPhotos;
    PhotosAdapter photosAdapter;

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
//        unsplashUserPresenter.getUserInfo("charlesdeluvio", 1, 10);
        try {
            srfContainer = findViewById(R.id.image_srl_container);
            rvPhotos = findViewById(R.id.image_rv_photos);
//
            photosAdapter = new PhotosAdapter(mContext, R.layout.image_rv_item_photos);
            rvPhotos.setAdapter(photosAdapter);
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            rvPhotos.setLayoutManager(manager);
            mPresenter.getPhotos(1, 10, "latest");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initPhotoList(List<PhotoBean> photoBeans) {
        photosAdapter.addData(photoBeans);
    }
}
