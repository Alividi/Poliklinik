package com.example.poliklinik.pasien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.poliklinik.R;

public class TekananDarahPasienDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tekanan_darah_pasien_detail);

        ImageButton btnBackDetailTekananDarahPasien = findViewById(R.id.btnBackDetailTekananDarahPasien);
        TextView tvTekananDarahDetail = findViewById(R.id.tvTekananDarahDetail);
        TextView tvTglTekananDarahDetail = findViewById(R.id.etTglTekananDarahDetail);
        TextView tvWaktuTekananDarahDetail = findViewById(R.id.etWaktuTekananDarahDetail);
        TextView tvBatasAtasTekananDarahDetail = findViewById(R.id.etBatasAtasTekananDarahDetail);
        TextView tvBatasBawahTekananDarahDetail = findViewById(R.id.etBatasBawahTekananDarahDetail);
        TextView tvKomentarTekananDarahDetail = findViewById(R.id.etKomentarTekananDarahDetail);

        tvTekananDarahDetail.setText(tvTekananDarahDetail.getText()+" (ID: "+getIntent().getExtras().getLong("id")+")");
        tvTglTekananDarahDetail.setText(getIntent().getExtras().getString("tanggal"));
        tvWaktuTekananDarahDetail.setText(getIntent().getExtras().getString("waktu"));
        tvBatasAtasTekananDarahDetail.setText(getIntent().getExtras().getString("atas"));
        tvBatasBawahTekananDarahDetail.setText(getIntent().getExtras().getString("bawah"));
        tvKomentarTekananDarahDetail.setText(getIntent().getExtras().getString("komen"));


        btnBackDetailTekananDarahPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}