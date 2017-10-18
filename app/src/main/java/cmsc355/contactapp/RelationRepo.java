package cmsc355.contactapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Austin on 10/16/2017.
 */

public class RelationRepo {

    private DatabaseContract.Relation relation;

    public RelationRepo() {
        relation = new DatabaseContract.Relation();
    }

    public void insert(DatabaseContract.Relation relation) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Relation.COLUMN_CONTACT_ID, relation.getContactId());
        values.put(DatabaseContract.Relation.COLUMN_GROUP_ID, relation.getGroupId());

        db.insert(DatabaseContract.Relation.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete(int relation_Id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(DatabaseContract.Relation.TABLE_NAME, DatabaseContract.Relation.KEY_RelationId + "= ?", new String[]{String.valueOf(relation_Id)});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(DatabaseContract.Relation.TABLE_NAME, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void update(DatabaseContract.Relation relation) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.Relation.COLUMN_CONTACT_ID, relation.getContactId());
        values.put(DatabaseContract.Relation.COLUMN_GROUP_ID, relation.getGroupId());

        db.update(DatabaseContract.Relation.TABLE_NAME, values, DatabaseContract.Relation.KEY_RelationId + "= ?", new String[]{String.valueOf(relation.getRelationId())});
        DatabaseManager.getInstance().closeDatabase();
    }
}
