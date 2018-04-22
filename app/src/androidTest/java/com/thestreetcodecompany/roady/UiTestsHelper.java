package com.thestreetcodecompany.roady;

import android.support.test.espresso.contrib.PickerActions;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.hamcrest.Matchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by fklezin on 18/04/2018.
 */

public class UiTestsHelper {

    public static void setDate_andSetTime(int buttonDateId, int buttonTimeId, int year, int month, int day, int hour, int minute) {
        onView(withId(buttonDateId)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month,day ));
        onView(withText("OK")).perform(click());

        onView(withId(buttonTimeId)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour,minute));
        onView(withText("OK")).perform(click());
    }


}
