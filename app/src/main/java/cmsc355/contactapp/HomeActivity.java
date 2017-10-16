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
                switch(position) {
                    case 0:
                        SendToActivity(ConnectActivity.class);
                        break;
                    case 1:
                        SendToActivity(ContactsActivity.class);
                        break;
                    case 2:
                        SendToActivity(FavoritesActivity.class);
                        break;
                    case 3:
                        SendToActivity(GroupsActivity.class);
                        break;
                    case 4:
                        SendToActivity(MyInfoActivity.class);
                        break;
                    case 5:
                        SendToActivity(ScanActivity.class);
                        break;
                    case 6:
                        SendToActivity(SettingsActivity.class);
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

    private void SendToActivity(Class activityClass) {
        Intent i = new Intent(HomeActivity.this, activityClass);
        startActivity(i);
    }
}
