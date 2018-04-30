package com.thestreetcodecompany.roady;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static junit.framework.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 *
 * @author fklezin
 * See the tutorial https://developer.android.com/training/testing/ui-testing/espresso-testing.html
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class InfosActivityUiTest {

    Calendar calendar;

    @Rule
    public ActivityTestRule<DrivingSessionAfter> mActivityRule =
            new ActivityTestRule(DrivingSessionAfter.class);

    @Before
    public void initValidString() {
        String x = "blank";
    }

    @Test
    public void checkUiElements() {

    }


}

