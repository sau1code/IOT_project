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

public class RegisterDistrictFragment extends Fragment {

    private RegisterActivity registerActivity;
    private ListView ListViewDistrict;
    private ImageView ImageViewBack;

    public static RegisterDistrictFragment newInstance() {
        return new RegisterDistrictFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register_district, container, false);
        registerActivity = (RegisterActivity)getActivity();

        ImageViewBack = (ImageView)v.findViewById(R.id.ImageView_register_district_back);
        ListViewDistrict = (ListView)v.findViewById(R.id.ListView_register_district);
        ListViewDistrict.setAdapter(new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1, getDistrictName(registerActivity.getCityName())));

        ImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerActivity.myOnBackPressed(true);
            }
        });

        ListViewDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String districtName = adapterView.getItemAtPosition(position).toString();
                registerActivity.setDistrictName(districtName);
                registerActivity.onBackPressed();
            }
        });
        return v;
    }

    public String[] getDistrictName(String cityName) {
        String[] area;
        switch(registerActivity.getCityName()) {
            case "臺北市":
            default:
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
        return area;
    }

}