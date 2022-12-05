package com.example.iot_project.NewProduct;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.iot_project.DBHelper;
import com.example.iot_project.R;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetPriceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetPriceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView imageViewSetNormDelete;
    private EditText editTextSetNorm_productNorm;
    private EditText editTextSetNorm_productAmount;
    private EditText editTextSetNorm_productPrice;
    private String productNorm;
    private int productNormPrice;
    private Integer productNormAmount;



    public SetPriceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetPriceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetPriceFragment newInstance(String param1, String param2) {
        SetPriceFragment fragment = new SetPriceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_set_price, container, false);
        SetPriceActivity setPriceActivity = (SetPriceActivity) getActivity();
        editTextSetNorm_productNorm = (EditText)v.findViewById(R.id.editText_setNorm_productNorm);
        editTextSetNorm_productAmount = (EditText)v.findViewById(R.id.editText_setNorm_productAmount);
        editTextSetNorm_productPrice = (EditText)v.findViewById(R.id.editText_setNorm_productPrice);
        DBHelper dbHelper = new DBHelper(setPriceActivity);
        SQLiteDatabase setPriceDatabase = dbHelper.getWritableDatabase();
        Map<String, Object> normInfoMap = new HashMap<>();
        Cursor normCursor = setPriceDatabase.rawQuery(" SELECT * FROM goodsNorm WHERE fragNum='"+mParam2+"';", null);
        normCursor.moveToFirst();
        if(normCursor.getCount()!=0){
            productNorm = normCursor.getString(normCursor.getColumnIndexOrThrow("norm"));
            productNormPrice = normCursor.getInt(normCursor.getColumnIndexOrThrow("price"));
            productNormAmount = normCursor.getInt(normCursor.getColumnIndexOrThrow("normNum"));
            editTextSetNorm_productNorm.setText(productNorm);
            editTextSetNorm_productPrice.setText(String.valueOf(productNormPrice));
            editTextSetNorm_productAmount.setText(String.valueOf(productNormAmount));
        }
        setPriceDatabase.close();
        dbHelper.close();
        editTextSetNorm_productNorm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()!=0){
                    productNorm = s.toString();

                }else{
                    productNorm = "";
                }
                DBHelper dbHelper = new DBHelper(setPriceActivity);
                SQLiteDatabase setPriceDatabase = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("norm",productNorm);
                setPriceDatabase.update("goodsNorm",cv,"fragNum='"+mParam2+"'",null);
                setPriceDatabase.close();
                dbHelper.close();
            }
        });
        editTextSetNorm_productPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()!=0){
                    productNormPrice=Integer.valueOf(s.toString());
                }else{
                    productNormPrice=0;
                }
                DBHelper dbHelper = new DBHelper(setPriceActivity);
                SQLiteDatabase setPriceDatabase = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("price",productNormPrice);
                setPriceDatabase.update("goodsNorm",cv,"fragNum='"+mParam2+"'",null);
                setPriceDatabase.close();
                dbHelper.close();
            }
        });
        editTextSetNorm_productAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()!=0){
                    productNormAmount= Integer.valueOf(s.toString());
                }else {
                    productNormAmount = 0;

                }
                DBHelper dbHelper = new DBHelper(setPriceActivity);
                SQLiteDatabase setPriceDatabase = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("normNum",productNormAmount);
                setPriceDatabase.update("goodsNorm",cv,"fragNum='"+mParam2+"'",null);
                setPriceDatabase.close();
                dbHelper.close();

            }
        });

        imageViewSetNormDelete = (ImageView)v.findViewById(R.id.imageView_setNorm_delete);
        imageViewSetNormDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPriceActivity.deleteFragment(mParam2);
            }
        });

        return v;
    }
}