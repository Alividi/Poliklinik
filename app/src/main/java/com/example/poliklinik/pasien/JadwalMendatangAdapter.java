package com.example.poliklinik.pasien;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.poliklinik.R;

import java.util.ArrayList;
import java.util.Objects;

public class JadwalMendatangAdapter extends RecyclerView.Adapter<JadwalMendatangAdapter.MyViewHolder> {
    Context context;
    ArrayList<JadwalPasienModel> jadwalPasienModels;
    String url;
    RequestQueue queue;
    SharedPreferences loginPreferences;

    public JadwalMendatangAdapter(Context context, ArrayList<JadwalPasienModel> jadwalPasienModels) {
        this.context = context;
        this.jadwalPasienModels = jadwalPasienModels;
    }

    @NonNull
    @Override
    public JadwalMendatangAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_jadwal_konsultasi_pasien, parent, false);
        return new JadwalMendatangAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalMendatangAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        loginPreferences = context.getSharedPreferences("LoginPrefs", 0);
        if(jadwalPasienModels.isEmpty()){
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }else {
            if (Objects.equals(jadwalPasienModels.get(position).getKalenderNmPasien(), loginPreferences.getString("nama", "-"))){
                holder.jam.setText(jadwalPasienModels.get(position).getKalenderJam());
                holder.nmPasien.setText(jadwalPasienModels.get(position).getKalenderNmPasien());
                holder.nmDokter.setText(jadwalPasienModels.get(position).getKalenderNmDokter());
                holder.kategori.setText(jadwalPasienModels.get(position).getKalenderKategori());
                if (jadwalPasienModels.get(position).getKalenderKategori().equals("Medical Check Up")){
                    holder.kategori.setTextColor(Color.parseColor("#8ED2E2"));
                    holder.dotJadwal.setBackgroundResource(R.drawable.dot_bg_lightblue);
                }
                if (jadwalPasienModels.get(position).getKalenderStatus().equals("selesai")){
                    holder.jadwalPasien.setBackgroundResource(R.drawable.menu_done_bg);
                    holder.btnCancel.setImageResource(R.drawable.trash_red);
                    holder.jam.setTextColor(Color.parseColor("#FFFFFFFF"));
                    holder.nmPasien.setTextColor(Color.parseColor("#FFFFFFFF"));
                    holder.nmDokter.setTextColor(Color.parseColor("#FFFFFFFF"));
                    holder.kategori.setTextColor(Color.parseColor("#FFFFFFFF"));
                    holder.dotJadwal.setBackgroundResource(R.drawable.dot_bg_white);
                    holder.btnCancel.setPadding(40,40,40,40);
                }
            }
            if (!Objects.equals(jadwalPasienModels.get(position).getKalenderNmPasien(), loginPreferences.getString("nama", "-"))) {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
        }

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("api", "onClick id: "+jadwalPasienModels.get(position).getKalenderId());
                url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/delete_jadwal?id="+jadwalPasienModels.get(position).getKalenderId();
                Log.e("api", "onClick url: "+url );
                cancelJadwal();
                jadwalPasienModels.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView jam;
        TextView nmPasien;
        TextView nmDokter;
        TextView kategori;
        View dotJadwal;
        ConstraintLayout jadwalPasien;
        AppCompatImageButton btnCancel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            jam = itemView.findViewById(R.id.tvJamJadwalKonsultasiPasien);
            nmPasien = itemView.findViewById(R.id.tvNamaPasienJadwalKonsultasiPasien);
            nmDokter = itemView.findViewById(R.id.tvNamaDokterJadwalKonsultasiPasien);
            kategori = itemView.findViewById(R.id.tvJenisJadwalKonsultasiPasien);
            dotJadwal = itemView.findViewById(R.id.dotJadwalKonsultasiPasien);
            jadwalPasien = itemView.findViewById(R.id.jadwalKonsultasiPasien);
            btnCancel = itemView.findViewById(R.id.btnDeclineJadwalKonsultasiPasien);
        }
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
