package com.example.poliklinik.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
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
import com.android.volley.toolbox.JsonObjectRequest;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class TambahJadwalKonsultasiDokterActivity extends AppCompatActivity {

    TextView sKategoriTambahJadwalKonsulDokter;
    EditText etTanggalKonsultasiTambahJadwalDokter;
    Spinner sJamKonsultasiTambahJadwalDokter;
    Spinner sNamaPasienTambahJadwalKonsulDokter;
    TextView sNamaDokterTambahJadwalKonsulDokter;
    ArrayList<PasienModel> pasienModels;
    RequestQueue queue;
    String url = "";
    ArrayAdapter<String> sAdapter;
    SharedPreferences loginPreferences;
    String tglSkrng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal_konsultasi_dokter);

        TextView btnCancelTambahJadwalKonsultasiDokter = findViewById(R.id.btnCancelTambahJadwalKonsultasiDokter);
        ImageButton btnBackTambahJadwalKonsultasiDokter = findViewById(R.id.btnBackTambahJadwalKonsultasiDokter);
        AppCompatButton btnTambahKonsultasiDokter = findViewById(R.id.btnTambahKonsultasiDokter);
        sKategoriTambahJadwalKonsulDokter = findViewById(R.id.sKategoriTambahJadwalKonsulDokter);
        etTanggalKonsultasiTambahJadwalDokter = findViewById(R.id.etTanggalKonsultasiTambahJadwalDokter);
        sJamKonsultasiTambahJadwalDokter = findViewById(R.id.sJamKonsultasiTambahJadwalDokter);
        sNamaPasienTambahJadwalKonsulDokter = findViewById(R.id.sNamaPasienTambahJadwalKonsulDokter);
        sNamaDokterTambahJadwalKonsulDokter = findViewById(R.id.sNamaDokterTambahJadwalKonsulDokter);

        loginPreferences = this.getSharedPreferences("LoginPrefs", 0);
        sNamaDokterTambahJadwalKonsulDokter.setText(loginPreferences.getString("nama","-"));
        pasienModels = new ArrayList<>();
        getPasien();
        sAdapter = new ArrayAdapter<>(TambahJadwalKonsultasiDokterActivity.this, R.layout.spinner_item_style);

        tglSkrng = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        etTanggalKonsultasiTambahJadwalDokter.setText(tglSkrng);

        btnTambahKonsultasiDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahJadwal("Konsultasi",
                        etTanggalKonsultasiTambahJadwalDokter.getText().toString(),
                        sJamKonsultasiTambahJadwalDokter.getSelectedItem().toString(),
                        sNamaPasienTambahJadwalKonsulDokter.getSelectedItem().toString(),
                        sNamaDokterTambahJadwalKonsulDokter.getText().toString(),
                        "menunggu");
                Intent intent = new Intent(TambahJadwalKonsultasiDokterActivity.this, JadwalKonsultasiDokterActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btnBackTambahJadwalKonsultasiDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahJadwalKonsultasiDokterActivity.this, JadwalKonsultasiDokterActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btnCancelTambahJadwalKonsultasiDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTanggalKonsultasiTambahJadwalDokter.setText("");
                sJamKonsultasiTambahJadwalDokter.setSelection(0);
                sNamaPasienTambahJadwalKonsulDokter.setSelection(0);
            }
        });
    }
    public void getPasien(){
        queue = Volley.newRequestQueue(TambahJadwalKonsultasiDokterActivity.this);
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
                                sAdapter.setDropDownViewResource(R.layout.spinner_item_style);
                                sNamaPasienTambahJadwalKonsulDokter.setAdapter(sAdapter);
                                sAdapter.add(pasienModels.get(i).getNama());
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
        queue = Volley.newRequestQueue(TambahJadwalKonsultasiDokterActivity.this);
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