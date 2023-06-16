package com.example.poliklinik.pasien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.poliklinik.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class RekamMedisPasienActivity extends AppCompatActivity {
    ArrayList<RekamMedisPasienModel> rekamMedisPasienModels;
    RekamMedisPasienAdapter adapter;
    RecyclerView rvRMPasien;
    RequestQueue queueRM;
    String url = "";
    Integer filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekam_medis_pasien);

        ImageButton btnBackRiwayatKunjunganPasien = findViewById(R.id.btnBackRiwayatKunjunganPasien);
        Spinner sFilterRiwayatKunjunganPasien = findViewById(R.id.sFilterRiwayatKunjunganPasien);
        AppCompatButton btnFilterRMPasien = findViewById(R.id.btnFilterRMPasien);
        filter = 0;

        rvRMPasien = findViewById(R.id.rvRMPasien);
        rekamMedisPasienModels = new ArrayList<>();
        adapter = new RekamMedisPasienAdapter(RekamMedisPasienActivity.this, rekamMedisPasienModels);

        sFilterRiwayatKunjunganPasien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sFilterRiwayatKunjunganPasien.getSelectedItem().toString().equals("Semua")){
                    filter = 0;
                    btnFilterRMPasien.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_arrow_up_filter, 0, 0, 0);
                    rekamMedisPasienModels.clear();
                    url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_rekam_medis";
                    getDataAPI();
                }
                else if(sFilterRiwayatKunjunganPasien.getSelectedItem().toString().equals("Belum Diizinkan")){
                    filter = 0;
                    btnFilterRMPasien.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_arrow_up_filter, 0, 0, 0);
                    rekamMedisPasienModels.clear();
                    url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_rekam_medis_by_status?status=Belum%20Diizinkan";
                    getDataAPI();
                }
                else if(sFilterRiwayatKunjunganPasien.getSelectedItem().toString().equals("Diizinkan")){
                    filter = 0;
                    btnFilterRMPasien.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_arrow_up_filter, 0, 0, 0);
                    rekamMedisPasienModels.clear();
                    url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_rekam_medis_by_status?status=Diizinkan";
                    getDataAPI();
                }
                else if(sFilterRiwayatKunjunganPasien.getSelectedItem().toString().equals("Ditolak")){
                    filter = 0;
                    btnFilterRMPasien.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_arrow_up_filter, 0, 0, 0);
                    rekamMedisPasienModels.clear();
                    url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_rekam_medis_by_status?status=Ditolak";
                    getDataAPI();
                }
                else {
                    Log.e("TAG", "onItemSelected: Belum Pilih Filter" );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnFilterRMPasien.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                if (filter == 0){
                    filter = 1;
                    btnFilterRMPasien.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_arrow_down_filter, 0, 0, 0);
                }else if(filter == 1) {
                    filter = 0;
                    btnFilterRMPasien.setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_arrow_up_filter, 0, 0, 0);
                }
                Collections.reverse(rekamMedisPasienModels);
                rvRMPasien.setAdapter(adapter);
                rvRMPasien.setLayoutManager(new LinearLayoutManager(RekamMedisPasienActivity.this));
                adapter.notifyDataSetChanged();
            }
        });

        btnBackRiwayatKunjunganPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RekamMedisPasienActivity.this, PasienMainActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }
    public void getDataAPI(){
        queueRM = Volley.newRequestQueue(RekamMedisPasienActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                rekamMedisPasienModels.add(new RekamMedisPasienModel(jsonObject.getString("_id"),
                                        jsonObject.getString("tanggal"),
                                        jsonObject.getString("diagnosa"),
                                        jsonObject.getString("keluhan"),
                                        jsonObject.getString("nama_pasien"),
                                        jsonObject.getString("nama_dokter"),
                                        jsonObject.getString("status"),
                                        jsonObject.getString("obat"),
                                        jsonObject.getString("jumlah"),
                                        jsonObject.getString("obat2"),
                                        jsonObject.getString("jumlah2"),
                                        jsonObject.getString("obat3"),
                                        jsonObject.getString("jumlah3"),
                                        jsonObject.getString("obat4"),
                                        jsonObject.getString("jumlah4"),
                                        jsonObject.getString("obat5"),
                                        jsonObject.getString("jumlah5")));
                            }
                            initRecyclerView();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getStackTrace();
            }
        });
        queueRM.add(jsonArrayRequest);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void initRecyclerView(){
        rvRMPasien.setAdapter(adapter);
        rvRMPasien.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }
}