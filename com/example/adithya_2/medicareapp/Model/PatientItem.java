package com.example.adithya_2.medicareapp.Model;

public class PatientItem {
    public static final String PATIENT_ADDRESS = "ADDRESS";
    public static final String PATIENT_ANSWER1 = "ANSWER1";
    public static final String PATIENT_ANSWER2 = "ANSWER2";
    public static final String PATIENT_CITY = "CITY";
    public static final String PATIENT_DOB = "DOB";
    public static final String PATIENT_EMAIL = "EMAIL";
    public static final String PATIENT_F_NAME = "F_NAME";
    public static final String PATIENT_L_NAME = "L_NAME";
    public static final String PATIENT_MOBILE_NO = "MOBILE_NO";
    public static final String PATIENT_PAT_ID = "PAT_ID";
    public static final String PATIENT_P_WORD = "P_WORD";
    public static final String PATIENT_SSN = "SSN";
    public static final String PATIENT_STATE = "STATE";
    public static final String PATIENT_ZIP = "ZIP";
    public static final String TABLE_PATIENT = "Patient_Table";
    public static final String TAG;
    private String ADDRESS;
    private String ANSWER1;
    private String ANSWER2;
    private String CITY;
    private String DOB;
    private String EMAIL;
    private String F_NAME;
    private String L_NAME;
    private String MOBILE_NO;
    private String PAT_ID;
    private String P_WORD;
    private String SSN;
    private String STATE;
    private String ZIP;

    static {
        TAG = PatientItem.class.getSimpleName();
    }

    public String getPatientItemF_NAME() {
        return this.F_NAME;
    }

    public void setPatientItemF_NAME(String F_NAME) {
        this.F_NAME = F_NAME;
    }

    public String getPatientItemL_NAME() {
        return this.L_NAME;
    }

    public void setPatientItemL_NAME(String L_NAME) {
        this.L_NAME = L_NAME;
    }

    public String getPatientItemDOB() {
        return this.DOB;
    }

    public void setPatientItemDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPatientItemADDRESS() {
        return this.ADDRESS;
    }

    public void setPatientItemADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getPatientItemCITY() {
        return this.CITY;
    }

    public void setPatientItemCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getPatientItemSTATE() {
        return this.STATE;
    }

    public void setPatientItemSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getPatientItemZIP() {
        return this.ZIP;
    }

    public void setPatientItemZIP(String ZIP) {
        this.ZIP = ZIP;
    }

    public String getPatientItemMOBILE_NO() {
        return this.MOBILE_NO;
    }

    public void setPatientItemMOBILE_NO(String MOBILE_NO) {
        this.MOBILE_NO = MOBILE_NO;
    }

    public String getPatientItemSSN() {
        return this.SSN;
    }

    public void setPatientItemSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getPatientItemEMAIL() {
        return this.EMAIL;
    }

    public void setPatientItemEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPatientItemP_WORD() {
        return this.P_WORD;
    }

    public void setPatientItemP_WORD(String P_WORD) {
        this.P_WORD = P_WORD;
    }

    public String getPatientItemANSWER1() {
        return this.ANSWER1;
    }

    public void setPatientItemANSWER1(String ANSWER1) {
        this.ANSWER1 = ANSWER1;
    }

    public String getPatientItemANSWER2() {
        return this.ANSWER2;
    }

    public void setPatientItemANSWER2(String ANSWER2) {
        this.ANSWER2 = ANSWER2;
    }

    public String getPatientItemPAT_ID() {
        return this.PAT_ID;
    }

    public void setPatientItemPAT_ID(String PAT_ID) {
        this.PAT_ID = PAT_ID;
    }
}
