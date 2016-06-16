package com.example.adithya_2.medicareapp.View.patientview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.adithya_2.medicareapp.BuildConfig;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.PatientDBCtrl;

public class PatientChangePassActivity extends AppCompatActivity {
    Button btnCancel;
    Button btnOk;
    EditText confirmPass;
    EditText editFavFood;
    EditText editNickName;
    EditText email;
    EditText newpass;

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientChangePassActivity.1 */
    class C02261 implements OnClickListener {
        final /* synthetic */ PatientDBCtrl val$patientDBCtrl;

        C02261(PatientDBCtrl patientDBCtrl) {
            this.val$patientDBCtrl = patientDBCtrl;
        }

        public void onClick(View v) {
            if (PatientChangePassActivity.this.email.getText().toString().equals(null)) {
                Toast.makeText(PatientChangePassActivity.this, "Please enter your email.", 1).show();
            } else if (PatientChangePassActivity.this.newpass.getText().toString().equals(null)) {
                Toast.makeText(PatientChangePassActivity.this, "Please enter your new password.", 1).show();
            } else if (PatientChangePassActivity.this.confirmPass.getText().toString().equals(null)) {
                Toast.makeText(PatientChangePassActivity.this, "Please enter your confirm password.", 1).show();
            } else if (PatientChangePassActivity.this.newpass.getText().toString().length() < 6) {
                Toast.makeText(PatientChangePassActivity.this, "Password length must be more 6 lettes.", 1).show();
                PatientChangePassActivity.this.email.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.newpass.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.confirmPass.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.editNickName.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.editFavFood.setText(BuildConfig.FLAVOR);
            } else if (PatientChangePassActivity.this.confirmPass.getText().toString().length() < 6) {
                Toast.makeText(PatientChangePassActivity.this, "Password length must be more 6 lettes.", 1).show();
                PatientChangePassActivity.this.email.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.newpass.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.confirmPass.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.editNickName.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.editFavFood.setText(BuildConfig.FLAVOR);
            } else if (!PatientChangePassActivity.this.newpass.getText().toString().equals(PatientChangePassActivity.this.confirmPass.getText().toString())) {
                Toast.makeText(PatientChangePassActivity.this, "Sorry No match Password.Please Retry", 1).show();
                PatientChangePassActivity.this.email.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.newpass.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.confirmPass.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.editNickName.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.editFavFood.setText(BuildConfig.FLAVOR);
            } else if (!this.val$patientDBCtrl.checkNickAndFood(PatientChangePassActivity.this.editNickName.getText().toString(), PatientChangePassActivity.this.editFavFood.getText().toString(), PatientChangePassActivity.this.email.getText().toString())) {
                Toast.makeText(PatientChangePassActivity.this, "Wrong NickName or Favorite Food.", 1).show();
                PatientChangePassActivity.this.email.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.newpass.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.confirmPass.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.editNickName.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.editFavFood.setText(BuildConfig.FLAVOR);
            } else if (this.val$patientDBCtrl.changePass(PatientChangePassActivity.this.email.getText().toString(), PatientChangePassActivity.this.newpass.getText().toString()) > 0) {
                Toast.makeText(PatientChangePassActivity.this, "Success updating password.", 1).show();
                PatientChangePassActivity.this.finish();
                PatientChangePassActivity.this.email.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.newpass.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.confirmPass.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.editNickName.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.editFavFood.setText(BuildConfig.FLAVOR);
            } else {
                Toast.makeText(PatientChangePassActivity.this, "Sorry updating failed. Please check Email.", 1).show();
                PatientChangePassActivity.this.email.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.newpass.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.confirmPass.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.editNickName.setText(BuildConfig.FLAVOR);
                PatientChangePassActivity.this.editFavFood.setText(BuildConfig.FLAVOR);
            }
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientChangePassActivity.2 */
    class C02272 implements OnClickListener {
        C02272() {
        }

        public void onClick(View v) {
            PatientChangePassActivity.this.email.setText(BuildConfig.FLAVOR);
            PatientChangePassActivity.this.newpass.setText(BuildConfig.FLAVOR);
            PatientChangePassActivity.this.confirmPass.setText(BuildConfig.FLAVOR);
            PatientChangePassActivity.this.editNickName.setText(BuildConfig.FLAVOR);
            PatientChangePassActivity.this.editFavFood.setText(BuildConfig.FLAVOR);
            PatientChangePassActivity.this.finish();
        }
    }

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView((int) C0211R.layout.patient_changepass);
        PatientDBCtrl patientDBCtrl = new PatientDBCtrl();
        this.email = (EditText) findViewById(C0211R.id.editChangeEmail);
        this.newpass = (EditText) findViewById(C0211R.id.editChangeNewPass);
        this.confirmPass = (EditText) findViewById(C0211R.id.editChangeConfirmPass);
        this.editNickName = (EditText) findViewById(C0211R.id.editNickName);
        this.editFavFood = (EditText) findViewById(C0211R.id.editFavFood);
        this.btnOk = (Button) findViewById(C0211R.id.btnChangeOk);
        this.btnOk.setOnClickListener(new C02261(patientDBCtrl));
        this.btnCancel = (Button) findViewById(C0211R.id.btnChaneCancel);
        this.btnCancel.setOnClickListener(new C02272());
    }
}
