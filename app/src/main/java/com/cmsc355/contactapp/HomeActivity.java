package com.cmsc355.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar homeToolbar = (Toolbar) findViewById(R.id.home_toolbar);    //home doesn't have onCreateOptionsMenu, because
        setSupportActionBar(homeToolbar);                                   //it doesn't display the Home action button
        ListView listView = (ListView) findViewById(R.id.home_list);

        setupButtonList(listView);
    }

    private void setupButtonList(ListView listView) {
        ArrayList<String> buttonNames = new ArrayList<>();
        buttonNames.addAll(Arrays.asList(getResources().getStringArray(R.array.home_button_names)));

        listView.setAdapter(new ArrayAdapter<>(this, R.layout.item_home, R.id.home_item_name, buttonNames));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {    //Set button destination based on location
                Intent i = null;
                switch (position) {
                    case 0:
                        i = new Intent(HomeActivity.this, ConnectActivity.class);
                        break;
                    case 1:
                        i = new Intent(HomeActivity.this, ContactsActivity.class);
                        break;
                    case 2:
                        i = new Intent(HomeActivity.this, FavoritesActivity.class);
                        break;
                    case 3:
                        i = new Intent(HomeActivity.this, GroupsActivity.class);
                        break;
                    case 4:
                        i = new Intent(HomeActivity.this, ContactInfoActivity.class);
                        i.putExtra("Contact", Contact.myInfoMock.addContactToJSON(new JSONObject()).toString());       //TODO - retrieve "my info" from db
                        i.putExtra("isEditEnabled", true);
                        break;
                    case 5:
                        i = new Intent(HomeActivity.this, ScanActivity.class);
                        break;
                    case 6:
                        i = new Intent(HomeActivity.this, SettingsActivity.class);
                        break;
                    default:
                        break;
                }
                if (i != null) {
                    startActivity(i);
                }
            }
        });
    }

    private void insertSampleData() throws JSONException {
        ContactRepo contactRepo = new ContactRepo();
        GroupRepo groupRepo = new GroupRepo();
        RelationRepo relationRepo = new RelationRepo();

        contactRepo.deleteAll();
        groupRepo.deleteAll();
        relationRepo.deleteAll();

        Contact contact = new Contact("Austin", new JSONObject("{\"Name\":\"Austin\"}"));
        contactRepo.insertToDB(contact);

    }
}
