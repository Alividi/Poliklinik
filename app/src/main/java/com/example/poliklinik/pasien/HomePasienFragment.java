package com.example.poliklinik.pasien;

import android.annotation.SuppressLint;
import android.app.Dialog;
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

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePasienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePasienFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor prefsEditor;
    RecyclerView rvJadwalMendatang;
    JadwalMendatangAdapter adapterMendatang;
    ArrayList<JadwalPasienModel> jadwalPasienModels;
    RequestQueue queue;
    String url = "";
    String tglSkrng;
    TextView tvInfoJadwal;

    public HomePasienFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePasienFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePasienFragment newInstance(String param1, String param2) {
        HomePasienFragment fragment = new HomePasienFragment();
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
        return inflater.inflate(R.layout.fragment_home_pasien, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginPreferences = getActivity().getSharedPreferences("LoginPrefs", 0);
        prefsEditor = loginPreferences.edit();

        tvInfoJadwal = view.findViewById(R.id.tvInfoJadwal);
        TextView tvWelcomePasien = view.findViewById(R.id.tvWelcomePasien);
        tvWelcomePasien.setText(tvWelcomePasien.getText()+loginPreferences.getString("nama","")+"!");
        AppCompatImageButton btnLogoutPasien = view.findViewById(R.id.btnLogoutPasien);
        ConstraintLayout menuJadwalKonsul = view.findViewById(R.id.menuJadwalKonsul);
        ConstraintLayout menuJadwalCheckUp = view.findViewById(R.id.menuJadwalCheckUp);
        ConstraintLayout menuRiwayatKunjungan = view.findViewById(R.id.menuRiwayatKunjungan);
        ConstraintLayout menuTekananDarah = view.findViewById(R.id.menuTekananDarah);

        tglSkrng = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        rvJadwalMendatang = view.findViewById(R.id.rvJadwalMendatang);
        jadwalPasienModels = new ArrayList<>();
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/get_jadwal_pasien_tanggal?pasien="+loginPreferences.getString("nama","");
        url = url+"&tanggal="+tglSkrng;
        adapterMendatang = new JadwalMendatangAdapter(getContext(), jadwalPasienModels);
        getJadwalMendatang();


        menuJadwalKonsul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),JadwalKonsultasiPasienActivity.class);
                requireActivity().finish();
                startActivity(intent);
            }
        });

        menuJadwalCheckUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),JadwalCheckUpPasienActivity.class);
                startActivity(intent);
            }
        });

        menuRiwayatKunjungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RekamMedisPasienActivity.class);
                requireActivity().finish();
                startActivity(intent);
            }
        });

        menuTekananDarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TekananDarahPasienActivity.class);
                startActivity(intent);
            }
        });

        btnLogoutPasien.setOnClickListener(new View.OnClickListener() {
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

    public void getJadwalMendatang(){
        queue = Volley.newRequestQueue(requireContext());

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
                            if (jadwalPasienModels.isEmpty()) {
                                rvJadwalMendatang.setVisibility(View.GONE);
                                tvInfoJadwal.setVisibility(View.VISIBLE);
                            }
                            else {
                                rvJadwalMendatang.setVisibility(View.VISIBLE);
                                tvInfoJadwal.setVisibility(View.GONE);
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
    public void initRecyclerView(){
        rvJadwalMendatang.setLayoutManager(new LinearLayoutManager(getContext()));
        rvJadwalMendatang.setAdapter(adapterMendatang);
        adapterMendatang.notifyDataSetChanged();
    }
}