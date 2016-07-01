package com.rainmonth.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.igexin.sdk.PushManager;
import com.rainmonth.HomeViewPagerAdapter;
import com.rainmonth.R;
import com.rainmonth.fragment.BaseLazyFragment;
import com.rainmonth.presenter.BasePresenter;
import com.rainmonth.presenter.MainPresenter;
import com.rainmonth.view.MainView;
import com.rainmonth.widgets.NavigationTabBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView{

    @Bind(R.id.vp_horizontal_ntb)
    ViewPager vpHorizontalNtb;
    @Bind(R.id.bg_ntb_horizontal)
    View bgNtbHorizontal;
    @Bind(R.id.ntb_horizontal)
    NavigationTabBar ntbHorizontal;
    @Bind(R.id.fl_ntb_horizontal_container)
    FrameLayout flNtbHorizontalContainer;

    BasePresenter mainPresenter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化个推
        PushManager.getInstance().initialize(getApplicationContext());
        ButterKnife.bind(this);

        mainPresenter = new MainPresenter(this, this);
        mainPresenter.initialize();
    }

    @Override
    public void initializeViews(List<NavigationTabBar.Model> models, List<BaseLazyFragment> fragments) {
        vpHorizontalNtb = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        ntbHorizontal = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        vpHorizontalNtb.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager(), fragments));
        ntbHorizontal.setIsBadged(true);
        ntbHorizontal.setModels(models);
        ntbHorizontal.setViewPager(vpHorizontalNtb, 2);
        ntbHorizontal.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                ntbHorizontal.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

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
}
