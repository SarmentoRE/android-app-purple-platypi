package com.cmsc355.contactapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import static com.cmsc355.contactapp.App.databaseIoManager;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Home","Creating Home activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar homeToolbar = (Toolbar) findViewById(R.id.home_toolbar);    //home doesn't have onCreateOptionsMenu, because
        setSupportActionBar(homeToolbar);                                   //it doesn't display the Home action button
        ListView listView = (ListView) findViewById(R.id.home_list);

        setupButtonList(listView);

        // Check if My Info data has been inserted into the database already, insert if it hasn't
        if (databaseIoManager.getAllContacts().isEmpty()) {
            JSONObject testAttributes = new JSONObject();
            try {
                testAttributes.put("Email","Enter Email");
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
            Contact myInfoContact = new Contact("Enter Name",testAttributes);
            int i = databaseIoManager.putContact(myInfoContact);
            Log.d("Home", "Contact Id: " + i);
        }
        else {
            Log.d("Home","First contact name: " + databaseIoManager.getContact(1).getName());
        }

        // Check if Favorites data has been inserted into the database already, insert if it hasn't
        if (databaseIoManager.getAllGroups().isEmpty()) {
            ArrayList<Contact> noContacts = new ArrayList<>();
            ContactGroup favGroup = new ContactGroup("Favorites", noContacts);
            int i = databaseIoManager.putGroup(favGroup);
            Log.d("Home", "FavGroup id: " + i);
        }
        else {
            Log.d("Home","First group name: " + databaseIoManager.getGroup(1).getName());
        }
    }

    private void setupButtonList(ListView listView) {
        ArrayList<String> buttonNames = new ArrayList<>();
        buttonNames.addAll(Arrays.asList(getResources().getStringArray(R.array.home_button_names)));

        listView.setAdapter(new ArrayAdapter<>(this, R.layout.item_home, R.id.home_item_name, buttonNames));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {    //Set button destination based on location
                Intent intent = null;
                switch (position) {
                  case 0:
                      intent = new Intent(HomeActivity.this, ConnectActivity.class);
                      break;
                  case 1:
                      intent = new Intent(HomeActivity.this, ContactsActivity.class);
                      break;
                  case 2:
                      intent = new Intent(HomeActivity.this, FavoritesActivity.class);
                      break;
                  case 3:
                      intent = new Intent(HomeActivity.this, GroupsActivity.class);
                      break;
                  case 4:
                      intent = new Intent(HomeActivity.this, ContactInfoActivity.class);
                      //TODO - retrieve "my info" from db
                      intent.putExtra("ContactID", 1);
                      intent.putExtra("isEditEnabled", true);
                      break;
                  case 5:
                      intent = new Intent(HomeActivity.this, ScanActivity.class);
                      break;
                  case 6:
                      intent = new Intent(HomeActivity.this, SettingsActivity.class);
                      break;
                  default:
                      break;
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private void insertSampleData() throws JSONException {
        ContactRepo.deleteAll();
        GroupRepo.deleteAll();
        RelationRepo.deleteAll();

        Contact contact = new Contact("Austin", new JSONObject("{\"Name\":\"Austin\"}"));
        ContactRepo.insertToDatabase(contact);

    }
}
