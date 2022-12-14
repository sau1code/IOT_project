package com.example.iot_project.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_project.Admin.AdminLoginActivity;
import com.example.iot_project.LoginActivity;
import com.example.iot_project.Member;
import com.example.iot_project.R;
import com.example.iot_project.SearchResultsActivity;
import com.example.iot_project.Seller;
import com.example.iot_project.member.MemberActivity;
import com.example.iot_project.register.RegisterActivity;
import com.example.iot_project.Cart.CartActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textViewBecomeSaller, textViewLogin;
    private Button buttonLogin, buttonEnroll;
    private RelativeLayout relativeLayout;
    private ImageButton imageButtonMember;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View headerView;
    private ImageView imageView;
    private ImageButton imagebuttonShoppingCart;
    private MainViewPagerAdapter pagerAdapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private LinearLayout linearLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationBarView.OnItemSelectedListener bottmNaviListener;
    private ImageButton imageButtonSearch;
    private EditText editTextSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//      To hide Action Bar
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        imagebuttonShoppingCart = (ImageButton) findViewById(R.id.imageButton_main_shoppingcart);

//      build context menu to enter administer Activity
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout_main_bottom);
        registerForContextMenu(linearLayout);
//      Login button => LoginActivity
//        buttonLogin = (Button) findViewById(R.id.button_main_login);
//      (temp) Enroll button => BecomeSellerActivity.class
//        buttonEnroll = (Button) findViewById(R.id.button_main_enroll);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigationView_id);

//      ?????? : ?????????????????? NavigationView??????Imagebutton??????????????? -----------------------------------------
////    reference : https://material.io/components/navigation-drawer/android#using-navigation-drawers
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_id);
        navigationView = (NavigationView) findViewById(R.id.navigationView_id);
        //      ??????headerLayout :
//      ??????1 : ??????????????????????????????????????? + ??????????????? ?????????????????????????????????
//      ??? activity_main.xml ??? com.google.android.material.navigation.NavigationView
//      ?????? ?????? : app:headerLayout="@layout/header_navigation_drawer"
//      ??????2 : ????????? code ??????????????????????????????????????????????????????1??????????????? app:headerLayout ?????????
        headerView = navigationView.inflateHeaderView(R.layout.header_navigation_drawer);

//      ?????????????????????????????????--------------------------------------------------------------------------------
        imageView = (ImageView) headerView.findViewById(R.id.imageView_id);
//      ???????????????????????? SharedPreferences "LoginInformation"?????????"picture"???????????????
        SharedPreferences sp = getSharedPreferences("LoginInformation", MODE_PRIVATE);
        String base64Pic = sp.getString("picture", "").toString();
//      ???"picture"??????????????????base64Pic??????""
        if (base64Pic.equals("")) {
            imageView.setImageResource(R.drawable.pearls);
        } else { //???"picture"????????????????????????bitmap????????????????????????????????????
            byte[] decodedString = Base64.decode(base64Pic, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Drawable d = new BitmapDrawable(getResources(), decodedByte);
            imageView.setImageBitmap(decodedByte);
        }
//      ----------------------------------------------------------------------------------------------------

        imageButtonMember = (ImageButton) findViewById(R.id.imageButton_main_member);

//        -------------------------------------------------------------------------------
//       ??????: ??????Tablayout
//        1. set fragment content : create ViewPagerAdapter(extends FragmentStateAdapter)
        pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), getLifecycle());

//        2. set fragment content on ViewPager2 widget.
        viewPager = (ViewPager2) findViewById(R.id.ViewPager_main_id);
        viewPager.setAdapter(pagerAdapter);

//        3. using TabLayoutMediator().attach() :
//        combine TabLayout widget and ViewPager2 widget.
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_main_id);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("???????????????");
                        break;
                    case 1:
                        tab.setText("????????????");
                        break;
                    case 2:
                        tab.setText("????????????");
                        break;
                    case 3:
                        tab.setText("????????????");
                        break;
                }
            }
        }).attach();

//      imageButton Search ????????????
        editTextSearch = (EditText) findViewById(R.id.editText_main_search);
        imageButtonSearch = (ImageButton) findViewById(R.id.imageButton_main_search);
        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchStr = editTextSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
                intent.putExtra("keyword",searchStr);
                startActivity(intent);
            }
        });

