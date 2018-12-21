package com.rainmonth.common.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @date: 2018-12-21
 * @author: randy
 * @description: TreadService对外暴露的接口
 */
public interface IThread {

    /**
     * 执行网络任务
     * @param task 具体任务
     * @param name 任务名
     */
    void executeNetTask(Runnable task, String name);

    /**
     * 执行网络任务
     * @param task
     * @param name
     * @param <T>
     * @return
     */
    <T> Future<T> executeNetTask(Callable<T> task, String name);

    void executeDaemonTask(Runnable task, String name);

    <T> Future<T> executeDaemonTask(Callable<T> task, String name);

}