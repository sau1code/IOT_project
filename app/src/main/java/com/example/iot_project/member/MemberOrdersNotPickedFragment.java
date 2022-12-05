package com.example.iot_project.member;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iot_project.R;


public class MemberOrdersNotPickedFragment extends Fragment {


    private RecyclerView recyclerViewGoods;

    public MemberOrdersNotPickedFragment() {}

    public static MemberOrdersNotPickedFragment newInstance(String param1, String param2) {
        MemberOrdersNotPickedFragment fragment = new MemberOrdersNotPickedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_member_orders_notpicked, container, false);
        
        recyclerViewGoods = (RecyclerView)v.findViewById(R.id.RecyclerView_member_orders);

        recyclerViewGoods.setLayoutManager(new GridLayoutManager(getContext(),1));
        MemberOrdersRecyclerViewAdapter adapter = new MemberOrdersRecyclerViewAdapter("待取貨");
        recyclerViewGoods.setAdapter(adapter);

        return v;
    }
}