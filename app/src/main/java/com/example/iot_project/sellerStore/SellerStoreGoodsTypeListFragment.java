package com.example.iot_project.sellerStore;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.iot_project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SellerStoreGoodsTypeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellerStoreGoodsTypeListFragment extends Fragment {

    private View view;
    private ListView listViewType;
    private List<Map<String, Object>> listSample;
    private String[] typeArray;
    private TypedArray picArray;
    private SimpleAdapter adapter;
    private SellerStoreActivity sellerStoreActivity;

    public static SellerStoreGoodsTypeListFragment newInstance() {
        return new SellerStoreGoodsTypeListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_seller_store_goods_type_list, container, false);
        sellerStoreActivity = (SellerStoreActivity)getActivity();
        listViewType = (ListView)view.findViewById(R.id.ListView_seller_store_goods_type);

        typeArray = new String[]{"居家生活","五金","最新上架"};
        picArray = getResources().obtainTypedArray(R.array.seller_type_goods_pic); 

        listSample = new ArrayList<>();
        for (int i = 0; i < typeArray.length; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("pic", picArray.getResourceId(i, 0));
            data.put("type", typeArray[i]);
            listSample.add(data);
        }

        adapter = new SimpleAdapter(getContext(), listSample, R.layout.listview_seller_store_goods_type,
                new String[]{ "pic", "type" },
                new int[]{ R.id.imageView_seller_listview, R.id.textView_seller_listview });
        listViewType.setAdapter(adapter);

        listViewType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> item = (Map<String, Object>)parent.getItemAtPosition(position);
                String typeName = item.get("type").toString();
                Toast.makeText(getContext(), typeName, Toast.LENGTH_SHORT).show();
                sellerStoreActivity.showGoodsOfType(typeName);
            }
        });

        return view;
    }
}