package com.rainmonth.common.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * {@link java.util.concurrent.Executors}
 *
 * @date: 2018-12-21
 * @author: randy
 * @description: 线程服务定义
 */
public class SrThreadService implements IThread {
    // 固定的线程池
    // 缓存线程池
    // 带优先级的线程池

    private int mCpuNumbers = ThreadHelper.getCpuNumbers();
    private SrThreadPoolExecutor mNetworkPool = new SrThreadPoolExecutor(Math.max(mCpuNumbers, 2) / 2, Math.max(mCpuNumbers, 10), "SrNetwork");
    private SrThreadPoolExecutor mDaemonPool = new SrThreadPoolExecutor(Math.max(mCpuNumbers, 2) / 2, Math.max(mCpuNumbers, 10), "SrDaemon");
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
}