package com.example.iot_project.Cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.iot_project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartCouponAndShippingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartCouponAndShippingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private View view_cart_coupon1, view_cart_coupon2;
    private CartActivity cartActivity;
    private ImageView imageViewDown;
    private FragmentManager fragmentMgr;
    private int i;
    private FragmentTransaction fragmentTrans;
    private CartCouponItemFragment fragmentItem;
    private CartFreeShippingItemFragment shippingItem;
    private List<Map<String, String>> ticketList;
    private Map<String, String> ticketMap;

    public CartCouponAndShippingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartCouponFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartCouponAndShippingFragment newInstance(String param1, String param2) {
        CartCouponAndShippingFragment fragment = new CartCouponAndShippingFragment();
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
        view = inflater.inflate(R.layout.fragment_cart_coupon, container, false);

        findView();
        setData();
        setListener();

        return view;
    }

    private void findView() {
        view_cart_coupon1 = (View)view.findViewById(R.id.view_cart_coupon1);
        view_cart_coupon2 = (View)view.findViewById(R.id.view_cart_coupon2);
        imageViewDown = (ImageView)view.findViewById(R.id.imageView_cart_coupon_down);
    }

    private void setData() {

        // ?????????????????? ?????????+????????? ???????????????id?????????????????????????????????Map (?????????)
        // ???????????????????????????????????????????????????
        // ????????????Map 1???1 ?????????fragment  (??????????????????????????????id????????????firebase???)
        // ??????
        String [] coupon_id = new String[] { "E", "F", "G", "A", "B", "C", "D" };
        String [] cName = new String[] { "????????????30???", "??????9.5???", "??????8.5???", "0?????????", "99?????????", "199?????????", "299?????????" };
        String [] minimum = new String[] { "300", "1000", "2000", "0", "99", "199", "299" };
        String [] cInfo = new String[7];
        cInfo[0] = "???????????????300???????????????????????????????????????????????????!";
        cInfo[1] = "???????????????1000???????????????????????????????????????????????????!";
        cInfo[2] = "???????????????2000???????????????????????????????????????????????????!";
        cInfo[3] = "???????????????0???????????????????????????????????????????????????!";
        cInfo[4] = "???????????????99???????????????????????????????????????????????????!";
        cInfo[5] = "???????????????199???????????????????????????????????????????????????!";
        cInfo[6] = "???????????????299???????????????????????????????????????????????????!";
        String [] expiryTime = new String[] { "2023-08-01", "2023-08-01", "2023-08-01", "2023-05-01", "2023-05-01", "2023-05-01", "2023-05-01" };

        ticketList = new ArrayList<>();
        for (int i = 0; i < coupon_id.length; i++) {
            ticketMap = new HashMap<>();
            ticketMap.put("coupon_id", coupon_id[i]);
            ticketMap.put("cName", cName[i]);
            ticketMap.put("minimum", minimum[i]);
            ticketMap.put("cInfo", cInfo[i]);
            ticketMap.put("expiryTime", expiryTime[i]);
            ticketList.add(ticketMap);
        }

        fragmentMgr = getParentFragmentManager();
        for (Map ticketMap : ticketList) {
            String id = ticketMap.get("coupon_id").toString();
            switch (id) {
                case "A":
                case "B":
                case "C":
                case "D":
                    fragmentTrans = fragmentMgr.beginTransaction();
                    shippingItem = CartFreeShippingItemFragment.newInstance(ticketMap);
                    fragmentTrans.add(R.id.LinearLayout_crat_coupon, shippingItem, id);
                    fragmentTrans.commit();
                    break;
                case "E":
                case "F":
                case "G":
                    fragmentTrans = fragmentMgr.beginTransaction();
                    fragmentItem = CartCouponItemFragment.newInstance(ticketMap);
                    fragmentTrans.add(R.id.LinearLayout_crat_coupon, fragmentItem, id);
                    fragmentTrans.commit();
                    break;
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListener() {
        view_cart_coupon1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    cartActivity.onBackPressed();
                return true;
            }
        });
        view_cart_coupon2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    cartActivity.onBackPressed();
                return true;
            }
        });
        imageViewDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    cartActivity.onBackPressed();
                return true;
            }
        });
    }

    public void deleteFragment(String tag) {
        fragmentMgr.beginTransaction().remove(fragmentMgr.findFragmentByTag(tag)).commit();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cartActivity = (CartActivity)getActivity();
    }

}