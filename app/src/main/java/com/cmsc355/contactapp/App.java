package com.cmsc355.contactapp;

import android.app.Application;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

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

        //set to true to clear data on app start, useful for testing
        if (false) {
            DatabaseHelper.deleteDatabaseData(DatabaseManager.getInstance().openDatabase());
            DatabaseManager.getInstance().closeDatabase();
            dbHelper = new DatabaseHelper();
            DatabaseManager.initializeInstance(dbHelper);
            databaseIoManager = new DatabaseIoManager();
        }

        JSONObject testAttributes = new JSONObject();
        try {
            testAttributes.put("Email","test@example.com");
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        Contact testContact = new Contact("Test",testAttributes);
        databaseIoManager.putContact(testContact);

        Contact testCopy = new Contact(testContact);
        databaseIoManager.putContact(testCopy);
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
