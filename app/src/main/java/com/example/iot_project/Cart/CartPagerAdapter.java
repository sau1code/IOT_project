package com.example.iot_project.Cart;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class CartPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> fList = new ArrayList<Fragment>(){{
        add(new CartAllProductFragment());
        add(new CartBuyAgainFragment());
    }};

    public CartPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
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
