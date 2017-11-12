package com.cmsc355.contactapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Created by Austin on 10/16/2017.
 * Collection of methods to help interaction with db pertaining to groups
 * should not be used by front end!!!!!!!!
 */
class GroupRepo {
    //deconstruct group obj into usable fields and store in db
    static int insertToDatabase(ContactGroup contactGroup) {
        int groupId;
        SQLiteDatabase sqLiteDatabase = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactGroup.COLUMN_NAME, contactGroup.getName());

        groupId = (int) sqLiteDatabase.insert(ContactGroup.TABLE_NAME, null, values);
        RelationRepo.updateRelations(contactGroup);
        DatabaseManager.getInstance().closeDatabase();
        return groupId;
    }

    //remove a group from the db
    static void delete(int groupId) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(ContactGroup.TABLE_NAME, ContactGroup._ID + " = ?", new String[]{String.valueOf(groupId)});
        db.delete(Relation.TABLE_NAME, Relation.COLUMN_GROUP_ID + " = ?", new String[]{String.valueOf(groupId)});
        DatabaseManager.getInstance().closeDatabase();
    }

    //remove all groups from db
    static void deleteAll() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(ContactGroup.TABLE_NAME, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    //update a group
    static void update(ContactGroup contactGroup) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(ContactGroup.COLUMN_NAME, contactGroup.getName());
        db.update(ContactGroup.TABLE_NAME, values, ContactGroup._ID + "= ?", new String[]{String.valueOf(contactGroup.getGroupId())});
        RelationRepo.updateRelations(contactGroup);

        DatabaseManager.getInstance().closeDatabase();
    }

    //search groups by name
    static ArrayList<ContactGroup> searchGroups(String searchQuery) {
        ArrayList<ContactGroup> groups = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ArrayList<Contact> contacts = new ArrayList<>();
        String[] args = new String[]{"%" + searchQuery + "%"};
        String query = "SELECT * FROM " + ContactGroup.TABLE_NAME
                + " JOIN " + Relation.TABLE_NAME + " ON " + ContactGroup._ID + " = " + Relation.COLUMN_GROUP_ID
                + " JOIN " + Contact.TABLE_NAME + " ON " + Contact._ID + " = " + Relation.COLUMN_CONTACT_ID
                + " WHERE " + ContactGroup.COLUMN_NAME + " LIKE ? ORDER BY " + ContactGroup.COLUMN_NAME + " DESC";
        Cursor cursor = db.rawQuery(query, args); //grabs all groups that have the search string in the name and their associated contacts
        ContactGroup group;
        String oldName = "";
        String groupName;

        if (cursor.moveToFirst()) {
            do {
                groupName = cursor.getString(cursor.getColumnIndex(ContactGroup.COLUMN_NAME));
                if (oldName.isEmpty() || oldName.equals(groupName)) {
                    //grab contact information and add it to the array only if it is in the same group as the previous iteration
                    try {
                        contacts.add(new Contact(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_NAME)),
                                new JSONObject(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_JSON)))));
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                } else {
                    //if we are looking at a new group add the old group to the array and begin a new contact array for the new group
                    group = new ContactGroup(groupName, contacts);
                    groups.add(group);
                    contacts = new ArrayList<>();
                    try {
                        contacts.add(new Contact(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_NAME)),
                                new JSONObject(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_JSON)))));
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
                oldName = cursor.getString(cursor.getColumnIndex(ContactGroup.COLUMN_NAME));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return groups;
    }

    //get a specific group from the group id
    static ContactGroup getGroup(int id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT * FROM " + ContactGroup.TABLE_NAME + " WHERE " + ContactGroup._ID + " = ?";
        String query2 = "SELECT * FROM " + Relation.TABLE_NAME + ", " + Contact.TABLE_NAME + " WHERE "
                + Relation.COLUMN_CONTACT_ID + " = " + Contact._ID + " AND " + Relation.COLUMN_GROUP_ID + " = ?";
        String[] args = new String[]{"" + id};
        Cursor cursor = db.rawQuery(query, args);
        ContactGroup contactGroup = new ContactGroup();
        if (cursor.moveToFirst()) {
            contactGroup.setName(cursor.getString(cursor.getColumnIndex(ContactGroup.COLUMN_NAME)));
        }

        //populate group obj with contacts
        cursor = db.rawQuery(query2, args);
        ArrayList<Contact> contacts = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                try {
                    contacts.add(new Contact(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_NAME)),
                            new JSONObject(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_JSON)))));
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
            while (cursor.moveToNext());
        }
        contactGroup.setContacts(contacts);
        contactGroup.setGroupId(id);
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return contactGroup;
    }


}
