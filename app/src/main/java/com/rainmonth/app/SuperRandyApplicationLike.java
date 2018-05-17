package com.rainmonth.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.rainmonth.common.utils.CrashHandler;
import com.rainmonth.support.tinker.Log.MyLogImp;
import com.rainmonth.support.tinker.util.SuperRandyApplicationContext;
import com.rainmonth.support.tinker.util.TinkerManager;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.BuildConfig;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by RandyZhang on 2017/2/14.
 */

@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.rainmonth.SuperRandyApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class SuperRandyApplicationLike extends DefaultApplicationLike {

    public SuperRandyApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);

        SuperRandyApplicationContext.application = getApplication();
        SuperRandyApplicationContext.context = getApplication();

        TinkerManager.setTinkerApplicationLike(this);

        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new MyLogImp());

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());
        if (BuildConfig.DEBUG) {
            CrashHandler.getInstance().init(getApplication());
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    // the old SuperRandyApplication file，now removed
//    package com.rainmonth;
//
//    import android.app.Application;
//
//    /**
//     * Created by RandyZhang on 16/9/19.
//     */
//    public class SuperRandyApplication extends Application {
//
//        private static SuperRandyApplication application;
//        private static int mainThreadId;
//
//        @Override
//        public void onCreate() {
//            super.onCreate();
//            application = this;
//            mainThreadId = android.os.Process.myTid();
//            configEnv();
//            // activity 管理
//        }
//
//        public static SuperRandyApplication getApplication() {
//            return application;
//        }
//
//        public static int getMainThreadId() {
//            return mainThreadId;
//        }
//
//        /**
//         * 配置环境
//         */
//        private void configEnv() {
//            // 环境相关变量管理
//        }
//    }

}
