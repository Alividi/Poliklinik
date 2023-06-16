package com.example.poliklinik.dokter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.poliklinik.LoginActivity;
import com.example.poliklinik.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeDokterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeDokterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SharedPreferences loginPreferences;
    SharedPreferences.Editor prefsEditor;
    ArrayList<KalenderDokterModel> kalenderDokterModels;
    RecyclerView rvKalenderDokter;
    JadwalMendatangDokterAdapter adapterJM;
    RequestQueue queue;
    String url = "";
    String tglSkrng;
    TextView tvInfoJadwalDokter;
    ArrayList<AntrianDokterModel> antrianDokterModels;
    AntrianSelanjutnyaAdapter adapterAntrian;
    RecyclerView rvAntrian;
    TextView tvInfoAntrianDokter;
    ArrayList<PengajuanRmDokterModel> pengajuanRmDokterModels;
    PengajuanRMHomeDokterAdapter adapterPRM;
    RecyclerView rvPengajuanRM;
    TextView tvInfoPRMDokter;

    public HomeDokterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeDokterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeDokterFragment newInstance(String param1, String param2) {
        HomeDokterFragment fragment = new HomeDokterFragment();
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
        return inflater.inflate(R.layout.fragment_home_dokter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginPreferences = getActivity().getSharedPreferences("LoginPrefs", 0);
        prefsEditor = loginPreferences.edit();

        TextView tvNamaDokter = view.findViewById(R.id.tvNamaDokter);
        tvInfoJadwalDokter = view.findViewById(R.id.tvInfoJadwalDokter);
        tvInfoAntrianDokter = view.findViewById(R.id.tvInfoAntrianDokter);
        tvInfoPRMDokter = view.findViewById(R.id.tvInfoPRMDokter);
        tvNamaDokter.setText(tvNamaDokter.getText()+loginPreferences.getString("nama",""));
        TextView btnShowMorePengajuanRM = view.findViewById(R.id.btnShowMorePengajuanRM);
        AppCompatImageButton btnLogoutDokter = view.findViewById(R.id.btnLogoutDokter);
        ConstraintLayout btnDaftarAntrianDokter = view.findViewById(R.id.btnDaftarAntrianDokter);
        ConstraintLayout btnRekamMedisDokter = view.findViewById(R.id.btnRekamMedisDokter);
        ConstraintLayout btnJadwalCheckupDokter = view.findViewById(R.id.btnJadwalCheckupDokter);
        ConstraintLayout btnJadwalKonsulDokter = view.findViewById(R.id.btnJadwalKonsulDokter);
        ConstraintLayout btnPasienDokter = view.findViewById(R.id.btnPasienDokter);

        rvAntrian = view.findViewById(R.id.rvAntrian);
        antrianDokterModels = new ArrayList<>();
        adapterAntrian = new AntrianSelanjutnyaAdapter(getContext(), antrianDokterModels);
        getAntrian();

        tglSkrng = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        rvKalenderDokter = view.findViewById(R.id.rvJadwalMendatangDokter);
        kalenderDokterModels = new ArrayList<>();
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_jadwal_by_nama_tanggal?dokter="+loginPreferences.getString("nama","");
        url = url+"&tanggal="+tglSkrng;
        adapterJM = new JadwalMendatangDokterAdapter(getContext(), kalenderDokterModels);
        getJadwalMendatang();

        rvPengajuanRM = view.findViewById(R.id.rvPengajuanRM);
        pengajuanRmDokterModels = new ArrayList<>();
        adapterPRM = new PengajuanRMHomeDokterAdapter(getContext(), pengajuanRmDokterModels);
        getPRM();

        btnDaftarAntrianDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AntrianDokterActivity.class);
                requireActivity().finish();
                startActivity(intent);
            }
        });

        btnRekamMedisDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),RekamMedisDokterActivity.class);
                startActivity(intent);
            }
        });

        btnShowMorePengajuanRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PengajuanRmDokterActivity.class);
                requireActivity().finish();
                startActivity(intent);
            }
        });

        btnJadwalKonsulDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),JadwalKonsultasiDokterActivity.class);
                requireActivity().finish();
                startActivity(intent);
            }
        });

        btnJadwalCheckupDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),JadwalCheckUpDokterActivity.class);
                requireActivity().finish();
                startActivity(intent);
            }
        });

        btnPasienDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ListDataPasienDokterActivity.class);
                startActivity(intent);
            }
        });

        btnLogoutDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });
    }

    public void showLogoutDialog(){
        final Dialog dialogLogout = new Dialog(getActivity(), R.style.MaterialDialogSheet);
        dialogLogout.setContentView(R.layout.dialog_logout);
        dialogLogout.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogLogout.getWindow().setGravity(Gravity.BOTTOM);
        dialogLogout.show();

        AppCompatButton btnLogoutDialog = dialogLogout.findViewById(R.id.btnLogoutDialog);
        TextView btnKembaliDialog = dialogLogout.findViewById(R.id.btnKembaliDialog);

        btnLogoutDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefsEditor.clear();
                prefsEditor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        btnKembaliDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout.dismiss();
            }
        });
    }
    public void getJadwalMendatang() {
        queue = Volley.newRequestQueue(requireContext());

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
                            initRecyclerViewJM();
                            if (kalenderDokterModels.isEmpty()) {
                                rvKalenderDokter.setVisibility(View.GONE);
                                tvInfoJadwalDokter.setVisibility(View.VISIBLE);
                            }
                            else {
                                rvKalenderDokter.setVisibility(View.VISIBLE);
                                tvInfoJadwalDokter.setVisibility(View.GONE);
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

    @SuppressLint("NotifyDataSetChanged")
    public void initRecyclerViewJM(){
        rvKalenderDokter.setLayoutManager(new LinearLayoutManager(getContext()));
        rvKalenderDokter.setAdapter(adapterJM);
        adapterJM.notifyDataSetChanged();
    }

    public void getAntrian(){
        queue = Volley.newRequestQueue(requireContext());
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_antrian_by_dokter?dokter="+loginPreferences.getString("nama", "-");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                antrianDokterModels.add(new AntrianDokterModel(jsonObject.getString("_id"),
                                        jsonObject.getString("urutan"),
                                        jsonObject.getString("nama_pasien"),
                                        jsonObject.getString("nama_dokter"),
                                        jsonObject.getString("status")));
                            }
                            initRecyclerViewAntrian();
                            if (antrianDokterModels.isEmpty()){
                                rvAntrian.setVisibility(View.GONE);
                                tvInfoAntrianDokter.setVisibility(View.VISIBLE);
                            }
                            else {
                                rvAntrian.setVisibility(View.VISIBLE);
                                tvInfoAntrianDokter.setVisibility(View.GONE);
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
    @SuppressLint("NotifyDataSetChanged")
    public void initRecyclerViewAntrian(){
        rvAntrian.setAdapter(adapterAntrian);
        rvAntrian.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterAntrian.notifyDataSetChanged();
    }

    public void getPRM(){
        queue = Volley.newRequestQueue(requireContext());
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_p_rekam_medis?dokter="+loginPreferences.getString("nama", "-");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                pengajuanRmDokterModels.add(new PengajuanRmDokterModel(jsonObject.getString("_id"),
                                        jsonObject.getString("tanggal"),
                                        jsonObject.getString("diagnosa"),
                                        jsonObject.getString("keluhan"),
                                        jsonObject.getString("nama_pasien"),
                                        jsonObject.getString("nama_dokter"),
                                        jsonObject.getString("status"),
                                        jsonObject.getString("obat"),
                                        jsonObject.getString("jumlah"),
                                        jsonObject.getString("obat2"),
                                        jsonObject.getString("jumlah2"),
                                        jsonObject.getString("obat3"),
                                        jsonObject.getString("jumlah3"),
                                        jsonObject.getString("obat4"),
                                        jsonObject.getString("jumlah4"),
                                        jsonObject.getString("obat5"),
                                        jsonObject.getString("jumlah5")));
                            }
                            Collections.reverse(pengajuanRmDokterModels);
                            initRecyclerViewPRM();
                            if (pengajuanRmDokterModels.isEmpty()){
                                rvPengajuanRM.setVisibility(View.GONE);
                                tvInfoPRMDokter.setVisibility(View.VISIBLE);
                            }
                            else {
                                rvPengajuanRM.setVisibility(View.VISIBLE);
                                tvInfoPRMDokter.setVisibility(View.GONE);
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
    @SuppressLint("NotifyDataSetChanged")
    public void initRecyclerViewPRM(){
        rvPengajuanRM.setAdapter(adapterPRM);
        rvPengajuanRM.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterPRM.notifyDataSetChanged();
    }

}