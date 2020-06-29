package com.rainmonth.di.component;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.di.module.XunModule;
import com.rainmonth.mvp.ui.fragment.XunFragment;

import dagger.Component;

/**
 * XunComponent
 * Created by RandyZhang on 2018/5/31.
 */
@FragmentScope
@Component(modules = XunModule.class, dependencies = AppComponent.class)
public interface XunComponent {
    void inject(XunFragment fragment);
}
