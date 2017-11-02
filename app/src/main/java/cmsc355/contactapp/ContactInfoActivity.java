package cmsc355.contactapp;

import android.content.Intent;
import android.os.SystemClock;
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

import java.sql.Time;
import java.util.ArrayList;

import static cmsc355.contactapp.Contact.contactsMock;
import static cmsc355.contactapp.Contact.myInfoMock;
import static cmsc355.contactapp.ContactGroup.groupsMock;

public class ContactInfoActivity extends AppCompatActivity
{

    //boolean toggles the ability to edit all the edittexts. keyListener is stored when edittexts are
    //disabled, so they can be reenabled properly later. callingActivity keeps the activity which called
    //this activity, to go back to proper screen
    private boolean isEditEnabled;
    private KeyListener keyListener;
    private long clickedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {        //TODO - ability to add new attributes
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        Toolbar infoToolbar = (Toolbar) findViewById(R.id.info_toolbar);
        setSupportActionBar(infoToolbar);

        setupUI(findViewById(R.id.info_parent));

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.info_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //pulling info out of the incoming intent
        Intent intent = getIntent();
        String name = null;
        JSONObject attributes = new JSONObject();
        try {
            //using JSONObject constructor from string to "inflate" object again
            JSONObject json = new JSONObject(intent.getStringExtra("Contact"));
            name = json.getString("Name");
            attributes = json.getJSONObject("Attributes");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //determine whether edittexts should be enabled at start
        isEditEnabled = intent.getBooleanExtra("isEditEnabled", false);

        //creating Contact from the info pulled out of JSONObject, stores initial contact values
        final Contact contact = new Contact(name, attributes);       //TODO - Pull correct contact from db

        //Disabling editing on the Name edittext; this happens here because it's independent of the recyclerview
        final EditText editName = (EditText) findViewById(R.id.info_name);
        editName.setHint(name);
        if (!isEditEnabled) {
            keyListener = editName.getKeyListener();
            editName.setHint(contact.getName());
            editName.setEnabled(false);
            editName.setClickable(false);
            editName.setKeyListener(null);
        }

        //create list of attributes, but all edittexts are disabled
        recyclerView.setAdapter(new InfoAdapter(contact, isEditEnabled));

        //this button first enables the ability to edit fields, and after that submits the changes made to the fields
        Button submitButton = (Button) findViewById(R.id.info_edit_button);
        if (isEditEnabled) {
            submitButton.setText(R.string.info_submit);
        } else {
            submitButton.setText(R.string.info_edit);
        }
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button thisButton = (Button) view;

                //Button clicked for first time; enable editing on name, change button text, and
                //reset adapter on recycerview to generate attributes again but with edittexts enabled
                if (!isEditEnabled) {
                    editName.setEnabled(true);
                    editName.setClickable(true);
                    editName.setKeyListener(keyListener);
                    thisButton.setText(R.string.info_submit);
                    isEditEnabled = true;
                    recyclerView.setAdapter(new InfoAdapter(contact, isEditEnabled));
                    clickedTime = SystemClock.elapsedRealtime();
                }
                //Button clicked second time (debounced); read edittext inputs, decide if any of them have changes,
                //make the changes if needed, and update the correct contact in the mock db
                else if (SystemClock.elapsedRealtime() - clickedTime > 1000){
                    //newContact will hold all the updated values
                    Contact newContact = new Contact();
                    try {
                        //start by copying values from contact to newContact
                        newContact = new Contact(contact.getName(), new JSONObject(contact.getAttributes().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //read the updated value from the Name edittext
                    String nameInput = editName.getText().toString();
                    //if there was no input, nameInput will be "" and we skip this; otherwise,
                    //we update newContact's name to whatever was in the edittext
                    if (!nameInput.isEmpty()) {
                        newContact.setName(nameInput);
                        editName.setHint(nameInput);
                        editName.getText().clear();
                    }

                    //now we do a similar process, but for each attribute
                    int numAttributes = recyclerView.getAdapter().getItemCount();
                    JSONObject newAttributes = newContact.getAttributes();
                    for (int i = 0; i < numAttributes; i++) {
                        InfoAdapter.ViewHolder vHolder = (InfoAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        //read attribute key and value
                        TextView textView = vHolder.txtKey;
                        EditText editText = vHolder.txtValue;
                        //if the value's edittext had some change input into it, write that change
                        //to the same attribute in newContact
                        if (!editText.getText().toString().isEmpty()) {
                            String attrKey = textView.getText().toString();
                            attrKey = attrKey.substring(0, attrKey.length() - 1);
                            String newAttrValue = editText.getText().toString();
                            try {
                                newAttributes.put(attrKey, newAttrValue);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            editText.setHint(newAttrValue);
                            editText.getText().clear();
                        }
                    }
                    newContact.setAttributes(newAttributes);
                    //now we use the original contact values to check which contact we were editing
                    //if we were editing My Info, set myInfoMock to newContact, otherwise find the
                    //contact we were editing inside contactsMock and set that contact to newContact
                    if (myInfoMock.addContactToJSON(new JSONObject()).toString().equals(contact.addContactToJSON(new JSONObject()).toString())) {
                        myInfoMock = newContact;
                    } else {
                        for (Contact c : contactsMock) {
                            if (c.addContactToJSON(new JSONObject()).toString().equals(contact.addContactToJSON(new JSONObject()).toString())) {
                                c.setName(newContact.getName());
                                c.setAttributes(newContact.getAttributes());
                            }
                        }
                    }
                    // Returns to previous activity
                    finish();
                }
            }
        });

        Button deleteButton = (Button) findViewById(R.id.info_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Don't allow user to delete the My Info contact page
                if (myInfoMock.addContactToJSON(new JSONObject()).toString().equals(contact.addContactToJSON(new JSONObject()).toString())) {
                    Toast.makeText(ContactInfoActivity.this, "Your info page is not deletable", Toast.LENGTH_SHORT).show();   //TODO - just remove button for MyInfo screen instead
                } else {
                    //have to find the contact in both the contactsMock and the groupsMock, to make sure
                    //it is removed form the entire app and both lists are synced
                    Contact targetContact = new Contact();
                    ContactGroup targetGroup = new ContactGroup();
                    boolean targetFound = false;
                    boolean groupFound = false;
                    for (Contact c : contactsMock) {
                        //if we find a contact in the contactsMock list which perfectly matches this contact...
                        if (c.addContactToJSON(new JSONObject()).toString().equals(contact.addContactToJSON(new JSONObject()).toString())) {
                            //remember this contact, then hunt for it in the groupsMock
                            targetContact = c;
                            targetFound = true;
                            for (ContactGroup g : groupsMock) {
                                for (Contact groupContact : g.getContacts()) {
                                    //if we find the contact somewhere in the groupsMock...
                                    if (c.addContactToJSON(new JSONObject()).toString().equals(groupContact.addContactToJSON(new JSONObject()).toString())) {
                                        //remember which group it belongs to
                                        targetGroup = g;
                                        groupFound = true;
                                    }
                                }
                            }
                        }
                    }
                    //delete from contactsMock and groupsMock, as appropriate
                    if (targetFound) {
                        contactsMock.remove(contactsMock.indexOf(targetContact));
                        if (groupFound) {
                            ArrayList<Contact> targetGroupContacts = targetGroup.getContacts();
                            targetGroupContacts.remove(targetGroupContacts.indexOf(targetContact));
                        }
                        Toast.makeText(ContactInfoActivity.this, "Contact deleted", Toast.LENGTH_SHORT).show();
                    }
                    //if we couldn't find it in contactsMock...
                    else {
                        Toast.makeText(ContactInfoActivity.this, "This contact doesn't exist!", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });
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
                Intent i = new Intent(ContactInfoActivity.this, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void setupUI(View view) {

        //Make it so that, if we touch a view that isn't edittext, it hides the keyboard
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    v.performClick();
                    Utilities.hideSoftKeyboard(ContactInfoActivity.this);
                    return false;
                }
            });
        }

        //recursive call to get children inside a viewgroup
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}
