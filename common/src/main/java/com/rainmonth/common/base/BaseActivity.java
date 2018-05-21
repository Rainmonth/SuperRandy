package com.rainmonth.common.base;

import android.os.Bundle;

/**
 * Activity 基类
 */
public abstract class BaseActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public abstract void initToolbar();

}
