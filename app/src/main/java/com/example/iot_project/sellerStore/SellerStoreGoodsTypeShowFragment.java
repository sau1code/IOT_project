package com.example.iot_project.sellerStore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iot_project.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SellerStoreGoodsTypeShowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellerStoreGoodsTypeShowFragment extends Fragment {

    private View view;
    private TextView textViewBar;
    private ImageView imageViewBack;
    private SellerStoreActivity sellerStoreActivity;
    private RecyclerView recyclerViewShow;

    public static SellerStoreGoodsTypeShowFragment newInstance(String barName) {
        SellerStoreGoodsTypeShowFragment fragment = new SellerStoreGoodsTypeShowFragment();
        Bundle args = new Bundle();
        args.putString("barName", barName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_seller_store_goods_type_show, container, false);
        sellerStoreActivity = (SellerStoreActivity)getActivity();
        textViewBar = (TextView)view.findViewById(R.id.textView_seller_store_type_show_bar);
        textViewBar.setText(getArguments().getString("barName"));
        imageViewBack = (ImageView)view.findViewById(R.id.imageView_seller_store_type_show_back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellerStoreActivity.onBackPressed();
            }
        });
        recyclerViewShow = (RecyclerView)view.findViewById(R.id.RecyclerView_seller_goods_type_show);
        recyclerViewShow.setLayoutManager(new GridLayoutManager(getContext(),2));
        SellerStoreRecycleViewAdapter adapter = new SellerStoreRecycleViewAdapter(); // 這裡要去資料庫篩條件符合 "賣家"+"五金" 做一個list
        recyclerViewShow.setAdapter(adapter);
        return view;
    }
}