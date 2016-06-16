package com.example.adithya_2.medicareapp.View.doctorview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.adithya_2.medicareapp.BuildConfig;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.AppointmentDBCtrl;
import com.example.adithya_2.medicareapp.Model.AppointmentItem;
import java.util.List;

public class DoctorAppointmentActivity extends AppCompatActivity {
    AppointmentDBCtrl appointmentDBCtrl;
    AppointmentItem appointmentItem;
    Button d_btnDetail;
    TextView d_curEditAppID;
    EditText d_curEditComments;
    TextView d_curEditPrescription;
    TextView d_curEditStatus;
    TextView d_editAppID;
    TextView d_editComments;
    TextView d_editPrescription;
    TextView editStatus;
    String m_AppID;
    List<String> m_AppIDList;
    String patID;

    /* renamed from: com.example.adithya_2.medicareapp.View.doctorview.DoctorAppointmentActivity.1 */
    class C02171 implements OnClickListener {
        C02171() {
        }

        public void onClick(View v) {
            String Comments = DoctorAppointmentActivity.this.d_curEditComments.getText().toString();
            String Precription = DoctorAppointmentActivity.this.d_curEditPrescription.getText().toString();
            if (Comments.equals(BuildConfig.FLAVOR) || Precription.equals(BuildConfig.FLAVOR)) {
                Toast.makeText(DoctorAppointmentActivity.this, "Please Enter Prescription or Comments.", 0).show();
            } else if (new AppointmentDBCtrl().updateComments(DoctorAppointmentActivity.this.m_AppID, Comments, Precription) > 0) {
                Toast.makeText(DoctorAppointmentActivity.this, "Registering Comments Success!", 1).show();
                Intent intent1 = new Intent(DoctorAppointmentActivity.this, DoctorMedicineInfoActivity.class);
                intent1.putExtra("App_ID", DoctorAppointmentActivity.this.m_AppID);
                DoctorAppointmentActivity.this.startActivity(intent1);
            } else {
                Toast.makeText(DoctorAppointmentActivity.this, "Sorry, Retry.", 1).show();
            }
        }
    }

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView((int) C0211R.layout.doctor_appointment);
        Intent intent = new Intent(getIntent());
        this.m_AppID = intent.getStringExtra("App_ID");
        this.patID = intent.getStringExtra("PatID");
        this.appointmentDBCtrl = new AppointmentDBCtrl();
        this.m_AppIDList = this.appointmentDBCtrl.getPreviousAPPID(this.patID);
        if (this.m_AppIDList.size() > 2) {
            this.appointmentItem = this.appointmentDBCtrl.getAppData((String) this.m_AppIDList.get(this.m_AppIDList.size() - 2));
            this.d_editPrescription = (TextView) findViewById(C0211R.id.d_editPrescription);
            this.d_editComments = (TextView) findViewById(C0211R.id.d_editComments);
            this.d_editPrescription.setText(this.appointmentItem.getApp_PRESCRIPTION());
            this.d_editComments.setText(this.appointmentItem.getApp_COMMENTS());
        }
        this.d_curEditPrescription = (EditText) findViewById(C0211R.id.d_curEditPrescription);
        this.d_curEditComments = (EditText) findViewById(C0211R.id.d_cureditComments);
        this.d_btnDetail = (Button) findViewById(C0211R.id.d_btnDetail);
        this.d_btnDetail.setOnClickListener(new C02171());
    }
}
