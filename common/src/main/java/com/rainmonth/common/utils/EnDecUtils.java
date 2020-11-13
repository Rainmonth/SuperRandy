package com.rainmonth.common.utils;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密解密更具类
 *
 * @author RandyZhang
 * @date 2020/11/7 5:04 PM
 */
public class EnDecUtils {

    public static final String HASH_ALG_MD2 = "MD2";
    public static final String HASH_ALG_MD5 = "MD5";
    public static final String HASH_ALG_SHA1 = "SHA-1";
    public static final String HASH_ALG_SHA224 = "SHA-224";
    public static final String HASH_ALG_SHA256 = "SHA-256";
    public static final String HASH_ALG_SHA384 = "SHA-384";
    public static final String HASH_ALG_SHA512 = "SHA-512";

    public static final String HMAC_ALG_MD5 = "HmacMD5";
    public static final String HMAC_ALG_SHA1 = "HmacSHA1";
    public static final String HMAC_ALG_SHA224 = "HmacSHA224";
    public static final String HMAC_ALG_SHA256 = "HmacSHA256";
    public static final String HMAC_ALG_SHA384 = "HmacSHA384";
    public static final String HMAC_ALG_SHA512 = "HmacSHA512";

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
    public static String enByBase64(final String source) throws UnsupportedEncodingException {
        return Base64.encodeToString(source.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
    }

    /**
     * Base64解密
     *
     * @param encryptStr 加密后的字符串
     * @return 加密前的字符串
     */
    public static String decByBase64(final String encryptStr) {
        return new String(Base64.decode(encryptStr, Base64.NO_WRAP), StandardCharsets.UTF_8);
    }

    public static String enByMd2ToString(final String data) {
        if (data == null || data.length() <= 0) return "";
        return enByMd2ToString(data.getBytes());
    }

    public static String enByMd2ToString(final byte[] data) {
        return ConvertUtils.bytes2HexString(enByMd2(data));
    }

    /**
     * Md2加密
     *
     * @param data 待加密的字节数据
     * @return 加密后的字节数据
     */
    public static byte[] enByMd2(final byte[] data) {
        return hashTemplate(data, HASH_ALG_MD2);
    }

    public static String enByMd5ToString(final String data) {
        if (data == null || data.length() == 0) return "";
        return enByMd5ToString(data.getBytes());
    }

    public static String enByMd5ToString(final byte[] data) {
        return ConvertUtils.bytes2HexString(enByMd5(data));
    }

    public static String enByMd5ToString(final String data, final String salt) {
        if (data == null && salt == null) return "";
        if (salt == null) return ConvertUtils.bytes2HexString(enByMd5(data.getBytes()));
        if (data == null) return ConvertUtils.bytes2HexString(enByMd5(salt.getBytes()));
        return ConvertUtils.bytes2HexString(enByMd5((data + salt).getBytes()));
    }

    public static String enByMd5ToString(final byte[] data, final byte[] salt) {
        if (data == null && salt == null) return "";
        if (salt == null) return ConvertUtils.bytes2HexString(enByMd5(data));
        if (data == null) return ConvertUtils.bytes2HexString(enByMd5(salt));
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return ConvertUtils.bytes2HexString(enByMd5(dataSalt));
    }

    /**
     * Md5加密
     *
     * @param bytes 待加密的字节数据
     * @return 加密后的字节数据
     */
    public static byte[] enByMd5(final byte[] bytes) {
        return hashTemplate(bytes, HASH_ALG_MD5);
    }

    public static String enByMd5File2String(final String filePath) {
        File file = StringUtils.isSpace(filePath) ? null : new File(filePath);
        return enByMd5File2String(file);
    }

    public static String enByMd5File2String(final File file) {
        return ConvertUtils.bytes2HexString(enByMd5File(file));
    }

    public static byte[] enByMd5File(final File file) {
        if (file == null) return null;
        FileInputStream fis = null;
        DigestInputStream dis;

        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance(HASH_ALG_MD5);
            dis = new DigestInputStream(fis, md);
            byte[] buffer = new byte[256 * 1024];
            while (true) { // 不断调用 md 的update方法
                if (!(dis.read(buffer) > 0)) break;
            }
            md = dis.getMessageDigest();
            return md.digest();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String enBySha1ToString(final String data) {
        if (data == null || data.length() <= 0) return "";
        return enByMd2ToString(data.getBytes());
    }

    public static String enBySha1ToString(final byte[] bytes) {
        return ConvertUtils.bytes2HexString(enBySha1(bytes));
    }

    /**
     * SHA-1加密
     *
     * @param data 待加密的字节数据
     * @return 加密后的字节数据
     */
    public static byte[] enBySha1(byte[] data) {
        return hashTemplate(data, HASH_ALG_SHA1);
    }

    public static String enBySha224ToString(final String data) {
        if (data == null || data.length() <= 0) return "";
        return enBySha224ToString(data.getBytes());
    }

    public static String enBySha224ToString(final byte[] bytes) {
        return ConvertUtils.bytes2HexString(enBySha224(bytes));
    }

    /**
     * SHA-224加密
     *
     * @param bytes 待加密的字节数据
     * @return 加密后的字节数据
     */
    public static byte[] enBySha224(byte[] bytes) {
        return hashTemplate(bytes, HASH_ALG_SHA224);
    }

    public static String enBySha256ToString(final String data) {
        if (data == null || data.length() <= 0) return "";
        return enBySha256ToString(data.getBytes());
    }

    public static String enBySha256ToString(final byte[] bytes) {
        return ConvertUtils.bytes2HexString(enBySha256(bytes));
    }

    /**
     * SHA-256加密
     *
     * @param bytes 待加密的字节数据
     * @return 加密后的字节数据
     */
    public static byte[] enBySha256(byte[] bytes) {
        return hashTemplate(bytes, HASH_ALG_SHA256);
    }

    public static String enBySha384ToString(final String data) {
        if (data == null || data.length() <= 0) return "";
        return enBySha256ToString(data.getBytes());
    }

    public static String enBySha384ToString(final byte[] bytes) {
        return ConvertUtils.bytes2HexString(enBySha384(bytes));
    }

    /**
     * SHA-384加密
     *
     * @param bytes 待加密的字节数据
     * @return 加密后的字节数据
     */
    public static byte[] enBySha384(byte[] bytes) {
        return hashTemplate(bytes, HASH_ALG_SHA384);
    }

    public static String enBySha512ToString(final String data) {
        if (data == null || data.length() <= 0) return "";
        return enBySha256ToString(data.getBytes());
    }

    public static String enBySha512ToString(final byte[] bytes) {
        return ConvertUtils.bytes2HexString(enBySha512(bytes));
    }

    /**
     * SHA-512加密
     *
     * @param bytes 待加密的字节数据
     * @return 加密后的字节数据
     */
    public static byte[] enBySha512(byte[] bytes) {
        return hashTemplate(bytes, HASH_ALG_SHA512);
    }

    /**
     * hash 加密算法模板
     *
     * @param bytes     待加密的字节数组
     * @param algorithm 对应的加密算法
     * @return 加密后的字节数组
     */
    private static byte[] hashTemplate(final byte[] bytes, final String algorithm) {
        if (bytes == null || bytes.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(bytes);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String enByHmacMd5ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return enByHmacMd5ToString(data.getBytes(), key.getBytes());
    }

    public static String enByHmacMd5ToString(final byte[] data, final byte[] key) {
        return ConvertUtils.bytes2HexString(enByHmacMd5(data, key));
    }

    public static byte[] enByHmacMd5(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, HMAC_ALG_MD5);
    }

    public static String enByHmacSha1ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return enByHmacSha1ToString(data.getBytes(), key.getBytes());
    }

    public static String enByHmacSha1ToString(final byte[] data, final byte[] key) {
        return ConvertUtils.bytes2HexString(enByHmacSha1(data, key));
    }

    public static byte[] enByHmacSha1(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, HMAC_ALG_SHA1);
    }

