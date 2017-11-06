package com.cmsc355.contactapp;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "Contacts.db";
    private static final int DATABASE_VERSION = 1;




    DatabaseHelper() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateContactTable = "CREATE TABLE IF NOT EXISTS " + Contact.TABLE_NAME + "("
                + Contact._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contact.COLUMN_NAME + " TEXT NOT NULL, "
                + Contact.COLUMN_JSON + " TEXT NOT NULL );";
        db.execSQL(sqlCreateContactTable);
        Log.d(TAG, "Contact table created successfully!");

        String sqlCreateGroupsTable = "CREATE TABLE IF NOT EXISTS " + ContactGroup.TABLE_NAME + "("
                + ContactGroup._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ContactGroup.COLUMN_NAME + " TEXT NOT NULL );";
        db.execSQL(sqlCreateGroupsTable);
        Log.d(TAG, "Groups table created successfully!");

        String sqlCreateRelationTable = "CREATE TABLE IF NOT EXISTS " + Relation.TABLE_NAME + "("
                + Relation._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Relation.COLUMN_CONTACT_ID + " INTEGER NOT NULL, "
                + Relation.COLUMN_GROUP_ID + " INTEGER NOT NULL );";
        db.execSQL(sqlCreateRelationTable);
        Log.d(TAG, "Relation table created successfully");

        final String sqlDeleteContactTable =
                "DROP TABLE IF EXISTS" + Contact.TABLE_NAME;

        final String sqlDeleteGroupsTable =
                "DROP TABLE IF EXISTS" + ContactGroup.TABLE_NAME;

        final String sqlDeleteRelationTable =
                "DROP TABLE IF EXISTS" + Relation.TABLE_NAME;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
