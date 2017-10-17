package cmsc355.contactapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static cmsc355.contactapp.Utilities.GenerateRandomString;

class Contact {
    private String name;
    private JSONObject json;

    public Contact() {
        name = "N/A";
        try {
            json = new JSONObject("{}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Contact(String name, JSONObject json) {
        this.name = name;
        this.json = json;
    }

    static void GenerateRandomContacts(ArrayList<Contact> contactList, int numContacts) {
        for (int i = 0; i < numContacts; i++) {
            String name = GenerateRandomString(8) + " " + GenerateRandomString(8);
            try {
                contactList.add(new Contact(name, new JSONObject("{\"Name\":\"" + name + "\" \"PhoneNumber\":" + "\"555-443-7089\"}")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
>>>>>>>>> Temporary merge branch 2
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }
}
