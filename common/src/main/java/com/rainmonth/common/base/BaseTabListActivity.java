package com.rainmonth.common.base;

import com.rainmonth.common.R;

/**
 * 带Tab和ViewPager的activity
 *
 * @author 张豪成
 * @date 2019-11-06 11:10
 */
public class BaseTabListActivity extends BaseActivity {

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.common_base_tab_list_activity;
    }

    @Override
    protected void initViewsAndEvents() {

    }
}
