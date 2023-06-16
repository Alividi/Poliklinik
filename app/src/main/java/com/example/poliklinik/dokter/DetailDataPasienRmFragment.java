package com.example.poliklinik.dokter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailDataPasienRmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailDataPasienRmFragment extends Fragment implements DetailDataPasienRmInterface{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<RekamMedisDokterModel> rekamMedisDokterModels;
    RecyclerView recyclerViewDataPasien;
    RequestQueue queue;
    String url = "";
    DetailDataPasienRmAdapter adapter;
    SearchView svDataPasien;
    SharedPreferences loginPreferences;
    Intent intent;
    AppCompatImageButton btnTambahListRMDataPasien;

    public DetailDataPasienRmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailDataPasienRmFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailDataPasienRmFragment newInstance(String param1, String param2) {
        DetailDataPasienRmFragment fragment = new DetailDataPasienRmFragment();
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
        return inflater.inflate(R.layout.fragment_detail_data_pasien_rm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnTambahListRMDataPasien = view.findViewById(R.id.btnTambahListRMDataPasien);

        intent = requireActivity().getIntent();
        loginPreferences = requireActivity().getSharedPreferences("LoginPrefs", 0);

        svDataPasien = view.findViewById(R.id.svDataPasien);
        svDataPasien.clearFocus();

        svDataPasien.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        recyclerViewDataPasien = view.findViewById(R.id.recyclerViewRMDataPasien);
        rekamMedisDokterModels = new ArrayList<>();
        adapter = new DetailDataPasienRmAdapter(requireContext(),rekamMedisDokterModels,this);
        getDataAPI();

        btnTambahListRMDataPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), TambahRekamMedisDokterActivity.class);
                requireActivity().finish();
                startActivity(intent1);
            }
        });
    }

    public void getDataAPI(){
        queue = Volley.newRequestQueue(requireContext());
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_rekam_medis_by_dokter_pasien?dokter="+loginPreferences.getString("nama","-")+"&pasien="+intent.getStringExtra("nama");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                rekamMedisDokterModels.add(new RekamMedisDokterModel(jsonObject.getString("_id"),
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
                            Collections.reverse(rekamMedisDokterModels);
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
        queue.add(jsonArrayRequest);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void initRecyclerView(){
        recyclerViewDataPasien.setAdapter(adapter);
        recyclerViewDataPasien.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
    }

    private void filterList(String text){
        ArrayList<RekamMedisDokterModel> filterArray = new ArrayList<>();
        for (RekamMedisDokterModel rekamMedisDokterModel : rekamMedisDokterModels){
            if (rekamMedisDokterModel.getIdRM().toLowerCase().contains(text.toLowerCase()) ||
                    rekamMedisDokterModel.getTanggalRM().toLowerCase().contains(text.toLowerCase())){
                filterArray.add(rekamMedisDokterModel);
            }
            if (filterArray.isEmpty()){
                Toast.makeText(getContext(), "tidak ada Rekam medis", Toast.LENGTH_SHORT).show();
            }else {
                adapter.setFilteredModels(filterArray);
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent1 = new Intent(getActivity(), RekamMedisDetailDokterActivity.class);
        intent1.putExtra("tanggal", rekamMedisDokterModels.get(position).getTanggalRM());
        intent1.putExtra("nomor", rekamMedisDokterModels.get(position).getIdRM());
        intent1.putExtra("pasien",rekamMedisDokterModels.get(position).getNmPasienRM());
        intent1.putExtra("keluhan", rekamMedisDokterModels.get(position).getKeluhanRM());
        intent1.putExtra("diagnosa", rekamMedisDokterModels.get(position).getDiagnosaRM());
        intent1.putExtra("obat1", rekamMedisDokterModels.get(position).getObat1RM());
        intent1.putExtra("jumlah1", rekamMedisDokterModels.get(position).getJumlah1RM());
        intent1.putExtra("obat2", rekamMedisDokterModels.get(position).getObat2RM());
        intent1.putExtra("jumlah2", rekamMedisDokterModels.get(position).getJumlah2RM());
        intent1.putExtra("obat3", rekamMedisDokterModels.get(position).getObat3RM());
        intent1.putExtra("jumlah3", rekamMedisDokterModels.get(position).getJumlah3RM());
        intent1.putExtra("obat4", rekamMedisDokterModels.get(position).getObat4RM());
        intent1.putExtra("jumlah4", rekamMedisDokterModels.get(position).getJumlah4RM());
        intent1.putExtra("obat5", rekamMedisDokterModels.get(position).getObat5RM());
        intent1.putExtra("jumlah5", rekamMedisDokterModels.get(position).getJumlah5RM());
        startActivity(intent1);
    }
}