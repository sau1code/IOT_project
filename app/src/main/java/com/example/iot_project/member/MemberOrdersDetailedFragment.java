package com.example.iot_project.member;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_project.R;

public class MemberOrdersDetailedFragment extends Fragment{

    private MemberActivity memberActivity;
    private ImageView ImageViewBack;
    private TextView textViewBar;
    private FragmentManager fragmentMgr;
    private FragmentTransaction fragmentTrans;
    private MemberOrdersDetailedInfoFragment fragmentInfo;
    private MemberOrdersDetailedItemFragment fragmentItem;
    private MemberOrdersDetailedTimeFragment fragmentTime;

    public static MemberOrdersDetailedFragment newInstance(String barName) {
        MemberOrdersDetailedFragment fragment = new MemberOrdersDetailedFragment();
        Bundle bundle = new Bundle();
        bundle.putString("barName", barName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_member_orders_detailed, container, false);

        ImageViewBack = (ImageView)v.findViewById(R.id.ImageView_member_back);
        textViewBar = (TextView)v.findViewById(R.id.TextView_member_bar);
        textViewBar.setText(getArguments().getString("barName"));

        ImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberActivity.onBackPressed();
            }
        });

        fragmentMgr = getParentFragmentManager();
        fragmentTrans = fragmentMgr.beginTransaction();
        fragmentInfo = new MemberOrdersDetailedInfoFragment();
        fragmentTrans.add(R.id.LinearLayout_orders_detailed, fragmentInfo, "fragmentInfo");
        fragmentTrans.commit();

        for (int i = 0; i < 5; i++) {
            fragmentTrans = fragmentMgr.beginTransaction();
            fragmentItem = MemberOrdersDetailedItemFragment.newInstance("MemberOrdersDetailedItemFragment" + i);
            fragmentTrans.add(R.id.LinearLayout_orders_detailed, fragmentItem, "MemberOrdersDetailedItemFragment" + i);
            fragmentTrans.commit();
        }

        fragmentTrans = fragmentMgr.beginTransaction();
        fragmentTime = new MemberOrdersDetailedTimeFragment();
        fragmentTrans.add(R.id.LinearLayout_orders_detailed, fragmentTime, "fragmentTime");
        fragmentTrans.commit();

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        memberActivity = (MemberActivity)getActivity();
    }

}