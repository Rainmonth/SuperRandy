package com.rainmonth.common.utils;

import com.rainmonth.common.BuildConfig;
import com.socks.library.KLog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

/**
 * @author RandyZhang
 * @date 2020/11/9 10:00 AM
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class EnDecUtilsTest {

    @Test
    public void enByBase64() {
        String source = "你好";
        try {
            System.out.println(source + "->Base64加密后：" + EnDecUtils.enByBase64(source));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void decByBase64() {
        String enSource = "5L2g5aW9";
        System.out.println(enSource + "->Base64解密后：" + EnDecUtils.decByBase64(enSource));
    }

    @Test
    public void getCharAsciiInt() {
        char ch = '=';
        System.out.println(ch + "对应的ASCII值为：" + EnDecUtils.getCharAsciiInt(ch));
        ch = 'A';
        System.out.println(ch + "对应的ASCII值为：" + (int) ch);
    }

}