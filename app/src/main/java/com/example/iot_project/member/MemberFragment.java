package com.example.iot_project.member;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import static com.example.iot_project.NewProduct.NewPictureFragment.PICK_PHOTO;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_project.Main.MainActivity;
import com.example.iot_project.MyStore.MyStoreActivity;
import com.example.iot_project.R;
import com.example.iot_project.register.RegisterActivity;
import com.example.iot_project.SellerRegister.BecomeSellerActivity;
import com.example.iot_project.Cart.CartActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class MemberFragment extends Fragment implements View.OnClickListener{

    private View view;
    private MemberActivity memberActivity;
    private Intent intent;
    private ImageView imageViewSetting, imageViewCart, imageViewMypic;
    private LinearLayout LinearLayoutOrders_0, LinearLayoutOrders_1, LinearLayoutOrders_2, LinearLayoutOrders_3;
    private RelativeLayout RelativeLayoutMystore, RelativeLayoutBecomeSeller, RelativeLayoutOrders;
    private RelativeLayout RelativeLayoutFavorite, RelativeLayoutBought, RelativeLayoutSeen;
    private RelativeLayout RelativeLayoutCoupon, RelativeLayoutPersonal, RelativeLayoutGoMain;
    private TextView textViewName;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private String memberId, account_name;
    private String picture;

    public static MemberFragment newInstance() {
        return new MemberFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_member, container, false);

        findView();
        setData();
        setListener();
        myRegisterForActivityResult();

        return view;
    }

    // 更換用戶大頭照 從相機回Activity時執行
    private void myRegisterForActivityResult() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                    // 相機拍照後的照片在這取出，此時圖片資料型態為 Bitmap
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imageViewMypic.setImageBitmap(bitmap);

                    // 將 Bitmap 轉型成 Base64
                    String encodedImage = "";
                    try {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); // bm is the bitmap object
                        byte[] b = baos.toByteArray();
                        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                        Log.d("member","encodedImage = "+encodedImage);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 使用 member_id 與 "picture"，向firebase更新使用者大頭照
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference();
                    ref.child("member").child(memberId).child("picture").setValue(encodedImage);
                    Toast.makeText(memberActivity, "大頭照修改完成", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        memberActivity = (MemberActivity)getActivity();
    }

    private void findView() {
        textViewName = (TextView)view.findViewById(R.id.textView_member_name);
        imageViewSetting = (ImageView)view.findViewById(R.id.imageView_member_setting);
        imageViewCart = (ImageView)view.findViewById(R.id.imageView_member_cart);
        imageViewMypic = (ImageView)view.findViewById(R.id.imageView_member_picture);
        LinearLayoutOrders_0 = (LinearLayout)view.findViewById(R.id.LinearLayout_member_orders_0);
        LinearLayoutOrders_1 = (LinearLayout)view.findViewById(R.id.LinearLayout_member_orders_1);
        LinearLayoutOrders_2 = (LinearLayout)view.findViewById(R.id.LinearLayout_member_orders_2);
        LinearLayoutOrders_3 = (LinearLayout)view.findViewById(R.id.LinearLayout_member_orders_3);
        RelativeLayoutMystore = (RelativeLayout)view.findViewById(R.id.RelativeLayout_member_mystore);
        RelativeLayoutGoMain = (RelativeLayout)view.findViewById(R.id.RelativeLayout_member_goMain);
        RelativeLayoutBecomeSeller = (RelativeLayout)view.findViewById(R.id.RelativeLayout_member_becomeSeller);
        RelativeLayoutOrders = (RelativeLayout)view.findViewById(R.id.RelativeLayout_member_orders);
        RelativeLayoutFavorite = (RelativeLayout)view.findViewById(R.id.RelativeLayout_member_favorite);
        RelativeLayoutBought = (RelativeLayout)view.findViewById(R.id.RelativeLayout_member_bought);
        RelativeLayoutSeen = (RelativeLayout)view.findViewById(R.id.RelativeLayout_member_seen);
        RelativeLayoutCoupon = (RelativeLayout)view.findViewById(R.id.RelativeLayout_member_coupon);
        RelativeLayoutPersonal = (RelativeLayout)view.findViewById(R.id.RelativeLayout_member_personal);
    }

    private void setData() {
        // 向 SharedPreferences 取得 member_id 備用
        SharedPreferences sp = memberActivity.getSharedPreferences("LoginInformation", MODE_PRIVATE);
        memberId = sp.getString("member_id", "0");
        account_name = sp.getString("account_name", "Apple2022");
        picture = sp.getString("picture", "");
        Log.d("member", "memberId = " +  memberId);
        Log.d("member", "account_name = " +  account_name);
        Log.d("member", "picture = " +  picture);

        if (!picture.equals("")) {
            byte[] bytes = Base64.decode(picture, Base64.DEFAULT);
            Bitmap bitmap_picture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            // 在會員頁面顯示firebase裡的大頭照
            imageViewMypic.setImageBitmap(bitmap_picture);
        } else imageViewMypic.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.cat6));

        // 在會員頁面顯示SharedPreferences裡的帳號
        textViewName.setText(account_name);






        // 改從SharedPreferences拿大頭照

