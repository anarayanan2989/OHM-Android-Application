package com.example.adithya_2.medicareapp.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class DoctorDBCtrl {
    private static DoctorDBCtrl instance;
    private final String TAG;

    public DoctorDBCtrl() {
        this.TAG = DoctorDBCtrl.class.getSimpleName();
    }

    static {
        instance = null;
    }

    public static DoctorDBCtrl getInstance() {
        if (instance == null) {
            instance = new DoctorDBCtrl();
        }
        return instance;
    }

    public static String createTable() {
        return "CREATE TABLE  IF NOT EXISTS Doctor_Table(F_NAME CHAR(15) NOT NULL,L_NAME CHAR(15) NOT NULL,DOB TEXT NOT NULL,RES_ADDRESS VARCHAR(40),MOBILE_NO TEXT NOT NULL,SPECIALIZATION CHAR(15) NOT NULL,RESUME TEXT NOT NULL,HOSP_NAME TEXT NOT NULL,HOSP_CITY CHAR(8) NOT NULL,EMAIL VARCHAR(50) NOT NULL,P_WORD VARCHAR(12) NOT NULL,ANSWER1 TEXT NOT NULL,ANSWER2 TEXT NOT NULL,DOC_ID INTEGER PRIMARY KEY AUTOINCREMENT);";
    }

    public void insertDoctor(DoctorItem doctorItem) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(PatientItem.PATIENT_F_NAME, doctorItem.getDoctorItemF_NAME());
        values.put(PatientItem.PATIENT_L_NAME, doctorItem.getDoctorItemL_NAME());
        values.put(PatientItem.PATIENT_DOB, doctorItem.getDoctorItemDOB());
        values.put(DoctorItem.DOCTOR_RES_ADDRESS, doctorItem.getDoctorItemRES_ADDRESS());
        values.put(PatientItem.PATIENT_MOBILE_NO, doctorItem.getDoctorItemMOBILE_NO());
        values.put(DoctorItem.DOCTOR_SPECIALIZATION, doctorItem.getDoctorItemSPECIALIZATION());
        values.put(DoctorItem.DOCTOR_RESUME, doctorItem.getDoctorItemRESUME());
        values.put(DoctorItem.DOCTOR_HOSP_NAME, doctorItem.getDoctorItemHOSP_NAME());
        values.put(DoctorItem.DOCTOR_HOSP_CITY, doctorItem.getDoctorItemHOSP_CITY());
        values.put(PatientItem.PATIENT_EMAIL, doctorItem.getDoctorItemEMAIL());
        values.put(PatientItem.PATIENT_P_WORD, doctorItem.getDoctorItemP_WORD());
        values.put(PatientItem.PATIENT_ANSWER1, doctorItem.getDoctorItemANSWER1());
        values.put(PatientItem.PATIENT_ANSWER2, doctorItem.getDoctorItemANSWER2());
        db.insert(DoctorItem.TABLE_DOCTOR, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public List<SearchResultList> getDoctorName(String spec, String city, String date) {
        List<SearchResultList> searchResultLists = new ArrayList();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selNameQuery = " SELECT Doctor_Table.DOC_ID , F_NAME , L_NAME , HOSP_CITY , HOSP_NAME , SPECIALIZATION , MOBILE_NO , START_TIME , END_TIME FROM Doctor_Table INNER JOIN Consulting_Hours ON Consulting_Hours.DOC_ID = Doctor_Table.DOC_ID WHERE HOSP_CITY='" + city + "'" + " AND Doctor_Table." + DoctorItem.DOCTOR_SPECIALIZATION + " = " + "'" + spec + "'" + " AND  Consulting_Hours." + ConsultItem.CONSULT_DAY + " = " + "'" + date + "'" + " AND Consulting_Hours." + ConsultItem.CONSULT_STATUS + " = " + "'" + "Open" + "'";
        Log.d(this.TAG, selNameQuery);
        Cursor cursor = db.rawQuery(selNameQuery, null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        do {
            SearchResultList searchResultList = new SearchResultList();
            searchResultList.setDoc_ID(cursor.getString(cursor.getColumnIndex(DoctorItem.DOCTOR_DOC_ID)));
            searchResultList.setF_Name(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_F_NAME)));
            searchResultList.setL_Name(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_L_NAME)));
            searchResultList.setHOSP_City(cursor.getString(cursor.getColumnIndex(DoctorItem.DOCTOR_HOSP_CITY)));
            searchResultList.setHOSP_Name(cursor.getString(cursor.getColumnIndex(DoctorItem.DOCTOR_HOSP_NAME)));
            searchResultList.setSpec(cursor.getString(cursor.getColumnIndex(DoctorItem.DOCTOR_SPECIALIZATION)));
            searchResultList.setMobile_No(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_MOBILE_NO)));
            searchResultList.setStart_Time(cursor.getString(cursor.getColumnIndex(ConsultItem.CONSULT_START_TIME)));
            searchResultList.setEnd_Time(cursor.getString(cursor.getColumnIndex(ConsultItem.CONSULT_END_TIME)));
            searchResultLists.add(searchResultList);
        } while (cursor.moveToNext());
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return searchResultLists;
    }

    public boolean isNotValue() {
        int count = 0;
        Cursor cursor = DatabaseManager.getInstance().openDatabase().rawQuery("SELECT DOC_ID FROM Doctor_Table", null);
        if (!(cursor == null || cursor.isClosed())) {
            count = cursor.getCount();
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        if (count > 0) {
            return false;
        }
        return true;
    }

    public boolean isDoctor(String email, String pass) {
        int count = 0;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String sqlQuery = "SELECT * FROM Doctor_Table Where P_WORD='" + pass + "'" + " AND " + PatientItem.PATIENT_EMAIL + "=" + "'" + email + "'";
        Log.d("TAG1", sqlQuery);
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

    public boolean updateDocInfo(String Add, String Mobile, String Loc, String Hosp, String email) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DoctorItem.DOCTOR_RES_ADDRESS, Add);
        values.put(PatientItem.PATIENT_MOBILE_NO, Mobile);
        values.put(DoctorItem.DOCTOR_HOSP_CITY, Loc);
        values.put(DoctorItem.DOCTOR_HOSP_NAME, Hosp);
        if (db.update(DoctorItem.TABLE_DOCTOR, values, "EMAIL=?", new String[]{email.trim()}) > 0) {
            return true;
        }
        return false;
    }

    public DoctorItem getUpdateInfo(String email) {
        DoctorItem doctorItem = new DoctorItem();
        Cursor cursor = DatabaseManager.getInstance().openDatabase().rawQuery("SELECT RES_ADDRESS, MOBILE_NO, HOSP_CITY, HOSP_NAME FROM Doctor_Table WHERE EMAIL = '" + email + "'", null);
        if (cursor.moveToFirst()) {
            do {
                doctorItem = new DoctorItem();
                doctorItem.setDoctorItemRES_ADDRESS(cursor.getString(cursor.getColumnIndex(DoctorItem.DOCTOR_RES_ADDRESS)));
                doctorItem.setDoctorItemMOBILE_NO(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_MOBILE_NO)));
                doctorItem.setDoctorItemHOSP_CITY(cursor.getString(cursor.getColumnIndex(DoctorItem.DOCTOR_HOSP_CITY)));
                doctorItem.setDoctorItemHOSP_NAME(cursor.getString(cursor.getColumnIndex(DoctorItem.DOCTOR_HOSP_NAME)));
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return doctorItem;
    }

    public int changePassDoctor(String email, String pass) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(PatientItem.PATIENT_P_WORD, pass);
        return db.update(DoctorItem.TABLE_DOCTOR, values, "EMAIL=?", new String[]{email});
    }
}
