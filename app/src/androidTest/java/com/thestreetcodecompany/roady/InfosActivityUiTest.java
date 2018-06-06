package com.thestreetcodecompany.roady;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 *
 * @author fklezin
 * See the tutorial https://developer.android.com/training/testing/ui-testing/espresso-testing.html
 */
@RunWith(AndroidJUnit4.class)
public class InfosActivityUiTest {

    @Rule
    public ActivityTestRule<InfosActivity> mActivityRule = new ActivityTestRule<>(InfosActivity.class, true, true);

    @Before
    public void init() {
        DBHandler dbh = new DBHandler();
        if(dbh.getAllDrivingSessions(dbh.getUser()).size()==0) dbh.makeTestData();
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Assert.assertEquals("com.thestreetcodecompany.roady", appContext.getPackageName());
    }

    @Test
    public void testContent() {
        DBHandler dbh = new DBHandler();
        Calendar c;
        c = Calendar.getInstance();
        Date startDate, endDate;
        c.set(Calendar.DAY_OF_YEAR, 1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        startDate = c.getTime();
        c.add(Calendar.YEAR, 1);
        endDate = c.getTime();
        List<DrivingSession> sessions = DrivingSession.getAllDrivingSessionsTimePeriod(dbh.getUser(), startDate, endDate);
        float sum = 0;
        for(DrivingSession session: sessions) {
            sum += session.getDistance();
        }
        onView(withId(R.id.total_text)).check(matches(withText((int)sum+"km")));
        if(sessions.size()!=0) onView(withId(R.id.average_text)).check(matches(withText(((int)sum/sessions.size())+"km")));
        else onView(withId(R.id.average_text)).check(matches(withText("0km")));
    }

    @Test
    public void testControls() {
        onView(withText("Jahre")).perform(click());
        Calendar c = Calendar.getInstance();
        onView(withId(R.id.time_text_view)).check(matches(withText(c.get(Calendar.YEAR)+"")));
        onView(withId(R.id.time_back)).check(matches(isClickable()))
            .perform(click());
        c.add(Calendar.YEAR, -1);
        onView(withId(R.id.time_text_view)).check(matches(withText(c.get(Calendar.YEAR)+"")));
        c.add(Calendar.YEAR, 1);
        onView(withText("Monat")).perform(click());
        onView(withId(R.id.time_text_view)).check(matches(withText(new SimpleDateFormat("MMMM YYYY").format(c.getTime()))));
        onView(withId(R.id.time_back)).check(matches(isClickable()))
                .perform(click());
        c.add(Calendar.MONTH, -1);
        onView(withId(R.id.time_text_view)).check(matches(withText(new SimpleDateFormat("MMMM YYYY").format(c.getTime()))));
    }
}

