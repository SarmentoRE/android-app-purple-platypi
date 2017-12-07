package com.cmsc355.contactapp;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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

        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            Toast.makeText(this, "Sorry this device does not have NFC.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!mAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
        }

        mAdapter.setNdefPushMessageCallback(this, this);

        final String text = "WORKING!!!!!!!!!!?";
        handleIntent(getIntent());
    }

    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }
    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;

        String text = "";
//        String tagId = new String(msgs[0].getRecords()[0].getType());
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the Text Encoding
        int languageCodeLength = payload[0] & 0063; // Get the Language Code, e.g. "en"
        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

        try {
            // Get the Text
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }

        Log.d("TESTNFC","NFC Content: " + text);
    }
    // Handles the incoming Intent
    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) { // If the intent filter discovers an NDEF Message
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) { // If the intent type matches MIME_TEXT_PLAIN

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG); // set tag to NFC extra tag
                new NdefReaderTask().execute(tag); // Runds NdefReaderTask to see if the tag is an NDEF message, Returns null if not the right tag

            } else {
                Log.d(TAG, "Wrong mime type: " + type); //Show in the log the type
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        //Set adapter onResume, so that our list updates every time we come to the screen,
        super.onResume();           //not just the first time
        ArrayMap<String, Object> myInfoAttributes = Utilities.jsonToMap(App.databaseIoManager.getContact(0).getAttributes());
        recyclerView.setAdapter(new ConnectAdapter(myInfoAttributes));
        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) rawMessages[0]; // only one message transferred


            String contactData = new String(message.getRecords()[0].getPayload());
            Log.d("ON RESUME DISCOVERED", contactData);

            Toast.makeText(getApplicationContext(),contactData ,
                    Toast.LENGTH_SHORT).show();
            String[] contactInfo = contactData.split("\\s",2);
            Log.d("ON RESUME", "CONTACT INFO: "+contactInfo.length);
            try {
                Contact contact = new Contact(contactInfo[0], new JSONObject(contactInfo[1].trim()));
                databaseIoManager.putContact(contact);
                Toast.makeText(this,contact.getId() ,
                        Toast.LENGTH_SHORT).show();

            } catch (JSONException exception) {
                exception.printStackTrace();
            }

        } else {
            Log.d("ON RESUME NOT DISC", "Waiting for NDEF Message");
        }
        setupForegroundDispatch(this, nfcAdapter); // Start the foreground dispatch so that the intent can be caught
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
        stopForegroundDispatch(this, nfcAdapter); //stop the task of waiting for the intent
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        /*
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called.
         * In our case this method gets called, when the user attaches a Tag to the device.
         */
        Log.d("onNewIntent","Reached onNewIntent");
        handleIntent(intent);
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
        String message = contact.getName()+" "+contact.getAttributes().toString();
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", message.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }

    private void write(String text, Tag tag) throws IOException, FormatException {
        NdefRecord[] records = {createRecord(text)};
        NdefMessage message = new NdefMessage(records);
        // Get an instance of Ndef for the tag.
        Ndef ndef = Ndef.get(tag);
        // Enable I/O
        ndef.connect();
        // Write the message
        ndef.writeNdefMessage(message);
        // Close the connection
        ndef.close();
    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];

        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);

        return recordNFC;
    }
}