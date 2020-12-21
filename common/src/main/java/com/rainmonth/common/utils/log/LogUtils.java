package com.rainmonth.common.utils.log;


import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.rainmonth.common.utils.log.internal.BaseLog;
import com.rainmonth.common.utils.log.internal.FileLog;
import com.rainmonth.common.utils.log.internal.JsonLog;
import com.rainmonth.common.utils.log.internal.XmlLog;

import java.io.File;

/**
 * This is a Log tool，with this you can the following
 * <ol>
 * <li>use KLog.d(),you could print whether the method execute,and the default tag is current class's name</li>
 * <li>use KLog.d(msg),you could print log as before,and you could location the method with a click in Android Studio Logcat</li>
 * <li>use KLog.json(),you could print json string with well format automatic</li>
 * </ol>
 * 扩展功能，添加对文件的支持
 * 扩展功能，增加对XML的支持，修复BUG
 * 扩展功能，添加对任意参数的支持
 * 扩展功能，增加对无限长字符串支持
 * 扩展功能，添加对自定义全局Tag的支持
 * 扩展功能，添加日志等级的限制
 */
public class LogUtils {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String NULL_TIPS = "Log with null object";

    private static final String DEFAULT_MESSAGE = "execute";
    private static final String PARAM = "Param";
    private static final String NULL = "null";
    private static final String TAG_DEFAULT = "RdLog";
    private static final String SUFFIX = ".java";
    private static final String PREFIX = "Rd_";      // tag 前缀

    public static final int JSON_INDENT = 4;
    public static final int V = 0x1;

    public static final int D = 0x2;
    public static final int I = 0x3;
    public static final int W = 0x4;
    public static final int E = 0x5;
    public static final int A = 0x6;

    private static final int JSON = 0x7;
    private static final int XML = 0x8;

    private static final int STACK_TRACE_INDEX = 5;

    private static String mGlobalTag;                  // 全局tag
    private static boolean mIsGlobalTagEmpty = true;    // 全局tag是否为空
    private static int mLogLevel = A;       // 日志等级
    private static boolean IS_SHOW_LOG = true;    // 是否显示Log

    public static void init(boolean isShowLog) {
        IS_SHOW_LOG = isShowLog;
        mLogLevel = A;
    }

    public static void init(boolean isShowLog, @Nullable String tag) {
        IS_SHOW_LOG = isShowLog;
        mGlobalTag = tag;
        mIsGlobalTagEmpty = TextUtils.isEmpty(mGlobalTag);
        mLogLevel = A;
    }

    public static void v() {
        printLog(V, null, true, DEFAULT_MESSAGE);
    }

    public static void v(Object msg) {
        printLog(V, null, true, msg);
    }

    public static void v(String tag, Object... objects) {
        printLog(V, tag, true, objects);
    }

    public static void d() {
        printLog(D, null, true, DEFAULT_MESSAGE);
    }

    public static void d(Object msg) {
        printLog(D, null, true, msg);
    }

    public static void d(String tag, Object... objects) {
        printLog(D, tag, true, objects);
    }

    public static void i() {
        printLog(I, null, true, DEFAULT_MESSAGE);
    }

    public static void i(Object msg) {
        printLog(I, null, true, msg);
    }

    public static void i(String tag, Object... objects) {
        printLog(I, tag, true, objects);
    }

    public static void w() {
        printLog(W, null, true, DEFAULT_MESSAGE);
    }

    public static void w(Object msg) {
        printLog(W, null, true, msg);
    }

    public static void w(String tag, Object... objects) {
        printLog(W, tag, true, objects);
    }

    public static void e() {
        printLog(E, null, true, DEFAULT_MESSAGE);
    }

    public static void e(Object msg) {
        printLog(E, null, true, msg);
    }

    public static void e(String tag, Object... objects) {
        printLog(E, tag, true, objects);
    }

    public static void a() {
        printLog(A, null, true, DEFAULT_MESSAGE);
    }

    public static void a(Object msg) {
        printLog(A, null, true, msg);
    }

    public static void a(String tag, Object... objects) {
        printLog(A, tag, true, objects);
    }

    public static void json(String jsonFormat) {
        printLog(JSON, null, true, jsonFormat);
    }

    public static void json(String tag, String jsonFormat) {
        printLog(JSON, tag, true, jsonFormat);
    }

    public static void xml(String xml) {
        printLog(XML, null, true, xml);
    }

    public static void xml(String tag, String xml) {
        printLog(XML, tag, true, xml);
    }

