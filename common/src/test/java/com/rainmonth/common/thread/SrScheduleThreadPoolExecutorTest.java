package com.rainmonth.common.thread;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;

public class SrScheduleThreadPoolExecutorTest {
    @Test
    public void scheduleExecutorServiceFixedRateTest() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("TAG->run fixedRate, task finish at:" + formatCurrentTime());
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
        System.out.println("TAG->run fixedRate, schedule finish at: " + formatCurrentTime());
        while (true) {

        }
    }

    public static final TimeZone targetTimeZone = TimeZone.getTimeZone("GMT+8"); //设置为东八区

    private String formatCurrentTime() {
        return currentDate("HH:mm:ss");
    }

    protected static String currentDate(String formatString) {
        DateFormat format = new SimpleDateFormat(formatString);
        format.setTimeZone(targetTimeZone);
        Date date = new Date(currentTimeMillis());
        return format.format(date);
    }

    @Test
    public void scheduleExecutorServiceFixedDelayTest() {
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("TAG->run fixedDelay:" + formatCurrentTime());
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
        System.out.println("TAG->run fixedDelay, schedule start at: " + formatCurrentTime());
        while (true) {

        }
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

}