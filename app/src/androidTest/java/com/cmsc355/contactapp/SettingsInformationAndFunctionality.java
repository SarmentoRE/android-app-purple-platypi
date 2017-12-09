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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;

//Tests associated with Settings Information and Functionality (Iteration Three)
@RunWith(AndroidJUnit4.class)
public class SettingsInformationAndFunctionality {
    @Rule //Rule that launches the app from the HomeActivity
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void testSetUp() {
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
    }

    @Test //Associated with Get to the Settings Activity screen (Scenario 1)
    public void getToSettingsActivityTest() throws Exception {
        //confirms that there exists a TextView with id settings_title that contains the string
        //"Settings"; note that this TextView is unique to the Settings Activity screen and
        //demonstrates that we are in fact in the correct activity
        onView(withId(R.id.settings_title)).check(matches(withText("Settings")));
    }

    @Test //Associated with Nuke button should clear all local information in app and all
    // SQLite Database information (Scenario 2)
    public void nukeButtonFunctionalityTest() throws Exception {
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

        //types in the name "Shane" into the name field for the Contact
        onView(withId(R.id.info_name)).perform(typeText("Shane"), closeSoftKeyboard());

        //performs a click operation on the "Submit Changes" button
        onView(withId(R.id.info_edit_button)).perform(click());

        //performs the click operation on the My Settings button on the HomeActivity of app
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 6;
                homeLv.performItemClick(homeLv.getChildAt(index), index, homeLv.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress1 = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity settingsActivity1 = instrum.waitForMonitorWithTimeout(progress, 3000);

        //clicks the Nuke Data button
        onView(withId(R.id.settings_nuke)).perform(click());

        //performs the click operation on the My Info button on the HomeActivity of app
        instrum.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                int index = 4;
                homeLv.performItemClick(homeLv.getChildAt(index), index, homeLv.getAdapter().getItemId(index));
            }
        });

        //allows us to monitor the progress of app opening and loading the home activity
        Instrumentation.ActivityMonitor progress2 = instrum.addMonitor(HomeActivity.class.getName(), null, false);

        //serves the purpose of returning the next activity and waiting 3 seconds until the next activity is launched
        //to avoid possible timing errors
        Activity myInfoActivity2 = instrum.waitForMonitorWithTimeout(progress2, 3000);

        //confirms that the EditText field where the Contact's name is entered is set to Enter Name indicating that the previous
        //entry of "Shane" was deleted. This confirms that the database was wiped
        onView(withId(R.id.info_name)).check(matches(withHint("Enter Name")));
    }

    @Test //Associated with Developer Information is Available (Scenario 3)
    public void developerInformationTest() throws Exception {
        //performs the click operation on the About App button in the Settings Activity screen
        onView(withId(R.id.settings_info)).perform(click());

        //Confirms that the names of all of the developers responsible for the creation of this application are listed
        //on this page
        onView(withResourceName("app_info_devs_text")).check(matches(withText("Developers:")));
        onView(withResourceName("app_info_devs_1")).check(matches(withText("Aaron")));
        onView(withResourceName("app_info_devs_2")).check(matches(withText("Austin")));
        onView(withResourceName("app_info_devs_3")).check(matches(withText("Shane")));
        onView(withResourceName("app_info_devs_4")).check(matches(withText("Tal")));

    }
}
