
package com.rainmonth.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.Map;
import java.util.Set;

/**
 * SharedPreference 工具类
 * <p>
 * commit()和apply()的区别
 * 1、commit()是同步的，而apply()则是异步的
 * 2、commit()是有返回值的，而apply()没有返回值的
 *
 * @author RandyZhang
 * @date 2020/11/06 AM
 */
public class SpUtils {
    private static final String TAG = SpUtils.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    private static Context mAppCtx;

    private void init(Context context) {
        mAppCtx = context.getApplicationContext();
    }

    public static void putString(final String key, final String value, String spName) {
        getEditor(spName).putString(key, value).commit();
    }

    public static void putString(final String key, final String value) {
        putString(key, value, "");
    }

    public static String getString(String key, final String defaultValue, String spName) {
        return get(spName).getString(key, defaultValue);
    }

    public static String getString(String key, final String defaultValue) {
        return getString(key, defaultValue, "");
    }

    public static void putInt(final String key, final int value, String spName) {
        getEditor(spName).putInt(key, value).commit();
    }

    public static void putInt(final String key, final int value) {
        putInt(key, value, "");
    }

    public static int getInt(final String key, final int defaultValue, String spName) {
        return get(spName).getInt(key, defaultValue);
    }

    public static int getInt(final String key, final int defaultValue) {
        return getInt(key, defaultValue, "");
    }

    public static boolean getBoolean(final String key, final boolean defaultValue, String spName) {
        return get(spName).getBoolean(key, defaultValue);
    }

    public static boolean getBoolean(final String key, final boolean defaultValue) {
        return getBoolean(key, defaultValue, "");
    }

    public static void putBoolean(final String key, final boolean value, String spName) {
        getEditor(spName).putBoolean(key, value).commit();
    }

    public static void putBoolean(final String key, final boolean value) {
        putBoolean(key, value, "");
    }

    public static float getFloat(final String key, final float defaultValue, String spName) {
        return get(spName).getFloat(key, defaultValue);
    }

    public static float getFloat(final String key, final float defaultValue) {
        return getFloat(key, defaultValue, "");
    }

    public static void putFloat(final String key, final float value, String spName) {
        getEditor(spName).putFloat(key, value).commit();
    }

    public static void putFloat(final String key, final float value) {
        putFloat(key, value, "");
    }

    public static long getLong(final String key, final long defaultValue, String spName) {
        return get(spName).getLong(key, defaultValue);
    }

    public static long getLong(final String key, final long defaultValue) {
        return getLong(key, defaultValue, "");
    }

    public static void putLong(final String key, final long value, String spName) {
        getEditor(spName).putLong(key, value).commit();
    }

    public static void putLong(final String key, final long value) {
        putLong(key, value, "");
    }

    public static boolean hasKey(final String key, String spName) {
        return get(spName).contains(key);
    }

    public static boolean hasKey(final String key) {
        return hasKey(key, "");
    }

    public static Map<String, ?> getAll(String spName) {
        return get(spName).getAll();
    }

    public static void printAll(String spName) {
        checkCtx();
        Map<String, ?> map = getAll(spName);
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key:" + key + ", value:" + getObjectValueStr(value));
        }
    }

    private static String getObjectValueStr(Object valueObj) {
        if (valueObj instanceof Float || valueObj instanceof String
                || valueObj instanceof Integer || valueObj instanceof Boolean) {
            return String.valueOf(valueObj);
        } else if (valueObj instanceof Set) {
            return "Set<String>[]";
        } else {
            return "unknown";
        }
    }

    private static SharedPreferences get(String spName) {
        checkCtx();
        SharedPreferences sharedPreferences;
        if (TextUtils.isEmpty(spName)) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mAppCtx);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                System.out.println("SpFile name:" + PreferenceManager.getDefaultSharedPreferencesName(mAppCtx));
            } else {
                System.out.println("SpFile name:" + mAppCtx.getPackageName() + "_preferences");
            }
        } else {
            sharedPreferences = mAppCtx.getSharedPreferences(spName, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    private static Editor getEditor(String spName) {
        return get(spName).edit();
    }

    public static void clearPreference(final SharedPreferences p) {
        final Editor editor = p.edit();
        editor.clear();
        editor.commit();
    }

    private static void checkCtx() {
        if (mAppCtx == null) {
            throw new IllegalArgumentException("mAppCtx is null, please call init() before you use it");
        }
    }
}
