package com.cmsc355.contactapp;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static com.cmsc355.contactapp.App.context;
import static com.cmsc355.contactapp.App.databaseIoManager;

public class ConnectActivity extends NonHomeActivity implements NfcAdapter.CreateNdefMessageCallback {

    private RecyclerView recyclerView;
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    private TextView textView1;
    private NfcAdapter nfcAdapter;
    private Tag tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        Toolbar connectToolbar = findViewById(R.id.connect_toolbar);
        setSupportActionBar(connectToolbar);

        recyclerView = findViewById(R.id.connect_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcCheck(); // check for NFC settings

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "Sorry this device does not have NFC.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
        }

        nfcAdapter.setNdefPushMessageCallback(this, this);
    }

    @Override
    protected void onResume() {
        Log.d("On resume called"," Yeah");
        //Set adapter onResume, so that our list updates every time we come to the screen,
        super.onResume();           //not just the first time
        ArrayMap<String, Object> myInfoAttributes = Utilities.jsonToMap(App.databaseIoManager.getContact(0).getAttributes());
        recyclerView.setAdapter(new ConnectAdapter(myInfoAttributes));
        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) { //if ndef message is discovered, begin trying to figure out if it fits our app
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) rawMessages[0]; // only one message transferred

            String contactData = new String(message.getRecords()[0].getPayload()); // Get NDEF record to parse it into the different parts
            Log.d("ON RESUME DISCOVERED", contactData);

            Toast.makeText(getApplicationContext(),contactData ,
                    Toast.LENGTH_SHORT).show();
            String[] contactInfo = contactData.split("\\s",2); // parse the contact data into specific attributes
            Log.d("ON RESUME", "CONTACT INFO: " + contactInfo[1]);
            try {
                Contact contact = new Contact(contactInfo[0], new JSONObject(contactInfo[1].trim())); // create contact object
                databaseIoManager.putContact(contact); // put the new contact into the database
            } catch (JSONException exception) {
                exception.printStackTrace();
            }

        } else {
            Log.d("ON RESUME NOT DISC", "Waiting for NDEF Message");
        }
    }

    //adds the home button to the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    //home button takes you straight home, resets the list of activities for the back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void nfcCheck() {
        // This gets the manager object instantiated, and sets it to the NFC service
        NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        // This instantiates the adapter and sets it to the default
        if (manager != null) {
            NfcAdapter adapter = manager.getDefaultAdapter();
            if (adapter != null && adapter.isEnabled()) {
                /* If adapter is null or adapter is enabled
                *  if adapter is null, then the device does not support NFC,
                *  however I felt that it was unnecessary to make it a separate elseif statement
                *  since it is included in the android manifest "uses Feature"
                */
                Toast.makeText(getApplicationContext(), "NFC is on, you're good to go!",
                        Toast.LENGTH_SHORT).show(); //Show toast that NFC is on
            } else {
                Toast.makeText(getApplicationContext(), "NFC must be turned on by User in Settings",
                        Toast.LENGTH_LONG).show(); //Show toast that NFC is off
                startActivityForResult(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS), 23); //open settings page for NFC
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    /**
     * @param activity The corresponding {@link Activity} requesting the foreground dispatch.
     * @param adapter  The {@link NfcAdapter} used for the foreground dispatch.
     */
    public void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];

        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException exception) {
            throw new RuntimeException("Check your mime type.");
        }

        String[][] techList = new String[][]{};
        adapter.enableForegroundDispatch(this, pendingIntent, filters, techList);
    }

    /**
     * @param activity The corresponding {@link HomeActivity} requesting to stop the foreground dispatch.
     * @param adapter  The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        Contact contact = databaseIoManager.getContact(1);
        //contact string here
        String message = contact.getName() + " " + contact.getAttributes().toString();
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", message.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }
}