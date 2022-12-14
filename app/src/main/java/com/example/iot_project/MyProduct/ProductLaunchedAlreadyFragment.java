package com.example.iot_project.MyProduct;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.iot_project.R;
import com.example.iot_project.salesRecord.OrderInvalidRecyclerAdapter;
import com.example.iot_project.salesRecord.SalesRecordActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductLaunchedAlreadyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductLaunchedAlreadyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView ProductLaunchAlready_listView;
    private LinearLayoutManager productLayoutManager;
    private RecyclerView recyclerViewLaunchedAlready;
    private LaunchedAlreadyRecyclerAdapter ProductRecyclerAdapter;

    public ProductLaunchedAlreadyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductLaunchedAlreadyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductLaunchedAlreadyFragment newInstance(String param1, String param2) {
        ProductLaunchedAlreadyFragment fragment = new ProductLaunchedAlreadyFragment();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_launched_already, container, false);
        MyProductActivity myProductActivity = (MyProductActivity)getActivity();
        List<Map<String,Object>> productList = new ArrayList<>();
        Map<String,Object> productMap = new HashMap<>();
        for(int i=0;i<10;i++){
            productMap.put("productName","??????");
            productMap.put("productInventory",2000);
            productMap.put("productPrice",200);
            productMap.put("productSoldAmount",199);
            productMap.put("productState","?????????");
            productList.add(productMap);
        }
        //      set the LayoutManager and Adapter of RecuclerView
//      LinearLayoutManager : reverseLayout = false??????????????????????????????true???????????????????????????
//      LinearLayoutManager.VERTICAL ??????
        productLayoutManager = new LinearLayoutManager(myProductActivity,LinearLayoutManager
                .VERTICAL,false);
        recyclerViewLaunchedAlready = (RecyclerView)v.findViewById(R.id.ProductLaunchAlready_RecyclerView);
        recyclerViewLaunchedAlready.setLayoutManager(productLayoutManager);
        ProductRecyclerAdapter = new LaunchedAlreadyRecyclerAdapter(myProductActivity,productList);
        recyclerViewLaunchedAlready.setAdapter(ProductRecyclerAdapter);
        return v;
    }
}

//-------------------------------------------------------------------------------------------------
// ????????????????????????
// ??????????????? ??????????????? "?????????" ?????????????????????
//----------------------------------------------------------------------------------
//    ??????????????? : goods
//    ??????????????????            ????????????          Cursor Index
//    * ??????_id            goods_id             0
//    # ??????_id            seller_id            1
//      ????????????            gName                2
//      ??????               info                 3
//      ????????????            packageLength        4
//      ????????????            packageWidth         5
//      ????????????            packageHeight        6
//      ?????????              inventory            7
//      ????????????           soldQuantity          8
//      ????????????7-11        seven                9
//      ??????????????????         familyMart          10
//      ??????????????????         postOffice          11
//      ??????????????????         blackCat            12
//      ??????7-11            sevenFee            13
//      ????????????            familyMartFee        14
//      ????????????            postOfficeFee        15
//      ????????????            blackCatFee          16
//      ????????????            gState               17
//----------------------------------------------------------------------------------
//----------------------------------------------------------------------------------
//    ??????????????? : goodsNorm
//    ??????????????????                         ????????????          Cursor Index
//  * ????????????_id                       goodsNorm_id             0
//     ????????????                         goods_name               1
//     fragment??????                       fragNum               2
//     ????????????                            price                 3
//     ????????????                            norm                  4
//     ????????????                            normNum               5
//     ??????fragment??????(???????????????)          count                 6
//----------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------
//    ??????????????? : goodsPic
//    ??????????????????                         ????????????          Cursor Index
//   * ??????_id                       goodsPicture_id             0
//     ????????????                         goods_name                1
//     fragment??????                       fragPic                2
//     ??????                            goodsPicture              3
//     ??????fragment??????(???????????????)          count                 4
//----------------------------------------------------------------------------------