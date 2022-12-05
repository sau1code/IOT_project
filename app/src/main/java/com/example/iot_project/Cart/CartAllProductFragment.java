package com.example.iot_project.Cart;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.iot_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAllProductFragment extends Fragment {

    private View view;
    private FragmentManager fragmentMgr;
    private FragmentTransaction fragmentTrans;
    private CartItemHeadFragment cartItemHeadFragment;
    private CartItemBodyFragment cartItemBodyFragment;
    private CartItemFooterFragment cartItemFooterFragment;
    private TextView textViewCheckout, textViewShippingSelect, textViewDiscountSelect;
    private TextView textViewTotal, textViewNoSelect;
    private ImageView imageViewRedPoint, imageViewGreenPoint;
    private ConstraintLayout RelativeLayoutCoupon;
    private CartActivity cartActivity;
    private CheckBox checkBoxSelectAll;
    private List<Fragment> fragmentList;
    private Map<String, Map<String, Object>> outsideMap, outsideCouponMap, outsideShippingMap;
    private Map<String, Object> CouponChooseMap, ShippingChooseMap;
    private int subTotal, allSum, discount, shippingFree, subTotalAfterDiscount;
    private boolean isSelectAll, NoSelectShowFlagByDiscount, NoSelectShowFlagByShipping;
    private boolean hasCouponChoose, hasShippingChoose;
    private AlphaAnimation alphaAnimation;
    private double discount_MCL = 1;
    private DatabaseReference fireRef;
    private ValueEventListener fireListener;
    private List<Map<String, String>> fireList;
    private Map<String, List<String>> keyMap;

    public CartAllProductFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart_all_product, container, false);

        findView();
        setData();
        setListener();
        makeCard();    // 模擬兩張訂單

        return view;
    }

    // 將CartBody傳來的Map遍歷，計算總金額(未折扣)與，"判斷設定是否為全選選"
    public void calculateTotal() {
        subTotal = 0; allSum = 0; isSelectAll = true;

        outsideMap.forEach((id, insideMap) -> {

            if ((boolean)insideMap.get("isExist")) {            // itemBody 存在
                if ((boolean)insideMap.get("checkBoxFlag")) {   // itemBody 被勾選
                    int price = Integer.parseInt(insideMap.get("price").toString());
                    int sum = (int) insideMap.get("sum");
                    subTotal += price * sum;
                    allSum += sum;
                } else {                                        // itemBody 未被勾選
                    isSelectAll = false;                        // 存在 + 未勾選 = "未全選"
                }
            }

        });

        checkBoxSelectAll.setChecked(isSelectAll);              // 設定是否全選
        textViewCheckout.setText("去買單(" + allSum +")");      // 顯示商品數量

        subTotalAfterDiscount = (int)(subTotal * discount_MCL - discount);  // 扣除折扣
        subTotalAfterDiscount = (subTotalAfterDiscount > 0 ? subTotalAfterDiscount : 0);
        textViewTotal.setText("" + subTotalAfterDiscount);  // 計算總金額，若小於零為零

        setRedPointGone();              // 先關閉紅燈等checkMinimumInItem裡的人來點亮
        setGreenPointGone();            // 先關閉綠燈等checkMinimumInItem裡的人來點亮
        checkMinimumInCouponItem();
        checkMinimumInShippingItem();
    }

    // 當有Coupon被選並傳入時，Show出相應的燈號圖式與總金額
    public void setMarkShowsAndWhiteByCoupon(Map insideMap) {
        discount = 0;
        discount_MCL = 1;
        if ((boolean)insideMap.get("checkBoxFlag")) {           // 選取
            CouponChooseMap = insideMap;                        // 紀錄一下是誰 之後金額改變先問它還合格嗎再動作
            hasCouponChoose = true;
            setTextViewDiscountSelectShow();                    // 顯示 [已選折價]
            setRedPointGone();                                  // 關閉紅燈
            whatDiscount(insideMap.get("id").toString());       // 依照優惠券id 設定AllProduct中的discount // 應該要再insideMap中get出 來自資料庫
            outsideCouponMap.forEach((id, allInsideMap) -> {
                if (allInsideMap != CouponChooseMap) {          // 關閉除了CouponChooseMap的優惠券
                    ((CartCouponItemFragment) getParentFragmentManager().findFragmentByTag(id)).setViewWhiteVisibility();
                }
            });
        } else {
            checkMinimumInCouponItem();                         // 取消選取了 通知大家符合金額的自己打開
            CouponChooseMap = new HashMap<>();                  // CouponChooseMap 清空
            hasCouponChoose = false;
            calculateTotal();                                   // 重新顯示價格
        }
    }


    // 當有Shipping被選並傳入時，Show出相應的燈號圖式
    public void setMarkShowsAndWhiteByShipping(Map insideMap) {
        if ((boolean)insideMap.get("checkBoxFlag")) {               // 選取
            ShippingChooseMap = insideMap;                          // 紀錄一下是誰 之後金額改變先問它還合格嗎再動作
            hasShippingChoose = true;
            setTextViewShippingSelectShow();                        // 顯示 [已選免運]
            setGreenPointGone();                                    // 關閉綠燈
            outsideShippingMap.forEach((id, allInsideMap) -> {
                if (allInsideMap != ShippingChooseMap) {            // 關閉除了ShippingChooseMap的免運券
                    ((CartFreeShippingItemFragment) getParentFragmentManager().findFragmentByTag(id)).setViewWhiteVisibility();
                }
            });
        } else {
            checkMinimumInShippingItem();                           // 取消選取了 通知大家符合金額的自己打開
            ShippingChooseMap = new HashMap<>();                    // CouponChooseMap 清空
            hasShippingChoose = false;
            calculateTotal();                                       // 重新顯示價格
        }
    }

    // 將CartBody傳來的 insideMap 存入 outsideMap，  ( outsideMap<id, insideMap> )
    public void setOutSideMap(Map insideMap) {
        this.outsideMap.put(insideMap.get("id").toString(), insideMap);
        calculateTotal();           // 再由subTotal扒開來計算
    }

    public void setOutsideCouponMapFirst(Map insideMap) {
        this.outsideCouponMap.put(insideMap.get("id").toString(), insideMap);
    }

    public void setOutsideShippingMapFirst(Map insideMap) {
        this.outsideShippingMap.put(insideMap.get("id").toString(), insideMap);
    }

    // 將CouponItem傳來的 insideMap 存入 outsideCouponMap，  ( outsideCouponMap<id, insideMap> )
    public void setOutSideCouponMap(Map insideMap) {
        this.outsideCouponMap.put(insideMap.get("id").toString(), insideMap);
        setMarkShowsAndWhiteByCoupon(insideMap);    // 再由setMarkShowsAndWhiteVisibility判斷 其他優惠券該如何顯示 與 呼叫設定/計算價格
        calculateTotal();                           // 再由subTotal扒開來計算
    }

    // 將ShippingItem傳來的 insideMap 存入 outsideShippingMap，  ( outsideCouponMap<id, insideMap> )
    public void setOutSideShippingMap(Map insideMap) {
        this.outsideShippingMap.put(insideMap.get("id").toString(), insideMap);
        setMarkShowsAndWhiteByShipping(insideMap);  // 再由setMarkShowsAndWhiteVisibility判斷 其他優惠券該如何顯示
    }

    // 請每個在outsideCouponMap中的類自我審查，是否符合優惠條件，審查判斷式寫在各自類裡
    public void checkMinimumInShippingItem() {
        // 如果有選擇中的先檢查選擇中的需要取消嗎
        if (hasShippingChoose) {
            ((CartFreeShippingItemFragment) getParentFragmentManager().findFragmentByTag(ShippingChooseMap.get("id").toString())).checkMinimum(subTotalAfterDiscount);
        } else {
            outsideShippingMap.forEach((id, insideMap) -> {
                ((CartFreeShippingItemFragment) getParentFragmentManager().findFragmentByTag(id)).checkMinimum(subTotalAfterDiscount);
            });
        }
    }

    // 請每個在outsideCouponMap中的類自我審查，是否符合優惠條件，審查判斷式寫在各自類裡
    public void checkMinimumInCouponItem() {
        // 如果有選擇中的先檢查選擇中的需要取消嗎
        if (hasCouponChoose) {
            ((CartCouponItemFragment) getParentFragmentManager().findFragmentByTag(CouponChooseMap.get("id").toString())).checkMinimum(subTotal);
        } else {
            outsideCouponMap.forEach((id, insideMap) -> {
                ((CartCouponItemFragment) getParentFragmentManager().findFragmentByTag(id)).checkMinimum(subTotal);
            });
        }
    }

    // 判斷優惠總類，並設定金額，將來給subTotal使用   /////// 這部分 優惠價格應該由資料庫抓取
    private void whatDiscount(String id) {
        switch (id) {
            case "E":
                discount = 30;
                break;
            case "F":
                discount_MCL = 0.95;
                break;
            case "G":
                discount_MCL = 0.85;
                break;
        }
    }

    public void setTextViewDiscountSelectHide() {
        NoSelectShowFlagByDiscount = true;
        setTextViewNoSelectShow();                              // 顯示 [選擇優惠券]
        textViewDiscountSelect.setVisibility(View.GONE);        // 隱藏 [已選折價]
    }

    public void setTextViewDiscountSelectShow() {
        NoSelectShowFlagByDiscount = false;
        textViewNoSelect.setVisibility(View.GONE);              // 隱藏 [選擇優惠券]
        textViewDiscountSelect.setVisibility(View.VISIBLE);     // 顯示 [已選折價]
    }

    public void setTextViewShippingSelectHide() {
        NoSelectShowFlagByShipping = true;
        setTextViewNoSelectShow();                              // 顯示 [選擇優惠券]
        textViewShippingSelect.setVisibility(View.GONE);        // 隱藏 [已選折價]
    }

    public void setTextViewShippingSelectShow() {
        NoSelectShowFlagByShipping = false;
        textViewNoSelect.setVisibility(View.GONE);              // 隱藏 [選擇優惠券]
        textViewShippingSelect.setVisibility(View.VISIBLE);     // 顯示 [已選折價]
    }


    public void setTextViewNoSelectShow() {
        if (NoSelectShowFlagByDiscount && NoSelectShowFlagByShipping) {
            textViewNoSelect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cartActivity = (CartActivity)getActivity();
    }

    public int getSubTotal() { return subTotal; }

    // 閃爍紅燈
    public void setRedPointVisible() {
        imageViewRedPoint.setVisibility(View.VISIBLE);
        imageViewRedPoint.setAnimation(alphaAnimation);
    }

    // 關閉紅燈
    public void setRedPointGone() {
        imageViewRedPoint.setVisibility(View.GONE);
        imageViewRedPoint.clearAnimation();
    }

    // 閃爍綠燈
    public void setGreenPointVisible() {
        imageViewGreenPoint.setVisibility(View.VISIBLE);
        imageViewGreenPoint.setAnimation(alphaAnimation);
    }

    // 關閉綠燈
    public void setGreenPointGone() {
        imageViewGreenPoint.setVisibility(View.GONE);
        imageViewGreenPoint.clearAnimation();
    }

    private void setData() {
        fragmentMgr = getParentFragmentManager();
        fragmentList = new ArrayList<>();
        outsideMap = new HashMap<>();
        outsideCouponMap = new HashMap<>();
        outsideShippingMap = new HashMap<>();
        subTotal = 0;
        subTotalAfterDiscount = 0;
        allSum = 0;
        discount = 0;
        shippingFree = 0;
        textViewTotal.setText("0");
        int sum = 0;
        textViewCheckout.setText("去買單(" + sum +")");

        alphaAnimation = new AlphaAnimation(0.1f, 1.0f);   // Alpha透明度 0.0透明 ~ 1.0不透明
        alphaAnimation.setDuration(1000);                  // 週期毫秒
        alphaAnimation.setRepeatCount(Animation.INFINITE); // 重複
        alphaAnimation.setRepeatMode(Animation.REVERSE);   // 反向執行
        alphaAnimation.start();

        cartActivity.newCouponFragment();   // 先將CouponFragment創出來 需與其互動的效果才會出來
    }

    // 模擬訂單
    public void makeCard() {

        /*
            購物車實現構思

            一進這頁這方法，就搜尋以下

            "SELECT goods_id, sum FROM sum WHERE member_id = 'SP中的ID' AND order_id != null;
            取出商品id 與 數量  這裡可能會有好幾組

            "SELECT * FROM goods WHERE goods_id = '商品id';"

            現在有 "商品資訊???" "商品id" "商品賣家" "商品勾選數量" 就可以做出購物車的樣子了
            主要要先做出一種可以forLoop的型態
            先判斷相同賣家就共用head跟footer
            在將Map(包含預計購買數量)丟進子fragment
            用這數值賦予子fragment中的變數真實意義

            %%%%%%%% 再用賣家id去找 賣場名稱 在 "SELECT storeName FROM seller WHERE seller_id = seller_id"
            %%%%%%%% 價格在 goodsNorm 怕是總數浮動價子fragment在搜 "SELECT price FROM goodsNorm WHERE goodsNorm_id = goods_id"

            將以上搜尋的Map加入Map.put("sum", sum)放進List做Loop


         */


/*
        // 商品在 NewProductActivity 第305行存入
        GoodInfoMap.put("seller_id",seller_id);
        GoodInfoMap.put("goods_name",gName);
        GoodInfoMap.put("info",info);
        GoodInfoMap.put("packageLength",packageLength);
        GoodInfoMap.put("packageWidth",packageWidth);
        GoodInfoMap.put("packageHeight",packageHeight);
        GoodInfoMap.put("inventory",productNum);
        GoodInfoMap.put("seven",seven);
        GoodInfoMap.put("familyMart",familyMart);
        GoodInfoMap.put("postOffice",postOffice);
        GoodInfoMap.put("blackCat",blackCat);
        GoodInfoMap.put("sevenFee",sevenFee);
        GoodInfoMap.put("familyMartFee",familyMartFee);
        GoodInfoMap.put("postOfficeFee",postOfficeFee);
        GoodInfoMap.put("blackCatFee",blackCatFee);
        GoodInfoMap.put("gState",gState);
        GoodInfoMap.put("createTime",createTime);
*/

        SharedPreferences sp = cartActivity.getSharedPreferences("LoginInformation", MODE_PRIVATE);
        String memberId = sp.getString("member_id", "0");

        // 真實這段要只搜member選過的商品

        // 先假設試試這買家加入了全站商品
        fireList = new ArrayList<>();
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        fireRef = firebase.getReference("goods");
        fireListener = fireRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot goods : snapshot.getChildren()) {
                        String goodsKey = goods.getKey();                                       // 商品id
                        Map<String, String> goodsMap = (Map<String, String>) goods.getValue();  // 商品Map
                        if (goodsMap.get("gState").equals("通過")) {
                            goodsMap.put("goodsKey", goodsKey);
                            fireList.add(goodsMap);
                        }
                    }
                    makeFragmentByFirebase(fireList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    private void makeFragmentByFirebase(List fireList) {
        keyMap = new HashMap<>();   // Map<String, List<String>> keyMap;

        while (fireList.size() > 0) {
            String seller_idTemp = ((Map) fireList.get(0)).get("seller_id").toString(); // 將第一位的賣家取出當參考

            fragmentTrans = fragmentMgr.beginTransaction();
            cartItemHeadFragment = CartItemHeadFragment.newInstance(seller_idTemp);
            fragmentTrans.add(R.id.LinearLayout_allproduct, cartItemHeadFragment, seller_idTemp);
            fragmentTrans.commit();

            List<String> keyList = new ArrayList<>();
            fragmentTrans = fragmentMgr.beginTransaction();
            for (int i = 0; i < fireList.size(); i++) {
                Map map = (Map)fireList.get(i);
                if (map.get("seller_id").toString().equals(seller_idTemp)) {
                    cartItemBodyFragment = CartItemBodyFragment.newInstance((HashMap)map);
                    fragmentTrans.add(R.id.LinearLayout_allproduct, cartItemBodyFragment, map.get("goodsKey").toString());
                    fragmentList.add(cartItemBodyFragment); // ?? 再看看有需要嗎
                    keyList.add(map.get("goodsKey").toString());     // 將單一賣家的所有goodsKey放進keyList 之後再放進Map
                    fireList.remove(i--);
                }
            }
            fragmentTrans.commit();
            keyMap.put(seller_idTemp, keyList); // 做一個Map給將來的Head取用，完成"編輯"刪除全開

            fragmentTrans = fragmentMgr.beginTransaction();
            cartItemFooterFragment = new CartItemFooterFragment();
            fragmentTrans.add(R.id.LinearLayout_allproduct, cartItemFooterFragment, "" + seller_idTemp);
            fragmentTrans.commit();
        }
    }

    public List getGoodsKeyFormKeyMap(String seller_id) {
        return this.keyMap.get(seller_id);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListener() {
        checkBoxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (isSelectAll || b) {
                    for (Fragment f : fragmentList) {
                        ((CartItemBodyFragment) f).setCheckBox_1(b);
                    }
                }
            }
        });

        RelativeLayoutCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartActivity.showCouponFragment();
            }
        });

        textViewCheckout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        textViewCheckout.setBackgroundColor(Color.parseColor("#FF6060"));
                        break;
                    case MotionEvent.ACTION_UP:
                        textViewCheckout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.Mycolor_1));
                        if (allSum < 1) {
                            Toast.makeText(getContext(), "請至少選取一項商品", Toast.LENGTH_SHORT).show();
                        } else {
                            cartActivity.showCheckFragment();
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void findView() {
        textViewCheckout = (TextView)view.findViewById(R.id.textView_cart_checkout);
        textViewTotal = (TextView)view.findViewById(R.id.textView_cart_total);
        textViewNoSelect = (TextView)view.findViewById(R.id.textView_cart_no_select);
        textViewShippingSelect = (TextView)view.findViewById(R.id.textView_cart_ship_select);
        textViewDiscountSelect = (TextView)view.findViewById(R.id.textView_cart_discount_select);
        RelativeLayoutCoupon = (ConstraintLayout)view.findViewById(R.id.RelativeLayout_cart_coupon);
        checkBoxSelectAll = (CheckBox)view.findViewById(R.id.checkBox_cart_selectAll);
        imageViewRedPoint = (ImageView)view.findViewById(R.id.imageView_redpoint);
        imageViewGreenPoint = (ImageView)view.findViewById(R.id.imageView_greenpoint);
    }

}


