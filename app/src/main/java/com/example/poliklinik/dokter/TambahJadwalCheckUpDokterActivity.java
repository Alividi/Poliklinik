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

public class TambahJadwalCheckUpDokterActivity extends AppCompatActivity {
    TextView sKategoriTambahJadwalCheckUpDokter;
    EditText etTanggalCheckUpTambahJadwalDokter;
    Spinner sJamCheckUpTambahJadwalDokter;
    Spinner sNamaPasienTambahJadwalCheckUpDokter;
    TextView sNamaDokterTambahJadwalCheckUpDokter;
    ArrayList<PasienModel> pasienModels;
    RequestQueue queue;
    String url = "";
    ArrayAdapter<String> sAdapter;
    SharedPreferences loginPreferences;
    String tglSkrng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jadwal_check_up_dokter);

        TextView btnCancelTambahJadwalCheckUpDokter = findViewById(R.id.btnCancelTambahJadwalCheckUpDokter);
        ImageButton btnBackTambahJadwalCheckUpDokter = findViewById(R.id.btnBackTambahJadwalCheckUpDokter);
        AppCompatButton btnTambahCheckUpDokter = findViewById(R.id.btnTambahCheckUpDokter);
        sKategoriTambahJadwalCheckUpDokter = findViewById(R.id.sKategoriTambahJadwalCheckUpDokter);
        etTanggalCheckUpTambahJadwalDokter = findViewById(R.id.etTanggalCheckUpTambahJadwalDokter);
        sJamCheckUpTambahJadwalDokter = findViewById(R.id.sJamCheckUpTambahJadwalDokter);
        sNamaPasienTambahJadwalCheckUpDokter = findViewById(R.id.sNamaPasienTambahJadwalCheckUpDokter);
        sNamaDokterTambahJadwalCheckUpDokter = findViewById(R.id.sNamaDokterTambahJadwalCheckUpDokter);

        loginPreferences = this.getSharedPreferences("LoginPrefs", 0);
        sNamaDokterTambahJadwalCheckUpDokter.setText(loginPreferences.getString("nama","-"));
        pasienModels = new ArrayList<>();
        getPasien();
        sAdapter = new ArrayAdapter<>(TambahJadwalCheckUpDokterActivity.this, R.layout.spinner_item_style);

        tglSkrng = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        etTanggalCheckUpTambahJadwalDokter.setText(tglSkrng);

        btnTambahCheckUpDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahJadwal("Medical Check Up",
                        etTanggalCheckUpTambahJadwalDokter.getText().toString(),
                        sJamCheckUpTambahJadwalDokter.getSelectedItem().toString(),
                        sNamaPasienTambahJadwalCheckUpDokter.getSelectedItem().toString(),
                        sNamaDokterTambahJadwalCheckUpDokter.getText().toString(),
                        "menunggu");
                Intent intent = new Intent(TambahJadwalCheckUpDokterActivity.this, JadwalCheckUpDokterActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btnBackTambahJadwalCheckUpDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahJadwalCheckUpDokterActivity.this, JadwalCheckUpDokterActivity.class);
                finish();
                startActivity(intent);
            }
        });
        btnCancelTambahJadwalCheckUpDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTanggalCheckUpTambahJadwalDokter.setText("");
                sJamCheckUpTambahJadwalDokter.setSelection(0);
                sNamaPasienTambahJadwalCheckUpDokter.setSelection(0);
            }
        });
    }
    public void getPasien(){
        queue = Volley.newRequestQueue(TambahJadwalCheckUpDokterActivity.this);
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
                                sNamaPasienTambahJadwalCheckUpDokter.setAdapter(sAdapter);
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
        queue = Volley.newRequestQueue(TambahJadwalCheckUpDokterActivity.this);
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