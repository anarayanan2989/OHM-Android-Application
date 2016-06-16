package com.example.adithya_2.medicareapp.View.doctorview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.AppointmentDBCtrl;
import com.example.adithya_2.medicareapp.Model.ConsultDBCtrl;
import com.example.adithya_2.medicareapp.Model.MedicineDBCtrl;
import com.example.adithya_2.medicareapp.Model.MedicineItem;

public class DoctorMedicineInfoActivity extends AppCompatActivity {
    String m_AppID;
    String m_PatID;
    MedicineDBCtrl medicineDBCtrl;

    /* renamed from: com.example.adithya_2.medicareapp.View.doctorview.DoctorMedicineInfoActivity.1 */
    class C02211 implements OnCheckedChangeListener {
        final /* synthetic */ EditText val$c_Date;
        final /* synthetic */ EditText val$c_Time;

        C02211(EditText editText, EditText editText2) {
            this.val$c_Time = editText;
            this.val$c_Date = editText2;
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                this.val$c_Time.setVisibility(0);
                this.val$c_Date.setVisibility(0);
                return;
            }
            this.val$c_Time.setVisibility(4);
            this.val$c_Date.setVisibility(4);
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.doctorview.DoctorMedicineInfoActivity.2 */
    class C02222 implements OnClickListener {
        final /* synthetic */ CheckBox val$c_Blood;
        final /* synthetic */ EditText val$c_BreakAfter;
        final /* synthetic */ EditText val$c_BreakBefore;
        final /* synthetic */ EditText val$c_Date;
        final /* synthetic */ EditText val$c_DinnerAfter;
        final /* synthetic */ EditText val$c_DinnerBefore;
        final /* synthetic */ EditText val$c_LunchAfter;
        final /* synthetic */ EditText val$c_LunchBefore;
        final /* synthetic */ EditText val$c_MedicineName;
        final /* synthetic */ EditText val$c_PatBPLevel;
        final /* synthetic */ EditText val$c_PatSugar;
        final /* synthetic */ EditText val$c_PatWeight;
        final /* synthetic */ EditText val$c_Time;
        final /* synthetic */ CheckBox val$c_XRay;
        final /* synthetic */ MedicineItem val$medicineItem;

        C02222(EditText editText, MedicineItem medicineItem, EditText editText2, EditText editText3, EditText editText4, EditText editText5, EditText editText6, EditText editText7, CheckBox checkBox, EditText editText8, EditText editText9, CheckBox checkBox2, EditText editText10, EditText editText11, EditText editText12) {
            this.val$c_MedicineName = editText;
            this.val$medicineItem = medicineItem;
            this.val$c_BreakBefore = editText2;
            this.val$c_BreakAfter = editText3;
            this.val$c_LunchBefore = editText4;
            this.val$c_LunchAfter = editText5;
            this.val$c_DinnerBefore = editText6;
            this.val$c_DinnerAfter = editText7;
            this.val$c_Blood = checkBox;
            this.val$c_Time = editText8;
            this.val$c_Date = editText9;
            this.val$c_XRay = checkBox2;
            this.val$c_PatSugar = editText10;
            this.val$c_PatBPLevel = editText11;
            this.val$c_PatWeight = editText12;
        }

