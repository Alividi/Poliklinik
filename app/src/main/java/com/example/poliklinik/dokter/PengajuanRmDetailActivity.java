package com.example.poliklinik.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.poliklinik.R;

public class PengajuanRmDetailActivity extends AppCompatActivity {
    TextView detailNmPasienPRMPasien;
    TextView detailTglPRMPasien;
    TextView detailNoPRMPasien;
    TextView detailKeluhanPRMPasien;
    TextView detailNmDokterPRMPasien;
    TextView detailDiagnosaPRMPasien;
    TextView detailObat1PRMPasien;
    TextView detailJmlObat1PRMPasien;
    TextView detailObat2PRMPasien;
    TextView detailJmlObat2PRMPasien;
    TextView detailObat3PRMPasien;
    TextView detailJmlObat3PRMPasien;
    TextView detailObat4PRMPasien;
    TextView detailJmlObat4PRMPasien;
    TextView detailObat5PRMPasien;
    TextView detailJmlObat5PRMPasien;
    AppCompatButton btnIzikanPRMDokter;
    AppCompatButton btnTolakPRMDokter;
    String url,urlN;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan_rm_detail);

        ImageButton btnBackPRMDetailPasien = findViewById(R.id.btnBackPRMDetailPasien);
        Intent intent = getIntent();
        detailNmPasienPRMPasien = findViewById(R.id.detailNmPasienPRMPasien);
        detailTglPRMPasien = findViewById(R.id.detailTglPRMPasien);
        detailNoPRMPasien = findViewById(R.id.detailNoPRMPasien);
        detailKeluhanPRMPasien = findViewById(R.id.detailKeluhanPRMPasien);
        detailNmDokterPRMPasien = findViewById(R.id.detailNmDokterPRMPasien);
        detailDiagnosaPRMPasien = findViewById(R.id.detailDiagnosaPRMPasien);
        detailObat1PRMPasien = findViewById(R.id.detailObat1PRMPasien);
        detailJmlObat1PRMPasien = findViewById(R.id.detailJmlObat1PRMPasien);
        detailObat2PRMPasien = findViewById(R.id.detailObat2PRMPasien);
        detailJmlObat2PRMPasien = findViewById(R.id.detailJmlObat2PRMPasien);
        detailObat3PRMPasien = findViewById(R.id.detailObat3PRMPasien);
        detailJmlObat3PRMPasien = findViewById(R.id.detailJmlObat3PRMPasien);
        detailObat4PRMPasien = findViewById(R.id.detailObat4PRMPasien);
        detailJmlObat4PRMPasien = findViewById(R.id.detailJmlObat4PRMPasien);
        detailObat5PRMPasien = findViewById(R.id.detailObat5PRMPasien);
        detailJmlObat5PRMPasien = findViewById(R.id.detailJmlObat5PRMPasien);
        btnIzikanPRMDokter = findViewById(R.id.btnIzikanPRMDokter);
        btnTolakPRMDokter = findViewById(R.id.btnTolakPRMDokter);

        detailNmPasienPRMPasien.setText(intent.getStringExtra("pasien"));
        detailTglPRMPasien.setText(intent.getStringExtra("tanggal"));
        detailNoPRMPasien.setText(intent.getStringExtra("nomor"));
        detailKeluhanPRMPasien.setText(intent.getStringExtra("keluhan"));
        detailNmDokterPRMPasien.setText(intent.getStringExtra("dokter"));
        detailDiagnosaPRMPasien.setText(intent.getStringExtra("diagnosa"));
        detailObat1PRMPasien.setText(intent.getStringExtra("obat1"));
        detailJmlObat1PRMPasien.setText(intent.getStringExtra("jumlah1"));
        detailObat2PRMPasien.setText(intent.getStringExtra("obat2"));
        detailJmlObat2PRMPasien.setText(intent.getStringExtra("jumlah2"));
        detailObat3PRMPasien.setText(intent.getStringExtra("obat3"));
        detailJmlObat3PRMPasien.setText(intent.getStringExtra("jumlah3"));
        detailObat4PRMPasien.setText(intent.getStringExtra("obat4"));
        detailJmlObat4PRMPasien.setText(intent.getStringExtra("jumlah4"));
        detailObat5PRMPasien.setText(intent.getStringExtra("obat5"));
        detailJmlObat5PRMPasien.setText(intent.getStringExtra("jumlah5"));

        if (detailObat1PRMPasien.getText().equals("")){
            detailObat1PRMPasien.setVisibility(View.INVISIBLE);
            detailJmlObat1PRMPasien.setVisibility(View.INVISIBLE);
        }
        if (detailObat2PRMPasien.getText().equals("")){
            detailObat2PRMPasien.setVisibility(View.INVISIBLE);
            detailJmlObat2PRMPasien.setVisibility(View.INVISIBLE);
        }
        if (detailObat3PRMPasien.getText().equals("")){
            detailObat3PRMPasien.setVisibility(View.INVISIBLE);
            detailJmlObat3PRMPasien.setVisibility(View.INVISIBLE);
        }
        if (detailObat4PRMPasien.getText().equals("")){
            detailObat4PRMPasien.setVisibility(View.INVISIBLE);
            detailJmlObat4PRMPasien.setVisibility(View.INVISIBLE);
        }
        if (detailObat5PRMPasien.getText().equals("")){
            detailObat5PRMPasien.setVisibility(View.INVISIBLE);
            detailJmlObat5PRMPasien.setVisibility(View.INVISIBLE);
        }

        if (intent.getStringExtra("status").equals("Diizinkan")){
            btnIzikanPRMDokter.setText("Diizinkan");
            btnIzikanPRMDokter.setEnabled(false);
            btnTolakPRMDokter.setVisibility(View.INVISIBLE);
            btnTolakPRMDokter.setEnabled(false);
        }
        if (intent.getStringExtra("status").equals("Ditolak")){
            btnTolakPRMDokter.setText("Ditolak");
            btnTolakPRMDokter.setEnabled(false);
            btnIzikanPRMDokter.setVisibility(View.INVISIBLE);
            btnIzikanPRMDokter.setEnabled(false);
        }

        btnIzikanPRMDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PengajuanRmDetailActivity.this, PengajuanRmDokterActivity.class);
                url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/update_status_rekam_medis_diizinkan?id="+intent.getStringExtra("id");
                statusUpdate();
                urlN ="https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/update_status_pemberitahuan_diizinkan?idrm="+intent.getStringExtra("id");
                statusNotif();
                Toast.makeText(PengajuanRmDetailActivity.this, "Pengajuan Diizinkan", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(intent1);
            }
        });

        btnTolakPRMDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PengajuanRmDetailActivity.this, PengajuanRmDokterActivity.class);
                url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/update_status_rekam_medis_ditolak?id="+intent.getStringExtra("id");
                statusUpdate();
                urlN ="https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/update_status_pemberitahuan_ditolak?idrm="+intent.getStringExtra("id");
                statusNotif();
                Toast.makeText(PengajuanRmDetailActivity.this, "Pengajuan Ditolak", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(intent1);
            }
        });

        btnBackPRMDetailPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PengajuanRmDetailActivity.this, PengajuanRmDokterActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }

    public void statusUpdate(){
        queue = Volley.newRequestQueue(PengajuanRmDetailActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("api", "onResponse: "+response );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getStackTrace();
            }
        });
        queue.add(stringRequest);
    }

    public void statusNotif(){
        queue = Volley.newRequestQueue(PengajuanRmDetailActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, urlN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("api", "onResponse: "+response );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getStackTrace();
            }
        });
        queue.add(stringRequest);
    }
}