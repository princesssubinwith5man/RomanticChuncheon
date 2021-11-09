package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SeeBoardActivity extends AppCompatActivity {
    String title;
    String content;
    String time;
    String name;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_board);
        Intent intent = getIntent();
        title = intent.getExtras().getString("title");
        content = intent.getExtras().getString("content");
        time = intent.getExtras().getString("time");
        name = intent.getExtras().getString("name");
        key = intent.getExtras().getString("key");

        TextView Ttv = (TextView) findViewById(R.id.title_tv);
        TextView Dtv = (TextView) findViewById(R.id.date_tv);
        TextView Ctv = (TextView) findViewById(R.id.content_tv);
        Ttv.setText(title);
        Dtv.setText(time);
        Ctv.setText(content);
    }
}