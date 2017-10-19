package cmsc355.contactapp;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import static cmsc355.contactapp.App.context;
import static cmsc355.contactapp.Contact.myInfoMock;

public class ConnectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        Toolbar connectToolbar = (Toolbar) findViewById(R.id.connect_toolbar);
        setSupportActionBar(connectToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.connect_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        nfcCheck(); // check for NFC connection
    /* Todo see what here isn't working and why
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

    public void nfcCheck() {
        NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE); // This gets the manager object instantiated, and sets it to the NFC service
        NfcAdapter adapter = manager.getDefaultAdapter(); // this instantiates the adapter and sets it to the default

        if (adapter != null && adapter.isEnabled()) {
            /* If adapter is null or adapter is enabled
            *  if adapter is null, then the device does not support NFC, however I felt that it was unnecessary to make it a separate elseif statement
            *  since it is included in the android manifest "uses Feature"
            */
            Toast.makeText(getApplicationContext(), "NFC is on, you're good to go!",
                    Toast.LENGTH_SHORT).show(); //Show toast that NFC is on
        } else {
            Toast.makeText(getApplicationContext(), "NFC must be turned on by User in Settings",
                    Toast.LENGTH_LONG).show(); //Show toast that NFC is off
            startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS)); //open settings page for NFC
        }
    }

    @Override
    protected void onResume() {     //Set adapter onResume, so that our list updates every time we come to the screen,
        super.onResume();           //not just the first time
        ArrayMap<String, Object> myInfoAttributes = Utilities.JSONToMap(myInfoMock.getAttributes());
        recyclerView.setAdapter(new ConnectAdapter(myInfoAttributes));
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
                Intent i = new Intent(ConnectActivity.this, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /* todo figure out how to make the below methods correctly listen for and stop listening for the NDEF tag
    @Override
    protected void onResume() {
        super.onResume();
        nfcAdpt.enableForegroundDispatch(
        this,
        nfcPendingIntent,
        intentFiltersArray,
        null);
        handleIntent(getIntent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        nfcAdpt.disableForegroundDispatch(this);
    }
    */

}
