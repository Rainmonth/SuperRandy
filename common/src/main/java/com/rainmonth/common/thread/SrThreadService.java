package com.rainmonth.common.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * {@link java.util.concurrent.Executors}
 *
 * @date: 2018-12-21
 * @author: randy
 * @description: 线程服务定义
 */
public class SrThreadService implements IThread {

    private static SrThreadService sInstance;

    public static SrThreadService getInstance() {
        synchronized (SrThreadService.class) {
            if (sInstance == null) {
                synchronized (SrThreadService.class) {
                    sInstance = new SrThreadService();
                }
            }
        }
        return sInstance;
    }

    private SrThreadService() {

    }

    /**
     * Cpu核心数
     */
    private int mCpuNumbers = ThreadHelper.getCpuNumbers();
    /**
     * 网络请求线程池
     */
    private SrThreadPoolExecutor mNetworkPool = new SrThreadPoolExecutor(Math.max(mCpuNumbers, 2) / 2, Math.max(mCpuNumbers, 10), "SrNetwork");
    /**
     * 耗时的后台任务线程池
     */
    private SrThreadPoolExecutor mDaemonPool = new SrThreadPoolExecutor(Math.max(mCpuNumbers, 2) / 2, Math.max(mCpuNumbers, 10), "SrDaemon");
    /**
     * 定时任务线程池
     */
    private SrScheduleThreadPoolExecutor mSchedulePool = new SrScheduleThreadPoolExecutor("SrSchedule");

    @Override
    public void executeNetTask(Runnable task, String name) {
        mNetworkPool.post(task, "Network_" + name);
    }

    @Override
    public <T> Future<T> executeNetTask(Callable<T> task, String name) {
        return mNetworkPool.post(task, "Network_" + name);
    }

    @Override
    public void executeDaemonTask(Runnable task, String name) {
        mDaemonPool.post(task, "Daemon_" + name);
    }

    @Override
    public <T> Future<T> executeDaemonTask(Callable<T> task, String name) {
        return mDaemonPool.post(task, "Daemon_" + name);
    }

    @Override
    public void executeScheduleTask(Runnable task, String name) {
        mSchedulePool.post(task, "Schedule_" + name);
    }

    @Override
    public void executeScheduleTask(Runnable task, String name, long delay, TimeUnit timeUnit) {
        mSchedulePool.post(task, "Schedule_" + name, delay, timeUnit);
    }

    @Override
    public <T> Future<T> executeScheduleTask(Callable<T> task, String name) {
        return mSchedulePool.post(task, "Schedule_" + name);
    }

    @Override
    public <T> Future<T> executeScheduleTask(Callable<T> task, String name, long delay, TimeUnit timeUnit) {
        return mSchedulePool.post(task, "Schedule_" + name, delay, timeUnit);
    }
}