package com.rainmonth.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.TreeSet;

/**
 * 运行时异常捕获处理，当程序发生Uncaught异常的时候,有该类 来接管程序,并记录 发送错误报告
 * 使用方式：
 * CrashHandler.getInstance().init(getApplicationContext());
 * CrashHandler.getInstance().sendPreviousReportsToServer();//发送以前没发送的报告(可选)
 */
public class CrashHandler implements UncaughtExceptionHandler {
    /**
     * Debug Log tag
     */
    private static final String TAG = "CrashHandler";
    /**
     * 是否开启日志输出,在Debug状态下开启, 在Release状态下关闭以提示程序性能
     */
    private static final boolean DEBUG = true;
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;
    /**
     * CrashHandler实例
     */
    @SuppressLint("StaticFieldLeak")
    private static CrashHandler INSTANCE;
    /**
     * 程序的Context对象
     */
    private Context mContext;

    private String PATH;

    /**
     * 使用Properties来保存设备的信息和错误堆栈信息
     */
    private Properties mDeviceCrashInfo = new Properties();

    private static final String DEVICE_VENDOR = "DEVICE_VENDOR";
    private static final String DEVICE_MODEL = "DEVICE_MODEL";
    private static final String DEVICE_CPU_ABI = "DEVICE_CPU_ABI";
    private static final String ANDROID_OS = "ANDROID_OS";
    private static final String ANDROID_SDK = "ANDROID_SDK";
    private static final String VERSION_NAME = "VERSION_NAME";
    private static final String VERSION_CODE = "VERSION_CODE";
    private static final String STACK_TRACE = "STACK_TRACE";
    /**
     * 错误报告文件的扩展名
     */
    private static final String CRASH_REPORTER_EXTENSION = ".txt";

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    public void init(Context ctx) {
        mContext = ctx;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            PATH = Environment.getExternalStorageDirectory().getPath() + File.separator
                    + mContext.getPackageName() + File.separator;
        }
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                // Sleep一会后结束程序
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Error : ", e);
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex exception对象
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }
        // 收集设备信息
        collectCrashDeviceInfo(mContext);
        // 保存错误报告文件
        final String crashFileName = saveCrashInfoToFile(ex);
        // 发送错误报告到服务器
        sendCrashReportsToServer(mContext);
        final String msg = ex.getLocalizedMessage();
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                if (!CommonUtils.isNullOrEmpty(PATH)) {
                    Toast.makeText(mContext, "程序出错啦, 异常文件已保存至" + PATH + crashFileName, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, "程序出错啦:" + msg, Toast.LENGTH_LONG).show();
                }
                Looper.loop();
            }

        }.start();
        return true;
    }

    /**
     * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
     */
    public void sendPreviousReportsToServer() {
        sendCrashReportsToServer(mContext);
    }

    /**
     * 把错误报告发送给服务器,包含新产生的和以前没发送的.
     *
     * @param ctx context对象
     */
    private void sendCrashReportsToServer(Context ctx) {
        String[] crFiles;
        if (!CommonUtils.isNullOrEmpty(PATH)) {
            crFiles = getCrashReportFilesFromSdcard(ctx);
        } else {
            crFiles = getCrashReportFilesFromAppData(ctx);
        }
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<String>();
            sortedFiles.addAll(Arrays.asList(crFiles));
            for (String fileName : sortedFiles) {
                File cr = new File(ctx.getFilesDir(), fileName);
                postExceptionReport(cr);
                cr.delete();// 删除已发送的报告
            }
        }
    }

    private void postExceptionReport(File file) {
        // TODO 使用HTTP Post 发送错误报告到服务器
        // 这里不再详述,开发者可以根据OPhoneSDN上的其他网络操作
        // 教程来提交错误报告
    }

    /**
     * 获取data区存储的错误报告文件名
     *
     * @param context context对象
     * @return 错误报告文件名数组
     */
    private String[] getCrashReportFilesFromAppData(Context context) {
        File filesDir = context.getFilesDir();
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        return filesDir.list(filter);
    }

    /**
     * 从SD卡拿report
     *
     * @param context context对象
     * @return 错误报告文件数组
     */
    private String[] getCrashReportFilesFromSdcard(Context context) {
        File fileDir = new File(PATH);
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        return fileDir.list(filter);
    }


    /**
     * 保存错误信息到文件中
     *
     * @param ex exception对象
     * @return 保存
     */
    private String saveCrashInfoToFile(Throwable ex) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);

        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        String result = info.toString();
        printWriter.close();
        mDeviceCrashInfo.put(STACK_TRACE, result);
        String fileName = "";
        try {
            long timestamp = System.currentTimeMillis();
            String time = new SimpleDateFormat("yyyy_MM_dd HH:MM:SS").format(new Date(timestamp));
            if (!CommonUtils.isNullOrEmpty(PATH)) {
                // sd 存在，写入sd卡
                File dir = new File(PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                fileName = "crash-" + time + CRASH_REPORTER_EXTENSION;
                File file = new File(PATH + fileName);
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                pw.println("CRASH_TIME-" + time);
                dumpPhoneInfo(pw);
                pw.println();
                ex.printStackTrace(pw);
                pw.close();
                Log.e(TAG, "writing report file success");
            } else {
                // SD卡不存在，
                fileName = "crash-" + time + CRASH_REPORTER_EXTENSION;
                FileOutputStream trace = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
                mDeviceCrashInfo.store(trace, "");
                trace.flush();
                trace.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occurred while writing report file..." + fileName, e);
        }
        return null;
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.println(VERSION_NAME + "-" + pi.versionName);
        pw.println(VERSION_CODE + "-" + pi.versionCode);
        //Android版本号
        pw.println(ANDROID_OS + "-" + Build.VERSION.RELEASE);
        pw.println(ANDROID_SDK + "-" + Build.VERSION.SDK_INT);
        //手机制造商
        pw.println(DEVICE_VENDOR + "-" + Build.MANUFACTURER);
        //手机型号
        pw.println(DEVICE_MODEL + "-" + Build.MODEL);
        //CPU架构
        pw.println(DEVICE_CPU_ABI + "-" + Build.CPU_ABI);
    }

    /**
     * 收集程序崩溃的设备信息
     *
     * @param ctx context对象
     */
    public void collectCrashDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" : pi.versionName);
                mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode);
                mDeviceCrashInfo.put(DEVICE_VENDOR, Build.MANUFACTURER);
                mDeviceCrashInfo.put(DEVICE_MODEL, Build.MODEL);
                mDeviceCrashInfo.put(ANDROID_OS, Build.VERSION.RELEASE);
                mDeviceCrashInfo.put(ANDROID_SDK, Build.VERSION.SDK_INT);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "Error while collect package info", e);
        }
        // 使用反射来收集设备信息.在Build类中包含各种设备信息,
        // 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        // 具体信息请参考后面的截图
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), field.get(null));
                if (DEBUG) {
                    Log.d(TAG, field.getName() + " : " + field.get(null));
                }
            } catch (Exception e) {
                Log.e(TAG, "Error while collect crash info", e);
            }

        }

    }

}