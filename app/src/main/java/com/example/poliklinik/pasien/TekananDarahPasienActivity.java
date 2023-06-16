package com.example.poliklinik.pasien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.poliklinik.R;

import java.util.ArrayList;

public class TekananDarahPasienActivity extends AppCompatActivity implements TekananDarahPasienInterface {
    ArrayList<TekananDarah> tekananDarahArrayList;
    DBDataSource dataSource;
    TekananDarahPasienAdapter tekananDarahPasienAdapter;
    RecyclerView recyclerViewTekananDarahPasien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tekanan_darah_pasien);
        ImageButton btnBackTekananDarahPasien = findViewById(R.id.btnBackTekananDarahPasien);
        AppCompatButton btnTambahTekananDarahPasien = findViewById(R.id.btnTambahTekananDarahPasien);

        tekananDarahArrayList = new ArrayList<>();
        dataSource = new DBDataSource(TekananDarahPasienActivity.this);
        tekananDarahArrayList = dataSource.getAllTekananDarah();

        tekananDarahPasienAdapter = new TekananDarahPasienAdapter(TekananDarahPasienActivity.this, tekananDarahArrayList, this);
        recyclerViewTekananDarahPasien = findViewById(R.id.recyclerViewTekananDarahPasien);
        recyclerViewTekananDarahPasien.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTekananDarahPasien.setAdapter(tekananDarahPasienAdapter);


        btnTambahTekananDarahPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TekananDarahPasienActivity.this, TambahTekananDarahPasienActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnBackTekananDarahPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        TekananDarah tkn = tekananDarahArrayList.get(position);
        TekananDarah td = dataSource.getTekananDarah(tkn.getId());
        Intent intent = new Intent(TekananDarahPasienActivity.this, TekananDarahPasienDetailActivity.class);

        Bundle bundle = new Bundle();
        bundle.putLong("id",td.getId());
        bundle.putString("tanggal",td.getTanggal_tekanan_darah());
        bundle.putString("waktu",td.getWaktu_tekanan_darah());
        bundle.putString("atas",td.getBatas_atas_tekanan());
        bundle.putString("bawah",td.getBatas_bawah_tekanan());
        bundle.putString("komen",td.getKomentar_tekanan_darah());
        intent.putExtras(bundle);
        dataSource.close();

        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_tekanan_darah_pasien);
        dialog.setTitle("Pilih Aksi");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ConstraintLayout btnEditTekananDarahPasien = dialog.findViewById(R.id.btnEditTekananDarahPasien);
        TextView btnDeleteTekananDarahPasien = dialog.findViewById(R.id.btnDeleteTekananDarahPasien);

        btnEditTekananDarahPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TekananDarah tkn = tekananDarahArrayList.get(position);
                TekananDarah td = dataSource.getTekananDarah(tkn.getId());
                Intent intent = new Intent(TekananDarahPasienActivity.this, TekananDarahPasienEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id",td.getId());
                bundle.putString("tanggal",td.getTanggal_tekanan_darah());
                bundle.putString("waktu",td.getWaktu_tekanan_darah());
                bundle.putString("atas",td.getBatas_atas_tekanan());
                bundle.putString("bawah",td.getBatas_bawah_tekanan());
                bundle.putString("komen",td.getKomentar_tekanan_darah());
                intent.putExtras(bundle);
                dataSource.close();
                startActivity(intent);
                dialog.dismiss();
            }
        });

        btnDeleteTekananDarahPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TekananDarah tkn = tekananDarahArrayList.get(position);
                TekananDarah td = dataSource.getTekananDarah(tkn.getId());
                dataSource.DeleteTekananDarah(td.getId());
                dialog.dismiss();
                finish();
                startActivity(getIntent());
            }
        });
    }
}