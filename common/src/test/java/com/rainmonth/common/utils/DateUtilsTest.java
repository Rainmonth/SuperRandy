package com.rainmonth.common.utils;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void getNowDate() {
        System.out.println(DateUtils.getNowDate());
        assertNotEquals(null, DateUtils.getNowDate());
    }

    @Test
    public void getNowDateShort() {
        System.out.println(DateUtils.getNowDateShort());
        assertNotEquals(null, DateUtils.getNowDateShort());
    }

    @Test
    public void getStringDate() {
        System.out.println(DateUtils.getStringDate());
    }

    @Test
    public void getStringDateShort() {
        System.out.println(DateUtils.getStringDateShort());
    }

    @Test
    public void getTimeShort() {
        System.out.println(DateUtils.getTimeShort());
    }

    @Test
    public void strToDateLong() {
        System.out.println(DateUtils.strToDateLong("2020-08-28 16:21:24"));
    }

    @Test
    public void dateToStrLong() {
        System.out.println(DateUtils.dateToStrLong(new Date()));
    }

    @Test
    public void dateToStr() {
        System.out.println(DateUtils.dateToStr(new Date()));
    }

    @Test
    public void dateToStrWithFormat() {
    }

    @Test
    public void strToDate() {
    }

    @Test
    public void strToDateWithFormat() {
    }

    @Test
    public void getNow() {
    }

    @Test
    public void getLastDate() {
    }

    @Test
    public void getStringToday() {
    }

    @Test
    public void getHour() {
        System.out.println(DateUtils.getHour());
    }

    @Test
    public void getTime() {
    }

    @Test
    public void getUserDate() {
    }

    @Test
    public void getDiffOfTwoHour() {

        System.out.println(DateUtils.getDiffOfTwoHour("16:43", "12:43"));
    }

    @Test
    public void getTwoDay() {
    }

    @Test
    public void isTheDateStringToday() {
    }

    @Test
    public void getPreTime() {
    }

    @Test
    public void getNextDay() {
    }

    @Test
    public void isLeapYear() {
    }

    @Test
    public void getEDate() {
    }

    @Test
    public void getEndDateOfMonth() {
    }

    @Test
    public void isSameWeekDates() {
    }

    @Test
    public void getSeqWeek() {
    }

    @Test
    public void getWeek() {
    }

    @Test
    public void testGetWeek() {
    }

    @Test
    public void getWeekStr() {
    }

    @Test
    public void getDays() {
    }

    @Test
    public void getNowMonth() {
    }

    @Test
    public void getNo() {
    }

    @Test
    public void getRandom() {
    }

    @Test
    public void rightDate() {
    }

    @Test
    public void showStyleConvert() {
    }

    @Test
    public void dateShowStyleConvert() {
    }

    @Test
    public void isDateStrToday() {
    }

    @Test
    public void stringForTime() {
    }
}