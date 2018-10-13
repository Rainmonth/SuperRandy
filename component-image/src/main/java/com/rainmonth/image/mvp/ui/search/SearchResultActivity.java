package com.rainmonth.image.mvp.ui.search;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.widgets.ProgressHUD;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SearchBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.model.bean.UserBean;

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
 * <p>
 * 注意：
 * ViewPager和TagLayout配合使用时，如果要显示标题，要重写ViewPager的adapter的getPageTitle方法
 */
public class SearchResultActivity extends BaseActivity<SearchResultPresenter> implements SearchResultContract.View {
    private TabLayout resultTabLayout;
    private ViewPager resultViewPager;
    private PhotoSearchResultFragment photoResultFragment;
    private CollectionSearchResultFragment collectionResultFragment;
    private UserSearchResultFragment userResultFragment;

    private String searchKeys;
    private SearchResultPagerAdapter adapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_search_result;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (null != extras) {
            searchKeys = extras.getString(Consts.SEARCH_KEY);
        }
    }

    @Override
    protected void initViewsAndEvents() {
        resultTabLayout = findViewById(R.id.resultTabLayout);
        resultViewPager = findViewById(R.id.resultViewPager);

        resultTabLayout.setupWithViewPager(resultViewPager);
        mPresenter.search("", searchKeys, 1, 10, "", "");
        showProgress();
    }

    ProgressHUD progressHUD;

    @Override
    public void showProgress() {
        progressHUD = ProgressHUD.show(mContext, "正在加载", R.mipmap.image_ic_launcher_round, true, null);
    }

    @Override
    public void hideProgress() {
        ProgressHUD.safeDismiss(progressHUD);
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
    public void initSearchResult(SearchBean<PhotoBean, CollectionBean, UserBean> searchBean) {
        hideProgress();
        photoResultFragment = PhotoSearchResultFragment.getInstance(searchKeys);
        collectionResultFragment = CollectionSearchResultFragment.getInstance(searchKeys);
        userResultFragment = UserSearchResultFragment.getInstance(searchKeys);

        photoResultFragment.setPhotoSearchResult(searchBean.getPhotos());
        collectionResultFragment.setCollectionSearchResult(searchBean.getCollections());
        userResultFragment.setUserSearchResult(searchBean.getUsers());
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> tagTitles = new ArrayList<>();

        fragmentList.add(photoResultFragment);
        fragmentList.add(collectionResultFragment);
        fragmentList.add(userResultFragment);

        if (searchBean.getPhotos() != null && searchBean.getPhotos().getTotal() > 0) {
            tagTitles.add("照片-" + searchBean.getPhotos().getTotal());
        } else {
            tagTitles.add("照片");
        }
        if (searchBean.getCollections() != null && searchBean.getCollections().getTotal() > 0) {
            tagTitles.add("合集-" + searchBean.getCollections().getTotal());
        } else {
            tagTitles.add("合集");
        }
        if (searchBean.getUsers() != null && searchBean.getUsers().getTotal() > 0) {
            tagTitles.add("用户-" + searchBean.getUsers().getTotal());
        } else {
            tagTitles.add("用户");
        }

        adapter = new SearchResultPagerAdapter(getSupportFragmentManager(), fragmentList);
        adapter.setTitleList(tagTitles);

        if (fragmentList.size() == tagTitles.size()) {
            for (int i = 0; i < fragmentList.size(); i++) {
                resultTabLayout.addTab(resultTabLayout.newTab().setText(tagTitles.get(i)));
            }
        }

        resultViewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public <T> void initViewWithSearchResult(SearchResult<T> searchResult) {

    }

    @Override
    public void showError(String message) {
        hideProgress();
    }
}
