package com.rainmonth.image.mvp.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.di.component.DaggerCollectionHomeComponent;
import com.rainmonth.image.di.module.CollectionHomeModule;
import com.rainmonth.image.mvp.contract.CollectionHomeContract;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.presenter.CollectionHomePresenter;
import com.rainmonth.image.mvp.ui.adapter.CollectionsAdapter;

import java.util.List;

/**
 * @desprition: 合集页面
 * @author: RandyZhang
 * @date: 2018/8/14 下午10:27
 */
public class CollectionHomeActivity extends BaseActivity<CollectionHomePresenter>
        implements CollectionHomeContract.View {
    RecyclerView imageRvCollections;
    SwipeRefreshLayout imageSrlContainer;
    CollectionsAdapter collectionsAdapter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCollectionHomeComponent.builder()
                .appComponent(appComponent)
                .collectionHomeModule(new CollectionHomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_collection_home;
    }

    @Override
    protected void initViewsAndEvents() {
        try {
            Log.d("Randy", "initViewsAndEvents");
            Log.e("Randy", "initViewsAndEvents");
            collectionsAdapter = new CollectionsAdapter(mContext, R.layout.image_rv_item_collections);
            imageSrlContainer = findViewById(R.id.image_srl_container);
            imageRvCollections = findViewById(R.id.image_rv_collections);
            GridLayoutManager manager = new GridLayoutManager(mContext, 2);
            imageRvCollections.setLayoutManager(manager);
            imageRvCollections.setAdapter(collectionsAdapter);
            mPresenter.getCollections(1, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initCollectionList(List<CollectionBean> collectionBeans) {
        collectionsAdapter.addData(collectionBeans);
    }
}