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
//    ???????????????(seller_id) ????????????(orderStatus)="?????????"?????????????????????(orders_id)
//    ??????????????????????????????(member_id)
//    ??????????????????????????????
//      ????????????(goods.goods_name)
//      ????????????(goodsNorm.norm)
//      ????????????(goodsNorm.price)
//      ????????????(sum???????????????sum??????)
//    ????????????(payway)
//    ????????????(payTime)
//    ????????????(shippingTime)
//    ??????????????????(createTime)


}