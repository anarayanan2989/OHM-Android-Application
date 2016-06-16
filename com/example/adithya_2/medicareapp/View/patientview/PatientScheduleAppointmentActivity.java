package com.example.adithya_2.medicareapp.View.patientview;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.DoctorDBCtrl;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PatientScheduleAppointmentActivity extends AppCompatActivity {
    ArrayAdapter adapter;
    Button btnCalendar;
    Button btnSearch;
    Button btnSpecial;
    Spinner comboCity;
    OnDateSetListener date;
    String m_city;
    String m_date;
    String m_patId;
    String m_spec;
    Calendar myCalendar;
    RadioButton radioSpecButton;
    RadioGroup radioSpecGroup;
    EditText txtDate;

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientScheduleAppointmentActivity.1 */
    class C02431 implements OnItemSelectedListener {
        C02431() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            PatientScheduleAppointmentActivity.this.m_city = PatientScheduleAppointmentActivity.this.comboCity.getSelectedItem().toString();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientScheduleAppointmentActivity.2 */
    class C02442 implements OnClickListener {
        C02442() {
        }

        public void onClick(View v) {
            PatientScheduleAppointmentActivity.this.startActivityForResult(new Intent(PatientScheduleAppointmentActivity.this, PatientSpecActivity.class), 1);
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientScheduleAppointmentActivity.3 */
    class C02453 implements OnClickListener {
        C02453() {
        }

        public void onClick(View v) {
            PatientScheduleAppointmentActivity.this.startActivityForResult(new Intent(PatientScheduleAppointmentActivity.this, PatientDateSelectActivity.class), 2);
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientScheduleAppointmentActivity.4 */
    class C02464 implements OnClickListener {
        C02464() {
        }

        public void onClick(View v) {
            if (PatientScheduleAppointmentActivity.this.m_spec == null) {
                Toast.makeText(PatientScheduleAppointmentActivity.this, "Please Choose Speciality", 1).show();
            } else if (PatientScheduleAppointmentActivity.this.m_date == null) {
                Toast.makeText(PatientScheduleAppointmentActivity.this, "Please Select Date", 1).show();
            } else if (new DoctorDBCtrl().getDoctorName(PatientScheduleAppointmentActivity.this.m_spec, PatientScheduleAppointmentActivity.this.m_city, PatientScheduleAppointmentActivity.this.m_date) != null) {
                PatientScheduleAppointmentActivity.this.m_city = PatientScheduleAppointmentActivity.this.comboCity.getSelectedItem().toString();
                Intent intent = new Intent(PatientScheduleAppointmentActivity.this, PatientSearchActivity.class);
                intent.putExtra("PatId", PatientScheduleAppointmentActivity.this.m_patId);
                intent.putExtra("m_city", PatientScheduleAppointmentActivity.this.m_city);
                intent.putExtra("m_spec", PatientScheduleAppointmentActivity.this.m_spec);
                intent.putExtra("m_date", PatientScheduleAppointmentActivity.this.m_date);
                PatientScheduleAppointmentActivity.this.startActivity(intent);
            } else {
                Toast.makeText(PatientScheduleAppointmentActivity.this, "No Match!", 1).show();
            }
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientScheduleAppointmentActivity.5 */
    class C02475 implements OnDateSetListener {
        C02475() {
        }

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            PatientScheduleAppointmentActivity.this.myCalendar.set(1, year);
            PatientScheduleAppointmentActivity.this.myCalendar.set(2, monthOfYear);
            PatientScheduleAppointmentActivity.this.myCalendar.set(5, dayOfMonth);
            PatientScheduleAppointmentActivity.this.updateLabel();
        }
    }

    public PatientScheduleAppointmentActivity() {
        this.myCalendar = Calendar.getInstance();
        this.m_spec = null;
        this.date = new C02475();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0211R.layout.patient_appointment);
        this.m_patId = new Intent(getIntent()).getStringExtra("PatID");
        this.comboCity = (Spinner) findViewById(C0211R.id.spinCity1);
        this.adapter = ArrayAdapter.createFromResource(this, C0211R.array.city_1, 17367049);
        this.comboCity.setAdapter(this.adapter);
        this.comboCity.setOnItemSelectedListener(new C02431());
        this.btnSpecial = (Button) findViewById(C0211R.id.btnSpecial);
        this.btnSpecial.setOnClickListener(new C02442());
        this.txtDate = (EditText) findViewById(C0211R.id.dateEdit);
        this.btnCalendar = (Button) findViewById(C0211R.id.btnCalender);
        this.btnCalendar.setOnClickListener(new C02453());
        this.btnSearch = (Button) findViewById(C0211R.id.btnSearch);
        this.btnSearch.setOnClickListener(new C02464());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            return;
        }
        if (requestCode == 1) {
            this.m_spec = data.getStringExtra("data");
        } else if (requestCode == 2) {
            this.m_date = data.getStringExtra("Day");
            this.txtDate.setText(this.m_date);
        }
    }

    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        this.m_date = this.txtDate.getText().toString();
    }
}
