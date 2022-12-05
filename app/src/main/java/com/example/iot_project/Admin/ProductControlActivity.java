package com.example.iot_project.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.iot_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductControlActivity extends AppCompatActivity {

    private DatabaseReference dataref;
    private ImageView imageViewProduct;
    private EditText editTextViewType;
    private EditText editTextPrice;
    private Button buttonDisagree,buttonAgree;
    private String seller_id;
    private EditText editTextProductId;
    private EditText editTextCreateTime,editTextProductName,editTextInfor,editTextVolume;
    private EditText editTextInventory,editText711,editTextFamilymart,editTextPostoffice,editTextBlackCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_control);

//       get widget
         imageViewProduct = (ImageView) findViewById(R.id.imageView_admin_sellerproduct_id);
         editTextViewType = (EditText) findViewById(R.id.editText_admin_sellerproduct_type);
         editTextPrice = (EditText) findViewById(R.id.editText_admin_sellerproduct_price);

        //       顯示返回鍵
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        String goods_id =intent.getStringExtra("goods_id");
        seller_id =intent.getStringExtra("seller_id");
        String goods_name =intent.getStringExtra("goods_name");
        String info =intent.getStringExtra("info");

        String  packageWidth =intent.getStringExtra("packageWidth");
        String  packageLength =intent.getStringExtra("packageLength");
        String  packageHeight =intent.getStringExtra("packageHeight");

        String inventory =intent.getStringExtra("inventory");
        String seven =intent.getStringExtra("seven");
        String familyMart  =intent.getStringExtra("familyMart");
        String postOffice =intent.getStringExtra("postOffice");
        String blackCat  =intent.getStringExtra("blackCat");

        String createTime =intent.getStringExtra("createTime");

//      取得editText元件
        editTextProductId = (EditText) findViewById(R.id.editText_admin_sellerproduct_id);
        editTextCreateTime = (EditText) findViewById(R.id.editText_admin_sellerproduct_createTime);
        editTextProductName = (EditText) findViewById(R.id.editText_admin_sellerproduct_productname);
        editTextInfor = (EditText) findViewById(R.id.editText_admin_sellerproduct_info);
        editTextVolume = (EditText) findViewById(R.id.editText_admin_sellerproduct_volume);
        editTextInventory = (EditText) findViewById(R.id.editText_admin_sellerproduct_inventory);
        editText711 = (EditText) findViewById(R.id.editText_admin_sellerproduct_711);
        editTextFamilymart = (EditText) findViewById(R.id.editText_admin_sellerproduct_familymart);
        editTextPostoffice = (EditText) findViewById(R.id.editText_admin_sellerproduct_postoffice);
        editTextBlackCat = (EditText) findViewById(R.id.editText_admin_sellerproduct_blackcat);



//      editText 放入對應的會員資料
        editTextProductId.setText(goods_id);
        editTextCreateTime.setText(createTime);
        editTextProductName.setText(goods_name);
        editTextInfor.setText(info);
        editTextVolume.setText(packageLength+"*");
        editTextVolume.append(packageWidth+"*");
        editTextVolume.append(packageHeight+"(長*寬*高)");

        editTextInventory.setText(inventory);
        editText711.setText(isDelivery(seven));
        editTextFamilymart.setText(isDelivery(familyMart));
        editTextPostoffice.setText(isDelivery(postOffice));
        editTextBlackCat.setText(isDelivery(blackCat));


//       disable ediText
        editTextProductId.setEnabled(false);
        editTextCreateTime.setEnabled(false);
        editTextProductName.setEnabled(false);
        editTextInfor.setEnabled(false);
        editTextVolume.setEnabled(false);
        editTextInventory.setEnabled(false);
        editText711.setEnabled(false);
        editTextFamilymart.setEnabled(false);
        editTextPostoffice.setEnabled(false);
        editTextBlackCat.setEnabled(false);
        editTextPrice.setEnabled(false);
        editTextViewType.setEnabled(false);


//      取得商品id
//        String goods_id = intent.getStringExtra("goods_id");
//        取得商品名稱
//        String goods_name = intent.getStringExtra("goods_name");

