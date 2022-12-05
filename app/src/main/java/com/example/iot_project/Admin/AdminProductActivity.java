package com.example.iot_project.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminProductActivity extends AppCompatActivity {

    private Button buttonDelete;
    private ImageView imageViewProduct;
    private EditText editTextViewType,editTextPrice;
    private String seller_id;
    private Button buttonDelete2;
    private EditText editTextProductId;
    private EditText editTextCreateTime,editTextProductName,editTextInfor,editTextVolume;
    private EditText editTextInventory,editText711,editTextFamilymart,editTextPostoffice,editTextBlackCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);

        //       顯示返回鍵
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        //       get widget
        imageViewProduct = (ImageView) findViewById(R.id.imageView_admin_product_id);
        editTextViewType = (EditText) findViewById(R.id.editText_admin_product_type);
        editTextPrice = (EditText) findViewById(R.id.editText_admin_product_price);

        Intent intent = getIntent();
        String goods_id =intent.getStringExtra("goods_Id");
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
        editTextProductId = (EditText) findViewById(R.id.editText_admin_product_id);
        editTextCreateTime = (EditText) findViewById(R.id.editText_admin_product_createTime);
        editTextProductName = (EditText) findViewById(R.id.editText_admin_product_productname);
        editTextInfor = (EditText) findViewById(R.id.editText_admin_product_info);
        editTextVolume = (EditText) findViewById(R.id.editText_admin_product_volume);
        editTextInventory = (EditText) findViewById(R.id.editText_admin_product_inventory);
        editText711 = (EditText) findViewById(R.id.editText_admin_product_711);
        editTextFamilymart = (EditText) findViewById(R.id.editText_admin_product_familymart);
        editTextPostoffice = (EditText) findViewById(R.id.editText_admin_product_postoffice);
        editTextBlackCat = (EditText) findViewById(R.id.editText_admin_product_blackcat);



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
        editTextViewType.setEnabled(false);
        editTextPrice.setEnabled(false);

//      取得商品id
//        String goods_id = intent.getStringExtra("goods_Id");
//        取得商品名稱
//        String goods_name = intent.getStringExtra("goods_name");

//        取得賣家ID
        seller_id = intent.getStringExtra("seller_id");

        //      使用 FireBase 服務
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d("main", "database=" + database);
//      使用  reference 節點進入FireBase
        DatabaseReference dataref = database.getReference();
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

        //       下架商品
        buttonDelete = (Button) findViewById(R.id.button_admin_product_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder altbuilder = new AlertDialog.Builder(AdminProductActivity.this);

                altbuilder.setTitle("下架賣家商品");
                altbuilder.setMessage("請確認是否下架賣家商品?");
                altbuilder.setIcon(android.R.drawable.ic_dialog_alert);
                altbuilder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference dataRef = database.getReference();
                        Map<String,Object>map = new HashMap<>();
                        map.put("gState","已下架");
                        dataRef.child("goods").child(goods_id).updateChildren(map);
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

        //       標示商品已違規商品
        buttonDelete2 = (Button) findViewById(R.id.button_admin_product_delete2);
        buttonDelete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder altbuilder = new AlertDialog.Builder(AdminProductActivity.this);

                altbuilder.setTitle("賣家商品列為違規");
                altbuilder.setMessage("請確認是否標註賣家商品已違規?");
                altbuilder.setIcon(android.R.drawable.ic_dialog_alert);
                altbuilder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference dataRef = database.getReference();
                        Map<String,Object>map = new HashMap<>();
                        map.put("gState","已違規");
                        dataRef.child("goods").child(goods_id).updateChildren(map);
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