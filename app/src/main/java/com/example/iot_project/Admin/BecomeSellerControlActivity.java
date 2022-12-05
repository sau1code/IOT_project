package com.example.iot_project.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.iot_project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BecomeSellerControlActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button buttonDisagree;
    private Button buttonAgree;
    private EditText editTextSellerId,editTextCreateTime,editTextName,editTextStoreName,editTextCountry;
    private EditText editTextIDNumber,editTextBirthday,editTextCity,editTextCounty,editTextBankAccount,editTextBankNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_seller_control);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        //      取得圖片以外的會員資料
        Intent intent = getIntent();

        String seller_id =intent.getStringExtra("seller_Id");
        String storeName =intent.getStringExtra("storeName");
        String sCountry =intent.getStringExtra("sCountry");
        String sName =intent.getStringExtra("sName");
        String sBirthday =intent.getStringExtra("sBirthday");
        String IDNumber =intent.getStringExtra("IDNumber");
        String sCity =intent.getStringExtra("sCity");
        String district =intent.getStringExtra("district");
//        String sState =intent.getStringExtra("sState");
        String bankNumber =intent.getStringExtra("bankNumber");
        String bankAccount =intent.getStringExtra("bankAccount");
        String createTime =intent.getStringExtra("createTime");

//      取得editText元件
        editTextSellerId = (EditText) findViewById(R.id.editText_admin_member_id);
        editTextCreateTime = (EditText) findViewById(R.id.editText_admin_member_createTime);
        editTextName = (EditText) findViewById(R.id.editText_admin_member_name);
        editTextStoreName = (EditText) findViewById(R.id.editText_admin_member_accountname);
        editTextCountry = (EditText) findViewById(R.id.editText_admin_member_password);

        editTextIDNumber = (EditText) findViewById(R.id.editText_admin_becomseller_IDnumber);
        editTextBirthday = (EditText) findViewById(R.id.editText_admin_becomeseller_birthday);
        editTextCity = (EditText) findViewById(R.id.editText_admin_becomeseller_city);
        editTextCounty = (EditText) findViewById(R.id.editText_admin_becomeseller_county);
        editTextBankAccount = (EditText) findViewById(R.id.editText_admin_becomeselelr_bankAccount);
        editTextBankNumber = (EditText) findViewById(R.id.editText_admin_becomeselelr_bankNumber);


//      editText 放入對應的會員資料
        editTextSellerId.setText(seller_id);
        editTextCreateTime.setText(createTime);
        editTextName.setText(sName);
        editTextStoreName.setText(storeName);
        editTextCountry.setText(sCountry);

        editTextIDNumber.setText(IDNumber);
        editTextBirthday.setText(sBirthday);
        editTextCity.setText(sCity);
        editTextCounty.setText(district);
        editTextBankNumber.setText(bankNumber);
        editTextBankAccount.setText(bankAccount);

//       disable ediText
        editTextSellerId.setEnabled(false);
        editTextCreateTime.setEnabled(false);
        editTextName.setEnabled(false);
        editTextStoreName.setEnabled(false);
        editTextCountry.setEnabled(false);

        editTextIDNumber.setEnabled(false);
        editTextBirthday.setEnabled(false);
        editTextBankNumber.setEnabled(false);
        editTextBankAccount.setEnabled(false);
        editTextCity.setEnabled(false);
        editTextCounty.setEnabled(false);

//      由SharedPreference取得會員圖片base64字串並轉換成Bitmap
        SharedPreferences sp = getSharedPreferences("AdminContent", MODE_PRIVATE);
        String base64Pic =sp.getString("storePicture","").toString();
//        String base64Pic = intent.getStringExtra("picture"); //intent 傳不過來，改存在SharedPreference
        Log.d("main", "base64Pic=" + base64Pic);
        byte[] decodedString = Base64.decode(base64Pic, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Drawable d = new BitmapDrawable(getResources(), decodedByte);

//      顯示此會員的代表圖片
        imageView = (ImageView) findViewById(R.id.imageView_admin_becomeseller_id);
        imageView.setImageBitmap(decodedByte);

//        String sName = intent.getStringExtra("sName");
        String title = "會員 " + sName + " 的申請詳細資料";
        actionbar.setTitle(title);

        String seller_Id = intent.getStringExtra("seller_Id").toString();

//       不通過直接刪除賣家申請
        buttonDisagree = (Button) findViewById(R.id.button_admin_member_disagree);
        buttonDisagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder altbuilder = new AlertDialog.Builder(BecomeSellerControlActivity.this);

                altbuilder.setTitle("刪除成為賣家申請");
                altbuilder.setMessage("請確認是否刪除申請?");
                altbuilder.setIcon(android.R.drawable.ic_dialog_alert);
                altbuilder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference dataRef = database.getReference("seller");
                        dataRef.child(seller_Id).removeValue();//delete此申請
                        onBackPressed();//關掉此activity
                    }
                });

                altbuilder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                altbuilder.show();

            }
        });
//        成為賣家申請通過 :
//        seller/key/sState : 通過
//        seller/key/seller_id
//        member/seller_id/is_seller/ : true
        buttonAgree = (Button) findViewById(R.id.button_admin_becomeseller_agree);
        buttonAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dataRef = database.getReference();
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("sState","通過");
                dataRef.child("seller").child(seller_Id).updateChildren(map);
                map.clear();
                map.put("is_seller",true);
                dataRef.child("member").child(seller_Id).updateChildren(map);
                onBackPressed();//關掉此activity
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){ //開啟返回鍵
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}