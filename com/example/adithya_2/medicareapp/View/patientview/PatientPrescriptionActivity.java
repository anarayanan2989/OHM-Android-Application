package com.example.adithya_2.medicareapp.View.patientview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.MedicineDBCtrl;
import com.example.adithya_2.medicareapp.Model.MedicineItem;

public class PatientPrescriptionActivity extends AppCompatActivity {
    String m_AppId;
    String m_Comments;

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientPrescriptionActivity.1 */
    class C02421 implements OnClickListener {
        C02421() {
        }

        public void onClick(View v) {
            PatientPrescriptionActivity.this.startActivity(new Intent(PatientPrescriptionActivity.this, PatientHomeActivity.class));
            PatientPrescriptionActivity.this.finish();
        }
    }

    public void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        setContentView(C0211R.layout.patient_prescription);
        MedicineDBCtrl medicineDBCtrl = new MedicineDBCtrl();
        Intent intent1 = new Intent(getIntent());
        this.m_AppId = intent1.getStringExtra("APPID");
        this.m_Comments = intent1.getStringExtra("Comments");
        Log.d("AppID", this.m_AppId);
        TextView p_MedicineName = (TextView) findViewById(C0211R.id.editMedicineName1);
        TextView p_BreakBefore = (TextView) findViewById(C0211R.id.editBeforeBreakfast1);
        TextView p_BreakAfter = (TextView) findViewById(C0211R.id.editAfterBreakfast1);
        TextView p_LunchBefore = (TextView) findViewById(C0211R.id.editBeforeLunch1);
        TextView p_LunchAfter = (TextView) findViewById(C0211R.id.editAfterLunch1);
        TextView p_DinnerBefore = (TextView) findViewById(C0211R.id.editBeforeDinner1);
        TextView p_DinnerAfter = (TextView) findViewById(C0211R.id.editAfterDinner1);
        CheckBox p_Blood = (CheckBox) findViewById(C0211R.id.checkBloodTest1);
        TextView p_Time = (TextView) findViewById(C0211R.id.editTime1);
        TextView p_Date = (TextView) findViewById(C0211R.id.editDate1);
        CheckBox p_XRay = (CheckBox) findViewById(C0211R.id.checkXRay);
        TextView p_PatSugar = (TextView) findViewById(C0211R.id.editSugarLevel1);
        TextView p_PatBPLevel = (TextView) findViewById(C0211R.id.editBPLevel1);
        TextView p_PatWeight = (TextView) findViewById(C0211R.id.editWeight1);
        Button btnOk = (Button) findViewById(C0211R.id.btnOkMedicine);
        TextView editComments = (TextView) findViewById(C0211R.id.editComments1);
        if (medicineDBCtrl.isExistValue(this.m_AppId)) {
            Log.d("AppID", "APPID exist.");
            MedicineItem medicineItem = medicineDBCtrl.getMedicineInfo(this.m_AppId);
            p_MedicineName.setText(medicineItem.getMedicineName());
            p_BreakBefore.setText(medicineItem.getMedicineBreakBefore());
            p_BreakAfter.setText(medicineItem.getMedicineBreakAfter());
            p_LunchBefore.setText(medicineItem.getMedicineLunchBefore());
            p_LunchAfter.setText(medicineItem.getMedicineLunchAfter());
            p_DinnerBefore.setText(medicineItem.getMedicineDinnerBefore());
            p_DinnerAfter.setText(medicineItem.getMedicineDinnerAfter());
            if (medicineItem.getMedicineBloodTest() == null) {
                p_Blood.setChecked(false);
            } else if (medicineItem.getMedicineBloodTest().equals("true")) {
                p_Blood.setChecked(true);
                p_Time.setText(medicineItem.getTime());
                p_Date.setText(medicineItem.getDate());
            } else {
                p_Blood.setChecked(false);
            }
            if (medicineItem.getXRay() == null) {
                p_XRay.setChecked(false);
            } else if (medicineItem.getXRay().equals("true")) {
                p_XRay.setChecked(true);
            } else {
                p_XRay.setChecked(false);
            }
            p_PatSugar.setText(medicineItem.getPatSugar());
            p_PatBPLevel.setText(medicineItem.getPatBplevel());
            p_PatWeight.setText(medicineItem.getPatWeight());
            editComments.setText(this.m_Comments);
        } else {
            Log.d("AppID", "APPID is not exist.");
        }
        btnOk.setOnClickListener(new C02421());
    }
}
