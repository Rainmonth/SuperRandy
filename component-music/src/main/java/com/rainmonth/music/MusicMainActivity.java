package com.rainmonth.music;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.rainmonth.common.adapter.BaseTabViewPagerAdapter;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.music.fragment.DynamicsFragment;
import com.rainmonth.music.fragment.MineFragment;
import com.rainmonth.music.fragment.MusicHallFragment;
import com.rainmonth.music.fragment.RecommendFragment;
import com.rainmonth.music.widget.MusicMiniBar;
import com.rainmonth.router.RouterConstant;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 音乐主页面
 */
@Route(path = RouterConstant.PATH_MUSIC_HOME)
public class MusicMainActivity extends BaseActivity implements View.OnClickListener {

    ViewPager vpMain;
    MusicMiniBar musicMiniBar;
    TabLayout tlMain;

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    BaseTabViewPagerAdapter fragmentAdapter;

    public static final int TAB_MUSIC_HALL = 0;
    public static final int TAB_RECOMMEND = 1;
    public static final int TAB_DYNAMICS = 2;
    public static final int TAB_MINE = 3;

    private int selectTab = TAB_MUSIC_HALL;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.music_activity_home;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewsAndEvents() {
        vpMain = findViewById(R.id.vp_music_main);
        musicMiniBar = findViewById(R.id.mmb_minibar);
        tlMain = findViewById(R.id.tl_music_main);

        initViewPager();
        initTabs();
    }

    private void initViewPager() {
        if (vpMain == null) {
            return;
        }
        fragments.add(MusicHallFragment.newInstance(null));
        fragments.add(RecommendFragment.newInstance(null));
        fragments.add(DynamicsFragment.newInstance(null));
        fragments.add(MineFragment.newInstance(null));

        titles.add(getResources().getString(R.string.music_main_tab_music_hall));
        titles.add(getResources().getString(R.string.music_main_tab_recommend));
        titles.add(getResources().getString(R.string.music_main_tab_music_dynamics));
        titles.add(getResources().getString(R.string.music_main_tab_mine));

        fragmentAdapter = new BaseTabViewPagerAdapter(getSupportFragmentManager(), fragments);
        fragmentAdapter.setTitleList(titles);
        vpMain.setAdapter(fragmentAdapter);
    }

    private void initTabs() {
//        if (tlMain == null) {
//            return;
//        }
//        tlMain.removeAllTabs();
//        TabLayout.Tab tabMusicHall = tlMain.newTab();
//        tabMusicHall.setText("音乐馆");
//
//        tlMain.addTab(tabMusicHall, true);
//
//        TabLayout.Tab tabRecommend = tlMain.newTab();
//        tabRecommend.setText("推荐");
//
//        tlMain.addTab(tabRecommend);
//
//        TabLayout.Tab tabDynamics = tlMain.newTab();
//        tabDynamics.setText("动态");
//
//        tlMain.addTab(tabDynamics);
//
//        TabLayout.Tab tabMine = tlMain.newTab();
//        tabMine.setText("我的");
//        tlMain.addTab(tabMine);

//        if (selectTab == TAB_MUSIC_HALL) {
//            tabMusicHall.select();
//        } else if (selectTab == TAB_RECOMMEND) {
//            tabRecommend.select();
//        } else if (selectTab == TAB_DYNAMICS) {
//            tabDynamics.select();
//        } else if (selectTab == TAB_MINE) {
//            tabMine.select();
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            SmartBarUtils.transparencyStatusBar(this);
//            SmartBarUtils.setLightStatusBar(getWindow(), true);
//        } else {
//            SmartBarUtils.translucentStatusBar(this, false);
//        }

        tlMain.setupWithViewPager(vpMain);
//        tabMusicHall.setIcon(R.drawable.music_main_musichall_tab_selector);
//        tabRecommend.setIcon(R.drawable.music_main_recommend_tab_selector);
//        tabDynamics.setIcon(R.drawable.music_main_dynamics_tab_selector);
//        tabMine.setIcon(R.drawable.music_main_mine_tab_selector);
        int[] icons = {
                R.drawable.music_main_musichall_tab_selector,
                R.drawable.music_main_recommend_tab_selector,
                R.drawable.music_main_dynamics_tab_selector,
                R.drawable.music_main_mine_tab_selector
        };
        if (fragments.size() != icons.length) {
            KLog.e("数据出错，请检查");
            return;
        }
        for (int i = 0; i < fragments.size(); i++) {
            TabLayout.Tab tab = tlMain.getTabAt(i);
            if (tab != null) {
                tab.setIcon(icons[i]);
            }
        }

        tlMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case TAB_RECOMMEND:
                        switchAndShowAndSwitchFragments(TAB_RECOMMEND);
                        break;
                    case TAB_DYNAMICS:
                        switchAndShowAndSwitchFragments(TAB_DYNAMICS);
                        break;
                    case TAB_MINE:
                        switchAndShowAndSwitchFragments(TAB_MINE);
                        break;
                    case TAB_MUSIC_HALL:
                    default:
                        switchAndShowAndSwitchFragments(TAB_MUSIC_HALL);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == TAB_MINE) {
//                    switchAndShowAndSwitchFragments(TAB_MINE);
//                    FragmentUtils.showHide(TAB_MINE, getSupportFragmentManager().getFragments());
                }
            }
        });
    }

    private void switchAndShowAndSwitchFragments(int selectTab) {
        if (vpMain != null && fragments.size() > selectTab) {
            vpMain.setCurrentItem(selectTab, false);
        }
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
