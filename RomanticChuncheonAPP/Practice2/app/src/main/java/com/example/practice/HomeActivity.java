package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private BlankFragment blankFragment = new BlankFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private BoardFragment boardFragment = new BoardFragment();
    private MyInformationFragment myInformationFragment = new MyInformationFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT); }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, blankFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
        // 하단 네비게이션바
    }
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.navi_main:
                    transaction.replace(R.id.frameLayout, blankFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commitAllowingStateLoss();
                    break;
                case R.id.navi_search:
                    transaction.replace(R.id.frameLayout, searchFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commitAllowingStateLoss();
                    break;
                case R.id.navi_board:
                    transaction.replace(R.id.frameLayout, boardFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commitAllowingStateLoss();
                    break;
                case R.id.navi_information:
                    transaction.replace(R.id.frameLayout, myInformationFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

}