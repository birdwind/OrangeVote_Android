package com.orange.orangetvote.view.adapter;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> fragmentList) {
        super(fm, behavior);
        this.fragments = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
