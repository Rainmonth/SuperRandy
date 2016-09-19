package com.rainmonth.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

public class NetWorkUtils {
    private static String LOG_TAG = "NetWorkUtils";
    public static Uri uri = Uri.parse("content://telephony/carriers");

    public static boolean isMobileDataEnable(Context paramContext)
            throws Exception {
        return ((ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(0).isConnectedOrConnecting();
    }


    public static boolean isWifiDataEnable(Context paramContext) {
        try {
            boolean bool = ((ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
            return bool;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断网络是否连接
     *
     * @param context context
     * @return true if network is connected
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo netWorkInfo = connectivityManager.getActiveNetworkInfo();
            if (null != netWorkInfo && netWorkInfo.isConnected()) {
                if (netWorkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是WiFi连接
     *
     * @param context context
     * @return true if is wifi connect,otherwise false
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 打开网络设置页面
     *
     * @param context context
     */
    public static void openNetWorkSetting(Context context) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        context.startActivity(intent);
    }

}