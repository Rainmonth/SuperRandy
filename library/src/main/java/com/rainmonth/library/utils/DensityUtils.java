package com.rainmonth.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.lang.reflect.Method;

public class DensityUtils {

    private static final float DOT_FIVE = 0.5f;


    private static DisplayMetrics sDisplayMetrics;

    /**
     * get screen width
     *
     * @param context
     * @return
     */
    public static int getDisplayWidth(Context context) {
        initDisplayMetrics(context);
        return sDisplayMetrics.widthPixels;
    }

    /**
     * get screen height
     *
     * @param context
     * @return
     */
    public static int getDisplayHeight(Context context) {
        initDisplayMetrics(context);
        return sDisplayMetrics.heightPixels;
    }

    /**
     * init display metrics
     *
     * @param context
     */
    private static synchronized void initDisplayMetrics(Context context) {
        sDisplayMetrics = context.getResources().getDisplayMetrics();
    }

    /**
     * is landscape
     *
     * @param context
     * @return
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * is portrait
     *
     * @param context context
     * @return true if is portrait
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }


    /**
     * finger = getString("ro.product.brand") + '/' +
     * getString("ro.product.name") + '/' +
     * getString("ro.product.device") + ':' +
     * getString("ro.build.version.release") + '/' +
     * getString("ro.build.id") + '/' +
     * getString("ro.build.version.incremental") + ':' +
     * getString("ro.build.type") + '/' +
     * getString("ro.build.tags");
     *
     * @return
     */
    public static String getReformatFingerprint() {
        return "brand->" + Build.BOARD + "\n\t" +
                "name->" + Build.PRODUCT + "\n\t" +
                "device->" + Build.DEVICE + "\n\t" +
                "release->" + Build.VERSION.RELEASE + "\n\t" +
                "id->" + Build.ID + "\n\t" +
                "incremental->" + Build.VERSION.INCREMENTAL + "\n\t" +
                "type->" + Build.TYPE + "\n\t" +
                "tags->" + Build.TAGS;
    }

    /**
     * 打印设备信息（包括density、densityDpi、屏幕宽高）
     *
     * @param context context
     */
    public static void printDeviceInfo(Context context) {
        Log.i("DeviceInfo", "density = " + getDensity(context) + "\n" +
                "densityDpi = " + getDensityDpi(context) + "\n" +
                "width = " + getScreenWidth(context) + "\n" +
                "height = " + getScreenHeight(context) + "\n" +
                "statusBarHeight = " + getStatusBarHeight(context) + "\n" +
                "deviceId = " + getDeviceId(context) + "\n" +
                "fingerprint = " + getReformatFingerprint());
    }

    /**
     * 获取设备density
     *
     * @param context 上下文
     * @return 设备density值
     */
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取设备dpi
     *
     * @param context 上下文
     * @return 设备dpi值
     */
    public static int getDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue 要转换的dp值
     * @return 转换得到的px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + DensityUtils.DOT_FIVE);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue 要转化的px值
     * @return 转化得到的dip值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + DensityUtils.DOT_FIVE);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     *
     * @param context 上下文
     * @param pxValue 要转化的px值
     * @return 转化得到的sp值
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + DensityUtils.DOT_FIVE);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px
     *
     * @param context 上下文
     * @param spValue 要转化的sp值
     * @return 转化得到的px值
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + DensityUtils.DOT_FIVE);
    }

    /**
     * 获取dialog宽度
     *
     * @param aty aty
     * @return dialog width
     */
    public static int getDialogWidth(Activity aty) {
        DisplayMetrics dm;
        dm = aty.getResources().getDisplayMetrics();
        int w = dm.widthPixels - 100;
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth() - 100;
        return w;
    }

    /**
     * 获取屏幕宽度
     *
     * @param aty aty
     * @return 获取屏幕宽度
     */
    public static int getScreenWidth(Activity aty) {
        DisplayMetrics dm;
        dm = aty.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth();
        return w;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm;
        dm = context.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        return w;
    }

    /**
     * 获取屏幕高度
     *
     * @param aty aty
     * @return 获取屏幕高度
     */
    public static int getScreenHeight(Activity aty) {
        DisplayMetrics dm;
        dm = aty.getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        // int h = aty.getWindowManager().getDefaultDisplay().getHeight();
        return h;
    }

    /**
     * 获取屏幕高度
     *
     * @param context 上下文
     * @return 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm;
        dm = context.getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        // int h = aty.getWindowManager().getDefaultDisplay().getHeight();
        return h;
    }

    /**
     * 采用反射获取状态栏的高度
     *
     * @param context 上下文
     * @return 状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusHeight;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 通过反射获取NavigationBar的高度
     *
     * @param context context
     * @return 导航栏高度
     */
    public static int getNavigationBarHeight(Context context) {
        int navigationHeight = 0;
        Class<?> localClass;
        try {
            localClass = Class.forName("com.android.internal.R$dimen");
            Object localObject = localClass.newInstance();
            int i = Integer.parseInt(localClass.getField("navigation_bar_height").get(localObject).toString());
            if (i > 0 && checkDeviceHasNavigationBar(context)) {
                navigationHeight = context.getResources().getDimensionPixelSize(i);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return navigationHeight;
    }

    /**
     * 采用反射的方式检查是否存在NavigationBar
     *
     * @param context context
     * @return true if navigation bar exist
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasNavigationBar;

    }

    /**
     * 获取status bar的高度
     *
     * @param activity activity
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 获取NavigationBar的高度
     *
     * @param activity activity
     * @return 导航栏高度
     */
    public static int getNavigationBarHeight(Activity activity) {
        int navigationHeight = 0;
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0 && checkDeviceHasNavigationBar(activity)) {
            navigationHeight = resources.getDimensionPixelSize(resourceId);
        }
        return navigationHeight;
    }

    /**
     * get toolbar height
     *
     * @param context context
     * @return toolbar height
     */
    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }
}