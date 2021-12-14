package com.wibmothon.fbank.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.wibmothon.fbank.ui.fragment.goalstab.AchievedGoalsTab;
import com.wibmothon.fbank.ui.fragment.goalstab.InProgressGoalsTab;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {

    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new InProgressGoalsTab();
            case 1:
                return new AchievedGoalsTab();
        }
        return new InProgressGoalsTab();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
