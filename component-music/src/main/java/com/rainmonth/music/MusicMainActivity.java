package com.rainmonth.music;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.music.audioplayer.AudioPlayerActivity;
import com.rainmonth.router.RouterConstant;
import com.rainmonth.router.RouterUtils;

/**
 * 音乐主页面
 */
@Route(path = RouterConstant.PATH_MUSIC_HOME)
public class MusicMainActivity extends BaseActivity implements View.OnClickListener {

    TextView tvPlayMusic;
    TextView tvGoApp;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_activity_home;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewsAndEvents() {
        tvPlayMusic = findViewById(R.id.tv_play_music);
        tvGoApp = findViewById(R.id.tv_go_app);
        tvPlayMusic.setOnClickListener(this);
        tvGoApp.setOnClickListener(this);
    }

    @Override
    public void initToolbar(int colorResId) {
        if (null != mActionBar) {
            mActionBar.setTitle("音乐主页");
            mActionBar.setLogo(R.drawable.ic_action_bar_logo);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_play_music) {
            readyGo(AudioPlayerActivity.class);
        } else if (id == R.id.tv_go_app) {
            // do nothing
            RouterUtils.getInstance().build(RouterConstant.PATH_APP_HOME).navigation();
        }
    }
}
