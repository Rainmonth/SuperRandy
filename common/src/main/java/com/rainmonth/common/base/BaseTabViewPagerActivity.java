package com.rainmonth.common.base;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rainmonth.common.R;
import com.rainmonth.common.adapter.BaseTabViewPagerAdapter;
import com.rainmonth.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张豪成
 * @date 2019-11-20 15:05
 */
public abstract class BaseTabViewPagerActivity extends BaseCleanActivity {

    ViewPager vpMain;
    TabLayout tlMain;

    protected List<Fragment> fragments = new ArrayList<>();
    protected List<String> titles = new ArrayList<>();
    protected List<Integer> icons = new ArrayList<>();
    protected BaseTabViewPagerAdapter fragmentAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.common_base_tab_view_pager_activity;
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();

        vpMain = findViewById(R.id.vp_music_main);
        tlMain = findViewById(R.id.tl_music_main);

        bindFragmentsAndTitles();
        initViewPager();

        if (fragments.size() != titles.size()) {
            LogUtils.e("数据出错，请检查");
            return;
        }

        if (isNeedShowTabIcon()) {
            if (titles.size() != icons.size()) {
                LogUtils.e("数据出错，请检查");
                return;
            }
        }

        if (fragments.size() == 0) {
            LogUtils.e("数据为空，请检查");
            return;
        }
        for (int i = 0; i < fragments.size(); i++) {
            TabLayout.Tab tab = tlMain.getTabAt(i);
            if (tab != null) {
                tab.setText(titles.get(i));
                if (isNeedShowTabIcon()) {
                    tab.setIcon(icons.get(i));
                }
            }

        }
    }

    private void initViewPager() {
        fragmentAdapter = new BaseTabViewPagerAdapter(getSupportFragmentManager(), fragments);
        fragmentAdapter.setTitleList(titles);
        vpMain.setAdapter(fragmentAdapter);
        tlMain.setupWithViewPager(vpMain);
    }

    /**
     * 是否需要显示tabIcon
     *
     * @return true if need show tabIcon, default is false
     */
    protected boolean isNeedShowTabIcon() {
        return false;
    }

    public abstract void bindFragmentsAndTitles();

}
