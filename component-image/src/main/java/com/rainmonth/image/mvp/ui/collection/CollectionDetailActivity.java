package com.rainmonth.image.mvp.ui.collection;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.di.component.DaggerCollectionDetailComponent;
import com.rainmonth.image.di.module.CollectionDetailMoudle;
import com.rainmonth.image.mvp.contract.CollectionDetailContract;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.presenter.CollectionDetailPresenter;
import com.rainmonth.image.mvp.ui.adapter.PhotosAdapter;
import com.rainmonth.image.mvp.ui.photo.PhotoDetailActivity;

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
    private SwipeRefreshLayout detailSrl;
    private RecyclerView detailPhotosRv;
    PhotosAdapter photosAdapter;
    private long collectionId;
    int page = 1, perPage = 10;
    private boolean isRefresh = false;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_collection_detail;
    }

    @Override
    protected void initViewsAndEvents() {
        detailSrl = findViewById(R.id.collectionDetailSrl);
        detailPhotosRv = findViewById(R.id.collectionDetailPhotosRv);
        detailSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });

        photosAdapter = new PhotosAdapter(mContext, R.layout.image_rv_item_photos);
        photosAdapter.setEnableLoadMore(true);
        photosAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                mPresenter.getCollectionPhotos(collectionId, page, perPage);
            }
        }, detailPhotosRv);

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
                bundle.putInt(Consts.CURRENT_PAGE, page);
                bundle.putInt(Consts.CURRENT_INDEX, currentIndex);
                bundle.putInt(Consts.PAGE_SIZE, perPage);
                readyGo(PhotoDetailActivity.class, bundle);
            }
        });

        detailPhotosRv.setAdapter(photosAdapter);
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        detailPhotosRv.setLayoutManager(manager);
        doRefresh();
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
        detailSrl.setRefreshing(true);
        page = 1;
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
        detailSrl.setRefreshing(false);
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
