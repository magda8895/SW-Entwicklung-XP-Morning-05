package com.thestreetcodecompany.roady;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.ListView;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.RoadyData;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StartInstrumentedTest {

    @Rule
    public ActivityTestRule<StartActivity> rule = new ActivityTestRule<StartActivity>(StartActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.thestreetcodecompany.roady", appContext.getPackageName());
    }

    @Test
    public void testProgressBar() {
        onView(withId(R.id.start_progressBar)).check(matches(isDisplayed()));
    }

    @Test
    public void testFloatingMenu() {
        onView(withId(R.id.start_floating_menu)).check(matches(isDisplayed()));
        onView(withId(R.id.start_floating_menu)).perform(click());
    }

    @Test
    public void testDistanceData() {
        RoadyData rd = RoadyData.getInstance();
        waitSeconds(2);
        onView(withText(((int)rd.user.getDrivenKm()) + " / " + ((int)rd.user.getGoalKm()) + " km")).check(matches(isDisplayed()));
    }

    private void waitSeconds(int seconds) {
        Calendar timeToWait = Calendar.getInstance();
        timeToWait.add(Calendar.SECOND, seconds);
        Calendar compareTime = Calendar.getInstance();

        while(!compareTime.equals(timeToWait))
            compareTime = Calendar.getInstance();
    }

    @Test
    public void testProgress() {
        onView(withId(R.id.start_progressBar)).check(matches(isDisplayed()));
    }

    @Test
    public void testListItemClick() {
        onView(withId(R.id.start_list)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.start_list)).atPosition(0).perform(click());
    }

    @Test
    public void testListData() {
        //get Data
        DBHandler dbh = new DBHandler();
        RoadyData rd = RoadyData.getInstance();
        final List<DrivingSession> sessions = rd.user.getAllDrivingSessions();
        onView(withId(R.id.start_list)).check(matches(isDisplayed()));
        onView(withId(R.id.start_list)).check(ViewAssertions.matches(Matchers.withListSize(sessions.size()+1)));
    }

    @Test
    public void testNavigationDrawer() throws Exception {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_achievements));
    }
}

