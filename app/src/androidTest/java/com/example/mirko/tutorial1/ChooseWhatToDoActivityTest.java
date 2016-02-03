package com.example.mirko.tutorial1;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by lorenzo on 02/02/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChooseWhatToDoActivityTest {

    @Rule
    public ActivityTestRule<ChooseWhatToDoActivity> mActivityRule = new ActivityTestRule(ChooseWhatToDoActivity.class);

    @Test
    public void areTitleAndButtonDisplayedTest() {
        //check that title and setting are displayed at the top of the page
        onView(withText("ChooseWhatToDoActivity")).check(matches(isDisplayed()));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.action_settings)).check(matches(isDisplayed()));
    }

    @Test
    public void copyAndPasteTest() throws InterruptedException {
        //Check that after creating a sandbox we can see it in "open my last sandbox"

        final String SANDBOX_TITLE = "My first sandbox";
        final String SANDBOX_CONTENT = "What a wonderful sunny day but winter was better";
        //Click "copy and paste" and create a sandbox
        onView(withId(R.id.open_my_sandbox)).perform(click());
        onView(withId(R.id.edit_text_sandbox_name)).perform(typeText(SANDBOX_TITLE), closeSoftKeyboard());
        onView(withId(R.id.button_attach_sandbox)).perform(click());
        // Wait until the app postes your sandbox name
        Thread.sleep(2000); //I should implement a different/better way here.
        onView(withId(R.id.edit_text_sandbox)).perform(click(), typeText(SANDBOX_CONTENT), closeSoftKeyboard());
        //check that the sandobx has been created correctly
        pressBack();
        onView(withId(R.id.reopen_my_sandbox)).perform(click());
        onView(withId(R.id.edit_text_sandbox_name)).check(matches(withText(SANDBOX_TITLE)));
        onView(withId(R.id.edit_text_sandbox)).check(matches(withText(SANDBOX_CONTENT)));
    }

    @Test
    public void creditsTest() {
        //Click the button "credits"
        onView(withId(R.id.credits_app)).perform(click());
        //Check if something is displayed
        onView(withText(R.string.credits_content)).check(matches(isDisplayed()));
        onView(withText(R.string.OK)).perform(click());
    }
}