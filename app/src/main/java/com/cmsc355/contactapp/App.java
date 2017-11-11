package com.cmsc355.contactapp;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    // TODO - figure out why android wants me to not use a static Context.
    // The app works with this currently but gives a warning which I would like to research.
    public static Context context;
    private static DatabaseHelper dbHelper;

    public static DatabaseIoManager databaseIoManager;

    public static Context getContext() {
        return context;
    }

    //All of this is run only once, when the app starts
    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DatabaseHelper();
        DatabaseManager.initializeInstance(dbHelper);
        databaseIoManager = new DatabaseIoManager();

        //setupMocks();
    }

    //sets up the variables where we are mocking database functionality
    /*private void setupMocks() {
        //contactsMock holds all contacts, groupsMock holds groups which each hold their own list of contacts
        //currently no way to add contacts into a group
        contactsMock = new ArrayList<>();
        groupsMock = generateRandomGroups(2, 2);
        for (ContactGroup group : groupsMock) {
            contactsMock.addAll(group.getContacts());
        }

        //myInfoMock mocks the special contact location where the user's data is stored
        myInfoMock = new Contact();
        JSONObject attributes = new JSONObject();
        try {
            attributes.put("Email", "Enter email");
            attributes.put("Phone Number", "Enter Phone Number");
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        myInfoMock.setName("Enter Name");
        myInfoMock.setAttributes(attributes);
    }*/
}
