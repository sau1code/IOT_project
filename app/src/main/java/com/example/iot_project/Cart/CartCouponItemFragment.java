package com.example.iot_project.Cart;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_project.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CartCouponItemFragment extends Fragment implements View.OnClickListener {

    private CartActivity cartActivity;
    private CartCouponAndShippingFragment cartCouponAndShippingFragment;
    private View view;
    private CheckBox checkBoxSelect;
    private View viewWhite;
    private CardView cardViewCoupon;
    private Map<String, Object> insideMap;
    private boolean isExist;
    private CartAllProductFragment cartAllProductFragment;
    private TextView textViewTitle, textViewIllus;
    private Map thisMap;    // "coupon_id", "cName", "cInfo", "expiryTime"

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart_coupon_item, container, false);

        findView();
        setData();
        setListener();

        setInsideMap();
        cartAllProductFragment.setOutsideCouponMapFirst(insideMap);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CardView_cart_coupon:
                checkBoxSelect.setChecked(!checkBoxSelect.isChecked());         // 因為改由CardView_cart_coupon代替checkbox 此處是模擬點選時狀態交互改變
                setInsideMap();
                cartAllProductFragment.setOutSideCouponMap(insideMap);          // 將更新的insideMap傳給cartAllProductFragment
                if (!checkBoxSelect.isChecked()) {                              // 如果為未選，一定是由已選變未選，請求All那邊幫忙動作
                    cartAllProductFragment.checkMinimumInCouponItem();          // 叫大家檢查符合條件就開放
                    cartAllProductFragment.setTextViewDiscountSelectHide();     // 隱藏 [已選折價]
                }
                break;
            case R.id.view_cart_coupon_white:
                Toast.makeText(cartActivity, "不符合使用條件", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setInsideMap() {
        insideMap.put("id", thisMap.get("coupon_id").toString());
        insideMap.put("checkBoxFlag", checkBoxSelect.isChecked());
        insideMap.put("isExist", isExist);
    }

    // CartAllProduct那邊收到有人被選擇時，會呼叫不是被選取的人啟動此方法反白自身
    public void setViewWhiteVisibility() {
        viewWhite.setVisibility(View.VISIBLE);
    }

    // 確認自身條件是否符合目前金額，當有選中狀態時，會優先詢問選中的人，內層if就是設計給他的
    public void checkMinimum(int subTotal) {
        if (subTotal >= Integer.parseInt(thisMap.get("minimum").toString())) {
            if (!checkBoxSelect.isChecked()) {          // 金額改變時會先詢問已選的人 此if代表如果符合條件+已選取狀態，就不會有任何動作
                viewWhite.setVisibility(View.GONE);                         // 關閉反白
                cartAllProductFragment.setRedPointVisible();                // 開起紅燈
                cartAllProductFragment.setTextViewDiscountSelectHide();     // 隱藏 [已選折價]
            }
        } else {
            viewWhite.setVisibility(View.VISIBLE);                          // 條件不符就開啟反白
            if (checkBoxSelect.isChecked()) {                               // 如果本來是勾選狀態，取消勾選並做一個Map告知allProduct
                checkBoxSelect.setChecked(false);
                setInsideMap();
                cartAllProductFragment.setOutSideCouponMap(insideMap);
                cartAllProductFragment.setTextViewDiscountSelectHide();     // 隱藏 [已選折價]
            }
        }
    }

    public static CartCouponItemFragment newInstance(Map thisMap) {
        CartCouponItemFragment fragment = new CartCouponItemFragment();
        Bundle args = new Bundle();
        args.putSerializable("thisMap", (HashMap)thisMap);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            thisMap = (Map)(getArguments().getSerializable("thisMap"));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cartActivity = (CartActivity)getActivity();
        cartCouponAndShippingFragment = (CartCouponAndShippingFragment)getParentFragmentManager().findFragmentById(R.id.FrameLayout_Cart);
        cartAllProductFragment = (CartAllProductFragment)getParentFragmentManager().findFragmentByTag("f0");
    }

    private void setData() {
        insideMap = new HashMap<>();
        isExist = true;
        textViewTitle.setText(thisMap.get("cName").toString());
        textViewIllus.setText(thisMap.get("cInfo").toString());
    }

    private void setListener() {
        viewWhite.setOnClickListener(this);
        cardViewCoupon.setOnClickListener(this);
    }

    private void findView() {
        cardViewCoupon = (CardView) view.findViewById(R.id.CardView_cart_coupon);
        checkBoxSelect = (CheckBox)view.findViewById(R.id.checkBox_cart_coupon_select);
        viewWhite = (View)view.findViewById(R.id.view_cart_coupon_white);
        textViewTitle = (TextView)view.findViewById(R.id.textView_cart_coupon_title);
        textViewIllus = (TextView)view.findViewById(R.id.textView_cart_coupon_illus);
    }

}














