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

    public static class Group implements BaseColumns{
        public static final String TABLE_NAME = "Group";
        public static final String COLUMN_NAME = "Name";
    }

    public static class Relation implements BaseColumns{
        public static final String TABLE_NAME = "Relation";
        public static final String COLUMN_CONTACT_ID = "Contact id";
        public static final String COLUMN_GROUP_ID = "Group id";
    }

}
