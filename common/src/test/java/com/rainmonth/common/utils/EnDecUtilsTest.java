package com.rainmonth.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * 加密解密单元测试
 * robolectric单元测试地址
 * https://blog.csdn.net/lyabc123456/article/details/89481000
 *
 * @author RandyZhang
 * @date 2020/11/14 2:51 PM
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 23, manifest = Config.NONE)
public class EnDecUtilsTest {
    String source = "hello world!";
    String base64Result = "aGVsbG8gd29ybGQh";
    String md2Result = "76008DC5481F235664E357735905887F";
    String md5Result = "76008DC5481F235664E357735905887F";

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void encodeWithBase64() {
        String result = EnDecUtils.encodeWithBase64ToString(source);
        System.out.println(result);
//        assertNotEquals(null, DateUtils.getNowDate());
        assertNotEquals(base64Result + 1, result);
    }

    @Test
    public void encodeWithBase64ToString() {
        String result = EnDecUtils.encodeWithBase64ToString(source);
        System.out.println(result);
    }

    @Test
    public void decodeBase64() {
        String source = new String(EnDecUtils.decodeBase64(base64Result));
        assertEquals("hello world!", source);
    }

    @Test
    public void enByMd2ToString() {
        System.out.println(EnDecUtils.enByMd2ToString(source));
        System.out.println(EnDecUtils.enByMd5ToString(source));
        assertEquals("76008DC5481F235664E357735905887F", EnDecUtils.enByMd2ToString(source));
        assertEquals("76008DC5481F235664E357735905887F", EnDecUtils.enByMd5ToString(source));
    }

    @Test
    public void testEnByMd2ToString() {
    }

    @Test
    public void enByMd2() {
    }

    @Test
    public void enByMd5ToString() {
    }

    @Test
    public void testEnByMd5ToString() {
    }

    @Test
    public void testEnByMd5ToString1() {
    }

    @Test
    public void testEnByMd5ToString2() {
    }

    @Test
    public void enByMd5() {
    }

    @Test
    public void enByMd5File2String() {
    }

    @Test
    public void testEnByMd5File2String() {
    }

    @Test
    public void enByMd5File() {
    }

    @Test
    public void enBySha1ToString() {
    }

    @Test
    public void testEnBySha1ToString() {
    }

    @Test
    public void enBySha1() {
    }

    @Test
    public void enBySha224ToString() {
    }

    @Test
    public void testEnBySha224ToString() {
    }

    @Test
    public void enBySha224() {
    }

    @Test
    public void enBySha256ToString() {
    }

    @Test
    public void testEnBySha256ToString() {
    }

    @Test
    public void enBySha256() {
    }

    @Test
    public void enBySha384ToString() {
    }

    @Test
    public void testEnBySha384ToString() {
    }

    @Test
    public void enBySha384() {
    }

    @Test
    public void enBySha512ToString() {
    }

    @Test
    public void testEnBySha512ToString() {
    }

    @Test
    public void enBySha512() {
    }

    @Test
    public void enByHmacMd5ToString() {
    }

    @Test
    public void testEnByHmacMd5ToString() {
    }

    @Test
    public void enByHmacMd5() {
    }

    @Test
    public void enByHmacSha1ToString() {
    }

    @Test
    public void testEnByHmacSha1ToString() {
    }

    @Test
    public void enByHmacSha1() {
    }

    @Test
    public void enByHmacSha224ToString() {
    }

    @Test
    public void testEnByHmacSha224ToString() {
    }

    @Test
    public void enByHmacSha224() {
    }

    @Test
    public void enByHmacSha256ToString() {
    }

    @Test
    public void testEnByHmacSha256ToString() {
    }

    @Test
    public void enByHmacSha256() {
    }

    @Test
    public void enByHmacSha384ToString() {
    }

    @Test
    public void testEnByHmacSha384ToString() {
    }

    @Test
    public void enByHmacSha384() {
    }

    @Test
    public void enByHmacSha512ToString() {
    }

    @Test
    public void testEnByHmacSha512ToString() {
    }

    @Test
    public void enByHmacSha512() {
    }

    @Test
    public void enByDes2Base64() {
    }

    @Test
    public void enByDes2HexString() {
    }

    @Test
    public void enByDes() {
    }

    @Test
    public void decBase64Des() {
    }

    @Test
    public void decHexStringDes() {
    }

    @Test
    public void decDes() {
    }

    @Test
    public void enBy3Des2Base64() {
    }

    @Test
    public void enBy3Des2HexString() {
    }

    @Test
    public void enBy3Des() {
    }

    @Test
    public void decBase64_3Des() {
    }

    @Test
    public void decHexString3Des() {
    }

    @Test
    public void dec3Des() {
    }

    @Test
    public void enByAes2Base64() {
    }

    @Test
    public void enByAes2HexString() {
    }

    @Test
    public void enByAes() {
    }

    @Test
    public void decBase64Aes() {
    }

    @Test
    public void decHexStringAes() {
    }

    @Test
    public void decAes() {
    }

    @Test
    public void encryptRSA2Base64() {
    }

    @Test
    public void encryptRSA2HexString() {
    }

    @Test
    public void encryptRSA() {
    }

    @Test
    public void decryptBase64RSA() {
    }

    @Test
    public void decryptHexStringRSA() {
    }

    @Test
    public void decryptRSA() {
    }

    @Test
    public void rc4() {
    }
}