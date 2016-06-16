package com.example.adithya_2.medicareapp.View.patientview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.DoctorDBCtrl;
import com.example.adithya_2.medicareapp.Model.DoctorItem;
import com.example.adithya_2.medicareapp.Model.MedicineItem;
import com.example.adithya_2.medicareapp.Model.SearchResultList;
import java.util.List;

public class PatientSearchActivity extends AppCompatActivity {
    Button btnSchedule;
    DoctorDBCtrl doctorDBCtrl;
    DoctorItem doctorItem;
    TextView editDocName;
    TextView editEnd;
    TextView editHospCity;
    TextView editHospName;
    TextView editMobile;
    TextView editSpec;
    TextView editStart;
    String m_DOC_ID;
    String m_city;
    String m_date;
    String m_patID;
    String m_spec;

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientSearchActivity.1 */
    class C02481 implements OnClickListener {
        C02481() {
        }

        public void onClick(View v) {
            if (PatientSearchActivity.this.editStart == null || PatientSearchActivity.this.editEnd == null) {
                Toast.makeText(PatientSearchActivity.this, "Select Doctor", 1).show();
                PatientSearchActivity.this.finish();
                return;
            }
            Intent intent1 = new Intent(PatientSearchActivity.this, PatientSubmitActivity.class);
            intent1.putExtra("PatID", PatientSearchActivity.this.m_patID);
            intent1.putExtra("DocID", PatientSearchActivity.this.m_DOC_ID);
            intent1.putExtra(MedicineItem.MEDICINE_DATE, PatientSearchActivity.this.m_date);
            intent1.putExtra("start", PatientSearchActivity.this.editStart.getText().toString());
            intent1.putExtra("end", PatientSearchActivity.this.editEnd.getText().toString());
            intent1.putExtra("location", PatientSearchActivity.this.editHospCity.getText().toString());
            PatientSearchActivity.this.startActivity(intent1);
        }
    }

    public void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        setContentView((int) C0211R.layout.patient_search);
        this.editDocName = (TextView) findViewById(C0211R.id.editName1);
        this.editHospCity = (TextView) findViewById(C0211R.id.editLoc1);
        this.editHospName = (TextView) findViewById(C0211R.id.editHosp);
        this.editSpec = (TextView) findViewById(C0211R.id.editSpec1);
        this.editStart = (TextView) findViewById(C0211R.id.editStart1);
        this.editEnd = (TextView) findViewById(C0211R.id.editEnd1);
        this.editMobile = (TextView) findViewById(C0211R.id.edtiMobile1);
        this.btnSchedule = (Button) findViewById(C0211R.id.btnSchedule1);
        Intent intent = new Intent(getIntent());
        this.m_city = intent.getStringExtra("m_city");
        this.m_spec = intent.getStringExtra("m_spec");
        this.m_date = intent.getStringExtra("m_date");
        this.m_patID = intent.getStringExtra("PatId");
        this.doctorItem = new DoctorItem();
        this.doctorDBCtrl = new DoctorDBCtrl();
        List<SearchResultList> searchResultLists = this.doctorDBCtrl.getDoctorName(this.m_spec, this.m_city, this.m_date);
        if (searchResultLists != null) {
            this.m_DOC_ID = ((SearchResultList) searchResultLists.get(0)).getDoc_ID();
            this.editDocName.setText("    " + ((SearchResultList) searchResultLists.get(0)).getL_Name() + " " + ((SearchResultList) searchResultLists.get(0)).getF_Name());
            this.editHospCity.setText("    " + ((SearchResultList) searchResultLists.get(0)).getHOSP_City());
            this.editHospName.setText("     " + ((SearchResultList) searchResultLists.get(0)).getHOSP_Name());
            this.editSpec.setText("     " + ((SearchResultList) searchResultLists.get(0)).getSpec());
            this.editMobile.setText("     " + ((SearchResultList) searchResultLists.get(0)).getMobile_No());
            this.editStart.setText(" " + ((SearchResultList) searchResultLists.get(0)).getStart_Time());
            this.editEnd.setText(" " + ((SearchResultList) searchResultLists.get(0)).getEnd_Time());
        } else {
            Toast.makeText(this, "No Match", 1).show();
        }
        this.btnSchedule.setOnClickListener(new C02481());
    }
}
