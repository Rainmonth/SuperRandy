package com.rainmonth.image.mvp.ui.photo;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;

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
 */
public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_photo_detail;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }
}
