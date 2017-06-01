package com.natodriod.timecontrol;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.natodriod.timecontrol.alarmclock.AlarmClock;
import com.natodriod.timecontrol.alarmclock.AlarmConfig;
import com.natodriod.timecontrol.alarmclock.AlarmType;
import com.natodriod.timecontrol.fragments.DoingListFragment;
import com.natodriod.timecontrol.fragments.DoneFragment;
import com.natodriod.timecontrol.fragments.PieCharFragment;
import com.natodriod.timecontrol.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_doing_list:
                    showDoingList();
                    return true;
                case R.id.navigation_done_list:
                        replaceFragment(new DoneFragment(), "done_list");
                    return true;
                case R.id.navigation_pie_char:
                     replaceFragment(new PieCharFragment(), "pie_char");
                    return true;
                case R.id.navigation_settings:
                    replaceFragment(new SettingsFragment(), "settings");
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        showDoingList();
        new AlarmClock(this).setAlarm(AlarmType.CONTROLLER, AlarmConfig.CONTROLLER_ALARM_TIME, AlarmConfig.REQUEST_CODE_CONTROLLER);


    }





    private void showDoingList() {
        replaceFragment(new DoingListFragment(), "doing_list");
    }

    private void replaceFragment(Fragment fragment, String tag){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      //  fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.content, fragment, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

}
