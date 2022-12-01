package com.example.SensorTroubleshootApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

//REFERENCES AND RESOURCES USED:
//ACCELEROMETER TUTORIAL: https://youtu.be/zUzZ67grYt8
//PROXIMITY SENSOR TUTORIAL: https://youtu.be/QE0Qsy55iuk
//TEMPERATURE SENSOR TUTORIAL: https://youtu.be/JKuTnuUsKOI
//LAYOUT & PAGER TUTORIAL: https://youtu.be/Q20jZQy6vwU

//SOURCES USED APP'S POTENTIAL USES / INSPIRATION
//https://www.reddit.com/r/Nexus6P/comments/3to0ds/broken_accelerometer_try_dropping_your_phone/
//^ This helped establish that sensors can be damaged and/or broken. Who doesn't drop their phone?
//https://www.makeuseof.com/android-phone-proximity-sensor-not-working
//^ This also helped establish that sensors can be damaged and needs to be monitored

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Accelerometer"));
        tabLayout.addTab(tabLayout.newTab().setText("Temperature"));
        tabLayout.addTab(tabLayout.newTab().setText("Proximity"));

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch(position) {
                    case 0:
                        Accelerometer accel = new Accelerometer();
                        return accel;
                    case 1:
                        Temperature temp = new Temperature();
                        return temp;
                    case 2:
                        Proximity prox = new Proximity();
                        return prox;
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return tabLayout.getTabCount();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());//when a tab is selected, display the proper class
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



}