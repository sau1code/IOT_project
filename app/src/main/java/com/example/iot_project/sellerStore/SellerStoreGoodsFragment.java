package com.example.iot_project.sellerStore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iot_project.R;

public class SellerStoreGoodsFragment extends Fragment {

    private View view;
    private RecyclerView recyclerViewGoods;

    public static SellerStoreGoodsFragment newInstance() {
        return new SellerStoreGoodsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_seller_store_goods, container, false);
        findView();
        setData();
        setAdapter();
        setListener();
        return view;
    }

    private void findView() {
        recyclerViewGoods = (RecyclerView)view.findViewById(R.id.RecyclerView_seller_store_goods);
    }

    private void setData() {
    }

    private void setAdapter() {
        recyclerViewGoods.setLayoutManager(new GridLayoutManager(getContext(),2));
        SellerStoreRecycleViewAdapter adapter = new SellerStoreRecycleViewAdapter();
        recyclerViewGoods.setAdapter(adapter);
    }

    private void setListener() {
    }
}