package com.example.l23_0656_a1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MoviePagerAdapter extends FragmentStateAdapter {

    public MoviePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) return new NowShowingFragment();
        return new ComingSoonFragment();
    }

    @Override
    public int getItemCount() { return 2; }
}