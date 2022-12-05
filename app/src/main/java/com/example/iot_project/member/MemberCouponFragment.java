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

import com.example.iot_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MemberCouponFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemberCouponFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private ImageView ImageViewBack;
    private TextView textViewBar;
    private MemberActivity memberActivity;
    private FragmentManager fragmentMgr;
    private FragmentTransaction fragmentTrans;
    private MemberCouponItemFragment fragmentItem;
    private int i;

    public MemberCouponFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MemberCouponFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MemberCouponFragment newInstance(String param1, String param2) {
        MemberCouponFragment fragment = new MemberCouponFragment();
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
        view = inflater.inflate(R.layout.fragment_member_coupon, container, false);

        findView();
        setData();
        setListener();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        memberActivity = (MemberActivity)getActivity();
    }

    private void findView() {
        ImageViewBack = (ImageView)view.findViewById(R.id.imageView_seller_store_type_show_back);
        textViewBar = (TextView)view.findViewById(R.id.textView_seller_store_type_show_bar);
    }

    private void setData(){
        fragmentMgr = getParentFragmentManager();
        for (i = 0; i < 5; i++) {
            fragmentTrans = fragmentMgr.beginTransaction();
            fragmentItem = MemberCouponItemFragment.newInstance("MemberCouponItemFragment" + i);
            fragmentTrans.add(R.id.LinearLayout_member_coupon, fragmentItem, "MemberCouponItemFragment" + i);
            fragmentTrans.commit();
        }
    }

    private void setListener() {
        ImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memberActivity.onBackPressed();
            }
        });
        textViewBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTrans = fragmentMgr.beginTransaction();
                fragmentItem = MemberCouponItemFragment.newInstance("MemberCouponItemFragment" + i);
                fragmentTrans.add(R.id.LinearLayout_member_coupon, fragmentItem, "MemberCouponItemFragment" + i++);
                fragmentTrans.commit();
            }
        });
    }

    public void deleteFragment(String tag) {
        fragmentMgr.beginTransaction().remove(fragmentMgr.findFragmentByTag(tag)).commit();
    }

}