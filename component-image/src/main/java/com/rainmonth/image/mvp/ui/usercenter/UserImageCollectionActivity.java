package com.rainmonth.image.mvp.ui.usercenter;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.mvp.ui.search.CommonPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户照片和合集展示页
 * 功能简介
 * - 展示用户的照片和合集（包括当前用户和其他用户）传递参数用户名即可
 * - 图片和合集支持通用浏览操作
 */
public class UserImageCollectionActivity extends BaseActivity {
    private TabLayout tlUserContents;
    private ViewPager vpUserContents;
    private UserLikePhotosFragment userLikePhotosFragment;
    private UserPhotosFragment userPhotosFragment;
    private UserCollectionsFragment userCollectionsFragment;
    private List<Fragment> fragmentList;
    private List<String> tagTitles;
    private CommonPagerAdapter adapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_user_image_collection;
    }

    @Override
    protected void initViewsAndEvents() {
        tlUserContents = findViewById(R.id.tl_user_contents);
        vpUserContents = findViewById(R.id.vp_user_contents);
        String username = "rainmonth";

        userLikePhotosFragment = UserLikePhotosFragment.newInstance(username);
        userPhotosFragment = UserPhotosFragment.newInstance(username);
        userCollectionsFragment = UserCollectionsFragment.newInstance(username);

        fragmentList = new ArrayList<>();
        tagTitles = new ArrayList<>();

        fragmentList.add(userLikePhotosFragment);
        fragmentList.add(userPhotosFragment);
        fragmentList.add(userCollectionsFragment);

        tagTitles.add("我喜欢的");
        tagTitles.add("我的");
        tagTitles.add("我的合集");

        if (fragmentList.size() == tagTitles.size()) {
            for (int i = 0; i < fragmentList.size(); i++) {
                tlUserContents.addTab(tlUserContents.newTab().setText(tagTitles.get(i)));
            }
        }

        adapter = new CommonPagerAdapter(getSupportFragmentManager(), fragmentList);
        adapter.setTitleList(tagTitles);
        tlUserContents.setupWithViewPager(vpUserContents);
        vpUserContents.setAdapter(adapter);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }
}
