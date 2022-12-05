package com.example.iot_project.Admin;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.iot_project.Member;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView ListViewProduct;
    private TextView textViewProductNum;
    private TextView textViewProductDataCount;
    private EditText editTextSearch;
    private ImageButton buttonSearch;
    private Activity activity;
    private DatabaseReference dataref;
    private ValueEventListener productListener;


    public AdminProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminProductFragment newInstance(String param1, String param2) {
        AdminProductFragment fragment = new AdminProductFragment();
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
        View productView =inflater.inflate(R.layout.fragment_admin_product, container, false);
        ListViewProduct = (ListView) productView.findViewById(R.id.listView_admin_p_id);//商品資料
        textViewProductNum = (TextView) productView.findViewById(R.id.textView_admin_p_number);//商品數量
        textViewProductDataCount = (TextView) productView.findViewById(R.id.textView_admin_p_data);//商品資料搜尋結果筆數
//        editTextSearch = (EditText) productView.findViewById(R.id.editText_admin_p_search);//搜尋商品資料
//        buttonSearch = (ImageButton) productView.findViewById(R.id.imageButton_admin_p_search);//搜尋按鈕
//
////        做不完先關起來搜尋功能
//        editTextSearch.setVisibility(View.INVISIBLE);
//        buttonSearch.setVisibility(View.INVISIBLE);
        return productView;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = activity.getSharedPreferences("LoginInformation", MODE_PRIVATE);
//        Log.d("main", "sp.All=" + sp.getAll());
//        String member_id = sp.getString("member_id", "").toString();
//        Boolean is_Login = sp.getBoolean("is_login", false);

//      製作表格 id 與 對應表格的帳戶名稱
        String reference = "goods";
        String ID = reference.concat("_Id");

//      使用 FireBase 服務
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d("main", "database=" + database);
//      使用  reference 節點進入FireBase
        dataref = database.getReference(reference);
        Log.d("main", "dataref=" + dataref);

        List<Map<String, Object>> ListData = new ArrayList<Map<String, Object>>();

//         從reference為起點下去撈已通過審核的商品資料
        productListener = dataref.orderByChild("gState").equalTo("通過").addValueEventListener(new ValueEventListener() { //每次資料更新都會監聽

            private String dataKey;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //怕更新List會有重複資料，所以如果ListData已經有資料要清掉，不然List會一直add上去
                if (ListData.isEmpty() == false) ListData.clear();
                //   取得商品數量 = 幾個 Children
                long prodctNumber = snapshot.getChildrenCount();
                textViewProductNum.setText(String.valueOf(prodctNumber));
//                if (editTextSearch.length() == 0) { //如果沒有執行搜尋，搜尋結果等於商品數量
                    textViewProductDataCount.setText(String.valueOf(prodctNumber));
//                }
                Log.d("main","snapshot.getValue()="+snapshot.getValue());
                for (DataSnapshot data : snapshot.getChildren()) {
                    dataKey = data.getKey();
                    Log.d("main","dataKey="+dataKey);
                    dataref.child(dataKey).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() { //只監聽一次
                        private Map<String, Object> map;

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String productKey = snapshot.getKey();
                            Log.d("main","productKey="+productKey);
//                            "sState","通過"
                            Log.d("main","snapshot.getValue()="+snapshot.getValue());
                            map=(HashMap<String,Object>)snapshot.getValue();
                            map.put(ID, productKey);
                            Log.d("main", "map=" + map);
                            ListData.add(map);
//                          Log.d("main","ListData()="+ListData);

                            SimpleAdapter adpter = new SimpleAdapter(getContext(), ListData, R.layout.listview_admin_product
                                    , new String[]{"goods_name", ID,"createTime","gState"}
                                    , new int[]{R.id.textView_admin_product_name, R.id.textView_admin_product_id
                                    ,R.id.textView_admin_product_createTime,R.id.textView_admin_product_status});
                            adpter.notifyDataSetChanged();
                            ListViewProduct.setAdapter(adpter);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ListViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
                Log.d("main","item="+item);
                Intent intent = new Intent(activity, AdminProductActivity.class);
                for (Object key : item.keySet().toArray()) {
                    String mapKey = key.toString();
                    Object mapValue = item.get(mapKey);
                    Log.d("main", "mapKey[mapValue]=" + mapKey + " " + mapValue);
                    String StringData = mapValue.toString();
                    intent.putExtra(mapKey, StringData);

                }
                startActivity(intent);
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity= (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dataref.removeEventListener(productListener);
    }
}