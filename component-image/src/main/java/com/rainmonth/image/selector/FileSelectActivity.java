package com.rainmonth.image.selector;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;

/**
 * 文件选择页面
 * - 支持图片选择
 * - 支持视频选择
 * - 支持apk文件选择
 *
 * @author 张豪成
 * @date 2019-05-24 16:36
 */
public class FileSelectActivity extends BaseActivity {
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_file_select;
    }

    @Override
    protected void initViewsAndEvents() {

    }

}
