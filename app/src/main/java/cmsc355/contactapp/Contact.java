package cmsc355.contactapp;

import java.util.ArrayList;

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

    static void GenerateRandomContacts(ArrayList<Contact> contactList, int numContacts) {
        for (int i = 0; i < numContacts; i++) {
            contactList.add(new Contact());
            contactList.get(i).name = GenerateRandomString(8);
            contactList.get(i).address = GenerateRandomString(8);
            contactList.get(i).phoneNumber = GenerateRandomString(8);
            contactList.get(i).email = GenerateRandomString(8);
        }
    }
}
