package com.example.iot_project.Main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewPagerAdapter extends FragmentStateAdapter {
    private final List<Fragment> Listfrag;
    private Fragment fragment;
    private DatabaseReference dataRef;
    private long count;
    private List<String> typeList;
    private int itemcount;

    public MainViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);//每次開啟都會建立一次，從背景回來也會進入
        Listfrag = new ArrayList<>();
        Listfrag.add(MainTab1Fragment.newInstance("美食伴手禮",""));
        Listfrag.add(MainTab1Fragment.newInstance("最新商品",""));
        Listfrag.add(MainTab1Fragment.newInstance("熱賣商品",""));
        Listfrag.add(MainTab1Fragment.newInstance("文創商品",""));

    }

    @Override
    public int getItemCount() { //傳回Fragment 數量
        return 4;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) { //建立Fragment
        fragment = Listfrag.get(position);
        return fragment; //每次開啟都會建立一次，從背景回來也會進入
    }
}
