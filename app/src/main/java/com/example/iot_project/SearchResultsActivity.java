package com.example.iot_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.iot_project.Cart.CartActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DatabaseReference dataRef;
    private ValueEventListener searchListener;
    private List<Map<String, Object>> goodsList;
    private EditText editTextSearch;
    private ImageButton imageButtonSearch;
    private LinearLayout LinearLayoutSearch;
    private ListView listViewSearch;
    private RecyclerView recyclerViewData;
    private LinearLayoutManager myLayoutManager;
    private SearchResultRecyclerAdapter recyclerAdapter;
    private ImageButton imageButtonShopping;
    private String keyword;
    private String keywordRegex;
    private String storeNameCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        //       顯示返回鍵
        toolbar = (Toolbar) findViewById(R.id.toolbar_search_result_id);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SharedPreferences sp = getSharedPreferences("LoginInformation", MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("is_login", false);
        //        購物車頁面
        imageButtonShopping = (ImageButton) findViewById(R.id.imageButton_search_result_shoppingcart);
        imageButtonShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isLogin){
                   Intent intent = new Intent(SearchResultsActivity.this, CartActivity.class);
                   startActivity(intent);
               }else{
                   Intent intent = new Intent(SearchResultsActivity.this, LoginActivity.class);
                   startActivity(intent);
               }

            }
        });


//      RecyclerView
        recyclerViewData = (RecyclerView) findViewById(R.id.recyclerView_search_results);

        //  set the LayoutManager and Adapter of RecuclerView
//      LinearLayoutManager : reverseLayout = false，會按資料順序顯示，true則反轉資料顯示順序
        myLayoutManager = new LinearLayoutManager(SearchResultsActivity.this, LinearLayoutManager
                .VERTICAL, false);
        recyclerViewData.setLayoutManager(myLayoutManager);

        Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");
        keywordRegex = "^.*" + keyword + ".*$";

        Log.d("main", "keyword=" + keyword);
        editTextSearch = (EditText) findViewById(R.id.editText_search_keyword);
        imageButtonSearch = (ImageButton) findViewById(R.id.imageButton_result_search);

        editTextSearch.setEnabled(false);
        editTextSearch.setText(keyword);

        imageButtonSearch.setVisibility(View.INVISIBLE);
//        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String searchStr = editTextSearch.getText().toString();
//                if (!searchStr.equals(keyword)) {
//                    keyword = searchStr;
//                    keywordRegex = "^.*" + keyword + ".*$";
//                    Log.d("main", "keyword=" + keyword);
//                    Log.d("main", "keywordRegex=" + keywordRegex);
//                }
//            }
//        });


//      來不及做篩選排序 先隱藏起來功能
        LinearLayoutSearch = (LinearLayout) findViewById(R.id.linearLayout_search_result);
        LinearLayoutSearch.setVisibility(View.GONE);

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
                        private String seller_id;

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
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



                                    if (StringData.matches(keywordRegex)) {

//                                    取出 seller_id , goods_name
//                                        String seller_id = goodsMap.get("seller_id").toString();
                                        String goods_name = goodsMap.get("goods_name").toString();

//                                    (goods.seller_id)
                                        //seller.storeName 取出賣場名稱
                                        dataRef.child("seller").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot d : snapshot.getChildren()) {
                                                    Seller sellerData = d.getValue(Seller.class);
                                                    String Id = sellerData.getSeller_id();
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
                                                    Map<String, Object> goodsNormData = (HashMap<String, Object>) d2.getValue();
                                                    String name = goodsNormData.get("goods_name").toString();
                                                    String sellerId = goodsNormData.get("seller_id").toString();

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
                                                    Map<String, Object> goodsPicData = (HashMap<String, Object>) d3.getValue();
                                                    String name = goodsPicData.get("goods_name").toString();
                                                    String sellerId = goodsPicData.get("seller_id").toString();

                                                    if (sellerId.equals(seller_id) && name.equals(goods_name)) {
                                                        String goodsPicture = goodsPicData.get("goodsPicture").toString();
                                                        goodsMap.put("goodsPicture", goodsPicture);
                                                        break;
                                                    }
                                                }


                                                Log.d("main", "goodsList=" + goodsList);


                                                recyclerAdapter = new SearchResultRecyclerAdapter(SearchResultsActivity.this, goodsList);
                                                recyclerAdapter.notifyDataSetChanged();
                                                recyclerViewData.setAdapter(recyclerAdapter);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        goodsMap.put("goods_id",datakey);
                                        goodsList.add(goodsMap);
                                        break;
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataRef.removeEventListener(searchListener);
    }
}