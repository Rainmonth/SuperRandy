package com.rainmonth.image.mvp.ui.collection;

import android.os.Bundle;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.di.component.DaggerCollectionDetailComponent;
import com.rainmonth.image.di.module.CollectionDetailMoudle;
import com.rainmonth.image.mvp.contract.CollectionDetailContract;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.presenter.CollectionDetailPresenter;
import com.socks.library.KLog;

import java.util.List;

/**
 * 合集详情页面
 * 页面功能
 * - 展示合集里面的所有图片
 * - 对于合集里面的图片，其浏览方式采用一般图片的处理方式
 * - <b>图片展示完成后</b>，显示该合集相关联的合集
 * - 对于关联的合集，其处理方式同一般合集
 */
public class CollectionDetailActivity extends BaseActivity<CollectionDetailPresenter>
        implements CollectionDetailContract.View {
    private long collectionId;
    int page = 1, perPage = 10;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_collection_detail;
    }

    @Override
    protected void initViewsAndEvents() {
        mPresenter.getCollectionPhotos(collectionId, page, perPage);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        if (extras != null) {
            collectionId = extras.getLong(Consts.COLLECTION_ID);
        }

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCollectionDetailComponent
                .builder()
                .appComponent(appComponent)
                .collectionDetailMoudle(new CollectionDetailMoudle(this))
                .build()
                .inject(this);
    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    public void initCollectionPhotoList(List<PhotoBean> photoBeans) {
        KLog.d("Randy", photoBeans);
    }
}
