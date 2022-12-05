package com.example.iot_project.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.iot_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminSellerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminSellerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button ButtonBecomeSeller,ButtonSellerReturn,ButtonSellerProduct;
    private Intent intent;
    private ValueEventListener sellerCountListener;
    private DatabaseReference dataref;
    private TextView TextViewSellerNumber;

    public AdminSellerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminSellerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminSellerFragment newInstance(String param1, String param2) {
        AdminSellerFragment fragment = new AdminSellerFragment();
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
        View SellerView = inflater.inflate(R.layout.fragment_admin_seller, container, false);
        TextViewSellerNumber = (TextView) SellerView.findViewById(R.id.textView_admin_seller_number);
        ButtonBecomeSeller = (Button) SellerView.findViewById(R.id.button_admin_seller_apply);
        ButtonSellerReturn = (Button) SellerView.findViewById(R.id.button_admin_seller_return);
        ButtonSellerProduct = (Button) SellerView.findViewById(R.id.button_admin_seller_product);
        return SellerView;
    }

    @Override
    public void onResume() {
        super.onResume();
//       取得賣家人數
        String reference = "seller";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dataref = database.getReference(reference);
        sellerCountListener = dataref.orderByChild("sState").equalTo("通過").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long NumberOfSeller = snapshot.getChildrenCount();
                TextViewSellerNumber.setText(String.valueOf(NumberOfSeller));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//      前往賣家申請審核畫面
        ButtonBecomeSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(),AdminBecomeSellerActivity.class);
                startActivity(intent);
            }
        });
//      前往賣家退貨審核畫面
        ButtonSellerReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(),AdminSellerReturnActivity.class);
                startActivity(intent);
            }
        });
//      前往賣家商品上架審核畫面
        ButtonSellerProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(),AdminSellerProductActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dataref.removeEventListener(sellerCountListener);
    }
}