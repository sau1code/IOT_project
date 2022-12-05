package com.example.iot_project.salesRecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.iot_project.MyStore.MyStoreActivity;
import com.example.iot_project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    private ListView listView_payment;
    private ImageView imageView_payment_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setWindow();
        onBackPressed();
        imageView_payment_back = (ImageView)findViewById(R.id.imageView_payment_back);
        listView_payment = (ListView) findViewById(R.id.listView_payment);
        List<Map<String,Object>> paymentList= new ArrayList<>();
        Map<String,Object> paymentMap = new HashMap<>();
        for(int i=0;i<30;i++){
            paymentMap.put("orderNum","F123456789");
            paymentMap.put("totalPrice","800");
            paymentMap.put("createTime","2022-9-28");
            paymentList.add(paymentMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(PaymentActivity.this,paymentList,R.layout.payment_listview_items,new String[]{ "orderNum","totalPrice","createTime"},new int[]{
                R.id.textView_payment_orderNumber,R.id.textView_payment_totalAmount,R.id.textView_payment_orderDate});
        listView_payment.setAdapter(simpleAdapter);

        imageView_payment_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, MyStoreActivity.class);
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

    //---------------------------------------------------------------------------------------------
//    取出此賣家(seller_id) 訂單狀態(orderStatus)="已完成"的所有訂單編號(orders_id)
//    以及訂單中的買家帳號(member_id)
//    以及訂單中所有商品的
//      商品名稱(goods.goods_name)
//      商品規格(goodsNorm.norm)
//      商品售價(goodsNorm.price)
//      購買數量(sum資料表中的sum欄位)
//    付款方式(payway)
//    付款時間(payTime)
//    出貨時間(shippingTime)
//    訂單成立時間(createTime)


}