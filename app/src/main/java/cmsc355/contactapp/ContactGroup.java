package cmsc355.contactapp;

import java.util.ArrayList;

class ContactGroup {

    String name;
    ArrayList<Contact> contacts;

    public ContactGroup() {
        name = "Default";
        contacts = new ArrayList<>();
    }

    private ContactGroup(String n, ArrayList<Contact> cList) {
        name = n;
        contacts = new ArrayList<>();
        contacts.addAll(cList);
    }

    static void GenerateRandomGroups(ArrayList<ContactGroup> groupList, int numGroups) {
        for (int i = 0; i < numGroups; i++) {
            groupList.add(new ContactGroup(Utilities.GenerateRandomString(8), new ArrayList<Contact>()));
            Contact.GenerateRandomContacts(groupList.get(i).contacts, 3);
        }
    }
}
