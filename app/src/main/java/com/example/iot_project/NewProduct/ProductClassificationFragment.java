package com.example.iot_project.NewProduct;

import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.iot_project.DBHelper;
import com.example.iot_project.Main.MainActivity;
import com.example.iot_project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductClassificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductClassificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner spinnerProductClass;
    private ImageButton imageButtonDeleteClass;
    private String productClassName;
    private String productType;
    private int typeIndex;
    boolean firstTime;
    public ProductClassificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductClassicificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductClassificationFragment newInstance(String param1, String param2) {
        ProductClassificationFragment fragment = new ProductClassificationFragment();
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
    public void onAttach(Context context){
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_classification, container, false);
        NewProductClassificationActivity newProductClassificationActivity = (NewProductClassificationActivity) getActivity();
        String[] productClassArray  = getResources().getStringArray(R.array.productClass);
        spinnerProductClass = (Spinner)v.findViewById(R.id.spinner_productClass);
        imageButtonDeleteClass=(ImageButton)v.findViewById(R.id.imageButton_deleteClass);

//        DBHelper dbHelper = new DBHelper(newProductClassificationActivity);
//        SQLiteDatabase productClassDatabase = dbHelper.getWritableDatabase();
//        Cursor classCursor = productClassDatabase.rawQuery( "SELECT * FROM goodsType WHERE fragType='"+mParam2+"';", null);
//        classCursor.moveToFirst();
//        if(classCursor.getCount()!=0){
//            productType = classCursor.getString(classCursor.getColumnIndexOrThrow("type"));
//            if(productType!=null){
//                for(int i=0;i<productClassArray.length;i++){
//                    if(productType.equals(productClassArray[i])){
//                        typeIndex = i;
//                    }
//                }
//            }
//
//        }
//        productClassDatabase.close();
//        dbHelper.close();
//        spinnerProductClass.setSelection(typeIndex,true);

        spinnerProductClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                productClassName = productClassArray[position];
                DBHelper dbHelper = new DBHelper(newProductClassificationActivity);
                SQLiteDatabase productClassDatabase = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("type",productClassName);
                productClassDatabase.update("goodsType",cv,"fragType ='"+mParam2+"';",null);

                Map<String, Object> typeInfoMap = new HashMap<>();
                Cursor classCursor = productClassDatabase.rawQuery(" SELECT * FROM goodsType WHERE fragType='"+mParam2+"';", null);
                classCursor.moveToFirst();
                while(!classCursor.isAfterLast()) {
                    int goodsType_id = classCursor.getInt(classCursor.getColumnIndexOrThrow("goodsType_id"));
                    String fragType = classCursor.getString(classCursor.getColumnIndexOrThrow("fragType"));
                    String type = classCursor.getString(classCursor.getColumnIndexOrThrow("type"));
                    typeInfoMap.put("goodsType_id",goodsType_id);
                    typeInfoMap.put("fragType",fragType);
                    typeInfoMap.put("type",type);
                    classCursor.moveToNext();
                    Log.d("main",typeInfoMap.toString());
                }
                productClassDatabase.close();
                dbHelper.close();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        imageButtonDeleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newProductClassificationActivity.deleteFragment(mParam2);
            }
        });
        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        NewProductClassificationActivity newProductClassificationActivity = (NewProductClassificationActivity) getActivity();
        String[] productClassArray  = getResources().getStringArray(R.array.productClass);
        DBHelper dbHelper = new DBHelper(newProductClassificationActivity);
        SQLiteDatabase productClassDatabase = dbHelper.getWritableDatabase();
        Cursor classCursor = productClassDatabase.rawQuery( "SELECT * FROM goodsType WHERE fragType='"+mParam2+"';", null);
        classCursor.moveToFirst();
        if(classCursor.getCount()!=0){
            productType = classCursor.getString(classCursor.getColumnIndexOrThrow("type"));
            if(productType!=null){
                for(int i=0;i<productClassArray.length;i++){
                    if(productType.equals(productClassArray[i])){
                        typeIndex = i;
                    }
                }
                spinnerProductClass.setSelection(typeIndex,true);
            }
        }
        productClassDatabase.close();
        dbHelper.close();

    }
}