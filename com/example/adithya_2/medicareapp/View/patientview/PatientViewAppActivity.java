package com.example.adithya_2.medicareapp.View.patientview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.adithya_2.medicareapp.BuildConfig;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.AppDocNameList;
import com.example.adithya_2.medicareapp.Model.AppointmentDBCtrl;
import com.example.adithya_2.medicareapp.Model.ConsultDBCtrl;
import java.util.ArrayList;
import java.util.List;

public class PatientViewAppActivity extends AppCompatActivity {
    List<AppDocNameList> appDocNameLists;
    Button btnBull;
    Button btnCancel;
    Button btnDash;
    Button btnHome;
    TextView editAppDocName;
    String m_AppID;
    String m_DocName;
    String m_PatID;
    TextView spinnerApp;

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientViewAppActivity.1 */
    class C02501 implements OnClickListener {
        C02501() {
        }

        public void onClick(View v) {
            String comments = new AppointmentDBCtrl().checkComments(PatientViewAppActivity.this.m_AppID);
            if (comments != null) {
                new ConsultDBCtrl().updateAppID(PatientViewAppActivity.this.m_AppID, BuildConfig.FLAVOR);
                Intent intent1 = new Intent(PatientViewAppActivity.this, PatientPrescriptionActivity.class);
                intent1.putExtra("APPID", PatientViewAppActivity.this.m_AppID);
                intent1.putExtra("Comments", comments);
                PatientViewAppActivity.this.startActivity(intent1);
                return;
            }
            Toast.makeText(PatientViewAppActivity.this, "Sorry No Appointment Please wait.", 1).show();
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientViewAppActivity.2 */
    class C02512 implements OnClickListener {
        C02512() {
        }

        public void onClick(View v) {
            PatientViewAppActivity.this.startActivity(new Intent(PatientViewAppActivity.this, PatientPayActivity.class));
            PatientViewAppActivity.this.finish();
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientViewAppActivity.3 */
    class C02523 implements OnClickListener {
        C02523() {
        }

        public void onClick(View v) {
            PatientViewAppActivity.this.startActivity(new Intent(PatientViewAppActivity.this, PatientScheduleAppointmentActivity.class));
            PatientViewAppActivity.this.finish();
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientViewAppActivity.4 */
    class C02534 implements OnClickListener {
        C02534() {
        }

        public void onClick(View v) {
            PatientViewAppActivity.this.startActivity(new Intent(PatientViewAppActivity.this, PatientHomeActivity.class));
            PatientViewAppActivity.this.finish();
        }
    }

    public void onCreate(Bundle saveInstance) {
        Intent intent = new Intent(getIntent());
        this.m_AppID = intent.getStringExtra("AppID");
        this.m_PatID = intent.getStringExtra("PatID");
        super.onCreate(saveInstance);
        setContentView((int) C0211R.layout.patient_appview);
        this.appDocNameLists = new AppointmentDBCtrl().getAppDocName();
        this.spinnerApp = (TextView) findViewById(C0211R.id.spinner_app);
        this.spinnerApp.setText(this.m_AppID);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, 17367049, new ArrayList());
        adapter.add(this.m_AppID.trim());
        for (int i = 0; i < this.appDocNameLists.size(); i++) {
            if (!((AppDocNameList) this.appDocNameLists.get(i)).getAppID().trim().equals(this.m_AppID.trim())) {
                adapter.add(((AppDocNameList) this.appDocNameLists.get(i)).getAppID());
            }
            if (((AppDocNameList) this.appDocNameLists.get(i)).getAppID().trim().equals(this.m_AppID.trim())) {
                this.m_DocName = ((AppDocNameList) this.appDocNameLists.get(i)).getDocLName() + ((AppDocNameList) this.appDocNameLists.get(i)).getDocFName();
            }
        }
        this.editAppDocName = (TextView) findViewById(C0211R.id.editAppDocName);
        this.editAppDocName.setText(this.m_DocName);
        this.btnDash = (Button) findViewById(C0211R.id.btnDash);
        this.btnDash.setOnClickListener(new C02501());
        this.btnBull = (Button) findViewById(C0211R.id.btnBull);
        this.btnBull.setOnClickListener(new C02512());
        this.btnCancel = (Button) findViewById(C0211R.id.btnCancel);
        this.btnCancel.setOnClickListener(new C02523());
        this.btnHome = (Button) findViewById(C0211R.id.btnGoHome);
        this.btnHome.setOnClickListener(new C02534());
    }

    public void onResume() {
        super.onResume();
    }
}