//        取得賣家ID
//        seller_id = intent.getStringExtra("seller_id");

        //      使用 FireBase 服務
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d("main", "database=" + database);
//      使用  reference 節點進入FireBase
        dataref = database.getReference();
        Log.d("main", "dataref=" + dataref);

        //        取得商品照片
        dataref.child("goodsPic").orderByChild("goods_name").equalTo(goods_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot PicData:snapshot.getChildren()){
                    Object PicObject = PicData.getValue();
                    Map<String, Object> PicMap = (Map<String, Object>) PicObject;
                    String sellerID = PicMap.get("seller_id").toString();
                    if(sellerID.equals(seller_id)){
                        String goodsPicture = PicMap.get("goodsPicture").toString();
                        Log.d("main","goodsPicture="+goodsPicture);
                        byte[] decodedString = Base64.decode(goodsPicture, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageViewProduct.setImageBitmap(decodedByte);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //        取得商品類型
        dataref.child("goodsType").orderByChild("goods_name").equalTo(goods_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> typeList = new ArrayList<>();
                for(DataSnapshot typeData:snapshot.getChildren()){
                    Object typeObject = typeData.getValue();
                    Map<String, Object> typeMap = (Map<String, Object>) typeObject;
                    String sellerID = typeMap.get("seller_id").toString();
                    if(sellerID.equals(seller_id)) {
                        String type = typeMap.get("type").toString();
                        Log.d("main", "type=" + type);
                        typeList.add(type);
                    }

                    for (int i = 0; i < typeList.size(); i++) {
                        String typeElement = typeList.get(i);
                        for (int j = i + 1; j < typeList.size(); j++) {
                            String othersType = typeList.get(j);
                            if (othersType.equals(typeElement)) {
                                typeList.remove(j);
                            }
                        }
                    }
                    Log.d("main", "typearray=" + typeList);
                    String alltype = typeList.toString();
                    String alltypeStr = alltype.replaceAll("(\\[|\\])", "");
                    Log.d("main", "alltypeStr=" + alltypeStr);
                    editTextViewType.setText(alltypeStr);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        取得商品價錢
        dataref.child("goodsNorm").orderByChild("goods_name").equalTo(goods_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> priceList = new ArrayList<>();
                for(DataSnapshot  priceData:snapshot.getChildren()){
                    Object  priceObject =  priceData.getValue();
                    Map<String, Object> priceMap = (Map<String, Object>)  priceObject;
                    String sellerID = priceMap.get("seller_id").toString();
                    if(sellerID.equals(seller_id)) {
                        String price = priceMap.get("price").toString();
                        Log.d("main", "price=" + price);
                        priceList.add(price);
                    }

                    for (int i = 0; i < priceList.size(); i++) {
                        String typeElement = priceList.get(i);
                        for (int j = i + 1; j < priceList.size(); j++) {
                            String othersType = priceList.get(j);
                            if (othersType.equals(typeElement)) {
                                priceList.remove(j);
                            }
                        }
                    }
                    String allprice = priceList.toString();
                    String allpriceStr = allprice.replaceAll("(\\[|\\])", "");
                    Log.d("main", "allprice=" + allprice);
                    editTextPrice.setText("新臺幣 "+allpriceStr+" 元");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //       不通過直接刪除新增商品申請
        buttonDisagree = (Button) findViewById(R.id.button_admin_sellerproduct_disagree);
        buttonDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder altbuilder = new AlertDialog.Builder(ProductControlActivity.this);

                altbuilder.setTitle("刪除賣家商品上架申請");
                altbuilder.setMessage("請確認是否刪除申請?");
                altbuilder.setIcon(android.R.drawable.ic_dialog_alert);
                altbuilder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference dataRef = database.getReference();
                        dataRef.child("goods").child(goods_id).removeValue();//delete此申請
                        onBackPressed();//關掉此activity
                    }
                });

                altbuilder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                altbuilder.show();

            }
        });
//       新增商品申請通過 :
//        goods/key/gState : 通過
        buttonAgree = (Button) findViewById(R.id.button_admin_sellerproduct_agree);
        buttonAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dataRef = database.getReference();
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("gState","通過");
                //                    String createTime = picCursor.getString(picCursor.getColumnIndexOrThrow("createTime"));
                //      新增建立時間
                //      1. 取得台灣時區(Asia/Taipei)的目前日期時間
                ZonedDateTime NowTime = ZonedDateTime.now(ZoneId.of("Asia/Taipei"));
                //      2. 設定日期時間格式 : "uuuu-MM-dd HH:mm:ss" = "2022-09-20 20:27:17"
                DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
                //      3. 將目前日期時間格式化，ex: 2022-09-20 20:27:17
                String createTime = NowTime.format(dateTimeFormat);
                map.put("createTime",createTime); //更新createTime為上架時間
                dataRef.child("goods").child(goods_id).updateChildren(map);
                onBackPressed();//關掉此activity
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){ //開啟返回鍵
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public String isDelivery(String deliveryWay){
        String str="";
        switch (deliveryWay){
            case "0":
                str="不適用";
                break;
            case "1":
                str="適用";
                break;
        }
        return str;
    }
}