package com.rainmonth.common.thread;

/**
 * @date: 2018-12-21
 * @author: randy
 * @description: 线程封装
 */
public class SrThread extends Thread {

    public SrThread(String name) {
        super();
        setName(name);

    }

    public SrThread(Runnable runnable, String name) {
        super(runnable);
        setName(name);
    }

    @Override
    public void run() {
        super.run();
    }
}