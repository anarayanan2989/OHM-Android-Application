package com.example.adithya_2.medicareapp.View.patientview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import com.example.adithya_2.medicareapp.C0211R;

public class PatientPayByInsurActivity extends AppCompatActivity {
    Button btnInsurCancel;
    Button btnInsurDate;
    Button btnInsurOK;
    CalendarView calendarView;
    EditText editInsurExpiryDate;
    boolean isClicked;

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientPayByInsurActivity.1 */
    class C02381 implements OnDateChangeListener {
        C02381() {
        }

        public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
            if (calendarView.getVisibility() == 0) {
                PatientPayByInsurActivity.this.editInsurExpiryDate.setText("     " + PatientPayByInsurActivity.this.makeDate(i1 + 1).toString() + "/" + PatientPayByInsurActivity.this.makeDate(i2).toString() + "/" + PatientPayByInsurActivity.this.makeDate(i).toString());
            }
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientPayByInsurActivity.2 */
    class C02392 implements OnClickListener {
        C02392() {
        }

        public void onClick(View v) {
            if (PatientPayByInsurActivity.this.isClicked) {
                PatientPayByInsurActivity.this.calendarView.setVisibility(0);
                PatientPayByInsurActivity.this.isClicked = false;
            } else if (!PatientPayByInsurActivity.this.isClicked) {
                PatientPayByInsurActivity.this.calendarView.setVisibility(4);
                PatientPayByInsurActivity.this.isClicked = true;
            }
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientPayByInsurActivity.3 */
    class C02403 implements OnClickListener {
        C02403() {
        }

        public void onClick(View v) {
            PatientPayByInsurActivity.this.finish();
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientPayByInsurActivity.4 */
    class C02414 implements OnClickListener {
        C02414() {
        }

        public void onClick(View v) {
            PatientPayByInsurActivity.this.finish();
        }
    }

    public PatientPayByInsurActivity() {
        this.isClicked = true;
    }

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView((int) C0211R.layout.patient_insur);
        this.editInsurExpiryDate = (EditText) findViewById(C0211R.id.editInsurExpiryDate);
        this.calendarView = (CalendarView) findViewById(C0211R.id.insurcalendarView);
        this.calendarView.setOnDateChangeListener(new C02381());
        this.btnInsurDate = (Button) findViewById(C0211R.id.btnInsurDate);
        this.btnInsurDate.setOnClickListener(new C02392());
        this.btnInsurOK = (Button) findViewById(C0211R.id.btnInsurOK);
        this.btnInsurOK.setOnClickListener(new C02403());
        this.btnInsurCancel = (Button) findViewById(C0211R.id.btnInsurCancel);
        this.btnInsurCancel.setOnClickListener(new C02414());
    }

    public String makeDate(int date) {
        if (date < 10) {
            return "0" + Integer.toString(date);
        }
        return Integer.toString(date);
    }
}
