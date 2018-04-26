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
        assertEquals("com.thestreetcodecompany.roady", appContext.getPackageName());
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
    public void setName() {
    UiTestsHelper.setText(R.id.editTextName, "Max Mustermann");
    }

    @Test
    public void setStartMileage() {
        UiTestsHelper.setText(R.id.editTextMileageStart, "123123");
    }

    @Test
    public void setEndMileage() {
        UiTestsHelper.setText(R.id.editTextMileageEnd, "9876543");
    }

    @Test
    public void setVehicle() {
        UiTestsHelper.setSpinnerwithoutData(R.id.spinnerVehicle);
    }

    @Test
    public void setCoDriver() {
        UiTestsHelper.setSpinner(R.id.spinnerCoDriver,"Carlos");
    }

    @Test
    public void setWeather() {
        UiTestsHelper.setSpinner(R.id.spinnerWeather, "Snow");

    }

    @Test
    public void setRoadCondition() {
        UiTestsHelper.setSpinner(R.id.spinnerRoadCondition, "Crowd");
    }


}

