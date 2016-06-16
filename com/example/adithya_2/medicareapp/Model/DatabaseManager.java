package com.example.adithya_2.medicareapp.Model;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager {
    private static DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;
    private Integer mOpenCounter;

    public DatabaseManager() {
        this.mOpenCounter = Integer.valueOf(0);
    }

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        synchronized (DatabaseManager.class) {
            if (instance == null) {
                instance = new DatabaseManager();
                mDatabaseHelper = helper;
            }
        }
    }

    public static synchronized DatabaseManager getInstance() {
        DatabaseManager databaseManager;
        synchronized (DatabaseManager.class) {
            if (instance == null) {
                throw new IllegalStateException(DatabaseManager.class.getSimpleName() + " is not initialized, call initializeInstance(..) method first.");
            }
            databaseManager = instance;
        }
        return databaseManager;
    }

    public synchronized SQLiteDatabase openDatabase() {
        this.mOpenCounter = Integer.valueOf(this.mOpenCounter.intValue() + 1);
        if (this.mOpenCounter.intValue() == 1) {
            this.mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return this.mDatabase;
    }

    public synchronized void closeDatabase() {
        this.mOpenCounter = Integer.valueOf(this.mOpenCounter.intValue() - 1);
        if (this.mOpenCounter.intValue() == 0) {
            this.mDatabase.close();
        }
    }
}
