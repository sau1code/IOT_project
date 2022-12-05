package com.example.iot_project.SellerRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.iot_project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseBankAreaActivity extends AppCompatActivity {

    private ListView ChooseBankAreaListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bank_area);
        setWindow();

        ChooseBankAreaListView = (ListView) findViewById(R.id.chooseBankArea_listView);
        String[] area = getResources().getStringArray(R.array.bankArea);
        List<Map<String,String>> areaList = new ArrayList<>();
        for(int i=0;i < area.length;i++){
            Map<String,String> item = new HashMap<>();
            item.put("bankArea",area[i]);
            areaList.add(item);
        }
        SimpleAdapter simAdapter = new SimpleAdapter(ChooseBankAreaActivity.this,areaList,R.layout.basic_listview_items,
                new String[]{"bankArea"},new int[] {R.id.textView_basiclistView});
        ChooseBankAreaListView.setAdapter(simAdapter);
        ChooseBankAreaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,String>data = (Map<String,String>)parent.getItemAtPosition(position);
                String bankArea = (String)data.get("bankArea");
                Log.d("main","bankArea = "+bankArea);
                SharedPreferences sp = getSharedPreferences("sellerDetail",MODE_PRIVATE);
                sp.edit().putString("bankArea",bankArea).commit();
                Intent intent = new Intent(ChooseBankAreaActivity.this,BankAccountActivity.class);
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
}