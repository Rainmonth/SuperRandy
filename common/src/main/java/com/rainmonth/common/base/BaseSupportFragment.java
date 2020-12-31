package com.rainmonth.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.rainmonth.common.R;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.common.widgets.ProgressHUD;
import com.rainmonth.common.widgets.loading.VaryViewHelperController;
import com.rainmonth.utils.StringUtils;
import com.rainmonth.utils.log.LogUtils;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 用来下沉一些方法，避免业务Fragment重写的方法过多
 * Created by RandyZhang on 2018/5/31.
 */
public abstract class BaseSupportFragment extends Fragment implements IBaseView {
    /**
     * Log tag
     */
    protected String TAG = null;

    protected AppComponent mAppComponent;

    /**
     * context
     */
    protected Context mContext = null;

    private VaryViewHelperController mVaryViewHelperController = null;
    protected Handler mHandler = new Handler();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.i("Randy", TAG);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("Randy", TAG);
        if (isBindEventBusHere()) {
            EventBus.getDefault().register(mContext);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogUtils.i("Randy", TAG);
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        LogUtils.i("Randy", TAG);
        // 初始化AppComponent实例
        mAppComponent = ComponentUtils.getAppComponent();
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.i("Randy", TAG);
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i("Randy", TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i("Randy", TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i("Randy", TAG);
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.i("Randy", TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i("Randy", TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i("Randy", TAG);
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.i("Randy", TAG);
        // for bug ---> java.lang.IllegalStateException: Activity has been destroyed
        try {
            Field childFragmentManager =
                    Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * get loading target view
     */
    protected abstract View getLoadingTargetView();

    /**
     * is bind eventBus
     *
     * @return bind event bus if return true, otherwise not bound
     */
    protected abstract boolean isBindEventBusHere();

    /**
     * when event coming
     *
     * @param eventCenter eventCenter
     */
    protected abstract void onEventComing(EventCenter eventCenter);

    public void onEventMainThread(EventCenter eventCenter) {
        if (null != eventCenter) {
            onEventComing(eventCenter);
        }
    }

    /**
     * get the support fragment manager
     *
     * @return supportFragmentManager
     */
    protected FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    /**
     * startActivity
     *
     * @param clazz activity class to go
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz  activity class to go
     * @param bundle extra info to bound
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param clazz       activity class to go
     * @param requestCode request code for dealing result
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
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
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * show toast
     *
     * @param msg toast msg
     */
    protected void showToast(String msg) {
        if (!StringUtils.isEmpty(msg)) {
            Snackbar.make(((Activity) mContext).getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * toggle show loading
     *
     * @param toggle show if toggle is true, otherwise not show
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
     * @param toggle show if toggle is true, otherwise not show
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
     * @param toggle show if toggle is true, otherwise not show
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
     * @param toggle show if toggle is true, otherwise not show
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

    @Override
    public void toast(String msg) {

    }

    ProgressHUD progressHUD;

    @Override
    public void showProgress() {
        progressHUD = ProgressHUD.show(mContext, "正在加载", R.mipmap.ic_launcher_round, true, null);
    }

    @Override
    public void hideProgress() {
        ProgressHUD.safeDismiss(progressHUD);
    }
}
