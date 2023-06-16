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

public class AntrianDokterActivity extends AppCompatActivity {
    ArrayList<AntrianDokterModel> antrianDokterModels;
    AntrianDokterAdapter adapter;
    RecyclerView recyclerViewAntrianDokter;
    RequestQueue queue;
    String url = "";
    SearchView svListAntrianDokter;
    SharedPreferences loginPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antrian_dokter);
        loginPreferences = this.getSharedPreferences("LoginPrefs", 0);

        ImageButton btnBackListAntrianDokter = findViewById(R.id.btnBackListAntrianDokter);
        svListAntrianDokter = findViewById(R.id.svListAntrianDokter);
        svListAntrianDokter.clearFocus();

        svListAntrianDokter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        recyclerViewAntrianDokter = findViewById(R.id.recyclerViewAntrianDokter);
        antrianDokterModels = new ArrayList<>();
        adapter = new AntrianDokterAdapter(AntrianDokterActivity.this, antrianDokterModels);
        getDataAPI();

        btnBackListAntrianDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AntrianDokterActivity.this, DokterMainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void getDataAPI(){
        queue = Volley.newRequestQueue(AntrianDokterActivity.this);
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_antrian_by_dokter?dokter="+loginPreferences.getString("nama", "-");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                antrianDokterModels.add(new AntrianDokterModel(jsonObject.getString("_id"),
                                        jsonObject.getString("urutan"),
                                        jsonObject.getString("nama_pasien"),
                                        jsonObject.getString("nama_dokter"),
                                        jsonObject.getString("status")));
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
        queue.add(jsonArrayRequest);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void initRecyclerView(){
        recyclerViewAntrianDokter.setAdapter(adapter);
        recyclerViewAntrianDokter.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    private void filterList(String text){
        ArrayList<AntrianDokterModel> filterArray = new ArrayList<>();
        for (AntrianDokterModel antrianDokterModel : antrianDokterModels){
            if (antrianDokterModel.getNmPasien().toLowerCase().contains(text.toLowerCase()) ||
                    antrianDokterModel.getUrutan().toLowerCase().contains(text.toLowerCase())){
                filterArray.add(antrianDokterModel);
            }
            if (filterArray.isEmpty()){
                Toast.makeText(this, "tidak ada antrian", Toast.LENGTH_SHORT).show();
            }else {
                adapter.setFilteredModels(filterArray);
            }
        }
    }
}