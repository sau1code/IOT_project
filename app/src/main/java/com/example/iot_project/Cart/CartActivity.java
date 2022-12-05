package com.example.iot_project.Cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import com.example.iot_project.R;

public class CartActivity extends AppCompatActivity {

    private FragmentManager fragmentMgr;
    private CartFragment cartFragment;
    private CartCouponAndShippingFragment cartCouponAndShippingFragment;
    private boolean isCouponShow, isDoneShow;
    private int i;
    private CartCheckFragment cartCheckFragment;
    private CartDoneFragment cartDoneFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setWindow();
        setFragment();
        isCouponShow = false;
        isDoneShow = false;
        i = 2;

    }

    private void setWindow() {
        getSupportActionBar().hide();                   // 隱藏ActionBar
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.Mycolor_1));      // 最上面StatusBar白色底
        getWindow().setNavigationBarColor(0xaaffffff);  // 最下面NavigationBar白色底
        getWindow().getDecorView()                      // 上面字設黑 | 下面虛擬按鈕深色
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
    }

    private void setFragment() {
        fragmentMgr = getSupportFragmentManager();
        cartFragment = new CartFragment();
        fragmentMgr.beginTransaction()
                .add(R.id.FrameLayout_Cart, cartFragment, "cartFragment")
                .addToBackStack("")
                .commit();
    }

    public void newCouponFragment() {
        if (fragmentMgr.findFragmentByTag("cartCouponFragment") == null) {
            cartCouponAndShippingFragment = new CartCouponAndShippingFragment();
            fragmentMgr.beginTransaction()
                    .setCustomAnimations(R.anim.trans_in_from_botton, R.anim.no_anim)
                    .add(R.id.FrameLayout_Cart, cartCouponAndShippingFragment, "cartCouponFragment")
                    .addToBackStack("")
                    .hide(cartCouponAndShippingFragment)
                    .commit();
            i = 3;
        }
    }

    public void showCouponFragment() {
        if (fragmentMgr.findFragmentByTag("cartCouponFragment") == null) {
            cartCouponAndShippingFragment = new CartCouponAndShippingFragment();
            fragmentMgr.beginTransaction()
                    .setCustomAnimations(R.anim.trans_in_from_botton, R.anim.no_anim)
                    .add(R.id.FrameLayout_Cart, cartCouponAndShippingFragment, "cartCouponFragment")
                    .addToBackStack("")
                    .commit();
            i = 3;
        } else {
            fragmentMgr.beginTransaction()
                    .setCustomAnimations(R.anim.trans_in_from_botton, R.anim.no_anim)
                    .show(cartCouponAndShippingFragment)
                    .commit();
        }
        isCouponShow = true;
    }

    public void showCheckFragment() {
            cartCheckFragment = new CartCheckFragment();
            fragmentMgr.beginTransaction()
                    .setCustomAnimations(R.anim.trans_in_from_right, R.anim.no_anim, R.anim.no_anim, R.anim.trans_out_to_right)
                    .add(R.id.FrameLayout_Cart, cartCheckFragment, "cartCheckFragment")
                    .addToBackStack("")
                    .commit();

    }

    public void showDoneFragment() {
        cartDoneFragment = new CartDoneFragment();
        fragmentMgr.beginTransaction()
                .setCustomAnimations(R.anim.trans_in_from_right, R.anim.no_anim, R.anim.no_anim, R.anim.trans_out_to_right)
                .add(R.id.FrameLayout_Cart, cartDoneFragment, "cartDoneFragment")
                .addToBackStack("")
                .commit();
        isDoneShow = true;

    }

    @Override
    public void onBackPressed() {

        if (isDoneShow) finish();


        if (isCouponShow) {
            fragmentMgr.beginTransaction()
                    .setCustomAnimations(R.anim.no_anim, R.anim.trans_out_to_bottom)
                    .hide(cartCouponAndShippingFragment)
                    .commit();
            isCouponShow = false;
        } else {
            if (fragmentMgr.getBackStackEntryCount() < i) finish();
            else fragmentMgr.popBackStack();
        }
    }

}