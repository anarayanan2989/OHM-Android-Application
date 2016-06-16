package com.example.adithya_2.medicareapp.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDBCtrl {
    private static AppointmentDBCtrl instance;
    private final String TAG;

    public AppointmentDBCtrl() {
        this.TAG = AppointmentDBCtrl.class.getSimpleName();
    }

    static {
        instance = null;
    }

    public static AppointmentDBCtrl getInstance() {
        if (instance == null) {
            instance = new AppointmentDBCtrl();
        }
        return instance;
    }

    public static String createTable() {
        return "CREATE TABLE  IF NOT EXISTS Appointment(APP_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,PAT_ID INTEGER NOT NULL,DOC_ID INTEGER NOT NULL,PRESCRIPTION TEXT,COMMENTS TEXT,STATUS CHAR(10) NOT NULL,FOREIGN KEY(PAT_ID) REFERENCES Patient_Table(PAT_ID)FOREIGN KEY(DOC_ID) REFERENCES Doctor_Table(DOC_ID));";
    }

    public void insertApp(AppointmentItem appointmentItem) {
        SQLiteDatabase sqLiteDatabase = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(PatientItem.PATIENT_PAT_ID, appointmentItem.getApp_PAT_ID());
        values.put(DoctorItem.DOCTOR_DOC_ID, appointmentItem.getApp_DOC_ID());
        values.put(AppointmentItem.APP_PRESCRIPTION, appointmentItem.getApp_PRESCRIPTION());
        values.put(AppointmentItem.APP_COMMENTS, appointmentItem.getApp_COMMENTS());
        values.put(ConsultItem.CONSULT_STATUS, appointmentItem.getApp_STATUS());
        sqLiteDatabase.insert(AppointmentItem.TABLE_APP, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public String getAppID(String patID, String docID) {
        String appId = null;
        Cursor cursor = DatabaseManager.getInstance().openDatabase().rawQuery("SELECT APP_ID FROM Appointment WHERE PAT_ID = '" + patID + "'" + " AND " + DoctorItem.DOCTOR_DOC_ID + " = " + "'" + docID + "'", null);
        if (cursor.moveToFirst()) {
            do {
                appId = cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_APP_ID));
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return appId;
    }

    public List<AppDocNameList> getAppDocName() {
        List<AppDocNameList> appDocNameLists = new ArrayList();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String sqlQuery = "SELECT F_NAME, L_NAME, APP_ID FROM Appointment INNER JOIN Doctor_Table ON Doctor_Table.DOC_ID=Appointment.DOC_ID";
        Log.d("App query", sqlQuery);
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (!cursor.moveToFirst()) {
            return null;
        }
        do {
            AppDocNameList appDocNameList = new AppDocNameList();
            appDocNameList.setAppID(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_APP_ID)));
            appDocNameList.setDocFName(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_F_NAME)));
            appDocNameList.setDocLName(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_L_NAME)));
            appDocNameLists.add(appDocNameList);
        } while (cursor.moveToNext());
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return appDocNameLists;
    }

    public AppointmentItem getAppData(String AppID) {
        AppointmentItem appointmentItem = null;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT * FROM Appointment WHERE APP_ID = '" + AppID + "'";
        Log.d("Query111", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                appointmentItem = new AppointmentItem();
                appointmentItem.setConsultItem_APP_ID(cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_APP_ID)));
                appointmentItem.setConsultItem_COMMENTS(cursor.getString(cursor.getColumnIndex(AppointmentItem.APP_COMMENTS)));
                appointmentItem.setConsultItem_PRESCRIPTION(cursor.getString(cursor.getColumnIndex(AppointmentItem.APP_PRESCRIPTION)));
                appointmentItem.setConsultItem_STATUS(cursor.getString(cursor.getColumnIndex(ConsultItem.CONSULT_STATUS)));
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return appointmentItem;
    }

    public int updateAppStatusExisting(String DocID) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(ConsultItem.CONSULT_STATUS, "Existing");
        return db.update(AppointmentItem.TABLE_APP, values, "DOC_ID =?", new String[]{DocID});
    }

    public int updateAppStatusPrevious(String PatID) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(ConsultItem.CONSULT_STATUS, "Previous");
        return db.update(AppointmentItem.TABLE_APP, values, "APP_ID =?", new String[]{PatID});
    }

    public String getDocId(String PatId) {
        String docID = null;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT DOC_ID FROM Appointment  WHERE PAT_ID =  '" + PatId + "'";
        Log.d("ds", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                docID = cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_PAT_ID));
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return docID;
    }

    public String checkComments(String AppID) {
        String Comments = null;
        Cursor cursor = DatabaseManager.getInstance().openDatabase().rawQuery("SELECT COMMENTS FROM Appointment  WHERE APP_ID = '" + AppID + "'", null);
        if (cursor.moveToFirst()) {
            do {
                Comments = cursor.getString(cursor.getColumnIndex(AppointmentItem.APP_COMMENTS));
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return Comments;
    }

    public String getPAT_ID(String AppID) {
        String m_PatID = null;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        String selectQuery = "SELECT PAT_ID FROM Appointment WHERE APP_ID = '" + AppID + "'";
        Log.d("Query", selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_PAT_ID)) != null) {
                    m_PatID = cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_PAT_ID));
                }
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return m_PatID;
    }

    public DoctorAppItemList getPatComments(String AppID) {
        DoctorAppItemList doctorAppItemList = new DoctorAppItemList();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT COMMENTS FROM Appointment WHERE APP_ID = '" + AppID + "'";
        Log.d("Query", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(AppointmentItem.APP_COMMENTS)) != null) {
                    doctorAppItemList.setReason(cursor.getString(cursor.getColumnIndex(AppointmentItem.APP_COMMENTS)));
                }
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return doctorAppItemList;
    }

    public int updateComments(String AppID, String m_Comments, String m_prescription) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(AppointmentItem.APP_PRESCRIPTION, m_prescription);
        values.put(AppointmentItem.APP_COMMENTS, m_Comments);
        return db.update(AppointmentItem.TABLE_APP, values, "APP_ID=?", new String[]{AppID});
    }

    public String appCheck(String PatID) {
        String AppId = null;
        Cursor cursor = DatabaseManager.getInstance().openDatabase().rawQuery("SELECT APP_ID FROM Appointment Where PAT_ID = '" + PatID + "'", null);
        if (cursor.moveToFirst()) {
            do {
                AppId = cursor.getString(cursor.getColumnIndex(MedicineItem.MEDICINE_APP_ID));
            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return AppId;
    }

    public List<String> getPreviousAPPID(String patID) {
        List<String> prePatIDs = new ArrayList();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT PAT_ID FROM Appointment WHERE PAT_ID = '" + patID + "'";
        Log.d("SQL Query", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String prePatID = new String();
                prePatIDs.add(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_PAT_ID)));
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return prePatIDs;
    }
}
