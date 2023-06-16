package com.example.poliklinik.pasien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.poliklinik.R;

public class TambahTekananDarahPasienActivity extends AppCompatActivity {

    EditText etTglTekananDarah,etWaktuTekananDarah,etBatasAtasTekananDarah,etBatasBawahTekananDarah,etKomentarTekananDarah;
    DBDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_tekanan_darah_pasien);
        AppCompatButton btnAddTekananDarahPasien = findViewById(R.id.btnAddTekananDarahPasien);
        ImageButton btnBackTambahTekananDarahPasien = findViewById(R.id.btnBackTambahTekananDarahPasien);
        etTglTekananDarah = findViewById(R.id.etTglTekananDarah);
        etWaktuTekananDarah = findViewById(R.id.etWaktuTekananDarah);
        etBatasAtasTekananDarah = findViewById(R.id.etBatasAtasTekananDarah);
        etBatasBawahTekananDarah = findViewById(R.id.etBatasBawahTekananDarah);
        etKomentarTekananDarah = findViewById(R.id.etKomentarTekananDarah);

        dataSource = new DBDataSource(this);
        dataSource.open();


        btnAddTekananDarahPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tgl = null;
                String wkt = null;
                String ats = null;
                String bwh = null;
                String kmt = null;
                TekananDarah tdh = null;

                if (etTglTekananDarah.getText().toString().length() >= 1 && etWaktuTekananDarah.getText().toString().length() >= 1 &&
                        etBatasAtasTekananDarah.getText().toString().length() >= 1 && etBatasBawahTekananDarah.getText().toString().length() >= 1 &&
                        etKomentarTekananDarah.getText().toString().length() >= 1){

                    tgl = etTglTekananDarah.getText().toString();
                    wkt = etWaktuTekananDarah.getText().toString();
                    ats = etBatasAtasTekananDarah.getText().toString();
                    bwh = etBatasBawahTekananDarah.getText().toString();
                    kmt = etKomentarTekananDarah.getText().toString();
                    tdh = dataSource.createTekananDarah(tgl,wkt,ats,bwh,kmt);

                    Toast.makeText(TambahTekananDarahPasienActivity.this, tdh.toString()+" Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TambahTekananDarahPasienActivity.this, TekananDarahPasienActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    Toast.makeText(TambahTekananDarahPasienActivity.this, "Tolong Isi Semua Kolom", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBackTambahTekananDarahPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahTekananDarahPasienActivity.this, TekananDarahPasienActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}