package cmsc355.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ListView listView = (ListView) findViewById(R.id.home_list);

        SetupListView(listView);
    }

    private void SetupListView(ListView listView) {
        ArrayList<String> buttonNames = new ArrayList<>();
        buttonNames.addAll(Arrays.asList(getResources().getStringArray(R.array.home_button_names)));

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.item_home, R.id.firstLine, buttonNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//              Toast.makeText(getApplicationContext(),
//                      "Click ListItem Number " + position, Toast.LENGTH_SHORT)
//                      .show();
                Intent i;
                switch(position) {
                    case 0:
                        i = new Intent(HomeActivity.this, ConnectActivity.class);
                        startActivity(i);
                        break;
                    case 1:
                        i = new Intent(HomeActivity.this, ContactsActivity.class);
                        startActivity(i);
                        break;
                    case 2:
                        i = new Intent(HomeActivity.this, FavoritesActivity.class);
                        startActivity(i);
                        break;
                    case 3:
                        i = new Intent(HomeActivity.this, GroupsActivity.class);
                        startActivity(i);
                        break;
                    case 4:
                        i = new Intent(HomeActivity.this, ContactInfoActivity.class);
                        i.putExtra("Contact",Contact.GenerateRandomContact().ContactToJSON().toString());       //TODO - retrieve "my info" contact info
                        startActivity(i);
                        break;
                    case 5:
                        i = new Intent(HomeActivity.this, ScanActivity.class);
                        startActivity(i);
                        break;
                    case 6:
                        i = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivity(i);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void insertSampleData() throws JSONException {
        ContactRepo contactRepo = new ContactRepo();
        GroupRepo groupRepo = new GroupRepo();
        RelationRepo relationRepo = new RelationRepo();

        contactRepo.delete();
        groupRepo.delete();
        relationRepo.delete();

        Contact contact = new Contact("Austin", new JSONObject("{\"Name\":\"Austin\"}"));
    }
}
