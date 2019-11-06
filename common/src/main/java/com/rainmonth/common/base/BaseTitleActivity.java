package com.rainmonth.common.base;

import com.rainmonth.common.R;

/**
 * 带标题栏的Activity
 *
 * @author 张豪成
 * @date 2019-11-06 11:14
 */
public class BaseTitleActivity extends BaseActivity {
    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.common_base_title_activity;
    }

    @Override
    protected void initViewsAndEvents() {

    }
}
