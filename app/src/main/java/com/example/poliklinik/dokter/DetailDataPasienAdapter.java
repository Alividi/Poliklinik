package com.example.poliklinik.dokter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DetailDataPasienAdapter extends FragmentStateAdapter {

    public DetailDataPasienAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1){
            return new DetailDataPasienRmFragment();
        }
        return new DetailDataPasienFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
