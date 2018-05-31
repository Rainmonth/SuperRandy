package com.rainmonth.music;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.router.RouterConstant;
import com.rainmonth.router.RouterUtils;

/**
 * 音乐主页面
 */
@Route(path = RouterConstant.PATH_MUSIC_HOME)
public class MusicHomeActivity extends BaseActivity implements View.OnClickListener {

    TextView tvPlayMusic;
    TextView tvGoApp;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_activity_home;
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
        tvPlayMusic = findViewById(R.id.tv_play_music);
        tvGoApp = findViewById(R.id.tv_go_app);
        tvPlayMusic.setOnClickListener(this);
        tvGoApp.setOnClickListener(this);
    }

    @Override
    public void initToolbar() {
        mToolbar.setTitle("音乐主页");
        mToolbar.setLogo(R.drawable.ic_action_bar_logo);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_play_music) {
            readyGo(MusicPlayerActivity.class);
        } else if(id == R.id.tv_go_app) {
            // do nothing
            RouterUtils.getInstance().build(RouterConstant.PATH_APP_HOME).navigation();
        }
    }
}
