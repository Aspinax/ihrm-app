package com.aspinax.ihrm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.WHITE);
        }
        getSupportActionBar().hide();

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        //end of user experience

        ImageView goto_addDelegate = findViewById(R.id.adddelegate);
        goto_addDelegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.WHITE);
        }
        getSupportActionBar().hide();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setDefaultTabPosition(1);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                switch (tabId){
                    case R.id.tab_home:
                        homeFragment homeFragment = new homeFragment();
                        fragmentTransaction.replace(R.id.Content,homeFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.tab_attendance:
                        attendanceFragment attendanceFragment = new attendanceFragment();
                        fragmentTransaction.replace(R.id.Content,attendanceFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.tab_gift:
                        giftFragment giftFragment = new giftFragment();
                        fragmentTransaction.replace(R.id.Content,giftFragment);
                        fragmentTransaction.commit();
                        break;
                }

            }
        });
    }
}
