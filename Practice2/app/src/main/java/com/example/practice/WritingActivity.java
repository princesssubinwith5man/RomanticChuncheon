package com.example.practice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.HashMap;

public class WritingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void write(View view){
        HashMap<String,String> item = new HashMap<String, String>();

        EditText tet = (EditText) findViewById(R.id.title_et);
        EditText cet = (EditText)findViewById(R.id.content_et);
        String title = tet.getText().toString();
        String content = cet.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String e = user.getUid();
        Log.d("dafsadf", "onComplete: "+title+" " +content+" "+e);
        Board board = new Board(title,content, e , LocalDateTime.now().toString());
        //Log.d("dafsadf", "onComplete: "+comment);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("content");
        mDatabase.push().setValue(board);
        finish();
    }

}