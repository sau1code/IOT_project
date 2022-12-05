package com.example.iot_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_project.Cart.CartActivity;
import com.example.iot_project.Main.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ItemDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imageViewItem;
    private TextView textViewItemName, textViewItemStore, textViewItemPrice,textViewItemDate,textViewItemInventory,textViewItemInfor;
    private TextView textViewItemNumber;
    private ImageButton imageButtonShopping;
    private ImageButton ImageButtonItemAdd,ImageButtonItemMinus;
    private ImageView imageViewHeart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);


        //       顯示返回鍵
        toolbar = (Toolbar) findViewById(R.id.toolbar_item_detail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//      取出商品詳細資料
        Intent intent = getIntent();
        String goods_id = intent.getStringExtra("goods_id");
        String goods_name = intent.getStringExtra("goods_name");
        String storeName = intent.getStringExtra("storeName");
        String price = intent.getStringExtra("price");
        String createTime = intent.getStringExtra("createTime");
        String goodsPicture = intent.getStringExtra("goodsPicture");
        String inventory =intent.getStringExtra("inventory");
        String info = intent.getStringExtra("info");
        Log.d("main","goods_name="+goods_name);
        Log.d("main","storeName="+storeName);
        Log.d("main","price="+price);
        Log.d("main","createTime="+createTime);
        Log.d("main","goodsPicture="+goodsPicture);
        Log.d("main","inventory="+inventory);
        Log.d("main","info="+info);

//      設定標題
        String toolbarTitle = "商品 "+goods_name+" 詳細資料頁面";
        toolbar.setTitle(toolbarTitle);

//       base64 字串轉成 Bitmap 儲存
        byte[] decodedString = Base64.decode(goodsPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

//      確認登入狀態
        SharedPreferences sp = getSharedPreferences("LoginInformation", MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("is_login", false);
        String memberID = sp.getString("member_id", "");

//        get widget
//        購物車頁面
        imageButtonShopping = (ImageButton) findViewById(R.id.imageButton_detail_shoppingcart);
        imageButtonShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLogin){
                    Intent intent = new Intent(ItemDetailActivity.this, CartActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(ItemDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

//        設定商品資訊
        imageViewItem = (ImageView) findViewById(R.id.imageView_item_detail);
        textViewItemName = (TextView) findViewById(R.id.textView_item_detail_title);
        textViewItemStore = (TextView) findViewById(R.id.textView_item_detail_store);
        textViewItemPrice= (TextView) findViewById(R.id.textView_item_detail_price);
        textViewItemDate = (TextView) findViewById(R.id.textView_item_detail_date);
        textViewItemInventory = (TextView) findViewById(R.id.textView_item_detail_inventory);
        textViewItemInfor = (TextView) findViewById(R.id.textView_item_detail_infor);

//       設定商品數量
        textViewItemNumber = (TextView) findViewById(R.id.textView_item_detail_number);
        ImageButtonItemAdd = (ImageButton) findViewById(R.id.imageButton_item_detail_add);
        ImageButtonItemMinus = (ImageButton) findViewById(R.id.imageButton_item_detail_minus);
        textViewItemNumber.setText("0");


//        增加商品數量
        ImageButtonItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numStr = textViewItemNumber.getText().toString();
                int number = Integer.parseInt(numStr);
                if(number>=0){
                    number++;
                    Log.d("main","number="+number);
                    textViewItemNumber.setText(String.valueOf(number));
                }
            }
        });

        //      減少商品數量
        ImageButtonItemMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numStr = textViewItemNumber.getText().toString();
                int number = Integer.parseInt(numStr);
                if(number>0){
                    number--;
                    Log.d("main","number="+number);
                    textViewItemNumber.setText(String.valueOf(number));
                }
            }
        });

