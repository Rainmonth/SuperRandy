package com.rainmonth.music;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.rainmonth.common.adapter.BaseViewPagerAdapter;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.componentbase.ServiceFactory;
import com.rainmonth.music.audioplayer.AudioPlayerActivity;
import com.rainmonth.music.fragment.DynamicsFragment;
import com.rainmonth.music.fragment.MineFragment;
import com.rainmonth.music.fragment.MusicHallFragment;
import com.rainmonth.music.fragment.RecommendFragment;
import com.rainmonth.router.RouterConstant;
import com.rainmonth.router.RouterUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 音乐主页面
 */
@Route(path = RouterConstant.PATH_MUSIC_HOME)
public class MusicMainActivity extends BaseActivity implements View.OnClickListener {

    ViewPager vpMainMusic;
    RelativeLayout rlMusicController;
    TabLayout tlMainMusic;

    List<BaseLazyFragment> fragments = new ArrayList<>();
    BaseViewPagerAdapter<BaseLazyFragment> fragmentAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_activity_home;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewsAndEvents() {
        vpMainMusic = findViewById(R.id.vp_music_main);
        rlMusicController = findViewById(R.id.rl_music_controller_container);
        tlMainMusic = findViewById(R.id.tl_music_main);

        fragments.add(MusicHallFragment.newInstance(null));
        fragments.add(RecommendFragment.newInstance(null));
        fragments.add(DynamicsFragment.newInstance(null));
        fragments.add(MineFragment.newInstance(null));

        fragmentAdapter = new BaseViewPagerAdapter<>(getSupportFragmentManager(), fragments);
        vpMainMusic.setAdapter(fragmentAdapter);
        tlMainMusic.setupWithViewPager(vpMainMusic);
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

    }
}
