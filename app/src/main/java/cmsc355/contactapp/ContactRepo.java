package cmsc355.contactapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Austin on 10/16/2017.
 */

public class ContactRepo {

    private DatabaseContract.Contact contact;

    public ContactRepo() {
        contact = new DatabaseContract.Contact();
    }

    public void insert(DatabaseContract.Contact contact) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Contact.COLUMN_NAME, contact.getName());
        values.put(DatabaseContract.Contact.COLUMN_JSON, contact.getJson());

        db.insert(DatabaseContract.Contact.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(int contact_Id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(DatabaseContract.Contact.TABLE_NAME, DatabaseContract.Contact.KEY_ContactId + "= ?", new String[]{String.valueOf(contact_Id)});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(DatabaseContract.Contact.TABLE_NAME, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void update(DatabaseContract.Contact contact) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.Contact.COLUMN_NAME, contact.getName());
        values.put(DatabaseContract.Contact.COLUMN_JSON, contact.getJson());

        db.update(DatabaseContract.Contact.TABLE_NAME, values, DatabaseContract.Contact.KEY_ContactId + "= ?", new String[]{String.valueOf(contact.getContactId())});
        DatabaseManager.getInstance().closeDatabase();
    }
}
