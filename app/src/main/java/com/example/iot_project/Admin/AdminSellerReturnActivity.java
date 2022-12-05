package com.example.iot_project.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iot_project.R;
import android.os.Bundle;
import android.view.MenuItem;

public class AdminSellerReturnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_seller_return);

        //       顯示返回鍵
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        actionbar.setTitle("賣家退貨審核");

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){ //開啟返回鍵
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}