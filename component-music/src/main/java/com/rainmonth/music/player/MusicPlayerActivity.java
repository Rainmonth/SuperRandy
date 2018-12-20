package com.rainmonth.music.player;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.music.R;
import com.rainmonth.router.RouterConstant;

/**
 * 音乐播放界面
 */
@Route(path = RouterConstant.PATH_MUSIC_PLAYER)
public class MusicPlayerActivity extends BaseActivity {

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_activity_player;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    public void initToolbar(int colorResId) {
        if (null != mActionBar) {
            mActionBar.setTitle("音乐播放界面");
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }
}
