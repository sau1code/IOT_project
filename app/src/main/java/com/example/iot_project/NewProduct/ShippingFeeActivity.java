package com.example.iot_project.NewProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;

import android.app.Dialog;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_project.R;

import java.util.List;

public class ShippingFeeActivity extends AppCompatActivity {


    private EditText editTextNumber_productWidth;
    private EditText editTextNumber_productLength;
    private EditText editTextNumber_productHeight;
    private Switch switch_postOffice;
    private Switch switch_blackCat;
    private Switch switch_seven;
    private Switch switch_familyMart;
    private Button buttonFinishSettingShippingFee;
    private int shippingFlag_postOffice;
    private int shippingFlag_blackCat;
    private int shippingFlag_seven;
    private int shippingFlag_familyMart;
    private int productHeight;
    private int productLength;
    private int productWidth;
    private int shippingFee_postoffice,shippingFee_seven,shippingFee_familyMart,shippingFee_blackCat;
    private TextView textView_shippingDetail;
    private ImageButton imageButtonShippingFeeDetailDlg_Cancel;
    private TextView textViewSevenFee,textViewFamilyMartFee,textViewPostOfficeFee,textViewBlackCatFee;
    private TextView textViewSeven;
    private TextView textViewFamilyMart;
    private TextView textViewBlackCat;
    private TextView textViewPostOffice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_fee);
        setWindow();

        textViewSeven = (TextView) findViewById(R.id.textView_seven);
        textViewFamilyMart = (TextView) findViewById(R.id.textView_familyMart);
        textViewBlackCat = (TextView)findViewById(R.id.textView_blackCat);
        textViewPostOffice = (TextView)findViewById(R.id.textView_postOffice);
        textViewSeven.setText("不適用");
        textViewFamilyMart.setText("不適用");
        textViewBlackCat.setText("不適用");
        textViewPostOffice.setText("不適用");

        textViewSevenFee = (TextView)findViewById(R.id.textView_sevenFee);
        textViewFamilyMartFee = (TextView)findViewById(R.id.textView_familyMartFee);
        textViewPostOfficeFee = (TextView)findViewById(R.id.textView_postOfficeFee);
        textViewBlackCatFee = (TextView)findViewById(R.id.textView_blackCatFee);
        textViewSevenFee.setText("");
        textViewFamilyMartFee.setText("");
        textViewPostOfficeFee.setText("");
        textViewBlackCatFee.setText("");

        editTextNumber_productWidth = (EditText)findViewById(R.id.editTextNumber_productWidth);
        editTextNumber_productLength = (EditText)findViewById(R.id.editTextNumberproductLength);
        editTextNumber_productHeight = (EditText)findViewById(R.id.editTextNumberHeight);

        switch_postOffice = (Switch)findViewById(R.id.switch_postOffice);
        switch_blackCat = (Switch)findViewById(R.id.switch_blackCat);
        switch_seven = (Switch)findViewById(R.id.switch_seven);
        switch_familyMart = (Switch)findViewById(R.id.switch_familyMart);
        switch_familyMart.setClickable(false);
        switch_seven.setClickable(false);
        switch_postOffice.setClickable(false);
        switch_blackCat.setClickable(false);;
        //------------------------------------------------------------------------------------------
        SharedPreferences sp = getSharedPreferences("newProduct",MODE_PRIVATE);
        shippingFlag_postOffice = sp.getInt("shippingFlag_postOffice",0);
        shippingFlag_familyMart = sp.getInt("shippingFlag_familyMart",0);
        shippingFlag_seven = sp.getInt("shippingFlag_seven",0);
        shippingFlag_blackCat = sp.getInt("shippingFlag_blackCat",0);
        if(shippingFlag_postOffice==0){
            switch_postOffice.setChecked(false);
        }
        else{
            switch_postOffice.setChecked(true);
        }

        if(shippingFlag_familyMart==0){
            switch_familyMart.setChecked(false);
        }else{
            switch_familyMart.setChecked(true);
        }

        if(shippingFlag_seven==0){
            switch_seven.setChecked(false);
        }else{
            switch_seven.setChecked(true);
        }

        if(shippingFlag_blackCat==0){
            switch_blackCat.setChecked(false);
        }else{
            switch_blackCat.setChecked(true);
        }
        //------------------------------------------------------------------------------------------
        productWidth = sp.getInt("productWidth",0);
        productLength = sp.getInt("productLength",0);
        productHeight = sp.getInt("productHeight",0);
        //------------------------------------------------------------------------------------------
        editTextNumber_productHeight.setText(String.valueOf(productHeight));
        editTextNumber_productLength.setText(String.valueOf(productLength));
        editTextNumber_productWidth.setText(String.valueOf(productWidth));
        //------------------------------------------------------------------------------------------
        if (editTextNumber_productHeight.length()!=0 && editTextNumber_productWidth.length()!=0 && editTextNumber_productLength.length()!=0){
            if(Integer.valueOf(editTextNumber_productWidth.getText().toString()) > 0 &&
                    Integer.valueOf(editTextNumber_productHeight.getText().toString())>0 &&
                    Integer.valueOf(editTextNumber_productLength.getText().toString())>0) {
                if (productHeight > 45 || productLength > 45 || productWidth > 45) {
                    switch_familyMart.setChecked(false);
                    switch_seven.setChecked(false);

                    switch_familyMart.setClickable(false);
                    switch_seven.setClickable(false);

                    textViewFamilyMartFee.setText("");
                    textViewSevenFee.setText("");

                    textViewSeven.setText("不適用");
                    textViewFamilyMart.setText("不適用");
                    if (productWidth + productHeight + productLength <= 60) {
                        textViewPostOfficeFee.setText("130");
                        textViewBlackCatFee.setText("130");

                        textViewPostOffice.setText("NT$ ");
                        textViewBlackCat.setText("NT$ ");

                        switch_postOffice.setClickable(true);
                        switch_blackCat.setClickable(true);

                        switch_postOffice.setChecked(true);
                        switch_blackCat.setChecked(true);

                    } else if (productWidth + productHeight + productLength <= 90) {
                        textViewPostOffice.setText("NT$ ");
                        textViewBlackCat.setText("NT$ ");

                        textViewPostOfficeFee.setText("170");
                        textViewBlackCatFee.setText("170");

                        switch_postOffice.setClickable(true);
                        switch_blackCat.setClickable(true);

                        switch_postOffice.setChecked(true);
                        switch_blackCat.setChecked(true);
                    } else if (productWidth + productHeight + productLength <= 120) {
                        textViewPostOffice.setText("NT$ ");
                        textViewBlackCat.setText("NT$ ");

                        textViewPostOfficeFee.setText("210");
                        textViewBlackCatFee.setText("210");

                        switch_postOffice.setClickable(true);
                        switch_blackCat.setClickable(true);

                        switch_postOffice.setChecked(true);
                        switch_blackCat.setChecked(true);

                    } else if (productWidth + productHeight + productLength <= 150) {
                        textViewPostOffice.setText("NT$ ");
                        textViewBlackCat.setText("NT$ ");

                        textViewPostOfficeFee.setText("250");
                        textViewBlackCatFee.setText("250");

                        switch_postOffice.setClickable(true);
                        switch_blackCat.setClickable(true);
                        switch_postOffice.setChecked(true);
                        switch_blackCat.setChecked(true);
                    } else {
                        textViewSeven.setText("不適用");
                        textViewFamilyMart.setText("不適用");

                        textViewFamilyMartFee.setText("");
                        textViewSevenFee.setText("");

                        switch_familyMart.setChecked(false);
                        switch_seven.setChecked(false);

                        switch_familyMart.setClickable(false);
                        switch_seven.setClickable(false);
                        textViewPostOffice.setText("不適用");
                        textViewBlackCat.setText("不適用");

                        switch_postOffice.setChecked(false);
                        switch_blackCat.setChecked(false);

                        switch_postOffice.setClickable(false);
                        switch_blackCat.setClickable(false);

                        textViewPostOfficeFee.setText("");
                        textViewBlackCatFee.setText("");
                    }

                } else if (productWidth + productHeight + productLength <= 105) {
                    textViewSevenFee.setText("60");
                    textViewFamilyMartFee.setText("60");

                    textViewSeven.setText("NT$ ");
                    textViewFamilyMart.setText("NT$ ");

                    switch_familyMart.setClickable(true);
                    switch_seven.setClickable(true);

                    switch_familyMart.setChecked(true);
                    switch_seven.setChecked(true);
                    if (productWidth + productHeight + productLength <= 60) {
                        textViewPostOfficeFee.setText("130");
                        textViewBlackCatFee.setText("130");

                        textViewPostOffice.setText("NT$ ");
                        textViewBlackCat.setText("NT$ ");

                        switch_postOffice.setClickable(true);
                        switch_blackCat.setClickable(true);

                        switch_postOffice.setChecked(true);
                        switch_blackCat.setChecked(true);

                    } else if (productWidth + productHeight + productLength <= 90) {
                        textViewPostOffice.setText("NT$ ");
                        textViewBlackCat.setText("NT$ ");

                        textViewPostOfficeFee.setText("170");
                        textViewBlackCatFee.setText("170");

                        switch_postOffice.setClickable(true);
                        switch_blackCat.setClickable(true);

                        switch_postOffice.setChecked(true);
                        switch_blackCat.setChecked(true);
                    } else {
                        textViewPostOffice.setText("NT$ ");
                        textViewBlackCat.setText("NT$ ");

                        textViewPostOfficeFee.setText("210");
                        textViewBlackCatFee.setText("210");

                        switch_postOffice.setClickable(true);
                        switch_blackCat.setClickable(true);

                        switch_postOffice.setChecked(true);
                        switch_blackCat.setChecked(true);
                    }
                } else {
                    textViewSeven.setText("不適用");
                    textViewFamilyMart.setText("不適用");
                    textViewFamilyMartFee.setText("");
                    textViewSevenFee.setText("");
                    switch_familyMart.setChecked(false);
                    switch_seven.setChecked(false);
                    switch_familyMart.setClickable(false);
                    switch_seven.setClickable(false);
                    if (productWidth + productHeight + productLength <= 120) {
                        textViewPostOffice.setText("NT$ ");
                        textViewBlackCat.setText("NT$ ");

                        textViewPostOfficeFee.setText("210");
                        textViewBlackCatFee.setText("210");

                        switch_postOffice.setClickable(true);
                        switch_blackCat.setClickable(true);

                        switch_postOffice.setChecked(true);
                        switch_blackCat.setChecked(true);

                    } else if (productWidth + productHeight + productLength <= 150) {
                        textViewPostOffice.setText("NT$ ");
                        textViewBlackCat.setText("NT$ ");

                        textViewPostOfficeFee.setText("250");
                        textViewBlackCatFee.setText("250");

                        switch_postOffice.setClickable(true);
                        switch_blackCat.setClickable(true);
                        switch_postOffice.setChecked(true);
                        switch_blackCat.setChecked(true);

                    } else {
                        switch_postOffice.setChecked(false);
                        switch_blackCat.setChecked(false);
                        switch_postOffice.setClickable(false);
                        switch_blackCat.setClickable(false);
                        textViewPostOffice.setText("不適用");
                        textViewBlackCat.setText("不適用");
                        textViewPostOfficeFee.setText("");
                        textViewBlackCatFee.setText("");
                    }
                }
            } else {
                switch_postOffice.setChecked(false);
                switch_blackCat.setChecked(false);
                switch_postOffice.setClickable(false);
                switch_blackCat.setClickable(false);
                textViewPostOffice.setText("不適用");
                textViewBlackCat.setText("不適用");
                textViewPostOfficeFee.setText("");
                textViewBlackCatFee.setText("");
            }
        }else {
            switch_postOffice.setChecked(false);
            switch_blackCat.setChecked(false);
            switch_postOffice.setClickable(false);
            switch_blackCat.setClickable(false);
            textViewPostOffice.setText("不適用");
            textViewBlackCat.setText("不適用");
            textViewPostOfficeFee.setText("");
            textViewBlackCatFee.setText("");
        }
        //------------------------------------------------------------------------------------------
        editTextNumber_productWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0 &&
                        editTextNumber_productHeight.length()!=0 &&
                        editTextNumber_productLength.length()!=0)
                {
                    productWidth = Integer.valueOf(s.toString());
                    Log.d("main","productLength = "+productLength);
                    Log.d("main","productHeight = "+productHeight);
                    Log.d("main","productWidth = "+productWidth);
                    if(Integer.valueOf(s.toString()) > 0 &&
                            Integer.valueOf(editTextNumber_productHeight.getText().toString())>0 &&
                            Integer.valueOf(editTextNumber_productLength.getText().toString())>0)
                    {
                        if(productHeight > 45 || productLength >45 || productWidth >45) {
                            switch_familyMart.setChecked(false);
                            switch_seven.setChecked(false);
                            switch_familyMart.setClickable(false);
                            switch_seven.setClickable(false);
                            textViewFamilyMartFee.setText("");
                            textViewSevenFee.setText("");

                            textViewSeven.setText("不適用");
                            textViewFamilyMart.setText("不適用");
                            if (productWidth + productHeight + productLength <= 60) {
                                textViewPostOfficeFee.setText("130");
                                textViewBlackCatFee.setText("130");
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            } else if (productWidth + productHeight + productLength <= 90) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                textViewPostOfficeFee.setText("170");
                                textViewBlackCatFee.setText("170");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);
                            } else if (productWidth + productHeight + productLength <= 120) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                textViewPostOfficeFee.setText("210");
                                textViewBlackCatFee.setText("210");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            } else if (productWidth + productHeight + productLength <= 150) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                textViewPostOfficeFee.setText("250");
                                textViewBlackCatFee.setText("250");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);
                            } else {
                                textViewSeven.setText("不適用");
                                textViewFamilyMart.setText("不適用");
                                textViewFamilyMartFee.setText("");
                                textViewSevenFee.setText("");
                                switch_familyMart.setChecked(false);
                                switch_seven.setChecked(false);
                                switch_familyMart.setClickable(false);
                                switch_seven.setClickable(false);
                                textViewPostOffice.setText("不適用");
                                textViewBlackCat.setText("不適用");
                                switch_postOffice.setChecked(false);
                                switch_blackCat.setChecked(false);
                                switch_postOffice.setClickable(false);
                                switch_blackCat.setClickable(false);
                                textViewPostOfficeFee.setText("");
                                textViewBlackCatFee.setText("");
                            }

                        }else if(productWidth + productHeight + productLength <= 105){
                            textViewSevenFee.setText("60");
                            textViewFamilyMartFee.setText("60");
                            textViewSeven.setText("NT$ ");
                            textViewFamilyMart.setText("NT$ ");
                            switch_familyMart.setClickable(true);
                            switch_seven.setClickable(true);
                            switch_familyMart.setChecked(true);
                            switch_seven.setChecked(true);
                            if (productWidth + productHeight + productLength <= 60) {
                                textViewPostOfficeFee.setText("130");
                                textViewBlackCatFee.setText("130");
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            }else if (productWidth + productHeight + productLength <= 90) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                textViewPostOfficeFee.setText("170");
                                textViewBlackCatFee.setText("170");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);
                            }else{
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                textViewPostOfficeFee.setText("210");
                                textViewBlackCatFee.setText("210");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);
                            }
                        }else{
                            textViewSeven.setText("不適用");
                            textViewFamilyMart.setText("不適用");
                            textViewFamilyMartFee.setText("");
                            textViewSevenFee.setText("");
                            switch_familyMart.setChecked(false);
                            switch_seven.setChecked(false);
                            switch_familyMart.setClickable(false);
                            switch_seven.setClickable(false);
                            if (productWidth + productHeight + productLength <= 120) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                textViewPostOfficeFee.setText("210");
                                textViewBlackCatFee.setText("210");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            } else if (productWidth + productHeight + productLength <= 150) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                textViewPostOfficeFee.setText("250");
                                textViewBlackCatFee.setText("250");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            }
                            else {

                                textViewPostOffice.setText("不適用");
                                textViewBlackCat.setText("不適用");
                                switch_postOffice.setChecked(false);
                                switch_blackCat.setChecked(false);
                                switch_postOffice.setClickable(false);
                                switch_blackCat.setClickable(false);
                                textViewPostOfficeFee.setText("");
                                textViewBlackCatFee.setText("");
                            }
                        }
                    }else{
                        switch_familyMart.setChecked(false);
                        switch_seven.setChecked(false);
                        switch_familyMart.setClickable(false);
                        textViewFamilyMartFee.setText("");
                        textViewSevenFee.setText("");
                        textViewSeven.setText("不適用");
                        textViewFamilyMart.setText("不適用");
                        switch_seven.setClickable(false);
                        switch_postOffice.setClickable(false);
                        switch_blackCat.setClickable(false);
                        switch_postOffice.setChecked(false);
                        switch_blackCat.setChecked(false);
                        textViewPostOffice.setText("不適用");
                        textViewBlackCat.setText("不適用");
                        textViewPostOfficeFee.setText("");
                        textViewBlackCatFee.setText("");
                    }

                }else{
                    switch_familyMart.setChecked(false);
                    switch_seven.setChecked(false);
                    switch_familyMart.setClickable(false);
                    textViewFamilyMartFee.setText("");
                    textViewSevenFee.setText("");
                    textViewSeven.setText("不適用");
                    textViewFamilyMart.setText("不適用");
                    switch_seven.setClickable(false);
                    switch_postOffice.setClickable(false);
                    switch_blackCat.setClickable(false);
                    switch_postOffice.setChecked(false);
                    switch_blackCat.setChecked(false);
                    textViewPostOffice.setText("不適用");
                    textViewBlackCat.setText("不適用");
                    textViewPostOfficeFee.setText("");
                    textViewBlackCatFee.setText("");
                }
            }
        });

        editTextNumber_productLength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()!=0 && Integer.valueOf(s.toString())>0 &&
                        editTextNumber_productHeight.length()!=0 &&
                        editTextNumber_productWidth.length()!=0) {
                    productLength = Integer.valueOf(s.toString());
                    Log.d("main","productLength = "+productLength);
                    Log.d("main","productHeight = "+productHeight);
                    Log.d("main","productWidth = "+productWidth);
                    if(Integer.valueOf(s.toString()) > 0 &&
                            Integer.valueOf(editTextNumber_productHeight.getText().toString())>0 &&
                            Integer.valueOf(editTextNumber_productWidth.getText().toString())>0)
                    {
                        if(productHeight > 45 || productLength >45 || productWidth >45) {
                            switch_familyMart.setChecked(false);
                            switch_seven.setChecked(false);

                            switch_familyMart.setClickable(false);
                            switch_seven.setClickable(false);

                            textViewFamilyMartFee.setText("");
                            textViewSevenFee.setText("");

                            textViewSeven.setText("不適用");
                            textViewFamilyMart.setText("不適用");
                            if (productWidth + productHeight + productLength <= 60) {
                                textViewPostOfficeFee.setText("130");
                                textViewBlackCatFee.setText("130");

                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");

                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);

                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            }else if (productWidth + productHeight + productLength <= 90) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");

                                textViewPostOfficeFee.setText("170");
                                textViewBlackCatFee.setText("170");

                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);

                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);
                            }else if (productWidth + productHeight + productLength <= 120) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");

                                textViewPostOfficeFee.setText("210");
                                textViewBlackCatFee.setText("210");

                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);

                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            }else if (productWidth + productHeight + productLength <= 150) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");

                                textViewPostOfficeFee.setText("250");
                                textViewBlackCatFee.setText("250");

                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);
                            }else {
                                textViewSeven.setText("不適用");
                                textViewFamilyMart.setText("不適用");

                                textViewFamilyMartFee.setText("");
                                textViewSevenFee.setText("");

                                switch_familyMart.setChecked(false);
                                switch_seven.setChecked(false);

                                switch_familyMart.setClickable(false);
                                switch_seven.setClickable(false);
                                textViewPostOffice.setText("不適用");
                                textViewBlackCat.setText("不適用");

                                switch_postOffice.setChecked(false);
                                switch_blackCat.setChecked(false);

                                switch_postOffice.setClickable(false);
                                switch_blackCat.setClickable(false);

                                textViewPostOfficeFee.setText("");
                                textViewBlackCatFee.setText("");
                            }
                        }else if(productWidth + productHeight + productLength <= 105){
                            textViewSevenFee.setText("60");
                            textViewFamilyMartFee.setText("60");

                            textViewSeven.setText("NT$ ");
                            textViewFamilyMart.setText("NT$ ");

                            switch_familyMart.setClickable(true);
                            switch_seven.setClickable(true);

                            switch_familyMart.setChecked(true);
                            switch_seven.setChecked(true);
                            if (productWidth + productHeight + productLength <= 60) {
                                textViewPostOfficeFee.setText("130");
                                textViewBlackCatFee.setText("130");

                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");

                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);

                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            }else if (productWidth + productHeight + productLength <= 90) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");

                                textViewPostOfficeFee.setText("170");
                                textViewBlackCatFee.setText("170");

                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);

                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);
                            }else{
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");

                                textViewPostOfficeFee.setText("210");
                                textViewBlackCatFee.setText("210");

                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);

                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);
                            }
                        }else{
                            textViewSeven.setText("不適用");
                            textViewFamilyMart.setText("不適用");

                            textViewFamilyMartFee.setText("");
                            textViewSevenFee.setText("");

                            switch_familyMart.setChecked(false);
                            switch_seven.setChecked(false);

                            switch_familyMart.setClickable(false);
                            switch_seven.setClickable(false);
                            if (productWidth + productHeight + productLength <= 120) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");

                                textViewPostOfficeFee.setText("210");
                                textViewBlackCatFee.setText("210");

                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);

                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            } else if (productWidth + productHeight + productLength <= 150) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");

                                textViewPostOfficeFee.setText("250");
                                textViewBlackCatFee.setText("250");

                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            }
                            else {

                                textViewPostOffice.setText("不適用");
                                textViewBlackCat.setText("不適用");
                                switch_postOffice.setChecked(false);
                                switch_blackCat.setChecked(false);

                                switch_postOffice.setClickable(false);
                                switch_blackCat.setClickable(false);

                                textViewPostOfficeFee.setText("");
                                textViewBlackCatFee.setText("");
                            }
                        }
                    }else{
                        switch_familyMart.setChecked(false);
                        switch_seven.setChecked(false);
                        switch_familyMart.setClickable(false);
                        textViewFamilyMartFee.setText("");
                        textViewSevenFee.setText("");
                        textViewSeven.setText("不適用");
                        textViewFamilyMart.setText("不適用");
                        switch_seven.setClickable(false);
                        switch_postOffice.setClickable(false);
                        switch_blackCat.setClickable(false);
                        switch_postOffice.setChecked(false);
                        switch_blackCat.setChecked(false);
                        textViewPostOffice.setText("不適用");
                        textViewBlackCat.setText("不適用");
                        textViewPostOfficeFee.setText("");
                        textViewBlackCatFee.setText("");
                    }
                }else{
                    switch_familyMart.setChecked(false);
                    switch_seven.setChecked(false);
                    switch_familyMart.setClickable(false);
                    textViewFamilyMartFee.setText("");
                    textViewSevenFee.setText("");
                    textViewSeven.setText("不適用");
                    textViewFamilyMart.setText("不適用");
                    switch_seven.setClickable(false);
                    switch_postOffice.setClickable(false);
                    switch_blackCat.setClickable(false);
                    switch_postOffice.setChecked(false);
                    switch_blackCat.setChecked(false);
                    textViewPostOffice.setText("不適用");
                    textViewBlackCat.setText("不適用");
                    textViewPostOfficeFee.setText("");
                    textViewBlackCatFee.setText("");
                }
            }
        });

        editTextNumber_productHeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()!=0 && Integer.valueOf(s.toString())>0 &&
                        editTextNumber_productWidth.length()!=0 &&
                        editTextNumber_productLength.length()!=0)
                {
                    productHeight = Integer.valueOf(s.toString());
                    Log.d("main","productLength = "+productLength);
                    Log.d("main","productHeight = "+productHeight);
                    Log.d("main","productWidth = "+productWidth);
                    if(Integer.valueOf(s.toString()) > 0 &&
                            Integer.valueOf(editTextNumber_productLength.getText().toString())>0 &&
                            Integer.valueOf(editTextNumber_productWidth.getText().toString())>0) {
                        if (productHeight > 45 || productLength > 45 || productWidth > 45) {
                            switch_familyMart.setChecked(false);
                            switch_seven.setChecked(false);
                            switch_familyMart.setClickable(false);
                            switch_seven.setClickable(false);
                            textViewFamilyMartFee.setText("");
                            textViewSevenFee.setText("");
                            textViewSeven.setText("不適用");
                            textViewFamilyMart.setText("不適用");
                            if (productWidth + productHeight + productLength <= 60) {
                                textViewPostOfficeFee.setText("130");
                                textViewBlackCatFee.setText("130");
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            } else if (productWidth + productHeight + productLength <= 90) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                textViewPostOfficeFee.setText("170");
                                textViewBlackCatFee.setText("170");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);
                            } else if (productWidth + productHeight + productLength <= 120) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                textViewPostOfficeFee.setText("210");
                                textViewBlackCatFee.setText("210");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            } else if (productWidth + productHeight + productLength <= 150) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                textViewPostOfficeFee.setText("250");
                                textViewBlackCatFee.setText("250");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);
                            } else {
                                textViewSeven.setText("不適用");
                                textViewFamilyMart.setText("不適用");
                                textViewFamilyMartFee.setText("");
                                textViewSevenFee.setText("");
                                switch_familyMart.setChecked(false);
                                switch_seven.setChecked(false);
                                switch_familyMart.setClickable(false);
                                switch_seven.setClickable(false);
                                textViewPostOffice.setText("不適用");
                                textViewBlackCat.setText("不適用");
                                switch_postOffice.setChecked(false);
                                switch_blackCat.setChecked(false);
                                switch_postOffice.setClickable(false);
                                switch_blackCat.setClickable(false);
                                textViewPostOfficeFee.setText("");
                                textViewBlackCatFee.setText("");
                            }

                        } else if (productWidth + productHeight + productLength <= 105) {
                            textViewSevenFee.setText("60");
                            textViewFamilyMartFee.setText("60");
                            textViewSeven.setText("NT$ ");
                            textViewFamilyMart.setText("NT$ ");
                            switch_familyMart.setClickable(true);
                            switch_seven.setClickable(true);
                            switch_familyMart.setChecked(true);
                            switch_seven.setChecked(true);
                            if (productWidth + productHeight + productLength <= 60) {
                                textViewPostOfficeFee.setText("130");
                                textViewBlackCatFee.setText("130");
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            } else if (productWidth + productHeight + productLength <= 90) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                textViewPostOfficeFee.setText("170");
                                textViewBlackCatFee.setText("170");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);
                            } else {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                textViewPostOfficeFee.setText("210");
                                textViewBlackCatFee.setText("210");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);
                            }
                        } else {
                            textViewSeven.setText("不適用");
                            textViewFamilyMart.setText("不適用");
                            textViewFamilyMartFee.setText("");
                            textViewSevenFee.setText("");
                            switch_familyMart.setChecked(false);
                            switch_seven.setChecked(false);
                            switch_familyMart.setClickable(false);
                            switch_seven.setClickable(false);
                            if (productWidth + productHeight + productLength <= 120) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                textViewPostOfficeFee.setText("210");
                                textViewBlackCatFee.setText("210");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            } else if (productWidth + productHeight + productLength <= 150) {
                                textViewPostOffice.setText("NT$ ");
                                textViewBlackCat.setText("NT$ ");
                                textViewPostOfficeFee.setText("250");
                                textViewBlackCatFee.setText("250");
                                switch_postOffice.setClickable(true);
                                switch_blackCat.setClickable(true);
                                switch_postOffice.setChecked(true);
                                switch_blackCat.setChecked(true);

                            } else {

                                textViewPostOffice.setText("不適用");
                                textViewBlackCat.setText("不適用");
                                switch_postOffice.setChecked(false);
                                switch_blackCat.setChecked(false);
                                switch_postOffice.setClickable(false);
                                switch_blackCat.setClickable(false);

                                textViewPostOfficeFee.setText("");
                                textViewBlackCatFee.setText("");
                            }
                        }
                    }else{
                        switch_familyMart.setChecked(false);
                        switch_seven.setChecked(false);
                        switch_familyMart.setClickable(false);
                        textViewFamilyMartFee.setText("");
                        textViewSevenFee.setText("");
                        textViewSeven.setText("不適用");
                        textViewFamilyMart.setText("不適用");
                        switch_seven.setClickable(false);
                        switch_postOffice.setClickable(false);
                        switch_blackCat.setClickable(false);
                        switch_postOffice.setChecked(false);
                        switch_blackCat.setChecked(false);
                        textViewPostOffice.setText("不適用");
                        textViewBlackCat.setText("不適用");
                        textViewPostOfficeFee.setText("");
                        textViewBlackCatFee.setText("");
                    }
                }else{
                    switch_familyMart.setChecked(false);
                    switch_seven.setChecked(false);
                    switch_familyMart.setClickable(false);
                    textViewFamilyMartFee.setText("");
                    textViewSevenFee.setText("");
                    textViewSeven.setText("不適用");
                    textViewFamilyMart.setText("不適用");
                    switch_seven.setClickable(false);
                    switch_postOffice.setClickable(false);
                    switch_blackCat.setClickable(false);
                    switch_postOffice.setChecked(false);
                    switch_blackCat.setChecked(false);
                    textViewPostOffice.setText("不適用");
                    textViewBlackCat.setText("不適用");
                    textViewPostOfficeFee.setText("");
                    textViewBlackCatFee.setText("");
                }
            }
        });
        //------------------------------------------------------------------------------------------
        editTextNumber_productWidth.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        editTextNumber_productLength.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        editTextNumber_productHeight.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        //------------------------------------------------------------------------------------------
        switch_postOffice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    shippingFlag_postOffice = 1;

                }else{
                    shippingFlag_postOffice = 0;
                }
            }
        });

        switch_blackCat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    shippingFlag_blackCat = 1;
                }else{
                    shippingFlag_blackCat = 0;
                }
            }
        });

        switch_seven.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    shippingFlag_seven = 1;
                }else{
                    shippingFlag_seven = 0;
                }
            }
        });

        switch_familyMart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    shippingFlag_familyMart = 1;
                }else{
                    shippingFlag_familyMart = 0;
                }
            }
        });

        //-----------------------------------------------------------------------------------------
        textView_shippingDetail = (TextView)findViewById(R.id.textView_shippingDetail);
        textView_shippingDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog shippingDetailDlg = new Dialog(ShippingFeeActivity.this);
                shippingDetailDlg.setContentView(R.layout.shipping_detail_dialog);
                shippingDetailDlg.show();
                shippingDetailDlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                imageButtonShippingFeeDetailDlg_Cancel = (ImageButton)shippingDetailDlg.findViewById(R.id.imageButton_shippingDetailDlg_cancel);
                imageButtonShippingFeeDetailDlg_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shippingDetailDlg.dismiss();
                    }
                });
            }
        });
        //-----------------------------------------------------------------------------------------
        buttonFinishSettingShippingFee = (Button)findViewById(R.id.button_finishSetShipping);
        buttonFinishSettingShippingFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextNumber_productHeight.length()==0 || editTextNumber_productWidth.length()==0 || editTextNumber_productWidth.length()==0){
                    Toast.makeText(ShippingFeeActivity.this, "請填寫完整包裹尺寸", Toast.LENGTH_SHORT).show();
                }
                else if(shippingFlag_blackCat==0 && shippingFlag_familyMart==0 && shippingFlag_postOffice==0 && shippingFlag_seven==0){
                    Toast.makeText(ShippingFeeActivity.this, "請至少選擇一種運送方式", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(textViewFamilyMartFee.getText().toString()==""){
                        shippingFee_familyMart=0;
                    }else{
                        shippingFee_familyMart=Integer.valueOf(textViewFamilyMartFee.getText().toString());
                    }
                    if(textViewSevenFee.getText().toString()==""){
                        shippingFee_seven=0;
                    }else{
                        shippingFee_seven=Integer.valueOf(textViewSevenFee.getText().toString());
                    }
                    if(textViewPostOfficeFee.getText().toString()==""){
                        shippingFee_postoffice=0;
                    }else{
                        shippingFee_postoffice = Integer.valueOf(textViewPostOfficeFee.getText().toString());
                    }
                    if(textViewBlackCatFee.getText().toString()==""){
                        shippingFee_blackCat=0;
                    }else{
                        shippingFee_blackCat = Integer.valueOf(textViewBlackCatFee.getText().toString());
                    }
                    Log.d("main","productHeight = "+productHeight);
                    Log.d("main","productWidth = "+productWidth);
                    Log.d("main","productLength = "+productLength);
                    Log.d("main","--------------------------");
                    Log.d("main","shippingFee_postOffice = "+shippingFee_postoffice);
                    Log.d("main","shippingFee_seven = "+shippingFee_seven);
                    Log.d("main","shippingFee_familyMart = "+shippingFee_familyMart);
                    Log.d("main","shippingFee_blackCat = "+shippingFee_blackCat);
                    Log.d("main","--------------------------");
                    Log.d("main","shippingFlag_familyMart = "+shippingFlag_familyMart);
                    Log.d("main","shippingFlag_postOffice = "+shippingFlag_postOffice);
                    Log.d("main","shippingFlag_seven = "+shippingFlag_seven);
                    Log.d("main","shippingFlag_blackCat = "+shippingFlag_blackCat);

                    SharedPreferences sp = getSharedPreferences("newProduct",MODE_PRIVATE);
                    sp.edit().putInt("productHeight",productHeight)
                            .putInt("productWidth",productWidth)
                            .putInt("productLength",productLength)
                            .putInt("shippingFlag_blackCat",shippingFlag_blackCat)
                            .putInt("shippingFlag_familyMart",shippingFlag_familyMart)
                            .putInt("shippingFlag_postOffice",shippingFlag_postOffice)
                            .putInt("shippingFlag_seven",shippingFlag_seven)
                            .putInt("shippingFee_blackCat",shippingFee_blackCat)
                            .putInt("shippingFee_seven",shippingFee_seven)
                            .putInt("shippingFee_familyMart",shippingFee_familyMart)
                            .putInt("shippingFee_postOffice",shippingFee_postoffice)
                            .commit();

                    Intent intent = new Intent();
                    intent.setClass(ShippingFeeActivity.this,NewProductActivity.class);
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