package cmsc355.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import static cmsc355.contactapp.ContactGroup.groupsMock;

public class GroupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        Toolbar groupsToolbar = (Toolbar) findViewById(R.id.group_toolbar);
        setSupportActionBar(groupsToolbar);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.groups_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<ArrayList<Contact>> contactsLists = new ArrayList<>();

        groupsMock = Utilities.SortGroupList(groupsMock);
        for (ContactGroup group : groupsMock) {
            group.setContacts(Utilities.SortContactList(group.getContacts()));
            contactsLists.add(group.getContacts());
        }

        recyclerView.setAdapter(new GroupsAdapter(groupsMock, contactsLists));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent i = new Intent(GroupsActivity.this, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
