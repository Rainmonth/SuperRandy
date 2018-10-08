package com.rainmonth.image.mvp.ui.photo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.utils.ToastUtils;
import com.rainmonth.common.widgets.PullToRefreshViewPager;
import com.rainmonth.common.widgets.library.PullToRefreshBase;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 照片详情Activity
 * 页面功能简介
 * 1.支持左右滑动来浏览不同的图片，逻辑如下：
 * - 第一页左滑，相当于刷新当前列表；
 * - 最后一页右滑，相当于加载更多；
 * 2.支持图片的收藏，收藏后可以在我的->我的收藏中显示；
 * 3.支持图片的下载，点击下载后，下载任务添加至下载中心，可以在我的->我的下载中查看；
 * 4.支持添加到合集，添加后可以显示在添加的合集中；
 * 5.支持显示图片信息，包括但不限于作者信息、拍摄设备信息（API中都有提供）；
 * 6.支持查看作者信息（点击作者信息，跳转至作者介绍页面）；
 * <p>
 * 注意该页面的数据源可能是来自照片，也可能来自合集
 */
public class PhotoDetailActivity extends BaseActivity<PhotoDetailPresenter>
        implements PullToRefreshBase.OnRefreshListener<ViewPager>, PhotoDetailContract.View {

    private SparseArray<PhotoBean> photoBeans;
    private int currentPage;
    private int currentIndex;
    private int pageSize;
    private long collectionId;
    private String orderBy;
    private String from = Consts.FROM_PHOTO;

    private PullToRefreshViewPager refreshViewPager;
    private ViewPager viewPager;
    private PhotoPagerAdapter photoPagerAdapter;
    private List<PhotoBean> photoBeanList = new ArrayList<>();
    private boolean mIsRequesting = false;
    // todo 这里的判断不准确，后期通过获取响应头中的信息来获取总共的页数
    private boolean isLastPage = false;
    private boolean isAddAtHead = true;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_photo_detail;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        photoBeans = extras.getSparseParcelableArray(Consts.PHOTO_LIST);
        currentPage = extras.getInt(Consts.CURRENT_PAGE);
        currentIndex = extras.getInt(Consts.CURRENT_INDEX);
        pageSize = extras.getInt(Consts.PAGE_SIZE);
        collectionId = extras.getLong(Consts.COLLECTION_ID, -1);
        orderBy = extras.getString(Consts.ORDER_BY);
        from = extras.getString(Consts.FROM, Consts.FROM_PHOTO);
    }

    @Override
    protected void initViewsAndEvents() {
        refreshViewPager = findViewById(R.id.refreshViewPager);
        refreshViewPager.setPullToRefreshOverScrollEnabled(true);
        refreshViewPager.setOnRefreshListener(this);
        refreshViewPager.setMode(PullToRefreshBase.Mode.BOTH);

        viewPager = refreshViewPager.getRefreshableView();
        photoBeanList = convertSparseArrayToList(photoBeans);
        photoPagerAdapter = new PhotoPagerAdapter(getSupportFragmentManager(), photoBeanList);
        viewPager.setAdapter(photoPagerAdapter);
        viewPager.setCurrentItem(currentIndex);
    }

    private List<PhotoBean> convertSparseArrayToList(SparseArray<PhotoBean> photoBeanSparseArray) {
        List<PhotoBean> photoBeanList = new ArrayList<>();
        if (null != photoBeanSparseArray && photoBeanSparseArray.size() > 0) {
            for (int i = 0; i < photoBeanSparseArray.size(); i++) {
                photoBeanList.add(photoBeanSparseArray.get(i));
            }
        }
        return photoBeanList;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerPhotoDetailComponent
                .builder()
                .appComponent(appComponent)
                .photoDetailModule(new PhotoDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    public void onRefresh(PullToRefreshBase<ViewPager> refreshView) {
        if (this.mIsRequesting)
            return;
        if (refreshView.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_END) {//最右
            mIsRequesting = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isLastPage) {
                        isAddAtHead = false;
                        currentPage++;
                        mPresenter.getPrePagePhotos(currentPage, pageSize, collectionId, orderBy, from);
                    } else {
                        ToastUtils.showToast(mContext, "当前已是最后一页数据了");
                        refreshViewPager.onRefreshComplete();
                    }
                    mIsRequesting = false;
                }
            }, 2000);

        } else if (refreshView.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_START) {//最左
            mIsRequesting = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (currentPage > 1) {
                        isAddAtHead = true;
                        currentPage--;
                        mPresenter.getPrePagePhotos(currentPage, pageSize, collectionId, orderBy, from);
                    } else {
                        ToastUtils.showToast(mContext, "当前已是第一页数据了");
                        refreshViewPager.onRefreshComplete();
                    }
                    mIsRequesting = false;
                }
            }, 2000);
        }
    }

    @Override
    public void refreshViewWithPhotos(List<PhotoBean> photoBeans) {
        refreshViewPager.onRefreshComplete();
        if (photoBeans != null && photoBeans.size() > 0) {
            if (photoBeans.size() < pageSize) {
                isLastPage = true;
            }
            photoPagerAdapter.addPhotoList(photoBeans, isAddAtHead);
            photoPagerAdapter.notifyDataSetChanged();
            photoBeanList = photoPagerAdapter.getPhotoBeanList();
            try {
                int currentItem = viewPager.getCurrentItem();
                KLog.e("Randy", "currentItem:" + currentItem);
                if (isAddAtHead) {
                    viewPager.setCurrentItem(currentItem + pageSize - 1, true);
                } else {
                    viewPager.setCurrentItem(currentItem + 1, true);
                }
            } catch (Exception e) {
                KLog.e("Randy", "出错了");
            }
        }
    }
}
