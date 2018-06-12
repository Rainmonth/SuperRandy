package com.rainmonth.mvp.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.rainmonth.R;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.di.component.DaggerMainComponent;
import com.rainmonth.di.module.MainModule;
import com.rainmonth.mvp.contract.MainContract;
import com.rainmonth.mvp.presenter.MainPresenter;
import com.rainmonth.mvp.ui.adapter.HomeViewPagerAdapter;
import com.rainmonth.mvp.ui.fragment.RanFragment;
import com.rainmonth.mvp.ui.fragment.RenFragment;
import com.rainmonth.mvp.ui.fragment.XunFragment;
import com.rainmonth.mvp.ui.fragment.YouFragment;
import com.rainmonth.mvp.ui.fragment.ZhuiFragment;
import com.rainmonth.mvp.ui.widgets.NavigationTabBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.vp_horizontal_ntb)
    ViewPager vpHorizontalNtb;
    @BindView(R.id.bg_ntb_horizontal)
    View bgNtbHorizontal;
    @BindView(R.id.ntb_horizontal)
    NavigationTabBar ntbHorizontal;
    @BindView(R.id.fl_ntb_horizontal_container)
    FrameLayout flNtbHorizontalContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViewsAndEvents() {
        mPresenter.init(getNavigationListModels(), getNavigationFragments());

        ntbHorizontal.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset,
                                       final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                ntbHorizontal.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });
    }

    @Override
    public void initToolbar() {
        if (toolbar != null) {
            // 如果采用的时有ActionBar的主题，那么就不能调用这个方法
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("返回");
                }
            });
            mActionBar = getSupportActionBar();
            if (null != mActionBar) {
                mActionBar.setTitle("主页");
                // 设置为true的时候，如果不设置navigationIcon，则显示向左箭头，
                mActionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    @Override
    public void initializeViews(List<NavigationTabBar.Model> models,
                                List<BaseLazyFragment> fragments) {
        vpHorizontalNtb.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager(), fragments));
        ntbHorizontal.setIsBadged(true);
        ntbHorizontal.setModels(models);
        ntbHorizontal.setViewPager(vpHorizontalNtb, 0);

        ntbHorizontal.post(new Runnable() {
            @Override
            public void run() {
                bgNtbHorizontal = findViewById(R.id.bg_ntb_horizontal);
                bgNtbHorizontal.getLayoutParams().height = (int) ntbHorizontal.getBarHeight();
                bgNtbHorizontal.requestLayout();
            }
        });

        ntbHorizontal.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < ntbHorizontal.getModels().size(); i++) {
                    final NavigationTabBar.Model model = ntbHorizontal.getModels().get(i);
                    ntbHorizontal.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    private List<NavigationTabBar.Model> getNavigationListModels() {
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<NavigationTabBar.Model>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[0]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title(getResources().getString(R.string.ren))
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title(getResources().getString(R.string.ran))
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title(getResources().getString(R.string.zhui))
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title(getResources().getString(R.string.xun))
                        .badgeTitle("icon")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[4]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title(getResources().getString(R.string.you))
                        .badgeTitle("icon")
                        .build()
        );

        return models;
    }

    private List<BaseLazyFragment> getNavigationFragments() {
        final ArrayList<BaseLazyFragment> fragments = new ArrayList<BaseLazyFragment>();
        fragments.add(new RenFragment());
        fragments.add(new RanFragment());
        fragments.add(new ZhuiFragment());
        fragments.add(new XunFragment());
        fragments.add(new YouFragment());
        return fragments;
    }
}
