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
            productMap.put("productName","耳機");
            productMap.put("productInventory",2000);
            productMap.put("productPrice",200);
            productMap.put("productSoldAmount",199);
            productMap.put("productState","已上架");
            productList.add(productMap);
        }
        //      set the LayoutManager and Adapter of RecuclerView
//      LinearLayoutManager : reverseLayout = false，會按資料順序顯示，true則反轉資料顯示順序
//      LinearLayoutManager.VERTICAL 直向
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
// 以下是予馨的願望
// 存取此賣家 商品狀態為 "已上架" 的所有商品資訊
//----------------------------------------------------------------------------------
//    資料表名稱 : goods
//    欄位中文名稱            欄位名稱          Cursor Index
//    * 商品_id            goods_id             0
//    # 賣家_id            seller_id            1
//      商品名稱            gName                2
//      描述               info                 3
//      包裹長度            packageLength        4
//      包裹寬度            packageWidth         5
//      包裹高度            packageHeight        6
//      庫存量              inventory            7
//      售出數量           soldQuantity          8
//      運送方法7-11        seven                9
//      運送方法全家         familyMart          10
//      運送方法郵局         postOffice          11
//      運送方法黑貓         blackCat            12
//      運費7-11            sevenFee            13
//      運費全家            familyMartFee        14
//      運費郵局            postOfficeFee        15
//      運費黑貓            blackCatFee          16
//      商品狀態            gState               17
//----------------------------------------------------------------------------------
//----------------------------------------------------------------------------------
//    資料表名稱 : goodsNorm
//    欄位中文名稱                         欄位名稱          Cursor Index
//  * 商品規格_id                       goodsNorm_id             0
//     商品名稱                         goods_name               1
//     fragment代號                       fragNum               2
//     商品價格                            price                 3
//     商品規格                            norm                  4
//     商品數量                            normNum               5
//     創的fragment個數(包括刪掉的)          count                 6
//----------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------
//    資料表名稱 : goodsPic
//    欄位中文名稱                         欄位名稱          Cursor Index
//   * 圖片_id                       goodsPicture_id             0
//     商品名稱                         goods_name                1
//     fragment代號                       fragPic                2
//     圖片                            goodsPicture              3
//     創的fragment個數(包括刪掉的)          count                 4
//----------------------------------------------------------------------------------