package com.rainmonth.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IntDef;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.rainmonth.common.R;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.common.netstatus.NetChangeObserver;
import com.rainmonth.common.netstatus.NetStateReceiver;
import com.rainmonth.common.utils.CommonUtils;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.common.utils.NetworkUtils;
import com.rainmonth.common.utils.SmartBarUtils;
import com.rainmonth.common.utils.constant.StatusBarConstants;
import com.rainmonth.common.utils.log.LogUtils;
import com.rainmonth.common.widgets.loading.VaryViewHelperController;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public abstract class BaseAppCompatActivity extends AppCompatActivity {
    public static final int TRANSITION_MODE_LEFT = 0;
    public static final int TRANSITION_MODE_RIGHT = 1;
    public static final int TRANSITION_MODE_TOP = 2;
    public static final int TRANSITION_MODE_BOTTOM = 3;
    public static final int TRANSITION_MODE_SCALE = 4;
    public static final int TRANSITION_MODE_FADE = 5;

    /**
     * Log tag
     */
    protected static String TAG = null;

    protected AppComponent mAppComponent;
    /**
     * context
     */
    protected Context mContext = null;
    protected Activity mActivity;
    // 状态栏颜色
    protected int mStatusBarColor;

    protected ActionBar mActionBar = null;

    /**
     * network status
     */
    protected NetChangeObserver mNetChangeObserver = null;

    /**
     * loading view controller
     */
    private VaryViewHelperController mVaryViewHelperController = null;

    /**
     * overridePendingTransition mode
     */
    @IntDef({TRANSITION_MODE_LEFT, TRANSITION_MODE_RIGHT,
            TRANSITION_MODE_TOP, TRANSITION_MODE_BOTTOM,
            TRANSITION_MODE_SCALE, TRANSITION_MODE_FADE})
    @interface TransitionMode {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (toggleOverridePendingTransition()) {
            if (getOverridePendingTransitionMode() >= 0) {
                switch (getOverridePendingTransitionMode()) {
                    case TRANSITION_MODE_LEFT:
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        break;
                    case TRANSITION_MODE_RIGHT:
                        overridePendingTransition(R.anim.right_in, R.anim.right_out);
                        break;
                    case TRANSITION_MODE_TOP:
                        overridePendingTransition(R.anim.top_in, R.anim.top_out);
                        break;
                    case TRANSITION_MODE_BOTTOM:
                        overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                        break;
                    case TRANSITION_MODE_SCALE:
                        overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                        break;
                    case TRANSITION_MODE_FADE:
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                }
            }
        }
        try {
            super.onCreate(savedInstanceState);
        } catch (Throwable e) {
            LogUtils.printStackTrace(e);
        }
        // base setup
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        if (isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
        TAG = this.getClass().getSimpleName();
//        SmartBarUtils.hide(getWindow().getDecorView());
        setTranslucentStatus(isApplyStatusBarTranslucency());
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
            // 以代码的形式为每个Activity对应的布局文件添加fitsSystemWindows属性
            ViewGroup contentFrameLayout = findViewById(Window.ID_ANDROID_CONTENT);
            assert contentFrameLayout != null;
            View parentView = contentFrameLayout.getChildAt(0);
            if (parentView != null && Build.VERSION.SDK_INT >= 14) {
                parentView.setFitsSystemWindows(true);
            }
        } else if (getContentViewLayoutID() == -1) {
            LogUtils.d(TAG, "this activity is without layout resource!");
        } else {
            throw new IllegalArgumentException("You must return a right contentView " +
                    "layout resource Id");
        }

        mContext = this;
        mActivity = this;
        mAppComponent = ComponentUtils.getAppComponent();
        mStatusBarColor = mAppComponent.statusBarAttr().get(StatusBarConstants.COLOR);
        if (isChangeStatusBarColor()) {
            SmartBarUtils.setStatusBarColor(this, mStatusBarColor);
        }
        mAppComponent.appManager().addActivity(this);

        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetworkUtils.NetType type) {
                super.onNetConnected(type);
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                super.onNetDisConnect();
                onNetworkDisConnected();
            }
        };

        NetStateReceiver.registerObserver(mNetChangeObserver);

