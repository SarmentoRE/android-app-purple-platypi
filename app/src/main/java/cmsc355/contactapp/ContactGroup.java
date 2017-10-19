package cmsc355.contactapp;

import android.provider.BaseColumns;

import java.util.ArrayList;

class ContactGroup implements BaseColumns {

    public static final String TAG = ContactGroup.class.getSimpleName();
    public static final String TABLE_NAME = "ContactGroup";
    public static final String _ID = "GroupId";
    public static final String COLUMN_NAME = "Name";
    static ArrayList<ContactGroup> groupsMock;
    private int groupID;
    private String name;
    private ArrayList<Contact> contacts;

    ContactGroup() {
        name = "Default";
        contacts = new ArrayList<>();
    }

    ContactGroup(String n, ArrayList<Contact> cList)
    {
        name = n;
        contacts = new ArrayList<>();
        contacts.addAll(cList);
    }

    static ArrayList<ContactGroup> GenerateRandomGroups(int numGroups, int numContacts)
    {
        ArrayList<ContactGroup> groupList = new ArrayList<>();
        ArrayList<Contact> cList;
        for (int i = 0; i < numGroups; i++)
        {
            cList = Contact.GenerateRandomContacts(numContacts);
            groupList.add(new ContactGroup(Utilities.GenerateRandomString(8), cList));
        }
        return groupList;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {this.name = n;}

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> cList) {
        this.contacts = cList;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}
