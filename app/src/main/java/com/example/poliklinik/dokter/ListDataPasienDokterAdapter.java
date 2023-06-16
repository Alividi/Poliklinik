package com.example.poliklinik.dokter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poliklinik.R;

import java.util.ArrayList;

public class ListDataPasienDokterAdapter extends RecyclerView.Adapter<ListDataPasienDokterAdapter.MyViewHolder> {
    private final ListDataPasienDokterInterface dataPasienDokterInterface;
    Context context;
    ArrayList<PasienModel> pasienModels;

    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredModels(ArrayList<PasienModel> filteredModels){
        this.pasienModels = filteredModels;
        notifyDataSetChanged();
    }


    public ListDataPasienDokterAdapter(Context context, ArrayList<PasienModel> pasienModels, ListDataPasienDokterInterface dataPasienDokterInterface) {
        this.context = context;
        this.pasienModels = pasienModels;
        this.dataPasienDokterInterface = dataPasienDokterInterface;
    }

    @NonNull
    @Override
    public ListDataPasienDokterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_list_data_pasien,parent,false);
        return new ListDataPasienDokterAdapter.MyViewHolder(view, dataPasienDokterInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ListDataPasienDokterAdapter.MyViewHolder holder, int position) {
        holder.nmPasien.setText(pasienModels.get(position).getNama());
        holder.nim.setText(pasienModels.get(position).getNim());
    }

    @Override
    public int getItemCount() {
        return pasienModels.size();
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nmPasien;
        TextView nim;

        public MyViewHolder(@NonNull View itemView, ListDataPasienDokterInterface dataPasienDokterInterface) {
            super(itemView);
            nmPasien = itemView.findViewById(R.id.tvRecyclerViewNamaPasienAdmin);
            nim = itemView.findViewById(R.id.tvRecyclerViewNimPasienAdmin);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataPasienDokterInterface != null){
                        int postion = getAdapterPosition();
                        if (postion != RecyclerView.NO_POSITION){
                            dataPasienDokterInterface.onItemClick(postion);
                        }
                    }
                }
            });
        }
    }
}
