package com.rainmonth.di.component;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.di.module.RanModule;
import com.rainmonth.mvp.ui.fragment.RanFragment;

import dagger.Component;

/**
 * Ran component
 * Created by RandyZhang on 2018/5/31.
 */
@FragmentScope
@Component(modules = RanModule.class, dependencies = AppComponent.class)
public interface RanComponent {
    void inject(RanFragment fragment);
}
