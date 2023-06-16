package com.example.poliklinik.dokter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.poliklinik.R;
import com.example.poliklinik.pasien.RekamMedisDetailPasienActivity;

import java.util.ArrayList;
import java.util.Objects;

public class RekamMedisDokterAdapter extends RecyclerView.Adapter<RekamMedisDokterAdapter.MyViewHolder> {
    Context context;
    ArrayList<RekamMedisDokterModel> rekamMedisDokterModels;
    String url;
    RequestQueue queue;

    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredModels(ArrayList<RekamMedisDokterModel> filteredModels){
        this.rekamMedisDokterModels = filteredModels;
        notifyDataSetChanged();
    }

    public RekamMedisDokterAdapter(Context context, ArrayList<RekamMedisDokterModel> rekamMedisDokterModels) {
        this.context = context;
        this.rekamMedisDokterModels = rekamMedisDokterModels;
    }

    @NonNull
    @Override
    public RekamMedisDokterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_rekam_medis_dokter, parent, false);
        return new RekamMedisDokterAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RekamMedisDokterAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tanggal.setText(rekamMedisDokterModels.get(position).getTanggalRM());
        holder.nmPasien.setText(rekamMedisDokterModels.get(position).getNmPasienRM());
        holder.no.setText(rekamMedisDokterModels.get(position).getIdRM());
        holder.nmDokter.setText(rekamMedisDokterModels.get(position).getNmDokterRM());

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RekamMedisDetailDokterActivity.class);
                intent.putExtra("tanggal", rekamMedisDokterModels.get(position).getTanggalRM());
                intent.putExtra("nomor", rekamMedisDokterModels.get(position).getIdRM());
                intent.putExtra("pasien",rekamMedisDokterModels.get(position).getNmPasienRM());
                intent.putExtra("keluhan", rekamMedisDokterModels.get(position).getKeluhanRM());
                intent.putExtra("diagnosa", rekamMedisDokterModels.get(position).getDiagnosaRM());
                intent.putExtra("obat1", rekamMedisDokterModels.get(position).getObat1RM());
                intent.putExtra("jumlah1", rekamMedisDokterModels.get(position).getJumlah1RM());
                intent.putExtra("obat2", rekamMedisDokterModels.get(position).getObat2RM());
                intent.putExtra("jumlah2", rekamMedisDokterModels.get(position).getJumlah2RM());
                intent.putExtra("obat3", rekamMedisDokterModels.get(position).getObat3RM());
                intent.putExtra("jumlah3", rekamMedisDokterModels.get(position).getJumlah3RM());
                intent.putExtra("obat4", rekamMedisDokterModels.get(position).getObat4RM());
                intent.putExtra("jumlah4", rekamMedisDokterModels.get(position).getJumlah4RM());
                intent.putExtra("obat5", rekamMedisDokterModels.get(position).getObat5RM());
                intent.putExtra("jumlah5", rekamMedisDokterModels.get(position).getJumlah5RM());
                v.getContext().startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/delete_rekam_medis?id="+rekamMedisDokterModels.get(position).getIdRM();
                deleteRM();
                rekamMedisDokterModels.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rekamMedisDokterModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tanggal;
        TextView nmPasien;
        TextView no;
        TextView nmDokter;
        AppCompatImageButton btnDetail;
        AppCompatImageButton btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tvTanggalRM);
            nmPasien = itemView.findViewById(R.id.tvNamaPasienRM);
            no = itemView.findViewById(R.id.tvNomorRM);
            nmDokter = itemView.findViewById(R.id.tvNamaDokterRM);
            btnDetail = itemView.findViewById(R.id.btnDetailRM);
            btnDelete = itemView.findViewById(R.id.btnDeleteRM);
        }
    }
    public void deleteRM(){
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
