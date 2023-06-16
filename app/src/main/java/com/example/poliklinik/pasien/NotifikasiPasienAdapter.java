package com.example.poliklinik.pasien;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poliklinik.R;

import java.util.ArrayList;

public class NotifikasiPasienAdapter extends RecyclerView.Adapter<NotifikasiPasienAdapter.MyViewHolder> {
    private final NotifikasiPasienInterface notifikasiPasienInterface;
    Context context;
    ArrayList<NotifikasiPasienModel> notifikasiPasienModels;


    public NotifikasiPasienAdapter(Context context, ArrayList<NotifikasiPasienModel> notifikasiPasienModels, NotifikasiPasienInterface notifikasiPasienInterface) {
        this.context = context;
        this.notifikasiPasienModels = notifikasiPasienModels;
        this.notifikasiPasienInterface = notifikasiPasienInterface;
    }

    @NonNull
    @Override
    public NotifikasiPasienAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_notifikasi_pasien, parent, false);
        return new NotifikasiPasienAdapter.MyViewHolder(view, notifikasiPasienInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotifikasiPasienAdapter.MyViewHolder holder, int position) {
        if (notifikasiPasienModels.get(position).getStatusPS().equals("dikirim")){
            holder.tvNotif.setText(holder.tvNotif.getText()+notifikasiPasienModels.get(position).getStatusRM());
            holder.dot.setBackgroundResource(R.drawable.dot_bg_notifikasi_active);
            holder.img.setImageResource(R.drawable.img_notifikasi_active);
        }
        if (notifikasiPasienModels.get(position).getStatusPS().equals("dibaca")){
            holder.tvNotif.setText(holder.tvNotif.getText()+notifikasiPasienModels.get(position).getStatusRM());
            holder.dot.setBackgroundResource(R.drawable.dot_bg_grey);
            holder.img.setImageResource(R.drawable.img_notifikasi);
        }
    }

    @Override
    public int getItemCount() {
        return notifikasiPasienModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        View dot;
        ImageView img;
        TextView tvNotif;

        public MyViewHolder(@NonNull View itemView, NotifikasiPasienInterface notifikasiPasienInterface) {
            super(itemView);
            dot = itemView.findViewById(R.id.dotNotifikasi);
            img = itemView.findViewById(R.id.ivNotifikasi);
            tvNotif = itemView.findViewById(R.id.tvNotifikasi);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(notifikasiPasienInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            notifikasiPasienInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
