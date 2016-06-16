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

public class PatientPayByCardActivity extends AppCompatActivity {
    boolean isClicked;
    String m_date;

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientPayByCardActivity.1 */
    class C02341 implements OnDateChangeListener {
        final /* synthetic */ EditText val$editExpiryDate;

        C02341(EditText editText) {
            this.val$editExpiryDate = editText;
        }

        public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
            if (calendarView.getVisibility() == 0) {
                this.val$editExpiryDate.setText("     " + PatientPayByCardActivity.this.makeDate(i1 + 1).toString() + "/" + PatientPayByCardActivity.this.makeDate(i2).toString() + "/" + PatientPayByCardActivity.this.makeDate(i).toString());
            }
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientPayByCardActivity.2 */
    class C02352 implements OnClickListener {
        final /* synthetic */ CalendarView val$calendarView;

        C02352(CalendarView calendarView) {
            this.val$calendarView = calendarView;
        }

        public void onClick(View v) {
            if (PatientPayByCardActivity.this.isClicked) {
                this.val$calendarView.setVisibility(0);
                PatientPayByCardActivity.this.isClicked = false;
            } else if (!PatientPayByCardActivity.this.isClicked) {
                this.val$calendarView.setVisibility(4);
                PatientPayByCardActivity.this.isClicked = true;
            }
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientPayByCardActivity.3 */
    class C02363 implements OnClickListener {
        C02363() {
        }

        public void onClick(View v) {
            PatientPayByCardActivity.this.finish();
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientPayByCardActivity.4 */
    class C02374 implements OnClickListener {
        C02374() {
        }

        public void onClick(View v) {
            PatientPayByCardActivity.this.finish();
        }
    }

    public PatientPayByCardActivity() {
        this.m_date = null;
    }

    public void onCreate(Bundle instancedSave) {
        this.isClicked = true;
        super.onCreate(instancedSave);
        setContentView((int) C0211R.layout.patient_paybycard);
        EditText editCardName = (EditText) findViewById(C0211R.id.editCardNumber);
        CalendarView calendarView = (CalendarView) findViewById(C0211R.id.payBycalendarView);
        calendarView.setOnDateChangeListener(new C02341((EditText) findViewById(C0211R.id.editExpiryDate)));
        ((Button) findViewById(C0211R.id.btnDatePayByCard)).setOnClickListener(new C02352(calendarView));
        EditText editCardholderName = (EditText) findViewById(C0211R.id.editCardholderName);
        EditText editCVV = (EditText) findViewById(C0211R.id.editCVV);
        EditText editVisa = (EditText) findViewById(C0211R.id.editVisa);
        ((Button) findViewById(C0211R.id.btnPaybyCardOK)).setOnClickListener(new C02363());
        ((Button) findViewById(C0211R.id.btnByPayCancel)).setOnClickListener(new C02374());
    }

    public String makeDate(int date) {
        if (date < 10) {
            return "0" + Integer.toString(date);
        }
        return Integer.toString(date);
    }
}
