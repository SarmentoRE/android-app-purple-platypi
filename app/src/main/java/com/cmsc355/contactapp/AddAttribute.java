package com.cmsc355.contactapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Austin on 11/10/2017.
 */

public class AddAttribute extends NonHomeActivity {
    String attributeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final int contactId = intent.getIntExtra("ContactID",-1);
        final Contact contact = App.databaseIoManager.getContact(contactId);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attribute Name");
        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                attributeName = input.getText().toString();

                JSONObject attributes = contact.getAttributes();
                try {
                    attributes.put(attributeName," ");
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
                App.databaseIoManager.putContact(contact);
                Intent intent1 = new Intent(AddAttribute.this,ContactInfoActivity.class);
                intent1.putExtra("ContactID",contact.getId());
                startActivity(intent1);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent1 = new Intent(AddAttribute.this,ContactInfoActivity.class);
                intent1.putExtra("ContactID",contact.getId());
                startActivity(intent1);
            }
        });
        builder.show();
    }
}
