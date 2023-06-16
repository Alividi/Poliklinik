package com.example.poliklinik.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class PengajuanRmDokterActivity extends AppCompatActivity implements PengajuanRmDokterInterface{

    ArrayList<PengajuanRmDokterModel> pengajuanRmDokterModels;
    RecyclerView recyclerViewPRekamMedisDokter;
    RequestQueue queue;
    String url = "";
    SharedPreferences loginPreferences;
    PengajuanRmDokterAdapter adapter;
    SearchView svPRekamMedisDokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan_rm_dokter);

        loginPreferences = this.getSharedPreferences("LoginPrefs", 0);
        ImageButton btnBackPRekamMedisDokter = findViewById(R.id.btnBackPRekamMedisDokter);
        svPRekamMedisDokter = findViewById(R.id.svPRekamMedisDokter);

        svPRekamMedisDokter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        recyclerViewPRekamMedisDokter = findViewById(R.id.recyclerViewPRekamMedisDokter);
        pengajuanRmDokterModels = new ArrayList<>();
        adapter = new PengajuanRmDokterAdapter(PengajuanRmDokterActivity.this, pengajuanRmDokterModels, this);
        getPRM();


        btnBackPRekamMedisDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PengajuanRmDokterActivity.this, DokterMainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void getPRM(){
        queue = Volley.newRequestQueue(PengajuanRmDokterActivity.this);
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_list_p_rekam_medis?dokter="+loginPreferences.getString("nama", "-");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                pengajuanRmDokterModels.add(new PengajuanRmDokterModel(jsonObject.getString("_id"),
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
                            Collections.reverse(pengajuanRmDokterModels);
                            initRecyclerViewPRM();
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
    public void initRecyclerViewPRM(){
        recyclerViewPRekamMedisDokter.setAdapter(adapter);
        recyclerViewPRekamMedisDokter.setLayoutManager(new LinearLayoutManager(PengajuanRmDokterActivity.this));
        adapter.notifyDataSetChanged();
    }

    private void filterList(String text){
        ArrayList<PengajuanRmDokterModel> filterArray = new ArrayList<>();
        for (PengajuanRmDokterModel pengajuanRmDokterModel : pengajuanRmDokterModels){
            if (pengajuanRmDokterModel.getIdRM().toLowerCase().contains(text.toLowerCase()) ||
                    pengajuanRmDokterModel.getNmPasienRM().toLowerCase().contains(text.toLowerCase())){
                filterArray.add(pengajuanRmDokterModel);
            }
            if (filterArray.isEmpty()){
                Toast.makeText(this, "tidak ada Rekam medis", Toast.LENGTH_SHORT).show();
            }else {
                adapter.setFilteredModels(filterArray);
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(PengajuanRmDokterActivity.this, PengajuanRmDetailActivity.class);
        finish();
        intent.putExtra("id", pengajuanRmDokterModels.get(position).getIdRM());
        intent.putExtra("tanggal", pengajuanRmDokterModels.get(position).getTanggalRM());
        intent.putExtra("nomor", pengajuanRmDokterModels.get(position).getIdRM());
        intent.putExtra("pasien", pengajuanRmDokterModels.get(position).getNmPasienRM());
        intent.putExtra("dokter",pengajuanRmDokterModels.get(position).getNmDokterRM());
        intent.putExtra("keluhan", pengajuanRmDokterModels.get(position).getKeluhanRM());
        intent.putExtra("diagnosa", pengajuanRmDokterModels.get(position).getDiagnosaRM());
        intent.putExtra("obat1", pengajuanRmDokterModels.get(position).getObat1RM());
        intent.putExtra("jumlah1", pengajuanRmDokterModels.get(position).getJumlah1RM());
        intent.putExtra("obat2", pengajuanRmDokterModels.get(position).getObat2RM());
        intent.putExtra("jumlah2", pengajuanRmDokterModels.get(position).getJumlah2RM());
        intent.putExtra("obat3", pengajuanRmDokterModels.get(position).getObat3RM());
        intent.putExtra("jumlah3", pengajuanRmDokterModels.get(position).getJumlah3RM());
        intent.putExtra("obat4", pengajuanRmDokterModels.get(position).getObat4RM());
        intent.putExtra("jumlah4", pengajuanRmDokterModels.get(position).getJumlah4RM());
        intent.putExtra("obat5", pengajuanRmDokterModels.get(position).getObat5RM());
        intent.putExtra("jumlah5", pengajuanRmDokterModels.get(position).getJumlah5RM());
        intent.putExtra("status", pengajuanRmDokterModels.get(position).getStatusRM());
        startActivity(intent);
    }
}