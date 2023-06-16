package com.example.poliklinik.dokter;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.poliklinik.LoginActivity;
import com.example.poliklinik.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileDokterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileDokterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor prefsEditor;

    public ProfileDokterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsDokterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileDokterFragment newInstance(String param1, String param2) {
        ProfileDokterFragment fragment = new ProfileDokterFragment();
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
        return inflater.inflate(R.layout.fragment_profile_dokter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginPreferences = getActivity().getSharedPreferences("LoginPrefs", 0);
        prefsEditor = loginPreferences.edit();

        TextView tvNamaProfileDokter = view.findViewById(R.id.tvNamaProfileDokter);
        tvNamaProfileDokter.setText(loginPreferences.getString("nama","-"));
        TextView tvSIPProfileDokter = view.findViewById(R.id.tvSIPProfileDokter);
        tvSIPProfileDokter.setText(loginPreferences.getString("sip","-"));
        TextView tvHPProfileDokter = view.findViewById(R.id.tvHPProfileDokter);
        tvHPProfileDokter.setText(loginPreferences.getString("no_telp","-"));

        TextView btnLogOutProfileDokter = view.findViewById(R.id.btnLogOutProfileDokter);

        btnLogOutProfileDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });
    }

    public void showLogoutDialog(){
        final Dialog dialogLogout = new Dialog(getActivity(), R.style.MaterialDialogSheet);
        dialogLogout.setContentView(R.layout.dialog_logout);
        dialogLogout.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogLogout.getWindow().setGravity(Gravity.BOTTOM);
        dialogLogout.show();

        AppCompatButton btnLogoutDialog = dialogLogout.findViewById(R.id.btnLogoutDialog);
        TextView btnKembaliDialog = dialogLogout.findViewById(R.id.btnKembaliDialog);

        btnLogoutDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefsEditor.clear();
                prefsEditor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        btnKembaliDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout.dismiss();
            }
        });
    }
}