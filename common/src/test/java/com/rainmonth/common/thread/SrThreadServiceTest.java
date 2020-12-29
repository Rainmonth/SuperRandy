package com.rainmonth.common.thread;

import org.junit.Test;

import static org.junit.Assert.*;

public class SrThreadServiceTest {
    SrThreadService srThreadService = new SrThreadService();

    @Test
    public void executeNetTask() {

        srThreadService.executeNetTask(new Runnable() {
            @Override
            public void run() {
                System.out.println("...");
            }
        }, "testNetRunnable");
    }

    @Test
    public void testExecuteNetTask() {
    }

    @Test
    public void executeDaemonTask() {
    }

    @Test
    public void testExecuteDaemonTask() {
    }

    @Test
    public void executeScheduleTask() {
    }

    @Test
    public void testExecuteScheduleTask() {
    }

    @Test
    public void testExecuteScheduleTask1() {
    }

    @Test
    public void testExecuteScheduleTask2() {
    }
}