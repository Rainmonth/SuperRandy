package com.rainmonth.common.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SrScheduleThreadPoolExecutor {
    ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;

    public SrScheduleThreadPoolExecutor(String name) {
        this.mScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new SrThreadFactory(name));
    }

    void post(Runnable task, String name) {
        execute(task, name, 0, TimeUnit.MILLISECONDS);
    }

    void post(Runnable task, String name, long delay, TimeUnit timeUnit) {
        execute(task, name, delay, timeUnit);
    }

    <T> Future<T> post(Callable<T> task, String name) {
        RunnableFuture<T> futureTask = new FutureTask<>(task);
        execute(futureTask, name, 0, TimeUnit.MILLISECONDS);
        return futureTask;
    }

    <T> Future<T> post(Callable<T> task, String name, long delay, TimeUnit timeUnit) {
        RunnableFuture<T> futureTask = new FutureTask<>(task);
        execute(futureTask, name, delay, timeUnit);
        return futureTask;
    }

    void execute(Runnable task, String name, long delay, TimeUnit timeUnit) {
        NamedRunnable namedRunnable = new NamedRunnable(task, name);
        if (delay <= 0) {
            mScheduledThreadPoolExecutor.submit(namedRunnable);
            return;
        }
        mScheduledThreadPoolExecutor.schedule(namedRunnable, delay, timeUnit);
    }

    public void scheduleAtFixedDelay() {
//        mSchedule.scheduleWithFixedDelay()
    }

    public void scheduleAtFixedRate() {

    }
}
