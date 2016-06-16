package com.example.adithya_2.medicareapp.View.patientview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.example.adithya_2.medicareapp.C0211R;

public class PatientSpecActivity extends AppCompatActivity implements OnCheckedChangeListener {
    RadioButton first;
    RadioButton fourth;
    String m_data;
    RadioButton second;
    RadioGroup specGroup;
    RadioButton third;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0211R.layout.patient_special);
        this.specGroup = (RadioGroup) findViewById(C0211R.id.radioSpec);
        this.specGroup.clearCheck();
        this.specGroup.setOnCheckedChangeListener(this);
        this.first = (RadioButton) findViewById(C0211R.id.firstRadio);
        this.second = (RadioButton) findViewById(C0211R.id.secondRadio);
        this.third = (RadioButton) findViewById(C0211R.id.thirdRadio);
        this.fourth = (RadioButton) findViewById(C0211R.id.fourthRadio);
    }

    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (id) {
            case C0211R.id.firstRadio /*2131558746*/:
                this.m_data = this.first.getText().toString();
                break;
            case C0211R.id.secondRadio /*2131558747*/:
                this.m_data = this.second.getText().toString();
                break;
            case C0211R.id.thirdRadio /*2131558748*/:
                this.m_data = this.third.getText().toString();
                break;
            case C0211R.id.fourthRadio /*2131558749*/:
                this.m_data = this.fourth.getText().toString();
                break;
        }
        Intent intent = getIntent();
        intent.putExtra("data", this.m_data);
        setResult(-1, intent);
        finish();
    }
}
