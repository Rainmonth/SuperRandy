package com.rainmonth.common.thread;

import android.util.Log;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂
 * 1、用来创建线程的；
 * 2、创建的线程良好的日志支持；
 * 3、创建的线程数良好的控制；
 */
public class SrThreadFactory implements ThreadFactory {
    private static final String TAG = SrThreadFactory.class.getSimpleName();
    private final AtomicInteger threadNum = new AtomicInteger(0);
    private String mThreadFactoryName;

    public SrThreadFactory(String name) {
        this.mThreadFactoryName = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        int count = threadNum.getAndIncrement();
        String threadName = mThreadFactoryName + "-" + count;
        Log.v(IThread.TAG, "SrThreadFactory: newThread: " + threadName);
        SrThread srThread = new SrThread(r, threadName);

        if (srThread.isDaemon()) {
            srThread.setDaemon(false);
        }

        if (srThread.getPriority() != Thread.NORM_PRIORITY) {
            srThread.setPriority(Thread.NORM_PRIORITY);
        }
        return srThread;
    }
}
