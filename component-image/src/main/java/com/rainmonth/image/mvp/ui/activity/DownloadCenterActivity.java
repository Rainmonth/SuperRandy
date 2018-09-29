package com.rainmonth.image.mvp.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;

/**
 * 下载中心页面
 * 功能简介
 * 1.显示正在下载的内容
 * 2.显示已完成的内容
 * 3.已完成的内容支持浏览，浏览逻辑同普通照片的浏览，
 */
public class DownloadCenterActivity extends BaseActivity {

    private TabLayout downloadTabLayout;
    private ViewPager downloadViewPager;
    private BaseLazyFragment downloadingFrag, downloadedFrag;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_download_center;
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
