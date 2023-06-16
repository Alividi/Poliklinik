package com.example.poliklinik.pasien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.poliklinik.R;
import com.example.poliklinik.dokter.JadwalKonsultasiDokterActivity;
import com.example.poliklinik.dokter.JadwalKonsultasiDokterAdapter;
import com.example.poliklinik.dokter.JadwalKonsultasiDokterModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class JadwalKonsultasiPasienActivity extends AppCompatActivity {

    ArrayList<JadwalPasienModel> jadwalKonsultasiPasienModels;
    JadwalKonsultasiPasienAdapter adapter;
    RecyclerView recyclerViewJadwalKonsultasiPasien;
    RequestQueue queueKonsultasi;
    String url = "";
    String hari, bulan, tahun, tanggal;
    String tglSkrng,hrSkrng,blnSkrng,thSkrng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_konsultasi_pasien);

        ImageButton btnBackJadwalKonsultasiPasien = findViewById(R.id.btnBackJadwalKonsultasiPasien);
        AppCompatButton btnTambahJadwalKonsultasiPasien = findViewById(R.id.btnTambahJadwalKonsultasiPasien);

        Spinner sHariJadwalKonsultasiPasien = findViewById(R.id.sHariJadwalKonsultasiPasien);
        Spinner sBulanJadwalKonsultasiPasien = findViewById(R.id.sBulanJadwalKonsultasiPasien);
        Spinner sTahunJadwalKonsultasiPasien = findViewById(R.id.sTahunJadwalKonsultasiPasien);

        tglSkrng = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        hrSkrng = tglSkrng.substring(0,2);
        blnSkrng = tglSkrng.substring(3,5);
        thSkrng = tglSkrng.substring(6,10);
        sHariJadwalKonsultasiPasien.setSelection(Integer.parseInt(hrSkrng)-1);
        switch (blnSkrng){
            case "01":
                sBulanJadwalKonsultasiPasien.setSelection(0);
                break;
            case "02":
                sBulanJadwalKonsultasiPasien.setSelection(1);
                break;
            case "03":
                sBulanJadwalKonsultasiPasien.setSelection(2);
                break;
            case "04":
                sBulanJadwalKonsultasiPasien.setSelection(3);
                break;
            case "05":
                sBulanJadwalKonsultasiPasien.setSelection(4);
                break;
            case "06":
                sBulanJadwalKonsultasiPasien.setSelection(5);
                break;
            case "07":
                sBulanJadwalKonsultasiPasien.setSelection(6);
                break;
            case "08":
                sBulanJadwalKonsultasiPasien.setSelection(7);
                break;
            case "09":
                sBulanJadwalKonsultasiPasien.setSelection(8);
                break;
            case "10":
                sBulanJadwalKonsultasiPasien.setSelection(9);
                break;
            case "11":
                sBulanJadwalKonsultasiPasien.setSelection(10);
                break;
            case "12":
                sBulanJadwalKonsultasiPasien.setSelection(11);
                break;
        }
        switch (thSkrng){
            case "2023":
                sTahunJadwalKonsultasiPasien.setSelection(0);
                break;
            case "2024":
                sTahunJadwalKonsultasiPasien.setSelection(1);
                break;
            case "2025":
                sTahunJadwalKonsultasiPasien.setSelection(2);
                break;
            case "2026":
                sTahunJadwalKonsultasiPasien.setSelection(3);
                break;
            case "2027":
                sTahunJadwalKonsultasiPasien.setSelection(4);
                break;
            case "2028":
                sTahunJadwalKonsultasiPasien.setSelection(5);
                break;
        }

        hari = sHariJadwalKonsultasiPasien.getSelectedItem().toString();
        switch (sBulanJadwalKonsultasiPasien.getSelectedItem().toString()){
            case "Januari":
                bulan = "01";
                break;
            case "Februari":
                bulan = "02";
                break;
            case "Maret":
                bulan = "03";
                break;
            case "April":
                bulan = "04";
                break;
            case "May":
                bulan = "05";
                break;
            case "Juni":
                bulan = "06";
                break;
            case "Juli":
                bulan = "07";
                break;
            case "Agustus":
                bulan = "08";
                break;
            case "September":
                bulan = "09";
                break;
            case "Oktober":
                bulan = "10";
                break;
            case "November":
                bulan = "11";
                break;
            case "Desember":
                bulan = "12";
                break;
        }
        tahun = sTahunJadwalKonsultasiPasien.getSelectedItem().toString();
        if (hari.length() == 2){
            tanggal = hari+"-"+bulan+"-"+tahun;
        }else {
            tanggal = "0"+hari+"-"+bulan+"-"+tahun;
        }

        recyclerViewJadwalKonsultasiPasien = findViewById(R.id.recyclerViewJadwalKonsultasiPasien);
        jadwalKonsultasiPasienModels = new ArrayList<>();
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_jadwal_by_tanggal?tanggal=";
        url = url+tanggal;
        adapter =  new JadwalKonsultasiPasienAdapter(this, jadwalKonsultasiPasienModels);
        getDataAPI();

        sHariJadwalKonsultasiPasien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sHariJadwalKonsultasiPasien.getSelectedItem().toString().equals(hrSkrng)){
                    Log.e("TAG", "hari udh dipilih" );
                    hrSkrng = "";
                } else{
                    jadwalKonsultasiPasienModels.clear();
                    hari = sHariJadwalKonsultasiPasien.getSelectedItem().toString();
                    if (hari.length() == 2){
                        tanggal = hari+"-"+bulan+"-"+tahun;
                    }else {
                        tanggal = "0"+hari+"-"+bulan+"-"+tahun;
                    }
                    url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_jadwal_by_tanggal?tanggal=";
                    url = url+tanggal;
                    Log.e("link api", "hari: "+url );
                    getDataAPI();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sBulanJadwalKonsultasiPasien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sBulanJadwalKonsultasiPasien.getSelectedItemPosition() == Integer.parseInt(blnSkrng)-1){
                    Log.e("TAG", "Bulan udh dipilih");
                    blnSkrng = "1";
                }else {
                    jadwalKonsultasiPasienModels.clear();
                    switch (sBulanJadwalKonsultasiPasien.getSelectedItem().toString()){
                        case "Januari":
                            bulan = "01";
                            break;
                        case "Februari":
                            bulan = "02";
                            break;
                        case "Maret":
                            bulan = "03";
                            break;
                        case "April":
                            bulan = "04";
                            break;
                        case "May":
                            bulan = "05";
                            break;
                        case "Juni":
                            bulan = "06";
                            break;
                        case "Juli":
                            bulan = "07";
                            break;
                        case "Agustus":
                            bulan = "08";
                            break;
                        case "September":
                            bulan = "09";
                            break;
                        case "Oktober":
                            bulan = "10";
                            break;
                        case "November":
                            bulan = "11";
                            break;
                        case "Desember":
                            bulan = "12";
                            break;
                    }
                    if (hari.length() == 2){
                        tanggal = hari+"-"+bulan+"-"+tahun;
                    }else {
                        tanggal = "0"+hari+"-"+bulan+"-"+tahun;
                    }
                    url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_jadwal_by_tanggal?tanggal=";
                    url = url+tanggal;
                    Log.e("link api", "bulan: "+url );
                    getDataAPI();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sTahunJadwalKonsultasiPasien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sTahunJadwalKonsultasiPasien.getSelectedItem().toString().equals(thSkrng)){
                    Log.e("TAG", "Tahun udh dipilih: ");
                    thSkrng = "";
                }else {
                    jadwalKonsultasiPasienModels.clear();
                    tahun = sTahunJadwalKonsultasiPasien.getSelectedItem().toString();
                    if (hari.length() == 2){
                        tanggal = hari+"-"+bulan+"-"+tahun;
                    }else {
                        tanggal = "0"+hari+"-"+bulan+"-"+tahun;
                    }
                    url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_jadwal_by_tanggal?tanggal=";
                    url = url+tanggal;
                    Log.e("link api", "tahun: "+url );
                    getDataAPI();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnTambahJadwalKonsultasiPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JadwalKonsultasiPasienActivity.this,TambahJadwalKonsultasiPasienActivity.class);
                finish();
                startActivity(intent);
            }
        });
        btnBackJadwalKonsultasiPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JadwalKonsultasiPasienActivity.this,PasienMainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void getDataAPI(){
        queueKonsultasi = Volley.newRequestQueue(JadwalKonsultasiPasienActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                jadwalKonsultasiPasienModels.add(new JadwalPasienModel(jsonObject.getString("_id"),
                                        jsonObject.getString("jam"),
                                        jsonObject.getString("nama_pasien"),
                                        jsonObject.getString("nama_dokter"),
                                        jsonObject.getString("kategori"),
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
        queueKonsultasi.add(jsonArrayRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initRecyclerView(){
        recyclerViewJadwalKonsultasiPasien.setAdapter(adapter);
        recyclerViewJadwalKonsultasiPasien.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

}