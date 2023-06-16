package com.example.poliklinik.dokter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poliklinik.R;
import com.example.poliklinik.pasien.NotifikasiPasienAdapter;
import com.example.poliklinik.pasien.NotifikasiPasienInterface;

import java.util.ArrayList;

public class NotifikasiDokterAdapter extends RecyclerView.Adapter<NotifikasiDokterAdapter.MyViewHolder> {
    private final NotifikasiDokterInterface notifikasiDokterInterface;
    Context context;
    ArrayList<NotifikasiDokterModel> notifikasiDokterModels;
    public NotifikasiDokterAdapter(Context context, ArrayList<NotifikasiDokterModel> notifikasiDokterModels, NotifikasiDokterInterface notifikasiDokterInterface) {
        this.context = context;
        this.notifikasiDokterModels = notifikasiDokterModels;
        this.notifikasiDokterInterface = notifikasiDokterInterface;
    }

    @NonNull
    @Override
    public NotifikasiDokterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_notifikasi_pasien, parent, false);
        return new MyViewHolder(view, notifikasiDokterInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifikasiDokterAdapter.MyViewHolder holder, int position) {
        holder.tvNotif.setText("Kamu mendapatkan pengajuan rekam medis");
        holder.ingfo.setText("Cek segera permohonan pasien, ya!");

        if (notifikasiDokterModels.get(position).getStatusDT().equals("dikirim")){
            holder.dot.setBackgroundResource(R.drawable.dot_bg_notifikasi_active);
            holder.img.setImageResource(R.drawable.img_notifikasi_active);
        }
        if (notifikasiDokterModels.get(position).getStatusDT().equals("dibaca")){
            holder.dot.setBackgroundResource(R.drawable.dot_bg_grey);
            holder.img.setImageResource(R.drawable.img_notifikasi);
        }

    }

    @Override
    public int getItemCount() {
        return notifikasiDokterModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        View dot;
        ImageView img;
        TextView tvNotif;
        TextView ingfo;
        public MyViewHolder(@NonNull View itemView, NotifikasiDokterInterface notifikasiDokterInterface) {
            super(itemView);
            dot = itemView.findViewById(R.id.dotNotifikasi);
            img = itemView.findViewById(R.id.ivNotifikasi);
            tvNotif = itemView.findViewById(R.id.tvNotifikasi);
            ingfo = itemView.findViewById(R.id.ingfoNotif);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (notifikasiDokterInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            notifikasiDokterInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
