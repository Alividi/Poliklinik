package com.example.poliklinik.dokter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.poliklinik.LoginActivity;
import com.example.poliklinik.R;
import com.example.poliklinik.SplashScreenActivity;

public class SplashScreenDokterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_dokter);


        Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    Intent intent = new Intent(SplashScreenDokterActivity.this, DokterMainActivity.class);
                    intent.putExtra("nama",getIntent().getStringExtra("nama"));
                    startActivity(intent);

                    finish();
                }
            }
        };
        timer.start();
    }
}