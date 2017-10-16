package cmsc355.contactapp;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Austin on 10/6/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private final String SQL_CREATE_CONTACT_TABLE = "CREATE TABLE "  + DatabaseContract.Contact.TABLE_NAME + " ("+
            DatabaseContract.Contact.COLUMN_ID+" INTEGER PRIMARY KEY UNIQUE, " +
            DatabaseContract.Contact.COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
            DatabaseContract.Contact.COLUMN_LAST_NAME + " TEXT NOT NULL, " +
            DatabaseContract.Contact.COLUMN_JSON + " BLOB NOT NULL)" ;


    private final String SQL_DELETE_CONTACT_TABLE =
            "DROP TABLE IF EXISTS " + DatabaseContract.Contact.TABLE_NAME;

    private final String SQL_CREATE_GROUP_TABLE = "CREATE TABLE "  + DatabaseContract.Group.TABLE_NAME + " ("+
            DatabaseContract.Group.COLUMN_ID+" INTEGER PRIMARY KEY UNIQUE, " +
            DatabaseContract.Group.COLUMN_NAME + " TEXT NOT NULL)" ;

    private final String SQL_DELETE_GROUPS_TABLE =
            "DROP TABLE IF EXISTS " + DatabaseContract.Group.TABLE_NAME;

    private final String SQL_CREATE_RELATION_TABLE = "CREATE TABLE "  + DatabaseContract.Relation.TABLE_NAME + " ("+
            DatabaseContract.Relation.COLUMN_ID+" INTEGER PRIMARY KEY UNIQUE, " +
            DatabaseContract.Relation.COLUMN_CONTACT_ID+" INTEGER REFERENCES Groups(Group_ID), "+
            DatabaseContract.Relation.COLUMN_GROUP_ID +" INTEGER REFERENCES Contact(Contact_ID))";
    ;

    private final String SQL_DELETE_RELATION_TABLE =
            "DROP TABLE IF EXISTS " + DatabaseContract.Relation.TABLE_NAME;

    private static final String DATABASE_NAME = "Contacts.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CONTACT_TABLE);
        Log.d(TAG,"Contact table created successfully! ***" + SQL_CREATE_CONTACT_TABLE + "***\n***"+SQL_CREATE_GROUP_TABLE+"***");
        db.execSQL(SQL_CREATE_GROUP_TABLE);
        Log.d(TAG,"Group table created successfully!");
        db.execSQL(SQL_CREATE_RELATION_TABLE);
        Log.d(TAG,"Relation table created successfully!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    static public ArrayList<JSONObject> getContactList(SQLiteDatabase db)
    {
        String table = "Contact";
        String[] columns = {"JSON"};
        String selection = DatabaseContract.Contact.COLUMN_JSON+" = ?";
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);

        JSONObject json = new JSONObject();
        ArrayList contactsJSON = new ArrayList();

        while(cursor.moveToNext()){
            String stringForm = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Contact.COLUMN_JSON));
            try {
                json = new JSONObject(stringForm);
            } catch (JSONException e) {
                Log.d("Contacts Activity", "Could not parse JSON: **"+stringForm+"**");
            }
            contactsJSON.add(json);
        }
        return contactsJSON;
    }



    static public SQLiteDatabase openDatabase(Context context)
    {
        DatabaseHelper DbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DbHelper.getReadableDatabase();
        return db;
    }

    static public ArrayList<ContactGroup> getGroupList(SQLiteDatabase db){
        String table = DatabaseContract.Relation.TABLE_NAME+" r JOIN "
                     +DatabaseContract.Group.TABLE_NAME+" g ON "+"g."+DatabaseContract.Group.COLUMN_ID+" = "+"r."+DatabaseContract.Relation.COLUMN_GROUP_ID+" JOIN "
                     +DatabaseContract.Contact.TABLE_NAME+" c ON "+"c."+DatabaseContract.Contact.COLUMN_ID+" = "+"r."+DatabaseContract.Relation.COLUMN_CONTACT_ID;
        String[] columns = {DatabaseContract.Relation.COLUMN_GROUP_ID,DatabaseContract.Relation.COLUMN_CONTACT_ID};
        String selection =  " AND ";
        String[] selectionArgs = null;

        String MY_QUERY = "SELECT * FROM "+table;
        Log.d("DatabaseHelper",MY_QUERY);
        Cursor cursor = db.rawQuery(MY_QUERY, selectionArgs);

        JSONObject contactJSON = new JSONObject();
        ArrayList<ContactGroup> contactGroups = new ArrayList<>();
        ArrayList<JSONObject> contacts = new ArrayList<>();

        while(cursor.moveToNext()){
            Cursor specificGroup = db.rawQuery("SELECT * FROM "+table+" WHERE "+DatabaseContract.Relation.COLUMN_GROUP_ID+" = "+cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Relation.COLUMN_GROUP_ID)) , selectionArgs);
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Group.COLUMN_NAME));
            while(specificGroup.moveToNext()) {
                String stringForm = specificGroup.getString((cursor.getColumnIndexOrThrow(DatabaseContract.Contact.COLUMN_JSON)));
                try {
                    contactJSON = new JSONObject(stringForm);
                } catch (JSONException e) {
                    Log.d("Database Helper", "Could not parse JSON: **" + stringForm + "**");
                }
                contacts.add(contactJSON);
            }
            contactGroups.add(new ContactGroup(name, contacts));
        }

        return contactGroups;
    }
}
