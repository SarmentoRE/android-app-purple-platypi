package cmsc355.contactapp;

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
import static cmsc355.contactapp.Contact.contactsMock;
import org.hamcrest.core.StringStartsWith.*;
import org.hamcrest.core.StringEndsWith.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class EditContactTest/*Create and Store Contacts #14, Scenario 2*/
{
    //Rule that launches the app from the HomeActivity
    @Rule
    public ActivityTestRule<HomeActivity> main = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    /*Code that will test Scenario 2: Edit a contact from User Story #14 (Create and Store Contacts) for correctness*/
    @Test
    public void TestEditNewContact() throws Exception
    {
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

        //Confirms that we are on the edit contact page as it is the only page that has the delete contact button
        onView(withId(R.id.info_delete_button)).check(matches(withText("Delete Contact")));

        //types in some string to the contact name field in the edit contact activity
        onView(withId(R.id.info_name)).perform(typeText("Enter Name"));

        //performs a click operation on the SUBMIT CHANGES button
        onView(withText("ContactApp")).perform(click());

        //performs a click operation on the previously made contact
        onView(withText(containsString("Enter Name"))).perform(click());

        //Confirms that we are on the edit contact page as it is the only page that has the delete contact button
        onView(withId(R.id.info_delete_button)).check(matches(withText("Delete Contact")));

        //performs a click operation on the EDIT CONTACT INFO button
        //onView(withId(R.id.info_edit_button)).perform(click());
        onView(withText("ContactApp")).perform(click());

        //types in the name Harry to the contact name field in the edit contact activity
        onView(withId(R.id.info_name)).perform(typeText("Austin"));

        //performs a click operation on the SUBMIT CHANGES button
        onView(withText("ContactApp")).perform(click());

        /*Contact changed successfully, test complete.*/
        onView(withText("Austin")).check(matches(withText("Austin")));

        /*Deletes contact created for this test to prepare for next test.*/
        onView(withId(R.id.info_delete_button)).perform(click());

    }//TestEditNewContact method

}//EditContactTest class
