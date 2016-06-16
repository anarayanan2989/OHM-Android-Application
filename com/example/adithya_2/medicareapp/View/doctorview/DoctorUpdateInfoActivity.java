package com.example.adithya_2.medicareapp.View.doctorview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.DoctorDBCtrl;

public class DoctorUpdateInfoActivity extends AppCompatActivity {
    Button d_btnSub;
    EditText d_editAddress;
    EditText d_editHosp;
    EditText d_editLoc;
    EditText d_editMobile;
    DoctorDBCtrl doctorDBCtrl;
    String m_email;

    /* renamed from: com.example.adithya_2.medicareapp.View.doctorview.DoctorUpdateInfoActivity.1 */
    class C02251 implements OnClickListener {
        C02251() {
        }

        public void onClick(View v) {
            if (DoctorUpdateInfoActivity.this.d_editAddress.getText().toString().isEmpty()) {
                Toast.makeText(DoctorUpdateInfoActivity.this, "Please Enter Contact Address.", 1).show();
                DoctorUpdateInfoActivity.this.d_editAddress.requestFocus();
            } else if (DoctorUpdateInfoActivity.this.d_editMobile.getText().toString().isEmpty()) {
                Toast.makeText(DoctorUpdateInfoActivity.this, "Please Enter Mobile No.", 1).show();
                DoctorUpdateInfoActivity.this.d_editMobile.requestFocus();
            } else if (DoctorUpdateInfoActivity.this.d_editLoc.getText().toString().isEmpty()) {
                Toast.makeText(DoctorUpdateInfoActivity.this, "Please Enter Location Information.", 1).show();
                DoctorUpdateInfoActivity.this.d_editLoc.requestFocus();
            } else if (DoctorUpdateInfoActivity.this.d_editHosp.getText().toString().isEmpty()) {
                Toast.makeText(DoctorUpdateInfoActivity.this, "Please Enter Hospital Name.", 1).show();
                DoctorUpdateInfoActivity.this.d_editHosp.requestFocus();
            } else if (DoctorUpdateInfoActivity.this.doctorDBCtrl.updateDocInfo(DoctorUpdateInfoActivity.this.d_editAddress.getText().toString(), DoctorUpdateInfoActivity.this.d_editMobile.getText().toString(), DoctorUpdateInfoActivity.this.d_editLoc.getText().toString(), DoctorUpdateInfoActivity.this.d_editHosp.getText().toString(), DoctorUpdateInfoActivity.this.m_email)) {
                Toast.makeText(DoctorUpdateInfoActivity.this, "Information Updating Success!", 1).show();
                DoctorUpdateInfoActivity.this.finish();
            } else {
                Toast.makeText(DoctorUpdateInfoActivity.this, "Sorry Retry Information Updating.", 1).show();
            }
        }
    }

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView((int) C0211R.layout.doctor_updateprofile);
        this.doctorDBCtrl = new DoctorDBCtrl();
        this.m_email = new Intent(getIntent()).getStringExtra("Email");
        this.d_editAddress = (EditText) findViewById(C0211R.id.d_editAddress);
        this.d_editAddress.setText(this.doctorDBCtrl.getUpdateInfo(this.m_email).getDoctorItemRES_ADDRESS());
        this.d_editMobile = (EditText) findViewById(C0211R.id.d_editMobile);
        this.d_editMobile.setText(this.doctorDBCtrl.getUpdateInfo(this.m_email).getDoctorItemMOBILE_NO());
        this.d_editLoc = (EditText) findViewById(C0211R.id.d_editLoc);
        this.d_editLoc.setText(this.doctorDBCtrl.getUpdateInfo(this.m_email).getDoctorItemHOSP_CITY());
        this.d_editHosp = (EditText) findViewById(C0211R.id.d_editHosp);
        this.d_editHosp.setText(this.doctorDBCtrl.getUpdateInfo(this.m_email).getDoctorItemHOSP_NAME());
        this.d_btnSub = (Button) findViewById(C0211R.id.d_btnSub);
        this.d_btnSub.setOnClickListener(new C02251());
    }
}
