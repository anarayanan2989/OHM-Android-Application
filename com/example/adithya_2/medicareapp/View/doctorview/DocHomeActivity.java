package com.example.adithya_2.medicareapp.View.doctorview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.adithya_2.medicareapp.BuildConfig;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.AppointmentDBCtrl;
import com.example.adithya_2.medicareapp.Model.ConsultDBCtrl;
import com.example.adithya_2.medicareapp.Model.DoctorAppItemList;
import com.example.adithya_2.medicareapp.Model.PatientDBCtrl;
import com.example.adithya_2.medicareapp.View.MainActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DocHomeActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    String App_ID;
    String App_ID_2;
    String DocID;
    String Pat_ID;
    String Pat_ID_2;
    ArrayAdapter<String> adapter;
    Button btnDate;
    Calendar calendar;
    CalendarView calendarView;
    String date;
    DoctorAppItemList doctorAppItemList;
    List<DoctorAppItemList> doctorAppItemLists;
    EditText editDate;
    boolean isClicked;
    ArrayList<String> items;
    ListView listView;
    String m_date;
    String m_day;
    String m_email;
    String[] tmp_AppID;

    /* renamed from: com.example.adithya_2.medicareapp.View.doctorview.DocHomeActivity.1 */
    class C02141 implements OnClickListener {

        /* renamed from: com.example.adithya_2.medicareapp.View.doctorview.DocHomeActivity.1.1 */
        class C02131 implements OnItemClickListener {
            C02131() {
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("Selected", "Select");
                DocHomeActivity.this.Pat_ID_2 = ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(position)).getPatID();
                Intent intent = new Intent(DocHomeActivity.this, DoctorAppointmentActivity.class);
                intent.putExtra("PatID", DocHomeActivity.this.Pat_ID_2);
                DocHomeActivity.this.App_ID_2 = ConsultDBCtrl.getInstance().getAppID(DocHomeActivity.this.DocID, ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(position)).getTime1());
                intent.putExtra("App_ID", DocHomeActivity.this.App_ID_2);
                DocHomeActivity.this.doctorAppItemLists.clear();
                DocHomeActivity.this.startActivity(intent);
                DocHomeActivity.this.finish();
            }
        }

        C02141() {
        }

        public void onClick(View v) {
            DocHomeActivity.this.m_day = DocHomeActivity.this.editDate.getText().toString().trim();
            int i;
            if (DocHomeActivity.this.isClicked) {
                DocHomeActivity.this.calendarView.setVisibility(0);
                DocHomeActivity.this.isClicked = false;
                for (i = 0; i < ConsultDBCtrl.getInstance().getDateofDoctor(DocHomeActivity.this.DocID).size(); i++) {
                    StringBuilder stringBuilder = new StringBuilder();
                    DocHomeActivity docHomeActivity = DocHomeActivity.this;
                    docHomeActivity.date = stringBuilder.append(docHomeActivity.date).append(((String) ConsultDBCtrl.getInstance().getDateofDoctor(DocHomeActivity.this.DocID).get(i)).toString()).append(", ").toString();
                }
                Toast.makeText(DocHomeActivity.this, "You can only select " + DocHomeActivity.this.date, 1).show();
            } else if (!DocHomeActivity.this.isClicked) {
                DocHomeActivity.this.calendarView.setVisibility(4);
                DocHomeActivity.this.isClicked = true;
                Toast.makeText(DocHomeActivity.this, "It is " + DocHomeActivity.this.m_day + " which is " + DocHomeActivity.this.m_date, 1).show();
                Log.d("Date111", DocHomeActivity.this.m_date);
                if (!DocHomeActivity.this.m_day.isEmpty()) {
                    Log.d("Select", DocHomeActivity.this.editDate.getText().toString());
                    DocHomeActivity.this.items.clear();
                    DocHomeActivity.this.doctorAppItemLists = PatientDBCtrl.getInstance().getAppointmentByDate(DocHomeActivity.this.m_date);
                    if (DocHomeActivity.this.doctorAppItemLists != null) {
                        for (i = 0; i < DocHomeActivity.this.doctorAppItemLists.size(); i++) {
                            if (((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getReason() != null) {
                                DocHomeActivity.this.items.add(((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getPatID() + " " + ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getPatientLName() + " " + ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getPatientFName() + ":   " + ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getPat_DOB() + ":  " + ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getTime1() + ":   " + ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getStart_Time() + ":   " + ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getEnd_Time() + ":   " + ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getReason());
                            } else {
                                DocHomeActivity.this.items.add(((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getPatID() + " " + ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getPatientLName() + " " + ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getPatientFName() + ":  " + ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getPat_DOB() + ":  " + ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getTime1() + ":   " + ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getStart_Time() + ":   " + ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(i)).getEnd_Time() + ":   " + " ");
                            }
                        }
                    } else {
                        Toast.makeText(DocHomeActivity.this, "No Appointment", 1).show();
                    }
                    DocHomeActivity.this.listView.setAdapter(DocHomeActivity.this.adapter);
                    DocHomeActivity.this.listView.setChoiceMode(1);
                    DocHomeActivity.this.adapter.notifyDataSetChanged();
                    DocHomeActivity.this.listView.setOnItemClickListener(new C02131());
                }
            }
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.doctorview.DocHomeActivity.2 */
    class C02152 implements OnDateChangeListener {
        C02152() {
        }

        public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
            if (calendarView.getVisibility() == 0) {
                DocHomeActivity.this.m_date = DocHomeActivity.this.DateofCalendar(i, i1, i2);
                DocHomeActivity.this.editDate.setText("     " + DocHomeActivity.this.makeDate(i1 + 1).toString() + "/" + DocHomeActivity.this.makeDate(i2).toString() + "/" + DocHomeActivity.this.makeDate(i).toString());
                Log.d("Date333", DocHomeActivity.this.m_date);
            }
        }
    }

    /* renamed from: com.example.adithya_2.medicareapp.View.doctorview.DocHomeActivity.3 */
    class C02163 implements OnItemClickListener {
        C02163() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Log.d("Selected", "Select");
            DocHomeActivity.this.Pat_ID_2 = ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(position)).getPatID();
            Intent intent = new Intent(DocHomeActivity.this, DoctorAppointmentActivity.class);
            intent.putExtra("PatID", DocHomeActivity.this.Pat_ID_2);
            DocHomeActivity.this.App_ID_2 = ConsultDBCtrl.getInstance().getAppID(DocHomeActivity.this.DocID, ((DoctorAppItemList) DocHomeActivity.this.doctorAppItemLists.get(position)).getTime1());
            intent.putExtra("App_ID", DocHomeActivity.this.App_ID_2);
            DocHomeActivity.this.doctorAppItemLists.clear();
            DocHomeActivity.this.startActivity(intent);
            DocHomeActivity.this.finish();
        }
    }

    public DocHomeActivity() {
        this.m_email = null;
        this.m_day = null;
        this.date = BuildConfig.FLAVOR;
        this.isClicked = true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0211R.layout.activity_doc_home);
        Toolbar toolbar = (Toolbar) findViewById(C0211R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(C0211R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, C0211R.string.navigation_drawer_open, C0211R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ((NavigationView) findViewById(C0211R.id.nav_view)).setNavigationItemSelectedListener(this);
        this.m_email = new Intent(getIntent()).getStringExtra("Email");
        this.DocID = PatientDBCtrl.getInstance().getDocID(this.m_email);
        this.items = new ArrayList();
        this.editDate = (EditText) findViewById(C0211R.id.d_editDate);
        this.listView = (ListView) findViewById(C0211R.id.d_listView);
        this.adapter = new ArrayAdapter(this, 17367055, this.items);
        this.doctorAppItemList = new DoctorAppItemList();
        this.btnDate = (Button) findViewById(C0211R.id.d_btnDate);
        this.btnDate.setOnClickListener(new C02141());
        this.calendarView = (CalendarView) findViewById(C0211R.id.d_calendarView);
        this.calendarView.setVisibility(4);
        this.calendarView.setOnDateChangeListener(new C02152());
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(C0211R.id.drawer_layout);
        if (drawer.isDrawerOpen((int) GravityCompat.START)) {
            drawer.closeDrawer((int) GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0211R.menu.doc_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == C0211R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == C0211R.id.nav_appointment) {
            this.App_ID = ConsultDBCtrl.getInstance().getAppID(this.DocID);
            this.Pat_ID = AppointmentDBCtrl.getInstance().getPAT_ID(this.App_ID);
            this.doctorAppItemLists = PatientDBCtrl.getInstance().getAppointmentByDocID(this.DocID);
            if (this.doctorAppItemLists != null) {
                for (int i = 0; i < this.doctorAppItemLists.size(); i++) {
                    if (((DoctorAppItemList) this.doctorAppItemLists.get(i)).getReason() != null) {
                        this.items.add(((DoctorAppItemList) this.doctorAppItemLists.get(i)).getPatID() + " " + ((DoctorAppItemList) this.doctorAppItemLists.get(i)).getPatientLName() + " " + ((DoctorAppItemList) this.doctorAppItemLists.get(i)).getPatientFName() + ":   " + ((DoctorAppItemList) this.doctorAppItemLists.get(i)).getPat_DOB() + ":  " + ((DoctorAppItemList) this.doctorAppItemLists.get(i)).getTime1() + ":   " + ((DoctorAppItemList) this.doctorAppItemLists.get(i)).getStart_Time() + ":   " + ((DoctorAppItemList) this.doctorAppItemLists.get(i)).getEnd_Time() + ":   " + ((DoctorAppItemList) this.doctorAppItemLists.get(i)).getReason());
                    } else {
                        this.items.add(((DoctorAppItemList) this.doctorAppItemLists.get(i)).getPatID() + " " + ((DoctorAppItemList) this.doctorAppItemLists.get(i)).getPatientLName() + " " + ((DoctorAppItemList) this.doctorAppItemLists.get(i)).getPatientFName() + ":  " + ((DoctorAppItemList) this.doctorAppItemLists.get(i)).getPat_DOB() + ":  " + ((DoctorAppItemList) this.doctorAppItemLists.get(i)).getTime1() + ":   " + ((DoctorAppItemList) this.doctorAppItemLists.get(i)).getStart_Time() + ":   " + ((DoctorAppItemList) this.doctorAppItemLists.get(i)).getEnd_Time() + ":   " + " ");
                    }
                }
            } else {
                Toast.makeText(this, "No Appointment", 1).show();
            }
            this.listView.setAdapter(this.adapter);
            this.listView.setChoiceMode(1);
            this.adapter.notifyDataSetChanged();
            this.listView.setOnItemClickListener(new C02163());
        } else if (id == C0211R.id.nav_profile) {
            Intent intent = new Intent(this, DoctorUpdateInfoActivity.class);
            intent.putExtra("Email", this.m_email);
            startActivity(intent);
        } else if (id == C0211R.id.nav_logout) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (id == C0211R.id.nav_Empty) {
        }
        ((DrawerLayout) findViewById(C0211R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
        return true;
    }

    public String makeDate(int date) {
        if (date < 10) {
            return "0" + Integer.toString(date);
        }
        return Integer.toString(date);
    }

    public String DateofCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        switch (calendar.get(7)) {
            case ItemTouchHelper.UP /*1*/:
                return "Sunday";
            case ItemTouchHelper.DOWN /*2*/:
                return "Monday";
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                return "Tuesday";
            case ItemTouchHelper.LEFT /*4*/:
                return "Wednesday";
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                return "Thursday";
            case FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:
                return "Friday";
            case C0211R.styleable.Toolbar_contentInsetLeft /*7*/:
                return "Saturday";
            default:
                return null;
        }
    }
}
