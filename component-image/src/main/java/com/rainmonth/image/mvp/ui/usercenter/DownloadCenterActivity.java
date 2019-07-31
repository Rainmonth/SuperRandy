package com.rainmonth.image.mvp.ui.usercenter;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.mvp.ui.search.CommonPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 下载中心页面
 * 功能简介
 * 1.显示正在下载的内容
 * 2.显示已完成的内容
 * 3.已完成的内容支持浏览，浏览逻辑同普通照片的浏览，
 */
public class DownloadCenterActivity extends BaseActivity {

    private TabLayout tlDownloadCenter;
    private ViewPager vpDownloadCenter;
    private BaseLazyFragment downloadingFrag, downloadedFrag;

    private List<Fragment> fragmentList;
    private List<String> tagTitles;
    private CommonPagerAdapter adapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_download_center;
    }

    @Override
    protected void initViewsAndEvents() {
        tlDownloadCenter = findViewById(R.id.tl_download_center);
        vpDownloadCenter = findViewById(R.id.vp_download_center);
        // todo
        String username = "rainmonth";
        downloadingFrag = DownloadingFragment.newInstance(username);
        downloadedFrag = DownloadedFragment.newInstance(username);

        fragmentList = new ArrayList<>();
        tagTitles = new ArrayList<>();

        fragmentList.add(downloadingFrag);
        fragmentList.add(downloadedFrag);

        tagTitles.add("正在下载");
        tagTitles.add("下载完成");

        if (fragmentList.size() == tagTitles.size()) {
            for (int i = 0; i < fragmentList.size(); i++) {
                tlDownloadCenter.addTab(tlDownloadCenter.newTab().setText(tagTitles.get(i)));
            }
        }

        adapter = new CommonPagerAdapter(getSupportFragmentManager(), fragmentList);
        adapter.setTitleList(tagTitles);
        tlDownloadCenter.setupWithViewPager(vpDownloadCenter);
        vpDownloadCenter.setAdapter(adapter);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }
}
