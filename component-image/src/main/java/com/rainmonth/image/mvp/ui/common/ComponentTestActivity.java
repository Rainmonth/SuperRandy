package com.rainmonth.image.mvp.ui.common;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;

/**
 * @author 张豪成
 * @date 2019-07-24 09:53
 */
public class ComponentTestActivity extends BaseActivity {
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_component_test;
    }

    @Override
    protected void initViewsAndEvents() {

    }
}
