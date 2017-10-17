package cmsc355.contactapp;

import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import static cmsc355.contactapp.Contact.contactsMock;

public class ConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.connect_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayMap<String, Object> myInfoAttributes = Utilities.JSONToMap(contactsMock.get(0).getAttributes());
        recyclerView.setAdapter(new ConnectAdapter(myInfoAttributes));
    }
}
