package com.rainmonth.image.mvp.ui.usercenter;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;

/**
 * 用户照片和合集展示页
 * 功能简介
 * - 展示用户的照片和合集（包括当前用户和其他用户）传递参数用户名即可
 * - 图片和合集支持通用浏览操作
 */
public class UserImageCollectionActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_user_image_collection;
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
