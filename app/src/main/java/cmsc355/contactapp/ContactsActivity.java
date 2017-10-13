package cmsc355.contactapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contacts_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Contact> contactList = new ArrayList<>();
        Contact.GenerateRandomContacts(contactList, 7);   //TODO - replace this line with pulling contacts from database

        recyclerView.setAdapter(new ContactsAdapter(contactList));
    }
}
