package com.chong.testdex;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SomethingActivityTest {

    @Test
    public void testLogin() throws InterruptedException {
        Context context = InstrumentationRegistry.getTargetContext();
        Intent intent = new Intent(context, SomethingActivity.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Espresso.onView(withId(R.id.et_username))
                .perform(typeText("kechong"))
                .check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.bt_login))
                .perform(click());
        Thread.sleep(10000);
    }
}
