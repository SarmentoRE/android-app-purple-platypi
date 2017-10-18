package cmsc355.contactapp;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Austin on 10/6/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "Contacts.db";
    private static final int DATABASE_VERSION = 1;
    private final String SQL_CREATE_CONTACT_TABLE = "CREATE TABLE " + DatabaseContract.Contact.TABLE_NAME + "(" +
            DatabaseContract.Contact.KEY_ContactId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.Contact.COLUMN_NAME + " TEXT NOT NULL, " +
            DatabaseContract.Contact.COLUMN_JSON + " BLOB NOT NULL )";
    private final String SQL_DELETE_CONTACT_TABLE =
            "DROP TABLE IF EXISTS" + DatabaseContract.Contact.TABLE_NAME;
    private final String SQL_CREATE_GROUPS_TABLE = "CREATE TABLE " + DatabaseContract.Group.TABLE_NAME + "(" +
            DatabaseContract.Group.KEY_GroupId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.Group.COLUMN_NAME + " TEXT NOT NULL )";
    private final String SQL_DELETE_GROUPS_TABLE =
            "DROP TABLE IF EXISTS" + DatabaseContract.Group.TABLE_NAME;
    private final String SQL_CREATE_RELATION_TABLE = "CREATE TABLE " + DatabaseContract.Relation.TABLE_NAME + "(" +
            DatabaseContract.Relation.KEY_RelationId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.Relation.COLUMN_CONTACT_ID + " INTEGER NOT NULL, " +
            DatabaseContract.Relation.COLUMN_GROUP_ID + " INTEGER NOT NULL )";
    private final String SQL_DELETE_RELATION_TABLE =
            "DROP TABLE IF EXISTS" + DatabaseContract.Group.TABLE_NAME;
    ;

    public DatabaseHelper() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CONTACT_TABLE);
        Log.d(TAG, "Contact table created successfully!");
        db.execSQL(SQL_CREATE_GROUPS_TABLE);
        Log.d(TAG, "Groups table created successfully!");
        db.execSQL(SQL_CREATE_RELATION_TABLE);
        Log.d(TAG, "Relation table created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
