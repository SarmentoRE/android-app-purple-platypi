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
public class ContactInGroupRetainedTest
{





}
