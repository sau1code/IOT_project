package com.example.iot_project.salesRecord;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.iot_project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<String> mParam1;
    private String mParam2;
    private TextView textView_toBeShipDetail_orderNum;
    private TextView textView_orderToBeShipDetail_name;
    private TextView textView_orderToBeShipDetail_account;
    private TextView textView_toBeShipDetail_paymentMethos;
    private TextView textView_toBeShipDetail_pickUpMethod;
    private TextView textView_toBeShipDetail_address;
    private TextView textView_toBeShipDetail_payment_state;
    private TextView textView_toBeShipDetail_totalPrice;
    private ListView listView_toBeShipDetail;
    private TextView textView_toBeShipDetail_pickState;

    public OrderDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToBeShipDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderDetailFragment newInstance(ArrayList<String> param1, String param2) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getStringArrayList(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_detail, container, false);
        SalesRecordActivity salesRecordActivity = (SalesRecordActivity)getActivity();
        textView_toBeShipDetail_orderNum = (TextView)v.findViewById(R.id.textView_toBeShipDetail_orderNum);
        textView_orderToBeShipDetail_name = (TextView) v.findViewById(R.id.textView_orderToBeShipDetail_name);
        textView_orderToBeShipDetail_account = (TextView)v.findViewById(R.id.textView_orderToBeShipDetail_account);
        textView_toBeShipDetail_paymentMethos = (TextView)v.findViewById(R.id.textView_toBeShipDetail_paymentMethos);
        textView_toBeShipDetail_pickUpMethod = (TextView)v.findViewById(R.id.textView_toBeShipDetail_pickUpMethod);
        textView_toBeShipDetail_address = (TextView)v.findViewById(R.id.textView_toBeShipDetail_address);
        textView_toBeShipDetail_payment_state = (TextView)v.findViewById(R.id.textView_toBeShipDetail_payment_state);
        textView_toBeShipDetail_totalPrice = (TextView)v.findViewById(R.id.textView_toBeShipDetail_totalPrice);
        textView_toBeShipDetail_pickState = (TextView)v.findViewById(R.id.textView_toBeShipDetail_pickState);

        listView_toBeShipDetail = (ListView)v.findViewById(R.id.listView_toBeShipDetail);

        ArrayList<String> orderList = mParam1;
        String orderNum = orderList.get(0);
        String productName = orderList.get(1);
        String productNum = orderList.get(2);
        String productPrice = orderList.get(3);
        String allProductNum = orderList.get(4);
        String totalPrice = orderList.get(5);


        textView_toBeShipDetail_orderNum.setText(orderNum);
        textView_toBeShipDetail_totalPrice.setText(totalPrice);

        List<Map<String,String>> toBeShipList = new ArrayList<>();

        for(int i=0;i < 10;i++){
            Map<String,String> toBeShipMap = new HashMap<>();
            toBeShipMap.put("productName",productName);
            toBeShipMap.put("productNum",productNum);
            toBeShipMap.put("productPrice",productPrice);
            toBeShipList.add(toBeShipMap);
        }
        SimpleAdapter simAdapter = new SimpleAdapter(salesRecordActivity,toBeShipList,R.layout.fragment_member_orders_detailed_item,
                new String[]{"productName","productNum","productPrice"},new int[] {R.id.textView_cardview_member_orders_goodsName,R.id.textView_cardview_member_orders_goodsSum,R.id.textView_cardview_member_orders_goodsPrice});

        listView_toBeShipDetail.setAdapter(simAdapter);

        return v;
    }

}