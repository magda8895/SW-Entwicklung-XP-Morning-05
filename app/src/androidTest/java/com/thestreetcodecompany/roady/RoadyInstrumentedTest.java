package com.thestreetcodecompany.roady;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RoadyInstrumentedTest {

    @Rule
    public ActivityTestRule<SettingsBackend> activityRule = new ActivityTestRule<>(SettingsBackend.class);

    @Test
    public void useAppContext() throws Exception {

        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.thestreetcodecompany.roady", appContext.getPackageName());
    }

    @Test
    public void testSettingButtons() throws Exception {
        onView(withId(R.id.nameLabel)).perform(clearText());
        onView(withId(R.id.nameLabel)).perform(typeText("Alex"), closeSoftKeyboard());
        onView(withId(R.id.nameLabel)).perform(typeText("Christina"), closeSoftKeyboard());
        onView(withId(R.id.saveSettings)).perform(scrollTo(), click());
    }

    @Test
    public void addNewUser() throws Exception {
        onView(withId(R.id.nameLabel)).perform(clearText());
        onView(withId(R.id.nameLabel)).perform(typeText("Max Mustermann"), closeSoftKeyboard());
        onView(withId(R.id.saveSettings))
                .perform(scrollTo(), click());
        onView(withId(R.id.saveSettings)).perform(click());
    }

    @Test
    public void addNewAchievement() throws Exception {
        onView(withId(R.id.achievementsUserCreatedTitle)).perform(scrollTo());
        onView(withId(R.id.achievementsUserCreatedTitle)).perform(clearText());
        onView(withId(R.id.achievementsUserCreatedTitle)).perform(typeText("Star, 2, 1000, /, false"), closeSoftKeyboard());
        onView(withId(R.id.achievementUserCreatedAdd)).perform(scrollTo(), click());
    }

    @Test
    public void addNewCoDriver() throws Exception {
        onView(withId(R.id.coDriver)).perform(scrollTo());
        onView(withId(R.id.coDriverTitle)).perform(clearText());
        onView(withId(R.id.coDriverTitle)).perform(typeText("Martina Musterfrau"), closeSoftKeyboard());
        onView(withId(R.id.coDriverAdd)).perform(scrollTo(), click());
    }

    @Test
    public void addNewCar() throws Exception {
        onView(withId(R.id.car)).perform(scrollTo());
        onView(withId(R.id.carLabel)).perform(clearText());
        onView(withId(R.id.carLabel)).perform(typeText("Mustang, G-111HA"), closeSoftKeyboard());
        onView(withId(R.id.carAdd)).perform(scrollTo(), click());
    }

}
