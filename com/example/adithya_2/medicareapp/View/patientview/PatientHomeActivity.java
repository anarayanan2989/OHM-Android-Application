package com.example.adithya_2.medicareapp.View.patientview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.AppointmentDBCtrl;
import com.example.adithya_2.medicareapp.View.MainActivity;

public class PatientHomeActivity extends AppCompatActivity implements OnClickListener {
    static String m_patID;
    Button btn_EditPro;
    Button btn_Schedule;
    Button btn_SignOff;
    Button btn_ViewApp;
    final Context context;
    String m_AppID;
    String m_AppIDInput;
    String m_PATID;

    public PatientHomeActivity() {
        this.context = this;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0211R.layout.patient_home);
        this.btn_Schedule = (Button) findViewById(C0211R.id.btnSchedulMenu);
        this.btn_Schedule.setOnClickListener(this);
        this.btn_ViewApp = (Button) findViewById(C0211R.id.btnAppMenu);
        this.btn_ViewApp.setOnClickListener(this);
        this.btn_EditPro = (Button) findViewById(C0211R.id.btnProfileMenu);
        this.btn_EditPro.setOnClickListener(this);
        this.btn_SignOff = (Button) findViewById(C0211R.id.btnSingOff);
        this.btn_SignOff.setOnClickListener(this);
        this.m_AppID = new Intent(getIntent()).getStringExtra("AppID");
        this.m_PATID = new Intent(getIntent()).getStringExtra("PatID");
        this.m_AppIDInput = new AppointmentDBCtrl().appCheck(this.m_PATID);
        if (this.m_AppID == null && m_patID == null) {
            m_patID = this.m_PATID;
        }
    }

    public void onClick(View v) {
        Intent intent1;
        if (v == findViewById(C0211R.id.btnSchedulMenu)) {
            if (this.m_AppIDInput == null && this.m_AppID == null) {
                intent1 = new Intent(this, PatientScheduleAppointmentActivity.class);
                intent1.putExtra("PatID", m_patID);
                startActivity(intent1);
                return;
            }
            Toast.makeText(this, "Existing Appointment. ", 1).show();
        } else if (v == findViewById(C0211R.id.btnAppMenu)) {
            if (this.m_AppID == null && this.m_AppIDInput == null) {
                Toast.makeText(this.context, "There is no selected appointment.", 1).show();
                return;
            }
            intent1 = new Intent(this, PatientViewAppActivity.class);
            if (this.m_AppID != null) {
                intent1.putExtra("AppID", this.m_AppID);
            } else {
                intent1.putExtra("AppID", this.m_AppIDInput);
            }
            intent1.putExtra("PatID", m_patID);
            startActivity(intent1);
        } else if (v == findViewById(C0211R.id.btnProfileMenu)) {
            startActivity(new Intent(this.context, RegistrationActivity.class));
            finish();
        } else if (v == findViewById(C0211R.id.btnSingOff)) {
            startActivity(new Intent(this.context, MainActivity.class));
            finish();
        }
    }
}
