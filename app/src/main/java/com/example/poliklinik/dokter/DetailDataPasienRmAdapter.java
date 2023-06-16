package com.example.poliklinik.dokter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.util.ArrayList;

public class DetailDataPasienRmAdapter extends RecyclerView.Adapter<DetailDataPasienRmAdapter.MyViewHolder> {
    private final DetailDataPasienRmInterface detailDataPasienRmInterface;
    Context context;
    ArrayList<RekamMedisDokterModel> rekamMedisDokterModels;
    String url;
    RequestQueue queue;

    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredModels(ArrayList<RekamMedisDokterModel> filteredModels){
        this.rekamMedisDokterModels = filteredModels;
        notifyDataSetChanged();
    }

    public DetailDataPasienRmAdapter(Context context, ArrayList<RekamMedisDokterModel> rekamMedisDokterModels, DetailDataPasienRmInterface detailDataPasienRmInterface) {
        this.context = context;
        this.rekamMedisDokterModels = rekamMedisDokterModels;
        this.detailDataPasienRmInterface = detailDataPasienRmInterface;
    }

    @NonNull
    @Override
    public DetailDataPasienRmAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_rekam_medis_data_pasien, parent, false);
        return new DetailDataPasienRmAdapter.MyViewHolder(view, detailDataPasienRmInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailDataPasienRmAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tanggal.setText(rekamMedisDokterModels.get(position).getTanggalRM());
        holder.no.setText(rekamMedisDokterModels.get(position).getIdRM());
        holder.nmDokter.setText(rekamMedisDokterModels.get(position).getNmDokterRM());
        holder.diagnosa.setText(rekamMedisDokterModels.get(position).getDiagnosaRM());

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
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
        TextView no;
        TextView nmDokter;
        TextView diagnosa;
        AppCompatImageButton btnHapus;
        public MyViewHolder(@NonNull View itemView, DetailDataPasienRmInterface detailDataPasienRmInterface) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tvTanggalRMDP);
            no = itemView.findViewById(R.id.tvNoRMDP);
            nmDokter = itemView.findViewById(R.id.tvNamaDokterRMDP);
            diagnosa = itemView.findViewById(R.id.tvDiagnosaRMDP);
            btnHapus = itemView.findViewById(R.id.btnDeleteRMDP);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (detailDataPasienRmInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            detailDataPasienRmInterface.onItemClick(pos);
                        }
                    }
                }
            });
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
