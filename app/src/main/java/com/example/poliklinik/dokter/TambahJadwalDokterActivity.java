package com.example.poliklinik.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TambahJadwalDokterActivity extends AppCompatActivity {
    Spinner sKategoriTambahJadwalDokter;
    EditText etTanggalTambahJadwalDokter;
    Spinner sJamTambahJadwalDokter;
    Spinner sNamaPasienTambahJadwalDokter;
    TextView sNamaDokterTambahJadwalDokter;
    ArrayList<PasienModel> pasienModels;
    RequestQueue queue;
    String url = "";
    ArrayAdapter<String> sAdapter,skAdapter;
    String[] jamJadwal;
    SharedPreferences loginPreferences;
    String tglSkrng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal_dokter);

        TextView btnCancelTambahJadwalDokter = findViewById(R.id.btnCancelTambahJadwalDokter);
        ImageButton btnBackTambahJadwalDokter = findViewById(R.id.btnBackTambahJadwalDokter);
        AppCompatButton btnTambahDokter = findViewById(R.id.btnTambahDokter);
        sKategoriTambahJadwalDokter = findViewById(R.id.sKategoriTambahJadwalDokter);
        etTanggalTambahJadwalDokter = findViewById(R.id.etTanggalTambahJadwalDokter);
        sJamTambahJadwalDokter = findViewById(R.id.sJamTambahJadwalDokter);
        sNamaPasienTambahJadwalDokter = findViewById(R.id.sNamaPasienTambahJadwalDokter);
        sNamaDokterTambahJadwalDokter = findViewById(R.id.sNamaDokterTambahJadwalDokter);

        loginPreferences = this.getSharedPreferences("LoginPrefs", 0);
        sNamaDokterTambahJadwalDokter.setText(loginPreferences.getString("nama","-"));
        pasienModels = new ArrayList<>();
        getPasien();
        sAdapter = new ArrayAdapter<>(TambahJadwalDokterActivity.this, R.layout.spinner_item_style);

        tglSkrng = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        etTanggalTambahJadwalDokter.setText(tglSkrng);

        sKategoriTambahJadwalDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sKategoriTambahJadwalDokter.getSelectedItem().toString().equals("Konsultasi")){
                    jamJadwal = getResources().getStringArray(R.array.JamKonsultasi);
                    skAdapter = new ArrayAdapter<String>(TambahJadwalDokterActivity.this,
                            R.layout.spinner_item_style,jamJadwal);
                    sJamTambahJadwalDokter.setAdapter(skAdapter);
                    skAdapter.notifyDataSetChanged();
                }
                if (sKategoriTambahJadwalDokter.getSelectedItem().toString().equals("Medical Check Up")){
                    jamJadwal = getResources().getStringArray(R.array.JamCheckup);
                    skAdapter = new ArrayAdapter<String>(TambahJadwalDokterActivity.this,
                            R.layout.spinner_item_style,jamJadwal);
                    sJamTambahJadwalDokter.setAdapter(skAdapter);
                    skAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnTambahDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahJadwal(sKategoriTambahJadwalDokter.getSelectedItem().toString(),
                        etTanggalTambahJadwalDokter.getText().toString(),
                        sJamTambahJadwalDokter.getSelectedItem().toString(),
                        sNamaPasienTambahJadwalDokter.getSelectedItem().toString(),
                        sNamaDokterTambahJadwalDokter.getText().toString(),
                        "menunggu");
                if(sKategoriTambahJadwalDokter.getSelectedItem().toString().equals("Konsultasi")){
                    Intent intent = new Intent(TambahJadwalDokterActivity.this, JadwalKonsultasiDokterActivity.class);
                    finish();
                    startActivity(intent);
                }
                if(sKategoriTambahJadwalDokter.getSelectedItem().toString().equals("Medical Check Up")){
                    Intent intent = new Intent(TambahJadwalDokterActivity.this, JadwalCheckUpDokterActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });

        btnBackTambahJadwalDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCancelTambahJadwalDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sKategoriTambahJadwalDokter.setSelection(0);
                etTanggalTambahJadwalDokter.setText("");
                sJamTambahJadwalDokter.setSelection(0);
                sNamaPasienTambahJadwalDokter.setSelection(0);
            }
        });
    }
    public void getPasien(){
        queue = Volley.newRequestQueue(TambahJadwalDokterActivity.this);
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
                                sNamaPasienTambahJadwalDokter.setAdapter(sAdapter);
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
        queue = Volley.newRequestQueue(TambahJadwalDokterActivity.this);
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