package com.rainmonth.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.rainmonth.R;
import com.rainmonth.base.ui.activity.BaseActivity;
import com.rainmonth.library.eventbus.EventCenter;
import com.rainmonth.library.utils.NetworkUtils;

/**
 * 音乐播放界面
 */
public class MusicPlayerActivity extends BaseActivity {

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_music_player;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void onNetworkConnected(NetworkUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void initToolbar() {
        mToolbar.setTitle("音乐播放界面");
    }
}
