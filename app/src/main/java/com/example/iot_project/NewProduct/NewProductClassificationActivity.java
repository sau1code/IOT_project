package com.example.iot_project.NewProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.iot_project.DBHelper;
import com.example.iot_project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class NewProductClassificationActivity extends AppCompatActivity {

    private FragmentTransaction fragTransit;
    private TextView textViewAddClass;
    private int count=0;
    private ProductClassificationFragment newProductClass;
    private Button buttonNewClassFinished;
    private FragmentManager FragManager;
    private String productName;
    private String fragType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product_classification);
        setWindow();

        SharedPreferences sp = getSharedPreferences("newProduct",MODE_PRIVATE);
        productName = sp.getString("productName","");
        FragManager = getSupportFragmentManager();

        DBHelper dbHelper = new DBHelper(NewProductClassificationActivity.this);
        SQLiteDatabase productClassDatabase = dbHelper.getWritableDatabase();
        Cursor classCursor = productClassDatabase.rawQuery( "SELECT * FROM goodsType WHERE goods_name = '"+productName+"';", null);
        if(classCursor.getCount()!=0){
            classCursor.moveToFirst();
            while(!classCursor.isAfterLast()) {
                count = classCursor.getInt(classCursor.getColumnIndexOrThrow("count"));
                fragType = classCursor.getString(classCursor.getColumnIndexOrThrow("fragType"));

                fragTransit = FragManager.beginTransaction();
                newProductClass = ProductClassificationFragment.newInstance("Add data",fragType);
                fragTransit.add(R.id.linear_layout_productClass_fragment, newProductClass,fragType);
                fragTransit.commit();
                classCursor.moveToNext();
            }
        }
        productClassDatabase.close();
        dbHelper.close();

        textViewAddClass = (TextView) findViewById(R.id.textView_newProductClass);
        textViewAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                fragTransit = FragManager.beginTransaction();
                newProductClass = ProductClassificationFragment.newInstance("Add data", "productClass" + count);
                fragTransit.add(R.id.linear_layout_productClass_fragment, newProductClass, "productClass" + count);
                fragTransit.commit();
                DBHelper dbHelper = new DBHelper(NewProductClassificationActivity.this);
                SQLiteDatabase productClassDatabase = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("goods_name",productName);
                cv.put("fragType","productClass"+count);
                cv.put("count",count);
                long id = productClassDatabase.insert("goodsType",null,cv);
                Log.d("main","productClass id = "+id);
                productClassDatabase.close();
                dbHelper.close();
            }
        });
        //------------------------------------------------------------------------------------------
        buttonNewClassFinished = (Button)findViewById(R.id.button_newClass_finished);
        buttonNewClassFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewProductClassificationActivity.this,NewProductActivity.class);
                startActivity(intent);
            }
        });

    }

    public void deleteFragment(String tag) {
        Fragment f = FragManager.findFragmentByTag(tag);
        if (f != null) {
            fragTransit = FragManager.beginTransaction();
            fragTransit.remove(f);
            fragTransit.commit();

            DBHelper dbHelper = new DBHelper(NewProductClassificationActivity.this);
            SQLiteDatabase productClassDatabase = dbHelper.getWritableDatabase();
            productClassDatabase.delete("goodsType","fragType ='"+tag+"';",null);
            productClassDatabase.close();
            dbHelper.close();
        }
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

