package cmsc355.contactapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class ConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.connect_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> myInfoAttributes = new ArrayList<>();
        myInfoAttributes.addAll(Arrays.asList("Name", "Phone Number", "Address"));
        recyclerView.setAdapter(new ConnectAdapter(myInfoAttributes));
    }
}
