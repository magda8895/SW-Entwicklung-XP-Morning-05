package com.thestreetcodecompany.roady;

        import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListView;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
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
    public ActivityTestRule<StartActivity> rule  = new  ActivityTestRule<StartActivity>(StartActivity.class);

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
        DBHandler dbh = new DBHandler();
        User user = dbh.getTestUser();
        onView(withText(user.getDrivenKm() + "/" + user.getGoalKm() + " km")).check(matches(isDisplayed()));
    }
    @Test
    public void testProgress() {
        onView(withId(R.id.start_progressBar)).check(matches(isDisplayed()));
    }

    @Test
    public void testListItemClick() {
        onView(withId(R.id.start_list)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.start_list)).atPosition(0).perform(click());
        onView(withText("Klick Item: index: 0")).check(matches(isDisplayed()));
    }

    @Test
    public void testListData() {
        //get Data
        DBHandler dbh = new DBHandler();
        User user = dbh.getTestUser();
        final List<DrivingSession> sessions = dbh.getAllDrivingSessions(user);
        onView(withId(R.id.start_list)).check(matches(isDisplayed()));
        onView (withId (R.id.start_list)).check (ViewAssertions.matches (Matchers.withListSize (sessions.size())));
    }
    @Test
    public void testNavigation() {
        onView(withId(R.id.drawer_layout)).perform(swipeRight());
    }

}

class Matchers {
    public static Matcher<View> withListSize (final int size) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText ("Driving Session must have " + size + " items");
            }

            @Override public boolean matchesSafely (final View view) {
                return ((ListView) view).getCount () == size;
            }

        };
    }
}