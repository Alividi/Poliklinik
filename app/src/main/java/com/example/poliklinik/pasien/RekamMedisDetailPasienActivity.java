package com.example.poliklinik.pasien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.poliklinik.R;

public class RekamMedisDetailPasienActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekam_medis_detail_pasien);

        ImageButton btnBackRiwayatKunjunganDetailPasien = findViewById(R.id.btnBackRiwayatKunjunganDetailPasien);
        Intent intent = getIntent();
        TextView detailTanggalPeriksaDRMPasien = findViewById(R.id.detailTanggalPeriksaDRMPasien);
        TextView detailNomorDRMPasien = findViewById(R.id.detailNomorDRMPasien);
        TextView detailDokterDRMPasien = findViewById(R.id.detailDokterDRMPasien);
        TextView detailKeluhanDRMPasien = findViewById(R.id.detailKeluhanDRMPasien);
        TextView detailDiagnosaDRMPasien = findViewById(R.id.detailDiagnosaDRMPasien);
        TextView detailObat1DRMPasien = findViewById(R.id.detailObat1DRMPasien);
        TextView detailJmlObat1DRMPasien = findViewById(R.id.detailJmlObat1DRMPasien);
        TextView detailObat2DRMPasien = findViewById(R.id.detailObat2DRMPasien);
        TextView detailJmlObat2DRMPasien = findViewById(R.id.detailJmlObat2DRMPasien);
        TextView detailObat3DRMPasien = findViewById(R.id.detailObat3DRMPasien);
        TextView detailJmlObat3DRMPasien = findViewById(R.id.detailJmlObat3DRMPasien);
        TextView detailObat4DRMPasien = findViewById(R.id.detailObat4DRMPasien);
        TextView detailJmlObat4DRMPasien = findViewById(R.id.detailJmlObat4DRMPasien);
        TextView detailObat5DRMPasien = findViewById(R.id.detailObat5DRMPasien);
        TextView detailJmlObat5DRMPasien = findViewById(R.id.detailJmlObat5DRMPasien);

        detailTanggalPeriksaDRMPasien.setText(intent.getStringExtra("tanggal"));
        detailNomorDRMPasien.setText(intent.getStringExtra("nomor"));
        detailDokterDRMPasien.setText(intent.getStringExtra("dokter"));
        detailKeluhanDRMPasien.setText(intent.getStringExtra("keluhan"));
        detailDiagnosaDRMPasien.setText(intent.getStringExtra("diagnosa"));
        detailObat1DRMPasien.setText(intent.getStringExtra("obat1"));
        detailJmlObat1DRMPasien.setText(intent.getStringExtra("jumlah1"));
        detailObat2DRMPasien.setText(intent.getStringExtra("obat2"));
        detailJmlObat2DRMPasien.setText(intent.getStringExtra("jumlah2"));
        detailObat3DRMPasien.setText(intent.getStringExtra("obat3"));
        detailJmlObat3DRMPasien.setText(intent.getStringExtra("jumlah3"));
        detailObat4DRMPasien.setText(intent.getStringExtra("obat4"));
        detailJmlObat4DRMPasien.setText(intent.getStringExtra("jumlah4"));
        detailObat5DRMPasien.setText(intent.getStringExtra("obat5"));
        detailJmlObat5DRMPasien.setText(intent.getStringExtra("jumlah5"));

        if (detailObat1DRMPasien.getText().equals("")){
            detailObat1DRMPasien.setVisibility(View.GONE);
            detailJmlObat1DRMPasien.setVisibility(View.GONE);
        }
        if (detailObat2DRMPasien.getText().equals("")){
            detailObat2DRMPasien.setVisibility(View.GONE);
            detailJmlObat2DRMPasien.setVisibility(View.GONE);
        }
        if (detailObat3DRMPasien.getText().equals("")){
            detailObat3DRMPasien.setVisibility(View.GONE);
            detailJmlObat3DRMPasien.setVisibility(View.GONE);
        }
        if (detailObat4DRMPasien.getText().equals("")){
            detailObat4DRMPasien.setVisibility(View.GONE);
            detailJmlObat4DRMPasien.setVisibility(View.GONE);
        }
        if (detailObat5DRMPasien.getText().equals("")){
            detailObat5DRMPasien.setVisibility(View.GONE);
            detailJmlObat5DRMPasien.setVisibility(View.GONE);
        }

        btnBackRiwayatKunjunganDetailPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}