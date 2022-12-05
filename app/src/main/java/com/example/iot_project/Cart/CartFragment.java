package com.example.iot_project.Cart;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.iot_project.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View view;

    private ViewPager2 ShoppingCartViewPager;
    private TabLayout shoppingTabLayout;
    private List<Object> shoppingCartTabTitle;
    private ImageView imageViewBack;
    private CartActivity cartActivity;

    public CartFragment() {}

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        ShoppingCartViewPager = (ViewPager2)view.findViewById(R.id.ShoppingCart_ViewPager);
        shoppingTabLayout = (TabLayout)view.findViewById(R.id.shoppingCart_tabLayout);
        imageViewBack = (ImageView)view.findViewById(R.id.ImageView_Cart_back);

        // 禁止使用者滑痛頁面
        ShoppingCartViewPager.setUserInputEnabled(false);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartActivity.onBackPressed();
            }
        });

        shoppingCartTabTitle = new ArrayList<>();
        shoppingCartTabTitle.add("所有商品");
        shoppingCartTabTitle.add("再買一次");
        CartPagerAdapter pageAdapter = new CartPagerAdapter(cartActivity);
        ShoppingCartViewPager.setAdapter(pageAdapter);

        new TabLayoutMediator(shoppingTabLayout, ShoppingCartViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText((CharSequence) shoppingCartTabTitle.get(position));
            }
        }).attach();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cartActivity = (CartActivity)getActivity();
    }

}