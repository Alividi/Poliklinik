package com.example.poliklinik.pasien;

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
import com.example.poliklinik.dokter.PasienModel;
import com.example.poliklinik.dokter.TambahJadwalKonsultasiDokterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TambahJadwalKonsultasiPasienActivity extends AppCompatActivity {
    TextView sKategoriTambahJadwalPasien;
    EditText etTanggalKonsultasiTambahJadwalPasien;
    Spinner sJamKonsultasiTambahJadwalPasien;
    TextView sNamaPasienTambahJadwalPasien;
    Spinner sNamaDokterTambahJadwalPasien;
    ArrayList<DokterModel> dokterModels;
    RequestQueue queue;
    String url = "";
    ArrayAdapter<String> sAdapter;
    SharedPreferences loginPreferences;
    String tglSkrng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal_konsultasi_pasien);

        TextView btnCancelTambahJadwalKonsultasiPasien = findViewById(R.id.btnCancelTambahJadwalKonsultasiPasien);
        ImageButton btnBackTambahJadwalKonsultasiPasien = findViewById(R.id.btnBackTambahJadwalKonsultasiPasien);
        AppCompatButton btnTambahKonsultasiPasien = findViewById(R.id.btnTambahKonsultasiPasien);
        sKategoriTambahJadwalPasien = findViewById(R.id.sKategoriTambahJadwalPasien);
        etTanggalKonsultasiTambahJadwalPasien = findViewById(R.id.etTanggalKonsultasiTambahJadwalPasien);
        sJamKonsultasiTambahJadwalPasien =findViewById(R.id.sJamKonsultasiTambahJadwalPasien);
        sNamaPasienTambahJadwalPasien = findViewById(R.id.sNamaPasienTambahJadwalPasien);
        sNamaDokterTambahJadwalPasien = findViewById(R.id.sNamaDokterTambahJadwalPasien);

        loginPreferences = this.getSharedPreferences("LoginPrefs", 0);
        sNamaPasienTambahJadwalPasien.setText(loginPreferences.getString("nama","-"));
        dokterModels = new ArrayList<>();
        getDokter();
        sAdapter = new ArrayAdapter<>(TambahJadwalKonsultasiPasienActivity.this, R.layout.spinner_item_style);

        tglSkrng = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        etTanggalKonsultasiTambahJadwalPasien.setText(tglSkrng);

        btnTambahKonsultasiPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahJadwal("Konsultasi",
                        etTanggalKonsultasiTambahJadwalPasien.getText().toString(),
                        sJamKonsultasiTambahJadwalPasien.getSelectedItem().toString(),
                        sNamaPasienTambahJadwalPasien.getText().toString(),
                        sNamaDokterTambahJadwalPasien.getSelectedItem().toString(),
                        "menunggu");
                Intent intent = new Intent(TambahJadwalKonsultasiPasienActivity.this, JadwalKonsultasiPasienActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btnCancelTambahJadwalKonsultasiPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTanggalKonsultasiTambahJadwalPasien.setText("");
                sJamKonsultasiTambahJadwalPasien.setSelection(0);
                sNamaDokterTambahJadwalPasien.setSelection(0);
            }
        });

        btnBackTambahJadwalKonsultasiPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahJadwalKonsultasiPasienActivity.this, JadwalKonsultasiPasienActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void getDokter(){
        queue = Volley.newRequestQueue(TambahJadwalKonsultasiPasienActivity.this);
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_user_dokter";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                dokterModels.add(new DokterModel(jsonObject.getString("nama")));
                                Log.e("TAG", jsonObject.getString("_id")+" onResponse: "+jsonObject.getString("nama") );
                                sAdapter.setDropDownViewResource(R.layout.spinner_item_style);
                                sNamaDokterTambahJadwalPasien.setAdapter(sAdapter);
                                sAdapter.add(dokterModels.get(i).getNama());
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

    public void tambahJadwal(final String kategori, final String tanggal, final String waktu,
                             final String nama_pasien, final String nama_dokter, final String status){
        queue = Volley.newRequestQueue(TambahJadwalKonsultasiPasienActivity.this);
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/insert_jadwal";

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
                MyData.put("kategori", kategori);
                MyData.put("tanggal", tanggal);
                MyData.put("jam", waktu);
                MyData.put("nama_pasien", nama_pasien);
                MyData.put("nama_dokter", nama_dokter);
                MyData.put("status", status);
                return MyData;
            }
        };
        queue.add(MyStringRequest);
    }
}