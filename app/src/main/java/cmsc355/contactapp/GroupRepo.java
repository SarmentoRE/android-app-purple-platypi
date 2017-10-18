package cmsc355.contactapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Austin on 10/16/2017.
 */

public class GroupRepo {

    private DatabaseContract.Group group;

    public GroupRepo() {
        group = new DatabaseContract.Group();
    }

    public void insert(DatabaseContract.Group group) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Group.COLUMN_NAME, group.getName());

        db.insert(DatabaseContract.Group.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(int group_Id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(DatabaseContract.Group.TABLE_NAME, DatabaseContract.Group.KEY_GroupId + "= ?", new String[]{String.valueOf(group_Id)});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(DatabaseContract.Group.TABLE_NAME, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void update(DatabaseContract.Group group) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.Group.COLUMN_NAME, group.getName());

        db.update(DatabaseContract.Group.TABLE_NAME, values, DatabaseContract.Group.KEY_GroupId + "= ?", new String[]{String.valueOf(group.getGroupID())});
        DatabaseManager.getInstance().closeDatabase();
    }
}
