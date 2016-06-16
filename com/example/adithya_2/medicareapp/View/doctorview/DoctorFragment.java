package com.example.adithya_2.medicareapp.View.doctorview;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.adithya_2.medicareapp.BuildConfig;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.DoctorDBCtrl;
import com.example.adithya_2.medicareapp.View.patientview.PatientChangePassActivity;

public class DoctorFragment extends Fragment {
    EditText d_email;
    Button d_login;
    EditText d_pass;
    DoctorDBCtrl doctorDBCtrl;
    String m_email;
    String m_pass;
    View rootView;
    int wrong_times;

    /* renamed from: com.example.adithya_2.medicareapp.View.doctorview.DoctorFragment.1 */
    class C02201 implements OnClickListener {

        /* renamed from: com.example.adithya_2.medicareapp.View.doctorview.DoctorFragment.1.1 */
        class C02181 implements DialogInterface.OnClickListener {
            C02181() {
            }

            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        }

        /* renamed from: com.example.adithya_2.medicareapp.View.doctorview.DoctorFragment.1.2 */
        class C02192 implements DialogInterface.OnClickListener {
            C02192() {
            }

            public void onClick(DialogInterface dialog, int whichButton) {
                DoctorFragment.this.startActivity(new Intent(DoctorFragment.this.getActivity(), PatientChangePassActivity.class));
            }
        }

        C02201() {
        }

        public void onClick(View v) {
            DoctorFragment.this.m_pass = DoctorFragment.this.d_pass.getText().toString();
            DoctorFragment.this.m_email = DoctorFragment.this.d_email.getText().toString();
            if (DoctorFragment.this.m_email.isEmpty()) {
                DoctorFragment.this.d_email.requestFocus();
                Toast.makeText(DoctorFragment.this.getContext(), "Please enter your Email.", 1).show();
            } else if (DoctorFragment.this.m_pass.isEmpty()) {
                DoctorFragment.this.d_pass.requestFocus();
                Toast.makeText(DoctorFragment.this.getContext(), "Please enter your Password.", 1).show();
            } else if (DoctorFragment.this.doctorDBCtrl.isDoctor(DoctorFragment.this.m_email, DoctorFragment.this.m_pass)) {
                DoctorFragment.this.d_email.setText(BuildConfig.FLAVOR);
                DoctorFragment.this.d_pass.setText(BuildConfig.FLAVOR);
                Toast.makeText(DoctorFragment.this.getContext(), "Welcome! Doctor Login Success!", 1).show();
                Intent intent1 = new Intent(DoctorFragment.this.getActivity(), DocHomeActivity.class);
                intent1.putExtra("Email", DoctorFragment.this.m_email);
                DoctorFragment.this.startActivity(intent1);
            } else {
                Toast.makeText(DoctorFragment.this.getContext(), "Password or Email is not correct.Pleae retry.", 1).show();
                DoctorFragment.this.d_email.setText(BuildConfig.FLAVOR);
                DoctorFragment.this.d_pass.setText(BuildConfig.FLAVOR);
                DoctorFragment.this.d_email.requestFocus();
                DoctorFragment doctorFragment = DoctorFragment.this;
                doctorFragment.wrong_times++;
                if (DoctorFragment.this.wrong_times == 3) {
                    DoctorFragment.this.wrong_times = 0;
                    Builder builder = new Builder(DoctorFragment.this.getActivity());
                    builder.setTitle("Forget Password").setMessage("Did you forget password?").setCancelable(false).setPositiveButton("Yes", new C02192()).setNegativeButton("No", new C02181());
                    builder.create().show();
                }
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(C0211R.layout.fragment_doctor, container, false);
        this.rootView = inflate;
        return inflate;
    }

    public void onActivityCreated(Bundle saveInstance) {
        super.onActivityCreated(saveInstance);
        this.doctorDBCtrl = new DoctorDBCtrl();
        this.d_email = (EditText) this.rootView.findViewById(C0211R.id.d_editEmail);
        this.d_pass = (EditText) this.rootView.findViewById(C0211R.id.editPass);
        this.d_login = (Button) this.rootView.findViewById(C0211R.id.d_login);
        this.d_login.setOnClickListener(new C02201());
    }
}
