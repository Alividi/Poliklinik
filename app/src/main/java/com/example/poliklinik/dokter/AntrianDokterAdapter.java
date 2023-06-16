package com.example.poliklinik.dokter;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.Objects;

public class AntrianDokterAdapter extends RecyclerView.Adapter<AntrianDokterAdapter.MyViewHolder> {
    Context context;
    ArrayList<AntrianDokterModel> antrianDokterModels;
    String url;
    RequestQueue queue;

    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredModels(ArrayList<AntrianDokterModel> filteredModels){
        this.antrianDokterModels = filteredModels;
        notifyDataSetChanged();
    }

    public AntrianDokterAdapter(Context context, ArrayList<AntrianDokterModel> antrianDokterModels) {
        this.context = context;
        this.antrianDokterModels = antrianDokterModels;
    }

    @NonNull
    @Override
    public AntrianDokterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_antrian_dokter,parent,false);
        return new AntrianDokterAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AntrianDokterAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.no.setText(antrianDokterModels.get(position).getUrutan());
        holder.nmPasien.setText(antrianDokterModels.get(position).getNmPasien());
        holder.nmDokter.setText(antrianDokterModels.get(position).getNmDokter());

        holder.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/delete_antrian?id="+antrianDokterModels.get(position).getIdAntrian();
                doneAntrian();
                antrianDokterModels.remove(position);
                notifyItemRemoved(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return antrianDokterModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView no;
        TextView nmPasien;
        TextView nmDokter;
        AppCompatImageButton btnDone;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.tvRecyclerViewNoAntrianDokter);
            nmPasien = itemView.findViewById(R.id.tvRecyclerViewAntrianNamaPasienDokter);
            nmDokter = itemView.findViewById(R.id.tvRecyclerViewAntrianNamaDokterDokter);
            btnDone = itemView.findViewById(R.id.btnAcceptAntrianDokter);
        }
    }
    public void doneAntrian(){
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
