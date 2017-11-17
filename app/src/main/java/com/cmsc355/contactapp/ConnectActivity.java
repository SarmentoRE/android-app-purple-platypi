package com.cmsc355.contactapp;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static com.cmsc355.contactapp.App.context;
import static com.cmsc355.contactapp.ConnectActivity.TAG;

public class ConnectActivity extends NonHomeActivity {

    private RecyclerView recyclerView;
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    private TextView textView1;
    private NfcAdapter nfcAdapter;

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

        handleIntent(getIntent());

        /*
        Intent nfcIntent = new Intent(this, getClass()); // Instantiate the intent to NFC connect
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // add flag, pops the activity to the forefront

        nfcPendingIntent =
        PendingIntent.getActivity(this, 0, nfcIntent, 0); // sets a pending intent

        IntentFilter tagIntentFilter =
        new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED); //prepares the app to accept the NFC NDEF when its discovered
        try {
            tagIntentFilter.addDataType("text/plain");
            intentFiltersArray = new IntentFilter[]{tagIntentFilter};
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        */
    }

    // Handles the incoming Intent
    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) { // If the intent filter discovers an NDEF Message

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

    public void sendFile(View view) {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this); //Get the NFC adapter

        // Check whether NFC is enabled on device
        if (!nfcAdapter.isEnabled()) {
            // NFC is disabled, show the settings UI to enable NFC
            Toast.makeText(this, "Please enable NFC.",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
            // Check whether Android Beam feature is enabled on device
        } else if (!nfcAdapter.isNdefPushEnabled()) {
            // Android Beam is disabled, show the settings UI to enable Android Beam
            Toast.makeText(this, "Please enable Android Beam.",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFCSHARING_SETTINGS));
        } else {
            // NFC and Android Beam both are enabled

            nfcAdapter.setNdefPushMessageCallback(new NfcAdapter.CreateNdefMessageCallback() {
                //Creates an NdefMessage object to send to the other device, and waits for the device to appear
                @Override
                public NdefMessage createNdefMessage(NfcEvent event) {
                    NdefRecord uriRecord = NdefRecord.createUri(Uri.encode("http://www.google.com/"));
                    return new NdefMessage(new NdefRecord[] { uriRecord });
                }

            }, this, this);
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
        handleIntent(intent);
    }

    /**
     * @param activity The corresponding {@link Activity} requesting the foreground dispatch.
     * @param adapter  The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
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
        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    /**
     * @param activity The corresponding {@link HomeActivity} requesting to stop the foreground dispatch.
     * @param adapter  The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }
}
