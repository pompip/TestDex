package com.chong.testdex;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SomethingActivityTest {
    @Rule
    public ActivityTestRule<SomethingActivity> somethingActivityRule = new ActivityTestRule<>(SomethingActivity.class);

    @Test
    public void testLogin(){
        Espresso.onView(withId(R.id.et_username))
                .perform(typeText("kechong"))
                .check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.bt_login))
                .perform(click());
    }
}
