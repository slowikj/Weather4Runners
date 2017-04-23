package com.example.annabujak.weather4runners.Facebook;

import android.support.test.espresso.Espresso;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.annabujak.weather4runners.MainActivity;
import com.example.annabujak.weather4runners.R;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
/**
 * Created by pawel.bujak on 23.04.2017.
 */

public class FacebookLoginActivityTests {

    @Rule
    public ActivityTestRule<FacebookLoginActivity> mActivityRule = new ActivityTestRule<>(
            FacebookLoginActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
    }


    @Ignore
    @Test
    public void firstUITest() throws Exception {
        onView(withId(R.id.login_button))
                .perform(click());

      //  onView(withId(R.id.login_button))
      //          .check(matches(withText(mStringToBetyped)));
    }
}
