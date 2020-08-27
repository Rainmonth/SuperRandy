package com.rainmonth.common.thread;

import java.util.concurrent.ThreadFactory;

/**
 * 线程工厂
 * 1、用来创建线程的；
 * 2、创建的线程良好的日志支持；
 * 3、创建的线程数良好的控制；
 */
public class SrThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
