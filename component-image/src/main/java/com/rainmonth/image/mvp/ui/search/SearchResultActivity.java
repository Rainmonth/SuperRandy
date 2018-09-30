package com.rainmonth.image.mvp.ui.search;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;

/**
 * 搜索结果页
 * 功能简介
 * 展示搜索结果，包括：
 * - 图片搜索结果
 * - 合集搜索结果
 * - 用户搜索结果
 *
 * 图片搜索结果，支持浏览照片详情，支持左右滑动查看，由于搜索结果列表长度有限，浏览完成后给与提示；
 * 合集搜索结果，支持查看合集详情，支持左右滑动查看，当前合集查看完成后，继续查看下一个合集；
 */
public class SearchResultActivity extends BaseActivity {
    private TabLayout resultTabLayout;
    private ViewPager resultViewPager;
    private BaseLazyFragment photoResultFragment;
    private BaseLazyFragment collectionResultFragment;
    private BaseLazyFragment lazyResultFragment;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_search_result;
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
