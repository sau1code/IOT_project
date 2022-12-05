package com.example.iot_project.sellerStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.iot_project.R;

public class SellerStoreActivity extends AppCompatActivity {


    private SellerStoreFragment sellerStoreFragment;
    private FragmentManager fragmentMgr;
    private FragmentTransaction fragmentTrans;
    private SellerStoreGoodsTypeShowFragment sellerStoreGoodsTypeShowFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_store);
        setWindow();
        setFragment();
    }

    private void setWindow() {
        getSupportActionBar().hide();                   // 隱藏ActionBar
        getWindow().setNavigationBarColor(0xaaffffff);  // 最下面NavigationBar白色底
        getWindow().getDecorView()                      // 上面字設黑 | 下面虛擬按鈕深色
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private void setFragment() {
        sellerStoreFragment = new SellerStoreFragment();
        fragmentMgr = getSupportFragmentManager();
        fragmentTrans = fragmentMgr.beginTransaction();
        fragmentTrans.add(R.id.FrameLayour_seller_store, sellerStoreFragment, "sellerStoreFragment");
        fragmentTrans.commit();
    }

    public void showGoodsOfType(String barName) {
        fragmentTrans = fragmentMgr.beginTransaction();
        fragmentTrans.setCustomAnimations(R.anim.trans_in_from_right, R.anim.no_anim);
        if (fragmentMgr.findFragmentByTag("sellerStoreGoodsTypeShowFragment") == null) {
            sellerStoreGoodsTypeShowFragment = SellerStoreGoodsTypeShowFragment.newInstance(barName);
            fragmentTrans.add(R.id.FrameLayour_seller_store, sellerStoreGoodsTypeShowFragment, "sellerStoreGoodsTypeShowFragment");
            fragmentTrans.addToBackStack(null);
        } else {
            fragmentTrans.show(sellerStoreGoodsTypeShowFragment);
        }
        fragmentTrans.hide(sellerStoreFragment);
        fragmentTrans.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }


}