package cmsc355.contactapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static cmsc355.contactapp.Utilities.GenerateRandomString;

class Contact {

    static ArrayList<Contact> contactsMock;

    private String name;
    private JSONObject attributes;

    public Contact() {
        name = "Enter Name";
        attributes = new JSONObject();
    }

    public Contact(String name, JSONObject json) {
        this.name = name;
        this.attributes = json;
    }

    static ArrayList<Contact> GenerateRandomContacts(int numContacts) {
        ArrayList<Contact> contactList = new ArrayList<>();
        for (int i = 0; i < numContacts; i++) {
            contactList.add(GenerateRandomContact());
        }
        return contactList;
    }

    static private Contact GenerateRandomContact() {
        Contact contact = new Contact();
        contact.name = GenerateRandomString(8);

        JSONObject jsonAttributes = new JSONObject();
        Random random = new Random();
        try {
            jsonAttributes.put("Email", contact.name + "@gmail.com");
            jsonAttributes.put("Phone Number", String.format(Locale.getDefault(),"%03d",random.nextInt(999)) + "-" + String.format(Locale.getDefault(),"%04d",random.nextInt(9999)));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        contact.attributes = jsonAttributes;

        return contact;
    }

    JSONObject ContactToJSON() {
        JSONObject jsonContact = new JSONObject();
        try {
            jsonContact.put("Name",name);
            jsonContact.put("Attributes",attributes);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonContact;
    }

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

    static void AddAttribute(Contact contact, String key, String value) {
        try {
            contact.attributes.put(key, value);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
