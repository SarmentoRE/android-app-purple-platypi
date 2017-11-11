package com.cmsc355.contactapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/*
 * Created by Austin on 10/16/2017.
 */
public class RelationRepo {

    public static int insertToDatabase(Relation relation) {
        int relationId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Relation.COLUMN_CONTACT_ID, relation.getContactId());
        values.put(Relation.COLUMN_GROUP_ID, relation.getGroupId());

        relationId = (int) db.insert(Relation.TABLE_NAME, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return relationId;
    }

    public static void delete(int relationId) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Relation.TABLE_NAME, Relation._ID + "= ?", new String[]{String.valueOf(relationId)});
        DatabaseManager.getInstance().closeDatabase();
    }

    public static void deleteAll() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Relation.TABLE_NAME, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public static void update(Relation relation) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        values.put(Relation.COLUMN_CONTACT_ID, relation.getContactId());
        values.put(Relation.COLUMN_GROUP_ID, relation.getGroupId());

        db.update(Relation.TABLE_NAME, values, Relation._ID + "= ?", new String[]{String.valueOf(relation.getRelationId())});
        DatabaseManager.getInstance().closeDatabase();
    }

    public static ArrayList<Relation> searchRelationsbyContact(int contactId) {
        ArrayList<Relation> relations = new ArrayList<>();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String querry = "SELECT * FROM " + Relation.TABLE_NAME + " WHERE " + Relation.COLUMN_CONTACT_ID + " = ?";
        String[] vars = new String[]{"" + contactId};
        Cursor cursor = db.rawQuery(querry, vars);
        Relation relation;

        if (cursor.moveToFirst()) {
            do {
                relation = new Relation(cursor.getInt(cursor.getColumnIndex(Relation.COLUMN_CONTACT_ID)),
                        cursor.getInt(cursor.getColumnIndex(Relation.COLUMN_GROUP_ID)));
                relation.setRelationId(cursor.getInt(cursor.getColumnIndex(Relation._ID)));
                relations.add(relation);
            }
            while (cursor.moveToNext());
        }

        DatabaseManager.getInstance().closeDatabase();
        return relations;
    }

    public static ArrayList<Relation> searchRelationsbyGroup(int groupId) {
        ArrayList<Relation> relations = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT * FROM " + Relation.TABLE_NAME + " WHERE " + Relation.COLUMN_GROUP_ID + " = ?";
        String[] vars = new String[]{"" + groupId};
        Cursor cursor = sqLiteDatabase.rawQuery(query, vars);
        Relation relation;

        if (cursor.moveToFirst()) {
            do {
                relation = new Relation(cursor.getInt(cursor.getColumnIndex(Relation.COLUMN_CONTACT_ID)),
                        cursor.getInt(cursor.getColumnIndex(Relation.COLUMN_GROUP_ID)));
                relation.setRelationId(cursor.getInt(cursor.getColumnIndex(Relation._ID)));
                relations.add(relation);
            }
            while (cursor.moveToNext());
        }

        DatabaseManager.getInstance().closeDatabase();
        return relations;
    }

    //helper method to fix relations table when messing with groups
    public static void updateRelations(ContactGroup group) {
        int contactId;
        int groupId = group.getGroupId();
        ArrayList<Contact> contacts = group.getContacts();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        //remove old groups relations
        String query = "SELECT * FROM (SELECT * FROM " + ContactGroup.TABLE_NAME + " JOIN "
                + Relation.TABLE_NAME + " ON " + ContactGroup._ID + " = " + Relation.COLUMN_GROUP_ID + ")"
                + " WHERE " + ContactGroup.COLUMN_NAME + " = ? OR " + ContactGroup._ID + " = ?";
        String[] args = new String[]{String.valueOf(group.getName()), String.valueOf(groupId)};
        Cursor cursor = db.rawQuery(query, args);
        if (cursor.moveToFirst()) {
            do {
                delete(cursor.getInt(cursor.getColumnIndex(Relation._ID)));
            }
            while (cursor.moveToNext());
        }

        //add relations from group into db
        for (int i = 0; i < contacts.size(); i++) {
            contactId = contacts.get(i).getId();
            Relation relation = new Relation(contactId, groupId);
            relation.setRelationId(RelationRepo.insertToDatabase(relation));
        }
        DatabaseManager.getInstance().closeDatabase();
    }

}
