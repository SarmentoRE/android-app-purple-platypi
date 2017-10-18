package cmsc355.contactapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Austin on 10/16/2017.
 */

public class RelationRepo {

    private Relation relation;

    public RelationRepo() {
        relation = new Relation();
    }

    public void insert(Relation relation) {
        int relationId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Relation.COLUMN_CONTACT_ID, relation.getContactId());
        values.put(Relation.COLUMN_GROUP_ID, relation.getGroupId());

        relationId = (int) db.insert(Relation.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();
        relation.setRelationId(relationId);
    }

    public void delete(int relation_Id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Relation.TABLE_NAME, Relation._ID + "= ?", new String[]{String.valueOf(relation_Id)});
        DatabaseManager.getInstance().closeDatabase();
    }

    public void deleteAll() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Relation.TABLE_NAME, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void update(Relation relation) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(Relation.COLUMN_CONTACT_ID, relation.getContactId());
        values.put(Relation.COLUMN_GROUP_ID, relation.getGroupId());

        db.update(Relation.TABLE_NAME, values, Relation._ID + "= ?", new String[]{String.valueOf(relation.getRelationId())});
        DatabaseManager.getInstance().closeDatabase();
    }
}
