package com.example.adithya_2.medicareapp.Model;

public class AppointmentItem {
    public static final String APP_APP_ID = "APP_ID";
    public static final String APP_COMMENTS = "COMMENTS";
    public static final String APP_DOC_ID = "DOC_ID";
    public static final String APP_PAT_DOB = "DOB";
    public static final String APP_PAT_ID = "PAT_ID";
    public static final String APP_PRESCRIPTION = "PRESCRIPTION";
    public static final String APP_STATUS = "STATUS";
    public static final String TABLE_APP = "Appointment";
    public static final String TAG;
    private String APP_ID;
    private String COMMENTS;
    private String DOC_ID;
    private String PAT_DOB;
    private String PAT_ID;
    private String PRESCRIPTION;
    private String STATUS;

    static {
        TAG = AppointmentItem.class.getSimpleName();
    }

    public String getApp_App_ID() {
        return this.APP_ID;
    }

    public void setConsultItem_APP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getApp_PAT_ID() {
        return this.PAT_ID;
    }

    public void setConsultItem_PAT_ID(String PAT_ID) {
        this.PAT_ID = PAT_ID;
    }

    public String getApp_PAT_DOB() {
        return this.PAT_DOB;
    }

    public void setApp_PAT_DOB(String PAT_DOB) {
        this.PAT_DOB = PAT_DOB;
    }

    public String getApp_PRESCRIPTION() {
        return this.PRESCRIPTION;
    }

    public void setConsultItem_PRESCRIPTION(String PRESCRIPTION) {
        this.PRESCRIPTION = PRESCRIPTION;
    }

    public String getApp_DOC_ID() {
        return this.DOC_ID;
    }

    public void setConsultItem_DOC_ID(String DOC_ID) {
        this.DOC_ID = DOC_ID;
    }

    public String getApp_COMMENTS() {
        return this.COMMENTS;
    }

    public void setConsultItem_COMMENTS(String COMMENTS) {
        this.COMMENTS = COMMENTS;
    }

    public String getApp_STATUS() {
        return this.STATUS;
    }

    public void setConsultItem_STATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
