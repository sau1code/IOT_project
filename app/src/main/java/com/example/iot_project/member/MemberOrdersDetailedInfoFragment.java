package com.example.iot_project.member;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iot_project.R;

public class MemberOrdersDetailedInfoFragment extends Fragment {

    private View view;

    public static MemberOrdersDetailedInfoFragment newInstance() {
        return new MemberOrdersDetailedInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_member_orders_detailed_info, container, false);

        return view;
    }
}