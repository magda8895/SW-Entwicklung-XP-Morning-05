package com.thestreetcodecompany.roady;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by fklezin on 18/04/2018.
 */

public class UiTestsHelper {

    public static void setDate_andSetTime(int buttonDateId, int buttonTimeId, int year, int month, int day, int hour, int minute) {
        onView(withId(buttonDateId)).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isEnabled();
            }

            @Override
            public String getDescription() {
                return "click save button";
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.performClick();
            }
        });
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month,day ));
        onView(withText("OK")).perform(click());

        onView(withId(buttonTimeId)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour,minute));
        onView(withText("OK")).perform(click());
    }

    public static void setText(int editTextId, String EditTextTestInput) {
        onView(withId(editTextId))
                .perform(typeText(EditTextTestInput), closeSoftKeyboard())
                .check(matches(withText(EditTextTestInput)));
    }

    public static void setSpinnerwithoutData(int spinnerId) {
        onView(withId(spinnerId))
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled();
                    }

                    @Override
                    public String getDescription() {
                        return "click save button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                });
    }

    public static void setSpinner(int spinnerId, String spinnerString) {
        onView(withId(spinnerId))
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled();
                    }

                    @Override
                    public String getDescription() {
                        return "click save button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                });
        onView(withText(spinnerString)).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isEnabled();
            }

            @Override
            public String getDescription() {
                return "click save button";
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.performClick();
            }
        })
                .check(matches(withText(spinnerString)));
    }

    public static void getBackToMileageChange(int BackButtonId)
    {
        onView(withId(BackButtonId))
                .perform(click());
    }

    public static void SaveData(int SaveButtonId)
    {
        onView(withId(SaveButtonId))
                .perform(click());
    }


}
