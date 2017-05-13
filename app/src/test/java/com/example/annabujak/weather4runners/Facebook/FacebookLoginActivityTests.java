package com.example.annabujak.weather4runners.Facebook;

import com.example.annabujak.weather4runners.Fragments.LoginFragment;
import com.example.annabujak.weather4runners.R;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
/**
 * Created by pawel.bujak on 23.04.2017.
 */

public class FacebookLoginActivityTests {

    @Rule
    public ActivityTestRule<LoginFragment> mActivityRule = new ActivityTestRule<>(
            LoginFragment.class);

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
