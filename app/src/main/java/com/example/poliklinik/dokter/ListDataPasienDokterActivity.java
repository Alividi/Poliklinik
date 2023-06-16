package com.example.poliklinik.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListDataPasienDokterActivity extends AppCompatActivity implements ListDataPasienDokterInterface{

    ArrayList<PasienModel> pasienModels;
    RecyclerView recyclerListDataPasienDokter;
    ListDataPasienDokterAdapter adapter;
    RequestQueue queue;
    String url = "";
    SearchView svListDataPasienDokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data_pasien_dokter);

        ImageButton btnBackListDataPasienDokter = findViewById(R.id.btnBackListDataPasienDokter);
        svListDataPasienDokter = findViewById(R.id.svListDataPasienDokter);
        svListDataPasienDokter.clearFocus();

        svListDataPasienDokter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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


        pasienModels = new ArrayList<>();
        recyclerListDataPasienDokter = findViewById(R.id.recyclerListDataPasienDokter);
        adapter = new ListDataPasienDokterAdapter(ListDataPasienDokterActivity.this, pasienModels, this);
        getPasien();

        btnBackListDataPasienDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getPasien(){
        queue = Volley.newRequestQueue(ListDataPasienDokterActivity.this);
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_user_pasien";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                pasienModels.add(new PasienModel(jsonObject.getString("_id"),
                                        jsonObject.getString("nama"),
                                        jsonObject.getString("username"),
                                        jsonObject.getString("password"),
                                        jsonObject.getString("aktor"),
                                        jsonObject.getString("nim"),
                                        jsonObject.getString("alamat"),
                                        jsonObject.getString("bb"),
                                        jsonObject.getString("tb")));

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
        recyclerListDataPasienDokter.setLayoutManager(new LinearLayoutManager(ListDataPasienDokterActivity.this));
        recyclerListDataPasienDokter.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(int position) {
       Intent intent = new Intent(ListDataPasienDokterActivity.this, ListDataPasienDokterDetailActivity.class);
       intent.putExtra("id", pasienModels.get(position).getId());
       intent.putExtra("nama", pasienModels.get(position).getNama());
       intent.putExtra("username", pasienModels.get(position).getUsername());
       intent.putExtra("aktor", pasienModels.get(position).getAktor());
       intent.putExtra("nim", pasienModels.get(position).getNim());
       intent.putExtra("alamat", pasienModels.get(position).getAlamat());
       intent.putExtra("bb", pasienModels.get(position).getBb());
       intent.putExtra("tb", pasienModels.get(position).getTb());
       startActivity(intent);
    }

    private void filterList(String text){
        ArrayList<PasienModel> filterArray = new ArrayList<>();
        for (PasienModel pasienModel : pasienModels){
            if (pasienModel.getNama().toLowerCase().contains(text.toLowerCase()) ||
                    pasienModel.getNim().toLowerCase().contains(text.toLowerCase())){
                filterArray.add(pasienModel);
            }
            if (filterArray.isEmpty()){
                Toast.makeText(this, "tidak ada Pasien", Toast.LENGTH_SHORT).show();
            }else {
                adapter.setFilteredModels(filterArray);
            }
        }
    }
}