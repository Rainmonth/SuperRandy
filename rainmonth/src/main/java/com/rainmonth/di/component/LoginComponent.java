package com.rainmonth.di.component;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.di.module.LoginModule;
import com.rainmonth.mvp.model.UserModel;
import com.rainmonth.mvp.ui.activity.LoginActivity;

import dagger.Component;

/**
 * Created by RandyZhang on 2018/6/12.
 */
@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
}
