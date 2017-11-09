package com.cmsc355.contactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

//This activity is empty and doesn't do anything yet
public class SettingsActivity extends NonHomeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar settingsToolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(settingsToolbar);

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
