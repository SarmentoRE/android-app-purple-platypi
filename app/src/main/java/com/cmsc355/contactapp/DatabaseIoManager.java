package com.cmsc355.contactapp;

import android.util.Log;

import java.util.ArrayList;

public class DatabaseIoManager implements IoManager {

    @Override
    //puts a contact into the database, if the contact already exists it updates the existing one
    public int putContact(Contact contact) {
        int contactId = contact.getId();
        if (contactId == -1) {
            contactId = ContactRepo.insertToDatabase(contact);
            contact.setId(contactId);
            Log.d("DBIO"," CONTACT ID IS: " + contactId);
            return contactId;
        } else {
            ContactRepo.update(contact);
            return contact.getId();
        }
    }

    @Override
    //get a single contact from the db using id
    public Contact getContact(int contactId) {
        return ContactRepo.getContact(contactId);
    }

    @Override
    //search contacts by name (first and last)
    public ArrayList<Contact> searchContacts(String searchQuery) {
        return ContactRepo.searchContacts(searchQuery);
    }

    @Override
    //returns a list of all contacts in the db
    public ArrayList<Contact> getAllContacts() {
        return ContactRepo.searchContacts("");
    }

    @Override
    //removes a contact from the db
    public void deleteContact(Contact contact) {
        ContactRepo.delete(contact.getId());
    }

    @Override
    //puts a group into the db, if the group already exists it updates it
    public int putGroup(ContactGroup group) {
        int groupId;

        if (group.getGroupId() == -1) { //new group
            groupId = GroupRepo.insertToDatabase(group);
        } else {
            //old group
            GroupRepo.update(group);

            return group.getGroupId();
        }
        return groupId;
    }

    @Override
    //get a single group from id
    public ContactGroup getGroup(int groupId) {
        return GroupRepo.getGroup(groupId);
    }

    @Override
    //search groups by group name
    public ArrayList<ContactGroup> searchGroups(String searchQuery) {
        return GroupRepo.searchGroups(searchQuery);
    }

    @Override
    //get a list of all the groups
    public ArrayList<ContactGroup> getAllGroups() {
        return GroupRepo.searchGroups("");
    }

    @Override
    //remove a group from db
    public void deleteGroup(ContactGroup group) {
        GroupRepo.delete(group.getGroupId());
    }

}
