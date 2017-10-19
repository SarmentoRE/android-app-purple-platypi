package cmsc355.contactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static cmsc355.contactapp.Contact.contactsMock;
import static cmsc355.contactapp.Contact.myInfoMock;
import static cmsc355.contactapp.ContactGroup.groupsMock;

public class ContactInfoActivity extends AppCompatActivity {

    private boolean isEditDisabled;
    private KeyListener keyListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {        //TODO - ability to add new attributes
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        Toolbar infoToolbar = (Toolbar) findViewById(R.id.info_toolbar);
        setSupportActionBar(infoToolbar);

        isEditDisabled = true;
        setupUI(findViewById(R.id.info_parent));
        ((Button)findViewById(R.id.info_edit_button)).setText(R.string.info_edit);

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
        keyListener = txtName.getKeyListener();
        txtName.setHint(contact.getName());

        txtName.setEnabled(false);
        txtName.setClickable(false);
        txtName.setKeyListener(null);

        recyclerView.setAdapter(new InfoAdapter(contact, isEditDisabled));

        Button submitButton = (Button) findViewById(R.id.info_edit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button sButton = (Button) findViewById(R.id.info_edit_button);
                if (isEditDisabled) {
                    isEditDisabled = false;
                    txtName.setEnabled(true);
                    txtName.setClickable(true);
                    txtName.setKeyListener(keyListener);
                    sButton.setText(R.string.info_submit);
                    recyclerView.setAdapter(new InfoAdapter(contact, false));
                }
                else {
                    Contact newContact = new Contact();
                    try {
                        newContact = new Contact(contact.getName(),new JSONObject(contact.getAttributes().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

                    Intent i = new Intent(ContactInfoActivity.this, ContactsActivity.class);
                    if (myInfoMock.ContactToJSON().toString().equals(contact.ContactToJSON().toString())) {
                        myInfoMock = newContact;
                        i = new Intent(ContactInfoActivity.this, HomeActivity.class);
                    }
                    else {
                        for (Contact c : contactsMock) {
                            if (c.ContactToJSON().toString().equals(contact.ContactToJSON().toString())) {
                                c.setName(newContact.getName());
                                c.setAttributes(newContact.getAttributes());
                            }
                        }
                    }
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }

            }
        });

        Button deleteButton = (Button) findViewById(R.id.info_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myInfoMock.ContactToJSON().toString().equals(contact.ContactToJSON().toString())) {
                    Toast.makeText(ContactInfoActivity.this, "Not deletable", Toast.LENGTH_SHORT).show();
                }
                else {
                    Contact targetContact = new Contact();
                    ContactGroup targetGroup = new ContactGroup();
                    boolean targetFound = false;
                    for (Contact c : contactsMock) {
                        if (c.ContactToJSON().toString().equals(contact.ContactToJSON().toString())) {
                            targetContact = c;
                            for (ContactGroup g : groupsMock) {
                                for (Contact groupContact : g.getContacts()) {
                                    if (c.ContactToJSON().toString().equals(groupContact.ContactToJSON().toString())) {
                                        targetGroup = g;
                                        targetFound = true;
                                    }
                                }
                            }
                        }
                    }
                    if (targetFound) {
                        contactsMock.remove(contactsMock.indexOf(targetContact));
                        ArrayList<Contact> targetGroupContacts = targetGroup.getContacts();
                        targetGroupContacts.remove(targetGroupContacts.indexOf(targetContact));
                        Toast.makeText(ContactInfoActivity.this, "Contact deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent i = new Intent(ContactInfoActivity.this, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
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
