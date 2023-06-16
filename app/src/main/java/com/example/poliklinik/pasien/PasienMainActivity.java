package com.example.poliklinik.pasien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.poliklinik.R;
import com.example.poliklinik.databinding.ActivityPasienMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class PasienMainActivity extends AppCompatActivity {

    ActivityPasienMainBinding pasienBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pasienBinding = ActivityPasienMainBinding.inflate(getLayoutInflater());
        setContentView(pasienBinding.getRoot());
        replaceFragment(new HomePasienFragment());

        BottomNavigationView navPasien = findViewById(R.id.navPasien);
        FloatingActionButton fabPasien = findViewById(R.id.fabPasien);
        navPasien.setBackground(null);

        pasienBinding.navPasien.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.miHomePasien:
                    replaceFragment(new HomePasienFragment());
                    break;
                case R.id.miCalendarPasien:
                    replaceFragment(new KalenderPasienFragment());
                    break;
                case R.id.miNotifikasiPasien:
                    replaceFragment(new NotifikasiPasienFragment());
                    break;
                case R.id.miProfilePasien:
                    replaceFragment(new ProfilePasienFragment());
                    break;
            }
            return true;
        });

        fabPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasienMainActivity.this, TambahJadwalKonsultasiPasienActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameContainerPasien, fragment);
        fragmentTransaction.commit();
    }

}
