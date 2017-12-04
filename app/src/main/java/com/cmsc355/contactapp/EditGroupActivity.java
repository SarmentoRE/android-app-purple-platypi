package com.cmsc355.contactapp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EditGroupActivity extends NonHomeActivity {

    private RecyclerView recyclerView;
    private ArrayList<Contact> allContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        Toolbar editGroupToolbar = findViewById(R.id.edit_group_toolbar);
        setSupportActionBar(editGroupToolbar);

        recyclerView = findViewById(R.id.edit_group_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button submitButton = findViewById(R.id.edit_group_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Contact> groupContacts = new ArrayList<>();
                for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
                    EditGroupAdapter.ViewHolder viewHolder = (EditGroupAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);

                    if (viewHolder != null) {
                        if (viewHolder.checkBox.isChecked()) {
                            // TODO - Add contact to group
                            Log.d("EditGroup", viewHolder.checkBox.getText() + " is checked");
                            groupContacts.add(allContacts.get(i));
                        }
                        else {
                            Log.d("EditGroup", viewHolder.checkBox.getText() + " is unchecked");
                        }
                    }
                    else {
                        Log.e("EditGroup", "Trying to pull data from a null view.");
                    }
                }
                Log.d("EditGroup", "Found " + groupContacts.size() + " contacts");

                EditText groupNameEdit = findViewById(R.id.edit_group_name);
                String groupName = groupNameEdit.getText().toString();
                if (groupName.isEmpty()) {
                    groupName = "New Group";
                }
                ContactGroup newGroup = new ContactGroup(groupName,groupContacts);
                App.databaseIoManager.putGroup(newGroup);

                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Sorts the contact list, then displays it
        allContacts = Utilities.sortContactList(App.databaseIoManager.getAllContacts());
        Log.d("EditGroup", "Sending " + allContacts.size() + " contacts");
        recyclerView.setAdapter(new EditGroupAdapter(allContacts));
    }

    //adds the home button to the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    //home button takes you straight home, resets the list of activities for the back button
    //(see https://developer.android.com/guide/components/activities/tasks-and-back-stack.html)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
