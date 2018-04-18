package com.thestreetcodecompany.roady;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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
    public void changeText_sameActivity() {
        //onView(withId(R.id.startDateTime)).perform(click());
        onView(withId(R.id.buttonDateStart)).perform(click());
    }

    @Test
    public void setStartDate_andStartTime() {
        onView(withId(R.id.buttonDateStart)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2018, 4,12 ));
        onView(withText("OK")).perform(click());

        onView(withId(R.id.buttonTimeStart)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(12,11));
        onView(withText("OK")).perform(click());

        Assert.assertEquals("asd","asd");
    }

}
