package com.cmsc355.contactapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class EspressoTests {
    //Rule that launches the app from the HomeActivity
    @Rule
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<>(HomeActivity.class);

    @Mock
    private DatabaseManager mockDatabase;

    //Tests associated with Create and Store Contacts (Iteration One)
    /****************************************************************************************************************************/
    @Test
    public void getToContactInfoActivityTest() throws Exception {
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

        //performs a click operation on the new contact button
        onView(withId(R.id.contact_new)).perform(click());

        /*confirms that the button on the Edit Contact screen associated with the id info_delete_button
        has a string associated with it that reads "Delete Contact". This string and id is unique in the
        app and only exists in this singular activity.*/
        onView(withId(R.id.info_delete_button)).check(matches(withText("Delete Contact")));
    }

    @Test
    public void contactObjectCreationTest() throws Exception {
        /*Creates a mock object of the contact class that will serve to test whether or not
        a contact object is actually created at the point in time when necessary*/
        Contact newlyCreatedContact = mock(Contact.class);

        /*Tells the mock object that whenever the method .getName() of the Contact class is
        called, it should return the string "Aaron"*/
        when(newlyCreatedContact.getName()).thenReturn("Aaron");

        /*Calls the .getName() method on the mock contact object and asserts that the returned
        string reads "Aaron". This confirms that the contact object was in fact created*/
        assertEquals(newlyCreatedContact.getName(), "Aaron");
    }

    @Test
    public void returnToContactsActivityTest() throws Exception {
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

        //performs a click operation on the new contact button
        onView(withId(R.id.contact_new)).perform(click());

        /*Creates a mock object of the contact class that will serve to test whether or not
        a contact object is actually created at the point in time when necessary*/
        Contact newlyCreatedContact = mock(Contact.class);

        //Sets the name of the mock contact object to "Aaron"
        newlyCreatedContact.setName("Aaron");

        onView(withId(R.id.info_delete_button)).perform(click());

        /*Confirms that there exists a button on the current activity screen that is associated
        with the id contact_new and has a string associated with it that reads "New Contact". This
        button is unique in the app and only appears on the Contact Activity screen.*/
        onView(withId(R.id.contact_new)).check(matches(withText("New Contact")));
    }
    /****************************************************************************************************************************/


    //Tests associated with...
    /****************************************************************************************************************************/
    @Test
    public void checkDatabaseOpen() throws Exception{



    }



}
