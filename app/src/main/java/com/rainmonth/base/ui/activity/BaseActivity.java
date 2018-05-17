package com.rainmonth.base.ui.activity;

import android.os.Bundle;

import com.rainmonth.R;
import com.rainmonth.common.base.BaseAppCompatActivity;

import butterknife.ButterKnife;

/**
 * Activity 基类
 */
public abstract class BaseActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if (null != mToolbar) {
            initToolbar();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public abstract void initToolbar();

}
