package com.example.iot_project.Main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.iot_project.R;
import com.example.iot_project.SearchResultRecyclerAdapter;
import com.example.iot_project.SearchResultsActivity;
import com.example.iot_project.Seller;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainTab1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainTab1Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Map<String, Object>> itemData;
    private RecyclerView RecyclerViewItem;
    private LinearLayoutManager myLayoutManager;
    private List<Map<String, Object>> goodsList;
    private DatabaseReference dataRef;
    private ValueEventListener searchListener;
    private String keyword;
    private String keywordRegex;
    private LayoutInflater myinflater;
    private MainRecyclerAdapter recyclerAdapter;


    public MainTab1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainTab1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainTab1Fragment newInstance(String param1, String param2) {
        MainTab1Fragment fragment = new MainTab1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            keyword = getArguments().getString(ARG_PARAM1);
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myinflater=inflater;
        View viewFrag1 = inflater.inflate(R.layout.fragment_main_tab1, container, false);
        RecyclerViewItem = (RecyclerView)viewFrag1.findViewById(R.id.recyclerView_main_frag1);
        return viewFrag1;
    }

    @Override
    public void onResume() {
        super.onResume();


        //  set the LayoutManager and Adapter of RecuclerView
//      LinearLayoutManager : reverseLayout = false，會按資料順序顯示，true則反轉資料顯示順序
        myLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager
                .VERTICAL, false);
        RecyclerViewItem.setLayoutManager(myLayoutManager);
//        keyword = "美食伴手禮";
        keywordRegex = "^.*" + keyword + ".*$";

        goodsList = new ArrayList<Map<String, Object>>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dataRef = database.getReference();


        searchListener = dataRef.child("goods").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                goodsList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    String datakey = data.getKey().toString();
                    dataRef.child("goods").child(datakey).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                        private String goods_nameCheck;
                        private String seller_idCheck;
                        private String seller_id;

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            goodsList.clear();
                            Log.d("goodsList", "[1]goodsList=" + goodsList);
                            HashMap<String, Object> goodsMap = (HashMap<String, Object>) snapshot.getValue();
                            Log.d("main", "goodsMap=" + goodsMap);

                            seller_id = goodsMap.get("seller_id").toString();

                            if (goodsMap.get("gState").toString().equals("通過")) { // 審核通過商品才能上架
                                for (Object key : goodsMap.keySet().toArray()) {
                                    String mapKey = key.toString(); // Map Key
                                    Object mapValue = goodsMap.get(mapKey); //Map value
                                    Log.d("main", "mapKey[mapValue]=" + mapKey + " " + mapValue);

                                    String StringData = mapValue.toString();
                                    Log.d("main", "keywordRegex=" + keywordRegex);


//                                    seller_id goods_name
//                                  goodsType.type
                                    if(mapKey.equals("seller_id")){
                                        seller_idCheck = StringData;
                                    }
                                    if(mapKey.equals("goods_name")){
                                        goods_nameCheck = StringData;
                                    }

                                    Log.d("main", "[2]goodsList=" + goodsList);

                                    dataRef.child("goodsType").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Log.d("goodsList", "[3]goodsList=" + goodsList);

                                            for(DataSnapshot typedata : snapshot.getChildren()){
                                                Map<String,Object> typeData=(HashMap<String,Object>)typedata.getValue();
                                                String seller_idData = typeData.get("seller_id").toString();
                                                String goods_nameData = typeData.get("goods_name").toString();
                                                if(seller_idData.equals(seller_idCheck)&&goods_nameData.equals(goods_nameCheck)){
                                                    String goods_type = typeData.get("type").toString();
                                                    if(goods_type.matches(keywordRegex)){
                                                        String goods_name =goods_nameData;

                                                        //                                    (goods.seller_id)
                                                        //seller.storeName 取出賣場名稱
                                                        dataRef.child("seller").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                Log.d("goodsList", "[4]goodsList=" + goodsList);

                                                                for (DataSnapshot d : snapshot.getChildren()) {
                                                                    Seller sellerData = d.getValue(Seller.class);
                                                                    String Id = sellerData.getSeller_id();

                                                                    if(goodsMap.get("storeName")!=null) break;
                                                                    if (Id.equals(seller_id)) {
                                                                        String storeName = sellerData.getStoreName();
                                                                        goodsMap.put("storeName", storeName);
                                                                        Log.d("main", "storeName=" + storeName);

                                                                        break;
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });

                                                        //(goods.goods_name && goods.seller_id)
                                                        //goodsNorm.price
                                                        dataRef.child("goodsNorm").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                for (DataSnapshot d2 : snapshot.getChildren()) {
                                                                    Log.d("main","[d2]snapshot.getChildrenCount="+snapshot.getChildrenCount());
                                                                    Map<String, Object> goodsNormData = (HashMap<String, Object>) d2.getValue();
                                                                    String name = goodsNormData.get("goods_name").toString();
                                                                    String sellerId = goodsNormData.get("seller_id").toString();

                                                                    if(goodsMap.get("price")!=null) break;

                                                                    if (sellerId.equals(seller_id) && name.equals(goods_name)) {
                                                                        String price = goodsNormData.get("price").toString();
                                                                        goodsMap.put("price", price);
                                                                        Log.d("main", "price=" + price);
                                                                        break;
                                                                    }

                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });

                                                        //goodsPic.goodsPicture
                                                        dataRef.child("goodsPic").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                for (DataSnapshot d3 : snapshot.getChildren()) {
                                                                    Log.d("main","[d3]snapshot.getChildrenCount="+snapshot.getChildrenCount());
                                                                    Map<String, Object> goodsPicData = (HashMap<String, Object>) d3.getValue();
                                                                    String name = goodsPicData.get("goods_name").toString();
                                                                    String sellerId = goodsPicData.get("seller_id").toString();
                                                                    if(goodsMap.get("goodsPicture")!=null) break;
                                                                    if (sellerId.equals(seller_id) && name.equals(goods_name)) {
                                                                        String goodsPicture = goodsPicData.get("goodsPicture").toString();
                                                                        goodsMap.put("goodsPicture", goodsPicture);
                                                                        goodsList.add(goodsMap);
//                                                                        Log.d("main", "[5]goodsList=" + goodsList);
                                                                        Log.d("goodsList", "[5]goodsList.size=" + goodsList.size());
                                                                        recyclerAdapter = new MainRecyclerAdapter(getContext(),myinflater, goodsList);
                                                                        recyclerAdapter.notifyDataSetChanged();
                                                                        RecyclerViewItem.setAdapter(recyclerAdapter);
                                                                        break;
                                                                    }
                                                                }



                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });
                                                        Log.d("main", "[6]goodsList=" + goodsList);

                                                        goodsMap.put("goods_id",datakey);

//                                                        break;

                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("onResume","onResume()=onResume");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d("onAttach","onAttach()=onAttach");
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        Log.d("onPause","onPause()=onPause");
        super.onPause();
        //        Fragment 不顯示時移除FireBase監聽 : 滑到另一個tab會進入onPause
        dataRef.removeEventListener(searchListener);
    }

}