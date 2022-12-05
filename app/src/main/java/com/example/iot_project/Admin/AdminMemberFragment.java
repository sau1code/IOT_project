package com.example.iot_project.Admin;

import static android.content.Context.MODE_PRIVATE;

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
import android.widget.ImageView;
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
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminMemberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminMemberFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView ListViewMember;
    private TextView textViewMemberNum;
    private TextView textViewMemberDataCount;
    private EditText editTextSearch;
    private ImageButton buttonSearch;
    private AdminMainActivity activity;
    private String account;
    private long memberNumber;
    private ValueEventListener memberListener;
    private DatabaseReference dataref;
    private ImageView imageViewMember;


    public AdminMemberFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminMemberFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminMemberFragment newInstance(String param1, String param2) {
        AdminMemberFragment fragment = new AdminMemberFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
//        ,List<Map<String,Object>> ListData
//        args.putParcelableArrayList("ListData",(ArrayList<? extends Parcelable>)ListData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
//            ListData= getArguments().getParcelableArrayList("ListData");
//            Log.d("main"," ListData="+ ListData);

        }
        Log.d("main", "[memberFrag]onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("main", "[memberFrag]onCreateView");
        // Inflate the layout for this fragment
        View memberView = inflater.inflate(R.layout.fragment_admin_member, container, false);
        ListViewMember = (ListView) memberView.findViewById(R.id.listView_admin_menber);//會員資料
        textViewMemberNum = (TextView) memberView.findViewById(R.id.textView_admin_p_number);//會員數量
        textViewMemberDataCount = (TextView) memberView.findViewById(R.id.textView_admin_p_data);//會員資料搜尋結果筆數
//        editTextSearch = (EditText) memberView.findViewById(R.id.editText_admin_p_search);//搜尋會員資料
//        buttonSearch = (ImageButton) memberView.findViewById(R.id.imageButton_admin_p_search);//搜尋按鈕
        return memberView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (AdminMainActivity) context;

        Log.d("main", "[memberFrag]onAttach");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("main", "[memberFrag]onResume");
        SharedPreferences sp = activity.getSharedPreferences("LoginInformation", MODE_PRIVATE);
//        Log.d("main", "sp.All=" + sp.getAll());
//        String member_id = sp.getString("member_id", "").toString();
//        Boolean is_Login = sp.getBoolean("is_login", false);

//      製作表格 id 與 對應表格的帳戶名稱
        String reference = "member";
        String ID = reference.concat("_Id");
        if (reference.equals("member")) {
            account = "account_name";
        } else {
            account = "sName";
        }

//      使用 FireBase 服務
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d("main", "database=" + database);
//      使用  reference 節點進入FireBase
        dataref = database.getReference(reference);
        Log.d("main", "dataref=" + dataref);

        List<Map<String, Object>> ListData = new ArrayList<Map<String, Object>>();

//         從reference為起點下去撈會員資料
        memberListener = dataref.orderByKey().addValueEventListener(new ValueEventListener() { //每次資料更新都會監聽

            private String dataKey;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //怕更新List會有重複資料，所以如果ListData已經有資料要清掉，不然List會一直add上去
                if (ListData.isEmpty() == false) ListData.clear();
                //   取得會員人數 = 幾個 Children
                memberNumber = snapshot.getChildrenCount();
                textViewMemberNum.setText(String.valueOf(memberNumber));
//                if (editTextSearch.length() == 0) { //如果沒有執行搜尋，搜尋結果等於會員數量
                    textViewMemberDataCount.setText(String.valueOf(memberNumber));
//                }
                Log.d("main","snapshot.getValue()="+snapshot.getValue());
                for (DataSnapshot data : snapshot.getChildren()) {
                    dataKey = data.getKey();
                    Log.d("main","dataKey="+dataKey);
                    dataref.child(dataKey).addListenerForSingleValueEvent(new ValueEventListener() { //只監聽一次
                        private Map<String, Object> map;

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String memberKey = snapshot.getKey();
                            Log.d("main","memberKey="+memberKey);
                            if (reference.equals("member")) {
                                Member memberdata = snapshot.getValue(Member.class);
//                                Log.d("main","Member.ToMap()="+memberdata.ToMap());
                                map = memberdata.ToMap();
                            }
//                            }else if(reference.equals("seller")){
//                                Seller sellerdata= snapshot.getValue(Seller.class);
////                                Log.d("main","Member.ToMap()="+memberdata.ToMap());
//                                map = sellerdata.ToMap();
////                                Log.d("main","ListData="+ListData);
////                                Log.d("main","ListData.size()="+ListData.size());
//                            }

                            map.put(ID, memberKey);
                            Log.d("main", "map=" + map);
                            ListData.add(map);
//                          Log.d("main","ListData()="+ListData);

                            SimpleAdapter adpter = new SimpleAdapter(getContext(), ListData, R.layout.listview_admin_member
                                    , new String[]{"account_name", ID,"createTime"}
                                    , new int[]{R.id.textView_admin_becomeseller_account, R.id.textView_admin_becomeseller_id
                                    ,R.id.textView_admin_becomeseller_createTime});
                            adpter.notifyDataSetChanged();
                            ListViewMember.setAdapter(adpter);

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

        ListViewMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
                Log.d("main","item="+item);
                Intent intent = new Intent(activity, AdminMemberActivity.class);
                for(Object key: item.keySet().toArray()){
                    String mapKey = key.toString();
                    Object mapValue = item.get(mapKey);
                    Log.d("main","mapKey[mapValue]="+mapKey+" "+mapValue);
                    if(mapKey.equals("is_seller")){
                        Boolean is_seller = (Boolean) mapValue;
                        intent.putExtra(mapKey,is_seller);
                    }else if(!mapKey.equals("picture")){
                        String StringData = mapValue.toString();
                        intent.putExtra(mapKey,StringData);
                    }else{
                        String PicData = mapValue.toString();
                        sp.edit().putString(mapKey,PicData).commit();
                    }
                }
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        dataref.removeEventListener(memberListener);
    }

}

