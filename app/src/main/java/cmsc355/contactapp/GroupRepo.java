package cmsc355.contactapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Austin on 10/16/2017.
 */

public class GroupRepo {

    private ContactGroup contactGroup;

    public GroupRepo() {
        contactGroup = new ContactGroup();
    }

    public void insert(ContactGroup contactGroup) {
        int groupId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactGroup.COLUMN_NAME, contactGroup.getName());


        groupId = (int) db.insert(ContactGroup.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();
        contactGroup.setGroupID(groupId);
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
}
