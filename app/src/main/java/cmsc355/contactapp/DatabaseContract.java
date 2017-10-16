package cmsc355.contactapp;

import android.provider.BaseColumns;

/**
 * Created by Austin on 10/6/2017.
 */

public final class DatabaseContract {
    private DatabaseContract(){}

    public static class Contact implements BaseColumns{
        public static final String TAG = Contact.class.getSimpleName();
        public static final String TABLE_NAME = "Contact";
        public static final String KEY_ContactId = "ContactId";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_JSON = "JSON";

        private String contactId;
        private String name;
        private String json;

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }

        public String getContactId() {
            return contactId;
        }

        public void setContactId(String contactId) {
            this.contactId = contactId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Group implements BaseColumns{
        public static final String TAG = Group.class.getSimpleName();
        public static final String TABLE_NAME = "Group";
        public static final String KEY_GroupId = "GroupId";
        public static final String COLUMN_NAME = "Name";

        private String groupID;
        private String name;

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Relation implements BaseColumns{
        public static final String TAG = Relation.class.getSimpleName();
        public static final String TABLE_NAME = "Relation";
        public static final String KEY_RelationId = "RelationId";
        public static final String COLUMN_CONTACT_ID = "Contact id";
        public static final String COLUMN_GROUP_ID = "Group id";

        private String contactId;
        private String groupId;
        private String relationId;

        public String getRelationId() {
            return relationId;
        }

        public void setRelationId(String relationId) {
            this.relationId = relationId;
        }

        public String getContactId() {
            return contactId;
        }

        public void setContactId(String contactId) {
            this.contactId = contactId;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
    }
}