    public static void file(File targetDirectory, Object msg) {
        printFile(null, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, Object msg) {
        printFile(tag, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, String fileName, Object msg) {
        printFile(tag, targetDirectory, fileName, msg);
    }

    private static void printLog(int type, String tagStr, boolean defaultWrap, Object... objects) {

        if (!IS_SHOW_LOG) {
            return;
        }
        // json 形式的不打印方法堆栈
        defaultWrap = (type == JSON || type == XML) || defaultWrap;
        String[] contents = wrapperContent(tagStr, defaultWrap, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        switch (type) {
            case V:
            case D:
            case I:
            case W:
            case E:
            case A:
                if (type <= mLogLevel) {
                    BaseLog.printDefault(type, tag, headString + msg);
                }
                break;
            case JSON:
                JsonLog.printJson(tag, msg, headString);
                break;
            case XML:
                XmlLog.printXml(tag, msg, headString);
                break;
        }
    }


    private static void printFile(String tagStr, File targetDirectory, String fileName, Object objectMsg) {

        if (!IS_SHOW_LOG) {
            return;
        }

        String[] contents = wrapperContent(tagStr, objectMsg);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        FileLog.printFile(tag, targetDirectory, fileName, headString, msg);
    }

    /**
     * @param tagStr      tag
     * @param defaultWrap 是否采用默认的信息包装方式，true表示采用默认的，false表示采用打印调用堆栈的
     * @param objects     要包装的信息
     */
    private static String[] wrapperContent(String tagStr, boolean defaultWrap, Object... objects) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement targetElement = stackTrace[STACK_TRACE_INDEX];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }

        if (className.contains("$")) {
            className = className.split("\\$")[0] + SUFFIX;
        }

        String methodName = targetElement.getMethodName();
        String methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }

        String tag = getTag(tagStr);
        String msg = (objects == null) ? NULL_TIPS : getObjectsString(objects);

        if (defaultWrap) {
            String headString = "[ (" + className + ":" + lineNumber + ")#" + methodNameShort + " ] ";
            return new String[]{tag, msg, headString};
        } else {
            StringBuilder sb = new StringBuilder();
            appendStack(sb);
            return new String[]{tag, msg, sb.toString()};
        }
    }

    private static String[] wrapperContent(String tagStr, Object... objects) {
        return wrapperContent(tagStr, true, objects);
    }

    /**
     * 获取 正确的tag
     *
     * @param tagStr 传递过来的饿tag
     * @return 包装后的tag
     */
    private static String getTag(String tagStr) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement targetElement = stackTrace[STACK_TRACE_INDEX];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }

        if (className.contains("$")) {
            className = className.split("\\$")[0] + SUFFIX;
        }
        String tag = (tagStr == null ? className : tagStr);
        if (mIsGlobalTagEmpty && TextUtils.isEmpty(tag)) {
            tag = TAG_DEFAULT;
        } else if (!mIsGlobalTagEmpty) {
            tag = mGlobalTag;
        }

        tag = PREFIX + tag;
        return tag;
    }

    private static String getObjectsString(Object... objects) {

        if (objects.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(NULL).append("\n");
                } else {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(object.toString()).append("\n");
                }
            }
            return stringBuilder.toString();
        } else {
            Object object = objects[0];
            return object == null ? NULL : object.toString();
        }
    }

    public static void printStackTrace(Throwable e) {
        printStackTrace(E, "Exception", e);
    }

    public static void printStackTrace(int type, String tag, Throwable e) {
        StringBuilder sb = new StringBuilder();
        appendStack(sb);
        sb.append(e.getMessage());
        sb.append("\n");
        if (e != null) {
            sb.append(Log.getStackTraceString(e)).append("\n");
        }

        printLog(type, tag, true, sb.toString());
    }

    private static final int START_STACK_INDEX = 5;
    private static final int PRINT_STACK_MAX_COUNT = 2;

    private static void appendStack(StringBuilder sb) {
        if (PRINT_STACK_MAX_COUNT <= 0) {
            return;
        }

        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        if (stacks != null && stacks.length > START_STACK_INDEX) {
            int lastIndex = Math.min(stacks.length - 1, START_STACK_INDEX + PRINT_STACK_MAX_COUNT);
            for (int i = lastIndex; i > START_STACK_INDEX; i--) {
                if (stacks[i] == null) {
                    continue;
                }

                String className = stacks[i].getClassName();
                if (className != null) {
                    int dotIndx = className.lastIndexOf('.');
                    if (dotIndx > 0) {
                        className = className.substring(dotIndx + 1);
                    }
                }

                sb.append(className);
                sb.append('(');
                sb.append(stacks[i].getLineNumber());
                sb.append(")");
                sb.append("->");
            }

            String className = stacks[START_STACK_INDEX].getClassName();
            if (className != null) {
                int dotIndx = className.lastIndexOf('.');
                if (dotIndx > 0) {
                    className = className.substring(dotIndx + 1);
                }
            }

            sb.append(className);
            sb.append('#');
            sb.append(stacks[START_STACK_INDEX].getMethodName());
            sb.append('(');
            sb.append(stacks[START_STACK_INDEX].getLineNumber());
            sb.append(")");
            sb.append(": ");
        }
    }

    public static void stackV(String tag, Object... objects) {
        printLog(V, tag, false, objects);
    }

    public static void stackD(String tag, Object... objects) {
        printLog(D, tag, false, objects);
    }

    public static void stackI(String tag, Object... objects) {
        printLog(I, tag, false, objects);
    }

    public static void stackW(String tag, Object... objects) {
        printLog(W, tag, false, objects);
    }

    public static void stackE(String tag, Object... objects) {
        printLog(E, tag, false, objects);
    }

}
