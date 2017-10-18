package cmsc355.contactapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Austin on 10/16/2017.
 */

public class ContactRepo {

    private Contact contact;

    public ContactRepo() {
        contact = new Contact();
    }

    public static int insertToDB(Contact contact) {
        int contactId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Contact.COLUMN_NAME, contact.getName());
        values.put(Contact.COLUMN_JSON, contact.ContactToJSON().toString());

        contactId = (int) db.insert(Contact.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return contactId;
    }

    public void delete(int contact_Id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Contact.TABLE_NAME, Contact._ID + "= ?", new String[]{String.valueOf(contact_Id)});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void deleteAll() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Contact.TABLE_NAME, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void update(Contact contact) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(Contact.COLUMN_NAME, contact.getName());
        values.put(Contact.COLUMN_JSON, contact.ContactToJSON().toString());

        db.update(Contact.TABLE_NAME, values, Contact._ID + "= ?", new String[]{String.valueOf(contact.getContactId())});
        DatabaseManager.getInstance().closeDatabase();
    }

    public ArrayList<Contact> searchContacts(String searchQuery) {
        ArrayList<Contact> allContacts = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Contact.TABLE_NAME + " WHERE " + Contact.COLUMN_NAME + " LIKE ?", new String[]{"%" + searchQuery + "%"});
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
}
