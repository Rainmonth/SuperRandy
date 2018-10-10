package com.rainmonth.image.mvp.ui.search;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.ui.photo.PhotoPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索结果页
 * 功能简介
 * 展示搜索结果，包括：
 * - 图片搜索结果
 * - 合集搜索结果
 * - 用户搜索结果
 * <p>
 * 图片搜索结果，支持浏览照片详情，支持左右滑动查看，由于搜索结果列表长度有限，浏览完成后给与提示；
 * 合集搜索结果，支持查看合集详情，支持左右滑动查看，当前合集查看完成后，继续查看下一个合集；
 */
public class SearchResultActivity extends BaseActivity<SearchResultPresenter> implements SearchResultContract.View {
    private TabLayout resultTabLayout;
    private ViewPager resultViewPager;
    private BaseLazyFragment photoResultFragment;
    private BaseLazyFragment collectionResultFragment;
    private BaseLazyFragment userResultFragment;

    private String searchKeys;
    private SearchResultPagerAdapter adapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_search_result;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (null != extras) {
            searchKeys = extras.getString(Consts.SEARch_KEY);
        }
    }

    @Override
    protected void initViewsAndEvents() {
        List<Fragment> fragmentList = new ArrayList<>();
        photoResultFragment = PhotoSearchResultFragment.getInstance(searchKeys);
        collectionResultFragment = CollectionSearchResultFragment.getInstance(searchKeys);
        userResultFragment = UserSearchResultFragment.getInstance(searchKeys);

        fragmentList.add(photoResultFragment);
        fragmentList.add(collectionResultFragment);
        fragmentList.add(userResultFragment);

        adapter = new SearchResultPagerAdapter(getSupportFragmentManager(), fragmentList);
        resultTabLayout = findViewById(R.id.resultTabLayout);
        resultTabLayout.addTab(resultTabLayout.newTab().setText("照片"));
        resultTabLayout.addTab(resultTabLayout.newTab().setText("合集"));
        resultTabLayout.addTab(resultTabLayout.newTab().setText("用户"));
        resultViewPager = findViewById(R.id.resultViewPager);
        resultViewPager.setAdapter(adapter);
        resultTabLayout.setupWithViewPager(resultViewPager);


    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSearchResultComponent
                .builder()
                .appComponent(appComponent)
                .searchResultModule(new SearchResultModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    public <T> void initViewWithSearchResult(SearchResult<T> searchResult) {

    }
}
