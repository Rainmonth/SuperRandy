package com.rainmonth.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.widgets.NavigationTabBar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.vp_horizontal_ntb)
    ViewPager vpHorizontalNtb;
    @Bind(R.id.bg_ntb_horizontal)
    View bgNtbHorizontal;
    @Bind(R.id.ntb_horizontal)
    NavigationTabBar ntbHorizontal;
    @Bind(R.id.fl_ntb_horizontal_container)
    FrameLayout flNtbHorizontalContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        vpHorizontalNtb = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        vpHorizontalNtb.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view = LayoutInflater.from(
                        getBaseContext()).inflate(R.layout.item_vp, null, false);

                final TextView txtPage = (TextView) view.findViewById(R.id.txt_vp_item_page);
                txtPage.setText(String.format("Page #%d", position));

                container.addView(view);
                return view;
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        ntbHorizontal = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        ntbHorizontal.setIsBadged(false);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[0]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("荏")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("苒")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("追")
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("寻")
                        .badgeTitle("icon")
                        .build()
        );

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
