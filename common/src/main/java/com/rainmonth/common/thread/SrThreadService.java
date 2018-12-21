package com.rainmonth.common.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @date: 2018-12-21
 * @author: randy
 * @description: 线程服务定义
 */
public class SrThreadService implements IThread{

    @Override
    public void executeNetTask(Runnable task, String name) {

    }

    @Override
    public <T> Future<T> executeNetTask(Callable<T> task, String name) {
        return null;
    }

    @Override
    public void executeDaemonTask(Runnable task, String name) {

    }

    @Override
    public <T> Future<T> executeDaemonTask(Callable<T> task, String name) {
        return null;
    }
}