package com.example.poliklinik.dokter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.example.poliklinik.pasien.JadwalKonsultasiPasienAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class JadwalKonsultasiDokterAdapter extends RecyclerView.Adapter<JadwalKonsultasiDokterAdapter.MyViewHolder> {
    Context context;
    ArrayList<JadwalKonsultasiDokterModel> jadwalKonsultasiDokterModels;
    SharedPreferences loginPreferences;
    String url;
    RequestQueue queue;

    public JadwalKonsultasiDokterAdapter(Context context, ArrayList<JadwalKonsultasiDokterModel> jadwalKonsultasiDokterModels) {
        this.context = context;
        this.jadwalKonsultasiDokterModels = jadwalKonsultasiDokterModels;
    }

    @NonNull
    @Override
    public JadwalKonsultasiDokterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_jadwal_konsultasi_dokter, parent, false);
        return new JadwalKonsultasiDokterAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalKonsultasiDokterAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        loginPreferences = context.getSharedPreferences("LoginPrefs", 0);
        Log.e("TAG", "onBindViewHolder: "+loginPreferences.getString("nama","-") );
        if(jadwalKonsultasiDokterModels.get(position).getKonsultasiKategori().equals("Konsultasi")){
            if (Objects.equals(jadwalKonsultasiDokterModels.get(position).getKonsultasiNmDokter(), loginPreferences.getString("nama", "-"))){
                holder.jam.setText(jadwalKonsultasiDokterModels.get(position).getKonsultasiJam());
                holder.nmPasien.setText(jadwalKonsultasiDokterModels.get(position).getKonsultasiNmPasien());
                holder.nmDokter.setText(jadwalKonsultasiDokterModels.get(position).getKonsultasiNmDokter());
                holder.kategori.setText(jadwalKonsultasiDokterModels.get(position).getKonsultasiKategori());
                if (jadwalKonsultasiDokterModels.get(position).getKonsultasiStatus().equals("selesai")){
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
            } if (!Objects.equals(jadwalKonsultasiDokterModels.get(position).getKonsultasiNmDokter(), loginPreferences.getString("nama", "-"))) {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
        }
        if (jadwalKonsultasiDokterModels.get(position).getKonsultasiKategori().equals("Medical Check Up")){
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        holder.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("api", "onClick id: "+jadwalKonsultasiDokterModels.get(position).getKonsultasiId());
                url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/update_statusjadwal_by_id?id="+jadwalKonsultasiDokterModels.get(position).getKonsultasiId();
                Log.e("api", "onClick url: "+url );
                doneJadwal();
                jadwalKonsultasiDokterModels.get(position).setKonsultasiStatus("selesai");
                jadwalKonsultasiDokterModels.set(position, new JadwalKonsultasiDokterModel(jadwalKonsultasiDokterModels.get(position).getKonsultasiId(),
                        jadwalKonsultasiDokterModels.get(position).getKonsultasiJam(),
                        jadwalKonsultasiDokterModels.get(position).getKonsultasiNmPasien(),
                        jadwalKonsultasiDokterModels.get(position).getKonsultasiNmDokter(),
                        jadwalKonsultasiDokterModels.get(position).getKonsultasiKategori(),
                        jadwalKonsultasiDokterModels.get(position).getKonsultasiStatus()));
                notifyItemChanged(position);
            }
        });
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("api", "onClick id: "+jadwalKonsultasiDokterModels.get(position).getKonsultasiId());
                url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/delete_jadwal?id="+jadwalKonsultasiDokterModels.get(position).getKonsultasiId();
                Log.e("api", "onClick url: "+url );
                cancelJadwal();
                jadwalKonsultasiDokterModels.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jadwalKonsultasiDokterModels.size();
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
