package com.example.adithya_2.medicareapp.View.patientview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.example.adithya_2.medicareapp.C0211R;

public class PatientPayActivity extends AppCompatActivity {
    Button btnPayByCard;
    Button btnPayInsur;
    EditText editTotal;

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientPayActivity.1 */
    class C02321 implements OnClickListener {
        C02321() {
        }

        public void onClick(View v) {
            PatientPayActivity.this.startActivity(new Intent(PatientPayActivity.this, PatientPayByCardActivity.class));
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientPayActivity.2 */
    class C02332 implements OnClickListener {
        C02332() {
        }

        public void onClick(View v) {
            PatientPayActivity.this.startActivity(new Intent(PatientPayActivity.this, PatientPayByInsurActivity.class));
        }
    }

    public void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        setContentView((int) C0211R.layout.patient_pay);
        this.editTotal = (EditText) findViewById(C0211R.id.editTotal);
        this.btnPayByCard = (Button) findViewById(C0211R.id.btnPayCard);
        this.btnPayByCard.setOnClickListener(new C02321());
        this.btnPayInsur = (Button) findViewById(C0211R.id.btnIns);
        this.btnPayInsur.setOnClickListener(new C02332());
    }
}
