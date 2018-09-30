package com.rainmonth.image.mvp.ui.usercenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.base.BaseWebActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;

/**
 * 用户中心页面
 * 功能简介
 * 1.显示当前用户的信息
 * 2.显示我的收藏
 * 3.显示我的图片
 * 4.显示我的合集
 * 5.显示下载中心
 */
public class UserCenterActivity extends BaseActivity {
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_user_center;
    }

    @Override
    protected void initViewsAndEvents() {
        Button downloadBtn = findViewById(R.id.downloadBtn);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(DownloadCenterActivity.class);
            }
        });
        Button collectBtn = findViewById(R.id.collectBtn);
        collectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(UserImageCollectionActivity.class);
            }
        });
        Button focusBtn = findViewById(R.id.focusBtn);
        focusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(UserFocusActivity.class);
            }
        });
        Button infoBtn = findViewById(R.id.infoBtn);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(UserInfoActivity.class);
            }
        });
        Button statisticsBtn = findViewById(R.id.statisticsBtn);
        statisticsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(UserStatisticsActivity.class);
            }
        });
        Button siteBtn = findViewById(R.id.siteBtn);
        siteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(BaseWebActivity.BUNDLE_KEY_URL, "http://rainmonth.cn");
                bundle.putString(BaseWebActivity.BUNDLE_KEY_TITLE, "荏苒追寻");
                readyGo(BaseWebActivity.class, bundle);
            }
        });
        Button clearCacheBtn = findViewById(R.id.clearCacheBtn);
        clearCacheBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("清除缓存");
            }
        });
        Button showSettingBtn = findViewById(R.id.showSettingBtn);
        showSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(SettingImageShowStyleActivity.class);
            }
        });
    }
}