//        // 向 Firebase 取得該用戶大頭照 (base64)
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        fireRef = database.getReference("member");
//
//        base64_picture = "";
//        fireListener = fireRef.orderByChild("account_name").equalTo(account_name).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    for(DataSnapshot member : snapshot.getChildren()) {
//                        base64_picture = ((Map<String,String>)member.getValue()).get("picture");
//                        Log.d("member", "Map = member.getValue() = " + member.getValue());
//                    }
//
//                    // base64 轉 bitmap 並顯示在會員頁面
//                    if (!base64_picture.equals("")) {
//                        Log.d("member", "base64_picture = " + base64_picture);
//                        byte[] bytes = Base64.decode(base64_picture, Base64.DEFAULT);
//                        Bitmap bitmap_picture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                        // 在會員頁面顯示firebase裡的大頭照
//                        imageViewMypic.setImageBitmap(bitmap_picture);
//                    } else imageViewMypic.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.cat6));
//
//                } else Toast.makeText(memberActivity, "帳號資料不存在", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });
    }

    private void setListener(){
        RelativeLayoutGoMain.setOnClickListener(this);
        imageViewSetting.setOnClickListener(this);
        imageViewCart.setOnClickListener(this);
        imageViewMypic.setOnClickListener(this);
        LinearLayoutOrders_0.setOnClickListener(this);
        LinearLayoutOrders_1.setOnClickListener(this);
        LinearLayoutOrders_2.setOnClickListener(this);
        LinearLayoutOrders_3.setOnClickListener(this);
        RelativeLayoutMystore.setOnClickListener(this);
        RelativeLayoutBecomeSeller.setOnClickListener(this);
        RelativeLayoutOrders.setOnClickListener(this);
        RelativeLayoutFavorite.setOnClickListener(this);
        RelativeLayoutBought.setOnClickListener(this);
        RelativeLayoutSeen.setOnClickListener(this);
        RelativeLayoutCoupon.setOnClickListener(this);
        RelativeLayoutPersonal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RelativeLayout_member_goMain:
                intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.RelativeLayout_member_mystore:
                intent = new Intent(getContext(), MyStoreActivity.class);
                startActivity(intent);
                break;
            case R.id.imageView_member_cart:
                intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
                break;
            case R.id.imageView_member_picture:
                Dialog newPictureDlg = new Dialog(memberActivity);
                newPictureDlg.setContentView(R.layout.new_picture_dialog);
                newPictureDlg.show();
                newPictureDlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                TextView textViewCamara = (TextView) newPictureDlg.findViewById(R.id.textView_camara);
                TextView textViewAlbum = (TextView) newPictureDlg.findViewById(R.id.textView_album);
                textViewCamara.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newPictureDlg.dismiss();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(memberActivity.getPackageManager()) != null) {
                            activityResultLauncher.launch(intent);
                        } else {
                            Toast.makeText(memberActivity, "no app support", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                textViewAlbum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newPictureDlg.dismiss();

                        //動態申請獲取存取 讀寫磁碟的許可權
                        if (ContextCompat.checkSelfPermission(memberActivity,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(memberActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                        } else {
                            //開啟相簿
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            //Intent.ACTION_GET_CONTENT = "android.intent.action.GET_CONTENT"
                            intent.setType("image/*");
                            startActivityForResult(intent, PICK_PHOTO); // 開啟相簿
                        }
                    }
                });
                break;
            case R.id.RelativeLayout_member_orders:
            case R.id.LinearLayout_member_orders_0:
                memberActivity.showOrdersFragment(0);
                break;
            case R.id.LinearLayout_member_orders_1:
                memberActivity.showOrdersFragment(1);
                break;
            case R.id.LinearLayout_member_orders_2:
                memberActivity.showOrdersFragment(2);
                break;
            case R.id.LinearLayout_member_orders_3:
                memberActivity.showOrdersFragment(3);
                break;
            case R.id.RelativeLayout_member_becomeSeller:
                Log.d("main", "getContext() = " + getContext());
                intent = new Intent(getContext(), BecomeSellerActivity.class);
                startActivity(intent);
                RelativeLayoutBecomeSeller.setVisibility(View.GONE);
                break;
            case R.id.RelativeLayout_member_favorite:
                memberActivity.showGoodsFragment("按讚好物");
                break;
            case R.id.RelativeLayout_member_bought:
                memberActivity.showGoodsFragment("再買一次");
                break;
            case R.id.RelativeLayout_member_seen:
                memberActivity.showGoodsFragment("瀏覽紀錄");
                break;
            case R.id.RelativeLayout_member_coupon:
                memberActivity.showCouponFragment();
                break;
            case R.id.imageView_member_setting:
            case R.id.RelativeLayout_member_personal:
                intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

}