//        initViewsAndEvents();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (-1 != layoutResID) {
            super.setContentView(layoutResID);
        }
        ButterKnife.bind(this);
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
    }

    @Override
    public void finish() {
        try {
            super.finish();
        } catch (Throwable e) {
            LogUtils.printStackTrace(e);
        }
        mAppComponent.appManager().removeActivity(this);
        if (toggleOverridePendingTransition()) {
            if (getOverridePendingTransitionMode() >= 0) {
                switch (getOverridePendingTransitionMode()) {
                    case TRANSITION_MODE_LEFT:
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        break;
                    case TRANSITION_MODE_RIGHT:
                        overridePendingTransition(R.anim.right_in, R.anim.right_out);
                        break;
                    case TRANSITION_MODE_TOP:
                        overridePendingTransition(R.anim.top_in, R.anim.top_out);
                        break;
                    case TRANSITION_MODE_BOTTOM:
                        overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                        break;
                    case TRANSITION_MODE_SCALE:
                        overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                        break;
                    case TRANSITION_MODE_FADE:
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
        } catch (Throwable e) {
            LogUtils.printStackTrace(e);
        } finally {
            NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
            if (isBindEventBusHere()) {
                EventBus.getDefault().unregister(this);
            }
        }

    }

    /**
     * get bundle data
     *
     * @param extras bound info in the intent
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * when event coming
     *
     * @param eventCenter event center
     */
    protected abstract void onEventComing(EventCenter eventCenter);

    /**
     * get loading target view
     */
    protected abstract View getLoadingTargetView();

    /**
     * init all views and add events
     */
    protected abstract void initViewsAndEvents();

    /**
     * network connected
     */
    protected abstract void onNetworkConnected(NetworkUtils.NetType type);

    /**
     * network disconnected
     */
    protected abstract void onNetworkDisConnected();

    /**
     * is applyStatusBarTranslucency
     *
     * @return true if applyStatusBarTranslucency
     */
    protected abstract boolean isApplyStatusBarTranslucency();

    protected abstract boolean isChangeStatusBarColor();

    /**
     * is bind eventBus
     *
     * @return true if bind event bus
     */
    protected abstract boolean isBindEventBusHere();

    /**
     * toggle overridePendingTransition
     *
     * @return true if apply overridePendingTransition
     */
    protected abstract boolean toggleOverridePendingTransition();

    /**
     * get the overridePendingTransition mode
     *
     * @return the TransitionMode used
     */
    protected abstract int getOverridePendingTransitionMode();

    /**
     * startActivity
     *
     * @param clazz activity class to go
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz  activity class go to
     * @param bundle extra info to bound
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz activity class to go
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz  activity class to go
     * @param bundle extra info to bound
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz       activity class to go
     * @param requestCode request code for dealing result
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz       activity class to go
     * @param requestCode request code for dealing result
     * @param bundle      extra info to bound
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * show toast
     *
     * @param msg msg to show
     */
    protected void showToast(String msg) {
        //防止遮盖虚拟按键
        if (null != msg && !CommonUtils.isEmpty(msg)) {
            if (getLoadingTargetView() != null) {
                Snackbar.make(getLoadingTargetView(), msg, Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * toggle show loading
     *
     * @param toggle true if show loading indicator
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showLoading(msg);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show empty
     *
     * @param toggle true if show empty indicator
     */
    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showEmpty(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show error
     *
     * @param toggle          true if show error indicator
     * @param msg             msg to show
     * @param onClickListener listener react to error view click event
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show network error
     *
     * @param toggle          true if show network error
     * @param onClickListener listener react to network error view click event
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * @param eventCenter event center
     */
    public void onEventMainThread(EventCenter eventCenter) {
        if (null != eventCenter) {
            onEventComing(eventCenter);
        }
    }

    /**
     * use SystemBarTintManager
     *
     * @param tintDrawable tint drawable used
     */
    protected void setSystemBarTintDrawable(Drawable tintDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }
    }

    /**
     * set status bar translucency
     *
     * @param on true if apply translucent status
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }
}
