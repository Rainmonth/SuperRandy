package com.rainmonth.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.common.utils.NetworkUtils;

import butterknife.BindView;

/**
 * 音乐主页面
 */
public class MusicHomeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_play_music)
    TextView tvPlayMusic;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_music_home;
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
        tvPlayMusic.setOnClickListener(this);
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
        mToolbar.setTitle("音乐主页");
        mToolbar.setLogo(R.drawable.ic_action_bar_logo);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_play_music:
                readyGo(MusicPlayerActivity.class);
                break;
            default:

                break;
        }
    }
}
