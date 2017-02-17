package com.rainmonth.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.igexin.sdk.PushManager;
import com.rainmonth.R;
import com.rainmonth.ui.adapter.HomeViewPagerAdapter;
import com.rainmonth.base.ui.activity.BaseActivity;
import com.rainmonth.support.getui.DemoPushService;
import com.rainmonth.library.base.BaseLazyFragment;
import com.rainmonth.library.eventbus.EventCenter;
import com.rainmonth.library.utils.NetworkUtils;
import com.rainmonth.presenter.MainPresenter;
import com.rainmonth.view.MainView;
import com.rainmonth.ui.widgets.NavigationTabBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView {

    @Bind(R.id.vp_horizontal_ntb)
    ViewPager vpHorizontalNtb;
    @Bind(R.id.bg_ntb_horizontal)
    View bgNtbHorizontal;
    @Bind(R.id.ntb_horizontal)
    NavigationTabBar ntbHorizontal;
    @Bind(R.id.fl_ntb_horizontal_container)
    FrameLayout flNtbHorizontalContainer;

    MainPresenter mainPresenter = null;

    @Override
    public void initToolbar() {
        mToolbar.setTitle("主页");
        mToolbar.setSubtitle("主页说明");
        mToolbar.setLogo(R.drawable.ic_action_bar_logo);
    }

    @Override
    public void initializeViews(List<NavigationTabBar.Model> models, List<BaseLazyFragment> fragments) {
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
    public void toast(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
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
        printDeviceInfo();
        // 初始化个推
        PushManager.getInstance().initialize(getApplicationContext(), DemoPushService.class);
        ButterKnife.bind(this);

        /**
         * 关于原有广播方式和新的IntentService方式兼容性说明：
         * 1. 如果调用了registerPushIntentService方法注册自定义IntentService，则SDK仅通过IntentService回调推送服务事件；
         * 2. 如果未调用registerPushIntentService方法进行注册，则原有的广播接收器仍然可以继续使用。
         */
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), com.rainmonth.support.getui.DemoIntentService.class);
        String cid = PushManager.getInstance().getClientid(getApplicationContext());
        if (cid != null) {
            Log.e("client id=", cid);
        }
        mainPresenter = new MainPresenter(this);
        mainPresenter.initialize();
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
}
