package com.thestreetcodecompany.roady;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.RoadyData;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DrivingSessionActivityTest {
    @Rule
    public ActivityTestRule<DrivingSessionActivity> activityRule  = new ActivityTestRule<>(DrivingSessionActivity.class, true, false);


    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.thestreetcodecompany.roady", appContext.getPackageName());
    }

    @Test
    public void testNoId() throws Exception {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
    }

    @Test
    public void testWithId() throws Exception {
        DBHandler dbh = new DBHandler();
        dbh.makeTestData();
        dbh.getUser();
        User user = dbh.getUser();
        final List<DrivingSession> sessions = user.getAllDrivingSessions();
        if(sessions.size() > 0) {
            Intent intent = new Intent();
            DrivingSession session = sessions.get(0);
            intent.putExtra("id", session.getId());
            activityRule.launchActivity(intent);
            onView(withText(session.getDistance() + " km")).check(matches(isDisplayed())).check(matches(withId(R.id.distance)));
            if(session.getCoDriver() != null) onView(withId(R.id.co_driver)).check(matches(withText(session.getCoDriver().getName())));
            else onView(withId(R.id.co_driver)).check(matches(withText("Unknown")));
            Date d = new Date(session.getDateTimeEnd() - session.getDateTimeStart());
            if(session.getCar() != null) onView(withId(R.id.car)).check(matches(withText(session.getCar().getName())));
            else onView(withId(R.id.car)).check(matches(withText("Unknown")));
            DateFormat dfDuration = new SimpleDateFormat("HH:mm");
            onView(withId(R.id.duration)).check(matches(withText(dfDuration.format(d))));
            onView(withId(R.id.weather_condition)).check(matches(withText(activityRule.getActivity().getResources().getStringArray(R.array.Weather)[session.getWeather()])));
            onView(withId(R.id.street_condition)).check(matches(withText(activityRule.getActivity().getResources().getStringArray(R.array.RoadConditions)[session.getStreetCondition()])));
        }
    }

    @Test
    public void testDelete() {
        DBHandler dbh = new DBHandler();
        dbh.makeTestData();
        dbh.getUser();
        User user = dbh.getUser();
        final List<DrivingSession> sessions = user.getAllDrivingSessions();
        if(sessions.size() > 0) {
            Intent intent = new Intent();
            DrivingSession session = sessions.get(0);
            intent.putExtra("id", session.getId());
            activityRule.launchActivity(intent);
            onView(withId(R.id.delete_button)).perform(click());
            assertEquals(DrivingSession.find(DrivingSession.class, "id=?", session.getId()+"").size(), 0);
        }
    }
}
