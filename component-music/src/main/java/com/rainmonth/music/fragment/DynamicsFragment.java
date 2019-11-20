package com.rainmonth.music.fragment;

import android.os.Bundle;
import android.view.View;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.music.R;

/**
 * 推荐Fragment
 */
public class DynamicsFragment extends BaseLazyFragment {

    public static DynamicsFragment newInstance(Bundle args) {
        DynamicsFragment fragment = new DynamicsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_fragment_dynamics;
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
}
