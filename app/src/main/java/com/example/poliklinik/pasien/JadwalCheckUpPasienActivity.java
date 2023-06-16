package com.example.poliklinik.pasien;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.poliklinik.dokter.JadwalCheckUpDokterActivity;
import com.example.poliklinik.dokter.JadwalCheckUpDokterAdapter;
import com.example.poliklinik.dokter.JadwalCheckUpDokterModel;
import com.example.poliklinik.dokter.TambahJadwalCheckUpDokterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class JadwalCheckUpPasienActivity extends AppCompatActivity {

    ArrayList<JadwalPasienModel> jadwalCheckUpPasienModels;
    JadwalCheckUpPasienAdapter adapter;
    RecyclerView recyclerViewJadwalCheckUpPasien;
    RequestQueue queueCheckUp;
    String url = "";
    String hari, bulan, tahun, tanggal;
    String tglSkrng,hrSkrng,blnSkrng,thSkrng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_check_up_pasien);

        ImageButton btnBackJadwalCheckUpPasien = findViewById(R.id.btnBackJadwalCheckUpPasien);

        Spinner sHariJadwalCheckUpPasien = findViewById(R.id.sHariJadwalCheckUpPasien);
        Spinner sBulanJadwalCheckUpPasien = findViewById(R.id.sBulanJadwalCheckUpPasien);
        Spinner sTahunJadwalCheckUpPasien = findViewById(R.id.sTahunJadwalCheckUpPasien);

        tglSkrng = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        hrSkrng = tglSkrng.substring(0,2);
        blnSkrng = tglSkrng.substring(3,5);
        thSkrng = tglSkrng.substring(6,10);
        sHariJadwalCheckUpPasien.setSelection(Integer.parseInt(hrSkrng)-1);
        switch (blnSkrng){
            case "01":
                sBulanJadwalCheckUpPasien.setSelection(0);
                break;
            case "02":
                sBulanJadwalCheckUpPasien.setSelection(1);
                break;
            case "03":
                sBulanJadwalCheckUpPasien.setSelection(2);
                break;
            case "04":
                sBulanJadwalCheckUpPasien.setSelection(3);
                break;
            case "05":
                sBulanJadwalCheckUpPasien.setSelection(4);
                break;
            case "06":
                sBulanJadwalCheckUpPasien.setSelection(5);
                break;
            case "07":
                sBulanJadwalCheckUpPasien.setSelection(6);
                break;
            case "08":
                sBulanJadwalCheckUpPasien.setSelection(7);
                break;
            case "09":
                sBulanJadwalCheckUpPasien.setSelection(8);
                break;
            case "10":
                sBulanJadwalCheckUpPasien.setSelection(9);
                break;
            case "11":
                sBulanJadwalCheckUpPasien.setSelection(10);
                break;
            case "12":
                sBulanJadwalCheckUpPasien.setSelection(11);
                break;
        }
        switch (thSkrng){
            case "2023":
                sTahunJadwalCheckUpPasien.setSelection(0);
                break;
            case "2024":
                sTahunJadwalCheckUpPasien.setSelection(1);
                break;
            case "2025":
                sTahunJadwalCheckUpPasien.setSelection(2);
                break;
            case "2026":
                sTahunJadwalCheckUpPasien.setSelection(3);
                break;
            case "2027":
                sTahunJadwalCheckUpPasien.setSelection(4);
                break;
            case "2028":
                sTahunJadwalCheckUpPasien.setSelection(5);
                break;
        }

        hari = sHariJadwalCheckUpPasien.getSelectedItem().toString();
        switch (sBulanJadwalCheckUpPasien.getSelectedItem().toString()){
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
        tahun = sTahunJadwalCheckUpPasien.getSelectedItem().toString();
        if (hari.length() == 2){
            tanggal = hari+"-"+bulan+"-"+tahun;
        }else {
            tanggal = "0"+hari+"-"+bulan+"-"+tahun;
        }

        recyclerViewJadwalCheckUpPasien = findViewById(R.id.recyclerViewJadwalCheckUpPasien);
        jadwalCheckUpPasienModels = new ArrayList<>();
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_jadwal_by_tanggal?tanggal=";
        url = url+tanggal;
        adapter = new JadwalCheckUpPasienAdapter(this, jadwalCheckUpPasienModels);
        getDataAPI();

        sHariJadwalCheckUpPasien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sHariJadwalCheckUpPasien.getSelectedItem().toString().equals(hrSkrng)){
                    Log.e("TAG", "hari udh dipilih" );
                    hrSkrng = "";
                } else{
                    jadwalCheckUpPasienModels.clear();
                    hari = sHariJadwalCheckUpPasien.getSelectedItem().toString();
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

        sBulanJadwalCheckUpPasien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sBulanJadwalCheckUpPasien.getSelectedItemPosition() == Integer.parseInt(blnSkrng)-1){
                    Log.e("TAG", "Bulan udh dipilih");
                    blnSkrng = "1";
                }else {
                    jadwalCheckUpPasienModels.clear();
                    switch (sBulanJadwalCheckUpPasien.getSelectedItem().toString()){
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

        sTahunJadwalCheckUpPasien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sTahunJadwalCheckUpPasien.getSelectedItem().toString().equals(thSkrng)){
                    Log.e("TAG", "Tahun udh dipilih: ");
                    thSkrng = "";
                }else {
                    jadwalCheckUpPasienModels.clear();
                    tahun = sTahunJadwalCheckUpPasien.getSelectedItem().toString();
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

        btnBackJadwalCheckUpPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getDataAPI(){
        queueCheckUp = Volley.newRequestQueue(JadwalCheckUpPasienActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                jadwalCheckUpPasienModels.add(new JadwalPasienModel(jsonObject.getString("_id"),
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
        queueCheckUp.add(jsonArrayRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initRecyclerView(){
        recyclerViewJadwalCheckUpPasien.setAdapter(adapter);
        recyclerViewJadwalCheckUpPasien.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }


}