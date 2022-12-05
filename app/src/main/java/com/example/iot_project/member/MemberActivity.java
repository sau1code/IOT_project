package com.example.iot_project.member;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_project.R;

public class MemberActivity extends AppCompatActivity {

    private MemberGoodsFragment memberGoodsFragment;
    private MemberOrdersFragment memberOrdersFragment;
    private MemberFragment memberFragment;
    private MemberOrdersDetailedFragment memberOrdersDetailedFragment;
    private MemberCouponFragment memberCouponFragment;
    private FragmentManager fragmentMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        setWindow();
        setFragment();

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

    private void setFragment() {
            fragmentMgr = getSupportFragmentManager();
            memberFragment = new MemberFragment();
            fragmentMgr.beginTransaction()
                    .add(R.id.FrameLayout_member, memberFragment, "memberFragment")
                    .addToBackStack("")
                    .commit();
    }

    public void showOrdersFragment(int whichTab) {
        if (fragmentMgr.findFragmentByTag("memberOrdersFragment") == null) {
            memberOrdersFragment = MemberOrdersFragment.newInstance(whichTab);
            fragmentMgr.beginTransaction()
                    .setCustomAnimations(R.anim.trans_in_from_right, R.anim.no_anim, R.anim.no_anim, R.anim.trans_out_to_right)
                    .add(R.id.FrameLayout_member, memberOrdersFragment, "memberOrdersFragment")
                    .addToBackStack("")
                    .hide(memberFragment)
                    .commit();
        }
    }

    public void showOrdersDetailedFragment(String barName) {
        if (fragmentMgr.findFragmentByTag("memberOrdersDetailedFragment") == null) {
            memberOrdersDetailedFragment = MemberOrdersDetailedFragment.newInstance(barName);
            fragmentMgr.beginTransaction()
                    .setCustomAnimations(R.anim.trans_in_from_right, R.anim.no_anim, R.anim.no_anim, R.anim.trans_out_to_right)
                    .add(R.id.FrameLayout_member, memberOrdersDetailedFragment, "memberOrdersDetailedFragment")
                    .addToBackStack("")
                    .hide(memberOrdersFragment)
                    .commit();
        }
    }

    public void showGoodsFragment(String barName) {
        if (fragmentMgr.findFragmentByTag("memberGoodsFragment") == null) {
            memberGoodsFragment = MemberGoodsFragment.newInstance(barName);
            fragmentMgr.beginTransaction()
                    .setCustomAnimations(R.anim.trans_in_from_right, R.anim.no_anim, R.anim.no_anim, R.anim.trans_out_to_right)
                    .add(R.id.FrameLayout_member, memberGoodsFragment, "memberGoodsFragment")
                    .addToBackStack("")
                    .hide(memberFragment)
                    .commit();
        }
    }

    public void showCouponFragment() {
        if (fragmentMgr.findFragmentByTag("memberCouponFragment") == null) {
            memberCouponFragment = new MemberCouponFragment();
            fragmentMgr.beginTransaction()
                    .setCustomAnimations(R.anim.trans_in_from_right, R.anim.no_anim, R.anim.no_anim, R.anim.trans_out_to_right)
                    .add(R.id.FrameLayout_member, memberCouponFragment, "memberCouponFragment")
                    .addToBackStack("")
                    .hide(memberFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentMgr.getBackStackEntryCount() < 2) finish();
        else fragmentMgr.popBackStack();
    }

}


// Fragment各種操作與生命週期的關聯
// https://www.twblogs.net/a/5b800a5c2b717767c6b2fd52

// Android基础：Fragment，看这篇就够了
// https://xiazdong.github.io/2017/06/15/android-fragment/