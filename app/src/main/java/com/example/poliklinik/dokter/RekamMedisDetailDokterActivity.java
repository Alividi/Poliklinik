package com.example.poliklinik.dokter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.poliklinik.R;

public class RekamMedisDetailDokterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekam_medis_detail_dokter);

        ImageButton btnBackRMDetailDokter = findViewById(R.id.btnBackRMDetailDokter);
        Intent intent = getIntent();
        TextView detailTanggalPeriksaDRMDokter = findViewById(R.id.detailTanggalPeriksaDRMDokter);
        TextView detailNomorDRMDokter = findViewById(R.id.detailNomorDRMDokter);
        TextView detailDokterDRMDokter = findViewById(R.id.detailDokterDRMDokter);
        TextView detailKeluhanDRMDokter = findViewById(R.id.detailKeluhanDRMDokter);
        TextView detailDiagnosaDRMDokter = findViewById(R.id.detailDiagnosaDRMDokter);
        TextView detailObat1DRMDokter = findViewById(R.id.detailObat1DRMDokter);
        TextView detailJmlObat1DRMDokter = findViewById(R.id.detailJmlObat1DRMDokter);
        TextView detailObat2DRMDokter = findViewById(R.id.detailObat2DRMDokter);
        TextView detailJmlObat2DRMDokter = findViewById(R.id.detailJmlObat2DRMDokter);
        TextView detailObat3DRMDokter = findViewById(R.id.detailObat3DRMDokter);
        TextView detailJmlObat3DRMDokter = findViewById(R.id.detailJmlObat3DRMDokter);
        TextView detailObat4DRMDokter = findViewById(R.id.detailObat4DRMDokter);
        TextView detailJmlObat4DRMDokter = findViewById(R.id.detailJmlObat4DRMDokter);
        TextView detailObat5DRMDokter = findViewById(R.id.detailObat5DRMDokter);
        TextView detailJmlObat5DRMDokter = findViewById(R.id.detailJmlObat5DRMDokter);

        detailTanggalPeriksaDRMDokter.setText(intent.getStringExtra("tanggal"));
        detailNomorDRMDokter.setText(intent.getStringExtra("nomor"));
        detailDokterDRMDokter.setText(intent.getStringExtra("pasien"));
        detailKeluhanDRMDokter.setText(intent.getStringExtra("keluhan"));
        detailDiagnosaDRMDokter.setText(intent.getStringExtra("diagnosa"));
        detailObat1DRMDokter.setText(intent.getStringExtra("obat1"));
        detailJmlObat1DRMDokter.setText(intent.getStringExtra("jumlah1"));
        detailObat2DRMDokter.setText(intent.getStringExtra("obat2"));
        detailJmlObat2DRMDokter.setText(intent.getStringExtra("jumlah2"));
        detailObat3DRMDokter.setText(intent.getStringExtra("obat3"));
        detailJmlObat3DRMDokter.setText(intent.getStringExtra("jumlah3"));
        detailObat4DRMDokter.setText(intent.getStringExtra("obat4"));
        detailJmlObat4DRMDokter.setText(intent.getStringExtra("jumlah4"));
        detailObat5DRMDokter.setText(intent.getStringExtra("obat5"));
        detailJmlObat5DRMDokter.setText(intent.getStringExtra("jumlah5"));

        if (detailObat1DRMDokter.getText().equals("")){
            detailObat1DRMDokter.setVisibility(View.GONE);
            detailJmlObat1DRMDokter.setVisibility(View.GONE);
        }
        if (detailObat2DRMDokter.getText().equals("")){
            detailObat2DRMDokter.setVisibility(View.GONE);
            detailJmlObat2DRMDokter.setVisibility(View.GONE);
        }
        if (detailObat3DRMDokter.getText().equals("")){
            detailObat3DRMDokter.setVisibility(View.GONE);
            detailJmlObat3DRMDokter.setVisibility(View.GONE);
        }
        if (detailObat4DRMDokter.getText().equals("")){
            detailObat4DRMDokter.setVisibility(View.GONE);
            detailJmlObat4DRMDokter.setVisibility(View.GONE);
        }
        if (detailObat5DRMDokter.getText().equals("")){
            detailObat5DRMDokter.setVisibility(View.GONE);
            detailJmlObat5DRMDokter.setVisibility(View.GONE);
        }

        btnBackRMDetailDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}