package cmsc355.contactapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        //SQLiteDatabase db = DatabaseHelper.openDatabase(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contacts_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<JSONObject> contactList = new ArrayList<>();
        //ArrayList<JSONObject> contactList = DatabaseHelper.getContactList(db);
        //TODONE - replace this line with pulling contacts from database
        //This line still generates random data despite what is in the db
        //TODO: comment next line to see things inside the db
        Contact.GenerateRandomContacts(contactList, 7);
        recyclerView.setAdapter(new ContactsAdapter(contactList));
    }
}
