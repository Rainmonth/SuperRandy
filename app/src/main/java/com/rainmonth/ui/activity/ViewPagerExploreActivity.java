package com.rainmonth.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.base.ui.activity.BaseActivity;
import com.rainmonth.library.eventbus.EventCenter;
import com.rainmonth.library.utils.DensityUtils;
import com.rainmonth.library.utils.NetworkUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeAnchor;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeRule;

import java.util.Arrays;
import java.util.List;

/**
 * @author RandyZhang
 * @description 图片
 */
public class ViewPagerExploreActivity extends BaseActivity {

    String[] channels = new String[]{"热点", "军事", "情感", "娱乐", "旅行", "社会", "科技", "汽车",
            "财经", "热点", "军事", "情感", "娱乐", "旅行", "社会", "科技", "汽车", "财经"};
    private List<String> mDataList = Arrays.asList(channels);

    private ViewPager mViewPager;
    private PagerAdapter mAdapter = new PagerAdapter() {

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(container.getContext());
            textView.setText(mDataList.get(position));
            textView.setGravity(Gravity.CENTER);
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            TextView textView = (TextView) object;
            String text = textView.getText().toString();
            int index = mDataList.indexOf(text);
            if (index >= 0) {
                return index;
            }
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mDataList.get(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_view_pager_explore;
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
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mAdapter);

        // 当前页始终定位到中间
        final MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        final CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);

                if (index == 3) {
                    TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.simple_count_badge_layout, null);
                    textView.setText("3");
                    badgePagerTitleView.setBadgeView(textView);
                    badgePagerTitleView.setXBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_RIGHT, -DensityUtils.dip2px(context, 6)));
                    badgePagerTitleView.setYBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_TOP, 0));
                }

                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#88ffffff"));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);

                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(Color.parseColor("#40c4ff"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);

        // 动态增加、删除小红点
        commonNavigator.postDelayed(new Runnable() {
            @Override
            public void run() {
                commonNavigator.setReselectWhenLayout(false);

                BadgePagerTitleView badgePagerTitleView = (BadgePagerTitleView) commonNavigator.getPagerTitleView(3);
                badgePagerTitleView.setBadgeView(null);

                BadgePagerTitleView badgePagerTitleView1 = (BadgePagerTitleView) commonNavigator.getPagerTitleView(2);
                TextView textView = (TextView) LayoutInflater.from(badgePagerTitleView1.getContext()).inflate(R.layout.simple_count_badge_layout, null);
                textView.setText("1");
                badgePagerTitleView1.setBadgeView(textView);
                badgePagerTitleView1.setXBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_RIGHT, -DensityUtils.dip2px(badgePagerTitleView1.getContext(), 6)));
                badgePagerTitleView1.setYBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_TOP, 0));

                badgePagerTitleView1.post(new Runnable() {
                    @Override
                    public void run() {
                        commonNavigator.setReselectWhenLayout(true);
                    }
                });
            }
        }, 5000);
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
        mToolbar.setLogo(R.drawable.ic_action_bar_logo);
        mToolbar.setTitle("新闻浏览");
        mToolbar.setBackgroundResource(R.color.bg_home);
    }
}
