package cmsc355.contactapp;

import org.json.JSONObject;

import java.util.ArrayList;

class ContactGroup {

    static ArrayList<ContactGroup> groupsMock;

    String name;
    private ArrayList<Contact> contacts;

    ContactGroup() {
        name = "Default";
        contacts = new ArrayList<>();
    }

    ContactGroup(String n, ArrayList<Contact> cList) {
        name = n;
        contacts = new ArrayList<>();
        contacts.addAll(cList);
    }

    static ArrayList<ContactGroup> GenerateRandomGroups(int numGroups, int numContacts) {
        ArrayList<ContactGroup> groupList = new ArrayList<>();
        ArrayList<Contact> cList;
        for (int i = 0; i < numGroups; i++) {
            cList = Contact.GenerateRandomContacts(numContacts);
            groupList.add(new ContactGroup(Utilities.GenerateRandomString(8), cList));
        }
        return groupList;
    }

    String getName(){
        return name;
    }

    ArrayList<Contact> getContacts() {
        return contacts;
    }
}
