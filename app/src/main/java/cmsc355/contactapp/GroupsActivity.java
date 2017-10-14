package cmsc355.contactapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.groups_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<ContactGroup> groupsList = new ArrayList<>();
        ContactGroup.GenerateRandomGroups(groupsList, 3);       //TODO - replace this line with pulling groups from database

        recyclerView.setAdapter(new GroupsAdapter(groupsList));
    }

    public ArrayList<JSONObject> getContactList(SQLiteDatabase db) {
        String table = "Group";
        String[] columns = {"JSON"};
        String selection = DatabaseContract.Contact.COLUMN_JSON + " = ?";
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);

        JSONObject json = new JSONObject();
        ArrayList contactsJSON = new ArrayList();

        while (cursor.moveToNext()) {
            String stringForm = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Contact.COLUMN_JSON));
            try {
                json = new JSONObject(stringForm);
            } catch (JSONException e) {
                Log.d("Contacts Activity", "Could not parse JSON: **" + stringForm + "**");
            }
            contactsJSON.add(json);
        }
        return contactsJSON;
    }
}
