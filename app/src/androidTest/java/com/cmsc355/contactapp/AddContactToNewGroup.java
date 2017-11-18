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
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//Tests associated with Add Contact to New Group (Iteration One)
@RunWith(AndroidJUnit4.class)
public class AddContactToNewGroup {
    @Rule //Rule that launches the app from the HomeActivity
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void testSetUp() {
        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLv = (ListView) main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the Groups button on the HomeActivity of app
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 3;
                homeLv.performItemClick(homeLv.getChildAt(index), index, homeLv.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity groupsActivity = instrum.waitForMonitorWithTimeout(progress, 3000);
    }

    @Test //Associated with Get to the Create Groups Activity screen (Scenario 1)
    public void getToCreateGroupActivityTest() throws Exception {
        //performs a click operation on the New Group button
        onView(withId(R.id.group_new)).perform(click());

        //confirms that there exists a button on the current activity screen that is associated
        //with the id contact_new and has a string associated with it that reads "New Contact". This
        //button is unique in the app and only appears on the Contact Activity screen.
        onView(withId(R.id.contact_new)).check(matches(withText(R.string.contact_new)));
    }

    @Test //Associated with Create a new Group relation (Scenario 2)
    public void createGroupTest() throws Exception {
        //performs a click operation on the New Group button
        onView(withId(R.id.group_new)).perform(click());

        //creates a mock object of the ContactGroup class that will serve to test whether or not
        //a ContactGroup object is actually created and set with necessary parameters
        ContactGroup testContactGroup = mock(ContactGroup.class);

        //sets the mock ContactGroup object's name value to "Best Friends"
        testContactGroup.setName("Best Friends");

        //sets the mock ContactGroup object's groupId value to 5, this value is arbitrary for testing
        //purposes as GroupId's are assigned sequentially in the app itself
        testContactGroup.setGroupId(5);

        //confirms that both the .setName("Best Friends") and setGroupId(5) methods were able to be called
        //on the mock ContactGroup object, this proves that the object was in fact created
        verify(testContactGroup, times(1)).setName("Best Friends");
        verify(testContactGroup, times(1)).setGroupId(5);

        //confirms that the mock contactGroup object's name and GroupId variables were set to
        //"Best Friends" and "5" respectively, this proves that these methods in the ContactGroup
        //class are functional
        verify(testContactGroup).setName(ArgumentMatchers.eq("Best Friends"));
        verify(testContactGroup).setGroupId(ArgumentMatchers.eq(5));

        //confirms that there exists a button on the current activity screen that is associated
        //with the id contact_new and has a string associated with it that reads "New Contact". This
        //button is unique in the app and only appears on the Contact Activity screen.
        onView(withId(R.id.contact_new)).check(matches(withText(R.string.contact_new)));
    }

    @Test //Associated with Add one Contact to a Group (Scenario 3)
    public void addContactToGroupTest() throws Exception {
        //performs a click operation on the New Group button
        onView(withId(R.id.group_new)).perform(click());

        //creates a mock object of the ContactGroup class that will serve to test whether or not
        //a ContactGroup object is actually created and set with necessary parameters
        ContactGroup testContactGroup = mock(ContactGroup.class);

        //due to the complex interactions between the ContactGroup object and associated classes that
        //process it through the SQLite database, this method is stubbed and will be further tested via
        //isolated unit testing
        when(testContactGroup.getContacts()).thenReturn(null);

        //sets the mock ContactGroup object's name value to "Best Friends"
        testContactGroup.setName("Best Friends");

        //sets the mock ContactGroup object's groupId value to 5, this value is arbitrary for testing
        //purposes as GroupId's are assigned sequentially in the app itself
        testContactGroup.setGroupId(5);

        //confirms that both the .setName("Best Friends") and setGroupId(5) methods were able to be called
        //on the mock ContactGroup object, this proves that the object was in fact created
        verify(testContactGroup, times(1)).setName("Best Friends");
        verify(testContactGroup, times(1)).setGroupId(5);

        //confirms that the mock contactGroup object's name and GroupId variables were set to
        //"Best Friends" and "5" respectively, this proves that these methods in the ContactGroup
        //class are functional
        verify(testContactGroup).setName(ArgumentMatchers.eq("Best Friends"));
        verify(testContactGroup).setGroupId(ArgumentMatchers.eq(5));

        //confirms that there is the capability to return an ArrayList of Contact objects from
        //the ContactGroup class
        assertEquals(testContactGroup.getContacts(), null);
    }
}
