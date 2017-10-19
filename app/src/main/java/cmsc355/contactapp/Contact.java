package cmsc355.contactapp;

import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static cmsc355.contactapp.Utilities.GenerateRandomString;

//interface is for database
class Contact implements BaseColumns {

    //this is database stuff
    public static final String TAG = Contact.class.getSimpleName();
    public static final String TABLE_NAME = "Contact";
    public static final String _ID = "ContactId";
    public static final String COLUMN_NAME = "Name";
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
    }

    //note: in this constructor, we set attributes to be json, not a copy of json; need to be careful.
    //Best practice is usually to create a new JSONObject and populate it before passing to this constructor
    public Contact(String name, JSONObject json) {
        this.name = name;
        this.attributes = json;
    }

    //generates a given number of contacts with random name, address, and phone number
    static ArrayList<Contact> GenerateRandomContacts(int numContacts) {
        ArrayList<Contact> contactList = new ArrayList<>();
        for (int i = 0; i < numContacts; i++) {
            Contact contact = GenerateRandomContact();
            contactList.add(contact);
            ContactRepo.insertToDB(contact);
        }
        return contactList;
    }

    private static Contact GenerateRandomContact() {
        Contact contact = new Contact();
        contact.name = GenerateRandomString(8);

        JSONObject jsonAttributes = new JSONObject();
        Random random = new Random();
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
    static void AddAttribute(Contact contact, String key, String value) {
        try {
            contact.attributes.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //packages the contact into a JSONObject, mostly used to then turn the contact into a string
    //for easy transportation or comparison
    JSONObject ContactToJSON() {
        JSONObject jsonContact = new JSONObject();
        try {
            jsonContact.put("Name", name);
            jsonContact.put("Attributes", attributes);
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

    int getContactId() {
        return contactId;
    }

    void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
