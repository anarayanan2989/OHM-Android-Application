package com.example.adithya_2.medicareapp.Model;

public class DoctorAppItemList {
    private String End_Time;
    private String PatID;
    private String Pat_DOB;
    private String PatientFName;
    private String PatientLName;
    private String Reason;
    private String Start_Time;
    private String Time1;

    public void setPatID(String PatID) {
        this.PatID = PatID;
    }

    public String getPatID() {
        return this.PatID;
    }

    public void setPatientFName(String PatientFName) {
        this.PatientFName = PatientFName;
    }

    public String getPatientFName() {
        return this.PatientFName;
    }

    public void setPatientLName(String PatientLName) {
        this.PatientLName = PatientLName;
    }

    public String getPatientLName() {
        return this.PatientLName;
    }

    public void setPat_DOB(String Pat_DOB) {
        this.Pat_DOB = Pat_DOB;
    }

    public String getPat_DOB() {
        return this.Pat_DOB;
    }

    public void setTime1(String Time1) {
        this.Time1 = Time1;
    }

    public String getTime1() {
        return this.Time1;
    }

    public void setStart_Time(String StartTime) {
        this.Start_Time = StartTime;
    }

    public String getStart_Time() {
        return this.Start_Time;
    }

    public void setEnd_Time(String End_Time) {
        this.End_Time = End_Time;
    }

    public String getEnd_Time() {
        return this.End_Time;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }

    public String getReason() {
        return this.Reason;
    }
}
