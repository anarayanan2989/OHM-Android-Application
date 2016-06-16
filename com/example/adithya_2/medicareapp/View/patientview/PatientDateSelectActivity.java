package com.example.adithya_2.medicareapp.View.patientview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.example.adithya_2.medicareapp.C0211R;

public class PatientDateSelectActivity extends AppCompatActivity implements OnCheckedChangeListener {
    RadioButton Fri;
    RadioButton Mon;
    RadioButton Sat;
    RadioButton Sun;
    RadioButton Thu;
    RadioButton Tue;
    RadioButton Wed;
    RadioGroup dateGroup;
    String m_date;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView((int) C0211R.layout.patient_dateselect);
        this.dateGroup = (RadioGroup) findViewById(C0211R.id.radioGroupDate);
        this.dateGroup.clearCheck();
        this.dateGroup.setOnCheckedChangeListener(this);
        this.Mon = (RadioButton) findViewById(C0211R.id.radioMonday);
        this.Tue = (RadioButton) findViewById(C0211R.id.radioTue);
        this.Wed = (RadioButton) findViewById(C0211R.id.radioWedensday);
        this.Thu = (RadioButton) findViewById(C0211R.id.radioThursday);
        this.Fri = (RadioButton) findViewById(C0211R.id.radioFriday);
        this.Sat = (RadioButton) findViewById(C0211R.id.radioSat);
        this.Sun = (RadioButton) findViewById(C0211R.id.radioSun);
    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case C0211R.id.radioMonday /*2131558661*/:
                this.m_date = this.Mon.getText().toString();
                break;
            case C0211R.id.radioTue /*2131558662*/:
                this.m_date = this.Tue.getText().toString();
                break;
            case C0211R.id.radioWedensday /*2131558663*/:
                this.m_date = this.Wed.getText().toString();
                break;
            case C0211R.id.radioThursday /*2131558664*/:
                this.m_date = this.Thu.getText().toString();
                break;
            case C0211R.id.radioFriday /*2131558665*/:
                this.m_date = this.Fri.getText().toString();
                break;
            case C0211R.id.radioSat /*2131558666*/:
                this.m_date = this.Sat.getText().toString();
                break;
            case C0211R.id.radioSun /*2131558667*/:
                this.m_date = this.Sun.getText().toString();
                break;
        }
        Intent intent = getIntent();
        intent.putExtra("Day", this.m_date);
        setResult(-1, intent);
        finish();
    }
}
