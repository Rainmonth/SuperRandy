package com.rainmonth.common.thread;

import android.os.Looper;
import android.util.Log;

/**
 * @date: 2018-12-21
 * @author: randy
 * @description: 线程辅助类
 */
public class ThreadHelper {
    public static final String TAG = ThreadHelper.class.getSimpleName();

    /**
     * Android判断是否是主线程
     *
     * @return true if current thread is main thread
     */
    public static boolean isMainThread() {
        return Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId();
    }

    /**
     * Java判断师傅是主线程
     *
     * @return true if current thread is main thread
     */
    public static boolean isMainThreadByJava() {
        return "main".equals(Thread.currentThread().getName());
    }

    /**
     * 打印当前线程的信息
     */
    public static void printCurrentThreadInfo() {
        Log.d(TAG, Thread.currentThread().toString());
    }


    private static boolean isGetCpuNumbers;
    private static int cpuNumbers;

    public static int getCpuNumbers() {
        if (isGetCpuNumbers) {
            return cpuNumbers;
        }
        try {
            cpuNumbers = Runtime.getRuntime().availableProcessors();
        } catch (Throwable ignore) {
            cpuNumbers = 1;
        }
        return cpuNumbers;
    }
}