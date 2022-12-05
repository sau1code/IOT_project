package com.example.iot_project.member;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MemberOrdersPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> fList = new ArrayList<Fragment>(){{
        add(new MemberOrdersUnpaidFragment());
        add(new MemberOrdersNotShippedFragment());
        add(new MemberOrdersNotPickedFragment());
        add(new MemberOrdersCompletedFragment());
    }};

    public MemberOrdersPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fList.get(position);
    }

    @Override
    public int getItemCount() {
        return fList.size();
    }

}
