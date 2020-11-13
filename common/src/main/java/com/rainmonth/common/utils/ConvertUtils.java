package com.rainmonth.common.utils;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * 转换工具类
 *
 * @author RandyZhang
 * @date 2020/11/12 2:57 PM
 */

public class ConvertUtils {

    public static final char[] HEX_DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    public static final char[] HEX_DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 10 进制转换成 16 进制字符串
     *
     * @param num 待转化的十进制
     * @return 对应的 16 进制字符串
     */
    public static String int2HexString(int num) {
        return Integer.toHexString(num);
    }

    /**
     * 16 进制转换成 10 进制
     *
     * @param hexString 16进制字符串
     * @return 对应 10 进制
     */
    public static int hexString2Int(String hexString) {
        return Integer.parseInt(hexString, 16);
    }

    /**
     * 字节数组 转换成 二进制字符串
     *
     * @param bytes 字节数组
     * @return 二进制字符串
     */
    public static String bytes2Bits(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            for (int j = 7; j >= 0; --j) {
                sb.append(((b >> j) & 0x01) == 0 ? '0' : '1');
            }
        }
        return sb.toString();
    }

    /**
     * 二进制字符串 转换成 字节数组
     *
     * @param bits 二进制字符串
     * @return 字节数组
     */
    @NonNull
    public static byte[] bits2Bytes(@NonNull String bits) {
        int modLen = bits.length() % 8;
        int byteLen = bits.length() / 8;

        // 长度不是8的倍数时，在前面补0
        if (modLen != 0) {
            for (int i = modLen; i < 8; i++) {
                bits = "0" + bits;
            }
            byteLen++;
        }
        byte[] bytes = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            for (int j = 0; j < 8; j++) {
                bytes[i] <<= 1;// bytes[i]左移一位后保存在bytes[i]中
                bytes[i] |= bits.charAt(i * 8 + j) - '0';
            }
        }
        return bytes;
    }

    /**
     * 字节数组 转换成 char数组
     *
     * @param bytes 字节数组
     * @return char数组
     */
    public static char[] bytes2Chars(byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }
        return chars;
    }

    /**
     * char 数组 转换成 字节数组
     *
     * @param chars char数组
     * @return 字节数组
     */
    public static byte[] chars2Bytes(char[] chars) {
        if (chars == null || chars.length <= 0) return null;
        int len = chars.length;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) chars[i];
        }
        return bytes;
    }

    /**
     * 字节数组 转 16 进制字符串
     *
     * @param bytes 待转化的字节数组
     * @return 对应的 16 进制字符串
     */
    public static String bytes2HexString(final byte[] bytes) {
        return bytes2HexString(bytes, true);
    }

    /**
     * 字节数组 转 16 进制字符串
     *
     * @param bytes       待转化的字节数组
     * @param isUpperCase 是否大写
     * @return 对应的 16 进制字符串
     */
    public static String bytes2HexString(final byte[] bytes, boolean isUpperCase) {
        if (bytes == null) return "";
        char[] hexDigits = isUpperCase ? HEX_DIGITS_UPPER : HEX_DIGITS_LOWER;
        int len = bytes.length;
        if (len <= 0) return "";
        char[] ret = new char[len << 1];// 长度变为byte数组的两倍
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >> 4 & 0x0f];// 字节高四位对应的16进制数字
            ret[j++] = hexDigits[bytes[i] & 0x0f];// 字节低四位16进制数字
        }
        return new String(ret);
    }

    /**
     * 16 进制字符串转 字节数组
     *
     * @param hexString 待转化的16进制字符串
     * @return 对应的字节数组
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (StringUtils.isSpace(hexString)) return new byte[0];
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1]; // 字节长度为16进制串长度一半
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1])); // 高位*16 + 地位
        }
        return ret;
    }

    /**
     * 16 进制字符转换成 10 进制数
     *
     * @param hexChar 16 进制字符
     * @return 10进制数
     */
    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static String bytes2String(final byte[] bytes) {
        return bytes2String(bytes, "");
    }

    public static String bytes2String(final byte[] bytes, String charsetName) {
        if (bytes == null) return null;
        try {
            return new String(bytes, getSafeCharset(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(bytes);
        }
    }

    public static byte[] string2Bytes(String str) {
        return string2Bytes(str, "");
    }

    public static byte[] string2Bytes(String str, String charsetName) {
        if (str == null) return null;
        try {
            return str.getBytes(getSafeCharset(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str.getBytes();
        }
    }

    private static String getSafeCharset(String charsetName) {
        String charset = charsetName;
        if (StringUtils.isSpace(charset) || !Charset.isSupported(charset)) {
            charset = "UTF-8";
        }
        return charset;
    }

    public static JSONObject bytes2JSONObject(byte[] bytes) {
        if (bytes == null) return null;
        try {
            return new JSONObject(new String(bytes));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] jsonObject2Bytes(JSONObject jsonObject) {
        if (jsonObject == null) return null;
        return jsonObject.toString().getBytes();
    }

    public static JSONArray bytes2JSONArray(byte[] bytes) {
        if (bytes == null) return null;
        try {
            return new JSONArray(new String(bytes));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] jsonArray2Bytes(JSONArray jsonArray) {
        if (jsonArray == null) return null;
        return jsonArray.toString().getBytes();
    }
}
