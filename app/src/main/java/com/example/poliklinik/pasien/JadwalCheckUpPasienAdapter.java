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

public class JadwalCheckUpPasienAdapter extends RecyclerView.Adapter<JadwalCheckUpPasienAdapter.MyViewHolder> {
    Context context;
    ArrayList<JadwalPasienModel> jadwalCheckUpPasienModels;
    SharedPreferences loginPreferences;
    String url;
    RequestQueue queue;

    public JadwalCheckUpPasienAdapter(Context context, ArrayList<JadwalPasienModel> jadwalCheckUpPasienModels) {
        this.context = context;
        this.jadwalCheckUpPasienModels = jadwalCheckUpPasienModels;
    }

    @NonNull
    @Override
    public JadwalCheckUpPasienAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_jadwal_checkup_pasien, parent, false);
        return new JadwalCheckUpPasienAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalCheckUpPasienAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        loginPreferences = context.getSharedPreferences("LoginPrefs", 0);
        Log.e("TAG", "onBindViewHolder: "+loginPreferences.getString("nama","-") );
        if(jadwalCheckUpPasienModels.get(position).getKalenderKategori().equals("Medical Check Up")){
            if (Objects.equals(jadwalCheckUpPasienModels.get(position).getKalenderNmPasien(), loginPreferences.getString("nama", "-"))){
                holder.jam.setText(jadwalCheckUpPasienModels.get(position).getKalenderJam());
                holder.nmPasien.setText(jadwalCheckUpPasienModels.get(position).getKalenderNmPasien());
                holder.nmDokter.setText(jadwalCheckUpPasienModels.get(position).getKalenderNmDokter());
                holder.kategori.setText(jadwalCheckUpPasienModels.get(position).getKalenderKategori());
                if (jadwalCheckUpPasienModels.get(position).getKalenderStatus().equals("selesai")){
                    holder.jadwalDokter.setBackgroundResource(R.drawable.menu_done_bg);
                    holder.btnCancel.setImageResource(R.drawable.trash_red);
                    holder.jam.setTextColor(Color.parseColor("#FFFFFFFF"));
                    holder.nmPasien.setTextColor(Color.parseColor("#FFFFFFFF"));
                    holder.nmDokter.setTextColor(Color.parseColor("#FFFFFFFF"));
                    holder.kategori.setTextColor(Color.parseColor("#FFFFFFFF"));
                    holder.dotJadwal.setBackgroundResource(R.drawable.dot_bg_white);
                    holder.btnCancel.setPadding(40,40,40,40);
                }
            } if (!Objects.equals(jadwalCheckUpPasienModels.get(position).getKalenderNmPasien(), loginPreferences.getString("nama", "-"))) {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
        }
        if (jadwalCheckUpPasienModels.get(position).getKalenderKategori().equals("Konsultasi")){
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("api", "onClick id: "+jadwalCheckUpPasienModels.get(position).getKalenderId());
                url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/delete_jadwal?id="+jadwalCheckUpPasienModels.get(position).getKalenderId();
                Log.e("api", "onClick url: "+url );
                cancelJadwal();
                jadwalCheckUpPasienModels.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jadwalCheckUpPasienModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView jam;
        TextView nmPasien;
        TextView nmDokter;
        TextView kategori;
        View dotJadwal;
        ConstraintLayout jadwalDokter;
        AppCompatImageButton btnCancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            jam = itemView.findViewById(R.id.tvJamJadwalCheckUpPasien);
            nmPasien = itemView.findViewById(R.id.tvNamaPasienJadwalCheckUpPasien);
            nmDokter = itemView.findViewById(R.id.tvNamaDokterJadwalCheckUpPasien);
            kategori = itemView.findViewById(R.id.tvJenisJadwalCheckUpPasien);
            dotJadwal = itemView.findViewById(R.id.dotJadwalCheckUpPasien);
            jadwalDokter = itemView.findViewById(R.id.jadwalCheckUpPasien);
            btnCancel = itemView.findViewById(R.id.btnDeclineJadwalCheckUpPasien);
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
