package com.example.iot_project.SellerRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_project.DBHelper;
import com.example.iot_project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SellerDetailActivity extends AppCompatActivity {


    private Button buttonSellerDetail_next;
    private TextView textViewSellerName_sellerDetail;
    private TextView textViewSellerId_sellerDetail;
    private TextView textViewSellerBirthday_sellerDetial;
    private TextView textViewSellerCounty_sellerDetail;
    private TextView textViewSellerArea_sellerDetail;
    private EditText editTextSellerAddressNumber,editTextSellerAddress;
    private String SellerAddressNumber;
    private String SellerAddress;
    private String county;
    private String sellerBirthday;
    private String sellerId;
    private String sellerName;
    private String area;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_detail);
        SharedPreferences sp = getSharedPreferences("sellerDetail",MODE_PRIVATE);
        setWindow();

        sellerName = sp.getString("sellerName",null);
        sellerId = sp.getString("sellerId",null);
        sellerBirthday = sp.getString("sellerBirthday",null);
        county = sp.getString("county","選擇");
        area = sp.getString("area","選擇");
        //------------------------------------------------------------------------------------------
        textViewSellerName_sellerDetail = (TextView)findViewById(R.id.textView_sellerDetail_name);
        textViewSellerId_sellerDetail = (TextView)findViewById(R.id.textView_sellerDetail_nationalID);
        textViewSellerBirthday_sellerDetial = (TextView)findViewById(R.id.textView_sellerDetail_birthday);

        textViewSellerId_sellerDetail.setText(sellerId);
        textViewSellerName_sellerDetail.setText(sellerName);
        textViewSellerBirthday_sellerDetial.setText(sellerBirthday);
        //------------------------------------------------------------------------------------------
        textViewSellerCounty_sellerDetail = (TextView)findViewById(R.id.textView_sellerDetail_county);
        textViewSellerCounty_sellerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putString("area","選擇").commit();

                Intent intent = new Intent(SellerDetailActivity.this,ChooseSellerCountyActivity.class);
                startActivity(intent);
            }
        });
        textViewSellerCounty_sellerDetail.setText(county+"  > ");
       //-------------------------------------------------------------------------------------------
        textViewSellerArea_sellerDetail = (TextView)findViewById(R.id.textView_sellerDetail_area);
        textViewSellerArea_sellerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(county=="選擇"){
                    Toast.makeText(SellerDetailActivity.this, "請選擇城市", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(SellerDetailActivity.this,ChooseSellerAreaActivity.class);
                    startActivity(intent);
                }

            }
        });
        textViewSellerArea_sellerDetail.setText(area+"  > ");
        //------------------------------------------------------------------------------------------
        editTextSellerAddressNumber = (EditText)findViewById(R.id.editText_sellerDetail_areaNumber);
        SellerAddressNumber = sp.getString("sellerAddressNum","");
        editTextSellerAddressNumber.setText(SellerAddressNumber);
        editTextSellerAddressNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                SellerAddressNumber = s.toString();
                sp.edit().putString("sellerAddressNum",SellerAddressNumber).commit();
            }
        });
        //------------------------------------------------------------------------------------------
        editTextSellerAddress = (EditText)findViewById(R.id.editText_sellerDetail_address);
        SellerAddress = sp.getString("sellerAddress","");
        editTextSellerAddress.setText(SellerAddress);
        editTextSellerAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                SellerAddress = s.toString();
                sp.edit().putString("sellerAddress",SellerAddress).commit();
            }
        });



        //------------------------------------------------------------------------------------------
        buttonSellerDetail_next = (Button)findViewById(R.id.button_sellerDetail_next);
        buttonSellerDetail_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextSellerAddress.length()==0||editTextSellerAddressNumber.length()==0||area=="選擇"||county=="選擇"){
                    Toast.makeText(SellerDetailActivity.this, "請輸入完整資料", Toast.LENGTH_SHORT).show();
                }
                else{
                    SellerAddress = sp.getString("sellerAddress","");
                    SellerAddressNumber = sp.getString("sellerAddressNum","");
                    county = sp.getString("county","選擇");
                    area = sp.getString("area","選擇");
                    Intent intent = new Intent(SellerDetailActivity.this,BankAccountActivity.class);
                    startActivity(intent);
                }
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