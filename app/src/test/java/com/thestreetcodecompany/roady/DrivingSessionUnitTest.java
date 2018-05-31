package com.thestreetcodecompany.roady;

import junit.framework.Assert;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DrivingSessionUnitTest {

    @Test
    public void testFormatDateTimeTimestamp() {
        String dateTime = "12-12-2012 05:21:12";
        Date date = formatDateTimeDate(dateTime);
        long expected = 1355286072000L;
        Assert.assertEquals(expected,date.getTime());
    }

    @Test
    public void testFormatDateTimeTimestampInvalid() {
        String dateTime = "12-12-2s012 05:21:12x";
        Date date = formatDateTimeDate(dateTime);
        long expected = 1355286072000L;
        Assert.assertNotSame(expected,date.getTime());
    }


    public static Date formatDateTimeDate(String dateTime) {
        String pattern = "dd-MM-yyyy hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
