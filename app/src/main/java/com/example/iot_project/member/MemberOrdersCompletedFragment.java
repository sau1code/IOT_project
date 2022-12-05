package com.example.iot_project.member;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iot_project.R;

public class MemberOrdersCompletedFragment extends Fragment {

    private RecyclerView recyclerViewOrders;

    public MemberOrdersCompletedFragment() {}

    public static MemberOrdersCompletedFragment newInstance(String param1, String param2) {
        MemberOrdersCompletedFragment fragment = new MemberOrdersCompletedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_member_orders_completed, container, false);

        recyclerViewOrders = (RecyclerView)v.findViewById(R.id.RecyclerView_member_orders);

        recyclerViewOrders.setLayoutManager(new GridLayoutManager(getContext(),1));
        MemberOrdersRecyclerViewAdapter adapter = new MemberOrdersRecyclerViewAdapter("已完成");
        recyclerViewOrders.setAdapter(adapter);

        return v;
    }
}