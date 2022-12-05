package com.example.iot_project.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.iot_project.R;
import com.example.iot_project.member.MemberOrdersFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FragmentManager fragmentMgr;
    private FragmentTransaction fragmentTrans;
    private RegisterFragment registerFragment;
    private RegisterCityFragment registerCityFragment;
    private RegisterDistrictFragment registerDistrictFragment;
    private boolean cityFlag, districtFlag, myBackInDistrict;
    private InputMethodManager kryboard;
    private String cityName, districtName;
    private Map<String, Object> fireMap;
    private long timeTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setData();
        setFragment();

    }

    private void setData() {
        cityFlag = false;
        districtFlag = false;
        cityName = "";
        districtName = "";
        getSupportActionBar().hide();                   // 隱藏ActionBar
        getWindow().setStatusBarColor(0xffffffff);      // 最上面StatusBar白色底
        getWindow().setNavigationBarColor(0xaaffffff);  // 最下面NavigationBar白色底
        getWindow().getDecorView()                      // 上面字設黑 | 下面虛擬按鈕深色
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        kryboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        kryboard.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    private void setFragment() {
        registerFragment = new RegisterFragment();
        fragmentMgr = getSupportFragmentManager();
        fragmentTrans = fragmentMgr.beginTransaction();
        fragmentTrans.add(R.id.FrameLayout_register, registerFragment, "registerFragment");
        fragmentTrans.commit();
    }

      public void MapUploadToFireBase() { // 把會員註冊資料存到firebase
///     使用 Firebase 服務
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//      取得  Firebase 資料庫 (GET網址)
        DatabaseReference dataref = database.getReference();
//      儲存會員資料: 存在 member 資料表下，以 uniqueKey 儲存對應的會員資料
//        dataref.child("user").push().child("member").setValue(fireMap); // nested structur is hard to query
        dataref.child("member").push().setValue(fireMap);
    }

    public void showCityFragment() {
        cityFlag = true;
        fragmentTrans = fragmentMgr.beginTransaction();
        fragmentTrans.setCustomAnimations(R.anim.trans_in_from_right, R.anim.no_anim);
        if (fragmentMgr.findFragmentByTag("registerCityFragment") == null) {
            registerCityFragment = new RegisterCityFragment();
            fragmentTrans.add(R.id.FrameLayout_register, registerCityFragment, "registerCityFragment");
            fragmentTrans.addToBackStack(null);
        } else {
            fragmentTrans.show(registerCityFragment);
        }
        fragmentTrans.hide(registerFragment);
        fragmentTrans.commit();
    } 
    
    public void showDistrictFragment() {
        districtFlag = true;
        fragmentTrans = fragmentMgr.beginTransaction();
        fragmentTrans.setCustomAnimations(R.anim.trans_in_from_right, R.anim.no_anim);
        registerDistrictFragment = new RegisterDistrictFragment();
        fragmentTrans.add(R.id.FrameLayout_register, registerDistrictFragment, "registerDistrictFragment");
        fragmentTrans.hide(registerCityFragment);
        fragmentTrans.commit();
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - timeTemp) > 300) {
            timeTemp = System.currentTimeMillis();
            if (myBackInDistrict) {
                myBackInDistrict = false;
                districtFlag = false;
                fragmentTrans = fragmentMgr.beginTransaction();
                fragmentTrans.setCustomAnimations(R.anim.no_anim, R.anim.trans_out_to_right);
                fragmentTrans.hide(registerDistrictFragment);
                fragmentTrans.show(registerCityFragment);
                fragmentTrans.commit();
            } else if (districtFlag) {
                districtFlag = false;
                cityFlag = false;
                fragmentTrans = fragmentMgr.beginTransaction();
                fragmentTrans.setCustomAnimations(R.anim.no_anim, R.anim.trans_out_to_right);
                fragmentTrans.hide(registerDistrictFragment);
                fragmentTrans.hide(registerCityFragment);
                fragmentTrans.show(registerFragment);
                fragmentTrans.commit();
            } else if (cityFlag) {
                cityFlag = false;
                fragmentTrans = fragmentMgr.beginTransaction();
                fragmentTrans.setCustomAnimations(R.anim.no_anim, R.anim.trans_out_to_right);
                fragmentTrans.hide(registerCityFragment);
                fragmentTrans.show(registerFragment);
                fragmentTrans.commit();
            } else {
                for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    getSupportFragmentManager().popBackStack();
                    Log.d("register", "getBackStackEntryCount = " + i);
                }
                super.onBackPressed();
            }
        }
    }

    public void myOnBackPressed(Boolean myBackInDistrict) {
        this.myBackInDistrict = myBackInDistrict;
        onBackPressed();
    }

    // 由RegisterCityFragment呼叫
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    // 由RegisterDistrictFragment呼叫
    public String getCityName() {
        return this.cityName;
    }

    // 由RegisterDistrictFragment呼叫
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    // 由RegisterFragment的onHiddenChanged呼叫
    public String getDistrictName() {
        return this.districtName;
    }

    public void setFireMap(Map fireMap) {
        this.fireMap = fireMap;
    }
    
}