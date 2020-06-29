package com.rainmonth.di.component;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.di.module.PursueModule;
import com.rainmonth.mvp.ui.fragment.PursueFragment;

import dagger.Component;

/**
 * @desprition: 追 Component
 * @author: RandyZhang
 * @date: 2018/6/26 下午3:09
 */
@FragmentScope
@Component(modules = PursueModule.class, dependencies = AppComponent.class)
public interface PursueComponent {
    void inject(PursueFragment fragment);
}