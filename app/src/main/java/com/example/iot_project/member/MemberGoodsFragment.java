package com.example.iot_project.member;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.iot_project.R;

public class MemberGoodsFragment extends Fragment {

    private View view;
    private MemberActivity memberActivity;
    private ImageView ImageViewBack;
    private RecyclerView recyclerViewGoods;
    private TextView textViewBar;

    public static MemberGoodsFragment newInstance(String barName) {
        MemberGoodsFragment fragment = new MemberGoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("barName", barName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_member_goods, container, false);

        findView();
        setData();


/*
        預計是
        switch(barName) {
            這裡改變firebase的搜尋條件
            可能是製作部分關鍵字餵給makeListByFirebase
            或是每個case內都寫一組完整的firebase
        }

        // "SELECT goods_id FORM memberGoods WHERE member_id = 我的id AND favorite = 1;"      // 喜歡
        // "SELECT goods_id FORM memberGoods WHERE member_id = 我的id AND bought = 1;"        // 買過
        // "SELECT goods_id FORM memberGoods WHERE member_id = 我的id ORDER BY createTime;"   // 看過 (依照日期排序)

        // "SELECT (goods_name, seller_id, soldQuantity) FORM goods WHERE goods_id = 上面搜出的商品id;"        // 由商品id 搜出 [品名] 賣家ID [售出量]
        // "SELECT sCity FORM seller WHERE seller_id = 上面搜出的賣家id;"                                      // 由賣家id 搜出 [sCity]
        // "SELECT goodsPicture FORM goodsPic WHERE seller_id = 上面搜出的賣家id AND goods_name = 商品名稱;"   // 由賣家ID + 品名 = [圖片]

         // "SELECT price FORM goodsNorm WHERE seller_id = 上面搜出的賣家id;"                                   // 由商品id 搜出 [價格]





        製成List後放入適配器
*/

        setAdapter();
        setListener();


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        memberActivity = (MemberActivity)getActivity();
    }

    private void findView(){
        ImageViewBack = (ImageView)view.findViewById(R.id.ImageView_member_back);
        recyclerViewGoods = (RecyclerView)view.findViewById(R.id.RecyclerView_member_goods);
        textViewBar = (TextView)view.findViewById(R.id.TextView_member_bar);
    }

    private void setData(){
        textViewBar.setText(getArguments().getString("barName"));
    }

    private void setAdapter(){
        recyclerViewGoods.setLayoutManager(new GridLayoutManager(getContext(),2));
        MemberGoodsRecyclerViewAdapter adapter = new MemberGoodsRecyclerViewAdapter();
        recyclerViewGoods.setAdapter(adapter);
    }

    private void setListener(){
        ImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberActivity.onBackPressed();
            }
        });
    }

}