package com.rainmonth.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author RandyZhang
 * @date 2020/11/12 4:35 PM
 */
public class ConvertUtilsTest {

    @Test
    public void int2HexString() {
        assertEquals("int2HexString()未通过", "20", ConvertUtils.int2HexString(32));
        assertEquals("int2HexString()未通过", "ff", ConvertUtils.int2HexString(255));
    }

    @Test
    public void hexString2Int() {
        assertEquals("hexString2Int()未通过", 255, ConvertUtils.hexString2Int("ff"));
    }

    @Test
    public void bytes2Bits() {
        // -129~127之间的整数有对应的byte值
        System.out.println(ConvertUtils.bytes2Bits(new byte[]{'a', '1', -12, (byte) 254}));
    }

    @Test
    public void bits2Bytes() {
        System.out.println(ConvertUtils.bytes2Bits(ConvertUtils.bits2Bytes("1000010101010")));
    }

    @Test
    public void bytes2Chars() {
        System.out.println(ConvertUtils.bytes2Chars(new byte[]{'a', '1', 65, (byte) 254}));
    }

    @Test
    public void chars2Bytes() {
        System.out.println(new String(ConvertUtils.chars2Bytes(new char[]{'a', '1', 'A', 'B' })));
    }

    @Test
    public void bytes2HexString() {
        System.out.println(ConvertUtils.bytes2HexString(new byte[]{'a', 'A' }));
        System.out.println(ConvertUtils.bytes2HexString("aA".getBytes()));
    }

    @Test
    public void hexString2Bytes() {
        System.out.println(new String(ConvertUtils.hexString2Bytes("41")));
    }
}