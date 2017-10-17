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

        //SQLiteDatabase db = DatabaseHelper.openDatabase(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contacts_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //TODONE - replace this line with pulling contacts from database
        //This line still generates random data despite what is in the db
        //TODO: comment next line to see things inside the db
        Contact.GenerateRandomContacts(contactList, 7);
        recyclerView.setAdapter(new ContactsAdapter(contactList));
    }
}
