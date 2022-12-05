
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

public class ChooseBankBranchActivity extends AppCompatActivity {

    private ListView ChooseBankBranchListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bank_branch);
        setWindow();

        ChooseBankBranchListView = (ListView)findViewById(R.id.chooseBankBranch_listView);
        SharedPreferences sp = getSharedPreferences("sellerDetail",MODE_PRIVATE);
        String bankName = sp.getString("bankName","004 臺灣銀行");
        String bankArea = sp.getString("bankArea","臺北市");
        String [] bankBranch={};
        switch (bankName){
            case "004 臺灣銀行":
                switch (bankArea){
                    case "臺北市":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行台北);
                        break;
                    case "基隆市":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行基隆);
                        break;
                    case "新北市":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行新北);
                        break;
                    case "宜蘭縣":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行宜蘭);
                        break;
                    case "新竹地區":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行新竹);
                        break;
                    case "桃園市":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行桃園);
                        break;
                    case "苗栗縣":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行苗栗);
                        break;
                    case "臺中市":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行台中);
                        break;
                    case "彰化縣":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行彰化);
                        break;
                    case "南投縣":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行南投);
                        break;
                    case "嘉義地區":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行嘉義);
                        break;
                    case "雲林縣":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行雲林);
                        break;
                    case "臺南市":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行台南);
                        break;
                    case "高雄市":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行高雄);
                        break;
                    case "屏東縣":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行屏東);
                        break;
                    case "澎湖縣":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行澎湖);
                        break;
                    case "臺東縣":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行台東);
                        break;
                    case "花蓮縣":
                        bankBranch = getResources().getStringArray(R.array.臺灣銀行花蓮);
                        break;
                }
                break;
            case "006 合作金庫商業銀行":
                switch (bankArea){
                    case "臺北市":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行台北);
                        break;
                    case "基隆市":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行基隆);
                        break;
                    case "新北市":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行新北);
                        break;
                    case "宜蘭縣":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行宜蘭);
                        break;
                    case "新竹地區":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行新竹);
                        break;
                    case "桃園市":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行桃園);
                        break;
                    case "苗栗縣":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行苗栗);
                        break;
                    case "臺中市":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行台中);
                        break;
                    case "彰化縣":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行彰化);
                        break;
                    case "南投縣":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行南投);
                        break;
                    case "嘉義地區":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行嘉義);
                        break;
                    case "雲林縣":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行雲林);
                        break;
                    case "臺南市":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行台南);
                        break;
                    case "高雄市":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行高雄);
                        break;
                    case "屏東縣":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行屏東);
                        break;
                    case "澎湖縣":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行澎湖);
                        break;
                    case "臺東縣":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行台東);
                        break;
                    case "花蓮縣":
                        bankBranch = getResources().getStringArray(R.array.合作金庫商業銀行花蓮);
                        break;
                }
                break;
            case "007 第一商業銀行":
                switch (bankArea){
                    case "臺北市":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行臺北);
                        break;
                    case "基隆市":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行基隆);
                        break;
                    case "新北市":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行新北);
                        break;
                    case "宜蘭縣":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行宜蘭);
                        break;
                    case "新竹地區":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行新竹);
                        break;
                    case "桃園市":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行桃園);
                        break;
                    case "苗栗縣":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行苗栗);
                        break;
                    case "臺中市":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行台中);
                        break;
                    case "彰化縣":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行彰化);
                        break;
                    case "南投縣":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行南投);
                        break;
                    case "嘉義地區":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行嘉義);
                        break;
                    case "雲林縣":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行雲林);
                        break;
                    case "臺南市":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行台南);
                        break;
                    case "高雄市":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行高雄);
                        break;
                    case "屏東縣":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行屏東);
                        break;
                    case "澎湖縣":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行澎湖);
                        break;
                    case "臺東縣":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行台東);
                        break;
                    case "花蓮縣":
                        bankBranch = getResources().getStringArray(R.array.第一商業銀行花蓮);
                        break;
                }
                break;
            case "012 台北富邦商業銀行":
                switch (bankArea){
                    case "臺北市":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行新北);
                        break;
                    case "基隆市":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行基隆);
                        break;
                    case "新北市":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行新北);
                        break;
                    case "宜蘭縣":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行宜蘭);
                        break;
                    case "新竹地區":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行新竹);
                        break;
                    case "桃園市":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行桃園);
                        break;
                    case "苗栗縣":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行苗栗);
                        break;
                    case "臺中市":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行台中);
                        break;
                    case "彰化縣":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行彰化);
                        break;
                    case "南投縣":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行南投);
                        break;
                    case "嘉義地區":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行嘉義);
                        break;
                    case "雲林縣":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行雲林);
                        break;
                    case "臺南市":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行台南);
                        break;
                    case "高雄市":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行高雄);
                        break;
                    case "屏東縣":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行屏東);
                        break;
                    case "臺東縣":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行台東);
                        break;
                    case "花蓮縣":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行花蓮);
                        break;
                    case "澎湖縣":
                        bankBranch = getResources().getStringArray(R.array.台北富邦商業銀行澎湖);
                }
                break;
            case "013 國泰世華商業銀行":
                switch (bankArea){
                    case "臺北市":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行台北);
                        break;
                    case "基隆市":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行基隆);
                        break;
                    case "新北市":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行新北);
                        break;
                    case "宜蘭縣":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行宜蘭);
                        break;
                    case "新竹地區":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行新竹);
                        break;
                    case "桃園市":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行桃園);
                        break;
                    case "苗栗縣":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行苗栗);
                        break;
                    case "臺中市":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行台中);
                        break;
                    case "彰化縣":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行彰化);
                        break;
                    case "南投縣":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行南投);
                        break;
                    case "嘉義地區":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行嘉義);
                        break;
                    case "雲林縣":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行雲林);
                        break;
                    case "臺南市":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行台南);
                        break;
                    case "高雄市":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行高雄);
                        break;
                    case "屏東縣":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行屏東);
                        break;
                    case "澎湖縣":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行澎湖);
                        break;
                    case "臺東縣":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行台東);
                        break;
                    case "花蓮縣":
                        bankBranch = getResources().getStringArray(R.array.國泰世華商業銀行花蓮);
                        break;
                }
                break;
            case "808 玉山商業銀行":
                switch (bankArea){
                    case "臺北市":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行台北);
                        break;
                    case "基隆市":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行基隆);
                        break;
                    case "新北市":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行新北);
                        break;
                    case "宜蘭縣":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行宜蘭);
                        break;
                    case "新竹地區":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行新竹);
                        break;
                    case "桃園市":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行桃園);
                        break;
                    case "苗栗縣":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行苗栗);
                        break;
                    case "臺中市":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行台中);
                        break;
                    case "彰化縣":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行彰化);
                        break;
                    case "南投縣":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行南投);
                        break;
                    case "嘉義地區":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行嘉義);
                        break;
                    case "雲林縣":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行雲林);
                        break;
                    case "臺南市":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行台南);
                        break;
                    case "高雄市":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行高雄);
                        break;
                    case "屏東縣":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行屏東);
                        break;
                    case "澎湖縣":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行澎湖);
                        break;
                    case "臺東縣":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行台東);
                        break;
                    case "花蓮縣":
                        bankBranch = getResources().getStringArray(R.array.玉山商業銀行花蓮);
                        break;
                }
                break;
            case "822 中國信託商業銀行":
                switch (bankArea){
                    case "臺北市":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行台北);
                        break;
                    case "基隆市":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行基隆);
                        break;
                    case "新北市":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行新北);
                        break;
                    case "宜蘭縣":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行宜蘭);
                        break;
                    case "新竹地區":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行新竹);
                        break;
                    case "桃園市":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行桃園);
                        break;
                    case "苗栗縣":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行苗栗);
                        break;
                    case "臺中市":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行台中);
                        break;
                    case "彰化縣":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行彰化);
                        break;
                    case "南投縣":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行南投);
                        break;
                    case "嘉義地區":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行嘉義);
                        break;
                    case "雲林縣":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行雲林);
                        break;
                    case "臺南市":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行台南);
                        break;
                    case "高雄市":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行高雄);
                        break;
                    case "屏東縣":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行屏東);
                        break;
                    case "澎湖縣":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行澎湖);
                        break;
                    case "臺東縣":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行台東);
                        break;
                    case "花蓮縣":
                        bankBranch = getResources().getStringArray(R.array.中國信託商業銀行花蓮);
                        break;
                }
                break;

        }
        List<Map<String,String>> branchList = new ArrayList<>();
        for(int i=0; i<bankBranch.length;i++){
            Map<String,String> data = new HashMap<>();
            data.put("bankBranch",bankBranch[i]);
            branchList.add(data);
        }
        SimpleAdapter simAdapter = new SimpleAdapter(ChooseBankBranchActivity.this,branchList,
                R.layout.basic_listview_items,
                new String[]{"bankBranch"},
                new int[]{R.id.textView_basiclistView});
        ChooseBankBranchListView.setAdapter(simAdapter);
        ChooseBankBranchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,String > item =(Map<String, String>) parent.getItemAtPosition(position);
                String bankBranchName = (String) item.get("bankBranch");
                Log.d("main","bankbranch = "+bankBranchName);
                SharedPreferences sp = getSharedPreferences("sellerDetail",MODE_PRIVATE);
                sp.edit().putString("bankBranch",bankBranchName).commit();
                Intent intent = new Intent(ChooseBankBranchActivity.this,BankAccountActivity.class);
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