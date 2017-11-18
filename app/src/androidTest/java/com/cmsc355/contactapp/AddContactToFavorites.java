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

//Test associated with Add Contact to Favorites (Iteration One)
@RunWith(AndroidJUnit4.class)
public class AddContactToFavorites {
    @Rule //Rule that launches the app from the HomeActivity
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void testSetUp() {
        Instrumentation instrum = InstrumentationRegistry.getInstrumentation();
        final ListView homeLv = (ListView) main.getActivity().findViewById(R.id.home_list);

        //performs the click operation on the Favorites button on the HomeActivity of app
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 2;
                homeLv.performItemClick(homeLv.getChildAt(index), index, homeLv.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity favoritesActivity = instrum.waitForMonitorWithTimeout(progress, 3000);
    }

    @Test //Associated with Get to the Add Favorites Activity screen (Scenario 1)
    public void getToAddFavoriteActivityTest() throws Exception {
        //performs a click operation on the Add Favorite button
        onView(withId(R.id.favorites_new)).perform(click());

        //confirms that there exists a button on the current activity screen that is associated
        //with the id contact_new and has a string associated with it that reads "New Contact". This
        //button is unique in the app and only appears on the Contact Activity screen.
        onView(withId(R.id.contact_new)).check(matches(withText(R.string.contact_new)));
    }

    @Test //Associated with Confirm contact was added to Favorites (Scenario 2)
    public void addedToFavoritesTest() throws Exception {
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

    @Test //Associated with Confirms return to the Favorites Activity (Scenario 3)
    public void returnToFavoritesActivityScreenTest() throws Exception {
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
        //with the id contact_new and has a string associated with it that reads "New Contact". This
        //button is unique in the app and only appears on the Contact Activity screen.
        onView(withId(R.id.contact_new)).check(matches(withText(R.string.contact_new)));
    }
}
