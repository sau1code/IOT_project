package com.example.iot_project.member;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iot_project.R;

public class MemberOrdersUnpaidFragment extends Fragment {

    private MemberActivity memberActivity;
    private RecyclerView recyclerViewGoods;
    private TextView TextViewCardviewOrders;

    public MemberOrdersUnpaidFragment() {}

    public static MemberOrdersUnpaidFragment newInstance(String param1, String param2) {
        MemberOrdersUnpaidFragment fragment = new MemberOrdersUnpaidFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_member_orders_unpaid, container, false);
        memberActivity = (MemberActivity)getActivity();
        
        recyclerViewGoods = (RecyclerView)v.findViewById(R.id.RecyclerView_member_orders);

        recyclerViewGoods.setLayoutManager(new GridLayoutManager(getContext(),1));
        MemberOrdersRecyclerViewAdapter adapter = new MemberOrdersRecyclerViewAdapter("待付款");
        recyclerViewGoods.setAdapter(adapter);
        
        return v;
    }
}