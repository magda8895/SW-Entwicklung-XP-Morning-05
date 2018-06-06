package com.thestreetcodecompany.roady;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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
public class StopWatchUiTest {

    Calendar calendar;

    @Rule
    public ActivityTestRule<StopWatch> mActivityRule =
            new ActivityTestRule<>(StopWatch.class, true, false);

    @Before
    public void initValidString() {
        calendar = Calendar.getInstance();
    }


    @Test
    public void app_context_test() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.thestreetcodecompany.roady", appContext.getPackageName());
    }

    @Test
    public void SaveData() {
        Intent intent = new Intent();
        intent.putExtra("StartTime","123");
        intent.putExtra("from_NDS_to_SW","123");
        mActivityRule.launchActivity(intent);
        UiTestsHelper.getBackToMileageChange(R.id.finishchronometer);
    }

    @Test
    public void getBack() {
        Intent intent = new Intent();
        intent.putExtra("StartTime","123");
        intent.putExtra("from_NDS_to_SW","123");
        mActivityRule.launchActivity(intent);
        UiTestsHelper.SaveData(R.id.backButton);
    }



}

