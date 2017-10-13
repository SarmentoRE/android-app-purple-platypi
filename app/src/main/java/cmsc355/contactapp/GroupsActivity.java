package cmsc355.contactapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
}
