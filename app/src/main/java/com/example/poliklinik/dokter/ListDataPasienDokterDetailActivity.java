package com.example.poliklinik.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.poliklinik.R;
import com.google.android.material.tabs.TabLayout;

public class ListDataPasienDokterDetailActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    DetailDataPasienAdapter detailDataPasienAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pasien_dokter_detail);

        ImageButton btnBackDataPasienDtlDokter = findViewById(R.id.btnBackDataPasienDtlDokter);
        tabLayout = findViewById(R.id.tlDataPasien);
        viewPager2 = findViewById(R.id.vpDataPasien);

        tabLayout.addTab(tabLayout.newTab().setText("Profile Pasien"));
        tabLayout.addTab(tabLayout.newTab().setText("Rekam Medis"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        detailDataPasienAdapter = new DetailDataPasienAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(detailDataPasienAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        btnBackDataPasienDtlDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}