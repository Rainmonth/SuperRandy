package com.rainmonth.image.mvp.ui.usercenter;

import android.os.Bundle;
import android.view.View;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;

/**
 * 用户的图片
 */
public class UserPhotosFragment extends BaseLazyFragment {

    public static UserPhotosFragment newInstance(String username) {
        UserPhotosFragment fragment = new UserPhotosFragment();
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
        return R.layout.image_fragment_user_photos;
    }
}
