package com.cmsc355.contactapp;
import android.content.Intent;
import android.os.SystemClock;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.KeyListener;
import android.util.Log;
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

public class ContactInfoActivity extends NonHomeActivity {

    //boolean toggles the ability to edit all the edittexts. keyListener is stored when edittexts are
    //disabled, so they can be reenabled properly later. callingActivity keeps the activity which called
    //this activity, to go back to proper screen
    private boolean isEditEnabled;
    private KeyListener keyListener;
    private long clickedTime;
    private String tag = "ContactInfoActivity";
    private String attributeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {        //TODO - ability to add new attributes
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        Toolbar infoToolbar = (Toolbar) findViewById(R.id.info_toolbar);
        setSupportActionBar(infoToolbar);

        setupUi(findViewById(R.id.info_parent));

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.info_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //pulling info out of the incoming intent
        Intent intent = getIntent();
        final int contactId = intent.getIntExtra("ContactID",-1);
        Log.d(tag,"CONTACT ID IS: " + contactId);

        //determine whether edittexts should be enabled at start
        isEditEnabled = intent.getBooleanExtra("isEditEnabled", false);
        //creating Contact from the info pulled out of JSONObject, stores initial contact values
        final Contact contact = App.databaseIoManager.getContact(contactId);

        //Disabling editing on the Name edittext; this happens here because it's independent of the recyclerview
        final EditText editName = (EditText) findViewById(R.id.info_name);
        editName.setHint(contact.getName());
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
                final Button addAttribute = (Button) findViewById(R.id.add_attribute);

                //Button clicked for first time; enable editing on name, change button text, and
                //reset adapter on recycerview to generate attributes again but with edittexts enabled
                if (!isEditEnabled) {
                    addAttribute.setVisibility(View.VISIBLE);
                    editName.setEnabled(true);
                    editName.setClickable(true);
                    editName.setKeyListener(keyListener);
                    thisButton.setText(R.string.info_submit);
                    isEditEnabled = true;
                    recyclerView.setAdapter(new InfoAdapter(contact, true));
                    clickedTime = SystemClock.elapsedRealtime();
                    addAttribute.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                contact.getAttributes().put("Enter Attribute Name","Enter Attribute Value");
                                recyclerView.setAdapter(new InfoAdapter(contact, isEditEnabled));
                            } catch (JSONException exception) {
                                exception.printStackTrace();
                            }
                        }
                    });
                //Button clicked second time (debounced); read edittext inputs, decide if any of them have changes,
                //make the changes if needed, and update the correct contact in the mock db
                } else if (SystemClock.elapsedRealtime() - clickedTime > 1000) {
                    addAttribute.setVisibility(View.GONE);
                    //newContact will hold all the updated values
                    Contact newContact = new Contact(contact);

                    //read the updated value from the Name edittext
                    String nameInput = editName.getText().toString();
                    //if there was no input, nameInput will be "" and we skip this; otherwise,
                    //we update newContact's name to whatever was in the edittext
                    if (!nameInput.isEmpty()) {
                        newContact.setName(nameInput);
                        //editName.setHint(nameInput);
                        //editName.getText().clear();
                    }

                    //now we do a similar process, but for each attribute
                    int numAttributes = recyclerView.getAdapter().getItemCount();
                    JSONObject newAttributes = new JSONObject();
                    for (int i = 0; i < numAttributes; i++) {
                        InfoAdapter.ViewHolder viewHolder = (InfoAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        //read attribute key and value
                        EditText editKey = viewHolder.editKey;
                        EditText editValue = viewHolder.editValue;
                        //if the value's edittext had some change input into it, write that change
                        //to the same attribute in newContact
                        String keyInput = editKey.getText().toString();
                        String valueInput = editValue.getText().toString();

                        String newKey = editKey.getHint().toString();
                        String newValue = editValue.getHint().toString();
                        if (!keyInput.isEmpty()) {
                            if (keyInput.endsWith(":")) {
                                keyInput = keyInput.substring(0, keyInput.length() - 1);
                            }
                            newKey = keyInput;
                        }
                        if (!valueInput.isEmpty()) {
                            newValue = valueInput;
                        }
                        try {
                            newAttributes.put(newKey, newValue);
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                        }
                    }
                    newContact.setAttributes(newAttributes);

                    //Still has id from original contact, will update regardless of whether changes are made
                    App.databaseIoManager.putContact(newContact);

                    //debouncing this button
                    clickedTime = SystemClock.elapsedRealtime();

                    // Returns to previous activity
                    finish();
                }
            }
        });

        Button deleteButton = (Button) findViewById(R.id.info_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Don't allow user to delete the My Info contact page
                if (contact.getId() == 1) {
                    //TODO - just remove delete button from Contact Info screen instead
                    Toast.makeText(ContactInfoActivity.this, "Your info page is not deletable", Toast.LENGTH_SHORT).show();
                } else {
                    App.databaseIoManager.deleteContact(contact);
                    finish();
                }
            }
        });
    }

    public void setupUi(View view) {

        //Make it so that, if we touch a view that isn't edittext, it hides the keyboard
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent event) {
                    view.performClick();
                    Utilities.hideSoftKeyboard(ContactInfoActivity.this);
                    return false;
                }
            });
        }

        //recursive call to get children inside a viewgroup
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUi(innerView);
            }
        }
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
