package com.thestreetcodecompany.roady;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.RoadyData;
import com.thestreetcodecompany.roady.classes.model.Achievement;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

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
public class AchievementInstrumentedTest {

    @Rule
    public ActivityTestRule<AchievementsActivity> rule = new ActivityTestRule<AchievementsActivity>(AchievementsActivity.class);

    @Before
    public void init() {
        DBHandler dbh = new DBHandler();
        dbh.getUser(); // Will make test data if it doesn't exist
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.thestreetcodecompany.roady", appContext.getPackageName());
    }

    @Test
    public void testMostRecent() throws  Exception {
        // DB Connect
         DBHandler dbh = new DBHandler();
        RoadyData rd = RoadyData.getInstance();
        // most recent
        List<Achievement> achievementsList = rd.user.getAchievements();
        int latest = -1;
        for (int position = 0; position < achievementsList.size(); position++) {
            if (achievementsList.get(position).getReached()) {
                if (latest == -1 || achievementsList.get(position).getReachedDate().after(achievementsList.get(latest).getReachedDate())) {
                    latest = position;
                }
            }
        }

        if (latest != -1) {

            String title = achievementsList.get(latest).getTitle();
            onView(withId(R.id.textViewRecentAchievementTitle)).check(matches(withText(title)));

            String description = achievementsList.get(latest).getDescription();
            onView(withId(R.id.textViewRecentAchievementDescription)).check(matches(withText(description)));

            String date = "Achieved on " + achievementsList.get(latest).getReachedStringFormated();
            onView(withId(R.id.textViewRecentAchievementDate)).check(matches(withText(date)));
        }
        else
        {
            onView(withId(R.id.textViewRecentAchievementTitle)).check(matches(withText(R.string.achievements_recent_title)));
            onView(withId(R.id.textViewRecentAchievementDescription)).check(matches(withText(R.string.achievements_recent_description)));
            onView(withId(R.id.textViewRecentAchievementDate)).check(matches(withText("")));
        }
    }

    @Test
    public void testConditions() throws Exception {
        // DB Connect
        RoadyData rd = RoadyData.getInstance();
        // Conditions
        List<Achievement> achievementsCondition = rd.user.getAchievementsCondition();

        for(int index = 0; index < achievementsCondition.size(); index++)
        {
            onData(anything()).inAdapterView(withId(R.id.gridViewCondition)).atPosition(index).perform(click());
            String text;
            if(achievementsCondition.get(index).getReached())
            {
                text = achievementsCondition.get(index).getTitle() + "\n" + achievementsCondition.get(index).getDescription()
                        + " | " + achievementsCondition.get(index).getReachedStringFormated();
            }
            else
            {
                text = achievementsCondition.get(index).getTitle() + "\n" + "Not achieved yet";
            }

            onView(withText(text)).check(matches(isDisplayed()));
            waitSeconds(7);
        }
    }

    @Test
    public void testLevels() throws Exception
    {
        // DB Connect
        DBHandler dbh = new DBHandler();
        RoadyData rd = RoadyData.getInstance();

        for(int index = 0; index < 4; index++)
        {
            int type = index + 4;

            List<Achievement> achievements = rd.user.getAchievementsTypeReached(type);
            String text;

            if (!achievements.isEmpty()) {
                onData(anything()).inAdapterView(withId(R.id.gridViewLevel)).atPosition(index).perform(click());
                text = achievements.get(achievements.size() - 1).getTitle() + "\n" +
                        achievements.get(achievements.size() - 1).getDescription() + " | " +
                        achievements.get(achievements.size() - 1).getReachedStringFormated();
                onView(withText(text)).check(matches(isDisplayed()));
                waitSeconds(3);
            }
        }
    }

    private void waitSeconds(int seconds)
    {
        Calendar timeToWait = Calendar.getInstance();
        timeToWait.add(Calendar.SECOND, seconds);
        Calendar compareTime = Calendar.getInstance();

        while(!compareTime.equals(timeToWait))
            compareTime = Calendar.getInstance();
    }

}

