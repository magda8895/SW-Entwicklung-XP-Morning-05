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
public class DrivingSessionAfterwardsUiTest {

    Calendar calendar;

    @Rule
    public ActivityTestRule<DrivingSessionAfter> mActivityRule =
            new ActivityTestRule<>(DrivingSessionAfter.class);


    @Before
    public void initValidString() {
        calendar = Calendar.getInstance();
    }

    @Test
    public void testSave() {
        onView(withText("Save"))
                .check(matches(isDisplayed()))
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled();
                    }

                    @Override
                    public String getDescription() {
                        return "click save button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                });
        UiTestsHelper.setText(R.id.editTextRoute, "Paris - Dakar");
        onView(withText("Save"))
                .check(matches(isDisplayed()))
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled();
                    }

                    @Override
                    public String getDescription() {
                        return "click save button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                });
        UiTestsHelper.setText(R.id.editTextMileageStart, "0");
        UiTestsHelper.setText(R.id.editTextMileageEnd, "100");
        onView(withText("Save"))
                .check(matches(isDisplayed()))
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled();
                    }

                    @Override
                    public String getDescription() {
                        return "click save button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                });
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

    /*
    @Test
    public void setName() {
    UiTestsHelper.setText(R.id.editTextRoute, "Max Mustermann");
    }
    */

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
        UiTestsHelper.setSpinnerwithoutData(R.id.spinnerCoDriver);
    }

   /* @Test
    public void setWeather() {
        UiTestsHelper.setSpinner(R.id.spinnerWeather, "Rain");
    }*/

    @Test
    public void setRoadCondition() {
        UiTestsHelper.setSpinner(R.id.spinnerRoadCondition, "Free");
    }

    @Test
    public void testFromStopWatch() {
        Intent i = new Intent();
        i.putExtra("from_SW_to_NDA", "123");
        i.putExtra("StartTime", "23423");
        i.putExtra("Pass", 1);
        mActivityRule.launchActivity(i);

    }




}

