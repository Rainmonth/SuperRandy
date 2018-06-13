package com.rainmonth.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.rainmonth.common.di.component.AppComponent;

/**
 * Created by RandyZhang on 2018/5/31.
 */
public abstract class BaseFragment extends BaseSupportFragment {

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupFragmentComponent(mAppComponent);
        initViewsAndEvents(view);
    }

    protected abstract void setupFragmentComponent(AppComponent appComponent);

    protected abstract void initViewsAndEvents(View view);

    /**
     * get loading target view
     */
    protected abstract View getLoadingTargetView();
}
