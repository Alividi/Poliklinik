package com.example.poliklinik.pasien;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.poliklinik.R;
import com.example.poliklinik.dokter.KalenderDokterAdapter;
import com.example.poliklinik.dokter.KalenderDokterModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KalenderPasienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KalenderPasienFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<JadwalPasienModel> jadwalPasienModels;
    RecyclerView rvKalenderPasien;
    KalenderPasienAdapter adapter;
    RequestQueue queueKalender;
    String url = "";
    String hari, bulan, tahun, tanggal;
    String tglSkrng,hrSkrng,blnSkrng,thSkrng;
    public KalenderPasienFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarPasienFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KalenderPasienFragment newInstance(String param1, String param2) {
        KalenderPasienFragment fragment = new KalenderPasienFragment();
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
        return inflater.inflate(R.layout.fragment_kalender_pasien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner sHariKalenderPasien = view.findViewById(R.id.sHariKalenderPasien);
        Spinner sBulanKalenderPasien = view.findViewById(R.id.sBulanKalenderPasien);
        Spinner sTahunKalenderPasien = view.findViewById(R.id.sTahunKalenderPasien);

        tglSkrng = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        hrSkrng = tglSkrng.substring(0,2);
        blnSkrng = tglSkrng.substring(3,5);
        thSkrng = tglSkrng.substring(6,10);
        sHariKalenderPasien.setSelection(Integer.parseInt(hrSkrng)-1);
        switch (blnSkrng){
            case "01":
                sBulanKalenderPasien.setSelection(0);
                break;
            case "02":
                sBulanKalenderPasien.setSelection(1);
                break;
            case "03":
                sBulanKalenderPasien.setSelection(2);
                break;
            case "04":
                sBulanKalenderPasien.setSelection(3);
                break;
            case "05":
                sBulanKalenderPasien.setSelection(4);
                break;
            case "06":
                sBulanKalenderPasien.setSelection(5);
                break;
            case "07":
                sBulanKalenderPasien.setSelection(6);
                break;
            case "08":
                sBulanKalenderPasien.setSelection(7);
                break;
            case "09":
                sBulanKalenderPasien.setSelection(8);
                break;
            case "10":
                sBulanKalenderPasien.setSelection(9);
                break;
            case "11":
                sBulanKalenderPasien.setSelection(10);
                break;
            case "12":
                sBulanKalenderPasien.setSelection(11);
                break;
        }
        switch (thSkrng){
            case "2023":
                sTahunKalenderPasien.setSelection(0);
                break;
            case "2024":
                sTahunKalenderPasien.setSelection(1);
                break;
            case "2025":
                sTahunKalenderPasien.setSelection(2);
                break;
            case "2026":
                sTahunKalenderPasien.setSelection(3);
                break;
            case "2027":
                sTahunKalenderPasien.setSelection(4);
                break;
            case "2028":
                sTahunKalenderPasien.setSelection(5);
                break;
        }
        Log.e("tgl", "hr: "+hrSkrng+" bln: "+blnSkrng+" th: "+thSkrng);

        hari = sHariKalenderPasien.getSelectedItem().toString();
        switch (sBulanKalenderPasien.getSelectedItem().toString()){
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
        tahun = sTahunKalenderPasien.getSelectedItem().toString();
        if (hari.length() == 2){
            tanggal = hari+"-"+bulan+"-"+tahun;
        }else {
            tanggal = "0"+hari+"-"+bulan+"-"+tahun;
        }

        rvKalenderPasien = view.findViewById(R.id.rvKalenderPasien);
        jadwalPasienModels = new ArrayList<>();
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_jadwal_by_tanggal?tanggal=";
        url = url+tanggal;
        adapter = new KalenderPasienAdapter(getContext(), jadwalPasienModels);
        getDataAPI();

        sHariKalenderPasien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sHariKalenderPasien.getSelectedItem().toString().equals(hrSkrng)){
                    Log.e("TAG", "hari udh dipilih" );
                    hrSkrng = "";
                } else{
                    jadwalPasienModels.clear();
                    hari = sHariKalenderPasien.getSelectedItem().toString();
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

        sBulanKalenderPasien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sBulanKalenderPasien.getSelectedItemPosition() == Integer.parseInt(blnSkrng)-1){
                    Log.e("TAG", "Bulan udh dipilih");
                    blnSkrng = "1";
                }else {
                    jadwalPasienModels.clear();
                    switch (sBulanKalenderPasien.getSelectedItem().toString()){
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

        sTahunKalenderPasien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sTahunKalenderPasien.getSelectedItem().toString().equals(thSkrng)){
                    Log.e("TAG", "Tahun udh dipilih: ");
                    thSkrng = "";
                }else {
                    jadwalPasienModels.clear();
                    tahun = sTahunKalenderPasien.getSelectedItem().toString();
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
                                jadwalPasienModels.add(new JadwalPasienModel(jsonObject.getString("_id"),
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
        rvKalenderPasien.setLayoutManager(new LinearLayoutManager(getContext()));
        rvKalenderPasien.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}