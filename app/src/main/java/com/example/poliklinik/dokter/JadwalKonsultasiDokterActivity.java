package com.example.poliklinik.dokter;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class JadwalKonsultasiDokterActivity extends AppCompatActivity {

    ArrayList<JadwalKonsultasiDokterModel> jadwalKonsultasiDokterModels;
    JadwalKonsultasiDokterAdapter adapter;
    RecyclerView recyclerViewJadwalKonsultasiDokter;
    RequestQueue queueKonsultasi;
    String url = "";
    String hari, bulan, tahun, tanggal;
    String tglSkrng,hrSkrng,blnSkrng,thSkrng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_konsultasi_dokter);

        ImageButton btnBackJadwalKonsultasiDokter = findViewById(R.id.btnBackJadwalKonsultasiDokter);
        AppCompatButton btnTambahJadwalKonsultasiDokter = findViewById(R.id.btnTambahJadwalKonsultasiDokter);

        Spinner sHariJadwalKonsultasiDokter = findViewById(R.id.sHariJadwalKonsultasiDokter);
        Spinner sBulanJadwalKonsultasiDokter = findViewById(R.id.sBulanJadwalKonsultasiDokter);
        Spinner sTahunJadwalKonsultasiDokter = findViewById(R.id.sTahunJadwalKonsultasiDokter);

        tglSkrng = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        hrSkrng = tglSkrng.substring(0,2);
        blnSkrng = tglSkrng.substring(3,5);
        thSkrng = tglSkrng.substring(6,10);
        sHariJadwalKonsultasiDokter.setSelection(Integer.parseInt(hrSkrng)-1);
        switch (blnSkrng){
            case "01":
                sBulanJadwalKonsultasiDokter.setSelection(0);
                break;
            case "02":
                sBulanJadwalKonsultasiDokter.setSelection(1);
                break;
            case "03":
                sBulanJadwalKonsultasiDokter.setSelection(2);
                break;
            case "04":
                sBulanJadwalKonsultasiDokter.setSelection(3);
                break;
            case "05":
                sBulanJadwalKonsultasiDokter.setSelection(4);
                break;
            case "06":
                sBulanJadwalKonsultasiDokter.setSelection(5);
                break;
            case "07":
                sBulanJadwalKonsultasiDokter.setSelection(6);
                break;
            case "08":
                sBulanJadwalKonsultasiDokter.setSelection(7);
                break;
            case "09":
                sBulanJadwalKonsultasiDokter.setSelection(8);
                break;
            case "10":
                sBulanJadwalKonsultasiDokter.setSelection(9);
                break;
            case "11":
                sBulanJadwalKonsultasiDokter.setSelection(10);
                break;
            case "12":
                sBulanJadwalKonsultasiDokter.setSelection(11);
                break;
        }
        switch (thSkrng){
            case "2023":
                sTahunJadwalKonsultasiDokter.setSelection(0);
                break;
            case "2024":
                sTahunJadwalKonsultasiDokter.setSelection(1);
                break;
            case "2025":
                sTahunJadwalKonsultasiDokter.setSelection(2);
                break;
            case "2026":
                sTahunJadwalKonsultasiDokter.setSelection(3);
                break;
            case "2027":
                sTahunJadwalKonsultasiDokter.setSelection(4);
                break;
            case "2028":
                sTahunJadwalKonsultasiDokter.setSelection(5);
                break;
        }

        hari = sHariJadwalKonsultasiDokter.getSelectedItem().toString();
        switch (sBulanJadwalKonsultasiDokter.getSelectedItem().toString()){
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
        tahun = sTahunJadwalKonsultasiDokter.getSelectedItem().toString();
        if (hari.length() == 2){
            tanggal = hari+"-"+bulan+"-"+tahun;
        }else {
            tanggal = "0"+hari+"-"+bulan+"-"+tahun;
        }


        recyclerViewJadwalKonsultasiDokter = findViewById(R.id.recyclerViewJadwalKonsultasiDokter);
        jadwalKonsultasiDokterModels = new ArrayList<>();
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_jadwal_by_tanggal?tanggal=";
        url = url+tanggal;
        adapter =  new JadwalKonsultasiDokterAdapter(this, jadwalKonsultasiDokterModels);
        getDataAPI();

        sHariJadwalKonsultasiDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sHariJadwalKonsultasiDokter.getSelectedItem().toString().equals(hrSkrng)){
                    Log.e("TAG", "hari udh dipilih" );
                    hrSkrng = "";
                } else{
                    jadwalKonsultasiDokterModels.clear();
                    hari = sHariJadwalKonsultasiDokter.getSelectedItem().toString();
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

        sBulanJadwalKonsultasiDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sBulanJadwalKonsultasiDokter.getSelectedItemPosition() == Integer.parseInt(blnSkrng)-1){
                    Log.e("TAG", "Bulan udh dipilih");
                    blnSkrng = "1";
                }else {
                    jadwalKonsultasiDokterModels.clear();
                    switch (sBulanJadwalKonsultasiDokter.getSelectedItem().toString()){
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

        sTahunJadwalKonsultasiDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sTahunJadwalKonsultasiDokter.getSelectedItem().toString().equals(thSkrng)){
                    Log.e("TAG", "Tahun udh dipilih: ");
                    thSkrng = "";
                }else {
                    jadwalKonsultasiDokterModels.clear();
                    tahun = sTahunJadwalKonsultasiDokter.getSelectedItem().toString();
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

        btnBackJadwalKonsultasiDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JadwalKonsultasiDokterActivity.this, DokterMainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btnTambahJadwalKonsultasiDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JadwalKonsultasiDokterActivity.this, TambahJadwalKonsultasiDokterActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void getDataAPI(){
        queueKonsultasi = Volley.newRequestQueue(JadwalKonsultasiDokterActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                jadwalKonsultasiDokterModels.add(new JadwalKonsultasiDokterModel(jsonObject.getString("_id"),
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
        recyclerViewJadwalKonsultasiDokter.setAdapter(adapter);
        recyclerViewJadwalKonsultasiDokter.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

}