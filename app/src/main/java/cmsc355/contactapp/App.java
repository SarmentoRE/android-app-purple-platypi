package cmsc355.contactapp;

import android.app.Application;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static cmsc355.contactapp.Contact.contactsMock;
import static cmsc355.contactapp.ContactGroup.GenerateRandomGroups;
import static cmsc355.contactapp.ContactGroup.groupsMock;
import static cmsc355.contactapp.Contact.contactsMock;
import static cmsc355.contactapp.Contact.myInfoMock;

public class App extends Application {

    private static Context context;
    private static DatabaseHelper dbHelper;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DatabaseHelper();
        DatabaseManager.initializeInstance(dbHelper);

        contactsMock = new ArrayList<>();
        groupsMock = GenerateRandomGroups(2, 2);
        for (ContactGroup group : groupsMock)
        {
            contactsMock.addAll(group.getContacts());
        }
        myInfoMock = new Contact();
        JSONObject attributes = new JSONObject();
        try {
            attributes.put("Email", "Enter email");
            attributes.put("Phone Number", "Enter Phone Number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myInfoMock.setName("Enter Name");
        myInfoMock.setAttributes(attributes);
    }


}
