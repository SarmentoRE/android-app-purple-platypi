package com.cmsc355.contactapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


//Tests associated with My Info Contact Functionality (Iteration Three)
@RunWith(AndroidJUnit4.class)
public class MyInfoContactFunctionality {
    @Rule //Rule that launches the app from the HomeActivity
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void testSetUp() {
        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLv = main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the My Info button on the HomeActivity of app
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 4;
                homeLv.performItemClick(homeLv.getChildAt(index), index, homeLv.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity myInfoActivity = instrum.waitForMonitorWithTimeout(progress, 3000);
    }

    @After
    public void testCleanUp() {
        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLv = main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the Settings button on the HomeActivity of app
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 6;
                homeLv.performItemClick(homeLv.getChildAt(index), index, homeLv.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity settingsActivity = instrum.waitForMonitorWithTimeout(progress, 3000);

        //clicks the "Nuke Data" button which is designed to clear out the entire app of any persistent information
        //from the SQLite Database
        onView(withId(R.id.settings_nuke)).perform(click());
    }

    @Test //Associated with Get to the My Info Activity screen (Scenario 1)
    public void getToMyInfoActivityTest() throws Exception {
        //confirms that the button on the My Info screen associated with the id info_edit_button
        //has a string associated with it that reads "Submit Changes"
        onView(withId(R.id.info_edit_button)).check(matches(withText("Submit Changes")));

        //confirms that the button on the My Info screen associated with the id info_delete_button
        //has a string associated with it that reads "Delete Contact". The existence of this string
        //and the "Submit Changes" button are unique to this screen and confirm we are in the
        //My Info Activity screen
        onView(withId(R.id.info_delete_button)).check(matches(withText(R.string.info_delete)));
    }

    @Test //Associated with My Info Contact Should Not Populate in Contact Activity Screen (Scenario 2)
    public void myInfoContactNotPresentInContactActivity() throws Exception {
        //populates the name field of the My Info Contact with the string Aaron
        onView(withId(R.id.info_name)).perform(typeText("Aaron"), closeSoftKeyboard());

        //performs a click operation on the "Submit Changes" button
        onView(withId(R.id.info_edit_button)).perform(click());

        //creates a mock object of the Contact class
        Contact testContact = mock(Contact.class);

        //sets the name variable of the mock Contact object to Aaron
        testContact.setName("Aaron");

        //sets the contactId variable of the mock Contact object to 0; note that the My Info Contact is designed to be the first
        //Contact created and will always have contactId value 0
        testContact.setId(0);

        //confirms that the mock object's name field was in fact set to Aaron
        verify(testContact).setName(ArgumentMatchers.eq("Aaron"));

        //confirms that the mock object's contactId field was in fact set to 0
        verify(testContact).setId(ArgumentMatchers.eq(0));

        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLv = main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the Contacts button on the HomeActivity of app
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 1;
                homeLv.performItemClick(homeLv.getChildAt(index), index, homeLv.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity contactsActivity = instrum.waitForMonitorWithTimeout(progress, 3000);

        //confirms that there exists a button on the current activity screen that is associated
        //with the id contact_new and has a string associated with it that reads "New Contact". This
        //button is unique in the app and only appears on the Contact Activity screen.
        onView(withId(R.id.contact_new)).check(matches(withText(R.string.contact_new)));

        //confirms that there is no Contact present on the Contacts Activity screen that has the name Aaron
        onView(withText("Aaron")).check(doesNotExist());
    }

    @Test //Associated with My Info Contact Cannot be Deleted (Scenario 3)
    public void cannotDeleteMyInfoContact() throws Exception {
        //populates the name field of the My Info Contact with the string Austin
        onView(withId(R.id.info_name)).perform(typeText("Austin"), closeSoftKeyboard());

        //performs a click operation on the "Submit Changes" button
        onView(withId(R.id.info_edit_button)).perform(click());

        //creates a mock object of the Contact class
        Contact testContact = mock(Contact.class);

        //sets the name variable of the mock Contact object to Austin
        testContact.setName("Austin");

        //sets the contactId variable of the mock Contact object to 0; note that the My Info Contact is designed to be the first
        //Contact created and will always have contactId value 0
        testContact.setId(0);

        //confirms that the mock object's name field was in fact set to Austin
        verify(testContact).setName(ArgumentMatchers.eq("Austin"));

        //confirms that the mock object's contactId field was in fact set to 0
        verify(testContact).setId(ArgumentMatchers.eq(0));

        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLv = main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the My Info button on the HomeActivity of app
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 4;
                homeLv.performItemClick(homeLv.getChildAt(index), index, homeLv.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity myInfoActivity = instrum.waitForMonitorWithTimeout(progress, 3000);

        //performs the click operation on the Delete Contact button
        onView(withId(R.id.info_delete_button)).perform(click());

        //confirms that the button on the My Info screen associated with the id info_edit_button
        //has a string associated with it that reads "Submit Changes"
        onView(withId(R.id.info_edit_button)).check(matches(withText("Submit Changes")));

        //confirms that the button on the My Info screen associated with the id info_delete_button
        //has a string associated with it that reads "Delete Contact". The existence of this string
        //and the "Submit Changes" button are unique to this screen and confirm that the My Info
        //Contact was not deleted
        onView(withId(R.id.info_delete_button)).check(matches(withText(R.string.info_delete)));
    }
}
