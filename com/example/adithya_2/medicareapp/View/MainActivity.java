package com.example.adithya_2.medicareapp.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.example.adithya_2.medicareapp.BuildConfig;
import com.example.adithya_2.medicareapp.C0211R;
import com.example.adithya_2.medicareapp.Model.ConsultDBCtrl;
import com.example.adithya_2.medicareapp.Model.ConsultItem;
import com.example.adithya_2.medicareapp.Model.DoctorDBCtrl;
import com.example.adithya_2.medicareapp.Model.DoctorItem;
import com.example.adithya_2.medicareapp.View.doctorview.DoctorFragment;
import com.example.adithya_2.medicareapp.View.patientview.PatientFragment;

public class MainActivity extends FragmentActivity {
    static int start;
    int app_i;
    int con_i;
    ConsultDBCtrl consultDBCtrl;
    ConsultItem[] consultItem;
    int doc_i;
    DoctorDBCtrl doctorDBCtrl;
    DoctorItem[] doctorItem;
    ViewPager viewPager;

    public class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            switch (position) {
                case ItemTouchHelper.ACTION_STATE_IDLE /*0*/:
                    return new PatientFragment();
                case ItemTouchHelper.UP /*1*/:
                    return new DoctorFragment();
                default:
                    return null;
            }
        }

        public int getCount() {
            return 2;
        }
    }

    public MainActivity() {
        this.doctorItem = new DoctorItem[8];
        this.doctorDBCtrl = new DoctorDBCtrl();
        this.consultItem = new ConsultItem[21];
        this.consultDBCtrl = new ConsultDBCtrl();
    }

    static {
        start = 0;
    }

    protected void onCreate(Bundle savedInstanceState) {
        start++;
        super.onCreate(savedInstanceState);
        setContentView(C0211R.layout.activity_main);
        this.viewPager = (ViewPager) findViewById(C0211R.id.pager);
        this.viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        if (this.doctorDBCtrl.isNotValue()) {
            this.doc_i = 0;
            while (this.doc_i < 7) {
                this.doctorItem[this.doc_i] = new DoctorItem();
                initialDoctorData(this.doc_i);
                this.doctorDBCtrl.insertDoctor(this.doctorItem[this.doc_i]);
                this.doc_i++;
            }
            this.con_i = 0;
            while (this.con_i < 21) {
                this.consultItem[this.con_i] = new ConsultItem();
                initialConsult(this.con_i);
                this.consultDBCtrl.insertConsult(this.consultItem[this.con_i]);
                this.con_i++;
            }
        }
    }

    public void initialDoctorData(int doc_i) {
        if (doc_i == 0) {
            this.doctorItem[doc_i].setDoctorItemF_NAME("Adam");
            this.doctorItem[doc_i].setDoctorItemL_NAME("Scott");
            this.doctorItem[doc_i].setDoctorItemDOB("2/3/1972");
            this.doctorItem[doc_i].setDoctorItemRES_ADDRESS("1160 Park Avenue, Apt#26,Houston,Texas");
            this.doctorItem[doc_i].setDoctorItemMOBILE_NO("5162091269");
            this.doctorItem[doc_i].setDoctorItemSPECIALIZATION("Cardiologist");
            this.doctorItem[doc_i].setDoctorItemRESUME(BuildConfig.FLAVOR);
            this.doctorItem[doc_i].setDoctorItemHOSP_NAME("Elizabeth");
            this.doctorItem[doc_i].setDoctorItemHOSP_CITY("Houston");
            this.doctorItem[doc_i].setDoctorItemEMAIL("1");
            this.doctorItem[doc_i].setDoctorItemP_WORD("1");
            this.doctorItem[doc_i].setDoctorItemANSWER1("adam");
            this.doctorItem[doc_i].setDoctorItemANSWER2("Chicken Burger");
        } else if (doc_i == 1) {
            this.doctorItem[doc_i].setDoctorItemF_NAME("Mark");
            this.doctorItem[doc_i].setDoctorItemL_NAME("Walberg");
            this.doctorItem[doc_i].setDoctorItemDOB("12/6/1982");
            this.doctorItem[doc_i].setDoctorItemRES_ADDRESS("1010 South Cooper Street, Dallas, Texas");
            this.doctorItem[doc_i].setDoctorItemMOBILE_NO("6824579910");
            this.doctorItem[doc_i].setDoctorItemSPECIALIZATION("Cardiologist");
            this.doctorItem[doc_i].setDoctorItemRESUME(BuildConfig.FLAVOR);
            this.doctorItem[doc_i].setDoctorItemHOSP_NAME("Britainnia");
            this.doctorItem[doc_i].setDoctorItemHOSP_CITY("Dallas");
            this.doctorItem[doc_i].setDoctorItemEMAIL("Mark123@britainna.org");
            this.doctorItem[doc_i].setDoctorItemP_WORD("mw12345");
            this.doctorItem[doc_i].setDoctorItemANSWER1("markie");
            this.doctorItem[doc_i].setDoctorItemANSWER2("Pepperoni Pizza");
        } else if (doc_i == 2) {
            this.doctorItem[doc_i].setDoctorItemF_NAME("Tim");
            this.doctorItem[doc_i].setDoctorItemL_NAME("Southee");
            this.doctorItem[doc_i].setDoctorItemDOB("3/4/1980");
            this.doctorItem[doc_i].setDoctorItemRES_ADDRESS("1269, Oregon Ave, New York, New York");
            this.doctorItem[doc_i].setDoctorItemMOBILE_NO("8762123323");
            this.doctorItem[doc_i].setDoctorItemSPECIALIZATION("Dentist");
            this.doctorItem[doc_i].setDoctorItemRESUME(BuildConfig.FLAVOR);
            this.doctorItem[doc_i].setDoctorItemHOSP_NAME("Lincon");
            this.doctorItem[doc_i].setDoctorItemHOSP_CITY("New York");
            this.doctorItem[doc_i].setDoctorItemEMAIL("timsouthee@lincon.org");
            this.doctorItem[doc_i].setDoctorItemP_WORD("ts12345");
            this.doctorItem[doc_i].setDoctorItemANSWER1("timmy");
            this.doctorItem[doc_i].setDoctorItemANSWER2("Tasso Ham");
        } else if (doc_i == 3) {
            this.doctorItem[doc_i].setDoctorItemF_NAME("Brand");
            this.doctorItem[doc_i].setDoctorItemL_NAME("Macullum");
            this.doctorItem[doc_i].setDoctorItemDOB("7/9/1963");
            this.doctorItem[doc_i].setDoctorItemRES_ADDRESS("1231, E Mokingbird Ln, Dallas");
            this.doctorItem[doc_i].setDoctorItemMOBILE_NO("6571239098");
            this.doctorItem[doc_i].setDoctorItemSPECIALIZATION("Dentist");
            this.doctorItem[doc_i].setDoctorItemRESUME(BuildConfig.FLAVOR);
            this.doctorItem[doc_i].setDoctorItemHOSP_NAME("Dallas Methodist");
            this.doctorItem[doc_i].setDoctorItemHOSP_CITY("Dallas");
            this.doctorItem[doc_i].setDoctorItemEMAIL("brandmacullum@methodist.org");
            this.doctorItem[doc_i].setDoctorItemP_WORD("bm12345");
            this.doctorItem[doc_i].setDoctorItemANSWER1("brandy");
            this.doctorItem[doc_i].setDoctorItemANSWER2("Barbecue Ribs");
        } else if (doc_i == 4) {
            this.doctorItem[doc_i].setDoctorItemF_NAME("Shane");
            this.doctorItem[doc_i].setDoctorItemL_NAME("Warne");
            this.doctorItem[doc_i].setDoctorItemDOB("9/2/1965");
            this.doctorItem[doc_i].setDoctorItemRES_ADDRESS("7821, Baylor Ave, Dallas");
            this.doctorItem[doc_i].setDoctorItemMOBILE_NO("8762127676");
            this.doctorItem[doc_i].setDoctorItemSPECIALIZATION("Gynchologist");
            this.doctorItem[doc_i].setDoctorItemRESUME(BuildConfig.FLAVOR);
            this.doctorItem[doc_i].setDoctorItemHOSP_NAME("Saton Medical Center");
            this.doctorItem[doc_i].setDoctorItemHOSP_CITY("San Fracisco");
            this.doctorItem[doc_i].setDoctorItemEMAIL("shane.warne@saton.org");
            this.doctorItem[doc_i].setDoctorItemP_WORD("sw12345");
            this.doctorItem[doc_i].setDoctorItemANSWER1("wanry");
            this.doctorItem[doc_i].setDoctorItemANSWER2("Pastrami on Rye");
        } else if (doc_i == 5) {
            this.doctorItem[doc_i].setDoctorItemF_NAME("Lans");
            this.doctorItem[doc_i].setDoctorItemL_NAME("Armstrong");
            this.doctorItem[doc_i].setDoctorItemDOB("3/2/1981");
            this.doctorItem[doc_i].setDoctorItemRES_ADDRESS("7425, Crescent Street, Dallas");
            this.doctorItem[doc_i].setDoctorItemMOBILE_NO("8782122334");
            this.doctorItem[doc_i].setDoctorItemSPECIALIZATION("Gynchologist");
            this.doctorItem[doc_i].setDoctorItemRESUME(BuildConfig.FLAVOR);
            this.doctorItem[doc_i].setDoctorItemHOSP_NAME("Trinity Mother Frances Hospital");
            this.doctorItem[doc_i].setDoctorItemHOSP_CITY("Beaumont");
            this.doctorItem[doc_i].setDoctorItemEMAIL("lans.armstrong@trinity.org");
            this.doctorItem[doc_i].setDoctorItemP_WORD("la12345");
            this.doctorItem[doc_i].setDoctorItemANSWER1("triny");
            this.doctorItem[doc_i].setDoctorItemANSWER2("Frito Pie");
        } else if (doc_i == 6) {
            this.doctorItem[doc_i].setDoctorItemF_NAME("Rohit");
            this.doctorItem[doc_i].setDoctorItemL_NAME("Sharma");
            this.doctorItem[doc_i].setDoctorItemDOB("10/12/1980");
            this.doctorItem[doc_i].setDoctorItemRES_ADDRESS("1211 Burlin Road, Cupertino");
            this.doctorItem[doc_i].setDoctorItemMOBILE_NO("5762241919");
            this.doctorItem[doc_i].setDoctorItemSPECIALIZATION("Physicist");
            this.doctorItem[doc_i].setDoctorItemRESUME(BuildConfig.FLAVOR);
            this.doctorItem[doc_i].setDoctorItemHOSP_NAME("Lenox Hill Medical Center");
            this.doctorItem[doc_i].setDoctorItemHOSP_CITY("San Fracisco");
            this.doctorItem[doc_i].setDoctorItemEMAIL("rohit.sharma@lenoxhill.org");
            this.doctorItem[doc_i].setDoctorItemP_WORD("rs12345");
            this.doctorItem[doc_i].setDoctorItemANSWER1("rogi");
            this.doctorItem[doc_i].setDoctorItemANSWER2("Chola Puri");
        }
    }

    public void initialConsult(int con_i) {
        if (con_i == 0) {
            this.consultItem[con_i].setConsultItem_DOC_ID("1");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Monday");
            this.consultItem[con_i].setConsultItem_START_TIME("12:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("13:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 1) {
            this.consultItem[con_i].setConsultItem_DOC_ID("1");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Wednesday");
            this.consultItem[con_i].setConsultItem_START_TIME("12:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("13:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 2) {
            this.consultItem[con_i].setConsultItem_DOC_ID("1");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Saturday");
            this.consultItem[con_i].setConsultItem_START_TIME("12:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("13:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 3) {
            this.consultItem[con_i].setConsultItem_DOC_ID("2");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Tuesday");
            this.consultItem[con_i].setConsultItem_START_TIME("12:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("13:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 4) {
            this.consultItem[con_i].setConsultItem_DOC_ID("2");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Thursday");
            this.consultItem[con_i].setConsultItem_START_TIME("12:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("13:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 5) {
            this.consultItem[con_i].setConsultItem_DOC_ID("2");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Friday");
            this.consultItem[con_i].setConsultItem_START_TIME("12:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("13:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 6) {
            this.consultItem[con_i].setConsultItem_DOC_ID("3");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Tuesday");
            this.consultItem[con_i].setConsultItem_START_TIME("9:30:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("11:00:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 7) {
            this.consultItem[con_i].setConsultItem_DOC_ID("3");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Wednesday");
            this.consultItem[con_i].setConsultItem_START_TIME("9:300:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("11:00:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 8) {
            this.consultItem[con_i].setConsultItem_DOC_ID("3");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Thursday");
            this.consultItem[con_i].setConsultItem_START_TIME("12:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("15:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 9) {
            this.consultItem[con_i].setConsultItem_DOC_ID("4");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Monday");
            this.consultItem[con_i].setConsultItem_START_TIME("12:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("13:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 10) {
            this.consultItem[con_i].setConsultItem_DOC_ID("4");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Tuesday");
            this.consultItem[con_i].setConsultItem_START_TIME("15:30:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("17:00:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 11) {
            this.consultItem[con_i].setConsultItem_DOC_ID("4");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Monday");
            this.consultItem[con_i].setConsultItem_START_TIME("12:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("13:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 12) {
            this.consultItem[con_i].setConsultItem_DOC_ID("5");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Wednesday");
            this.consultItem[con_i].setConsultItem_START_TIME("13:30:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("15:0:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 13) {
            this.consultItem[con_i].setConsultItem_DOC_ID("5");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Thursday");
            this.consultItem[con_i].setConsultItem_START_TIME("13:30:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("15:00:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 14) {
            this.consultItem[con_i].setConsultItem_DOC_ID("5");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Friday");
            this.consultItem[con_i].setConsultItem_START_TIME("12:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("13:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 15) {
            this.consultItem[con_i].setConsultItem_DOC_ID("6");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Tuesday");
            this.consultItem[con_i].setConsultItem_START_TIME("14:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("15:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 16) {
            this.consultItem[con_i].setConsultItem_DOC_ID("6");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Thursday");
            this.consultItem[con_i].setConsultItem_START_TIME("13:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("15:00:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 17) {
            this.consultItem[con_i].setConsultItem_DOC_ID("6");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Friday");
            this.consultItem[con_i].setConsultItem_START_TIME("14:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("15:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 18) {
            this.consultItem[con_i].setConsultItem_DOC_ID("7");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Wednesday");
            this.consultItem[con_i].setConsultItem_START_TIME("14:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("15:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 19) {
            this.consultItem[con_i].setConsultItem_DOC_ID("7");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Friday");
            this.consultItem[con_i].setConsultItem_START_TIME("13:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("14:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        } else if (con_i == 20) {
            this.consultItem[con_i].setConsultItem_DOC_ID("7");
            this.consultItem[con_i].setConsultItem_APP_ID(BuildConfig.FLAVOR);
            this.consultItem[con_i].setConsultItem_DAY("Sunday");
            this.consultItem[con_i].setConsultItem_START_TIME("14:00:00 PM");
            this.consultItem[con_i].setConsultItem_END_TIME("15:30:00 PM");
            this.consultItem[con_i].setConsultItem_STATUS("Open");
        }
    }
}
