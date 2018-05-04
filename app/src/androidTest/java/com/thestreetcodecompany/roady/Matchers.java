package com.thestreetcodecompany.roady;

import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class Matchers {
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
