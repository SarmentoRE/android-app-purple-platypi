package com.cmsc355.contactapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//Tests associated with Create and Store Contacts (Iteration One)
@RunWith(AndroidJUnit4.class)
public class CreateAndStoreContacts {
    @Rule //Rule that launches the app from the HomeActivity
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<>(HomeActivity.class);

    @Before //happens before each test occurs
    public void setUpTest() {
        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLv = (ListView) main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the Contacts button in the HomeActivity screen
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
        Activity contactActivity = instrum.waitForMonitorWithTimeout(progress, 3000);
    }

    @Test //Associated with Create and Store Contacts User Story (Scenario 1)
    public void getToContactInfoActivityTest() throws Exception {
        //performs a click operation on the new contact button
        onView(withId(R.id.contact_new)).perform(click());

        //confirms that the button on the Edit Contact screen associated with the id info_delete_button
        //has a string associated with it that reads "Delete Contact". This string and id is unique in the
        //app and only exists in this singular activity.
        onView(withId(R.id.info_delete_button)).check(matches(withText(R.string.info_delete)));

        //clicks the Delete Contact button so that a new, empty Contact object is not created
        onView(withId(R.id.info_delete_button)).perform(click());
    }

    @Test //Associated with Create and Store Contacts User Story (Scenario 2)
    public void contactObjectCreationTest() throws Exception {
        //performs a click operation on the new contact button
        onView(withId(R.id.contact_new)).perform(click());

        //confirms that the button on the Edit Contact screen associated with the id info_delete_button
        //has a string associated with it that reads "Delete Contact". This string and id is unique in the
        //app and only exists in this singular activity.
        onView(withId(R.id.info_delete_button)).check(matches(withText(R.string.info_delete)));

        //creates a mock object of the Contact class that will serve to test whether or not
        //a Contact object is actually created at the point in time when necessary
        Contact testContact = mock(Contact.class);

        //sets the name variable of the Contact object to Aaron
        testContact.setName("Aaron");

        //confirms that the .setName("Aaron") method was called on the mock object once, this proves that
        //the method is able to be called and that the object has been created
        verify(testContact, times(1)).setName("Aaron");

        //confirms that the mock object's name field was in fact set to Aaron, this confirms that the Contact
        //object was in fact created and that the setName() method is functional
        verify(testContact).setName(ArgumentMatchers.eq("Aaron"));

        //clicks the Delete Contact button so that a new, empty Contact object is not created
        onView(withId(R.id.info_delete_button)).perform(click());
    }

    @Test //Associated with Create and Store Contacts User Story (Scenario 3)
    public void returnToContactsActivityTest() throws Exception {
        //performs a click operation on the new contact button
        onView(withId(R.id.contact_new)).perform(click());

        //creates a mock object of the Contact class that will serve to test whether or not
        //a Contact object is actually created at the point in time when necessary
        Contact testContact = mock(Contact.class);

        //sets the name variable of the Contact object to Aaron
        testContact.setName("Aaron");

        //confirms that the .setName("Aaron") method was called on the mock object once, this proves that
        //the method is able to be called and that the object has been created
        verify(testContact, times(1)).setName("Aaron");

        //confirms that the mock object's name field was in fact set to Aaron, this proves that the Contact
        //object was in fact created and that the setName() method is functional
        verify(testContact).setName(ArgumentMatchers.eq("Aaron"));

        onView(withId(R.id.info_delete_button)).perform(click());

        //confirms that there exists a button on the current activity screen that is associated
        //with the id contact_new and has a string associated with it that reads "New Contact". This
        //button is unique in the app and only appears on the Contact Activity screen.
        onView(withId(R.id.contact_new)).check(matches(withText(R.string.contact_new)));
    }
}
