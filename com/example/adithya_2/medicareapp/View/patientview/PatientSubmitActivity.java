package com.example.adithya_2.medicareapp.View.patientview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.AppointmentDBCtrl;
import com.example.adithya_2.medicareapp.Model.AppointmentItem;
import com.example.adithya_2.medicareapp.Model.ConsultDBCtrl;
import com.example.adithya_2.medicareapp.Model.ConsultItem;
import com.example.adithya_2.medicareapp.Model.MedicineItem;

public class PatientSubmitActivity extends AppCompatActivity {
    AppointmentDBCtrl appointmentDBCtrl;
    AppointmentItem appointmentItem;
    Button btnSubmit;
    ConsultDBCtrl consultDBCtrl;
    ConsultItem consultItem;
    EditText endEdit;
    TextView location;
    String m_PatId;
    String m_date;
    String m_docId;
    String m_endDate;
    String m_location;
    String m_startDate;
    EditText startEdit;

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientSubmitActivity.1 */
    class C02491 implements OnClickListener {
        C02491() {
        }

        public void onClick(View v) {
            PatientSubmitActivity.this.appointmentItem.setConsultItem_PAT_ID(PatientSubmitActivity.this.m_PatId);
            PatientSubmitActivity.this.appointmentItem.setConsultItem_DOC_ID(PatientSubmitActivity.this.m_docId);
            PatientSubmitActivity.this.appointmentItem.setConsultItem_STATUS("Existing");
            PatientSubmitActivity.this.appointmentDBCtrl.insertApp(PatientSubmitActivity.this.appointmentItem);
            String AppID = PatientSubmitActivity.this.appointmentDBCtrl.getAppID(PatientSubmitActivity.this.m_PatId, PatientSubmitActivity.this.m_docId);
            int update = PatientSubmitActivity.this.consultDBCtrl.updateAppID(PatientSubmitActivity.this.m_docId, PatientSubmitActivity.this.m_date, AppID);
            Toast.makeText(PatientSubmitActivity.this, "Appointment Confirmed.", 1).show();
            Intent intent1 = new Intent(PatientSubmitActivity.this, PatientHomeActivity.class);
            intent1.putExtra("AppID", AppID);
            PatientSubmitActivity.this.startActivity(intent1);
            PatientSubmitActivity.this.finish();
        }
    }

    public void onCreate(Bundle saveInstnce) {
        super.onCreate(saveInstnce);
        setContentView((int) C0211R.layout.patient_changeschedule);
        this.consultItem = new ConsultItem();
        this.consultDBCtrl = new ConsultDBCtrl();
        this.appointmentItem = new AppointmentItem();
        this.appointmentDBCtrl = new AppointmentDBCtrl();
        Intent intent = new Intent(getIntent());
        this.m_PatId = intent.getStringExtra("PatID");
        this.m_docId = intent.getStringExtra("DocID");
        this.m_startDate = intent.getStringExtra("start");
        this.m_endDate = intent.getStringExtra("end");
        this.m_location = intent.getStringExtra("location");
        this.m_date = intent.getStringExtra(MedicineItem.MEDICINE_DATE);
        this.startEdit = (EditText) findViewById(C0211R.id.editStart);
        this.startEdit.setText(this.m_startDate);
        this.endEdit = (EditText) findViewById(C0211R.id.editEnd);
        this.endEdit.setText(this.m_endDate);
        this.location = (TextView) findViewById(C0211R.id.editLocation);
        this.location.setText(this.m_location);
        this.btnSubmit = (Button) findViewById(C0211R.id.btnSub);
        this.btnSubmit.setOnClickListener(new C02491());
    }
}
