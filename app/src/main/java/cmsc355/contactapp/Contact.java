package cmsc355.contactapp;

import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;

import static cmsc355.contactapp.Utilities.generateRandomString;

//interface is for database
class Contact implements BaseColumns {

    //this is database stuff
    public static final String TAG = Contact.class.getSimpleName();
    public static final String TABLE_NAME = "Contact";
    public static final String _ID = "ContactId";
    public static final String COLUMN_NAME = "ContactName";
    public static final String COLUMN_JSON = "JSON";

    //This is where two of the database-mocking objects are stored; other one is in ContactGroup
    static ArrayList<Contact> contactsMock;
    static Contact myInfoMock;

    //the attributes are a JSONObject to be easily extensible and make it very easy to package the
    //contact into a JSONObject itself
    private String name;
    private int contactId;
    private JSONObject attributes;

    public Contact() {
        name = "Enter Name";
        attributes = new JSONObject();
        contactId = -1;
    }

    //note: in this constructor, we set attributes to be json, not a copy of json; need to be careful.
    //Best practice is usually to create a new JSONObject and populate it before passing to this constructor
    public Contact(String name, JSONObject json) {
        this.name = name;
        this.attributes = json;
    }

    //COMMENTED OUT FOR TESTING IN ITERATION 1, PUT BACK IN
    //generates a given number of contacts with random name, address, and phone number
    static ArrayList<Contact> generateRandomContacts(int numContacts) {
        ArrayList<Contact> contactList = new ArrayList<>();
//        ArrayList<Contact> contactListDummy = new ArrayList<>(); //BAD CODE, DELETE AFTER TESTING
        for (int i = 0; i < numContacts; i++) {
            Contact contact = generateRandomContact();
            contactList.add(contact);
//            ContactRepo.insertToDB(contact);
        }
        return contactList;
    }

    //COMMENTED OUT FOR TESTING IN ITERATION 1, PUT ALL CODE BACK IN
    private static Contact generateRandomContact() {
        Contact contact = new Contact();
        contact.name = generateRandomString(8);

        Random random = new Random();
        JSONObject jsonAttributes = new JSONObject();
        try {
            jsonAttributes.put("Email", contact.name + "@gmail.com");
            jsonAttributes.put("Phone Number", String.format(Locale.getDefault(), "(%03d)", random.nextInt(999)) + String.format(Locale.getDefault(), "%03d", random.nextInt(999)) + "-" + String.format(Locale.getDefault(), "%04d", random.nextInt(9999)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        contact.attributes = jsonAttributes;
        return contact;
    }

    //so far, we don't have to add attributes after the contact has been made, but we will need this
    //function once we allow the user to add/remove attributes from a contact
    static void addAttribute(Contact contact, String key, String value) {
        try {
            contact.attributes.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //packages the contact into a JSONObject, mostly used to then turn the json into a string
    //for easy transportation or comparison. Pass blank json as argument (for TDD)
    JSONObject addContactToJSON(JSONObject jsonContact) {
        Utilities.clearJSON(jsonContact);
        //add contact details
        try {
            jsonContact.put("Name", this.getName());
            jsonContact.put("Attributes", this.getAttributes());
        } catch (JSONException e) {
            e.printStackTrace();
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
        return attributes;
    }

    void setAttributes(JSONObject attributes) {
        this.attributes = attributes;
    }

    int getID() {
        return contactId;
    }

    void setID(int contactId) {
        this.contactId = contactId;
    }
}
