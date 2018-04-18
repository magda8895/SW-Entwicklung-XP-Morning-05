package com.thestreetcodecompany.roady;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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

    private String dateToBeEntered;

    @Rule
    public ActivityTestRule<DrivingSessionAfter> mActivityRule =
            new ActivityTestRule(DrivingSessionAfter.class);

    @Before
    public void initValidString() {
        dateToBeEntered = "12.3.2018";
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


}