    public static String enByHmacSha224ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return enByHmacSha224ToString(data.getBytes(), key.getBytes());
    }

    public static String enByHmacSha224ToString(final byte[] data, final byte[] key) {
        return ConvertUtils.bytes2HexString(enByHmacSha224(data, key));
    }

    public static byte[] enByHmacSha224(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, HMAC_ALG_SHA224);
    }

    public static String enByHmacSha256ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return enByHmacSha256ToString(data.getBytes(), key.getBytes());
    }

    public static String enByHmacSha256ToString(final byte[] data, final byte[] key) {
        return ConvertUtils.bytes2HexString(enByHmacSha256(data, key));
    }

    public static byte[] enByHmacSha256(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, HMAC_ALG_SHA256);
    }

    public static String enByHmacSha384ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return enByHmacSha384ToString(data.getBytes(), key.getBytes());
    }

    public static String enByHmacSha384ToString(final byte[] data, final byte[] key) {
        return ConvertUtils.bytes2HexString(enByHmacSha384(data, key));
    }

    public static byte[] enByHmacSha384(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, HMAC_ALG_SHA384);
    }

    public static String enByHmacSha512ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return enByHmacSha512ToString(data.getBytes(), key.getBytes());
    }

    public static String enByHmacSha512ToString(final byte[] data, final byte[] key) {
        return ConvertUtils.bytes2HexString(enByHmacSha512(data, key));
    }

    public static byte[] enByHmacSha512(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, HMAC_ALG_SHA512);
    }

    /**
     * hmac(Hash-based Message Authentication Code) 加密算法模板
     *
     * @param data      待加密的字节数组
     * @param key       key字节数组
     * @param algorithm 加密算法
     * @return 加密后的字节数组
     */
    private static byte[] hmacTemplate(final byte[] data, final byte[] key, final String algorithm) {
        if (data == null || data.length <= 0 || key == null || key.length <= 0) return null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKeySpec);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
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
