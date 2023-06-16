package com.example.poliklinik.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.poliklinik.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TambahRekamMedisDokterActivity extends AppCompatActivity {

    Spinner sNamaPasienTmbhRekamMedisDokter;
    EditText etTanggalTmbhRekamMedisDokter;
    EditText etNomorTmbhRekamMedisDokter;
    EditText etKeluhanTmbhRekamMedisDokter;
    TextView sNamaDokterTmbhRekamMedisDokter;
    EditText etDiagnosaTmbhRekamMedisDokter;
    Spinner sObat1TmbhRekamMedisDokter;
    EditText etBnykObat1TmbhRekamMedisDokter;
    ImageView ivTmbhObat1TmbhRekamMedisDokter;
    Spinner sObat2TmbhRekamMedisDokter;
    EditText etBnykObat2TmbhRekamMedisDokter;
    ImageView ivTmbhObat2TmbhRekamMedisDokter;
    Spinner sObat3TmbhRekamMedisDokter;
    EditText etBnykObat3TmbhRekamMedisDokter;
    ImageView ivTmbhObat3TmbhRekamMedisDokter;
    Spinner sObat4TmbhRekamMedisDokter;
    EditText etBnykObat4TmbhRekamMedisDokter;
    ImageView ivTmbhObat4TmbhRekamMedisDokter;
    Spinner sObat5TmbhRekamMedisDokter;
    EditText etBnykObat5TmbhRekamMedisDokter;
    TextView btnCancelTmbhRekamMedisDokter;
    AppCompatButton btnTambahRekamMedisDokter;
    ImageButton btnBackTambahRekamMedisDokter;
    RequestQueue queue;
    String url = "";
    ArrayAdapter<String> sAdapter;
    ArrayList<ObatModel> obatModels;
    ArrayList<PasienModel> pasienModels;
    ArrayAdapter<String> sAdapterP;
    ArrayList<RekamMedisDokterModel> rekamMedisDokterModels;
    SharedPreferences loginPreferences;
    String nmPasien = "", tanggal = "", noRM = "", keluhan = "", nmDokter = "", diagnosa = "", id = "";
    String obat1 = "", jml1 = "", obat2 = "", jml2 = "", obat3 = "", jml3 = "", obat4 = "", jml4 = "", obat5 = "", jml5 = "";
    String tglSkrng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_rekam_medis_dokter);

        tglSkrng = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        loginPreferences = this.getSharedPreferences("LoginPrefs", 0);
        btnBackTambahRekamMedisDokter = findViewById(R.id.btnBackTambahRekamMedisDokter);
        sNamaPasienTmbhRekamMedisDokter = findViewById(R.id.sNamaPasienTmbhRekamMedisDokter);
        etTanggalTmbhRekamMedisDokter = findViewById(R.id.etTanggalTmbhRekamMedisDokter);
        etTanggalTmbhRekamMedisDokter.setText(tglSkrng);
        etNomorTmbhRekamMedisDokter = findViewById(R.id.etNomorTmbhRekamMedisDokter);
        etKeluhanTmbhRekamMedisDokter = findViewById(R.id.etKeluhanTmbhRekamMedisDokter);
        sNamaDokterTmbhRekamMedisDokter = findViewById(R.id.sNamaDokterTmbhRekamMedisDokter);
        sNamaDokterTmbhRekamMedisDokter.setText(loginPreferences.getString("nama","-"));
        etDiagnosaTmbhRekamMedisDokter = findViewById(R.id.etDiagnosaTmbhRekamMedisDokter);
        sObat1TmbhRekamMedisDokter = findViewById(R.id.sObat1TmbhRekamMedisDokter);
        etBnykObat1TmbhRekamMedisDokter = findViewById(R.id.etBnykObat1TmbhRekamMedisDokter);
        ivTmbhObat1TmbhRekamMedisDokter = findViewById(R.id.ivTmbhObat1TmbhRekamMedisDokter);
        sObat2TmbhRekamMedisDokter = findViewById(R.id.sObat2TmbhRekamMedisDokter);
        etBnykObat2TmbhRekamMedisDokter = findViewById(R.id.etBnykObat2TmbhRekamMedisDokter);
        ivTmbhObat2TmbhRekamMedisDokter = findViewById(R.id.ivTmbhObat2TmbhRekamMedisDokter);
        sObat3TmbhRekamMedisDokter = findViewById(R.id.sObat3TmbhRekamMedisDokter);
        etBnykObat3TmbhRekamMedisDokter = findViewById(R.id.etBnykObat3TmbhRekamMedisDokter);
        ivTmbhObat3TmbhRekamMedisDokter = findViewById(R.id.ivTmbhObat3TmbhRekamMedisDokter);
        sObat4TmbhRekamMedisDokter = findViewById(R.id.sObat4TmbhRekamMedisDokter);
        etBnykObat4TmbhRekamMedisDokter = findViewById(R.id.etBnykObat4TmbhRekamMedisDokter);
        ivTmbhObat4TmbhRekamMedisDokter = findViewById(R.id.ivTmbhObat4TmbhRekamMedisDokter);
        sObat5TmbhRekamMedisDokter = findViewById(R.id.sObat5TmbhRekamMedisDokter);
        etBnykObat5TmbhRekamMedisDokter = findViewById(R.id.etBnykObat5TmbhRekamMedisDokter);
        btnCancelTmbhRekamMedisDokter = findViewById(R.id.btnCancelTmbhRekamMedisDokter);
        btnTambahRekamMedisDokter = findViewById(R.id.btnTambahRekamMedisDokter);

        rekamMedisDokterModels = new ArrayList<>();
        getLastRM();
        obatModels = new ArrayList<>();
        getObat();
        sAdapter = new ArrayAdapter<>(TambahRekamMedisDokterActivity.this, R.layout.spinner_item_style);
        pasienModels = new ArrayList<>();
        getPasien();
        sAdapterP = new ArrayAdapter<>(TambahRekamMedisDokterActivity.this, R.layout.spinner_item_style);


        btnTambahRekamMedisDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nmPasien = sNamaPasienTmbhRekamMedisDokter.getSelectedItem().toString();
                tanggal = etTanggalTmbhRekamMedisDokter.getText().toString();
                id = etNomorTmbhRekamMedisDokter.getText().toString().substring(3);
                keluhan = etKeluhanTmbhRekamMedisDokter.getText().toString();
                nmDokter = sNamaDokterTmbhRekamMedisDokter.getText().toString();
                diagnosa = etDiagnosaTmbhRekamMedisDokter.getText().toString();
                obat1 = sObat1TmbhRekamMedisDokter.getSelectedItem().toString();
                jml1 = etBnykObat1TmbhRekamMedisDokter.getText().toString();

                if(sObat2TmbhRekamMedisDokter.getVisibility() == View.VISIBLE){
                    obat2 = sObat2TmbhRekamMedisDokter.getSelectedItem().toString();
                    jml2 = etBnykObat2TmbhRekamMedisDokter.getText().toString();
                }else {
                    obat2 = "";
                    jml2 = "";
                }
                if(sObat3TmbhRekamMedisDokter.getVisibility() == View.VISIBLE){
                    obat3 = sObat3TmbhRekamMedisDokter.getSelectedItem().toString();
                    jml3 = etBnykObat3TmbhRekamMedisDokter.getText().toString();
                }else {
                    obat3 = "";
                    jml3 = "";
                }
                if(sObat4TmbhRekamMedisDokter.getVisibility() == View.VISIBLE){
                    obat4 = sObat4TmbhRekamMedisDokter.getSelectedItem().toString();
                    jml4 = etBnykObat4TmbhRekamMedisDokter.getText().toString();
                }else {
                    obat4 = "";
                    jml4 = "";
                }
                if(sObat5TmbhRekamMedisDokter.getVisibility() == View.VISIBLE){
                    obat5 = sObat5TmbhRekamMedisDokter.getSelectedItem().toString();
                    jml5 = etBnykObat5TmbhRekamMedisDokter.getText().toString();
                }else {
                    obat5 = "";
                    jml5 = "";
                }

                tambahRM(id, tanggal, diagnosa, keluhan, nmPasien,
                        nmDokter, "Belum Diizinkan", obat1, jml1,
                        obat2, jml2, obat3, jml3, obat4, jml4, obat5, jml5);

                Intent intent = new Intent(TambahRekamMedisDokterActivity.this, RekamMedisDokterActivity.class);
                finish();
                startActivity(intent);
            }
        });

        ivTmbhObat1TmbhRekamMedisDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sObat2TmbhRekamMedisDokter.getVisibility() == View.INVISIBLE){
                    sObat2TmbhRekamMedisDokter.setVisibility(View.VISIBLE);
                    etBnykObat2TmbhRekamMedisDokter.setVisibility(View.VISIBLE);
                    ivTmbhObat2TmbhRekamMedisDokter.setVisibility(View.VISIBLE);
                    ivTmbhObat1TmbhRekamMedisDokter.setImageResource(R.drawable.round_close_abu_obat);
                    ivTmbhObat2TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                    ivTmbhObat3TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                    ivTmbhObat4TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                } else {
                    sObat2TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    etBnykObat2TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    ivTmbhObat2TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    sObat3TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    etBnykObat3TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    ivTmbhObat3TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    sObat4TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    etBnykObat4TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    ivTmbhObat4TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    sObat5TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    etBnykObat5TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    ivTmbhObat1TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                    ivTmbhObat2TmbhRekamMedisDokter.setImageResource(R.drawable.round_close_abu_obat);
                    ivTmbhObat3TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                    ivTmbhObat4TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                }
            }
        });

        ivTmbhObat2TmbhRekamMedisDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sObat3TmbhRekamMedisDokter.getVisibility() == View.INVISIBLE){
                    sObat3TmbhRekamMedisDokter.setVisibility(View.VISIBLE);
                    etBnykObat3TmbhRekamMedisDokter.setVisibility(View.VISIBLE);
                    ivTmbhObat3TmbhRekamMedisDokter.setVisibility(View.VISIBLE);
                    ivTmbhObat2TmbhRekamMedisDokter.setImageResource(R.drawable.round_close_abu_obat);
                    ivTmbhObat3TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                    ivTmbhObat4TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                } else {
                    sObat3TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    etBnykObat3TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    ivTmbhObat3TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    sObat4TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    etBnykObat4TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    ivTmbhObat4TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    sObat5TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    etBnykObat5TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    ivTmbhObat2TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                    ivTmbhObat3TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                    ivTmbhObat4TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                }
            }
        });

        ivTmbhObat3TmbhRekamMedisDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sObat4TmbhRekamMedisDokter.getVisibility() == View.INVISIBLE){
                    sObat4TmbhRekamMedisDokter.setVisibility(View.VISIBLE);
                    etBnykObat4TmbhRekamMedisDokter.setVisibility(View.VISIBLE);
                    ivTmbhObat4TmbhRekamMedisDokter.setVisibility(View.VISIBLE);
                    ivTmbhObat3TmbhRekamMedisDokter.setImageResource(R.drawable.round_close_abu_obat);
                    ivTmbhObat4TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                } else {
                    sObat4TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    etBnykObat4TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    ivTmbhObat4TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    sObat5TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    etBnykObat5TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    ivTmbhObat3TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                    ivTmbhObat4TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                }

            }
        });

        ivTmbhObat4TmbhRekamMedisDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sObat5TmbhRekamMedisDokter.getVisibility() == View.INVISIBLE){
                    sObat5TmbhRekamMedisDokter.setVisibility(View.VISIBLE);
                    etBnykObat5TmbhRekamMedisDokter.setVisibility(View.VISIBLE);
                    ivTmbhObat4TmbhRekamMedisDokter.setImageResource(R.drawable.round_close_abu_obat);
                } else {
                    sObat5TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    etBnykObat5TmbhRekamMedisDokter.setVisibility(View.INVISIBLE);
                    ivTmbhObat4TmbhRekamMedisDokter.setImageResource(R.drawable.round_add_abu_obat);
                }
            }
        });

        btnBackTambahRekamMedisDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahRekamMedisDokterActivity.this, RekamMedisDokterActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btnCancelTmbhRekamMedisDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sNamaPasienTmbhRekamMedisDokter.setSelection(0);
                etTanggalTmbhRekamMedisDokter.setText("");
                etKeluhanTmbhRekamMedisDokter.setText("");
                etDiagnosaTmbhRekamMedisDokter.setText("");
                sObat1TmbhRekamMedisDokter.setSelection(0);
                etBnykObat1TmbhRekamMedisDokter.setText("");
                sObat2TmbhRekamMedisDokter.setSelection(0);
                etBnykObat2TmbhRekamMedisDokter.setText("");
                sObat3TmbhRekamMedisDokter.setSelection(0);
                etBnykObat3TmbhRekamMedisDokter.setText("");
                sObat4TmbhRekamMedisDokter.setSelection(0);
                etBnykObat4TmbhRekamMedisDokter.setText("");
                sObat5TmbhRekamMedisDokter.setSelection(0);
                etBnykObat5TmbhRekamMedisDokter.setText("");
            }
        });

    }
    public void getObat(){
        queue = Volley.newRequestQueue(TambahRekamMedisDokterActivity.this);
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_obat";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                obatModels.add(new ObatModel(jsonObject.getString("nama_obat")));
                                Log.e("TAG", jsonObject.getString("_id")+" onResponse: "+jsonObject.getString("nama_obat") );
                                sAdapter.setDropDownViewResource(R.layout.spinner_item_style);
                                sObat1TmbhRekamMedisDokter.setAdapter(sAdapter);
                                sObat2TmbhRekamMedisDokter.setAdapter(sAdapter);
                                sObat3TmbhRekamMedisDokter.setAdapter(sAdapter);
                                sObat4TmbhRekamMedisDokter.setAdapter(sAdapter);
                                sObat5TmbhRekamMedisDokter.setAdapter(sAdapter);
                                sAdapter.add(obatModels.get(i).getNmObat());
                                sAdapter.notifyDataSetChanged();
                            }

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

    public void getPasien(){
        queue = Volley.newRequestQueue(TambahRekamMedisDokterActivity.this);
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_user_pasien";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                pasienModels.add(new PasienModel(jsonObject.getString("nama")));
                                Log.e("TAG", jsonObject.getString("_id")+" onResponse: "+jsonObject.getString("nama") );
                                sAdapterP.setDropDownViewResource(R.layout.spinner_item_style);
                                sNamaPasienTmbhRekamMedisDokter.setAdapter(sAdapterP);
                                sAdapterP.add(pasienModels.get(i).getNama());
                                sAdapterP.notifyDataSetChanged();
                            }

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

    public void getLastRM(){
        queue = Volley.newRequestQueue(TambahRekamMedisDokterActivity.this);
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_last_rekam_medis";

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
                                noRM = Integer.toString(Integer.parseInt(rekamMedisDokterModels.get(i).getIdRM().substring(3)) + 1);
                                etNomorTmbhRekamMedisDokter.setText("NRM"+noRM);
                            }
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

    public void tambahRM(final String id, final String tanggal, final String diagnosa, final String keluhan,
                         final String nmPasien, final String nmDokter, final String status, final String obat1,
                         final String jml1, final String obat2, final String jml2, final String obat3,
                         final String jml3, final String obat4, final String jml4, final String obat5, final String jml5){
        queue = Volley.newRequestQueue(TambahRekamMedisDokterActivity.this);
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/insert_rekam_medis";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse: "+response );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getStackTrace();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("id", id);
                MyData.put("tanggal", tanggal);
                MyData.put("diagnosa", diagnosa);
                MyData.put("keluhan", keluhan);
                MyData.put("nama_pasien", nmPasien);
                MyData.put("nama_dokter", nmDokter);
                MyData.put("status", status);
                MyData.put("obat", obat1);
                MyData.put("jumlah", jml1);
                MyData.put("obat2", obat2);
                MyData.put("jumlah2", jml2);
                MyData.put("obat3", obat3);
                MyData.put("jumlah3", jml3);
                MyData.put("obat4", obat4);
                MyData.put("jumlah4", jml4);
                MyData.put("obat5", obat5);
                MyData.put("jumlah5", jml5);
                return MyData;
            }
        };
        queue.add(MyStringRequest);
    }
}