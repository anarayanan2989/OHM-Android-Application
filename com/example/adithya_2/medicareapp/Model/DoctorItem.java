package com.example.adithya_2.medicareapp.Model;

public class DoctorItem {
    public static final String DOCTOR_ANSWER1 = "ANSWER1";
    public static final String DOCTOR_ANSWER2 = "ANSWER2";
    public static final String DOCTOR_DOB = "DOB";
    public static final String DOCTOR_DOC_ID = "DOC_ID";
    public static final String DOCTOR_EMAIL = "EMAIL";
    public static final String DOCTOR_F_NAME = "F_NAME";
    public static final String DOCTOR_HOSP_CITY = "HOSP_CITY";
    public static final String DOCTOR_HOSP_NAME = "HOSP_NAME";
    public static final String DOCTOR_L_NAME = "L_NAME";
    public static final String DOCTOR_MOBILE_NO = "MOBILE_NO";
    public static final String DOCTOR_P_WORD = "P_WORD";
    public static final String DOCTOR_RESUME = "RESUME";
    public static final String DOCTOR_RES_ADDRESS = "RES_ADDRESS";
    public static final String DOCTOR_SPECIALIZATION = "SPECIALIZATION";
    public static final String TABLE_DOCTOR = "Doctor_Table";
    public static final String TAG;
    private String ANSWER1;
    private String ANSWER2;
    private String DOB;
    private String DOC_ID;
    private String EMAIL;
    private String F_NAME;
    private String HOSP_CITY;
    private String HOSP_NAME;
    private String L_NAME;
    private String MOBILE_NO;
    private String P_WORD;
    private String RESUME;
    private String RES_ADDRESS;
    private String SPECIALIZATION;

    static {
        TAG = PatientItem.class.getSimpleName();
    }

    public String getDoctorItemF_NAME() {
        return this.F_NAME;
    }

    public void setDoctorItemF_NAME(String F_NAME) {
        this.F_NAME = F_NAME;
    }

    public String getDoctorItemL_NAME() {
        return this.L_NAME;
    }

    public void setDoctorItemL_NAME(String L_NAME) {
        this.L_NAME = L_NAME;
    }

    public String getDoctorItemDOB() {
        return this.DOB;
    }

    public void setDoctorItemDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getDoctorItemRES_ADDRESS() {
        return this.RES_ADDRESS;
    }

    public void setDoctorItemRES_ADDRESS(String RES_ADDRESS) {
        this.RES_ADDRESS = RES_ADDRESS;
    }

    public String getDoctorItemMOBILE_NO() {
        return this.MOBILE_NO;
    }

    public void setDoctorItemMOBILE_NO(String MOBILE_NO) {
        this.MOBILE_NO = MOBILE_NO;
    }

    public String getDoctorItemSPECIALIZATION() {
        return this.SPECIALIZATION;
    }

    public void setDoctorItemSPECIALIZATION(String SPECIALIZATION) {
        this.SPECIALIZATION = SPECIALIZATION;
    }

    public String getDoctorItemRESUME() {
        return this.RESUME;
    }

    public void setDoctorItemRESUME(String RESUME) {
        this.RESUME = RESUME;
    }

    public String getDoctorItemHOSP_NAME() {
        return this.HOSP_NAME;
    }

    public void setDoctorItemHOSP_NAME(String HOSP_NAME) {
        this.HOSP_NAME = HOSP_NAME;
    }

    public String getDoctorItemHOSP_CITY() {
        return this.HOSP_CITY;
    }

    public void setDoctorItemHOSP_CITY(String HOSP_CITY) {
        this.HOSP_CITY = HOSP_CITY;
    }

    public String getDoctorItemEMAIL() {
        return this.EMAIL;
    }

    public void setDoctorItemEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getDoctorItemP_WORD() {
        return this.P_WORD;
    }

    public void setDoctorItemP_WORD(String P_WORD) {
        this.P_WORD = P_WORD;
    }

    public String getDoctorItemANSWER1() {
        return this.ANSWER1;
    }

    public void setDoctorItemANSWER1(String ANSWER1) {
        this.ANSWER1 = ANSWER1;
    }

    public String getDoctorItemANSWER2() {
        return this.ANSWER2;
    }

    public void setDoctorItemANSWER2(String ANSWER2) {
        this.ANSWER2 = ANSWER2;
    }

    public String getDoctorItemDOC_ID() {
        return this.DOC_ID;
    }

    public void setDoctorItemDOC_ID(String DOC_ID) {
        this.DOC_ID = DOC_ID;
    }
}
