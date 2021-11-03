package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}