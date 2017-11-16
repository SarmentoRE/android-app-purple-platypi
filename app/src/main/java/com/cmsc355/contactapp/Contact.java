package com.cmsc355.contactapp;

import android.provider.BaseColumns;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static com.cmsc355.contactapp.Utilities.generateRandomString;

//interface is for database
class Contact implements BaseColumns {

    //this is database stuff
    static final String TAG = Contact.class.getSimpleName();
    static final String TABLE_NAME = "Contact";
    static final String _ID = "ContactId";
    static final String COLUMN_NAME = "ContactName";
    static final String COLUMN_JSON = "JSON";

    //This is where two of the database-mocking objects are stored; other one is in ContactGroup
    //static ArrayList<Contact> contactsMock;
    //static Contact myInfoMock;

    //the attributes are a JSONObject to be easily extensible and make it very easy to package the
    //contact into a JSONObject itself
    private String name;
    private int contactId;
    private JSONObject attributes;

    public Contact() {
        name = "Enter Name";
        JSONObject attrs = new JSONObject();
        try {
            attrs.put("Email", "Enter Email");
            attrs.put("Phone Number", "Enter Phone Number");
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        attributes = attrs;
        contactId = -1;
    }

    //note: in this constructor, we set attributes to be json, not a copy of json; need to be careful.
    //Best practice is usually to create a new JSONObject and populate it before passing to this constructor
    public Contact(String name, JSONObject json) {
        this.name = name;
        this.attributes = json;
    }

    //This constructor copies a contact identically, used in ContactInfoActivity
    public Contact(Contact contactToCopy) {
        this.name = contactToCopy.getName();
        try {
            this.attributes = new JSONObject(contactToCopy.getAttributes().toString());
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        this.contactId = contactToCopy.getId();
    }

    //This constructor should be used when puling from the db to make sure we keep id consistent between app and db
    public Contact(String name, JSONObject json, int newId) {
        this.name = name;
        this.attributes = json;
        this. contactId = newId;
    }

    //generates a given number of contacts with random name, address, and phone number
    static ArrayList<Contact> generateRandomContacts(int numContacts) {
        ArrayList<Contact> contactList = new ArrayList<>();
        for (int i = 0; i < numContacts; i++) {
            Contact contact = generateRandomContact();
            contactList.add(contact);
            //ContactRepo.insertToDatabase(contact);
        }
        return contactList;
    }

    private static Contact generateRandomContact() {
        Contact contact = new Contact();
        contact.name = generateRandomString(8);

        Random random = new Random();
        JSONObject jsonAttributes = new JSONObject();
        try {
            jsonAttributes.put("Email", contact.name + "@gmail.com");
            jsonAttributes.put("Phone Number",
                String.format(Locale.getDefault(),"(%03d)", random.nextInt(999))
                        + String.format(Locale.getDefault(), "%03d", random.nextInt(999)) + "-"
                        + String.format(Locale.getDefault(), "%04d", random.nextInt(9999)));
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        contact.attributes = jsonAttributes;
        return contact;
    }

    //so far, we don't have to add attributes after the contact has been made, but we will need this
    //function once we allow the user to add/remove attributes from a contact
    public void addAttribute(String key, String value) {
        try {
            attributes.put(key, value);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    //packages the contact into a JSONObject, mostly used to then turn the json into a string
    //for easy transportation or comparison. Pass blank json as argument (for TDD)
    JSONObject addContactToJson(JSONObject jsonContact) {
        Utilities.clearJson(jsonContact);
        //add contact details
        try {
            jsonContact.put("Name", this.getName());
            jsonContact.put("Attributes", this.getAttributes());
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return jsonContact;
    }

    //getters and setters
    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    JSONObject getAttributes() {
        //Log.d("Contact getAttrbutes","Called, returning: "+attributes);
        return attributes;
    }

    void setAttributes(JSONObject attributes) {
        this.attributes = attributes;
        Log.d("Contact setAttributes","Attributes: " + attributes);
    }

    int getId() {
        return contactId;
    }

    void setId(int contactId) {
        this.contactId = contactId;
    }
}
