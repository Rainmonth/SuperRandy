package com.rainmonth.common.thread;

import android.util.Log;

public class NamedRunnable implements Runnable {

    private Runnable mRunnable;
    private String mName;

    public NamedRunnable(Runnable runnable, String name) {
        this.mRunnable = runnable;
        this.mName = name;
    }

    public String getName() {
        return mName;
    }


    @Override
    public void run() {
        if (mRunnable != null) {
            Log.d(IThread.TAG, "namedRunnable start task: " + mName);
            long start = System.currentTimeMillis();
            mRunnable.run();
            long end = System.currentTimeMillis();
            Log.d(IThread.TAG, "task: " + mName + " executed " + (end - start) + "ms");
        }

    }
}
