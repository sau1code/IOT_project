package com.example.iot_project.sellerStore;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_project.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class SellerStoreFragment extends Fragment {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private List<String> tabTitle;
    private View view;
    private SellerStoreActivity sellerStoreActivity;
    private ImageView imageViewBack;
    private ImageView imageViewMypic;
    private RelativeLayout RelativeLayoutSearch;
    private EditText editTextShow;
    private InputMethodManager keyboard;

    public static SellerStoreFragment newInstance() {
        return new SellerStoreFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_seller_store, container, false);
        sellerStoreActivity = (SellerStoreActivity)getActivity();
        keyboard = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        viewPager2 = (ViewPager2)view.findViewById(R.id.ViewPager_seller_store);
        tabLayout = (TabLayout)view.findViewById(R.id.TabLayout_seller_store);
        imageViewMypic = (ImageView)view.findViewById(R.id.imageView_member_picture);
        imageViewBack = (ImageView)view.findViewById(R.id.imageView_seller_store_back);
        RelativeLayoutSearch = (RelativeLayout)view.findViewById(R.id.RelativeLayout_seller_store_search);
        editTextShow = (EditText)view.findViewById(R.id.editText_seller_store_show);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellerStoreActivity.onBackPressed();
            }
        });
        

        RelativeLayoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        imageViewMypic.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.storedemo));

        // 改寫在editText按下虛擬鍵盤Enter的後續動作
        editTextShow.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search();
                }
                return false;
            }
        });

        tabTitle = new ArrayList<>();
        tabTitle.add("全部商品");
        tabTitle.add("商品分類");

        SellerStoreAdapter pageAdapter = new SellerStoreAdapter(sellerStoreActivity);
        viewPager2.setAdapter(pageAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitle.get(position));
            }
        }).attach();

        // 這頁cleanFocus跟正常關鍵盤都失效 用以下方法成功
        sellerStoreActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        return view;
    }

    public void search() {
        String keyWord = editTextShow.getText().toString();
        if ( keyWord.length() > 0) {
            sellerStoreActivity.showGoodsOfType(keyWord);
            editTextShow.clearFocus();
            keyboard.hideSoftInputFromWindow(editTextShow.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            if (keyboard.hideSoftInputFromWindow(editTextShow.getWindowToken(), 0)) {   //內容是空的 且 鍵盤是開的
                keyboard.hideSoftInputFromWindow(editTextShow.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            } else {
                editTextShow.requestFocus();
                keyboard.showSoftInput(editTextShow, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }
}