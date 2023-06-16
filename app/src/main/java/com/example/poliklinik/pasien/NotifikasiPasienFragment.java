package com.example.poliklinik.pasien;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotifikasiPasienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifikasiPasienFragment extends Fragment implements NotifikasiPasienInterface{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<NotifikasiPasienModel> notifikasiPasienModels;
    RecyclerView rvNotifikasiPasien;
    NotifikasiPasienAdapter adapter;
    RequestQueue queue;
    String url = "";
    SharedPreferences loginPreferences;
    public NotifikasiPasienFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryPasienFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotifikasiPasienFragment newInstance(String param1, String param2) {
        NotifikasiPasienFragment fragment = new NotifikasiPasienFragment();
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
        return inflater.inflate(R.layout.fragment_notifikasi_pasien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginPreferences = getActivity().getSharedPreferences("LoginPrefs", 0);

        rvNotifikasiPasien = view.findViewById(R.id.rvNotifikasiPasien);
        notifikasiPasienModels = new ArrayList<>();
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_pemberitahuan_pasien?pasien="+loginPreferences.getString("nama","");
        adapter =  new NotifikasiPasienAdapter(getContext(), notifikasiPasienModels, this);
        getDataAPI();


    }

    public void getDataAPI(){
        queue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                                notifikasiPasienModels.add(new NotifikasiPasienModel(jsonObject.getString("_id"),
                                        jsonObject.getString("nama_pasien"),
                                        jsonObject.getString("nama_dokter"),
                                        jsonObject.getString("id_rekam_medis"),
                                        jsonObject.getString("tanggal"),
                                        jsonObject.getString("status_rm"),
                                        jsonObject.getString("status_dokter"),
                                        jsonObject.getString("status_pasien")));
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
        queue.add(jsonArrayRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initRecyclerView(){
        rvNotifikasiPasien.setAdapter(adapter);
        rvNotifikasiPasien.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), RekamMedisPasienActivity.class);

        if (notifikasiPasienModels.get(position).getStatusPS().equals("dikirim")){
            queue = Volley.newRequestQueue(requireContext());
            url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/update_status_pemberitahuan_pasien?id="+notifikasiPasienModels.get(position).getId();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("api", "onResponse: "+response );
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.getStackTrace();
                }
            });
            queue.add(stringRequest);
        }
        requireActivity().finish();
        startActivity(intent);
    }

}