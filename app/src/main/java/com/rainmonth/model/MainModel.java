package com.rainmonth.model;

import android.graphics.Color;

import com.rainmonth.R;
import com.rainmonth.ui.fragment.RanFragment;
import com.rainmonth.ui.fragment.RenFragment;
import com.rainmonth.ui.fragment.XunFragment;
import com.rainmonth.ui.fragment.YouFragment;
import com.rainmonth.ui.fragment.ZhuiFragment;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.support.tinker.util.SuperRandyApplicationContext;
import com.rainmonth.ui.widgets.NavigationTabBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyZhang on 16/7/1.
 */
public class MainModel implements IMainModel {

    @Override
    public List<BaseLazyFragment> getNavigationFragments() {
        final ArrayList<BaseLazyFragment> fragments = new ArrayList<BaseLazyFragment>();
        fragments.add(new RenFragment());
        fragments.add(new RanFragment());
        fragments.add(new ZhuiFragment());
        fragments.add(new XunFragment());
        fragments.add(new YouFragment());
        return fragments;
    }

    @Override
    public List<NavigationTabBar.Model> getNavigationModels() {
        final String[] colors = SuperRandyApplicationContext.application.getResources().getStringArray(R.array.default_preview);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<NavigationTabBar.Model>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        SuperRandyApplicationContext.application.getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[0]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title(SuperRandyApplicationContext.application.getString(R.string.ren))
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        SuperRandyApplicationContext.application.getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title(SuperRandyApplicationContext.application.getString(R.string.ran))
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        SuperRandyApplicationContext.application.getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title(SuperRandyApplicationContext.application.getString(R.string.zhui))
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        SuperRandyApplicationContext.application.getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title(SuperRandyApplicationContext.application.getString(R.string.xun))
                        .badgeTitle("icon")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        SuperRandyApplicationContext.application.getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[4]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title(SuperRandyApplicationContext.application.getString(R.string.you))
                        .badgeTitle("icon")
                        .build()
        );

        return models;
    }
}
