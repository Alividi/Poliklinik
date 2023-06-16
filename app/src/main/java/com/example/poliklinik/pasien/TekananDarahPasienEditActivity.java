package com.example.poliklinik.pasien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.poliklinik.R;

public class TekananDarahPasienEditActivity extends AppCompatActivity {
    DBDataSource dataSource;
    TekananDarah tekananDarah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tekanan_darah_pasien_edit);

        dataSource = new DBDataSource(this);
        dataSource.open();

        AppCompatButton btnUpdateTekananDarahPasien = findViewById(R.id.btnUpdateTekananDarahPasien);
        ImageButton btnBackEditTekananDarahPasien = findViewById(R.id.btnBackEditTekananDarahPasien);
        EditText etTglTekananDarahEdit = findViewById(R.id.etTglTekananDarahEdit);
        EditText etWaktuTekananDarahEdit = findViewById(R.id.etWaktuTekananDarahEdit);
        EditText etBatasAtasTekananDarahEdit = findViewById(R.id.etBatasAtasTekananDarahEdit);
        EditText etBatasBawahTekananDarahEdit = findViewById(R.id.etBatasBawahTekananDarahEdit);
        EditText etKomentarTekananDarahEdit = findViewById(R.id.etKomentarTekananDarahEdit);

        long id = getIntent().getExtras().getLong("id");
        etTglTekananDarahEdit.setText(getIntent().getExtras().getString("tanggal"));
        etWaktuTekananDarahEdit.setText(getIntent().getExtras().getString("waktu"));
        etBatasAtasTekananDarahEdit.setText(getIntent().getExtras().getString("atas"));
        etBatasBawahTekananDarahEdit.setText(getIntent().getExtras().getString("bawah"));
        etKomentarTekananDarahEdit.setText(getIntent().getExtras().getString("komen"));

        btnUpdateTekananDarahPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tekananDarah = new TekananDarah();
                tekananDarah.setTanggal_tekanan_darah(etTglTekananDarahEdit.getText().toString());
                tekananDarah.setWaktu_tekanan_darah(etWaktuTekananDarahEdit.getText().toString());
                tekananDarah.setBatas_atas_tekanan(etBatasAtasTekananDarahEdit.getText().toString());
                tekananDarah.setBatas_bawah_tekanan(etBatasBawahTekananDarahEdit.getText().toString());
                tekananDarah.setKomentar_tekanan_darah(etKomentarTekananDarahEdit.getText().toString());
                tekananDarah.setId(id);
                dataSource.UpdateTekananDarah(tekananDarah);
                Intent intent = new Intent(TekananDarahPasienEditActivity.this, TekananDarahPasienActivity.class);
                startActivity(intent);
                TekananDarahPasienEditActivity.this.finish();
                dataSource.close();
            }
        });

        btnBackEditTekananDarahPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dataSource.close();
            }
        });
    }
}