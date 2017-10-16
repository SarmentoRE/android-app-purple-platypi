package cmsc355.contactapp;

import java.util.ArrayList;

class Group {

    private String name;
    private ArrayList<Contact> contacts;

    public Group() {
        name = "Default";
        contacts = new ArrayList<>();
    }

    public Group(String n, ArrayList<Contact> cList) {
        name = n;
        contacts = new ArrayList<>();
        contacts.addAll(cList);
    }

    static void GenerateRandomGroups(ArrayList<Group> groupList, int numGroups) {
        ArrayList<Contact> cList;
        for (int i = 0; i < numGroups; i++) {
            cList = new ArrayList<>();
            Contact.GenerateRandomContacts(cList, 3);
            groupList.add(new Group(Utilities.GenerateRandomString(8), cList));
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }
}
