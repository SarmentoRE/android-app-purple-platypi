package com.cmsc355.contactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Toolbar contactToolbar = (Toolbar) findViewById(R.id.contact_toolbar);
        setSupportActionBar(contactToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.contacts_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //SQLiteDatabase db = DatabaseHelper.openDatabase(this);        //TODO - pull contacts from database

        //This button generates a new contact, and takes you to the ContactInfo screen to edit it
        Button newContactButton = (Button) findViewById(R.id.contact_new);
        newContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact newContact = new Contact();
                App.databaseIoManager.putContact(newContact);

                Intent intent = new Intent(ContactsActivity.this, ContactInfoActivity.class);
                intent.putExtra("ContactID", newContact.getId());
                intent.putExtra("isEditEnabled", true);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Sorts the contact list, then displays it
        ArrayList<Contact> sortedList = Utilities.sortContactList(App.databaseIoManager.getAllContacts());
        recyclerView.setAdapter(new ContactsAdapter(sortedList));
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
              Intent intent = new Intent(ContactsActivity.this, HomeActivity.class);
              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(intent);
              return true;
          default:
              return super.onOptionsItemSelected(item);

        }
    }
}