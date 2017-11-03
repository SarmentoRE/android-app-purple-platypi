package com.cmsc355.contactapp;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager {
    private static DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private Integer openCounter = 0;
    private SQLiteDatabase database;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName()
                    + " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        openCounter += 1;
        if (openCounter == 1) {
            // Opening new database
            database = mDatabaseHelper.getWritableDatabase();
        }
        return database;
    }

    public synchronized void closeDatabase() {
        openCounter -= 1;
        if (openCounter == 0) {
            // Closing database
            database.close();

        }
    }
}
