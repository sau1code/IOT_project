package com.example.iot_project.register;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.iot_project.R;

public class RegisterCityFragment extends Fragment {

    private RegisterActivity registerActivity;
    private ListView listViewCommon;
    private ImageView ImageViewBack;
    private String cityName;

    public RegisterCityFragment() {}

    public static RegisterCityFragment newInstance(String param1, String param2) {
        RegisterCityFragment fragment = new RegisterCityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register_city, container, false);
        registerActivity = (RegisterActivity)getActivity();
        ImageViewBack = (ImageView)v.findViewById(R.id.ImageView_register_city_back);
        listViewCommon = (ListView)v.findViewById(R.id.ListView_common);

        ImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerActivity.onBackPressed();
            }
        });

        listViewCommon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                cityName = adapterView.getItemAtPosition(position).toString();
                registerActivity.setCityName(cityName);
                registerActivity.setDistrictName("");
                registerActivity.showDistrictFragment();
            }
        });

        return v;
    }

}