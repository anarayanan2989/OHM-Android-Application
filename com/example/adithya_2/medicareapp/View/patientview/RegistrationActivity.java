package com.example.adithya_2.medicareapp.View.patientview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.PatientDBCtrl;
import com.example.adithya_2.medicareapp.Model.PatientItem;

public class RegistrationActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstantstate) {
        super.onCreate(savedInstantstate);
        setContentView((int) C0211R.layout.activity_register);
    }

    public void onRegisterClick(View view) {
        PatientDBCtrl pdbCtrl = new PatientDBCtrl();
        PatientItem pItem = new PatientItem();
        EditText Fname = (EditText) findViewById(C0211R.id.etFirstName);
        EditText Lname = (EditText) findViewById(C0211R.id.etLastName);
        EditText DOB = (EditText) findViewById(C0211R.id.etDOB);
        EditText Address1 = (EditText) findViewById(C0211R.id.etAddress1);
        EditText Address2 = (EditText) findViewById(C0211R.id.etAddress2);
        EditText City = (EditText) findViewById(C0211R.id.etCity);
        EditText State = (EditText) findViewById(C0211R.id.etState);
        EditText Zip = (EditText) findViewById(C0211R.id.etZip);
        EditText MobileNo = (EditText) findViewById(C0211R.id.etMobileNumber);
        EditText SSN = (EditText) findViewById(C0211R.id.etSSN);
        EditText Email = (EditText) findViewById(C0211R.id.etEmail);
        EditText Passwd = (EditText) findViewById(C0211R.id.etPassword);
        EditText ConfirmPasswd = (EditText) findViewById(C0211R.id.etCPassword);
        EditText Answer1 = (EditText) findViewById(C0211R.id.etNickName);
        EditText Answer2 = (EditText) findViewById(C0211R.id.etFavorite);
        if (!Passwd.getText().toString().equals(ConfirmPasswd.getText().toString())) {
            Toast.makeText(this, "Password doesn't match!", 0).show();
        } else if (Passwd.getText().toString().length() >= 6 || ConfirmPasswd.getText().toString().length() >= 6) {
            pItem.setPatientItemF_NAME(Fname.getText().toString());
            pItem.setPatientItemL_NAME(Lname.getText().toString());
            pItem.setPatientItemDOB(DOB.getText().toString());
            pItem.setPatientItemADDRESS(Address1.getText().toString());
            pItem.setPatientItemCITY(City.getText().toString());
            pItem.setPatientItemSTATE(State.getText().toString());
            pItem.setPatientItemZIP(Zip.getText().toString());
            pItem.setPatientItemMOBILE_NO(MobileNo.getText().toString());
            pItem.setPatientItemSSN(SSN.getText().toString());
            pItem.setPatientItemEMAIL(Email.getText().toString());
            pItem.setPatientItemP_WORD(Passwd.getText().toString());
            pItem.setPatientItemANSWER1(Answer1.getText().toString());
            pItem.setPatientItemANSWER2(Answer2.getText().toString());
            pdbCtrl.insertPatient(pItem);
            Toast.makeText(this, "Register Success!", 1).show();
            finish();
        } else {
            Toast.makeText(this, "Please enter atleast more than 5 characters for Password", 1).show();
        }
    }
}
