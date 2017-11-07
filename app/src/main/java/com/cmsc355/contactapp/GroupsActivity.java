package com.cmsc355.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class GroupsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        Toolbar groupsToolbar = (Toolbar) findViewById(R.id.group_toolbar);
        setSupportActionBar(groupsToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.group_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button newGroupButton = (Button) findViewById(R.id.group_new);
        newGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GroupsActivity.this, "Create New Group", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //want to pass the adapter all the groups and all the contacts related to each group
        //that way, it can generate the correct amount of items in its list, and also put them
        //in the correct order
        ArrayList<ContactGroup> groupsList = Utilities.sortGroupList(App.databaseIoManager.getAllGroups());

        recyclerView.setAdapter(new GroupsAdapter(groupsList));
    }

    //adds the home button to the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    //home button takes you straight home, resets the list of activities for the back button
    //(see https://developer.android.com/guide/components/activities/tasks-and-back-stack.html)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
          case R.id.action_home:
              Intent intent = new Intent(GroupsActivity.this, HomeActivity.class);
              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(intent);
              return true;
          default:
              return super.onOptionsItemSelected(item);

        }
    }
}