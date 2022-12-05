package com.example.iot_project.member;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iot_project.R;

public class MemberOrdersNotShippedFragment extends Fragment {

    private RecyclerView RecyclerViewOrders;

    public MemberOrdersNotShippedFragment() {}

    public static MemberOrdersNotShippedFragment newInstance(String param1, String param2) {
        MemberOrdersNotShippedFragment fragment = new MemberOrdersNotShippedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_member_orders_notshipped, container, false);

        RecyclerViewOrders = (RecyclerView)v.findViewById(R.id.RecyclerView_member_orders);

        RecyclerViewOrders.setLayoutManager(new GridLayoutManager(getContext(),1));
        MemberOrdersRecyclerViewAdapter adapter = new MemberOrdersRecyclerViewAdapter("待出貨");
        RecyclerViewOrders.setAdapter(adapter);

        return v;
    }
}