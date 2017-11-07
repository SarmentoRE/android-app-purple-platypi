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
import org.hamcrest.core.StringStartsWith.*;
import org.hamcrest.core.StringEndsWith.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import android.support.test.espresso.matcher.ViewMatchers.*;

/*Create and Store Contacts #14, Scenario 1*/

@RunWith(AndroidJUnit4.class)
public class AddNewContactTest {
    //Rule that launches the app from the HomeActivity
    @Rule
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<>(HomeActivity.class);

    //Code that will test Scenario 1: Add a new contact from User Story # 14 (Create and Store Contacts) for correctness
    @Test
    public void TestAddNewContact() throws Exception {
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

        //TextView detailView = (TextView) contactActivity.findViewById(R.id.contact_toolbar);
        //assertThat(detailView.getText().toString(), is("1"));

        //performs a click operation on the new contact button
        onView(withId(R.id.contact_new)).perform(click());

        /*confirms that the button associated with the previous click has a string associated with it
         that reads "Edit Contact Info". This string is unique and only assigned to this button at this
         point in the execution so it confirms we are in the correct activity at the correct time.*/
        onView(withId(R.id.info_edit_button)).check(matches(withText(R.string.info_edit)));

        //performs a click operation on the EDIT CONTACT INFO button
        onView(withId(R.id.info_edit_button)).perform(click());

        //types in the name Harry to the contact name field in the edit contact activity
        onView(withId(R.id.info_name)).perform(typeText("Harry"));

        /*confirms that the button associated with the previous click has a string associated with it
         that reads "Submit Changes". This string is unique and only assigned to this button at this
         point in the execution so it confirms we are in teh correct activity at the correct time.*/
        onView(withId(R.id.info_edit_button)).check(matches(withText(R.string.info_submit)));

        //performs a click operation on the SUBMIT CHANGES button
        onView(withId(R.id.info_edit_button)).perform(click());

        /*confirms that the only contact currently in the ArrayList of Contact objects is the one that we just
        added. Contact added successfully, test complete.*/
        Contact varContact = App.databaseIoManager.getContact(0);
        onView(withText(varContact.getName())).check(matches(withText("Harry")));

    }
    //TestAddNewContact method

}
//AddNewContactTest class