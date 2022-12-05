package com.example.iot_project.SellerRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_project.DBHelper;
import com.example.iot_project.MyStore.MyStoreActivity;
import com.example.iot_project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class BankAccountActivity extends AppCompatActivity {

    private View butttonSuccessBecomeSeller_checkMyStore;
    private TextView textViewBankAccount_bankName,textViewBankAccount_bankArea,textViewBankAccount_branch;
    private EditText editTextBankAccount_bankAccountNumber,editTextBankAccount_bankAccountName;
    private Button buttonBankAccount_finish;
    private ContentValues cv;
    private String bankAccountNum;
    private String bankAccountName;


    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account);
        setWindow();
        SharedPreferences sp = getSharedPreferences("sellerDetail",MODE_PRIVATE);
        String bankName = sp.getString("bankName","臺灣銀行");
        String bankArea = sp.getString("bankArea","臺北");
        String bankBranch = sp.getString("bankBranch","選擇");
        String sellerName = sp.getString("sellerName","");
        String sellerBirthday = sp.getString("sellerBirthday","");
        String sellerID = sp.getString("sellerId","");
        String city = sp.getString("county","");
        String area = sp.getString("area","");
        String citizen = sp.getString("sCountry","");
        String sellerAddressNum = sp.getString("sellerAddressNum","");
        String sellerAddress = sp.getString("sellerAddress","");
        bankAccountNum = sp.getString("bankAccountNum","");
        bankAccountName = sp.getString("bankAccountName","");
        Log.d("main","sp.all()="+sp.getAll());

        textViewBankAccount_bankName = (TextView)findViewById(R.id.textView_bankAccount_bankName);
        textViewBankAccount_bankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankAccountActivity.this,ChooseBankNameActivity.class);
                startActivity(intent);
            }
        });
        textViewBankAccount_bankName.setText(bankName+"  > ");
        //------------------------------------------------------------------------------------------
        textViewBankAccount_bankArea = (TextView)findViewById(R.id.textView_bankAccount_bankArea);
        textViewBankAccount_bankArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putString("bankBranch","選擇").commit();
                Intent intent = new Intent(BankAccountActivity.this,ChooseBankAreaActivity.class);
                startActivity(intent);
            }
        });
        textViewBankAccount_bankArea.setText(bankArea+"  > ");
        //------------------------------------------------------------------------------------------
        textViewBankAccount_branch = (TextView)findViewById(R.id.textView_bankAccount_branch);
        textViewBankAccount_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BankAccountActivity.this,ChooseBankBranchActivity.class);
                startActivity(intent);
            }
        });
        textViewBankAccount_branch.setText(bankBranch+"  > ");
        //------------------------------------------------------------------------------------------
        editTextBankAccount_bankAccountNumber = (EditText)findViewById(R.id.editText_bankAccount_bankAccountNumber);
        editTextBankAccount_bankAccountNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                bankAccountNum = s.toString();
                sp.edit().putString("bankAccountNum",bankAccountNum).commit();
            }
        });
        editTextBankAccount_bankAccountName = (EditText)findViewById(R.id.editText_bankAccount_bankAccountName);
        editTextBankAccount_bankAccountName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                bankAccountName = s.toString();
                sp.edit().putString("bankAccountName",bankAccountName).commit();
            }
        });
        editTextBankAccount_bankAccountName.setText(bankAccountName);
        editTextBankAccount_bankAccountNumber.setText(bankAccountNum);
        //------------------------------------------------------------------------------------------

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.headshot);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] sellerStorePic = baos.toByteArray();
        //------------------------------------------------------------------------------------------
        buttonBankAccount_finish = (Button)findViewById(R.id.button_bankAccount_finish);
        buttonBankAccount_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bankBranch=="選擇"||editTextBankAccount_bankAccountName.length()==0||editTextBankAccount_bankAccountNumber.length()==0){
                    Toast.makeText(BankAccountActivity.this, "尚未填寫完成", Toast.LENGTH_SHORT).show();
                }else{
                    String bankAccountName = editTextBankAccount_bankAccountName.getText().toString();
                    String bankAccountNumber = editTextBankAccount_bankAccountNumber.getText().toString();
                    SharedPreferences sp = getSharedPreferences("sellerDetail",MODE_PRIVATE);
                    sp.edit().clear().commit();

                    SharedPreferences sp1 = getSharedPreferences("newProduct",MODE_PRIVATE);
                    sp.edit().putString("IDNumber",sellerID).commit();
                    //--------------------------------------------------------------------------
                    Dialog successbecomeSellerNowDlg = new Dialog(BankAccountActivity.this);
                    successbecomeSellerNowDlg.setContentView(R.layout.dialog_success_become_seller);
                    successbecomeSellerNowDlg.show();
                    successbecomeSellerNowDlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    butttonSuccessBecomeSeller_checkMyStore = (Button)successbecomeSellerNowDlg.findViewById(R.id.button_successBecomeSellerDialog_checkMyStore);
                    butttonSuccessBecomeSeller_checkMyStore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DBHelper dbHelper = new DBHelper(BankAccountActivity.this);
                            SQLiteDatabase sellerDatabase = dbHelper.getWritableDatabase();
                            cv = new ContentValues();
                            cv.put("sCountry",citizen);
                            cv.put("sName",sellerName);
                            cv.put("sBirthday",sellerBirthday);
                            cv.put("IDNumber",sellerID);
                            cv.put("sCity",city);
                            cv.put("district",area);
                            cv.put("postalCode",sellerAddressNum);
                            cv.put("sAddress",sellerAddress);
                            cv.put("bankName",bankName);
                            cv.put("bankArea",bankArea);
                            cv.put("bankBranch",bankBranch);
                            cv.put("bankNumber",bankAccountNum);
                            cv.put("bankAccount",bankAccountName);
                            cv.put("sState","審核中");
                            long id = sellerDatabase.insert("seller", null, cv);
                            Cursor sellerCursor= sellerDatabase.rawQuery("select * from seller;", null);
                            Map<String, Object> sellerInfoMap = new HashMap<>();
                            //    資料表名稱 : seller
                            //    欄位中文名稱     欄位名稱       Cursor Index
                            //    *賣家_id        seller_id        1
                            //    賣場名稱 	     storeName        2
                            //    賣場照片 	     storePicture     3
                            //    國籍 	         sCountry         4
                            //    姓名 	         sName            4
                            //    生日 	         sBirthday        5
                            //    身分證字號 	     IDNumber         6
                            //    城市            sCity            8
                            //    行政區          district         9
                            //    郵遞區號        postalCode       10
                            //    地址           sAddress         11
                            //    銀行名稱        bankName         12
                            //    銀行地區        bankArea         13
                            //    銀行分行        bankBranch       14
                            //    銀行帳戶        bankNumber       15
                            //    銀行戶名        bankAccount      16
                            //    賣家申請狀態     sState           17

                            sellerCursor.moveToFirst();
                            while(!sellerCursor.isAfterLast()) {
//                                int seller_id = sellerCursor.getInt(sellerCursor.getColumnIndexOrThrow("seller_id"));
                                String sCountry = sellerCursor.getString(sellerCursor.getColumnIndexOrThrow("sCountry"));
                                String sName = sellerCursor.getString(sellerCursor.getColumnIndexOrThrow("sName"));
                                byte[] storePicture = sellerStorePic;
                                String sBirthday = sellerCursor.getString(sellerCursor.getColumnIndexOrThrow("sBirthday"));
                                String IDNumber = sellerCursor.getString(sellerCursor.getColumnIndexOrThrow("IDNumber"));
                                String sCity = sellerCursor.getString(sellerCursor.getColumnIndexOrThrow("sCity"));
                                String district = sellerCursor.getString(sellerCursor.getColumnIndexOrThrow("district"));
                                String postalCode = sellerCursor.getString(sellerCursor.getColumnIndexOrThrow("postalCode"));
                                String sAddress = sellerCursor.getString(sellerCursor.getColumnIndexOrThrow("sAddress"));
                                String bankName = sellerCursor.getString(sellerCursor.getColumnIndexOrThrow("bankName"));
                                String bankArea = sellerCursor.getString(sellerCursor.getColumnIndexOrThrow("bankArea"));
                                String bankBranch = sellerCursor.getString(sellerCursor.getColumnIndexOrThrow("bankBranch"));
                                String bankNumber = sellerCursor.getString(sellerCursor.getColumnIndexOrThrow("bankNumber"));
                                String bankAccount = sellerCursor.getString(sellerCursor.getColumnIndexOrThrow("bankAccount"));
                                String sState = sellerCursor.getString(sellerCursor.getColumnIndexOrThrow("sState"));
//                                sellerInfoMap.put("seller_id",seller_id);
                                sellerInfoMap.put("sCountry",sCountry);
                                sellerInfoMap.put("sName",sName);
                                String storePictureBase64 =Base64.encodeToString(storePicture, Base64.DEFAULT);
                                sellerInfoMap.put("storePicture",storePictureBase64);   // 9/30 更新賣場預設照片
                                sellerInfoMap.put("storeName",sName+"的賣場");     // 9/30 更新賣場預設名稱
                                sellerInfoMap.put("sBirthday",sBirthday);
                                sellerInfoMap.put("IDNumber",IDNumber);
                                sellerInfoMap.put("sCity",sCity);
                                sellerInfoMap.put("district",district);
                                sellerInfoMap.put("postalCode",postalCode);
                                sellerInfoMap.put("sAddress",sAddress);
                                sellerInfoMap.put("bankName",bankName);
                                sellerInfoMap.put("bankArea",bankArea);
                                sellerInfoMap.put("bankBranch",bankBranch);
                                sellerInfoMap.put("bankNumber",bankNumber);
                                sellerInfoMap.put("bankAccount",bankAccount);
                                sellerInfoMap.put("sState",sState);
                                //      1. 取得台灣時區(Asia/Taipei)的目前日期時間
                                ZonedDateTime NowTime = ZonedDateTime.now(ZoneId.of("Asia/Taipei"));
                                //      2. 設定日期時間格式 : "uuuu-MM-dd HH:mm:ss" = "2022-09-20 20:27:17"
                                DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
                                //      3. 將目前日期時間格式化，ex: 2022-09-20 20:27:17
                                String createTime = NowTime.format(dateTimeFormat);

                                sellerInfoMap.put("createTime",createTime);

                                Log.d("main","SellerInfoMap = "+sellerInfoMap.toString());
                                sellerCursor.moveToNext();
                            }
                            sellerDatabase.close();
                            dbHelper.close();
                            sellerInfoMapUploadToFirebase(sellerInfoMap); // upload seller information to Firebase
                            Intent intent = new Intent(BankAccountActivity.this, MyStoreActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
    private void sellerInfoMapUploadToFirebase(Map<String, Object> map){
        //------------------------------------------------------------------------------------------
//        以下是予馨的願望
//        國籍(sCountry)(Text)
//        姓名(sName)(Text)
//        出生年月日(sBirthday)(Text)
//        身分證字號(IDNumber)(Text)
//        戶籍地址(城市+行政區+輸入地址)(sAddress )(Text)
//        郵遞區號(postalCode)(Int)
//        銀行名稱(bankName)(Text)
//        銀行地區(bankArea)(Text)
//        銀行分行名稱(bankBranch)(Text)
//        銀行帳號(bankNumber)(Text)
//        銀行戶名(bankAccount)(Text)
//-------------------------------------------------------------------------------------------------
        //    資料表名稱 : seller
        //    欄位中文名稱     欄位名稱       Cursor Index
        //    *賣家_id        seller_id = member_id       1
        //    賣場名稱 	     storeName =""       2
        //    賣場照片 	     storePicture =""    3
        //    國籍 	         sCountry         4
        //    姓名 	         sName            4
        //    生日 	         sBirthday        5
        //    身分證字號 	     IDNumber         6
        //    城市            sCity            8
        //    行政區          district         9
        //    郵遞區號        postalCode       10
        //    地址           sAddress         11
        //    銀行名稱        bankName         12
        //    銀行地區        bankArea         13
        //    銀行分行        bankBranch       14
        //    銀行帳戶        bankNumber       15
        //    銀行戶名        bankAccount      16
        //    賣家申請狀態     sState           17

//        將會員賣家Id統一(seller_id=member_id)
//        member_id : getSharedPreferenced("LoginInformation",Mode.Private) key: "member_id"

        SharedPreferences memberInfor = getSharedPreferences("LoginInformation", MODE_PRIVATE);
        String member_id = memberInfor.getString("member_id","No Id");
        Log.d("main","member_id="+member_id);

        map.put("seller_id",member_id);
//        map.put("storeName","");
//        map.put("storePicture","");

//      使用 Firebase 服務
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//      取得  Firebase 資料庫 (GET網址)
        DatabaseReference dataref = database.getReference();
//      上傳賣家資料至 seller 資料表，每個賣家會有unique key，但是id與自己本來的會員資料相同
        dataref.child("seller").child(member_id).setValue(map);
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
}