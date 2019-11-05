package com.rainmonth;

import com.rainmonth.common.ServiceConfig;
import com.rainmonth.common.base.BaseApplication;

/**
 * Application类
 * Created by RandyZhang on 2018/5/21.
 */

public class SuperRandyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void initModuleService() {
        for (String moduleApp : ServiceConfig.appModules) {
            try {
                Class clazz = Class.forName(moduleApp);
                BaseApplication baseApp = (BaseApplication) clazz.newInstance();
                baseApp.initModuleService();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
