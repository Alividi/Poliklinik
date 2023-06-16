package com.example.poliklinik.dokter;

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
import com.example.poliklinik.pasien.JadwalCheckUpPasienAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class JadwalCheckUpDokterAdapter extends RecyclerView.Adapter<JadwalCheckUpDokterAdapter.MyViewHolder> {
    Context context;
    ArrayList<JadwalCheckUpDokterModel> jadwalCheckUpDokterModels;
    SharedPreferences loginPreferences;
    String url;
    RequestQueue queue;

    public JadwalCheckUpDokterAdapter(Context context, ArrayList<JadwalCheckUpDokterModel> jadwalCheckUpDokterModels) {
        this.context = context;
        this.jadwalCheckUpDokterModels = jadwalCheckUpDokterModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_jadwal_checkup_dokter, parent, false);
        return new JadwalCheckUpDokterAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        loginPreferences = context.getSharedPreferences("LoginPrefs", 0);
        Log.e("TAG", "onBindViewHolder: "+loginPreferences.getString("nama","-") );
        if(jadwalCheckUpDokterModels.get(position).getCheckUpKategori().equals("Medical Check Up")){
            if (Objects.equals(jadwalCheckUpDokterModels.get(position).getCheckUpNmDokter(), loginPreferences.getString("nama", "-"))){
                holder.jam.setText(jadwalCheckUpDokterModels.get(position).getCheckUpJam());
                holder.nmPasien.setText(jadwalCheckUpDokterModels.get(position).getCheckUpNmPasien());
                holder.nmDokter.setText(jadwalCheckUpDokterModels.get(position).getCheckUpNmDokter());
                holder.kategori.setText(jadwalCheckUpDokterModels.get(position).getCheckUpKategori());
                if (jadwalCheckUpDokterModels.get(position).getCheckUpStatus().equals("selesai")){
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
            } if (!Objects.equals(jadwalCheckUpDokterModels.get(position).getCheckUpNmDokter(), loginPreferences.getString("nama", "-"))) {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
        }
        if (jadwalCheckUpDokterModels.get(position).getCheckUpKategori().equals("Konsultasi")){
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        holder.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("api", "onClick id: "+jadwalCheckUpDokterModels.get(position).getCheckUpId());
                url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/update_statusjadwal_by_id?id="+jadwalCheckUpDokterModels.get(position).getCheckUpId();
                Log.e("api", "onClick url: "+url );
                doneJadwal();
                jadwalCheckUpDokterModels.get(position).setCheckUpStatus("selesai");
                jadwalCheckUpDokterModels.set(position, new JadwalCheckUpDokterModel(jadwalCheckUpDokterModels.get(position).getCheckUpId(),
                        jadwalCheckUpDokterModels.get(position).getCheckUpJam(),
                        jadwalCheckUpDokterModels.get(position).getCheckUpNmPasien(),
                        jadwalCheckUpDokterModels.get(position).getCheckUpNmDokter(),
                        jadwalCheckUpDokterModels.get(position).getCheckUpKategori(),
                        jadwalCheckUpDokterModels.get(position).getCheckUpStatus()));
                notifyItemChanged(position);
            }
        });
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("api", "onClick id: "+jadwalCheckUpDokterModels.get(position).getCheckUpId());
                url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/delete_jadwal?id="+jadwalCheckUpDokterModels.get(position).getCheckUpId();
                Log.e("api", "onClick url: "+url );
                cancelJadwal();
                jadwalCheckUpDokterModels.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jadwalCheckUpDokterModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView jam;
        TextView nmPasien;
        TextView nmDokter;
        TextView kategori;
        View dotJadwal;
        ConstraintLayout jadwalDokter;
        AppCompatImageButton btnDone, btnCancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            jam = itemView.findViewById(R.id.tvJamJadwalCheckUpDokter);
            nmPasien = itemView.findViewById(R.id.tvNamaPasienJadwalCheckUpDokter);
            nmDokter = itemView.findViewById(R.id.tvNamaDokterJadwalCheckUpDokter);
            kategori = itemView.findViewById(R.id.tvJenisJadwalCheckUpDokter);
            dotJadwal = itemView.findViewById(R.id.dotJadwalCheckUpDokter);
            btnDone = itemView.findViewById(R.id.btnDoneJadwalCheckUpDokter);
            jadwalDokter = itemView.findViewById(R.id.jadwalCheckUpDokter);
            btnCancel = itemView.findViewById(R.id.btnHapusJadwalCheckUpDokter);
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
