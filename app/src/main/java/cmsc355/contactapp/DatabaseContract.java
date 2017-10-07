package cmsc355.contactapp;

import android.provider.BaseColumns;

/**
 * Created by Austin on 10/6/2017.
 */

public final class DatabaseContract {
    private DatabaseContract(){}

    public static class Contact implements BaseColumns{
        public static final String TABLE_NAME = "Contact";
        public static final String COLUMN_FIRST_NAME = "First Name";
        public static final String COLUMN_LAST_NAME = "Last Name";
        public static final String COLUMN_JSON = "JSON";
    }

    final String SQL_CREATE_CONTACT_TABLE = "CREATE TABLE"  + DatabaseContract.Contact.TABLE_NAME + "("+
            DatabaseContract.Contact._ID+"INTEGER PRIMARY KEY AUTOINCREMENT," +
            DatabaseContract.Contact.COLUMN_FIRST_NAME + "TEXT NOT NULL," +
            DatabaseContract.Contact.COLUMN_LAST_NAME + "TEXT NOT NULL," +
            DatabaseContract.Contact.COLUMN_JSON + "BLOB NOT NULL" +");";

    final String SQL_DELETE_CONTACT_TABLE =
            "DROP TABLE IF EXISTS" + DatabaseContract.Contact.TABLE_NAME;

}
