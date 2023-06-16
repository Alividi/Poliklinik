package com.example.poliklinik.dokter;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.poliklinik.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailDataPasienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailDataPasienFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Intent intent;
    EditText etNama;
    TextView etAlamat;
    EditText etNim;
    EditText etBB;
    EditText etTb;

    public DetailDataPasienFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailDataPasienFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailDataPasienFragment newInstance(String param1, String param2) {
        DetailDataPasienFragment fragment = new DetailDataPasienFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_data_pasien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etNama = view.findViewById(R.id.etNmPasienDataPasien);
        etAlamat = view.findViewById(R.id.etAlamatDataPasien);
        etNim = view.findViewById(R.id.etNimDataPasien);
        etBB = view.findViewById(R.id.etBBDataPasien);
        etTb = view.findViewById(R.id.etTBDataPasien);

        intent = requireActivity().getIntent();

        etNama.setText(intent.getStringExtra("nama"));
        etAlamat.setText(intent.getStringExtra("alamat"));
        etNim.setText(intent.getStringExtra("nim"));
        etBB.setText(intent.getStringExtra("bb"));
        etTb.setText(intent.getStringExtra("tb"));
    }
}