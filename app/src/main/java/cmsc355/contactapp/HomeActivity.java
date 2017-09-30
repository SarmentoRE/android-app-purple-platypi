package cmsc355.contactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button[] buttons = new Button[6];
        SetButtons(buttons);
    }

    //Assign destination activity to each button
    private void SetButtons(Button[] buttons) {
        buttons[0] = (Button) findViewById(R.id.buttonConnect);
        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ConnectActivity.class);
                startActivity(i);
            }
        });

        buttons[1] = (Button) findViewById(R.id.buttonGroups);
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, GroupsActivity.class);
                startActivity(i);
            }
        });

        buttons[2] = (Button) findViewById(R.id.buttonContacts);
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ContactsActivity.class);
                startActivity(i);
            }
        });

        buttons[3] = (Button) findViewById(R.id.buttonFavorites);
        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, FavoritesActivity.class);
                startActivity(i);
            }
        });

        buttons[4] = (Button) findViewById(R.id.buttonMyInfo);
        buttons[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ContactDetailsActivity.class);
                startActivity(i);
            }
        });

        buttons[5] = (Button) findViewById(R.id.buttonScan);
        buttons[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ScanActivity.class);
                startActivity(i);
            }
        });
    }
}
