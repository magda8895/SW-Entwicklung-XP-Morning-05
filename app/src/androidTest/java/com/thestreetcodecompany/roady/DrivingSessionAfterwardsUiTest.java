package com.thestreetcodecompany.roady;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;


import junit.framework.Assert;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
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
public class DrivingSessionAfterwardsUiTest {

    Calendar calendar;

    @Rule
    public ActivityTestRule<DrivingSessionAfter> mActivityRule =
            new ActivityTestRule(DrivingSessionAfter.class);

    @Before
    public void initValidString() {
        calendar = Calendar.getInstance();
    }

    @Test
    public void app_context_test() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com." +
         ".roady", appContext.getPackageName());
    }

    @Test
    public void setStartDateAndTime() {
        UiTestsHelper.setDate_andSetTime(R.id.buttonDateStart,R.id.buttonTimeStart,2018,3,2,22,12);
    }

    @Test
    public void setEndDateAndTime() {
        UiTestsHelper.setDate_andSetTime(R.id.buttonDateEnd,R.id.buttonTimeEnd,2018,3,2,23,50);
    }

    @Test
    public void setStartMileage() {
        onView(withId(R.id.editTextMileageStart))
                .perform(typeText("123123"), closeSoftKeyboard())
                .check(matches(withText("123123")));
    }

    @Test
    public void setEndMileage() {
        onView(withId(R.id.editTextMileageEnd))
                .perform(typeText("9876543"), closeSoftKeyboard())
                .check(matches(withText("9876543")));
    }
    @Test
    public void setVehicle() {
        onView(withId(R.id.spinnerVehicle))
                .perform(click());
    }

    @Test
    public void setCoDriver() {
        onView(withId(R.id.spinnerCoDriver))
                .perform(click());
    }

    @Test
    public void setWeather() {
        onView(withId(R.id.spinnerWeather))
                .perform(click());
        onView(withText("Snow")).perform(click())
                .check(matches(withText("Snow")));

    }

    @Test
    public void setRoadCondition() {
        onView(withId(R.id.spinnerRoadCondition))
                .perform(click());
        onView(withText("Crowd")).perform(click())
                .check(matches(withText("Crowd")));

    }


}

