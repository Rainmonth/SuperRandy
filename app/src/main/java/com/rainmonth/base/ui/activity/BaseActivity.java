package com.rainmonth.base.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rainmonth.R;

import butterknife.ButterKnife;

/**
 *  Activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected String Tag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewLayoutId() != 0) {
            setContentView(getContentViewLayoutId());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        ButterKnife.bind(this);
        mContext = this;
        Tag = this.getClass().getSimpleName();
        initViewsAndEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public abstract boolean isApplyTranslucentStatusBar();

    public abstract int getContentViewLayoutId();

    public abstract void initViewsAndEvent();


    /**
     * startActivity
     *
     * @param clazz class to start
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(mContext, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz class to start
     * @param bundle bundle to transport
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(mContext, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param clazz class to start
     * @param requestCode request code
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(mContext, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz class to start
     * @param requestCode request code
     * @param bundle bundle to transport
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(mContext, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


}
