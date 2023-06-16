package com.example.poliklinik.pasien;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.poliklinik.R;
import com.example.poliklinik.dokter.TambahRekamMedisDokterActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class RekamMedisPasienAdapter extends RecyclerView.Adapter<RekamMedisPasienAdapter.MyViewHolder> {
    Context context;
    ArrayList<RekamMedisPasienModel> rekamMedisPasienModels;
    SharedPreferences loginPreferences;
    String url;
    RequestQueue queue;
    String tglSkrng;

    public RekamMedisPasienAdapter(Context context, ArrayList<RekamMedisPasienModel> rekamMedisPasienModels) {
        this.context = context;
        this.rekamMedisPasienModels = rekamMedisPasienModels;
    }

    @NonNull
    @Override
    public RekamMedisPasienAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_rekam_medis_pasien, parent, false);
        return new RekamMedisPasienAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RekamMedisPasienAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        loginPreferences = context.getSharedPreferences("LoginPrefs", 0);
        tglSkrng = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        if (Objects.equals(rekamMedisPasienModels.get(position).getNmPasienRM(),loginPreferences.getString("nama","-"))){
            holder.no.setText(rekamMedisPasienModels.get(position).getIdRM());
            holder.tanggal.setText(rekamMedisPasienModels.get(position).getTanggalRM());

            if(Objects.equals(rekamMedisPasienModels.get(position).getStatusRM(),"Belum Diizinkan")){
                holder.btnAkses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/update_status_rekam_medis_diajukan?id=";
                        url = url + rekamMedisPasienModels.get(position).getIdRM();
                        statusUpdate();
                        rekamMedisPasienModels.get(position).setStatusRM("Diajukan");
                        notifyItemChanged(position);

                        postNotif(rekamMedisPasienModels.get(position).getNmPasienRM(),
                                rekamMedisPasienModels.get(position).getNmDokterRM(),
                                rekamMedisPasienModels.get(position).getIdRM(),
                                tglSkrng,
                                "Diajukan",
                                "dikirim",
                                "menunggu");
                    }
                });
            }
            if(Objects.equals(rekamMedisPasienModels.get(position).getStatusRM(),"Diajukan")){
                holder.btnAkses.setEnabled(false);
                holder.btnAkses.setBackgroundResource(R.drawable.btn_rm_blue);
                holder.status.setText("Belum Diizinkan");
                holder.btnAkses.setText("Akses Rekam Medis Diajukan");
                holder.btnAkses.setTextColor(Color.parseColor("#537DBC"));
            }
            if(Objects.equals(rekamMedisPasienModels.get(position).getStatusRM(),"Diizinkan")){
                holder.dot.setBackgroundResource(R.drawable.dot_bg_green);
                holder.status.setText("Diizinkan");
                holder.status.setTextColor(Color.parseColor("#1BD15D"));
                holder.btnAkses.setBackgroundResource(R.drawable.btn_rm_purple);
                holder.btnAkses.setText("Lihat Rekam Medis");
                holder.btnAkses.setTextColor(Color.parseColor("#FFFFFF"));
                holder.btnAkses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, RekamMedisDetailPasienActivity.class);
                        intent.putExtra("tanggal", rekamMedisPasienModels.get(position).getTanggalRM());
                        intent.putExtra("nomor", rekamMedisPasienModels.get(position).getIdRM());
                        intent.putExtra("dokter",rekamMedisPasienModels.get(position).getNmDokterRM());
                        intent.putExtra("keluhan", rekamMedisPasienModels.get(position).getKeluhanRM());
                        intent.putExtra("diagnosa", rekamMedisPasienModels.get(position).getDiagnosaRM());
                        intent.putExtra("obat1", rekamMedisPasienModels.get(position).getObat1RM());
                        intent.putExtra("jumlah1", rekamMedisPasienModels.get(position).getJumlah1RM());
                        intent.putExtra("obat2", rekamMedisPasienModels.get(position).getObat2RM());
                        intent.putExtra("jumlah2", rekamMedisPasienModels.get(position).getJumlah2RM());
                        intent.putExtra("obat3", rekamMedisPasienModels.get(position).getObat3RM());
                        intent.putExtra("jumlah3", rekamMedisPasienModels.get(position).getJumlah3RM());
                        intent.putExtra("obat4", rekamMedisPasienModels.get(position).getObat4RM());
                        intent.putExtra("jumlah4", rekamMedisPasienModels.get(position).getJumlah4RM());
                        intent.putExtra("obat5", rekamMedisPasienModels.get(position).getObat5RM());
                        intent.putExtra("jumlah5", rekamMedisPasienModels.get(position).getJumlah5RM());
                        v.getContext().startActivity(intent);
                    }
                });
            }
            if(Objects.equals(rekamMedisPasienModels.get(position).getStatusRM(),"Ditolak")){
                holder.dot.setBackgroundResource(R.drawable.dot_bg_pink);
                holder.status.setText("Ditolak");
                holder.status.setTextColor(Color.parseColor("#E96479"));
                holder.btnAkses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/update_status_rekam_medis_diajukan?id=";
                        url = url + rekamMedisPasienModels.get(position).getIdRM();
                        statusUpdate();
                        rekamMedisPasienModels.get(position).setStatusRM("Diajukan");
                        notifyItemChanged(position);

                        postNotif(rekamMedisPasienModels.get(position).getNmPasienRM(),
                                rekamMedisPasienModels.get(position).getNmDokterRM(),
                                rekamMedisPasienModels.get(position).getIdRM(),
                                tglSkrng,
                                "Diajukan",
                                "dikirim",
                                "menunggu");
                    }
                });
            }
        }
        if (!Objects.equals(rekamMedisPasienModels.get(position).getNmPasienRM(), loginPreferences.getString("nama","-"))){
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
    }

    @Override
    public int getItemCount() {
        return rekamMedisPasienModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView no;
        TextView tanggal;
        View dot;
        TextView status;
        AppCompatButton btnAkses;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.tvNoRMPasien);
            tanggal = itemView.findViewById(R.id.tvTanggalRMPasien);
            dot = itemView.findViewById(R.id.dotStatusRMPasien);
            status = itemView.findViewById(R.id.tvStatusRMPasien);
            btnAkses = itemView.findViewById(R.id.btnAksesRMPasien);
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

    public void postNotif(final String nama_pasien, final String nama_dokter, final String id_rekam_medis, final String tanggal,
                          final String status_rm, final String status_dokter, final String status_pasien){
        queue = Volley.newRequestQueue(context);
        url = "https://us-east-1.aws.data.mongodb-api.com/app/poliklinik-uukuv/endpoint/insert_pemberitahuan";

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse: "+response );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getStackTrace();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("nama_pasien", nama_pasien);
                MyData.put("nama_dokter", nama_dokter);
                MyData.put("id_rekam_medis", id_rekam_medis);
                MyData.put("tanggal", tanggal);
                MyData.put("status_rm", status_rm);
                MyData.put("status_dokter", status_dokter);
                MyData.put("status_pasien", status_pasien);
                return MyData;
            }
        };
        queue.add(MyStringRequest);
    }
}
