package com.rainmonth.image.mvp.ui.usercenter;

import android.os.Bundle;
import android.view.View;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;

public class DownloadingFragment extends BaseLazyFragment {

    public static DownloadingFragment newInstance(String username) {
        DownloadingFragment fragment = new DownloadingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Consts.USER_NAME, username);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewsAndEvents(View view) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_fragment_downloading;
    }
}
