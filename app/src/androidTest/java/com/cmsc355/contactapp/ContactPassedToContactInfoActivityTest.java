package com.cmsc355.contactapp;

import android.view.*;
import android.support.test.InstrumentationRegistry;
import static android.support.test.espresso.action.ViewActions.*;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.*;
import android.support.test.espresso.ViewAssertion;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.widget.ListView;
import android.app.Activity;
import org.junit.Test;
import org.junit.Rule;
import org.junit.runner.RunWith;
import static com.cmsc355.contactapp.Contact.contactsMock;
import org.hamcrest.core.StringStartsWith.*;
import org.hamcrest.core.StringEndsWith.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import android.support.test.espresso.matcher.ViewMatchers.*;

/*Contact and Group Persistence #15, Scenario 1*/

public class ContactPassedToContactInfoActivityTest {
    //Rule that launches the app from the HomeActivity
    @Rule
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    /*Code that will test Scenario 1: Contact passed to ContactInfoActivity from User Story #15
     (Contact and Group Persistence) for correctness*/
    @Test
    public void TestContactPassedToContactInfoActivity() throws Exception {
        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLV = (ListView) main.getActivity().findViewById(R.id.home_list);

        //performs the click operation in the main thread of app on Contacts
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 1;
                homeLV.performItemClick(homeLV.getChildAt(index), index, homeLV.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        /*serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        to avoid possible timing errors*/
        Activity contactActivity = instrum.waitForMonitorWithTimeout(progress, 3000);

        Contact contactOne = contactsMock.get(0);
        onView(withText(contactOne.getName())).perform(click());

        //onView(withText(containsString(contactOne.getName()))).check(matches(withText("Harry")));

        //onView(withText(txtName.getText().toString()))

        //onView(withText(contactOne.getName())).check(matches(withText("Harry")));

        //onView(withText(contactOne.getName())).check(matches(withId(R.id.info_name)));

        onView(withId(R.id.info_name)).check(matches(withText("Harry")));


    }
    //TestContactPassedToContactInfoActivity method

}
//ContactPassedToContactInfoActivityTest
