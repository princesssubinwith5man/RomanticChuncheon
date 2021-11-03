package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {
    String centername;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        final TextView cn = (TextView)findViewById(R.id.name);
        final TextView ad = (TextView)findViewById(R.id.name1);
        Intent intent = getIntent();
        centername = intent.getExtras().getString("centername");
        address = intent.getExtras().getString("add");
        cn.setText(centername);
        ad.setText(address);
    }

    public void like(View view) {
        final TextView li = (TextView)findViewById(R.id.likkk);
        String a = li.getText().toString();
        int num = Integer.parseInt(a)+1;
        String result = Integer.toString(num);
        li.setText(result);
    }

    public void map(View view) {
        Intent intent = new Intent(InformationActivity.this, MapActivity.class);
        intent.putExtra("centername", centername);
        intent.putExtra("add", address);
        startActivity(intent);
    }
}