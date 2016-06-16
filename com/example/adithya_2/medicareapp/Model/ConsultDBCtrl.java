package com.example.adithya_2.medicareapp.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class ConsultDBCtrl {
    private static ConsultDBCtrl instance;
    private final String TAG;
    String m_DocID;

    public ConsultDBCtrl() {
        this.TAG = ConsultDBCtrl.class.getSimpleName();
        this.m_DocID = null;
    }

    static {
        instance = null;
    }

    public static ConsultDBCtrl getInstance() {
        if (instance == null) {
            instance = new ConsultDBCtrl();
        }
        return instance;
    }

    public static String createTable() {
        return "CREATE TABLE  IF NOT EXISTS Consulting_Hours(DOC_ID TEXT,APP_ID TEXT,START_TIME TEXT NOT NULL,END_TIME TEXT NOT NULL,DAY CHAR(10) NOT NULL,STATUS CHAR(10) NOT NULL,FOREIGN KEY(DOC_ID) REFERENCES Doctor_Table(DOC_ID));";
    }

    public void insertConsult(ConsultItem consultItem) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DoctorItem.DOCTOR_DOC_ID, consultItem.getConsultItem_DOC_ID());
        values.put(ConsultItem.CONSULT_START_TIME, consultItem.getConsultItem_START_TIME());
        values.put(ConsultItem.CONSULT_END_TIME, consultItem.getConsultItem_END_TIME());
        values.put(ConsultItem.CONSULT_DAY, consultItem.getConsultItem_DAY());
        values.put(ConsultItem.CONSULT_STATUS, consultItem.getConsultItem_STATUS());
        db.insert(ConsultItem.TABLE_CONSULT, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public int upgrateConsultStatusClose(String DocID) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(ConsultItem.CONSULT_STATUS, "Close");
        return db.update(ConsultItem.TABLE_CONSULT, values, "DOC_ID =?", new String[]{DocID});
    }

    public int upgrateConsultStatusOpen(String DocID) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(ConsultItem.CONSULT_STATUS, "Open");
        return db.update(ConsultItem.TABLE_CONSULT, values, "DOC_ID =?", new String[]{DocID});
    }

    public String getAppID(String DocID, String Date) {
        String m_App_ID = null;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT APP_ID FROM Consulting_Hours WHERE Consulting_Hours.DOC_ID='" + DocID + "'" + " AND " + ConsultItem.CONSULT_DAY + " = " + "'" + Date + "'";
        Log.d("Query", selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_APP_ID)) != null) {
                    m_App_ID = cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_APP_ID));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return m_App_ID;
    }

    public String getAppID(String DocID) {
        String m_App_ID = null;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT APP_ID FROM Consulting_Hours WHERE Consulting_Hours.DOC_ID='" + DocID + "'" + " AND " + ConsultItem.CONSULT_DAY;
        Log.d("Query", selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_APP_ID)) != null) {
                    m_App_ID = cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_APP_ID));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return m_App_ID;
    }

    public int updateAppID(String DocID, String date, String AppID) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(MedicineItem.MEDICINE_APP_ID, AppID);
        return db.update(ConsultItem.TABLE_CONSULT, values, "DOC_ID = '" + DocID + "'" + " AND " + ConsultItem.CONSULT_DAY + " = " + "'" + date + "'", null);
    }

    public int updateAppID(String AppID, String ChangeAppID) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(MedicineItem.MEDICINE_APP_ID, ChangeAppID);
        return db.update(ConsultItem.TABLE_CONSULT, values, "APP_ID = '" + AppID + "'", null);
    }

    public DoctorAppItemList getPatTime(String AppID, String DocID) {
        DoctorAppItemList doctorAppItemList = new DoctorAppItemList();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT DAY,END_TIME,START_TIME FROM Consulting_Hours WHERE APP_ID = '" + AppID + "'" + " AND " + DoctorItem.DOCTOR_DOC_ID + " = " + "'" + DocID + "'";
        Log.d("Query", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                doctorAppItemList.setTime1(cursor.getString(cursor.getColumnIndex(ConsultItem.CONSULT_DAY)) + ":" + cursor.getString(cursor.getColumnIndex(ConsultItem.CONSULT_START_TIME)) + ":" + cursor.getString(cursor.getColumnIndex(ConsultItem.CONSULT_END_TIME)));
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return doctorAppItemList;
    }

    public List<String> getDateofDoctor(String DocID) {
        List<String> dates = new ArrayList();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT DAY FROM Consulting_Hours WHERE DOC_ID = '" + DocID + "'";
        Log.d("querydoctor", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String date = new String();
                dates.add(cursor.getString(cursor.getColumnIndex(ConsultItem.CONSULT_DAY)));
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return dates;
    }
}
