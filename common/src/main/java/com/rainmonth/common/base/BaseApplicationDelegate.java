package com.rainmonth.common.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rainmonth.common.BuildConfig;
import com.rainmonth.common.R;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.component.DaggerAppComponent;
import com.rainmonth.common.di.module.AppModule;
import com.rainmonth.common.di.module.ClientModule;
import com.rainmonth.common.di.module.GlobeConfigModule;
import com.rainmonth.common.di.module.ImageModule;
import com.rainmonth.common.http.GlobalHttpHandler;
import com.rainmonth.common.http.RequestInterceptor;
import com.rainmonth.common.integration.ConfigModule;
import com.rainmonth.common.integration.ManifestParser;
import com.rainmonth.common.utils.ComponentUtils;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Application 代理
 * Created by RandyZhang on 2018/5/30.
 */

public class BaseApplicationDelegate implements IBaseApplicationDelegate {

    private Application mApplication;
    private AppComponent mAppComponent;
    private final List<ConfigModule> mModules;

    public BaseApplicationDelegate(Application application) {
        this.mApplication = application;
        this.mModules = new ManifestParser(mApplication).parse();

    }

    @Override
    public void onCreate() {
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(mApplication)) // 提供application
                .clientModule(new ClientModule())       // 提供网络请求相关单例
                .imageModule(new ImageModule())
                .globeConfigModule(getGlobeConfigModule(mApplication, mModules))
                .build();
        mAppComponent.inject(this);
        ComponentUtils.init(mApplication);
        ComponentUtils.initAppComponent(mAppComponent);

        // 进行其他操作
        //router
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
            ARouter.printStackTrace();
        }
        ARouter.init(mApplication);

        // 注册数据及网络请求相关组件
        for (ConfigModule module : mModules) {
            module.registerComponents(mApplication, mAppComponent.repositoryManager());
        }
    }

    public void onTerminate() {

    }

    public void onDefaultProgressCreate() {

    }

    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     * 需要在AndroidManifest中声明{@link }的实现类,和Glide的配置方式相似
     */
    private GlobeConfigModule getGlobeConfigModule(Application context,
                                                   List<ConfigModule> modules) {
        GlobeConfigModule.Builder builder = GlobeConfigModule
                .builder()
                .addInterceptor(new RequestInterceptor(GlobalHttpHandler.EMPTY))
                //为了防止用户没有通过GlobeConfigModule配置baseurl,而导致报错,所以提前配置个默认baseurl
                .baseUrl("http://rainmonth.cn")
                .statusBarColor(R.color.colorPrimary)   //提供一个默认的状态栏颜色
                .statusBarAlpha(0);

        for (ConfigModule module : modules) {
            module.applyOptions(context, builder);
        }

        return builder.build();
    }

    public interface Lifecycle {

        void onCreate(Application application);

        void onTerminate(Application application);
    }
}
