package com.rainmonth.common.base;

import android.app.Application;

import com.rainmonth.common.R;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.component.DaggerAppComponent;
import com.rainmonth.common.di.module.AppModule;
import com.rainmonth.common.di.module.ClientModule;
import com.rainmonth.common.di.module.GlobeConfigModule;
import com.rainmonth.common.utils.ComponentUtils;

/**
 * Application 代理
 * Created by RandyZhang on 2018/5/30.
 */

public class BaseApplicationDelegate implements IBaseApplicationDelegate {

    private Application mApplication;
    private AppComponent mAppComponent;

    public BaseApplicationDelegate(Application application) {
        this.mApplication = application;

    }

    @Override
    public void onCreate() {
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(mApplication)) // 提供application
                .clientModule(new ClientModule())       // 提供网络请求相关单例
                .globeConfigModule(getGlobeConfigModule(mApplication))
                .build();
        mAppComponent.inject(this);
        ComponentUtils.init(mApplication);
        ComponentUtils.initAppComponent(mAppComponent);

        // 进行其他操作
    }

    public void onTerminate() {

    }

    public void onDefaultProgressCreate() {

    }

    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     * 需要在AndroidManifest中声明{@link }的实现类,和Glide的配置方式相似
     */
    private GlobeConfigModule getGlobeConfigModule(Application context
//            , List<ConfigModule> modules
    ) {

        GlobeConfigModule.Builder builder = GlobeConfigModule
                .builder()
                //为了防止用户没有通过GlobeConfigModule配置baseurl,而导致报错,所以提前配置个默认baseurl
                .baseurl("https://api.github.com")
                .statusBarColor(R.color.colorPrimary)   //提供一个默认的状态栏颜色
                .statusBarAlpha(0);

//        for (ConfigModule module : modules) {
//            module.applyOptions(context, builder);
//        }

        return builder.build();
    }
}
