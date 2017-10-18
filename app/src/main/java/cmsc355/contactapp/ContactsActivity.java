package cmsc355.contactapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import static cmsc355.contactapp.Contact.contactsMock;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contacts_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //SQLiteDatabase db = DatabaseHelper.openDatabase(this);

        ArrayList<Contact> contactList = contactsMock;   //TODO - replace this line with pulling contacts from database

        recyclerView.setAdapter(new ContactsAdapter(contactList));
    }
}
