package cmsc355.contactapp;

import java.util.ArrayList;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import static cmsc355.contactapp.Utilities.GenerateRandomString;

class Contact {
    public String name;
    private String address;
    private String phoneNumber;
    private String email;

    private Contact() {
        name = "N/A";
        address = "N/A";
        phoneNumber = "N/A";
        email = "N/A";
    }

    public Contact(String n, String a, String p, String e) {
        name = n;
        address = a;
        phoneNumber = p;
        email = e;
    }

    public void SetContactInfo(String n, String a, String p, String e) {
        name = n;
        address = a;
        phoneNumber = p;
        email = e;
    }

    static void GenerateRandomContacts(ArrayList<JSONObject> contactList, int numContacts) {
        for (int i = 0; i < numContacts; i++) {
            try {
                contactList.add(new JSONObject("{\"First name\":\" "+GenerateRandomString(8)+" \",\"Last name\":\" "+GenerateRandomString(8)+" \"}"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
