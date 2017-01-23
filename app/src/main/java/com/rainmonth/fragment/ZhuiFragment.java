package com.rainmonth.fragment;

import android.view.View;

import com.rainmonth.R;
import com.rainmonth.library.base.BaseLazyFragment;
import com.rainmonth.library.eventbus.EventCenter;

/**
 * Created by RandyZhang on 16/6/30.
 */
public class ZhuiFragment extends BaseLazyFragment {
    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public int getContentViewLayoutID() {
        return  R.layout.fragment_zhui;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents(View view) {

    }
}
