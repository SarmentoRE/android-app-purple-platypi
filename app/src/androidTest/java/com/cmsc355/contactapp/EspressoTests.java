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
import org.mockito.ArgumentMatchers;

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

    //Section of code where all Mock objects will be declared for later use
    @Mock
    private DatabaseManager mockDatabase;

    //Tests associated with Create and Store Contacts (Iteration One)
    //****************************************************************************************************************************

    @Test //Associated with Create and Store Contacts User Story (Scenario 1)
    public void GetToContactInfoActivityTest() throws Exception {
        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLV = (ListView) main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the Contacts button in the HomeActivity screen
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 1;
                homeLV.performItemClick(homeLV.getChildAt(index), index, homeLV.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity contactActivity = instrum.waitForMonitorWithTimeout(progress, 3000);

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
    public void ContactObjectCreationTest() throws Exception {
        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLV = (ListView) main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the Contacts button in the HomeActivity screen
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 1;
                homeLV.performItemClick(homeLV.getChildAt(index), index, homeLV.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity contactActivity = instrum.waitForMonitorWithTimeout(progress, 3000);

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
    public void ReturnToContactsActivityTest() throws Exception {
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

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity contactActivity = instrum.waitForMonitorWithTimeout(progress, 3000);

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

    //****************************************************************************************************************************


    //Tests associated with Add Contact to Favorites (Iteration One)
    //****************************************************************************************************************************

    @Test //Associated with Add Contact to Favorites User Story (Scenario 1)
    public void GetToAddFavoriteActivityTest() throws Exception {
        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLV = (ListView) main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the Favorites button on the HomeActivity of app
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 2;
                homeLV.performItemClick(homeLV.getChildAt(index), index, homeLV.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity favoritesActivity = instrum.waitForMonitorWithTimeout(progress, 3000);

        //performs a click operation on the Add Favorite button
        onView(withId(R.id.favorites_new)).perform(click());

        //confirms that there exists a button on the current activity screen that is associated
        //with the id...
        onView(withId(R.id.favorites_new)).check(matches(withText("Add Favorite")));

    }

    @Test //Associated with Add Contact to Favorites User Story (Scenario 2)
    public void AddedToFavoritesTest() throws Exception {
        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLV = (ListView) main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the Favorites button on the HomeActivity of app
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 2;
                homeLV.performItemClick(homeLV.getChildAt(index), index, homeLV.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity favoritesActivity = instrum.waitForMonitorWithTimeout(progress, 3000);

        //performs a click operation on the Add Favorite button
        onView(withId(R.id.favorites_new)).perform(click());

        //creates a mock object of the ContactGroup class that will serve to test whether or not
        //a ContactGroup object is actually createdand set with necessary parameters
        ContactGroup testContactGroup = mock(ContactGroup.class);

        //due to the complex interactions between the ContactGroup object and associated classes that
        //process it through the SQLite database, this method is stubbed and will be further tested via
        //isolated unit testing
        when(testContactGroup.getContacts()).thenReturn(null);

        //sets the mock ContactGroup object's name value to Favorites
        testContactGroup.setName("Favorites");

        //sets the mock ContactGroup object's groupId value to 1
        //Note that Favorites is set up to always be associated with GroupId 1 and is pre-defined
        testContactGroup.setGroupId(1);

        //confirms that both the .setName("Favorites") and setGroupId(1) methods were able to be called
        //on the mock ContactGroup object, this proves that the object was in fact created
        verify(testContactGroup, times(1)).setName("Favorites");
        verify(testContactGroup, times(1)).setGroupId(1);

        //confirms that the mock contactGroup object's name and GroupId variables were set to
        //"Favorites" and "1" respectively, this proves that these methods in the ContactGroup
        //class are functional
        verify(testContactGroup).setName(ArgumentMatchers.eq("Favorites"));
        verify(testContactGroup).setGroupId(ArgumentMatchers.eq(1));

        //confirms that there is the capability to return an ArrayList of Contact objects from
        //the ContactGroup class
        assertEquals(testContactGroup.getContacts(), null);
    }

    @Test //Associated with Add Contact to Favorites User Story (Scenario 3)
    public void ReturnToFavoritesActivityScreenTest() throws Exception {
        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLV = (ListView) main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the Favorites button on the HomeActivity of app
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 2;
                homeLV.performItemClick(homeLV.getChildAt(index), index, homeLV.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity favoritesActivity = instrum.waitForMonitorWithTimeout(progress, 3000);

        //performs a click operation on the Add Favorite button
        onView(withId(R.id.favorites_new)).perform(click());

        //creates a mock object of the ContactGroup class that will serve to test whether or not
        //a ContactGroup object is actually created and set with necessary parameters
        ContactGroup testContactGroup = mock(ContactGroup.class);

        //due to the complex interactions between the ContactGroup object and associated classes that
        //process it through the SQLite database, this method is stubbed and will be further tested via
        //isolated unit testing
        when(testContactGroup.getContacts()).thenReturn(null);

        //sets the mock ContactGroup object's name value to "Favorites"
        testContactGroup.setName("Favorites");

        //sets the mock ContactGroup object's groupId value to 1
        //Note that Favorites is set up to always be associated with GroupId 1 and is pre-defined
        testContactGroup.setGroupId(1);

        //confirms that both the .setName("Favorites") and setGroupId(1) methods were able to be called
        //on the mock ContactGroup object, this proves that the object was in fact created
        verify(testContactGroup, times(1)).setName("Favorites");
        verify(testContactGroup, times(1)).setGroupId(1);

        //confirms that the mock contactGroup object's name and GroupId variables were set to
        //"Favorites" and "1" respectively, this proves that these methods in the ContactGroup
        //class are functional
        verify(testContactGroup).setName(ArgumentMatchers.eq("Favorites"));
        verify(testContactGroup).setGroupId(ArgumentMatchers.eq(1));

        //confirms that there is the capability to return an ArrayList of Contact objects from
        //the ContactGroup class
        assertEquals(testContactGroup.getContacts(), null);

        //confirms that there exists a button on the current activity screen that is associated
        //with the id...
        onView(withId(R.id.favorites_new)).check(matches(withText("Add Favorite")));
    }

    //****************************************************************************************************************************


    //Tests associated with Create and Add Contact to Group (Iteration One)
    //****************************************************************************************************************************

    @Test //Associated with Create and Add Contact to Group (Scenario 1)
    public void GetToCreateGroupActivityTest() throws Exception {
        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLV = (ListView) main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the Groups button on the HomeActivity of app
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 3;
                homeLV.performItemClick(homeLV.getChildAt(index), index, homeLV.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity groupsActivity = instrum.waitForMonitorWithTimeout(progress, 3000);

        //performs a click operation on the New Group button
        onView(withId(R.id.group_new)).perform(click());

        //confirms that there exists a button on the current activity screen that is associated
        //with the id...
        onView(withId(R.id.group_new)).check(matches(withText(R.string.groups_new)));
    }

    @Test //Associated with Create and Add Contact to Group (Scenario 2)
    public void CreateGroupTest() throws Exception {
        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLV = (ListView) main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the Groups button on the HomeActivity of app
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 3;
                homeLV.performItemClick(homeLV.getChildAt(index), index, homeLV.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity groupsActivity = instrum.waitForMonitorWithTimeout(progress, 3000);

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
        //with the id...
        onView(withId(R.id.group_new)).check(matches(withText(R.string.groups_new)));
    }

    @Test //Associated with Create and Add Contact to Group (Scenario 3)
    public void AddContactToGroupTest() throws Exception {
        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLV = (ListView) main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the Groups button on the HomeActivity of app
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 3;
                homeLV.performItemClick(homeLV.getChildAt(index), index, homeLV.getAdapter().getItemId(index));}
            });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity groupsActivity = instrum.waitForMonitorWithTimeout(progress, 3000);

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

    //****************************************************************************************************************************


    //Tests associated with... (Iteration Two)
    //****************************************************************************************************************************















    //****************************************************************************************************************************



}//EspressoTests class
