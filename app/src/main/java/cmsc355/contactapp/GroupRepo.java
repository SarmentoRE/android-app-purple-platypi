package cmsc355.contactapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Austin on 10/16/2017.
 */

public class GroupRepo {

    private ContactGroup contactGroup;

    public GroupRepo() {
        contactGroup = new ContactGroup();
    }

    public int insertToDB(ContactGroup contactGroup) {
        int groupId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactGroup.COLUMN_NAME, contactGroup.getName());


        groupId = (int) db.insert(ContactGroup.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return groupId;
    }

    public void delete(int group_Id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(ContactGroup.TABLE_NAME, ContactGroup._ID + "= ?", new String[]{String.valueOf(group_Id)});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void deleteAll() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(ContactGroup.TABLE_NAME, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void update(ContactGroup contactGroup) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(ContactGroup.COLUMN_NAME, contactGroup.getName());

        db.update(ContactGroup.TABLE_NAME, values, ContactGroup._ID + "= ?", new String[]{String.valueOf(contactGroup.getGroupID())});
        DatabaseManager.getInstance().closeDatabase();
    }

    public ArrayList<ContactGroup> searchGroups(String searchQuery) {
        ArrayList<ContactGroup> groups = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + contactGroup.TABLE_NAME + " WHERE " + contactGroup.COLUMN_NAME + " LIKE ?", new String[]{"%" + searchQuery + "%"});
        ContactGroup group;

        if (cursor.moveToFirst()) {
            do {
                //TODO: make groups return with proper contact list
                group = new ContactGroup(cursor.getString(cursor.getColumnIndex(contactGroup.COLUMN_NAME)), new ArrayList<Contact>());
                groups.add(group);
            } while (cursor.moveToNext());
        }

        DatabaseManager.getInstance().closeDatabase();
        return groups;
    }
}
