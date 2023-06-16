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

public class JadwalCheckUpDokterActivity extends AppCompatActivity {

    ArrayList<JadwalCheckUpDokterModel> jadwalCheckUpDokterModels;
    JadwalCheckUpDokterAdapter adapter;
    RecyclerView recyclerViewJadwalCheckUpDokter;
    RequestQueue queueCheckUp;
    String url = "";
    String hari, bulan, tahun, tanggal;
    String tglSkrng,hrSkrng,blnSkrng,thSkrng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_check_up_dokter);

        ImageButton btnBackJadwalCheckUpDokter = findViewById(R.id.btnBackJadwalCheckUpDokter);
        AppCompatButton btnTambahJadwalCheckUpDokter = findViewById(R.id.btnTambahJadwalCheckUpDokter);

        Spinner sHariJadwalCheckUpDokter = findViewById(R.id.sHariJadwalCheckUpDokter);
        Spinner sBulanJadwalCheckUpDokter = findViewById(R.id.sBulanJadwalCheckUpDokter);
        Spinner sTahunJadwalCheckUpDokter = findViewById(R.id.sTahunJadwalCheckUpDokter);

        tglSkrng = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        hrSkrng = tglSkrng.substring(0,2);
        blnSkrng = tglSkrng.substring(3,5);
        thSkrng = tglSkrng.substring(6,10);
        sHariJadwalCheckUpDokter.setSelection(Integer.parseInt(hrSkrng)-1);
        switch (blnSkrng){
            case "01":
                sBulanJadwalCheckUpDokter.setSelection(0);
                break;
            case "02":
                sBulanJadwalCheckUpDokter.setSelection(1);
                break;
            case "03":
                sBulanJadwalCheckUpDokter.setSelection(2);
                break;
            case "04":
                sBulanJadwalCheckUpDokter.setSelection(3);
                break;
            case "05":
                sBulanJadwalCheckUpDokter.setSelection(4);
                break;
            case "06":
                sBulanJadwalCheckUpDokter.setSelection(5);
                break;
            case "07":
                sBulanJadwalCheckUpDokter.setSelection(6);
                break;
            case "08":
                sBulanJadwalCheckUpDokter.setSelection(7);
                break;
            case "09":
                sBulanJadwalCheckUpDokter.setSelection(8);
                break;
            case "10":
                sBulanJadwalCheckUpDokter.setSelection(9);
                break;
            case "11":
                sBulanJadwalCheckUpDokter.setSelection(10);
                break;
            case "12":
                sBulanJadwalCheckUpDokter.setSelection(11);
                break;
        }
        switch (thSkrng){
            case "2023":
                sTahunJadwalCheckUpDokter.setSelection(0);
                break;
            case "2024":
                sTahunJadwalCheckUpDokter.setSelection(1);
                break;
            case "2025":
                sTahunJadwalCheckUpDokter.setSelection(2);
                break;
            case "2026":
                sTahunJadwalCheckUpDokter.setSelection(3);
                break;
            case "2027":
                sTahunJadwalCheckUpDokter.setSelection(4);
                break;
            case "2028":
                sTahunJadwalCheckUpDokter.setSelection(5);
                break;
        }

        hari = sHariJadwalCheckUpDokter.getSelectedItem().toString();
        switch (sBulanJadwalCheckUpDokter.getSelectedItem().toString()){
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
        tahun = sTahunJadwalCheckUpDokter.getSelectedItem().toString();
        if (hari.length() == 2){
            tanggal = hari+"-"+bulan+"-"+tahun;
        }else {
            tanggal = "0"+hari+"-"+bulan+"-"+tahun;
        }

        recyclerViewJadwalCheckUpDokter = findViewById(R.id.recyclerViewJadwalCheckUpDokter);
        jadwalCheckUpDokterModels = new ArrayList<>();
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_jadwal_by_tanggal?tanggal=";
        url = url+tanggal;
        adapter = new JadwalCheckUpDokterAdapter(this, jadwalCheckUpDokterModels);
        getDataAPI();

        sHariJadwalCheckUpDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sHariJadwalCheckUpDokter.getSelectedItem().toString().equals(hrSkrng)){
                    Log.e("TAG", "hari udh dipilih" );
                    hrSkrng = "";
                } else{
                    jadwalCheckUpDokterModels.clear();
                    hari = sHariJadwalCheckUpDokter.getSelectedItem().toString();
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

        sBulanJadwalCheckUpDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sBulanJadwalCheckUpDokter.getSelectedItemPosition() == Integer.parseInt(blnSkrng)-1){
                    Log.e("TAG", "Bulan udh dipilih");
                    blnSkrng = "1";
                }else {
                    jadwalCheckUpDokterModels.clear();
                    switch (sBulanJadwalCheckUpDokter.getSelectedItem().toString()){
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

        sTahunJadwalCheckUpDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sTahunJadwalCheckUpDokter.getSelectedItem().toString().equals(thSkrng)){
                    Log.e("TAG", "Tahun udh dipilih: ");
                    thSkrng = "";
                }else {
                    jadwalCheckUpDokterModels.clear();
                    tahun = sTahunJadwalCheckUpDokter.getSelectedItem().toString();
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

        btnTambahJadwalCheckUpDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JadwalCheckUpDokterActivity.this, TambahJadwalCheckUpDokterActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btnBackJadwalCheckUpDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JadwalCheckUpDokterActivity.this, DokterMainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    public void getDataAPI(){
        queueCheckUp = Volley.newRequestQueue(JadwalCheckUpDokterActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                jadwalCheckUpDokterModels.add(new JadwalCheckUpDokterModel(jsonObject.getString("_id"),
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
        recyclerViewJadwalCheckUpDokter.setAdapter(adapter);
        recyclerViewJadwalCheckUpDokter.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

}