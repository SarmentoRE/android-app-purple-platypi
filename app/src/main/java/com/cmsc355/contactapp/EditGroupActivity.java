package com.cmsc355.contactapp;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class EditGroupActivity extends NonHomeActivity {

    private RecyclerView recyclerView;
    private ArrayList<Contact> allContacts;

    private long clickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        Toolbar editGroupToolbar = findViewById(R.id.edit_group_toolbar);
        setSupportActionBar(editGroupToolbar);

        setupUi(findViewById(R.id.edit_group_activity));

        recyclerView = findViewById(R.id.edit_group_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button submitButton = findViewById(R.id.edit_group_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - clickTime > 1000) {
                    ArrayList<Contact> groupContacts = new ArrayList<>();
                    for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
                        EditGroupAdapter.ViewHolder viewHolder = (EditGroupAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);

                        if (viewHolder != null) {
                            if (viewHolder.checkBox.isChecked()) {
                                // TODO - Add contact to group
                                Log.d("EditGroup", viewHolder.checkBox.getText() + " is checked");
                                groupContacts.add(allContacts.get(i));
                            } else {
                                Log.d("EditGroup", viewHolder.checkBox.getText() + " is unchecked");
                            }
                        } else {
                            Log.e("EditGroup", "Trying to pull data from a null view.");
                        }
                    }

                    EditText groupNameEdit = findViewById(R.id.edit_group_name);
                    String groupName = groupNameEdit.getText().toString();
                    if (groupName.isEmpty()) {
                        groupName = "New Group";
                    }
                    ContactGroup newGroup = new ContactGroup(groupName,groupContacts);
                    Log.d("EditGroup", "# added contacts: " + newGroup.getContacts().size());
                    int groupId = App.databaseIoManager.putGroup(newGroup);
                    Log.d("EditGroup","Group ID: " + groupId);

                    clickTime = SystemClock.elapsedRealtime();
                    finish();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Sorts the contact list, then displays it
        allContacts = App.databaseIoManager.getAllContacts();
        if (!allContacts.isEmpty()) {
            allContacts.remove(0);
            allContacts = Utilities.sortContactList(allContacts);
            Log.d("EditGroup", "Sending " + allContacts.size() + " contacts");
        }
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

    public void setupUi(View view) {

        //Make it so that, if we touch a view that isn't edittext, it hides the keyboard
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent event) {
                    view.performClick();
                    Utilities.hideSoftKeyboard(EditGroupActivity.this);
                    return false;
                }
            });
        }

        //recursive call to get children inside a viewgroup
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUi(innerView);
            }
        }
    }
}
