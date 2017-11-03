package com.cmsc355.contactapp;

import java.util.ArrayList;

/**
 * Created by Austin on 10/27/2017.
 */

public interface IOManager {
    int putContact(Contact contact);

    Contact getContact(int contactId);

    ArrayList<Contact> searchContacts(String searchQuery);

    ArrayList<Contact> getAllContacts();

    void deleteContact(Contact contact);

    int putGroup(ContactGroup group);

    ContactGroup getGroup(int groupId);

    ArrayList<ContactGroup> searchGroups(String searchQuery);

    ArrayList<ContactGroup> getAllGroups();

    void deleteGroup(ContactGroup group);
}
