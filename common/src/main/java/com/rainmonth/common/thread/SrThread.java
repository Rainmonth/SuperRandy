package com.rainmonth.common.thread;

import android.util.Log;

/**
 * @date: 2018-12-21
 * @author: randy
 * @description: 线程封装
 */
public class SrThread extends Thread {
    private static final String TAG = SrThread.class.getSimpleName();

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
        try {
            super.run();
        } catch (Exception e) {
            Log.e(TAG, "error happened, error thread is " + getName());
        }
    }
}