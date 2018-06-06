package com.thestreetcodecompany.roady;

import junit.framework.Assert;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StopWatchUnitTest {

    @Test
    public void testcalcDiffHour() {
        long current = StopWatch.getDiff(new SimpleDateFormat("HH"),"12", "14", 0);
        long expected = 7200000;
        Assert.assertEquals(expected,current);
    }

    @Test
    public void testcalcDiffMin() {
        long current = StopWatch.getDiff(new SimpleDateFormat("mm"),"12", "14", 0);
        long expected = 120000;
        Assert.assertEquals(expected,current);
    }

    @Test
    public void testcalcDiffSec() {
        long current = StopWatch.getDiff(new SimpleDateFormat("ss"),"12", "14", 0);
        long expected = 2000;
        Assert.assertEquals(expected,current);
    }


}
