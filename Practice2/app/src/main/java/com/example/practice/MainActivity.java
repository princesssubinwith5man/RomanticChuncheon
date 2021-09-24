package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // 자기 것만 건드리세요!!
    // 버튼을 눌렀을 때 toast를 이용해 학번과 이름이 뜨게 하기
    public void jeonghyeop(View view) {
    }

    public void seungmin(View view) {
    }

    public void yisak(View view) {
        Toast.makeText(getApplicationContext(), "이삭 ㅎㅇㅎㅇ", Toast.LENGTH_LONG).show();
    }

    public void subin(View view) {
    }

    public void bongkyu(View view) {
        Toast.makeText(getApplicationContext(), "춘천의 자랑 홍이삭 춘천의 자랑 홍이삭 춘천의 자랑 홍이삭 춘천의 자랑 홍이삭", Toast.LENGTH_LONG).show();
    }

    public void woojin(View view) {
    }

    public void laewon(View view) {
        Toast.makeText(getApplicationContext(),"201714198 정래원", Toast.LENGTH_LONG).show();
    }
}