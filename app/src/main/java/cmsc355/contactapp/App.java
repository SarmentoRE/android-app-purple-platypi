package cmsc355.contactapp;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

import static cmsc355.contactapp.ContactGroup.GenerateRandomGroups;
import static cmsc355.contactapp.ContactGroup.groupsMock;
import static cmsc355.contactapp.Contact.contactsMock;

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
        groupsMock = GenerateRandomGroups(3,3);
        for (ContactGroup group : groupsMock) {
            contactsMock.addAll(group.getContacts());
        }
    }


}
