package com.example.iot_project.MyStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iot_project.DBHelper;
import com.example.iot_project.Member;
import com.example.iot_project.MyProduct.MyProductActivity;
import com.example.iot_project.R;
import com.example.iot_project.Seller;
import com.example.iot_project.member.MemberActivity;
import com.example.iot_project.salesRecord.PaymentActivity;
import com.example.iot_project.salesRecord.SalesRecordActivity;
import com.example.iot_project.sellerStore.SellerStoreActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MyStoreActivity extends AppCompatActivity {

    private TextView textViewMyStore_name, textViewMyStore_account, textViewMyStore_checkSalesRecord, textViewMyStore_toBeShipped, textViewMyStore_invaild, textViewMyStore_return, textViewMyStore_myProduct, textViewMyStore_payment;
    private Button buttonMyStore_checkStore;
    private static final String DB_FILE = "demo.db";
    private static final String DB_TABLE = "create_goodsSQL";
    private DBHelper dbHelper;
    private ImageView imageView_myPicture;
    private TextView textView_myStore_checkStore;
    private Button button_myStore_editStore;
    private DatabaseReference dataRef;
    private ValueEventListener ordersEventListner;
    private Map<String, Object> SellerMap;
    private TextView textView_memberCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);
        setWindow();
        onBackPressed();
        imageView_myPicture = (ImageView) findViewById(R.id.imageView_myStore_picture);
        textViewMyStore_name = (TextView) findViewById(R.id.textView_myStore_name);
        textViewMyStore_account = (TextView) findViewById(R.id.textView_myStore_account);
        textViewMyStore_checkSalesRecord = (TextView) findViewById(R.id.textView_myStore_checkSalesRecord);
        textViewMyStore_toBeShipped = (TextView) findViewById(R.id.textView_myStore_toBeShipped);
        textViewMyStore_invaild = (TextView) findViewById(R.id.textView_myStore_invalid);
        textViewMyStore_return = (TextView) findViewById(R.id.textView_myStore_return);
        textViewMyStore_myProduct = (TextView) findViewById(R.id.textView_myStore_myProduct);
        textViewMyStore_payment = (TextView) findViewById(R.id.textView_myStore_payment);
        textView_myStore_checkStore = (TextView) findViewById(R.id.textView_myStore_checkStore);
        textView_memberCenter = (TextView)findViewById(R.id.textView_memberCenter);

        button_myStore_editStore = (Button) findViewById(R.id.button_myStore_editStore);

        imageView_myPicture.setImageResource(R.drawable.cat);

        button_myStore_editStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyStoreActivity.this, EditStoreActivity.class);
                startActivity(intent);
            }
        });

        textView_myStore_checkStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyStoreActivity.this, SellerStoreActivity.class);
                startActivity(intent);
            }
        });

        textViewMyStore_myProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyStoreActivity.this, MyProductActivity.class);
                startActivity(intent);
            }
        });

        textViewMyStore_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyStoreActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

        textViewMyStore_checkSalesRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyStoreActivity.this, SalesRecordActivity.class);
                startActivity(intent);


            }
        });

        textView_memberCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyStoreActivity.this, MemberActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setWindow() {
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(0xFFFFFF);
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // -------------------------------------------------------------------------------------------------
//  ???????????????????????? : [216 ??????Map?????????????????????]
//  ????????????????????????(member_id)??? ????????????(storePicture) ??? ????????????(storeName) ???????????????(account_name)
//  ????????????????????????(member_id) ????????????(orderStatus) =  "?????????" ????????????
//  ????????????????????????(member_id) ????????????(orderStatus) =  "?????????" ????????????
//  ????????????????????????(member_id) ????????????(orderStatus) =  "??????"&"??????" ????????????
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        dataRef = database.getReference();
//        SharedPreferences spData = getSharedPreferences("LoginInformation", MODE_PRIVATE);
////      ???Map ??????????????????:
//        SellerMap = new HashMap<String,Object>();
////      ------------------------------------------------------------------------
//        //  ????????????????????????(member_id)??? ????????????(account_name)
//        String seller_id = spData.getString("member_id", "");
//        Log.d("main","seller_id="+seller_id);
//        dataRef.child("member").child(seller_id).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Member account = snapshot.getValue(Member.class);
//                String account_name = account.getAccount_name();
//                SellerMap.put("account_name",account_name);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//        ordersEventListner = dataRef.child("orders").orderByChild("seller_id").equalTo(seller_id).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //  ordersToBeShipCount : ????????????????????????(member_id) ????????????(orderStatus) =  "?????????" ????????????
//                int ordersToBeShipCount = 0;
//                //  ordersInvaildCount : ????????????????????????(member_id) ????????????(orderStatus) =  "?????????" ????????????
//                int ordersInvaildCount = 0;
//                // ordersReturnCount :  ????????????????????????(member_id) ????????????(orderStatus) =  "??????"&"??????" ????????????
//                int ordersReturnCount = 0;
//
//                for (DataSnapshot data : snapshot.getChildren()) {
////                  --------------------------------------------------
//                    Object ordersData = data.getValue();
//                    Log.d("main", "ordersData=" + ordersData);
//                    Map<String, Object> ordersMap = (Map<String, Object>) ordersData;
////                  ---------------------------------------------------------------
//                    String orderStatus = ordersMap.get("orderStatus").toString();
//                    Log.d("main", "orderStatus=" + orderStatus);
//
//                    if (orderStatus.equals("?????????")) {
//                        ordersToBeShipCount++;
//                    }
//
//                    if (orderStatus.equals("?????????")) {
//                        ordersInvaildCount++;
//                    }
//
//                    if (orderStatus.equals("??????")||orderStatus.equals("??????")) {
//                        ordersReturnCount++;
//                    }
//                }
//
//                SellerMap.put("ordersToBeShipCount",ordersToBeShipCount);
//                SellerMap.put("ordersInvaildCount",ordersInvaildCount);
//                SellerMap.put("ordersReturnCount",ordersReturnCount);
//
//
////
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
////      ????????????????????????(member_id)??? ????????????(storePicture) ??? ????????????(storeName) ???
//        dataRef.child("seller").child(seller_id).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
//            @Override
//            public void onSuccess(DataSnapshot dataSnapshot) {
//                Seller seller = dataSnapshot.getValue(Seller.class);
//                String storeName = seller.getStoreName();
//                Log.d("main","storeName="+storeName);
//                String storePicture = seller.getStorePicture();
//                Log.d("main","storePicture="+storePicture);
//                SellerMap.put("storeName",storeName);
//                SellerMap.put("storePicture",storePicture);
////                -------------------------------------------------
////                SellerMap
////                ?????????????????????????????????????????????SellerMap
////                ------------------------------------------------
//
//                Log.d("main","SellerMap="+SellerMap);
//            }
//        });

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //      ??????Activity??????????????????FireBase?????????????????????
//        dataRef.removeEventListener(ordersEventListner);
//    }

}
