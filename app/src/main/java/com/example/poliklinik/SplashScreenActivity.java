package com.example.poliklinik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.poliklinik.dokter.DokterMainActivity;
import com.example.poliklinik.dokter.SplashScreenDokterActivity;
import com.example.poliklinik.pasien.PasienMainActivity;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity {
    SharedPreferences loginPreferences;
    String user_id;
    String aktor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Context context = this;
        loginPreferences = context.getSharedPreferences("LoginPrefs", 0);

        user_id = loginPreferences.getString("user_id", "");
        aktor = loginPreferences.getString("aktor","");

        Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (Objects.equals(user_id, "")) {
                        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if (aktor.equals("dokter")){
                            Intent intent = new Intent(SplashScreenActivity.this, DokterMainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (aktor.equals("pasien")) {
                            Intent intent = new Intent(SplashScreenActivity.this, PasienMainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        };
        timer.start();
    }
}