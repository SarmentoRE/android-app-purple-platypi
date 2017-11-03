package com.cmsc355.contactapp;

import android.provider.BaseColumns;

/**
 * Created by Austin on 10/18/2017.
 */

public class Relation implements BaseColumns {
    public static final String TAG = Relation.class.getSimpleName();
    public static final String TABLE_NAME = "Relation";
    public static final String _ID = "RelationId";
    public static final String COLUMN_CONTACT_ID = "Contact_id";
    public static final String COLUMN_GROUP_ID = "ContactGroup_id";

    private int contactId;
    private int groupId;
    private int relationId;

    public Relation(int contactId, int groupId) {
        this.contactId = contactId;
        this.groupId = groupId;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}

