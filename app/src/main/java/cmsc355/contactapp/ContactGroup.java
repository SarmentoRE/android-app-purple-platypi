package cmsc355.contactapp;

import android.provider.BaseColumns;

import java.util.ArrayList;

//interface is for database
class ContactGroup implements BaseColumns {

    //database stuff
    public static final String TAG = ContactGroup.class.getSimpleName();
    public static final String TABLE_NAME = "ContactGroup";
    public static final String _ID = "GroupId";
    public static final String COLUMN_NAME = "GroupName";

    //this mocks the groups column of the database; other mocking elements in Contact class
    static ArrayList<ContactGroup> groupsMock;


    private int groupId;
    private String name;
    private ArrayList<Contact> contacts;

    ContactGroup() {
        name = "Default";
        contacts = new ArrayList<>();
        groupId = -1;
    }

    //note: addAll here adds the same contacts, not copies of the contacts; be careful of this
    ContactGroup(String n, ArrayList<Contact> cList) {
        name = n;
        contacts = new ArrayList<>();
        contacts.addAll(cList);
        groupId = -1;
    }

    //Generates new groups, each containing the same amount of contacts
    static ArrayList<ContactGroup> GenerateRandomGroups(int numGroups, int numContacts) {
        ArrayList<ContactGroup> groupList = new ArrayList<>();
        ArrayList<Contact> cList;
        for (int i = 0; i < numGroups; i++)
        {
            cList = Contact.GenerateRandomContacts(numContacts);
            groupList.add(new ContactGroup(Utilities.GenerateRandomString(8), cList));
        }
        return groupList;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String n) {
        this.name = n;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> cList) {
        this.contacts = cList;
    }

    int getGroupId() {
        return groupId;
    }

    void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
