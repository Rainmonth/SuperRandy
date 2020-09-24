package com.rainmonth.common.thread;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SrThreadPoolExecutor {

    private static final long DELAY_INTERVAL = 20;
    private static final int MSG_EXECUTE_TASK = 1;

    ThreadPoolExecutor mThreadPool;         // 线程池实例对象
    final BlockingQueue<Runnable> mBufferQueue;   // 线程池缓存队列

    private int mMaxSize;                   // 线程池最大线程数
    private String mName;                   // 线程池名称

    private HandlerThread mHandlerThread;   // 带Looper的Thread，该Looper可用来创建Handler，注意调用start方法
    private Handler mHandler;               //

    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_EXECUTE_TASK:
                    int count = mMaxSize - mThreadPool.getActiveCount();
                    for (int i = 0; i < count; i++) {
                        Runnable task;
                        synchronized (mBufferQueue) {
                            task = mBufferQueue.poll();
                        }
                        if (task == null) {
                            break;
                        }
                        if (task instanceof NamedRunnable) {
                            Log.d(IThread.TAG, "handleMessage: retry execute: " + ((NamedRunnable) task).getName());
                        }
                        mThreadPool.execute(task);
                    }
                    if (mBufferQueue.size() > 0) {
                        mHandler.removeMessages(MSG_EXECUTE_TASK);
                        mHandler.sendEmptyMessageDelayed(MSG_EXECUTE_TASK, DELAY_INTERVAL);
                    }

                    return true;
                default:

                    break;
            }
            return false;
        }
    };


    /**
     * 自定义拒绝策略
     * 当前线程池已经饱和时，在想线程池中添加任务会调用到这个方法。
     * 采用策略：
     * 现将任务添加到{@link SrThreadPoolExecutor#mBufferQueue} 中，{@link SrThreadPoolExecutor#DELAY_INTERVAL}
     * 时长后检查线程池中是否有空闲线程，有的话，从{@link SrThreadPoolExecutor#mBufferQueue} 中取出任务执行，没有则在下一次
     * {@link SrThreadPoolExecutor#DELAY_INTERVAL} 重新检测
     */
    class RejectedHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (r == null) {
                return;
            }
            if (r instanceof NamedRunnable) {
                Log.d(IThread.TAG, "rejectedExecution: " + ((NamedRunnable) r).getName());
            }
            synchronized (mBufferQueue) {
                if (!mBufferQueue.contains(r)) {
                    mBufferQueue.offer(r);
                }
            }

            mHandler.removeMessages(MSG_EXECUTE_TASK);
            mHandler.sendEmptyMessageDelayed(MSG_EXECUTE_TASK, DELAY_INTERVAL);
        }
    }

    public SrThreadPoolExecutor(int coreSize, int maxSize, String name) {
        mMaxSize = maxSize;
        mName = name;
        mThreadPool = getThreadPool(coreSize, maxSize, name);
        mBufferQueue = getBufferQueue();

        mHandlerThread = new HandlerThread(name + "_HandlerThread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper(), mCallback);
    }

    public ThreadPoolExecutor getThreadPool(int coreSize, int maxSize, String name) {
        return new ThreadPoolExecutor(coreSize,
                maxSize,
                10L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new SrThreadFactory(name),
                new RejectedHandler());
    }


    public BlockingQueue<Runnable> getBufferQueue() {
        return new LinkedBlockingQueue<>();
    }

    public void post(Runnable runnable, String name) {
        execute(runnable, name);
    }

    public <T> Future<T> post(Callable<T> task, String name) {
        RunnableFuture<T> futureTask = new FutureTask<T>(task);
        execute(futureTask, name);
        return futureTask;
    }

    void execute(Runnable task, String name) {
        NamedRunnable namedRunnable = new NamedRunnable(task, name);
        mThreadPool.execute(namedRunnable);
    }

    public void removeTask(Runnable task) {
        if (task == null) {
            return;
        }
        synchronized (mBufferQueue) {
            Iterator<Runnable> iterator = mBufferQueue.iterator();
            while (iterator.hasNext()) {
                Runnable runnable = iterator.next();
                if (runnable instanceof NamedRunnable) {
                    NamedRunnable namedRunnable = (NamedRunnable) runnable;
                    if (task.equals(namedRunnable)) {
                        mBufferQueue.remove(task);
                        return;
                    }
                }
            }
        }
    }

    public String dump() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Monitor Thread pool: ").append(mName);
        stringBuilder.append("; activityCount: ").append(mThreadPool.getActiveCount());
        stringBuilder.append("; poolSize: ").append(mThreadPool.getPoolSize());
        stringBuilder.append("; bufferSize: ").append(mBufferQueue.size());
        return stringBuilder.toString();
    }

    /**
     * 允许线程池核心线程超时
     */
    public void allowCoreThreadTimeOut() {
        if (mThreadPool != null) {
            mThreadPool.allowCoreThreadTimeOut(true);
        }
    }

}
