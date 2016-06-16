package com.example.adithya_2.medicareapp.View.patientview;

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
import android.widget.ImageView;
import android.widget.Toast;
import com.example.adithya_2.medicareapp.BuildConfig;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.PatientDBCtrl;
import com.example.adithya_2.medicareapp.Model.PatientItem;

public class PatientFragment extends Fragment {
    String m_Email;
    String m_PATIENT_ID;
    String m_Pass;
    boolean m_login;
    EditText p_email;
    Button p_login;
    EditText p_passwd;
    PatientDBCtrl patientDBCtrl;
    PatientItem patientItem;
    View rootView;
    ImageView usericon;
    int wrong_times;

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientFragment.1 */
    class C02281 implements OnClickListener {
        C02281() {
        }

        public void onClick(View v) {
            Toast.makeText(PatientFragment.this.getActivity(), "Please enter required information for account creation!!", 1).show();
            PatientFragment.this.startActivity(new Intent(PatientFragment.this.getActivity(), RegistrationActivity.class));
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientFragment.2 */
    class C02312 implements OnClickListener {

        /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientFragment.2.1 */
        class C02291 implements DialogInterface.OnClickListener {
            C02291() {
            }

            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        }

        /* renamed from: com.example.adithya_2.medicareapp.View.patientview.PatientFragment.2.2 */
        class C02302 implements DialogInterface.OnClickListener {
            C02302() {
            }

            public void onClick(DialogInterface dialog, int whichButton) {
                PatientFragment.this.startActivity(new Intent(PatientFragment.this.getActivity(), PatientChangePassActivity.class));
            }
        }

        C02312() {
        }

        public void onClick(View v) {
            PatientFragment.this.m_Email = PatientFragment.this.p_email.getText().toString();
            PatientFragment.this.m_Pass = PatientFragment.this.p_passwd.getText().toString();
            PatientFragment.this.m_PATIENT_ID = PatientFragment.this.patientDBCtrl.getPATID(PatientFragment.this.m_Email);
            if (PatientFragment.this.m_Email.isEmpty()) {
                PatientFragment.this.p_email.requestFocus();
                Toast.makeText(PatientFragment.this.getActivity(), "Please enter your Email.", 1).show();
            } else if (PatientFragment.this.m_Pass.isEmpty()) {
                PatientFragment.this.p_passwd.requestFocus();
                Toast.makeText(PatientFragment.this.getActivity(), "Please enter your Password.", 1).show();
            } else {
                PatientFragment.this.m_login = PatientFragment.this.patientDBCtrl.getPatientItemLogin(PatientFragment.this.m_Email, PatientFragment.this.m_Pass);
                if (!PatientFragment.this.m_login) {
                    PatientFragment.this.p_email.setText(BuildConfig.FLAVOR);
                    PatientFragment.this.p_passwd.setText(BuildConfig.FLAVOR);
                    PatientFragment.this.p_email.requestFocus();
                    Toast.makeText(PatientFragment.this.getActivity(), "Your Email or Password is not correct. Please Enter Email or Password.", 1).show();
                    PatientFragment patientFragment = PatientFragment.this;
                    patientFragment.wrong_times++;
                    if (PatientFragment.this.wrong_times == 3) {
                        PatientFragment.this.wrong_times = 0;
                        Builder builder = new Builder(PatientFragment.this.getActivity());
                        builder.setTitle("Forget Password").setMessage("Did you forget password?").setCancelable(false).setPositiveButton("Yes", new C02302()).setNegativeButton("No", new C02291());
                        builder.create().show();
                    }
                } else if (PatientFragment.this.m_login) {
                    Toast.makeText(PatientFragment.this.getActivity(), "Login Success", 1).show();
                    Intent homeIntent = new Intent(PatientFragment.this.getActivity(), PatientHomeActivity.class);
                    homeIntent.putExtra("PatID", PatientFragment.this.m_PATIENT_ID);
                    PatientFragment.this.startActivity(homeIntent);
                    PatientFragment.this.p_email.setText(BuildConfig.FLAVOR);
                    PatientFragment.this.p_passwd.setText(BuildConfig.FLAVOR);
                }
            }
        }
    }

    public PatientFragment() {
        this.patientItem = null;
        this.patientDBCtrl = new PatientDBCtrl();
        this.wrong_times = 0;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(C0211R.layout.fragment_patient, container, false);
        return this.rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.p_email = (EditText) this.rootView.findViewById(C0211R.id.editEmail);
        this.p_passwd = (EditText) this.rootView.findViewById(C0211R.id.editPass);
        this.p_email.requestFocus();
        ((ImageView) getView().findViewById(C0211R.id.usericon)).setOnClickListener(new C02281());
        ((Button) getView().findViewById(C0211R.id.d_login)).setOnClickListener(new C02312());
    }
}
