package com.example.adithya_2.medicareapp.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MedicineDBCtrl {
    private final String TAG;

    public MedicineDBCtrl() {
        this.TAG = MedicineDBCtrl.class.getSimpleName();
    }

    public static String createTable() {
        return "CREATE TABLE  IF NOT EXISTS Medicine(Medicine_ID INTEGER PRIMARY KEY AUTOINCREMENT, APP_ID INTEGER, Medicine_Name TEXT, Break_before TEXT, Break_after TEXT, Lunch_before TEXT, Lunch_after TEXT, Dinner_before TEXT, Dinner_after TEXT, Blood_test TEXT, Time TEXT, Date TEXT, XRay TEXT, Sugar TEXT, BPLevel TEXT, Pat_Weight TEXT) ";
    }

    public MedicineItem getMedicineInfo(String App_ID) {
        MedicineItem medicineItem = new MedicineItem();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String medicineQuery = "SELECT * FROM Medicine WHERE APP_ID = '" + App_ID + "'";
        Log.d("medicine Query", medicineQuery);
        Cursor cursor = db.rawQuery(medicineQuery, null);
        if (cursor.moveToFirst()) {
            do {
                medicineItem = new MedicineItem();
                medicineItem.setApp_ID(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_APP_ID)));
                medicineItem.setPAT_ID(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_APP_ID)));
                medicineItem.setMedicineName(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_NAME)));
                medicineItem.setMedicineBreakBefore(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_BREAK_BEFORE)));
                medicineItem.setMedicineBreakAfter(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_BREAK_AFTER)));
                medicineItem.setMedicineLunchBefore(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_LUNCH_BEFORE)));
                medicineItem.setMedicineLunchAfter(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_LUNCH_AFTER)));
                medicineItem.setMedicineDinnerBefore(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_DINNER_BEFORE)));
                medicineItem.setMedicineDinnerAfter(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_DINNER_AFTER)));
                medicineItem.setMedicineBloodTest(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_BLOOD_TEST)));
                medicineItem.setTime(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_TIME)));
                medicineItem.setDate(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_DATE)));
                medicineItem.setXRay(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_XRAY)));
                medicineItem.setPatSugar(cursor.getString(cursor.getColumnIndex(MedicineItem.PAT_SUGAR)));
                medicineItem.setPatBplevel(cursor.getString(cursor.getColumnIndex(MedicineItem.PAT_BPLEVEL)));
                medicineItem.setPatWeight(cursor.getString(cursor.getColumnIndex(MedicineItem.PAT_WEIGHT)));
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return medicineItem;
    }

    public void insetMedicineData(MedicineItem medicineItem) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(MedicineItem.MEDICINE_APP_ID, medicineItem.getApp_ID());
        values.put(MedicineItem.MEDICINE_NAME, medicineItem.getMedicineName());
        values.put(MedicineItem.MEDICINE_BREAK_BEFORE, medicineItem.getMedicineBreakBefore());
        values.put(MedicineItem.MEDICINE_BREAK_AFTER, medicineItem.getMedicineBreakAfter());
        values.put(MedicineItem.MEDICINE_LUNCH_BEFORE, medicineItem.getMedicineLunchBefore());
        values.put(MedicineItem.MEDICINE_LUNCH_AFTER, medicineItem.getMedicineBreakAfter());
        values.put(MedicineItem.MEDICINE_DINNER_BEFORE, medicineItem.getMedicineDinnerBefore());
        values.put(MedicineItem.MEDICINE_DINNER_AFTER, medicineItem.getMedicineDinnerAfter());
        values.put(MedicineItem.MEDICINE_BLOOD_TEST, medicineItem.getMedicineBloodTest());
        values.put(MedicineItem.MEDICINE_TIME, medicineItem.getTime());
        values.put(MedicineItem.MEDICINE_DATE, medicineItem.getDate());
        values.put(MedicineItem.MEDICINE_XRAY, medicineItem.getXRay());
        values.put(MedicineItem.PAT_SUGAR, medicineItem.getPatSugar());
        values.put(MedicineItem.PAT_BPLEVEL, medicineItem.getPatBplevel());
        values.put(MedicineItem.PAT_WEIGHT, medicineItem.getPatWeight());
        db.insert(MedicineItem.TABLE_MEDICINE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public int updateMedicine(MedicineItem medicineItem) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(MedicineItem.MEDICINE_APP_ID, medicineItem.getApp_ID());
        values.put(MedicineItem.MEDICINE_BREAK_BEFORE, medicineItem.getMedicineBreakBefore());
        values.put(MedicineItem.MEDICINE_BREAK_AFTER, medicineItem.getMedicineBreakAfter());
        values.put(MedicineItem.MEDICINE_LUNCH_BEFORE, medicineItem.getMedicineLunchBefore());
        values.put(MedicineItem.MEDICINE_LUNCH_AFTER, medicineItem.getMedicineBreakAfter());
        values.put(MedicineItem.MEDICINE_DINNER_BEFORE, medicineItem.getMedicineDinnerBefore());
        values.put(MedicineItem.MEDICINE_DINNER_AFTER, medicineItem.getMedicineDinnerAfter());
        values.put(MedicineItem.MEDICINE_BLOOD_TEST, medicineItem.getMedicineBloodTest());
        values.put(MedicineItem.MEDICINE_TIME, medicineItem.getTime());
        values.put(MedicineItem.MEDICINE_DATE, medicineItem.getDate());
        values.put(MedicineItem.MEDICINE_XRAY, medicineItem.getXRay());
        values.put(MedicineItem.PAT_SUGAR, medicineItem.getPatSugar());
        values.put(MedicineItem.PAT_BPLEVEL, medicineItem.getPatBplevel());
        values.put(MedicineItem.PAT_WEIGHT, medicineItem.getPatWeight());
        return db.update(MedicineItem.TABLE_MEDICINE, values, "APP_ID = ?", new String[]{medicineItem.getApp_ID()});
    }

    public boolean isExistValue(String AppID) {
        int count = 0;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String sqlQuery = "SELECT * FROM Medicine WHERE APP_ID='" + AppID + "'";
        Log.d("isValue", sqlQuery);
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (!(cursor == null || cursor.isClosed())) {
            count = cursor.getCount();
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        if (count > 0) {
            return true;
        }
        return false;
    }
}
