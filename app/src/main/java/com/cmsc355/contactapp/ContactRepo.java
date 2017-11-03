package com.cmsc355.contactapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactRepo {

    public ContactRepo() {
    }

    public static int insertToDB(Contact contact) {
        int contactId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Contact.COLUMN_NAME, contact.getName());
        values.put(Contact.COLUMN_JSON, contact.addContactToJSON(new JSONObject()).toString());

        contactId = (int) db.insert(Contact.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return contactId;
    }

    public static void delete(int contact_Id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Contact.TABLE_NAME, Contact._ID + "= ?", new String[]{String.valueOf(contact_Id)});
        DatabaseManager.getInstance().closeDatabase();
    }

    public static void deleteAll() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Contact.TABLE_NAME, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public static void update(Contact contact) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(Contact.COLUMN_NAME, contact.getName());
        values.put(Contact.COLUMN_JSON, contact.addContactToJSON(new JSONObject()).toString());

        db.update(Contact.TABLE_NAME, values, Contact._ID + "= ?", new String[]{String.valueOf(contact.getID())});
        DatabaseManager.getInstance().closeDatabase();
    }

    public static ArrayList<Contact> searchContacts(String searchQuery) {
        ArrayList<Contact> allContacts = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT * FROM " + Contact.TABLE_NAME + " WHERE " + Contact.COLUMN_NAME + " LIKE ?";
        String args[] = new String[]{"%" + searchQuery + "%"};
        Cursor cursor = db.rawQuery(query, args);
        Contact contact;

        if (cursor.moveToFirst()) {
            do {
                try {
                    contact = new Contact(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_NAME)), new JSONObject(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_JSON))));
                    allContacts.add(contact);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        DatabaseManager.getInstance().closeDatabase();
        return allContacts;
    }

    public static Contact getContact(int id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT * FROM " + Contact.TABLE_NAME + " WHERE " + Contact._ID + " = ?";
        String args[] = new String[]{"" + id};
        Cursor cursor = db.rawQuery(query, args);
        Contact contact = new Contact();
        if (cursor.moveToFirst()) {
            try {
                contact.setName(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_NAME)));
                contact.setAttributes(new JSONObject(cursor.getString(cursor.getColumnIndex(Contact.COLUMN_JSON))));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        contact.setID(id);
        return contact;
    }
}
