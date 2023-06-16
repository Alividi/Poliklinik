package com.example.poliklinik.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.poliklinik.R;
import com.example.poliklinik.databinding.ActivityDokterMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DokterMainActivity extends AppCompatActivity{

    ActivityDokterMainBinding dokterMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dokterMainBinding = ActivityDokterMainBinding.inflate(getLayoutInflater());
        replaceFragment(new HomeDokterFragment());
        setContentView(dokterMainBinding.getRoot());

        FloatingActionButton fabDokter = findViewById(R.id.fabDokter);

        dokterMainBinding.navDokter.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.miHomeDokter:
                    replaceFragment(new HomeDokterFragment());
                    break;
                case R.id.miCalendarDokter:
                    replaceFragment(new KalenderDokterFragment());
                    break;
                case R.id.miNotifikasiDokter:
                    replaceFragment(new NotifikasiDokterFragment());
                    break;
                case R.id.miProfileDokter:
                    replaceFragment(new ProfileDokterFragment());
                    break;
            }
            return true;
        });

        fabDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DokterMainActivity.this, TambahJadwalDokterActivity.class);
                startActivity(intent);
            }
        });

    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameContainerDokter, fragment);
        fragmentTransaction.commit();
    }

}