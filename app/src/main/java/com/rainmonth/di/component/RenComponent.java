package com.rainmonth.di.component;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.di.module.RenModule;
import com.rainmonth.mvp.ui.fragment.RenFragment;

import dagger.Component;

/**
 *
 * Created by RandyZhang on 2018/5/31.
 */
@FragmentScope
@Component(modules = RenModule.class, dependencies = AppComponent.class)
public interface RenComponent {
    void inject(RenFragment fragment);
}
