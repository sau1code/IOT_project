package com.example.iot_project.MyProduct;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MyProductPageAdapter  extends FragmentStateAdapter {
    private ArrayList<Fragment> fList = new ArrayList<Fragment>(){{
        add(new ProductLaunchedAlreadyFragment());
        add(new ProductSoldOutFragment());
        add(new ProductPendingReviewFragment());
        add(new ProductViolationFragment());
    }};

    public  MyProductPageAdapter(@NonNull FragmentActivity fragmentActivity) {
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
