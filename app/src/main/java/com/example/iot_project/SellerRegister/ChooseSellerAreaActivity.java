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

public class ChooseSellerAreaActivity extends AppCompatActivity {

    private ListView listView_sellerChooseArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seller_area);
        setWindow();

        listView_sellerChooseArea = (ListView)findViewById(R.id.sellerChooseArea_listView);
        SharedPreferences sp = getSharedPreferences("sellerDetail",MODE_PRIVATE);
        String county = sp.getString("county","null");
        String[] area ={};
        switch(county){
            case "臺北市":
                area = getResources().getStringArray(R.array.taipei);
                break;
            case "基隆市":
                area = getResources().getStringArray(R.array.keelung);
                break;
            case "新北市":
                area = getResources().getStringArray(R.array.newtaipei);
                break;
            case "宜蘭縣":
                area = getResources().getStringArray(R.array.yilan);
                break;
            case "連江縣":
                area = getResources().getStringArray(R.array.lienchiang);
                break;
            case "新竹縣":
                area = getResources().getStringArray(R.array.hsinchu);
                break;
            case "新竹市":
                area = getResources().getStringArray(R.array.hsinchu_city);
                break;
            case "桃園市":
                area = getResources().getStringArray(R.array.taoyuan);
                break;
            case "苗栗縣":
                area = getResources().getStringArray(R.array.miaoli);
                break;
            case "臺中市":
                area = getResources().getStringArray(R.array.taichung);
                break;
            case "彰化縣":
                area = getResources().getStringArray(R.array.changhua);
                break;
            case "南投縣":
                area = getResources().getStringArray(R.array.nantou);
                break;
            case "嘉義市":
                area = getResources().getStringArray(R.array.chiayi_city);
                break;
            case "嘉義縣":
                area = getResources().getStringArray(R.array.chiayi);
                break;
            case "雲林縣":
                area = getResources().getStringArray(R.array.yunlin);
                break;
            case "臺南市":
                area = getResources().getStringArray(R.array.tainan);
                break;
            case "高雄市":
                area = getResources().getStringArray(R.array.kaohsiung);
                break;
            case "屏東縣":
                area = getResources().getStringArray(R.array.pingtung);
                break;
            case "澎湖縣":
                area = getResources().getStringArray(R.array.penghu);
                break;
            case "金門縣":
                area = getResources().getStringArray(R.array.kinmen);
                break;
            case "臺東縣":
                area = getResources().getStringArray(R.array.taitung);
                break;
            case "花蓮縣":
                area = getResources().getStringArray(R.array.hualien);
                break;
        }
        List<Map<String,String>> arealist = new ArrayList<>();
        for(int i=0;i<area.length;i++){
            Map<String,String> item = new HashMap<>();
            item.put("area",area[i]);
            arealist.add(item);
        }
        SimpleAdapter simAdapter = new SimpleAdapter(ChooseSellerAreaActivity.this,arealist,R.layout.basic_listview_items,
                new String[] {"area"},new int[] {R.id.textView_basiclistView});
        listView_sellerChooseArea.setAdapter(simAdapter);

        listView_sellerChooseArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,String> data = (Map<String, String>) parent.getItemAtPosition(position);
                String areaName = (String)data.get("area");
                Log.d("main","area = "+areaName);
                SharedPreferences sp = getSharedPreferences("sellerDetail",MODE_PRIVATE);
                sp.edit().putString("area",areaName).commit();
                Intent intent = new Intent(ChooseSellerAreaActivity.this,SellerDetailActivity.class);
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