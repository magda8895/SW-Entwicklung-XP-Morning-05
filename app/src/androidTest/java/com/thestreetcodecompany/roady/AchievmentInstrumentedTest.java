package com.thestreetcodecompany.roady;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListView;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.model.Achievement;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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
public class AchievmentInstrumentedTest {

    @Rule
    public ActivityTestRule<AchievementsActivity> rule = new ActivityTestRule<AchievementsActivity>(AchievementsActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.thestreetcodecompany.roady", appContext.getPackageName());
    }

    @Test
    public void testConditions() throws Exception {
        // DB Connect
        DBHandler dbh = new DBHandler();
        User user = dbh.getTestUser();
        // Conditions
        List<Achievement> achievementsCondition = user.getAchievementsCondition();

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
            waitSeconds(3);
        }
    }

    @Test
    public void testLevels() throws Exception
    {
        // DB Connect
        DBHandler dbh = new DBHandler();
        User user = dbh.getTestUser();

        for(int index = 3; index < 4; index++)
        {
            int type = 0;
            if(index == 0){
                type = 5;
            }else if(index == 1) {
                type = 4;
            }else if(index == 2) {
                type = 6;
            }else if(index == 3){
                type = 7;
            }

            List<Achievement> achievements = user.getAchievementsTypeReached(type);
            String text;

            if (achievements.isEmpty()) {
                onData(anything()).inAdapterView(withId(R.id.gridViewLevel)).atPosition(index).perform(click());
                if(type == 7){
                    text = "Hidden\nThis achievment is hidden";
                }else{
                    text = user.getAchievementsType(type).get(index).getTitle() + "\n" + "Not achieved yet";
                }
            } else {
                onData(anything()).inAdapterView(withId(R.id.gridViewLevel)).atPosition(index).perform(click());
                text = achievements.get(achievements.size() - 1).getTitle() + "\n" +
                        achievements.get(achievements.size() - 1).getDescription() + " | " +
                        achievements.get(achievements.size() - 1).getReachedStringFormated();
            }

            onView(withText(text)).check(matches(isDisplayed()));
            waitSeconds(3);
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

