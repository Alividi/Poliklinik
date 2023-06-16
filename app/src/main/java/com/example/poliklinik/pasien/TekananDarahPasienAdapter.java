package com.example.poliklinik.pasien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poliklinik.R;

import java.util.ArrayList;

public class TekananDarahPasienAdapter extends RecyclerView.Adapter<TekananDarahPasienAdapter.MyViewHolder> {
    private final TekananDarahPasienInterface tekananDarahPasienInterface;
    Context context;
    ArrayList<TekananDarah> tekananDarahArrayList;

    public TekananDarahPasienAdapter(Context context, ArrayList<TekananDarah> tekananDarahArrayList, TekananDarahPasienInterface tekananDarahPasienInterface) {
        this.context = context;
        this.tekananDarahArrayList = tekananDarahArrayList;
        this.tekananDarahPasienInterface = tekananDarahPasienInterface;
    }

    @NonNull
    @Override
    public TekananDarahPasienAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_tekanan_darah_pasien, parent,false);
        return new TekananDarahPasienAdapter.MyViewHolder(view, tekananDarahPasienInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull TekananDarahPasienAdapter.MyViewHolder holder, int position) {
        TekananDarah tekananDarah = tekananDarahArrayList.get(position);
        holder.tvNmrCatatanTekananDarah.setText(String.valueOf(position+1));
        holder.tvTglCatatanTekananDarah.setText(tekananDarah.getTanggal_tekanan_darah());
        holder.tvBtsAtsTekananDarah.setText(tekananDarah.getBatas_atas_tekanan());
        holder.tvBtsBwhTekananDarah.setText(tekananDarah.getBatas_bawah_tekanan());
    }

    @Override
    public int getItemCount() {
        return tekananDarahArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvNmrCatatanTekananDarah;
        TextView tvTglCatatanTekananDarah;
        TextView tvBtsAtsTekananDarah;
        TextView tvBtsBwhTekananDarah;
        public MyViewHolder(@NonNull View itemView, TekananDarahPasienInterface tekananDarahPasienInterface) {
            super(itemView);
            tvNmrCatatanTekananDarah = itemView.findViewById(R.id.tvNmrCatatanTekananDarah);
            tvTglCatatanTekananDarah = itemView.findViewById(R.id.tvTglCatatanTekananDarah);
            tvBtsAtsTekananDarah = itemView.findViewById(R.id.tvBtsAtsTekananDarah);
            tvBtsBwhTekananDarah = itemView.findViewById(R.id.tvBtsBwhTekananDarah);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tekananDarahPasienInterface != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            tekananDarahPasienInterface.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (tekananDarahPasienInterface != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            tekananDarahPasienInterface.onLongItemClick(position);
                        }
                    }
                    return true;
                }
            });
        }
    }
}
