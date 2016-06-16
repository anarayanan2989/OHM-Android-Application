package com.example.adithya_2.medicareapp.Model;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.adithya_2.medicareapp.app.App;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HealthCare.db";
    private static final int DATABASE_VERSION = 8;
    public static final String TAB = "DBHelper";

    public DBHelper() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d("Create DB", TAB);
        db.execSQL("DROP TABLE IF EXISTS Patient_Table");
        db.execSQL(PatientDBCtrl.createTable());
        db.execSQL("DROP TABLE IF EXISTS Doctor_Table");
        db.execSQL(DoctorDBCtrl.createTable());
        db.execSQL("DROP TABLE IF EXISTS Consulting_Hours");
        db.execSQL(ConsultDBCtrl.createTable());
        db.execSQL("DROP TABLE IF EXISTS Appointment");
        db.execSQL(AppointmentDBCtrl.createTable());
        db.execSQL("DROP TABLE IF EXISTS Medicine");
        db.execSQL(MedicineDBCtrl.createTable());
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Patient_Table");
        db.execSQL("DROP TABLE IF EXISTS Doctor_Table");
        db.execSQL("DROP TABLE IF EXISTS Consulting_Hours");
        db.execSQL("DROP TABLE IF EXISTS Appointment");
        db.execSQL("DROP TABLE IF EXISTS Medicine");
        onCreate(db);
    }
}
