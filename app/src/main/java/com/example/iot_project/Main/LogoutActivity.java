package com.example.iot_project.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.iot_project.R;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
//      目的 : 建立登出成功頁面 LogoutActivity => 顯示 1 秒後重新導向至首頁 MainActivity
//        1. 登出成功文字1秒內由淡(Alpha=0.5)轉深(Alpha=1.0)
        TextView textView = (TextView) findViewById(R.id.textView_logout_id);
        textView.startAnimation(AnimationUtils.loadAnimation(LogoutActivity.this,R.anim.logout_anim));
//        2. 設定登出成功文字顯示 1 秒後重新導向至首頁 MainActivity
        int timeout = 1000; // make the activity visible for 1 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent homepage = new Intent(LogoutActivity.this, MainActivity.class);
                startActivity(homepage);
            }
        }, timeout);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        moveTaskToBack(true);
    }
}