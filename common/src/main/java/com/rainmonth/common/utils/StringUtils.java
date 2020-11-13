package com.rainmonth.common.utils;

import android.text.TextUtils;

/**
 * @author RandyZhang
 * @date 2020/11/13 5:00 PM
 */
public class StringUtils {
    public static boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    /**
     * 是否是null或者全为空格的字符串
     *
     * @param s the string
     * @return true if is null or empty string
     */
    public static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
