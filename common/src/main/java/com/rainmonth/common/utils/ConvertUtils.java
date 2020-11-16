package com.rainmonth.common.utils;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.rainmonth.common.utils.constant.MemoryConstants;
import com.rainmonth.common.utils.constant.TimeConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 转换工具类
 *
 * @author RandyZhang
 * @date 2020/11/12 2:57 PM
 */

public class ConvertUtils {

    private static final int    BUFFER_SIZE      = 8192;
    public static final  char[] HEX_DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final  char[] HEX_DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

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

    public static <T> T bytes2Parcelable(final byte[] bytes, final Parcelable.Creator<T> creator) {
        if (bytes == null) return null;
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        T result = creator.createFromParcel(parcel);
        parcel.recycle();
        return result;
    }

    public static byte[] parcelable2Bytes(final Parcelable parcelable) {
        if (parcelable == null) return null;
        Parcel parcel = Parcel.obtain();
        parcelable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static Object bytes2Object(final byte[] bytes) {
        if (bytes == null) return null;
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] serializable2Bytes(Serializable serializable) {
        if (serializable == null) return null;
        ByteArrayOutputStream baos;
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(baos = new ByteArrayOutputStream());
            oos.writeObject(serializable);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 内存单位成对应的字节数
     *
     * @param memorySize 内存大小
     * @param unit       单位
     * @return 对应字节数
     */
    public static long memorySize2Bytes(final long memorySize, @MemoryConstants.Unit final int unit) {
        if (memorySize < 0) return -1;
        return memorySize * unit;
    }

    /**
     * 字节数转换成内存单位描述
     *
     * @param byteSize 字节大小
     * @param unit     单位
     * @return 对应的内存大小
     */
    public static double byte2MemorySize(final long byteSize, @MemoryConstants.Unit final int unit) {
        if (byteSize < 0) return -1;
        return (double) byteSize / unit;
    }

    /**
     * Size of byte to fit size of memory.
     * <p>to three decimal places</p>
     *
     * @param byteSize Size of byte.
     * @return fit size of memory
     */
    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(final long byteSize) {
        return byte2FitMemorySize(byteSize, 3);
    }

    /**
     * Size of byte to fit size of memory.
     * <p>to three decimal places</p>
     *
     * @param byteSize  Size of byte.
     * @param precision The precision
     * @return fit size of memory
     */
    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(final long byteSize, int precision) {
        if (precision < 0) {
            throw new IllegalArgumentException("precision shouldn't be less than zero!");
        }
        if (byteSize < 0) {
            throw new IllegalArgumentException("byteSize shouldn't be less than zero!");
        } else if (byteSize < MemoryConstants.KB) {
            return String.format("%." + precision + "fB", (double) byteSize);
        } else if (byteSize < MemoryConstants.MB) {
            return String.format("%." + precision + "fKB", (double) byteSize / MemoryConstants.KB);
        } else if (byteSize < MemoryConstants.GB) {
            return String.format("%." + precision + "fMB", (double) byteSize / MemoryConstants.MB);
        } else {
            return String.format("%." + precision + "fGB", (double) byteSize / MemoryConstants.GB);
        }
    }

    /**
     * Time span in unit to milliseconds.
     *
     * @param timeSpan The time span.
     * @param unit     The unit of time span.
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return milliseconds
     */
    public static long timeSpan2Millis(final long timeSpan, @TimeConstants.Unit final int unit) {
        return timeSpan * unit;
    }

    /**
     * Milliseconds to time span in unit.
     *
     * @param millis The milliseconds.
     * @param unit   The unit of time span.
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}</li>
     *               <li>{@link TimeConstants#SEC }</li>
     *               <li>{@link TimeConstants#MIN }</li>
     *               <li>{@link TimeConstants#HOUR}</li>
     *               <li>{@link TimeConstants#DAY }</li>
     *               </ul>
     * @return time span in unit
     */
    public static long millis2TimeSpan(final long millis, @TimeConstants.Unit final int unit) {
        return millis / unit;
    }

    /**
     * Milliseconds to fit time span.
     *
     * @param millis    The milliseconds.
     *                  <p>millis &lt;= 0, return null</p>
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return fit time span
     */
    public static String millis2FitTimeSpan(long millis, int precision) {
        return UtilsBridge.millis2FitTimeSpan(millis, precision);
    }

    /**
     * Input stream to output stream.
     */
    public static ByteArrayOutputStream input2OutputStream(final InputStream is) {
        if (is == null) return null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[BUFFER_SIZE];
            int len;
            while ((len = is.read(b, 0, BUFFER_SIZE)) != -1) {
                os.write(b, 0, len);
            }
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Output stream to input stream.
     */
    public static ByteArrayInputStream output2InputStream(final OutputStream out) {
        if (out == null) return null;
        return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
    }

    /**
     * Input stream to bytes.
     */
    public static byte[] inputStream2Bytes(final InputStream is) {
        if (is == null) return null;
        return input2OutputStream(is).toByteArray();
    }

    /**
     * Bytes to input stream.
     */
    public static InputStream bytes2InputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        return new ByteArrayInputStream(bytes);
    }

    /**
     * Output stream to bytes.
     */
    public static byte[] outputStream2Bytes(final OutputStream out) {
        if (out == null) return null;
        return ((ByteArrayOutputStream) out).toByteArray();
    }

    /**
     * Bytes to output stream.
     */
    public static OutputStream bytes2OutputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            os.write(bytes);
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Input stream to string.
     */
    public static String inputStream2String(final InputStream is, final String charsetName) {
        if (is == null) return "";
        try {
            ByteArrayOutputStream baos = input2OutputStream(is);
            if (baos == null) return "";
            return baos.toString(getSafeCharset(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * String to input stream.
     */
    public static InputStream string2InputStream(final String string, final String charsetName) {
        if (string == null) return null;
        try {
            return new ByteArrayInputStream(string.getBytes(getSafeCharset(charsetName)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Output stream to string.
     */
    public static String outputStream2String(final OutputStream out, final String charsetName) {
        if (out == null) return "";
        try {
            return new String(outputStream2Bytes(out), getSafeCharset(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * String to output stream.
     */
    public static OutputStream string2OutputStream(final String string, final String charsetName) {
        if (string == null) return null;
        try {
            return bytes2OutputStream(string.getBytes(getSafeCharset(charsetName)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> inputStream2Lines(final InputStream is) {
        return inputStream2Lines(is, "");
    }

    public static List<String> inputStream2Lines(final InputStream is,
                                                 final String charsetName) {
        BufferedReader reader = null;
        try {
            List<String> list = new ArrayList<>();
            reader = new BufferedReader(new InputStreamReader(is, getSafeCharset(charsetName)));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
