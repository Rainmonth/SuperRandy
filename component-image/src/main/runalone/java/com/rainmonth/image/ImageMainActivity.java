package com.rainmonth.image;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.di.component.DaggerImageHomeComponent;
import com.rainmonth.image.di.module.ImageHomeModule;
import com.rainmonth.image.di.module.UnsplashUserModule;
import com.rainmonth.image.mvp.contract.ImageHomeContract;
import com.rainmonth.image.mvp.contract.UnsplashUserContract;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.UserBean;
import com.rainmonth.image.mvp.presenter.ImageHomePresenter;
import com.rainmonth.image.mvp.ui.adapter.PhotosAdapter;

import java.util.List;

public class ImageMainActivity extends BaseActivity<ImageHomePresenter>
        implements ImageHomeContract.View, UnsplashUserContract.View {

    SwipeRefreshLayout srfContainer;
    RecyclerView rvPhotos;
    PhotosAdapter photosAdapter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerImageHomeComponent.builder()
                .appComponent(appComponent)
                .imageHomeModule(new ImageHomeModule(this))
                .unsplashUserModule(new UnsplashUserModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
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
    public void initToolbar(int colorResId) {

    }

    @Override
    public void initPhotoList(List<PhotoBean> photoBeans) {
        photosAdapter.addData(photoBeans);
    }

    @Override
    public void initUserInfo(UserBean responseResponse) {

    }
}
