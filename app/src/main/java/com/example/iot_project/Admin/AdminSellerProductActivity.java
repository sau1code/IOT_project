package com.example.iot_project.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.iot_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminSellerProductActivity extends AppCompatActivity {

    private TextView textViewNumber;

    private DatabaseReference dataref;
    private ValueEventListener productListener;
    private ListView ListViewSellerProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_seller_product);
        //       顯示返回鍵
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("賣家商品上架審核");
//        goods/key/sState : 通過
//        goods/key/sState : 不通過

        textViewNumber = (TextView) findViewById(R.id.textView_admin_seller_product_number);

        ListViewSellerProduct = (ListView) findViewById(R.id.listView_admin_seller_product_id);
        //      製作表格 id 與 對應表格的帳戶名稱
        String reference = "goods";
        String ID = reference.concat("_id");
//      使用 FireBase 服務
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d("main", "database=" + database);
//      使用  reference 節點進入FireBase
        dataref = database.getReference();
        Log.d("main", "dataref=" + dataref);

        List<Map<String, Object>> ListData = new ArrayList<Map<String, Object>>();

//         從reference為起點下去撈會員資料
        productListener = dataref.child("goods").orderByChild("gState").equalTo("審核中").addValueEventListener(new ValueEventListener() {
            private long productNumber;
            //每次資料更新都會監聽

            private String dataKey;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //怕更新List會有重複資料，所以如果ListData已經有資料要清掉，不然List會一直add上去
                if (ListData.isEmpty() == false) ListData.clear();
                //   取得申請商品上架數量= 幾個 Children
                productNumber = snapshot.getChildrenCount();
                textViewNumber.setText(String.valueOf(productNumber));
//                if (editTextSearch.length() == 0) { //如果沒有執行搜尋，搜尋結果等於會員數量
//                    textViewMemberDataCount.setText(String.valueOf(memberNumber));
//                }
                Log.d("main", "snapshot.getValue()=" + snapshot.getValue());
                for (DataSnapshot data : snapshot.getChildren()) {
                    dataKey = data.getKey();
                    Log.d("main", "dataKey=" + dataKey);
                    dataref.child("goods").child(dataKey).addListenerForSingleValueEvent(new ValueEventListener() { //只監聽一次
                        private Map<String, Object> map;

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String productKey = snapshot.getKey();
                            Log.d("main", "productKey=" + productKey);

                            Object productdata = snapshot.getValue();
                            map = (HashMap<String, Object>) productdata;
//                                Log.d("main","ListData="+ListData);
//                                Log.d("main","ListData.size()="+ListData.size());

                            map.put(ID, productKey);
                            Log.d("main", "map=" + map);
                            String goods_name ="";
                            if (map.get("gName") == null) {
                                goods_name = map.get("goods_name").toString();
                            }else{
                                goods_name = map.get("gName").toString();
                            }

                            ListData.add(map);
//                                      Log.d("main","ListData()="+ListData);

                            SimpleAdapter adpter = new SimpleAdapter(AdminSellerProductActivity.this, ListData, R.layout.listview_admin_seller_product
                                    , new String[]{"goods_name", "seller_id", "createTime"}
                                    , new int[]{R.id.textView_admin_sellerproduct_name, R.id.textView_admin_sellerproduct_id
                                    , R.id.textView_admin_sellerproduct_createTime});
                            adpter.notifyDataSetChanged();
                            ListViewSellerProduct.setAdapter(adpter);



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

        ListViewSellerProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
                Log.d("main", "item=" + item);
                Intent intent = new Intent(AdminSellerProductActivity.this, ProductControlActivity.class);
                for (Object key : item.keySet().toArray()) {
                    String mapKey = key.toString();
                    Object mapValue = item.get(mapKey);
                    Log.d("main", "mapKey[mapValue]=" + mapKey + " " + mapValue);
                    String data = String.valueOf(mapValue);
                    intent.putExtra(mapKey,data);
                }
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { //開啟返回鍵
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataref.removeEventListener(productListener);
    }
}