        public void onClick(View v) {
            if (this.val$c_MedicineName.getText().toString() == null) {
                Toast.makeText(DoctorMedicineInfoActivity.this, "Please enter Prescription ", 1).show();
                return;
            }
            this.val$medicineItem.setApp_ID(DoctorMedicineInfoActivity.this.m_AppID);
            AppointmentDBCtrl appointmentDBCtrl = new AppointmentDBCtrl();
            DoctorMedicineInfoActivity.this.m_PatID = appointmentDBCtrl.getPAT_ID(DoctorMedicineInfoActivity.this.m_AppID);
            this.val$medicineItem.setPAT_ID(DoctorMedicineInfoActivity.this.m_PatID);
            this.val$medicineItem.setMedicineName(this.val$c_MedicineName.getText().toString());
            this.val$medicineItem.setMedicineBreakBefore(this.val$c_BreakBefore.getText().toString());
            this.val$medicineItem.setMedicineBreakAfter(this.val$c_BreakAfter.getText().toString());
            this.val$medicineItem.setMedicineLunchBefore(this.val$c_LunchBefore.getText().toString());
            this.val$medicineItem.setMedicineLunchAfter(this.val$c_LunchAfter.getText().toString());
            this.val$medicineItem.setMedicineDinnerBefore(this.val$c_DinnerBefore.getText().toString());
            this.val$medicineItem.setMedicineDinnerAfter(this.val$c_DinnerAfter.getText().toString());
            if (this.val$c_Blood.isChecked()) {
                this.val$medicineItem.setMedicineBloodTest("true");
                this.val$medicineItem.setTime(this.val$c_Time.getText().toString());
                this.val$medicineItem.setDate(this.val$c_Date.getText().toString());
            } else {
                this.val$medicineItem.setMedicineBloodTest("false");
            }
            if (this.val$c_XRay.isChecked()) {
                this.val$medicineItem.setXRay("true");
            }
            this.val$medicineItem.setPatSugar(this.val$c_PatSugar.getText().toString());
            this.val$medicineItem.setPatBplevel(this.val$c_PatBPLevel.getText().toString());
            this.val$medicineItem.setPatWeight(this.val$c_PatWeight.getText().toString());
            if (!DoctorMedicineInfoActivity.this.medicineDBCtrl.isExistValue(DoctorMedicineInfoActivity.this.m_AppID)) {
                DoctorMedicineInfoActivity.this.medicineDBCtrl.insetMedicineData(this.val$medicineItem);
            } else if (DoctorMedicineInfoActivity.this.medicineDBCtrl.updateMedicine(this.val$medicineItem) > 0) {
                Toast.makeText(DoctorMedicineInfoActivity.this, "Updating Success!", 1).show();
                String m_prescription = this.val$c_MedicineName.getText().toString();
                ConsultDBCtrl consultDBCtrl = new ConsultDBCtrl();
            } else {
                Toast.makeText(DoctorMedicineInfoActivity.this, "Retry Updating!", 1).show();
            }
            DoctorMedicineInfoActivity.this.startActivity(new Intent(DoctorMedicineInfoActivity.this, DocHomeActivity.class));
            DoctorMedicineInfoActivity.this.finish();
        }
    }

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView((int) C0211R.layout.doctor_detail);
        EditText c_MedicineName = (EditText) findViewById(C0211R.id.d_editMedcineName);
        EditText c_BreakBefore = (EditText) findViewById(C0211R.id.d_editBreakBefore);
        EditText c_BreakAfter = (EditText) findViewById(C0211R.id.d_editBreakAfter);
        EditText c_LunchBefore = (EditText) findViewById(C0211R.id.d_editLanchBefore);
        EditText c_LunchAfter = (EditText) findViewById(C0211R.id.d_editLanchAfter);
        EditText c_DinnerBefore = (EditText) findViewById(C0211R.id.d_editDinnerBefore);
        EditText c_DinnerAfter = (EditText) findViewById(C0211R.id.d_editDinnerAfter);
        CheckBox c_Blood = (CheckBox) findViewById(C0211R.id.d_BloodCheck);
        EditText c_Time = (EditText) findViewById(C0211R.id.d_editTime2);
        c_Time.setVisibility(4);
        EditText c_Date = (EditText) findViewById(C0211R.id.d_editDate2);
        c_Date.setVisibility(4);
        CheckBox c_XRay = (CheckBox) findViewById(C0211R.id.d_XRayCheck);
        EditText c_PatSugar = (EditText) findViewById(C0211R.id.d_editpateitnetSugar);
        EditText c_PatBPLevel = (EditText) findViewById(C0211R.id.d_editpatitentBPLevel);
        EditText c_PatWeight = (EditText) findViewById(C0211R.id.d_patientWeight);
        Button btnUpdateInfo = (Button) findViewById(C0211R.id.d_btnUpdateMedicine);
        MedicineItem medicineItem = new MedicineItem();
        this.medicineDBCtrl = new MedicineDBCtrl();
        Intent intent = new Intent(getIntent());
        this.m_AppID = intent.getStringExtra("App_ID");
        c_Blood.setOnCheckedChangeListener(new C02211(c_Time, c_Date));
        btnUpdateInfo.setOnClickListener(new C02222(c_MedicineName, medicineItem, c_BreakBefore, c_BreakAfter, c_LunchBefore, c_LunchAfter, c_DinnerBefore, c_DinnerAfter, c_Blood, c_Time, c_Date, c_XRay, c_PatSugar, c_PatBPLevel, c_PatWeight));
    }
}
