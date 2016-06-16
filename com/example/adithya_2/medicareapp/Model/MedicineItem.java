package com.example.adithya_2.medicareapp.Model;

public class MedicineItem {
    public static final String MEDICINE_APP_ID = "APP_ID";
    public static final String MEDICINE_BLOOD_TEST = "Blood_test";
    public static final String MEDICINE_BREAK_AFTER = "Break_after";
    public static final String MEDICINE_BREAK_BEFORE = "Break_before";
    public static final String MEDICINE_DATE = "Date";
    public static final String MEDICINE_DINNER_AFTER = "Dinner_after";
    public static final String MEDICINE_DINNER_BEFORE = "Dinner_before";
    public static final String MEDICINE_ID = "Medicine_ID";
    public static final String MEDICINE_LUNCH_AFTER = "Lunch_after";
    public static final String MEDICINE_LUNCH_BEFORE = "Lunch_before";
    public static final String MEDICINE_NAME = "Medicine_Name";
    public static final String MEDICINE_TIME = "Time";
    public static final String MEDICINE_XRAY = "XRay";
    public static final String PAT_BPLEVEL = "BPLevel";
    public static final String PAT_SUGAR = "Sugar";
    public static final String PAT_WEIGHT = "Pat_Weight";
    public static final String TABLE_MEDICINE = "Medicine";
    public static final String TAG;
    private String App_ID;
    private String BPLevel;
    private String Blood_test;
    private String Breakfast_after;
    private String Breakfast_before;
    private String Date;
    private String Dinner_after;
    private String Dinner_before;
    private String Lanch_after;
    private String Lanch_before;
    private String Mdicine_Id;
    private String Medicine_Name;
    private String PAT_ID;
    private String Pat_Sugar;
    private String Pat_weight;
    private String Time;
    private String XRay;

    static {
        TAG = MedicineItem.class.getSimpleName();
    }

    public String getMdicine_Id() {
        return this.Mdicine_Id;
    }

    public void setMedicineId(String Medicine_Id) {
        this.Mdicine_Id = Medicine_Id;
    }

    public String getApp_ID() {
        return this.App_ID;
    }

    public void setApp_ID(String App_ID) {
        this.App_ID = App_ID;
    }

    public String getMedicineName() {
        return this.Medicine_Name;
    }

    public void setMedicineName(String Medicine_Name) {
        this.Medicine_Name = Medicine_Name;
    }

    public String getPAT_ID() {
        return this.PAT_ID;
    }

    public void setPAT_ID(String PAT_ID) {
        this.PAT_ID = PAT_ID;
    }

    public String getMedicineBreakBefore() {
        return this.Breakfast_before;
    }

    public void setMedicineBreakBefore(String Breakfast_before) {
        this.Breakfast_before = Breakfast_before;
    }

    public String getMedicineBreakAfter() {
        return this.Breakfast_after;
    }

    public void setMedicineBreakAfter(String Breakfast_after) {
        this.Breakfast_after = Breakfast_after;
    }

    public String getMedicineLunchBefore() {
        return this.Lanch_before;
    }

    public void setMedicineLunchBefore(String Lanch_before) {
        this.Lanch_before = Lanch_before;
    }

    public String getMedicineLunchAfter() {
        return this.Lanch_after;
    }

    public void setMedicineLunchAfter(String Lanch_after) {
        this.Lanch_after = Lanch_after;
    }

    public String getMedicineDinnerBefore() {
        return this.Dinner_before;
    }

    public void setMedicineDinnerBefore(String Dinner_before) {
        this.Dinner_before = Dinner_before;
    }

    public String getMedicineDinnerAfter() {
        return this.Dinner_after;
    }

    public void setMedicineDinnerAfter(String Dinner_after) {
        this.Dinner_after = Dinner_after;
    }

    public String getMedicineBloodTest() {
        return this.Blood_test;
    }

    public void setMedicineBloodTest(String Blood_test) {
        this.Blood_test = Blood_test;
    }

    public String getTime() {
        return this.Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getDate() {
        return this.Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getXRay() {
        return this.XRay;
    }

    public void setXRay(String XRay) {
        this.XRay = XRay;
    }

    public String getPatSugar() {
        return this.Pat_Sugar;
    }

    public void setPatSugar(String Pat_Sugar) {
        this.Pat_Sugar = Pat_Sugar;
    }

    public String getPatBplevel() {
        return this.BPLevel;
    }

    public void setPatBplevel(String BPLevel) {
        this.BPLevel = BPLevel;
    }

    public String getPatWeight() {
        return this.Pat_weight;
    }

    public void setPatWeight(String Pat_weight) {
        this.Pat_weight = Pat_weight;
    }
}
