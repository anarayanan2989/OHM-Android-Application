package com.example.adithya_2.medicareapp.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class PatientDBCtrl {
    private static PatientDBCtrl instance;
    private final String TAG;
    private PatientItem patientItem1;

    public PatientDBCtrl() {
        this.TAG = PatientDBCtrl.class.getSimpleName().toString();
    }

    static {
        instance = null;
    }

    public static PatientDBCtrl getInstance() {
        if (instance == null) {
            instance = new PatientDBCtrl();
        }
        return instance;
    }

    public static String createTable() {
        return "CREATE TABLE Patient_Table(F_NAME CHAR(15) NOT NULL,L_NAME CHAR(15) NOT NULL,DOB TEXT NOT NULL,ADDRESS VARCHAR(30),CITY CHAR(10) NOT NULL,STATE CHAR(2) ,ZIP INTEGER NOT NULL,MOBILE_NO TEXT NOT NULL UNIQUE,SSN INTEGER NOT NULL UNIQUE,EMAIL VARCHAR(50) NOT NULL UNIQUE,P_WORD VARCHAR(12) NOT NULL,ANSWER1 TEXT NOT NULL,ANSWER2 TEXT NOT NULL,PAT_ID INTEGER PRIMARY KEY AUTOINCREMENT);";
    }

    public void insertPatient(PatientItem patientItem) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(PatientItem.PATIENT_F_NAME, patientItem.getPatientItemF_NAME());
        values.put(PatientItem.PATIENT_L_NAME, patientItem.getPatientItemL_NAME());
        values.put(PatientItem.PATIENT_DOB, patientItem.getPatientItemDOB());
        values.put(PatientItem.PATIENT_ADDRESS, patientItem.getPatientItemADDRESS());
        values.put(PatientItem.PATIENT_CITY, patientItem.getPatientItemCITY());
        values.put(PatientItem.PATIENT_STATE, patientItem.getPatientItemSTATE());
        values.put(PatientItem.PATIENT_ZIP, patientItem.getPatientItemZIP());
        values.put(PatientItem.PATIENT_MOBILE_NO, patientItem.getPatientItemMOBILE_NO());
        values.put(PatientItem.PATIENT_SSN, patientItem.getPatientItemSSN());
        values.put(PatientItem.PATIENT_EMAIL, patientItem.getPatientItemEMAIL());
        values.put(PatientItem.PATIENT_P_WORD, patientItem.getPatientItemP_WORD());
        values.put(PatientItem.PATIENT_ANSWER1, patientItem.getPatientItemANSWER1());
        values.put(PatientItem.PATIENT_ANSWER2, patientItem.getPatientItemANSWER2());
        db.insert(PatientItem.TABLE_PATIENT, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public boolean getPatientItemLogin(String email, String pass) {
        int count = 0;
        this.patientItem1 = new PatientItem();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT * FROM Patient_Table WHERE EMAIL='" + email + "'" + " AND " + PatientItem.PATIENT_P_WORD + " = " + "'" + pass + "'";
        Log.d("PatientItem", selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
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

    public String getDocID(String email) {
        String docID = null;
        Cursor cursor = DatabaseManager.getInstance().openDatabase().rawQuery("SELECT DOC_ID FROM Doctor_Table WHERE EMAIL = '" + email + "'", null);
        if (cursor.moveToFirst()) {
            do {
                docID = cursor.getString(cursor.getColumnIndex(DoctorItem.DOCTOR_DOC_ID));
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return docID;
    }

    public DoctorAppItemList getPatInfo(String PatID) {
        DoctorAppItemList doctorAppItemList = new DoctorAppItemList();
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT Patient_Table.PAT_ID, Patient_Table.F_NAME, Patient_Table.L_NAME FROM Patient_Table WHERE PAT_ID = '" + PatID + "'";
        Log.d("QueryAAA", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_PAT_ID)) != null) {
                    doctorAppItemList.setPatID(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_PAT_ID)));
                    doctorAppItemList.setPatientFName(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_F_NAME)));
                    doctorAppItemList.setPatientLName(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_L_NAME)));
                }
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return doctorAppItemList;
    }

    public List<DoctorAppItemList> getAppointmentByDate(String date) {
        List<DoctorAppItemList> doctorAppItemLists = new ArrayList();
        Log.d(MedicineItem.MEDICINE_DATE, date);
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT Patient_Table.PAT_ID, Patient_Table.F_NAME, Patient_Table.L_NAME,DAY,COMMENTS,DOB,START_TIME,END_TIME FROM Patient_Table INNER JOIN Appointment ON Patient_Table.PAT_ID = Appointment.PAT_ID INNER JOIN Consulting_Hours ON Consulting_Hours.APP_ID = Appointment.APP_ID WHERE Consulting_Hours.DAY = '" + date + "'";
        Log.d("Query", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                DoctorAppItemList doctorAppItemList = new DoctorAppItemList();
                doctorAppItemList.setPatID(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_PAT_ID)));
                doctorAppItemList.setPatientFName(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_F_NAME)));
                doctorAppItemList.setPatientLName(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_L_NAME)));
                doctorAppItemList.setPat_DOB(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_DOB)));
                doctorAppItemList.setReason(cursor.getString(cursor.getColumnIndex(AppointmentItem.APP_COMMENTS)));
                doctorAppItemList.setTime1(cursor.getString(cursor.getColumnIndex(ConsultItem.CONSULT_DAY)));
                doctorAppItemList.setStart_Time(cursor.getString(cursor.getColumnIndex(ConsultItem.CONSULT_START_TIME)));
                doctorAppItemList.setEnd_Time(cursor.getString(cursor.getColumnIndex(ConsultItem.CONSULT_END_TIME)));
                doctorAppItemLists.add(doctorAppItemList);
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return doctorAppItemLists;
    }

    public List<DoctorAppItemList> getAppointmentByDocID(String DocID) {
        List<DoctorAppItemList> doctorAppItemLists = new ArrayList();
        Log.d("DocID", DocID);
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT Patient_Table.PAT_ID, Patient_Table.F_NAME, Patient_Table.L_NAME,DAY,COMMENTS,DOB,START_TIME,END_TIME FROM Patient_Table INNER JOIN Appointment ON Patient_Table.PAT_ID = Appointment.PAT_ID INNER JOIN Consulting_Hours ON Consulting_Hours.APP_ID = Appointment.APP_ID WHERE Consulting_Hours.DOC_ID = '" + DocID + "'";
        Log.d("Query", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                DoctorAppItemList doctorAppItemList = new DoctorAppItemList();
                doctorAppItemList.setPatID(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_PAT_ID)));
                doctorAppItemList.setPatientFName(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_F_NAME)));
                doctorAppItemList.setPatientLName(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_L_NAME)));
                doctorAppItemList.setPat_DOB(cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_DOB)));
                doctorAppItemList.setReason(cursor.getString(cursor.getColumnIndex(AppointmentItem.APP_COMMENTS)));
                doctorAppItemList.setTime1(cursor.getString(cursor.getColumnIndex(ConsultItem.CONSULT_DAY)));
                doctorAppItemList.setStart_Time(cursor.getString(cursor.getColumnIndex(ConsultItem.CONSULT_START_TIME)));
                doctorAppItemList.setEnd_Time(cursor.getString(cursor.getColumnIndex(ConsultItem.CONSULT_END_TIME)));
                doctorAppItemLists.add(doctorAppItemList);
            } while (cursor.moveToNext());
            cursor.close();
            DatabaseManager.getInstance().closeDatabase();
        }
        return doctorAppItemLists;
    }

    public int changePass(String email, String pass) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(PatientItem.PATIENT_P_WORD, pass);
        return db.update(PatientItem.TABLE_PATIENT, values, "EMAIL=?", new String[]{email});
    }

    public boolean checkNickAndFood(String nickName, String faveFood, String email) {
        int count = 0;
        Cursor cursor = DatabaseManager.getInstance().openDatabase().rawQuery("SELECT * FROM Patient_Table WHERE EMAIL = '" + email + "'" + " AND " + PatientItem.PATIENT_ANSWER1 + " = " + "'" + nickName + "'" + " AND " + PatientItem.PATIENT_ANSWER2 + " = " + "'" + faveFood + "'", null);
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

    public String getPATID(String email) {
        String m_PatID = null;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT PAT_ID FROM Patient_Table WHERE EMAIL = '" + email + "'";
        Log.d("AAAAAAAAA", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            m_PatID = cursor.getString(cursor.getColumnIndex(PatientItem.PATIENT_PAT_ID));
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return m_PatID;
    }
}
