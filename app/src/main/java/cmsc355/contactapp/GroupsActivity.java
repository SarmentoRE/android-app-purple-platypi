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

        //SQLiteDatabase db = DatabaseHelper.openDatabase(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.groups_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<ContactGroup> groupsList = new ArrayList<>();

        //groupsList = DatabaseHelper.getGroupList(db);

        ContactGroup.GenerateRandomGroups(groupsList, 3);       //TODONE - replace this line with pulling groups from database

        recyclerView.setAdapter(new GroupsAdapter(groupsList));
    }
}
