package com.rainmonth.common.utils;

import android.os.Build;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密解密更具类
 *
 * @author RandyZhang
 * @date 2020/11/7 5:04 PM
 */

public class EnDecUtils {

    //<editor-fold> 算法名称
    public static final String ALG_MD2 = "MD2";
    public static final String ALG_MD5 = "MD5";
    public static final String ALG_SHA1 = "SHA-1";
    public static final String ALG_SHA224 = "SHA-224";
    public static final String ALG_SHA256 = "SHA-256";
    public static final String ALG_SHA384 = "SHA-384";
    public static final String ALG_SHA512 = "SHA-512";

    public static final String ALG_HMAC_MD5 = "HmacMD5";
    public static final String ALG_HMAC_SHA1 = "HmacSHA1";
    public static final String ALG_HMAC_SHA224 = "HmacSHA224";
    public static final String ALG_HMAC_SHA256 = "HmacSHA256";
    public static final String ALG_HMAC_SHA384 = "HmacSHA384";
    public static final String ALG_HMAC_SHA512 = "HmacSHA512";

    public static final String ALG_DES = "DES";
    public static final String ALG_3DES = "3DES";
    public static final String ALG_AES = "AES";

    //</editor-fold>

    //<editor-fold> Base64

    public static byte[] encodeWithBase64(final String source) {
        return Base64.encode(source.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
    }

    public static byte[] encodeWithBase64(final byte[] bytes) {
        return Base64.encode(bytes, Base64.NO_WRAP);
    }

    public static String encodeWithBase64ToString(final byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
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
     */
    public static String encodeWithBase64ToString(final String source) {
        return Base64.encodeToString(source.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
    }

    /**
     * Base64解密
     *
     * @param encryptStr 加密后的字符串
     * @return 加密前的字节数组
     */
    public static byte[] decodeBase64(final String encryptStr) {
        return Base64.decode(encryptStr, Base64.NO_WRAP);
    }

    public static byte[] decodeBase64(final byte[] bytes) {
        return Base64.decode(bytes, Base64.NO_WRAP);
    }

    public static String enByMd2ToString(final String data) {
        if (data == null || data.length() <= 0) return "";
        return enByMd2ToString(data.getBytes());
    }

    //</editor-fold>

    //<editor-fold> hash 类加密算法

    /**
     * Md2加密
     *
     * @param data 待加密的字节数据
     * @return 对应的字符串
     */
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
        return hashTemplate(data, ALG_MD2);
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
     * Md5(Message-Digest Algorithm5)加密
     * https://baike.baidu.com/item/MD5/212708?fr=aladdin}
     *
     * @param bytes 待加密的字节数据
     * @return 加密后的字节数据
     */
    public static byte[] enByMd5(final byte[] bytes) {
        return hashTemplate(bytes, ALG_MD5);
    }

    /**
     * 获取文件内容对应的Md5
     *
     * @param filePath 文件路径
     * @return md5字符串
     */
    public static String enByMd5File2String(final String filePath) {
        File file = StringUtils.isSpace(filePath) ? null : new File(filePath);
        return enByMd5File2String(file);
    }

    /**
     * 获取文件内容对应的Md5
     *
     * @param file 对应文件
     * @return md5字符串
     */
    public static String enByMd5File2String(final File file) {
        return ConvertUtils.bytes2HexString(enByMd5File(file));
    }

    /**
     * 获取文件内容对应的Md5
     *
     * @param file 文件路径
     * @return md5字节数组
     */
    public static byte[] enByMd5File(final File file) {
        if (file == null) return null;
        FileInputStream fis = null;
        DigestInputStream dis;

        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance(ALG_MD5);
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

    /**
     * 获取Sha1加密字符串
     *
     * @param data 待加密数据
     * @return Sha1加密后的数据
     */
    public static String enBySha1ToString(final String data) {
        if (data == null || data.length() <= 0) return "";
        return enByMd2ToString(data.getBytes());
    }

    /**
     * 获取Sha1加密字符串
     *
     * @param bytes 待加密字节数据
     * @return Sha1加密后的数据
     */
    public static String enBySha1ToString(final byte[] bytes) {
        return ConvertUtils.bytes2HexString(enBySha1(bytes));
    }

    /**
     * SHA-1(Secure Hash Algorithm 1)加密
     * https://baike.baidu.com/item/SHA-1?fromtitle=SHA1&fromid=8812671
     *
     * @param data 待加密的字节数据
     * @return 加密后的字节数据
     */
    public static byte[] enBySha1(byte[] data) {
        return hashTemplate(data, ALG_SHA1);
    }

    /**
     * Sha224 加密
     *
     * @param data 待加密数据
     * @return 加密后的字符串
     */
    public static String enBySha224ToString(final String data) {
        if (data == null || data.length() <= 0) return "";
        return enBySha224ToString(data.getBytes());
    }

    /**
     * Sha224 加密
     *
     * @param bytes 待加密数据
     * @return 加密后字符串
     */
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
        return hashTemplate(bytes, ALG_SHA224);
    }

    /**
     * SHA-256加密
     *
     * @param data 待加密的字节数据
     * @return 加密后的字符串
     */
    public static String enBySha256ToString(final String data) {
        if (data == null || data.length() <= 0) return "";
        return enBySha256ToString(data.getBytes());
    }

    /**
     * SHA-256加密
     *
     * @param bytes 待加密的字节数据
     * @return 加密后的字符串
     */
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
        return hashTemplate(bytes, ALG_SHA256);
    }

    /**
     * Sha384 加密
     *
     * @param data 待加密字符串
     * @return 加密后字符串
     */
    public static String enBySha384ToString(final String data) {
        if (data == null || data.length() <= 0) return "";
        return enBySha256ToString(data.getBytes());
    }

    /**
     * Sha384 加密
     *
     * @param bytes 待加密字节数组
     * @return 加密后字符串
     */
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
        return hashTemplate(bytes, ALG_SHA384);
    }

    /**
     * Sha512 加密
     *
     * @param data 待加密字符串
     * @return 加密后字符串
     */
    public static String enBySha512ToString(final String data) {
        if (data == null || data.length() <= 0) return "";
        return enBySha256ToString(data.getBytes());
    }

    /**
     * SHA-512加密
     *
     * @param bytes 待加密的字节数据
     * @return 加密后的字节数据
     */
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
        return hashTemplate(bytes, ALG_SHA512);
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

    //</editor-fold>

    //<editor-fold> Hmac Md5

    public static String enByHmacMd5ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return enByHmacMd5ToString(data.getBytes(), key.getBytes());
    }

