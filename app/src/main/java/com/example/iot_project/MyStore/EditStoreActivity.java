package com.example.iot_project.MyStore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_project.DBHelper;
import com.example.iot_project.R;
import com.example.iot_project.Seller;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class EditStoreActivity extends AppCompatActivity {


    private TextView textView_editSellerStoreInfoPic;
    private ImageView imageView_sellerStoreInfoPic;
    private EditText editText_sellerStoreInfoName;
    private Button button_sellerStoreInfo_finish;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    byte[] sellerStorePic;
    private String sellerStoreName="";
    private Map<String, Object> SellerMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_store);
        setWindow();

        textView_editSellerStoreInfoPic = (TextView)findViewById(R.id.textView_editSellerStoreInfoPic);
        imageView_sellerStoreInfoPic = (ImageView)findViewById(R.id.imageView_sellerStoreInfoPic);
        editText_sellerStoreInfoName = (EditText)findViewById(R.id.editText_sellerStoreInfoName);
        button_sellerStoreInfo_finish = (Button)findViewById(R.id.button_sellerStoreInfo_finish);
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imageView_sellerStoreInfoPic.setImageBitmap(bitmap);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    sellerStorePic = baos.toByteArray();


                }
            }
        });

        textView_editSellerStoreInfoPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    activityResultLauncher.launch(intent);
                } else {
                    Toast.makeText(EditStoreActivity.this, "no app support", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editText_sellerStoreInfoName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    if(s.toString().length()>30){
                        Toast.makeText(EditStoreActivity.this, "賣場名稱不得超過30個字", Toast.LENGTH_SHORT).show();
                        button_sellerStoreInfo_finish.setEnabled(false);
                    }else{
                        button_sellerStoreInfo_finish.setEnabled(true);
                        sellerStoreName = s.toString();
                    }
                }else{
                    Toast.makeText(EditStoreActivity.this, "商場名稱不得為空", Toast.LENGTH_SHORT).show();
                    button_sellerStoreInfo_finish.setEnabled(false);
                }
            }
        });

        button_sellerStoreInfo_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------------------------------------------------------------------------
                //   更新此賣家的
                //   賣場照片 (storePicture) = sellerStorePic
                //   賣場名稱(storeName) = sellerStoreName

                //------------------------------------------------------------------------------
                Intent intent = new Intent(EditStoreActivity.this,MyStoreActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setWindow() {
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(0xFFFFFF);
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //-------------------------------------------------------------------------------------------------
//  以下是予馨的願望 [171 列的Map即匯集所有結果]
//  存取現在登入賣家(member_id)
//  sellerStorePic = 商場照片(storePicture)
//  sellerStoreName = 賣場名稱(storeName)
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference dataRef = database.getReference();
//        SharedPreferences spData = getSharedPreferences("LoginInformation", MODE_PRIVATE);
//        String seller_id = spData.getString("member_id", "");
////      用Map 儲存結果資料:
//        SellerMap = new HashMap<String,Object>();
////      存取現在登入賣家(member_id)的 商場照片(storePicture) 、 賣場名稱(storeName) 、
//        dataRef.child("seller").child(seller_id).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
//            @Override
//            public void onSuccess(DataSnapshot dataSnapshot) {
//                Seller seller = dataSnapshot.getValue(Seller.class);
//                String storeName = seller.getStoreName();
//                Log.d("main","storeName="+storeName);
//                String storePicture = seller.getStorePicture();
//                Log.d("main","storePicture="+storePicture);
//                SellerMap.put("storeName",storeName);
//                SellerMap.put("storePicture",storePicture);
////                -------------------------------------------------
////                SellerMap
////                最終完整的資料結果在這個區塊的SellerMap
////                ------------------------------------------------
//
//                Log.d("main","SellerMap="+SellerMap);
//            }
//        });
    }
}

