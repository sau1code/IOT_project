package com.example.iot_project.salesRecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.iot_project.MyStore.MyStoreActivity;
import com.example.iot_project.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalesRecordActivity extends AppCompatActivity {

    private ViewPager2 SalesRecord_ViewPager;
    private TabLayout SalesRecord_tabLayout;
    private List<Object> salesRecordtabTitle;
    private FragmentManager FragManager;
    private FragmentTransaction fragTransit;
    private OrderDetailFragment toBeShipDetailFrag;
    private OrderToBeShipFragment OrderToBeShipFragment;
    private ImageView imageViewSalesRecordBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_record);
        setWindow();
        SalesRecord_ViewPager = (ViewPager2) findViewById(R.id.SalesRecord_ViewPager);
        SalesRecord_tabLayout = (TabLayout)findViewById(R.id.tabLayout_salesRecord);
        imageViewSalesRecordBack = (ImageView)findViewById(R.id.imageView_salesRecord_back);

        salesRecordtabTitle = new ArrayList<>();
        salesRecordtabTitle.add("待出貨");
        salesRecordtabTitle.add("不成立");
        salesRecordtabTitle.add("退貨/退款");
        salesRecordtabTitle.add("已出貨");
        salesRecordtabTitle.add("已完成");

        SalesRecordPageAdapter pageAdapter = new SalesRecordPageAdapter(this);
        SalesRecord_ViewPager.setAdapter(pageAdapter);

        new TabLayoutMediator(SalesRecord_tabLayout, SalesRecord_ViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText((CharSequence) salesRecordtabTitle.get(position));
            }
        }).attach();

        imageViewSalesRecordBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SalesRecordActivity.this, MyStoreActivity.class);
                startActivity(intent);
            }
        });

    }

    public void orderToBeShipDetail(Map<String,Object> orderMap){
        Map<String, Object> data = orderMap;
        String orderNum = data.get("orderNum").toString();
        String productName = data.get("productName").toString();
        int productNum = (int)data.get("productNum");
        int productPrice = (int)data.get("productPrice");
        int allProductNum = (int)data.get("allProductNum");
        int totalPrice = (int)data.get("totalPrice");
        ArrayList<String> orderList = new ArrayList<>();
        orderList.add(orderNum);
        orderList.add(productName);
        orderList.add(String.valueOf(productNum));
        orderList.add(String.valueOf(productPrice));
        orderList.add(String.valueOf(allProductNum));
        orderList.add(String.valueOf(totalPrice));

        FragManager = getSupportFragmentManager();
        fragTransit = FragManager.beginTransaction();
        toBeShipDetailFrag = OrderDetailFragment.newInstance(orderList,orderNum);
        fragTransit.add(R.id.layout_salesRecord, toBeShipDetailFrag,orderNum)
                .addToBackStack("")
                .commit();
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
        FragmentManager fragManager = getSupportFragmentManager();
        if (fragManager.getBackStackEntryCount() < 1) finish();
        else fragManager.popBackStack();
    }


}





