package com.rainmonth.di.module;

import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.mvp.contract.UserContract;
import com.rainmonth.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;

/**
 * Login Module
 * Created by RandyZhang on 2018/6/12.
 */
@Module
public class LoginModule {
    private UserContract.View view;

    public LoginModule(UserContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserContract.View provideLoginView() {
        return view;
    }

    @ActivityScope
    @Provides
    UserContract.Model provideLoginModel(UserModel model) {
        return model;
    }
}
