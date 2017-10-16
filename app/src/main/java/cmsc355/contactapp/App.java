package cmsc355.contactapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by Austin on 10/16/2017.
 */

public class App extends Application {

    private static Context context;
    private static DatabaseHelper dbHelper;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DatabaseHelper();
        DatabaseManager.initializeInstance(dbHelper);
    }


}
