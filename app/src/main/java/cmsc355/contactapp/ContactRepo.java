package cmsc355.contactapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Austin on 10/16/2017.
 */

public class ContactRepo {

    private Contact contact;

    public ContactRepo() {
        contact = new Contact();
    }

    public void insert(Contact contact) {
        int contactId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Contact.COLUMN_NAME, contact.getName());
        values.put(Contact.COLUMN_JSON, contact.ContactToJSON().toString());

        contactId = (int) db.insert(Contact.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();
        // contact.setContactId(String.valueOf(contactId));
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
}
