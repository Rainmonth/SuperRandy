package com.rainmonth;

import android.app.Application;

import com.rainmonth.common.base.BaseApplication;
import com.rainmonth.common.di.component.DaggerAppComponent;
import com.rainmonth.common.di.module.AppModule;
import com.rainmonth.common.di.module.ClientModule;
import com.rainmonth.common.di.module.GlobeConfigModule;

/**
 * Application类
 * Created by RandyZhang on 2018/5/21.
 */

public class SuperRandyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .clientModule(new ClientModule())
                .globeConfigModule(getGlobeConfigModule(this))
                .build();
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
