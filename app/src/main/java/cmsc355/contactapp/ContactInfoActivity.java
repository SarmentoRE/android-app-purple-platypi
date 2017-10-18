package cmsc355.contactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import static cmsc355.contactapp.Contact.contactsMock;

public class ContactInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        setupUI(findViewById(R.id.info_parent));

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.info_list);
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
        final Contact contact = new Contact(name,attributes);       //TODO - Pull correct contact from db
        final EditText txtName = (EditText) findViewById(R.id.info_name);
        txtName.setHint(contact.getName());

        recyclerView.setAdapter(new InfoAdapter(contact));

        Button submitButton = (Button) findViewById(R.id.info_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact newContact = new Contact(contact.getName(),contact.getAttributes());
                String nameInput = txtName.getText().toString();
                if (!nameInput.matches("")) {
                    newContact.setName(nameInput);
                    txtName.setHint(nameInput);
                    txtName.getText().clear();
                }

                int numAttributes = recyclerView.getAdapter().getItemCount();
                JSONObject newAttributes = newContact.getAttributes();
                for (int i = 0; i < numAttributes; i++) {
                    InfoAdapter.ViewHolder vHolder = (InfoAdapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition(i);
                    TextView textView = vHolder.txtKey;
                    EditText editText = vHolder.txtValue;
                    if (!editText.getText().toString().matches("")) {
                        String attrKey = textView.getText().toString();
                        attrKey = attrKey.substring(0,attrKey.length()-1);
                        String newAttrValue = editText.getText().toString();
                        try {
                            newAttributes.put(attrKey,newAttrValue);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        editText.setHint(newAttrValue);
                        editText.getText().clear();
                    }
                }
                newContact.setAttributes(newAttributes);

                if (contactsMock.contains(contact)) {
                    contactsMock.set(contactsMock.indexOf(contact),newContact);
                }
                else {
                    int index = 0;
                    for (Contact c : contactsMock) {
                        if (c.getName().matches(contact.getName())) {
                            index = contactsMock.indexOf(c);
                        }
                    }
                    contactsMock.set(index,newContact);
                }

//                Intent i = new Intent(ContactInfoActivity.this, ContactsActivity.class);
//                startActivity(i);
            }
        });
    }

    public void setupUI(View view) {

        // Set up touch listener for non-edittext views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    Utilities.hideSoftKeyboard(ContactInfoActivity.this);
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}
