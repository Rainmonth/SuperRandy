package com.rainmonth.image.mvp.ui.activity;

import com.rainmonth.common.base.BaseActivity;
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

    }
}
