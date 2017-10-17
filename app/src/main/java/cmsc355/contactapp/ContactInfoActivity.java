package cmsc355.contactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ContactInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.info_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String name = null;
        JSONObject attributes = new JSONObject();
        try {
            JSONObject json = new JSONObject(intent.getStringExtra("Contact"));
            name = json.getString("Name");
            attributes = json.getJSONObject("Attributes");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        Contact contact = new Contact(name,attributes);

//        Contact contact = Contact.GenerateRandomContact();      //TODO - Pull correct contact from db
        TextView txtName = (TextView) findViewById(R.id.info_name);
        txtName.setText(contact.getName());

        recyclerView.setAdapter(new InfoAdapter(contact));
    }
}
