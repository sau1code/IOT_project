package com.example.iot_project.member;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iot_project.R;

public class MemberCouponItemFragment extends Fragment implements View.OnClickListener{

    private String thisFragTag;
    private View view;
    private MemberCouponFragment memberCouponFragment;
    private ConstraintLayout ConstraintLayoutCoupon;
    private TextView textViewCoupon;

    public static MemberCouponItemFragment newInstance(String thisFragTag) {
        MemberCouponItemFragment fragment = new MemberCouponItemFragment();
        Bundle args = new Bundle();
        args.putString("thisFragTag", thisFragTag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            thisFragTag = getArguments().getString("thisFragTag");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_member_coupon_item, container, false);
        ConstraintLayoutCoupon = (ConstraintLayout)view.findViewById(R.id.ConstraintLayout_member_coupon);
        textViewCoupon = (TextView) view.findViewById(R.id.textView_member_coupon);
        textViewCoupon.setText(thisFragTag.substring("MemberCouponItemFragment".length()));
        memberCouponFragment = (MemberCouponFragment)getParentFragmentManager().findFragmentById(R.id.FrameLayout_member);
        ConstraintLayoutCoupon.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        memberCouponFragment.deleteFragment(thisFragTag);
    }

}