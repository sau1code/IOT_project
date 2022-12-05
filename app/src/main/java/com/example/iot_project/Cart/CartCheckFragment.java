package com.example.iot_project.Cart;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartCheckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartCheckFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private ImageView imageViewBack;
    private TextView textViewCheckout;
    private CartActivity cartActivity;
    private LinearLayout LinearLayoutForFragment;
    private FragmentManager fragmentMgr;
    private FragmentTransaction fragmentTrans;
    private CartCheckItemHeadFragment cartCheckItemHeadFragment;
    private CartCheckItemBodyFragment cartCheckItemBodyFragment;
    private CartCheckItemFooterFragment cartCheckItemFooterFragment;


    public CartCheckFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartCheckFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartCheckFragment newInstance(String param1, String param2) {
        CartCheckFragment fragment = new CartCheckFragment();
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart_check, container, false);

        imageViewBack = (ImageView)view.findViewById(R.id.ImageView_Cart_back);
        textViewCheckout = (TextView)view.findViewById(R.id.textView_cart_checkout);
        LinearLayoutForFragment = (LinearLayout) view.findViewById(R.id.LinearLayout_check_forFragment);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartActivity.onBackPressed();
            }
        });

        textViewCheckout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        textViewCheckout.setBackgroundColor(Color.parseColor("#FF6060"));
                        break;
                    case MotionEvent.ACTION_UP:
                        Dialog dialogCartBodyDelete = new Dialog(getContext());
                        dialogCartBodyDelete.setContentView(R.layout.dialog_cart_check);
                        TextView textViewYes = (TextView) dialogCartBodyDelete.findViewById(R.id.textView_dialog_cart_body_yes);
                        TextView textViewCancel = (TextView) dialogCartBodyDelete.findViewById(R.id.textView_dialog_cart_body_cancel);
                        textViewCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogCartBodyDelete.dismiss();
                            }
                        });
                        textViewYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cartActivity.showDoneFragment();
                                Toast.makeText(getContext(), "恭喜，完成訂單!", Toast.LENGTH_SHORT).show();
                                dialogCartBodyDelete.dismiss();
                            }
                        });
                        dialogCartBodyDelete.show();
                        dialogCartBodyDelete.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        textViewCheckout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.Mycolor_1));
                        break;
                }
                return true;
            }
        });


        fragmentMgr = getParentFragmentManager();
        fragmentTrans = fragmentMgr.beginTransaction();
        cartCheckItemHeadFragment = new CartCheckItemHeadFragment();
        fragmentTrans.add(R.id.LinearLayout_check_forFragment, cartCheckItemHeadFragment, "cartCheckItemHeadFragment");
        fragmentTrans.commit();

        fragmentTrans = fragmentMgr.beginTransaction();
        cartCheckItemBodyFragment = new CartCheckItemBodyFragment();
        fragmentTrans.add(R.id.LinearLayout_check_forFragment, cartCheckItemBodyFragment, "cartCheckItemBodyFragment");
        fragmentTrans.commit();
        fragmentTrans = fragmentMgr.beginTransaction();
        cartCheckItemBodyFragment = new CartCheckItemBodyFragment();
        fragmentTrans.add(R.id.LinearLayout_check_forFragment, cartCheckItemBodyFragment, "cartCheckItemBodyFragment");
        fragmentTrans.commit();

        fragmentTrans = fragmentMgr.beginTransaction();
        cartCheckItemFooterFragment = new CartCheckItemFooterFragment();
        fragmentTrans.add(R.id.LinearLayout_check_forFragment, cartCheckItemFooterFragment, "cartCheckItemFooterFragment");
        fragmentTrans.commit();




        fragmentTrans = fragmentMgr.beginTransaction();
        cartCheckItemHeadFragment = new CartCheckItemHeadFragment();
        fragmentTrans.add(R.id.LinearLayout_check_forFragment, cartCheckItemHeadFragment, "cartCheckItemHeadFragment");
        fragmentTrans.commit();

        fragmentTrans = fragmentMgr.beginTransaction();
        cartCheckItemBodyFragment = new CartCheckItemBodyFragment();
        fragmentTrans.add(R.id.LinearLayout_check_forFragment, cartCheckItemBodyFragment, "cartCheckItemBodyFragment");
        fragmentTrans.commit();
        fragmentTrans = fragmentMgr.beginTransaction();
        cartCheckItemBodyFragment = new CartCheckItemBodyFragment();
        fragmentTrans.add(R.id.LinearLayout_check_forFragment, cartCheckItemBodyFragment, "cartCheckItemBodyFragment");
        fragmentTrans.commit();

        fragmentTrans = fragmentMgr.beginTransaction();
        cartCheckItemFooterFragment = new CartCheckItemFooterFragment();
        fragmentTrans.add(R.id.LinearLayout_check_forFragment, cartCheckItemFooterFragment, "cartCheckItemFooterFragment");
        fragmentTrans.commit();




        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cartActivity = (CartActivity)getActivity();
    }

}