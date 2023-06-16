package com.example.poliklinik.dokter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class KalenderDokterAdapter extends RecyclerView.Adapter<KalenderDokterAdapter.MyViewHolder> {
    Context context;
    ArrayList<KalenderDokterModel> kalenderDokterModels;
    String url;
    RequestQueue queue;
    SharedPreferences loginPreferences;


    public KalenderDokterAdapter(Context context, ArrayList<KalenderDokterModel> kalenderDokterModels) {
        this.context = context;
        this.kalenderDokterModels = kalenderDokterModels;
    }

    @NonNull
    @Override
    public KalenderDokterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_jadwal_konsultasi_dokter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KalenderDokterAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        loginPreferences = context.getSharedPreferences("LoginPrefs", 0);
        if (Objects.equals(kalenderDokterModels.get(position).getKalenderNmDokter(), loginPreferences.getString("nama", "-"))){
            holder.jam.setText(kalenderDokterModels.get(position).getKalenderJam());
            holder.nmPasien.setText(kalenderDokterModels.get(position).getKalenderNmPasien());
            holder.nmDokter.setText(kalenderDokterModels.get(position).getKalenderNmDokter());
            holder.kategori.setText(kalenderDokterModels.get(position).getKalenderKategori());
            if (kalenderDokterModels.get(position).getKalenderKategori().equals("Medical Check Up")){
                holder.kategori.setTextColor(Color.parseColor("#8ED2E2"));
                holder.dotJadwal.setBackgroundResource(R.drawable.dot_bg_lightblue);
            }
            if (kalenderDokterModels.get(position).getKalenderStatus().equals("selesai")){
                holder.jadwalDokter.setBackgroundResource(R.drawable.menu_done_bg);
                holder.btnDone.setVisibility(View.GONE);
                holder.btnCancel.setImageResource(R.drawable.trash_red);
                holder.jam.setTextColor(Color.parseColor("#FFFFFFFF"));
                holder.nmPasien.setTextColor(Color.parseColor("#FFFFFFFF"));
                holder.nmDokter.setTextColor(Color.parseColor("#FFFFFFFF"));
                holder.kategori.setTextColor(Color.parseColor("#FFFFFFFF"));
                holder.dotJadwal.setBackgroundResource(R.drawable.dot_bg_white);
                holder.btnCancel.setPadding(40,40,40,40);
            }
        }if (!Objects.equals(kalenderDokterModels.get(position).getKalenderNmDokter(), loginPreferences.getString("nama", "-"))) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }

        holder.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("api", "onClick id: "+kalenderDokterModels.get(position).getKalenderId());
                url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/update_statusjadwal_by_id?id="+kalenderDokterModels.get(position).getKalenderId();
                Log.e("api", "onClick url: "+url );
                doneJadwal();
                kalenderDokterModels.get(position).setKalenderStatus("selesai");
                kalenderDokterModels.set(position, new KalenderDokterModel(kalenderDokterModels.get(position).kalenderId,
                        kalenderDokterModels.get(position).getKalenderJam(),
                        kalenderDokterModels.get(position).getKalenderNmPasien(),
                        kalenderDokterModels.get(position).getKalenderNmDokter(),
                        kalenderDokterModels.get(position).getKalenderKategori(),
                        kalenderDokterModels.get(position).getKalenderStatus()));
                notifyItemChanged(position);
            }
        });
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("api", "onClick id: "+kalenderDokterModels.get(position).getKalenderId());
                url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/delete_jadwal?id="+kalenderDokterModels.get(position).getKalenderId();
                Log.e("api", "onClick url: "+url );
                cancelJadwal();
                kalenderDokterModels.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kalenderDokterModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView jam;
        TextView nmPasien;
        TextView nmDokter;
        TextView kategori;
        View dotJadwal;
        ConstraintLayout jadwalDokter;
        AppCompatImageButton btnDone, btnCancel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            jam = itemView.findViewById(R.id.tvJamJadwalKonsultasiDokter);
            nmPasien = itemView.findViewById(R.id.tvNamaPasienJadwalKonsultasiDokter);
            nmDokter = itemView.findViewById(R.id.tvNamaDokterJadwalKonsultasiDokter);
            kategori = itemView.findViewById(R.id.tvJenisJadwalKonsultasiDokter);
            dotJadwal = itemView.findViewById(R.id.dotJadwalKonsultasiDokter);
            btnDone = itemView.findViewById(R.id.btnDoneJadwalKonsultasiDokter);
            jadwalDokter = itemView.findViewById(R.id.jadwalKonsultasiDokter);
            btnCancel = itemView.findViewById(R.id.btnHapusJadwalKonsultasiDokter);
        }
    }

    public void doneJadwal(){
        queue = Volley.newRequestQueue(context);

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

    public void cancelJadwal(){
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
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
}
