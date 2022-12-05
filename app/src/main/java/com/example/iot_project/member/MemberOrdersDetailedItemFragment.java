package com.example.iot_project.member;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.iot_project.R;

public class MemberOrdersDetailedItemFragment extends Fragment implements View.OnClickListener{

    private ImageView imageViewPicture;
    private String thisFragTag;
    private MemberOrdersDetailedFragment memberOrdersDetailedFragment;

    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(), thisFragTag, Toast.LENGTH_SHORT).show();
    }

    public static MemberOrdersDetailedItemFragment newInstance(String thisFragTag) {
        MemberOrdersDetailedItemFragment fragment = new MemberOrdersDetailedItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("thisFragTag", thisFragTag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            thisFragTag = getArguments().getString("thisFragTag");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_orders_detailed_item, container, false);
        imageViewPicture = (ImageView)view.findViewById(R.id.imageView_cardview_member_orders_picture);
        imageViewPicture.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}