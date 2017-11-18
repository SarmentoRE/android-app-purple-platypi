package com.cmsc355.contactapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

//Tests associated with Ability to Associate a Photo with a Contact (Iteration Two)
@RunWith(AndroidJUnit4.class)
public class AssociatePhotoWithContact {
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

    @Test //Associated with Create a new contact (Scenario 1)
    public void createNewContactTest() throws Exception {
        //performs a click operation on the new contact button
        onView(withId(R.id.contact_new)).perform(click());

        //confirms that the button on the Edit Contact screen associated with the id info_delete_button
        //has a string associated with it that reads "Delete Contact". This string and id is unique in the
        //app and only exists in this singular activity.
        onView(withId(R.id.info_delete_button)).check(matches(withText(R.string.info_delete)));

        //types in the name "Aaron" into the name field for the Contact
        onView(withId(R.id.info_name)).perform(typeText("Shane"), closeSoftKeyboard());

        //performs a click operation on the "Submit Changes" button
        onView(withId(R.id.info_edit_button)).perform(click());

        //confirms that you are taken back to the ContactActivity page confirming that a
        //new Contact was in fact created
        onView(withId(R.id.contact_new)).check(matches(withText("New Contact")));

        //performs a scroll action within the activity to take you to the necessary spot where
        //the newly created contact will be located
        onView(withId(R.id.contacts_list)).perform(RecyclerViewActions.scrollToPosition(0));

        //performs a click operation on the newly created Contact that is within the field
        //of view for the RecyclerView
        onView(withText("Shane")).perform(click());

        //clicks the Delete Contact button
        onView(withId(R.id.info_delete_button)).perform(click());
    }

    @Test //Associated with Confirm image icon exists (Scenario 2)
    public void confirmImageIconExistsTest() throws Exception {
        //performs a click operation on the new contact button
        onView(withId(R.id.contact_new)).perform(click());

        //types in the name "Aaron" into the name field for the Contact
        onView(withId(R.id.info_name)).perform(typeText("Shane"), closeSoftKeyboard());

        //performs a click operation on the "Submit Changes" button
        onView(withId(R.id.info_edit_button)).perform(click());

        //performs a scroll action within the activity to take you to the necessary spot where
        //the newly created contact will be located
        onView(withId(R.id.contacts_list)).perform(RecyclerViewActions.scrollToPosition(0));

        //performs a click operation on the newly created Contact that is within the field
        //of view for the RecyclerView
        onView(withText("Shane")).perform(click());

        //performs a click operation on the "Edit Contact Info" button
        onView(withId(R.id.info_edit_button)).perform(click());

        //confirms that the button on the Edit Contact screen associated with the id info_delete_button
        //has a string associated with it that reads "Delete Contact". This string and id is unique in the
        //app and only exists in this singular activity.
        onView(withId(R.id.info_delete_button)).check(matches(withText(R.string.info_delete)));

        onView(withId(R.id.info_pic)).check(matches(withContentDescription(R.string.image_contact)));

        //clicks the Delete Contact button
        onView(withId(R.id.info_delete_button)).perform(click());
    }

    @Test //Associated with Associate a photo with a contact (Scenario 3)
    public void associatePhotoWithContactTest() throws Exception {
        //performs a click operation on the new contact button
        onView(withId(R.id.contact_new)).perform(click());

        //types in the name "Aaron" into the name field for the Contact
        onView(withId(R.id.info_name)).perform(typeText("Tal"), closeSoftKeyboard());

        //performs a click operation on the "Submit Changes" button
        onView(withId(R.id.info_edit_button)).perform(click());

        //performs a scroll action within the activity to take you to the necessary spot where
        //the newly created contact will be located
        onView(withId(R.id.contacts_list)).perform(RecyclerViewActions.scrollToPosition(0));

        //performs a click operation on the newly created Contact that is within the field
        //of view for the RecyclerView
        onView(withText("Tal")).perform(click());

        //performs a click operation on the "Edit Contact Info" button
        onView(withId(R.id.info_edit_button)).perform(click());

        onView(withId(R.id.info_pic)).perform(click());

        onView(withText("Add Photo!")).check(matches(isDisplayed()));

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

        onView(withText("Tal")).perform(click());

        onView(withId(R.id.info_delete_button)).perform(click());
    }
}
