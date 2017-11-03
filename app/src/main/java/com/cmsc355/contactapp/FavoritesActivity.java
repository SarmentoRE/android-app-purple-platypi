package com.cmsc355.contactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import static com.cmsc355.contactapp.ContactGroup.groupsMock;

public class FavoritesActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Toolbar favoritesToolbar = (Toolbar) findViewById(R.id.favorites_toolbar);
        setSupportActionBar(favoritesToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.favorites_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //assumes the first group in the db mock is the favorites group
        ArrayList<Contact> favoritesList = groupsMock.get(0).getContacts();   //TODO - replace this line with pulling favorite contacts from database
        //from here it works identical to the Contacts screen
        favoritesList = Utilities.sortContactList(favoritesList);
        recyclerView.setAdapter(new ContactsAdapter(favoritesList));
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
                Intent i = new Intent(FavoritesActivity.this, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