// 此類onCreateView()中 呼叫cartActivity.newCouponFragment();
// 兩類都存在時才能交流

/* ---------------------------------- 全選實現過程 ----------------------------------

    為 "全選checkbox" 與 多個"子checkbox" 間的互動

    a.全選checkbox被子checkbox控制
        1. 判斷過程，依附在金額初算的 calculateTotal() 裡
        2. 在CartItemBody中，每一個按鈕都會呼叫此類的 setOutSideMap() 並傳送新的insideMap過來
        3. setOutSideMap() 會順便執行 calculateTotal()
        4. 設旗標isSelectAll為全選 = true
        5. 檢查每個insideMap中的checkBoxFlag狀態，若其中有未選，設isSelectAll = false (若無保持true)
        6. 將全選checkBox的狀態，設定與isSelectAll旗標相同
        7. 全選checkbox被改變會觸發監聽，監聽再次將所有子checkbox設為與isSelectAll旗標相同

    b.全選checkbox控制子checkbox
        1. 當初在此類創造的CartItemBody都存放在一個List裡
        2. 當全選checkbox監聽啟動，就將List降階出每個fragment並呼叫其中setCheckBox_1()
        3. 由setCheckBox_1()將全選checkbox的現在狀態傳入設定所有子checkbox與全選狀態同步

 */