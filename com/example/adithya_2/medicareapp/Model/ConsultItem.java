package com.example.adithya_2.medicareapp.Model;

public class ConsultItem {
    public static final String CONSULT_APP_ID = "APP_ID";
    public static final String CONSULT_DAY = "DAY";
    public static final String CONSULT_DOC_ID = "DOC_ID";
    public static final String CONSULT_END_TIME = "END_TIME";
    public static final String CONSULT_START_TIME = "START_TIME";
    public static final String CONSULT_STATUS = "STATUS";
    public static final String TABLE_CONSULT = "Consulting_Hours";
    public static final String TAG;
    private String APP_ID;
    private String DAY;
    private String DOC_ID;
    private String END_TIME;
    private String START_TIME;
    private String STATUS;

    static {
        TAG = ConsultItem.class.getSimpleName();
    }

    public String getConsultItem_DOC_ID() {
        return this.DOC_ID;
    }

    public void setConsultItem_DOC_ID(String DOC_ID) {
        this.DOC_ID = DOC_ID;
    }

    public String getConsultItem_APP_ID() {
        return this.APP_ID;
    }

    public void setConsultItem_APP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getConsultItem_START_TIME() {
        return this.START_TIME;
    }

    public void setConsultItem_START_TIME(String START_TIME) {
        this.START_TIME = START_TIME;
    }

    public String getConsultItem_END_TIME() {
        return this.END_TIME;
    }

    public void setConsultItem_END_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    public String getConsultItem_DAY() {
        return this.DAY;
    }

    public void setConsultItem_DAY(String DAY) {
        this.DAY = DAY;
    }

    public String getConsultItem_STATUS() {
        return this.STATUS;
    }

    public void setConsultItem_STATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
