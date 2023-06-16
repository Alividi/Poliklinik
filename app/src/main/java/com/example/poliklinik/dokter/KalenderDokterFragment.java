package com.example.poliklinik.dokter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KalenderDokterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KalenderDokterFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<KalenderDokterModel> kalenderDokterModels;
    RecyclerView rvKalenderDokter;
    KalenderDokterAdapter adapter;
    RequestQueue queueKalender;
    String url = "";
    String hari, bulan, tahun, tanggal;
    String tglSkrng,hrSkrng,blnSkrng,thSkrng;
    public KalenderDokterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarDokterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KalenderDokterFragment newInstance(String param1, String param2) {
        KalenderDokterFragment fragment = new KalenderDokterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kalender_dokter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner sHariKalenderDokter = view.findViewById(R.id.sHariKalenderDokter);
        Spinner sBulanKalenderDokter = view.findViewById(R.id.sBulanKalenderDokter);
        Spinner sTahunKalenderDokter = view.findViewById(R.id.sTahunKalenderDokter);

        tglSkrng = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        hrSkrng = tglSkrng.substring(0,2);
        blnSkrng = tglSkrng.substring(3,5);
        thSkrng = tglSkrng.substring(6,10);
        sHariKalenderDokter.setSelection(Integer.parseInt(hrSkrng)-1);
        switch (blnSkrng){
            case "01":
                sBulanKalenderDokter.setSelection(0);
                break;
            case "02":
                sBulanKalenderDokter.setSelection(1);
                break;
            case "03":
                sBulanKalenderDokter.setSelection(2);
                break;
            case "04":
                sBulanKalenderDokter.setSelection(3);
                break;
            case "05":
                sBulanKalenderDokter.setSelection(4);
                break;
            case "06":
                sBulanKalenderDokter.setSelection(5);
                break;
            case "07":
                sBulanKalenderDokter.setSelection(6);
                break;
            case "08":
                sBulanKalenderDokter.setSelection(7);
                break;
            case "09":
                sBulanKalenderDokter.setSelection(8);
                break;
            case "10":
                sBulanKalenderDokter.setSelection(9);
                break;
            case "11":
                sBulanKalenderDokter.setSelection(10);
                break;
            case "12":
                sBulanKalenderDokter.setSelection(11);
                break;
        }
        switch (thSkrng){
            case "2023":
                sTahunKalenderDokter.setSelection(0);
                break;
            case "2024":
                sTahunKalenderDokter.setSelection(1);
                break;
            case "2025":
                sTahunKalenderDokter.setSelection(2);
                break;
            case "2026":
                sTahunKalenderDokter.setSelection(3);
                break;
            case "2027":
                sTahunKalenderDokter.setSelection(4);
                break;
            case "2028":
                sTahunKalenderDokter.setSelection(5);
                break;
        }
        Log.e("tgl", "hr: "+hrSkrng+" bln: "+blnSkrng+" th: "+thSkrng);

        hari = sHariKalenderDokter.getSelectedItem().toString();
        switch (sBulanKalenderDokter.getSelectedItem().toString()){
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
        tahun = sTahunKalenderDokter.getSelectedItem().toString();
        if (hari.length() == 2){
            tanggal = hari+"-"+bulan+"-"+tahun;
        }else {
            tanggal = "0"+hari+"-"+bulan+"-"+tahun;
        }

        rvKalenderDokter = view.findViewById(R.id.rvKalenderDokter);
        kalenderDokterModels = new ArrayList<>();
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_jadwal_by_tanggal?tanggal=";
        url = url+tanggal;
        adapter = new KalenderDokterAdapter(getContext(), kalenderDokterModels);
        getDataAPI();

        sHariKalenderDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sHariKalenderDokter.getSelectedItem().toString().equals(hrSkrng)){
                    Log.e("TAG", "hari udh dipilih" );
                    hrSkrng = "";
                } else{
                    kalenderDokterModels.clear();
                    hari = sHariKalenderDokter.getSelectedItem().toString();
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

        sBulanKalenderDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sBulanKalenderDokter.getSelectedItemPosition() == Integer.parseInt(blnSkrng)-1){
                    Log.e("TAG", "Bulan udh dipilih");
                    blnSkrng = "1";
                }else {
                    kalenderDokterModels.clear();
                    switch (sBulanKalenderDokter.getSelectedItem().toString()){
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
                    tanggal = hari+"-"+bulan+"-"+tahun;
                    url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_jadwal_by_tanggal?tanggal=";
                    if (hari.length() == 2){
                        tanggal = hari+"-"+bulan+"-"+tahun;
                    }else {
                        tanggal = "0"+hari+"-"+bulan+"-"+tahun;
                    }
                    Log.e("link api", "bulan: "+url );
                    getDataAPI();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sTahunKalenderDokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sTahunKalenderDokter.getSelectedItem().toString().equals(thSkrng)){
                    Log.e("TAG", "Tahun udh dipilih: ");
                    thSkrng = "";
                }else {
                    kalenderDokterModels.clear();
                    tahun = sTahunKalenderDokter.getSelectedItem().toString();
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

    }

    public void getDataAPI() {
        queueKalender = Volley.newRequestQueue(requireContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                kalenderDokterModels.add(new KalenderDokterModel(jsonObject.getString("_id"),
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
        queueKalender.add(jsonArrayRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initRecyclerView(){
        rvKalenderDokter.setLayoutManager(new LinearLayoutManager(getContext()));
        rvKalenderDokter.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}