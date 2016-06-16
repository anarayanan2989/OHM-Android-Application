package com.example.adithya_2.medicareapp.View.doctorview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.adithya_2.medicareapp.BuildConfig;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.DoctorDBCtrl;

public class DoctorPasswordChangeAcivity extends AppCompatActivity {
    Button btnCancel;
    Button btnOk;
    EditText confirmPass;
    EditText email;
    EditText newpass;

    /* renamed from: com.example.adithya_2.medicareapp.View.doctorview.DoctorPasswordChangeAcivity.1 */
    class C02231 implements OnClickListener {
        final /* synthetic */ DoctorDBCtrl val$doctorDBCtrl;

        C02231(DoctorDBCtrl doctorDBCtrl) {
            this.val$doctorDBCtrl = doctorDBCtrl;
        }

        public void onClick(View v) {
            if (DoctorPasswordChangeAcivity.this.email.getText().toString().equals(null)) {
                Toast.makeText(DoctorPasswordChangeAcivity.this, "Please enter your email.", 1).show();
            } else if (DoctorPasswordChangeAcivity.this.newpass.getText().toString().equals(null)) {
                Toast.makeText(DoctorPasswordChangeAcivity.this, "Please enter your new password.", 1).show();
            } else if (DoctorPasswordChangeAcivity.this.confirmPass.getText().toString().equals(null)) {
                Toast.makeText(DoctorPasswordChangeAcivity.this, "Please enter your confirm password.", 1).show();
            } else if (DoctorPasswordChangeAcivity.this.newpass.getText().toString().length() < 6) {
                Toast.makeText(DoctorPasswordChangeAcivity.this, "Password length must be more 6 lettes.", 1).show();
                DoctorPasswordChangeAcivity.this.newpass.setText(BuildConfig.FLAVOR);
                DoctorPasswordChangeAcivity.this.confirmPass.setText(BuildConfig.FLAVOR);
            } else if (DoctorPasswordChangeAcivity.this.confirmPass.getText().toString().length() < 6) {
                Toast.makeText(DoctorPasswordChangeAcivity.this, "Password length must be more 6 lettes.", 1).show();
                DoctorPasswordChangeAcivity.this.newpass.setText(BuildConfig.FLAVOR);
                DoctorPasswordChangeAcivity.this.confirmPass.setText(BuildConfig.FLAVOR);
            } else if (!DoctorPasswordChangeAcivity.this.newpass.getText().toString().equals(DoctorPasswordChangeAcivity.this.confirmPass.getText().toString())) {
                Toast.makeText(DoctorPasswordChangeAcivity.this, "Sorry No match Password.Please Retry", 1).show();
                DoctorPasswordChangeAcivity.this.email.setText(BuildConfig.FLAVOR);
                DoctorPasswordChangeAcivity.this.newpass.setText(BuildConfig.FLAVOR);
                DoctorPasswordChangeAcivity.this.confirmPass.setText(BuildConfig.FLAVOR);
            } else if (this.val$doctorDBCtrl.changePassDoctor(DoctorPasswordChangeAcivity.this.email.getText().toString(), DoctorPasswordChangeAcivity.this.newpass.getText().toString()) > 0) {
                Toast.makeText(DoctorPasswordChangeAcivity.this, "Success updating password.", 1).show();
                DoctorPasswordChangeAcivity.this.finish();
                DoctorPasswordChangeAcivity.this.email.setText(BuildConfig.FLAVOR);
                DoctorPasswordChangeAcivity.this.newpass.setText(BuildConfig.FLAVOR);
                DoctorPasswordChangeAcivity.this.confirmPass.setText(BuildConfig.FLAVOR);
            } else {
                Toast.makeText(DoctorPasswordChangeAcivity.this, "Sorry updating failed. Please check Email.", 1).show();
                DoctorPasswordChangeAcivity.this.email.setText(BuildConfig.FLAVOR);
                DoctorPasswordChangeAcivity.this.newpass.setText(BuildConfig.FLAVOR);
                DoctorPasswordChangeAcivity.this.confirmPass.setText(BuildConfig.FLAVOR);
            }
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.doctorview.DoctorPasswordChangeAcivity.2 */
    class C02242 implements OnClickListener {
        C02242() {
        }

        public void onClick(View v) {
            DoctorPasswordChangeAcivity.this.email.setText(BuildConfig.FLAVOR);
            DoctorPasswordChangeAcivity.this.newpass.setText(BuildConfig.FLAVOR);
            DoctorPasswordChangeAcivity.this.confirmPass.setText(BuildConfig.FLAVOR);
            DoctorPasswordChangeAcivity.this.finish();
        }
    }

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView((int) C0211R.layout.doctor_passchange);
        DoctorDBCtrl doctorDBCtrl = new DoctorDBCtrl();
        this.email = (EditText) findViewById(C0211R.id.d_changeEmail);
        this.newpass = (EditText) findViewById(C0211R.id.d_changeNewPass);
        this.confirmPass = (EditText) findViewById(C0211R.id.d_confimchangePass);
        this.btnOk = (Button) findViewById(C0211R.id.d_btnChangeOk);
        this.btnOk.setOnClickListener(new C02231(doctorDBCtrl));
        this.btnCancel = (Button) findViewById(C0211R.id.btnChaneCancel);
        this.btnCancel.setOnClickListener(new C02242());
    }
}
