package com.rainmonth.player;

import android.os.Handler;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.utils.PermissionUtils;
import com.rainmonth.common.utils.constant.PermissionConstants;
import com.rainmonth.common.utils.log.LogUtils;
import com.rainmonth.player.activity.VideoMainActivity;

/**
 * 播放器闪屏页
 *
 * @author 张豪成
 * @date 2019/11/05
 */
public class PlayerSplashActivity extends BaseActivity {
    private final static String TAG = PlayerSplashActivity.class.getSimpleName();

    private Handler mHandler;

    @Override
    protected int getContentViewLayoutID() {
        return -1;
    }

    @Override
    protected void initViewsAndEvents() {
        mHandler = new Handler();
        PermissionUtils.permission(PermissionConstants.STORAGE).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                LogUtils.d(TAG, "onGranted: " + PermissionConstants.STORAGE);
                mHandler.postDelayed(() -> {
                    readyGoThenKill(VideoMainActivity.class);
                }, 3000);
            }

            @Override
            public void onDenied() {
                LogUtils.d(TAG, "onDenied: " + PermissionConstants.STORAGE);
                mHandler.postDelayed(() -> {
                    readyGoThenKill(VideoMainActivity.class);
                }, 3000);
            }
        }).request();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }
}
