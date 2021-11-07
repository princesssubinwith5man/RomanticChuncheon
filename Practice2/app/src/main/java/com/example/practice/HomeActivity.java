package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private FirebaseAuth firebaseAuth;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT); }
        // 로그인시 닉네임불러오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String e = user.getUid();
        FirebaseDatabase.getInstance().getReference("name").child(e).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    tv = findViewById(R.id.nickname);
                    String nick = snapshot.getValue(String.class) + "님 환영합니다.";
                    tv.setText(nick);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
        // 하단 네비게이션바
        BottomNavigationView naviView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        naviView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // 뭐골랐는지 확인
                        switch (item.getItemId()) {
                            case R.id.navi_main:
                                Toast.makeText(HomeActivity.this, "죽여줘", Toast.LENGTH_LONG).show();
                                return true;
                            case R.id.navi_search:
                                Toast.makeText(HomeActivity.this, "검색해줘", Toast.LENGTH_LONG).show();
                                return true;
                            case R.id.navi_board:
                                Toast.makeText(HomeActivity.this, "게시판가줘", Toast.LENGTH_LONG).show();
                                return true;
                        }
                        return false;
                    }
                });

    }
    public void jeonghyeop(View view) {
        Intent intent = new Intent(HomeActivity.this, ListActivity.class);
        intent.putExtra("sector","운수업");
        startActivity(intent);
    }

    public void seungmin(View view) {
        Intent intent = new Intent(HomeActivity.this, ListActivity.class);
        intent.putExtra("sector","슈퍼/편의점");
        startActivity(intent);
    }

    public void yisak(View view) {
        Intent intent = new Intent(HomeActivity.this, ListActivity.class);
        intent.putExtra("sector","주유소");
        startActivity(intent);
    }

    public void subin(View view) {
        Intent intent = new Intent(HomeActivity.this, ListActivity.class);
        intent.putExtra("sector","도매 및 소매업");
        startActivity(intent);
    }

    public void bongkyu(View view) {
        Intent intent = new Intent(HomeActivity.this, ListActivity.class);
        intent.putExtra("sector","제조업");
        startActivity(intent);
    }

    public void woojin(View view) {
        Intent intent = new Intent(HomeActivity.this, ListActivity.class);
        intent.putExtra("sector","출판 등 정보서비스업");
        startActivity(intent);
    }

    public void laewon(View view) {
        //Toast.makeText(getApplicationContext(),"201714198 정래원", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(HomeActivity.this, ListActivity.class);
        intent.putExtra("sector","숙박 및 음식점");
        startActivity(intent);
    }

    public void laewon1(View view) {
        Intent intent = new Intent(HomeActivity.this, ListActivity.class);
        intent.putExtra("sector","보건, 사회복지 서비스업");
        startActivity(intent);
    }

    public void laewon2(View view) {
        Intent intent = new Intent(HomeActivity.this, ListActivity.class);
        intent.putExtra("sector","스포츠 등 기타 서비스업");
        startActivity(intent);
    }

    public void laewon3(View view) {
        Intent intent = new Intent(HomeActivity.this, ListActivity.class);
        intent.putExtra("sector","기타");
        startActivity(intent);
    }
    public void btn_logout(View view) { // 로그아웃 button11
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void check_name(View view) {

    }
}