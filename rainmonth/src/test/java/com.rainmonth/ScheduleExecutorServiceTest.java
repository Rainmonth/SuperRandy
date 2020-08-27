package com.rainmonth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ScheduleExecutorServiceTest {

    @Before
    public void beginTest() {
        System.out.println("------begin test------");
    }

    @Test
    public void scheduleExecutorServiceFixedRateTest() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("TAG->run fixedRate:" + System.currentTimeMillis());
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

    }

    @Test
    public void scheduleExecutorServiceFixedDelayTest() {


        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("TAG->run fixedDelay:" + System.currentTimeMillis());
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void beepForAnHour() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable beeper = new Runnable() {
            public void run() {
                System.out.println("beepForAnHour, run:" + System.currentTimeMillis());
            }
        };
        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 1000, 1000, TimeUnit.MILLISECONDS);


//        scheduler.schedule(new Runnable() {
//            public void run() {
//                System.out.println("beepForAnHour, cancel:" + System.currentTimeMillis());
//                beeperHandle.cancel(true);
//            }
//        }, 60 * 60, TimeUnit.SECONDS);
//        while (true) {
//
//        }
//        for (; ; ) {
//
//        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @After
    public void endTest() {
        System.out.println("end test");
    }
}