    public static String enByHmacMd5ToString(final byte[] data, final byte[] key) {
        return ConvertUtils.bytes2HexString(enByHmacMd5(data, key));
    }

    public static byte[] enByHmacMd5(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, ALG_HMAC_MD5);
    }

    public static String enByHmacSha1ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return enByHmacSha1ToString(data.getBytes(), key.getBytes());
    }

    public static String enByHmacSha1ToString(final byte[] data, final byte[] key) {
        return ConvertUtils.bytes2HexString(enByHmacSha1(data, key));
    }

    public static byte[] enByHmacSha1(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, ALG_HMAC_SHA1);
    }

    public static String enByHmacSha224ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return enByHmacSha224ToString(data.getBytes(), key.getBytes());
    }

    public static String enByHmacSha224ToString(final byte[] data, final byte[] key) {
        return ConvertUtils.bytes2HexString(enByHmacSha224(data, key));
    }

    public static byte[] enByHmacSha224(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, ALG_HMAC_SHA224);
    }

    public static String enByHmacSha256ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return enByHmacSha256ToString(data.getBytes(), key.getBytes());
    }

    public static String enByHmacSha256ToString(final byte[] data, final byte[] key) {
        return ConvertUtils.bytes2HexString(enByHmacSha256(data, key));
    }

    public static byte[] enByHmacSha256(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, ALG_HMAC_SHA256);
    }

    public static String enByHmacSha384ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return enByHmacSha384ToString(data.getBytes(), key.getBytes());
    }

    public static String enByHmacSha384ToString(final byte[] data, final byte[] key) {
        return ConvertUtils.bytes2HexString(enByHmacSha384(data, key));
    }

    public static byte[] enByHmacSha384(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, ALG_HMAC_SHA384);
    }

    public static String enByHmacSha512ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return enByHmacSha512ToString(data.getBytes(), key.getBytes());
    }

    public static String enByHmacSha512ToString(final byte[] data, final byte[] key) {
        return ConvertUtils.bytes2HexString(enByHmacSha512(data, key));
    }

    public static byte[] enByHmacSha512(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, ALG_HMAC_SHA512);
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

    //</editor-fold>

    //<editor-fold> 对称加密

    /**
     * des加密并转换成base64
     *
     * @param data           待加密数据
     * @param key            加密key
     * @param transformation 变换用的算法
     * @param iv             变换参数缓存数组
     * @return 加密后的数据
     */
    public static byte[] enByDes2Base64(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return encodeWithBase64(enByDes(data, key, transformation, iv));
    }

    /**
     * des加密并转换成16进制字符串
     *
     * @param data           待加密数据
     * @param key            秘钥
     * @param transformation 变换算法
     * @param iv             辅助参数
     * @return des加密后的数据对应16进制字符串
     */
    public static String enByDes2HexString(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return ConvertUtils.bytes2HexString(enByDes(data, key, transformation, iv));
    }

    /**
     * DES(Data Encryption Standard) 数据加密标准
     * https://baike.baidu.com/item/DES
     * DES作为算法时称为 DEA(Data Encryption Algorithms)
     *
     * @param data           待加密数据
     * @param key            秘钥
     * @param transformation 变换算法
     * @param iv             辅助参数
     * @return des加密后的数据
     */
    public static byte[] enByDes(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, ALG_DES, transformation, iv, true);
    }

    public static byte[] decBase64Des(final String data, final byte[] key, final String transformation, final byte[] iv) {
        return decDes(decodeBase64(data), key, transformation, iv);
    }

    public static byte[] decHexStringDes(final String data, final byte[] key, final String transformation, final byte[] iv) {
        return decDes(ConvertUtils.hexString2Bytes(data), key, transformation, iv);
    }

    /**
     * DES 解密
     *
     * @param data           待解密数据
     * @param key            秘钥
     * @param transformation 变换算法
     * @param iv             辅助参数
     * @return des解密后的数据
     */
    public static byte[] decDes(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, ALG_DES, transformation, iv, false);
    }

    /**
     * 3des加密并转换成base64
     *
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] enBy3Des2Base64(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return encodeWithBase64(enBy3Des(data, key, transformation, iv));
    }

    /**
     * 3des加密并转换成16进制字符串
     *
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static String enBy3Des2HexString(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return ConvertUtils.bytes2HexString(enBy3Des(data, key, transformation, iv));
    }

    /**
     * DES(Data Encryption Standard) 数据加密标准
     * https://baike.baidu.com/item/DES
     * DES作为算法时称为 DEA(Data Encryption Algorithms)
     *
     * @param data           源数据
     * @param key            加密key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] enBy3Des(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, ALG_3DES, transformation, iv, true);
    }

    public static byte[] decBase64_3Des(final String data, final byte[] key, final String transformation, final byte[] iv) {
        return decDes(decodeBase64(data), key, transformation, iv);
    }

    public static byte[] decHexString3Des(final String data, final byte[] key, final String transformation, final byte[] iv) {
        return decDes(ConvertUtils.hexString2Bytes(data), key, transformation, iv);
    }

    /**
     * 3DES 解密
     *
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] dec3Des(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, ALG_3DES, transformation, iv, false);
    }


    public static byte[] enByAes2Base64(final byte[] data,
                                        final byte[] key,
                                        final String transformation,
                                        final byte[] iv) {
        return encodeWithBase64(enByAes(data, key, transformation, iv));
    }

    public static String enByAes2HexString(final byte[] data,
                                           final byte[] key,
                                           final String transformation,
                                           final byte[] iv) {
        return ConvertUtils.bytes2HexString(enByAes(data, key, transformation, iv));
    }

    /**
     * AES 加密
     *
     * @param data           待加密的数据
     * @param key            key
     * @param transformation 变换算法
     * @param iv             辅助参数IvParameterSpec的缓冲数组
     * @return 加密后字符串
     */
    public static byte[] enByAes(final byte[] data,
                                 final byte[] key,
                                 final String transformation,
                                 final byte[] iv) {
        return symmetricTemplate(data, key, ALG_AES, transformation, iv, true);
    }


    /**
     * 解密 利用AES加密后生成的Base64字符串
     *
     * @param data           利用AES加密后生成的Base64字符串
     * @param key            加密解密用的key
     * @param transformation 加密解密所采用的变换，如 DES/CBC/PKCS5Padding
     * @param iv             辅助参数IvParameterSpec的缓冲数组
     * @return 解密后的数据
     */
    public static byte[] decBase64Aes(final String data,
                                      final byte[] key,
                                      final String transformation,
                                      final byte[] iv) {
        return decAes(decodeBase64(data), key, transformation, iv);
    }


    /**
     * 解密 利用AES加密后生成的16进制字符串
     *
     * @param data           利用AES加密后生成的16进制字符串
     * @param key            加密解密用的key
     * @param transformation 加密解密所采用的变换，如 DES/CBC/PKCS5Padding
     * @param iv             辅助参数IvParameterSpec的缓冲数组
     * @return 解密后的数据
     */
    public static byte[] decHexStringAes(final String data,
                                         final byte[] key,
                                         final String transformation,
                                         final byte[] iv) {
        return decAes(ConvertUtils.hexString2Bytes(data), key, transformation, iv);
    }

    /**
     * @param data           待解密的数据
     * @param key            加密解密用的key
     * @param transformation 加密解密所采用的变换，如 DES/CBC/PKCS5Padding
     * @param iv             辅助加密解密过程的参数
     * @return 解密后的数据
     */
    public static byte[] decAes(final byte[] data,
                                final byte[] key,
                                final String transformation,
                                final byte[] iv) {
        return symmetricTemplate(data, key, ALG_AES, transformation, iv, false);
    }


    /**
     * 对称加密或解密模板
     *
     * @param data           待加密或解密的数据
     * @param key            加密解密用的key
     * @param algorithm      加密解密用的算法
     * @param transformation 加密解密所采用的变换，如 DES/CBC/PKCS5Padding
     * @param iv             辅助加密解密过程的参数
     * @param isEncrypt      true 加密过程 ；false解密过程
     * @return 加密或解密后的字节数组
     */
    public static byte[] symmetricTemplate(final byte[] data,
                                           final byte[] key,
                                           final String algorithm,
                                           final String transformation,
                                           final byte[] iv,
                                           final boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKey secretKey;
            if (ALG_DES.endsWith(algorithm)) {
                DESKeySpec desKeySpec = new DESKeySpec(key);
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
                secretKey = keyFactory.generateSecret(desKeySpec);
            } else {
                secretKey = new SecretKeySpec(key, algorithm);
            }
            Cipher cipher = Cipher.getInstance(transformation);
            if (iv == null || iv.length == 0) {
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey);
            } else {
                AlgorithmParameterSpec params = new IvParameterSpec(iv);
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey, params);
            }
            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    //</editor-fold>

    //<editor-fold> RSA

    /**
     * Return the Base64-encode bytes of RSA encryption.
     *
     * @param data           The data.
     * @param publicKey      The public key.
     * @param keySize        The size of key, e.g. 1024, 2048...
     * @param transformation The name of the transformation, e.g., <i>RSA/CBC/PKCS1Padding</i>.
     * @return the Base64-encode bytes of RSA encryption
     */
    public static byte[] encryptRSA2Base64(final byte[] data,
                                           final byte[] publicKey,
                                           final int keySize,
                                           final String transformation) {
        return decodeBase64(encryptRSA(data, publicKey, keySize, transformation));
    }

    /**
     * Return the hex string of RSA encryption.
     *
     * @param data           The data.
     * @param publicKey      The public key.
     * @param keySize        The size of key, e.g. 1024, 2048...
     * @param transformation The name of the transformation, e.g., <i>RSA/CBC/PKCS1Padding</i>.
     * @return the hex string of RSA encryption
     */
    public static String encryptRSA2HexString(final byte[] data,
                                              final byte[] publicKey,
                                              final int keySize,
                                              final String transformation) {
        return ConvertUtils.bytes2HexString(encryptRSA(data, publicKey, keySize, transformation));
    }

    /**
     * Return the bytes of RSA encryption.
     *
     * @param data           The data.
     * @param publicKey      The public key.
     * @param keySize        The size of key, e.g. 1024, 2048...
     * @param transformation The name of the transformation, e.g., <i>RSA/CBC/PKCS1Padding</i>.
     * @return the bytes of RSA encryption
     */
    public static byte[] encryptRSA(final byte[] data,
                                    final byte[] publicKey,
                                    final int keySize,
                                    final String transformation) {
        return rsaTemplate(data, publicKey, keySize, transformation, true);
    }

    /**
     * Return the bytes of RSA decryption for Base64-encode bytes.
     *
     * @param data           The data.
     * @param privateKey     The private key.
     * @param keySize        The size of key, e.g. 1024, 2048...
     * @param transformation The name of the transformation, e.g., <i>RSA/CBC/PKCS1Padding</i>.
     * @return the bytes of RSA decryption for Base64-encode bytes
     */
    public static byte[] decryptBase64RSA(final byte[] data,
                                          final byte[] privateKey,
                                          final int keySize,
                                          final String transformation) {
        return decryptRSA(decodeBase64(data), privateKey, keySize, transformation);
    }

    /**
     * Return the bytes of RSA decryption for hex string.
     *
     * @param data           The data.
     * @param privateKey     The private key.
     * @param keySize        The size of key, e.g. 1024, 2048...
     * @param transformation The name of the transformation, e.g., <i>RSA/CBC/PKCS1Padding</i>.
     * @return the bytes of RSA decryption for hex string
     */
    public static byte[] decryptHexStringRSA(final String data,
                                             final byte[] privateKey,
                                             final int keySize,
                                             final String transformation) {
        return decryptRSA(ConvertUtils.hexString2Bytes(data), privateKey, keySize, transformation);
    }

    /**
     * Return the bytes of RSA decryption.
     *
     * @param data           The data.
     * @param privateKey     The private key.
     * @param keySize        The size of key, e.g. 1024, 2048...
     * @param transformation The name of the transformation, e.g., <i>RSA/CBC/PKCS1Padding</i>.
     * @return the bytes of RSA decryption
     */
    public static byte[] decryptRSA(final byte[] data,
                                    final byte[] privateKey,
                                    final int keySize,
                                    final String transformation) {
        return rsaTemplate(data, privateKey, keySize, transformation, false);
    }

    /**
     * Return the bytes of RSA encryption or decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param keySize        The size of key, e.g. 1024, 2048...
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS1Padding</i>.
     * @param isEncrypt      True to encrypt, false otherwise.
     * @return the bytes of RSA encryption or decryption
     */
    private static byte[] rsaTemplate(final byte[] data,
                                      final byte[] key,
                                      final int keySize,
                                      final String transformation,
                                      final boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) {
            return null;
        }
        try {
            Key rsaKey;
            KeyFactory keyFactory;
            if (Build.VERSION.SDK_INT < 28) {
                keyFactory = KeyFactory.getInstance("RSA", "BC");
            } else {
                keyFactory = KeyFactory.getInstance("RSA");
            }
            if (isEncrypt) {
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
                rsaKey = keyFactory.generatePublic(keySpec);
            } else {
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
                rsaKey = keyFactory.generatePrivate(keySpec);
            }
            if (rsaKey == null) return null;
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, rsaKey);
            int len = data.length;
            int maxLen = keySize / 8;
            if (isEncrypt) {
                String lowerTrans = transformation.toLowerCase();
                if (lowerTrans.endsWith("pkcs1padding")) {
                    maxLen -= 11;
                }
            }
            int count = len / maxLen;
            if (count > 0) {
                byte[] ret = new byte[0];
                byte[] buff = new byte[maxLen];
                int index = 0;
                for (int i = 0; i < count; i++) {
                    System.arraycopy(data, index, buff, 0, maxLen);
                    ret = joins(ret, cipher.doFinal(buff));
                    index += maxLen;
                }
                if (index != len) {
                    int restLen = len - index;
                    buff = new byte[restLen];
                    System.arraycopy(data, index, buff, 0, restLen);
                    ret = joins(ret, cipher.doFinal(buff));
                }
                return ret;
            } else {
                return cipher.doFinal(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //</editor-fold>

    //</editor-fold>


    //<editor-fold> RC4

    /**
     * Return the bytes of RC4 encryption/decryption.
     *
     * @param data The data.
     * @param key  The key.
     */
    public static byte[] rc4(byte[] data, byte[] key) {
        if (data == null || data.length == 0 || key == null) return null;
        if (key.length < 1 || key.length > 256) {
            throw new IllegalArgumentException("key must be between 1 and 256 bytes");
        }
        final byte[] iS = new byte[256];
        final byte[] iK = new byte[256];
        int keyLen = key.length;
        for (int i = 0; i < 256; i++) {
            iS[i] = (byte) i;
            iK[i] = key[i % keyLen];
        }
        int j = 0;
        byte tmp;
        for (int i = 0; i < 256; i++) {
            j = (j + iS[i] + iK[i]) & 0xFF;
            tmp = iS[j];
            iS[j] = iS[i];
            iS[i] = tmp;
        }

        final byte[] ret = new byte[data.length];
        int i = 0, k, t;
        for (int counter = 0; counter < data.length; counter++) {
            i = (i + 1) & 0xFF;
            j = (j + iS[i]) & 0xFF;
            tmp = iS[j];
            iS[j] = iS[i];
            iS[i] = tmp;
            t = (iS[i] + iS[j]) & 0xFF;
            k = iS[t];
            ret[counter] = (byte) (data[counter] ^ k);
        }
        return ret;
    }

    //</editor-fold>

    //<editor-fold> 辅助方法

    private static byte[] joins(final byte[] prefix, final byte[] suffix) {
        byte[] ret = new byte[prefix.length + suffix.length];
        System.arraycopy(prefix, 0, ret, 0, prefix.length);
        System.arraycopy(suffix, 0, ret, prefix.length, suffix.length);
        return ret;
    }

    //</editor-fold>
}
