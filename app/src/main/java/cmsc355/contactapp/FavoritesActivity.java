package cmsc355.contactapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONObject;

import java.util.ArrayList;

import static cmsc355.contactapp.ContactGroup.groupsMock;

public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.favorites_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Contact> favoritesList = groupsMock.get(0).getContacts();   //TODO - replace this line with pulling favorite contacts from database

        recyclerView.setAdapter(new ContactsAdapter(favoritesList));
    }
}