//      set button with Login status
        setButton(isLogin());

    }

    @Override
    protected void onResume() {
        super.onResume();
        // set button with Login status
        setButton(isLogin());
    }

    //??????????????????????????? : SharedPreferences"LoginInformation" : "is_login" key???
    // true???????????????false????????????
    private boolean isLogin() {
        SharedPreferences sp = getSharedPreferences("LoginInformation", MODE_PRIVATE);
        Boolean is_Login = sp.getBoolean("is_login", false);
        return is_Login;
    }

    private boolean isLogin(boolean status) {
        SharedPreferences sp = getSharedPreferences("LoginInformation", MODE_PRIVATE);
        sp.edit().putBoolean("is_login", status).commit();
        Boolean is_Login = sp.getBoolean("is_login", false);
        return is_Login;
    }

    private void setButton(boolean login) {

        imageButtonMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.imageButton_main_member) {

                    if (login) {
                        drawerLayout.openDrawer(GravityCompat.START);
                    } else {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                }
            }
        });

        imagebuttonShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.imageButton_main_shoppingcart) {
                    if (login) {
                        Intent intent = new Intent(MainActivity.this, CartActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        if (login) {//?????????
//          ????????????????????????????????????????????????
//            linearLayout.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.GONE);

            linearLayout.setVisibility(View.INVISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED); //??????NavigationView
            TextView textViewNavAccount = (TextView) headerView.findViewById(R.id.textView_main_nav_account);
            SharedPreferences sp = getSharedPreferences("LoginInformation", MODE_PRIVATE);
            String accountNAme = sp.getString("account_name", "account");
            textViewNavAccount.setText(accountNAme);

            //        ?????? NavigationView ????????? menu ????????? Toast
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    String message = "";
                    switch (item.getItemId()) {
                        case R.id.menu_main_member:
//                            message = "item1";
                            Intent intent = new Intent(MainActivity.this, MemberActivity.class);
                            startActivity(intent);
                            break;

                        case R.id.menu_main_logout:
//                            message = "item5";
                            isLogin(false);
//                            Toast.makeText(MainActivity.this, "????????????!!", Toast.LENGTH_SHORT).show();
//                            recreate();
//                          ???????????????????????? : LogoutActivity => ?????? 1 ??????????????????????????? MainActivity
                            Intent intentlogout = new Intent(MainActivity.this, LogoutActivity.class);
                            startActivity(intentlogout);
                            break;
                    }
//                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    return false;
                }
            });


        } else { //?????????

//           ???????????? navigationView ??? Button
            imageButtonMember.setVisibility(View.INVISIBLE);
//          ?????????????????????????????????????????????
            linearLayout.setVisibility(View.VISIBLE);

            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//??????NavigationView

//            1. buttom ????????????????????????
//           Login button => LoginActivity ????????????
//            buttonLogin.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                }
//            });
//          Enroll button => RegisterActivity
//            buttonEnroll.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
//                    startActivity(intent);
//                }
//            });
//

//        2. bottom navigation ????????????????????????
            bottmNaviListener = new NavigationBarView.OnItemSelectedListener() {
                private Intent intent;

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_eroll:
                            intent = new Intent(MainActivity.this, RegisterActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_login:
                            intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            break;
                    }
                    return false;
                }
            };
            bottomNavigationView.setOnItemSelectedListener(bottmNaviListener);


        }


    }

    private long exitTime;

    @Override
    public void onBackPressed() {
        Log.d("main", "exitTime=" + exitTime);
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "????????????????????????", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
            Log.d("main", "exitTime=" + exitTime);
        } else {
//            finish(); // ???????????????????????????
            moveTaskToBack(true);
        }
    }
    //  create context menu


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.linearLayout_main_bottom) {
            getMenuInflater().inflate(R.menu.admin_menu, menu);
        }
    }

    //  monitoring context menu selected options

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.admin_item) {
            Intent adminintent = new Intent(MainActivity.this, AdminLoginActivity.class);
            startActivity(adminintent);
        }
        return super.onContextItemSelected(item);
    }
}
