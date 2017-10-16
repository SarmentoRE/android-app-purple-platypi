package cmsc355.contactapp;

import org.json.JSONObject;

import java.util.ArrayList;

class ContactGroup {

    String name;
    ArrayList<JSONObject> contacts;

    public ContactGroup() {
        name = "Default";
        contacts = new ArrayList<>();
    }

    public ContactGroup(String n, ArrayList<JSONObject> cList) {
        name = n;
        contacts = new ArrayList<>();
        contacts.addAll(cList);
    }

    static void GenerateRandomGroups(ArrayList<ContactGroup> groupList, int numGroups) {
        ArrayList<JSONObject> cList;
        for (int i = 0; i < numGroups; i++) {
            cList = new ArrayList<>();
            Contact.GenerateRandomContacts(cList, 3);
            groupList.add(new ContactGroup(Utilities.GenerateRandomString(8), cList));
        }
    }

    private String getName(){
        return name;
    }

    private ArrayList<JSONObject> getContacts() {
        return contacts;
    }
}
