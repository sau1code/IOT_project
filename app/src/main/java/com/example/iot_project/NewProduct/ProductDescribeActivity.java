package com.example.iot_project.NewProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.iot_project.R;

public class ProductDescribeActivity extends AppCompatActivity {


    private String ProductDescribe;
    private String DescribeWordNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_describe);
        SharedPreferences sp = getSharedPreferences("newProduct",MODE_PRIVATE);
        setWindow();

        TextView textViewDescribeLength = (TextView) findViewById(R.id.textView_productDescribe_describeLength);
        EditText editTextProductDescirbe = (EditText) findViewById(R.id.editTextTextMultiLine_ProductDescribe);
        String productDescribe = sp.getString("productDescribe","");
        editTextProductDescirbe.setText(productDescribe);
        editTextProductDescirbe.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3000)});
        editTextProductDescirbe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //改變前
                //這個方法被調用，說明在s字符串中，從start位置開始的count個字符即將被長度為after的新文本所取代。在這個方法里面改變s，會報錯。
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //改變中
                //這個方法被調用，說明在s字符串中，從start位置開始的count個字符刚刚取代了長度為before的舊文本。在這個方法里面改變s，會報錯。
                DescribeWordNum = String.valueOf(s.length());
                textViewDescribeLength.setText(DescribeWordNum);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //改變後
                //這個方法被使用，那麼說明s字符串的某個地方已經被改變。
                ProductDescribe = s.toString();
                DescribeWordNum = String.valueOf(s.toString().length());


            }
        });
        //------------------------------------------------------------------------------------------
        Button buttonFinish = (Button) findViewById(R.id.button_productDescribe_finish);
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putString("productDescribe",ProductDescribe)
                        .putString("DescribeWordNum",DescribeWordNum).commit();
                Intent intent = new Intent(ProductDescribeActivity.this, NewProductActivity.class);
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