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
    public void jeonghyeop(View view) {
    }

    public void seungmin(View view) {
    }

    public void yisak(View view) {
    }

    public void subin(View view) {
    }

    public void bongkyu(View view) {
    }

    public void woojin(View view) {
    }

    public void laewon(View view) {
        Toast.makeText(getApplicationContext(),"201714198 정래원", Toast.LENGTH_LONG).show();
    }
}