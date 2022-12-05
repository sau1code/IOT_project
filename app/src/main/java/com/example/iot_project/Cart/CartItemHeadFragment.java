package com.example.iot_project.Cart;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iot_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartItemHeadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartItemHeadFragment extends Fragment {

    private View view;
    private boolean isDeleteOpen;
    private String tag;
    private TextView textViewShowDelete;
    private String seller_id;
    private TextView textViewSeller;
    private DatabaseReference fireRef;
    private ValueEventListener fireListener;
    private String sellerName;
    private List<String> keyList;
    private CartAllProductFragment cartAllProductFragment;
    private CartActivity cartActivity;


    public CartItemHeadFragment() {}

    public static CartItemHeadFragment newInstance(String seller_id) {
        CartItemHeadFragment fragment = new CartItemHeadFragment();
        Bundle args = new Bundle();
        args.putString("seller_id", seller_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            seller_id = getArguments().getString("seller_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart_item_head, container, false);
        textViewShowDelete = (TextView)view.findViewById(R.id.textView_cart_head_showdelete);
        textViewSeller = (TextView)view.findViewById(R.id.textView_cart_head_seller);
        keyList = cartAllProductFragment.getGoodsKeyFormKeyMap(seller_id);

        // 設定賣場名稱
        // "SELECT storeName FROM seller WHERE seller_id = seller_id"
        FirebaseDatabase.getInstance()
                .getReference("seller").orderByKey().equalTo(seller_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot sellerMap: snapshot.getChildren()) {
                        sellerName = ((Map<String, String>)sellerMap.getValue()).get("storeName");
                        textViewSeller.setText(sellerName);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        // 將同賣家商品，顯示側滑刪除鍵
        isDeleteOpen = false;
        textViewShowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDeleteOpen = isDeleteOpen ? false : true;
                for (int i = 0; i < keyList.size(); i++) {
                    Fragment f = getParentFragmentManager().findFragmentByTag(keyList.get(i).toString());     // findFragmentByTag(goodsKey)
                    if (f != null) ((CartItemBodyFragment)f).setIsDeleteShow(isDeleteOpen);
                }
            }
        });

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cartActivity = (CartActivity)getActivity();
        cartAllProductFragment = (CartAllProductFragment)getParentFragmentManager().findFragmentByTag("f0");
    }
}