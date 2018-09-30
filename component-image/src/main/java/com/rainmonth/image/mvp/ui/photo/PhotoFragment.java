package com.rainmonth.image.mvp.ui.photo;

import android.os.Bundle;
import android.view.View;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

public class PhotoFragment extends BaseLazyFragment {

    public static PhotoFragment getInstance(PhotoBean photoBean) {
        PhotoFragment photoFragment = new PhotoFragment();
        Bundle localBundle = new Bundle();
        localBundle.putSerializable(Consts.PHOTO_BEAN, photoBean);
        photoFragment.setArguments(localBundle);
        return photoFragment;
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
        return 0;
    }
}
