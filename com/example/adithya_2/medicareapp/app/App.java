package com.example.adithya_2.medicareapp.app;

import android.app.Application;
import android.content.Context;
import com.example.adithya_2.medicareapp.Model.DBHelper;
import com.example.adithya_2.medicareapp.Model.DatabaseManager;

public class App extends Application {
    private static Context context;
    private static DBHelper dbHelper;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        dbHelper = new DBHelper();
        DatabaseManager.initializeInstance(dbHelper);
    }

    public static Context getContext() {
        return context;
    }
}