//       設定按讚愛心

        imageViewHeart = (ImageView) findViewById(R.id.imageView_item_detail_heart);
        if(!isLogin){
            imageViewHeart.setVisibility(View.GONE);
        }else{
            imageViewHeart.setVisibility(View.VISIBLE);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference dataRef = database.getReference("memberGoods");

            dataRef.orderByChild("member_id").equalTo(memberID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        int count = 0;
                        for(DataSnapshot defaultheart : snapshot.getChildren()){
                            count++;
                            Map<String,Object> defaultMap = (HashMap<String, Object>) defaultheart.getValue();
                            String goodsID = defaultMap.get("goods_id").toString();
                            if(goodsID.equals(goods_id)){
                                String fav = defaultMap.get("favorite").toString();
                                if(fav.equals("1")){
                                    imageViewHeart.setImageResource(R.drawable.heart_48px); // 按讚後
                                }else{
                                    imageViewHeart.setImageResource(R.drawable.favorite_48px); // 按讚前
                                }
                                break;
                            }
                            if(count==snapshot.getChildrenCount()){
                                imageViewHeart.setImageResource(R.drawable.favorite_48px); // 按讚前
                            }
                        }
                    }else{
                        imageViewHeart.setImageResource(R.drawable.favorite_48px); // 按讚前
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


        imageViewHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("LoginInformation", MODE_PRIVATE);
                boolean isLogin = sp.getBoolean("is_login", false);
                if(isLogin) {
                    String member_id = sp.getString("member_id", "");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference dataRef = database.getReference("memberGoods");
                    dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Map<String, Object> heartMap = new HashMap<>();
                            if (snapshot.exists()) {
                                int count = 0;
                                for(DataSnapshot heartData : snapshot.getChildren()){
                                    count++;
                                    heartMap = (HashMap<String, Object>) heartData.getValue();
                                    String goodsId = heartMap.get("goods_id").toString();
                                    String memberId = heartMap.get("member_id").toString();
                                    if(goodsId.equals(goods_id)&& memberId.equals(member_id)) {
                                        String heartKey = heartData.getKey();
                                        String isFavorite = heartMap.get("favorite").toString();
                                        Map<String, Object> map = new HashMap<String, Object>();
                                        if (isFavorite.equals("1")) {
                                            imageViewHeart.setImageResource(R.drawable.favorite_48px); // 按讚前
                                            map.put("favorite", 0);
                                        } else {
                                            imageViewHeart.setImageResource(R.drawable.heart_48px); // 按讚後
                                            map.put("favorite", 1);
                                        }
                                        dataRef.child(heartKey).updateChildren(map);
                                        break;
                                    }

                                    if(count == snapshot.getChildrenCount()){
                                        heartMap.clear();
                                        heartMap.put("member_id",member_id);
                                        heartMap.put("goods_id",goods_id);
                                        heartMap.put("favorite",1);
                                        heartMap.put("bought",0);
                                        //      新增建立時間
                                        //      1. 取得台灣時區(Asia/Taipei)的目前日期時間
                                        ZonedDateTime NowTime = ZonedDateTime.now(ZoneId.of("Asia/Taipei"));
                                        //      2. 設定日期時間格式 : "uuuu-MM-dd HH:mm:ss" = "2022-09-20 20:27:17"
                                        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
                                        //      3. 將目前日期時間格式化，ex: 2022-09-20 20:27:17
                                        String createTime_heart = NowTime.format(dateTimeFormat);
                                        heartMap.put("createTime ",createTime_heart);
                                        dataRef.push().setValue(heartMap);
                                        imageViewHeart.setImageResource(R.drawable.heart_48px); // 按讚後
                                    }
                                }


                            }else{
                                heartMap.put("member_id",member_id);
                                heartMap.put("goods_id",goods_id);
                                heartMap.put("favorite",1);
                                heartMap.put("bought",0);
                                //      新增建立時間
                                //      1. 取得台灣時區(Asia/Taipei)的目前日期時間
                                ZonedDateTime NowTime = ZonedDateTime.now(ZoneId.of("Asia/Taipei"));
                                //      2. 設定日期時間格式 : "uuuu-MM-dd HH:mm:ss" = "2022-09-20 20:27:17"
                                DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
                                //      3. 將目前日期時間格式化，ex: 2022-09-20 20:27:17
                                String createTime_heart = NowTime.format(dateTimeFormat);
                                heartMap.put("createTime ",createTime_heart);
                                dataRef.push().setValue(heartMap);
                                imageViewHeart.setImageResource(R.drawable.heart_48px); // 按讚後

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });



//       設定商品圖片
        imageViewItem.setImageBitmap(decodedByte);

//      設定商品名稱
        textViewItemName.setText(goods_name);

//      設定店家名稱
        textViewItemStore.setText(storeName);

//      設定商品價錢
        textViewItemPrice.setText("新台幣 ");
        textViewItemPrice.append(price);
        textViewItemPrice.append(" 元");

//      設定上架日期
        textViewItemDate.setText(createTime);

//      設定庫存
        textViewItemInventory.setText(inventory);

//      設定商品資訊
        textViewItemInfor.setText(info);


    }

}