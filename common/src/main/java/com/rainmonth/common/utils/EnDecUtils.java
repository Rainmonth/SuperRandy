package com.rainmonth.common.utils;

import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密解密更具类
 *
 * @author RandyZhang
 * @date 2020/11/7 5:04 PM
 */
public class EnDecUtils {

    //<editor-fold> 加密

    public void encrypt() {

    }

    /**
     * Base64加密
     * 百度百科：https://baike.baidu.com/item/base64/8545775?fr=aladdin
     * 64指的是 26个大写英文字母（A-Z） + 26个小写应为字母（a-z） + 10个数字（0-9） + 两个特殊字符（'+'、'/'）
     * 规则：
     * 1. 每 三个字节（二进制） 转换成 四个字节（二进制）（高位补0）
     * 2. 每76个字符加一个换行符
     * 3. 处理最后的结束符
     *
     * @param source 待加密的字符串
     * @return 加密后的字符串
     * @throws UnsupportedEncodingException if is unsupported encoding
     */
    public static String enByBase64(String source) throws UnsupportedEncodingException {
        return Base64.encodeToString(source.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
    }

    /**
     * Base64解密
     *
     * @param encryptStr 加密后的字符串
     * @return 加密前的字符串
     */
    public static String decByBase64(String encryptStr) {
        return new String(Base64.decode(encryptStr, Base64.NO_WRAP), StandardCharsets.UTF_8);
    }

    /**
     * Md5加密
     * 原理：
     */
    public static String enByMd5(String source) {
        if (TextUtils.isEmpty(source)) {
            return "";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[]  bytes = md.digest(source.getBytes());
            for (byte b : bytes) {

            }
            return "";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Md5解密
     */
    public static void decByMd5() {

    }

    public static void enByRsa() {

    }

    public static void enByAes() {

    }

    public static void enBySha1() {

    }

    //</editor-fold>

    //<editor-fold> 解密
    public void decrypt() {

    }

    public static void decByAes() {

    }

    public static void decBySha1() {

    }

    //</editor-fold>

    //<editor-fold> Unicode相关
    public static void encode() {

    }

    public static void decode() {

    }
    //</editor-fold>

    //<editor-fold>进制转化

    //</editor-fold>

    public static int getCharAsciiInt(char ch) {
        return ch;
    }
}
