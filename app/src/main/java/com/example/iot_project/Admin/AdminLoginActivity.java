package com.example.iot_project.Admin;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iot_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText editTextAdminAccount, editTextAdminPassword;
    private Button buttonSubmit, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

//       editText receive administer's account and password
        editTextAdminAccount = (EditText) findViewById(R.id.editText_adminlogin_account);
        editTextAdminPassword = (EditText) findViewById(R.id.editText_adminlogin_password);

//       button to submit or cancel the input
        buttonSubmit = (Button) findViewById(R.id.button_adminlogin_submit);
        buttonCancel = (Button) findViewById(R.id.button_adminlogin_cancel);

//      Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref = database.getReference("admin");

//        1. submit button:
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextAdminAccount.length() > 0 && editTextAdminPassword.length() > 0) {
                    String adminAccount = editTextAdminAccount.getText().toString();
                    String adminPassword = editTextAdminPassword.getText().toString();

                    dataref.orderByKey().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Log.d("main", "snapshot=" + snapshot);

                            if (snapshot.exists()) {
                                byte flag = 0;
                                for (DataSnapshot admin : snapshot.getChildren()) {
//                                    Log.d("main", "admin.Key=" + admin.getKey());
//                                    Log.d("main", "admin.Value=" + admin.getValue());
                                    String adminKey = admin.getKey().toString();
                                    String adminValue = admin.getValue().toString();
                                    if (adminKey.equals("account")){
                                        if(adminValue.equals(adminAccount)){
                                            flag += 1;
                                        }else {
                                            Toast.makeText(AdminLoginActivity.this, "帳號輸入錯誤", Toast.LENGTH_SHORT).show();
                                            break;
                                        }

                                    }
                                    if (adminKey.equals("password")){
                                        if(adminValue.equals(adminPassword)){
                                            flag += 1;
                                        }else{
                                            Toast.makeText(AdminLoginActivity.this, "密碼輸入錯誤", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                    }
                                    if (flag == 2) {
                                        Intent intent = new Intent(AdminLoginActivity.this, AdminMainActivity.class);
                                        intent.putExtra("adminAccount", adminAccount);
                                        startActivity(intent);
                                    }
                                }

                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(AdminLoginActivity.this, "帳號密碼輸入不完整", Toast.LENGTH_SHORT).show();

                }
            }
        });
//        2. cancel button:
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAdminPassword.setText("");
                editTextAdminAccount.setText("");
            }
        });
    }
}