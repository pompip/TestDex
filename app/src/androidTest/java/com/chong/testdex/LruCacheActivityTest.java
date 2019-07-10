package com.chong.testdex;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LruCacheActivityTest {
//    @Rule
//    public ActivityTestRule<LruCacheActivity> somethingActivityRule = new ActivityTestRule<>(LruCacheActivity.class);

    @Test
    public void lunch(){
        Context appContext = InstrumentationRegistry.getTargetContext();

        Intent intent = new Intent(appContext,LruCacheActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        appContext.startActivity(intent);
        CountDownLatch countdown = new CountDownLatch(1);
        try {
            countdown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLogin(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent intent = new Intent(appContext,LruCacheActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);
        Espresso.onView(ViewMatchers.withText("hello world")).perform(ViewActions.swipeUp()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onIdle();
    }
}
