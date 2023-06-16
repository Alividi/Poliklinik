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

public class PengajuanRmDokterAdapter extends RecyclerView.Adapter<PengajuanRmDokterAdapter.MyViewHolder> {
    private final PengajuanRmDokterInterface pengajuanRmDokterInterface;
    Context context;
    ArrayList<PengajuanRmDokterModel> pengajuanRmDokterModels;
    String url,urlN;
    RequestQueue queue;

    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredModels(ArrayList<PengajuanRmDokterModel> filteredModels){
        this.pengajuanRmDokterModels = filteredModels;
        notifyDataSetChanged();
    }


    public PengajuanRmDokterAdapter(Context context, ArrayList<PengajuanRmDokterModel> pengajuanRmDokterModels,
                                    PengajuanRmDokterInterface pengajuanRmDokterInterface) {
        this.context = context;
        this.pengajuanRmDokterModels = pengajuanRmDokterModels;
        this.pengajuanRmDokterInterface = pengajuanRmDokterInterface;
    }

    @NonNull
    @Override
    public PengajuanRmDokterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_p_rekam_medis_dokter,parent,false);
        return new PengajuanRmDokterAdapter.MyViewHolder(view, pengajuanRmDokterInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull PengajuanRmDokterAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvNamaPasien.setText(pengajuanRmDokterModels.get(position).getNmPasienRM());
        holder.tvNomor.setText(pengajuanRmDokterModels.get(position).getIdRM());


        if(pengajuanRmDokterModels.get(position).getStatusRM().equals("Diajukan")){
            holder.dot.setBackgroundResource(R.drawable.dot_bg_grey);
            holder.tvStatus.setText("Belum Diizinkan");
            holder.tvStatus.setTextColor(Color.parseColor("#BFBFBF"));
            holder.btnAcc.setBackgroundResource(R.drawable.btn_done_green_bg);
            holder.btnAcc.setImageResource(R.drawable.ic_check_rounded_green);
            holder.btnDec.setBackgroundResource(R.drawable.btn_close_red_bg);
            holder.btnDec.setImageResource(R.drawable.ic_close_round);
        }
        if (pengajuanRmDokterModels.get(position).getStatusRM().equals("Diizinkan")){
            holder.dot.setBackgroundResource(R.drawable.dot_bg_green);
            holder.tvStatus.setText("Diizinkan");
            holder.tvStatus.setTextColor(Color.parseColor("#1BD15D"));
            holder.btnAcc.setEnabled(false);
            holder.btnDec.setEnabled(false);
            holder.btnDec.setBackgroundResource(R.drawable.btn_grayed);
            holder.btnDec.setImageResource(R.drawable.ic_round_close_grayed);
            holder.btnAcc.setBackgroundResource(R.drawable.btn_done_green_bg);
            holder.btnAcc.setImageResource(R.drawable.ic_check_rounded_green);
        }
        if (pengajuanRmDokterModels.get(position).getStatusRM().equals("Ditolak")){
            holder.dot.setBackgroundResource(R.drawable.dot_bg_pink);
            holder.tvStatus.setText("Ditolak");
            holder.tvStatus.setTextColor(Color.parseColor("#E96479"));
            holder.btnAcc.setEnabled(false);
            holder.btnDec.setEnabled(false);
            holder.btnAcc.setBackgroundResource(R.drawable.btn_grayed);
            holder.btnAcc.setImageResource(R.drawable.ic_round_check_grey);
            holder.btnDec.setBackgroundResource(R.drawable.btn_close_red_bg);
            holder.btnDec.setImageResource(R.drawable.ic_close_round);
        }

        holder.btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/update_status_rekam_medis_diizinkan?id="+pengajuanRmDokterModels.get(position).getIdRM();
                statusUpdate();
                urlN ="https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/update_status_pemberitahuan_diizinkan?idrm="+pengajuanRmDokterModels.get(position).getIdRM();
                statusNotif();
                pengajuanRmDokterModels.get(position).setStatusRM("Diizinkan");
                notifyItemChanged(position);
                Toast.makeText(context, "Pengajuan Diizinkan", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/update_status_rekam_medis_ditolak?id="+pengajuanRmDokterModels.get(position).getIdRM();
                statusUpdate();
                urlN ="https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/update_status_pemberitahuan_ditolak?idrm="+pengajuanRmDokterModels.get(position).getIdRM();
                statusNotif();
                pengajuanRmDokterModels.get(position).setStatusRM("Ditolak");
                notifyItemChanged(position);
                Toast.makeText(context, "Pengajuan Ditolak", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pengajuanRmDokterModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvNamaPasien;
        TextView tvNomor;
        TextView tvStatus;
        View dot;
        AppCompatImageButton btnAcc, btnDec;

        public MyViewHolder(@NonNull View itemView, PengajuanRmDokterInterface pengajuanRmDokterInterface) {
            super(itemView);
            tvNamaPasien = itemView.findViewById(R.id.tvNamaPasienPengajuanRMDokter);
            tvNomor = itemView.findViewById(R.id.tvNomorPengajuanRMDokter);
            tvStatus = itemView.findViewById(R.id.tvStatusPRM);
            dot = itemView.findViewById(R.id.dotStatusPRMDokter);
            btnAcc = itemView.findViewById(R.id.btnAcceptPengajuanRMDokter);
            btnDec = itemView.findViewById(R.id.btnDeclinePengajuanRMDokter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pengajuanRmDokterInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            pengajuanRmDokterInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
    public void statusUpdate(){
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

    public void statusNotif(){
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, urlN, new Response.Listener<String>() {
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
