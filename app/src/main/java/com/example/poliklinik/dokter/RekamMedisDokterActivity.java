package com.example.poliklinik.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.poliklinik.R;
import com.example.poliklinik.pasien.RekamMedisPasienActivity;
import com.example.poliklinik.pasien.RekamMedisPasienModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class RekamMedisDokterActivity extends AppCompatActivity {

    ArrayList<RekamMedisDokterModel> rekamMedisDokterModels;
    RecyclerView recyclerViewRekamMedisDokter;
    RequestQueue queue;
    String url = "";
    RekamMedisDokterAdapter adapter;
    SearchView svRekamMedisDokter;
    SharedPreferences loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekam_medis_dokter);
        loginPreferences = this.getSharedPreferences("LoginPrefs", 0);

        AppCompatImageButton btnTambahListRekamMedisDokter = findViewById(R.id.btnTambahListRekamMedisDokter);
        ImageButton btnBackRekamMedisDokter = findViewById(R.id.btnBackRekamMedisDokter);
        svRekamMedisDokter = findViewById(R.id.svRekamMedisDokter);
        svRekamMedisDokter.clearFocus();

        svRekamMedisDokter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        recyclerViewRekamMedisDokter = findViewById(R.id.recyclerViewRekamMedisDokter);
        rekamMedisDokterModels = new ArrayList<>();
        adapter = new RekamMedisDokterAdapter(RekamMedisDokterActivity.this, rekamMedisDokterModels);
        getDataAPI();


        btnTambahListRekamMedisDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RekamMedisDokterActivity.this, TambahRekamMedisDokterActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btnBackRekamMedisDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getDataAPI(){
        queue = Volley.newRequestQueue(RekamMedisDokterActivity.this);
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_rekam_medis_by_dokter?dokter="+loginPreferences.getString("nama","-");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                rekamMedisDokterModels.add(new RekamMedisDokterModel(jsonObject.getString("_id"),
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
                            Collections.reverse(rekamMedisDokterModels);
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
        queue.add(jsonArrayRequest);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void initRecyclerView(){
        recyclerViewRekamMedisDokter.setAdapter(adapter);
        recyclerViewRekamMedisDokter.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    private void filterList(String text){
        ArrayList<RekamMedisDokterModel> filterArray = new ArrayList<>();
        for (RekamMedisDokterModel rekamMedisDokterModel : rekamMedisDokterModels){
            if (rekamMedisDokterModel.getIdRM().toLowerCase().contains(text.toLowerCase()) ||
                    rekamMedisDokterModel.getTanggalRM().toLowerCase().contains(text.toLowerCase())){
                filterArray.add(rekamMedisDokterModel);
            }
            if (filterArray.isEmpty()){
                Toast.makeText(this, "tidak ada Rekam medis", Toast.LENGTH_SHORT).show();
            }else {
                adapter.setFilteredModels(filterArray);
            }
        }
    }
}