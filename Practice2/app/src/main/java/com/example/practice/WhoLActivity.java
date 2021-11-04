package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WhoLActivity extends AppCompatActivity {
    String shopNum;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_lactivity);
        listview = (ListView) findViewById(R.id.list1);
        Intent intent = getIntent();
        shopNum = intent.getExtras().getString("num");
        Log.d("asdf", "onCreate: "+shopNum);

        FirebaseDatabase.getInstance().getReference("like").child(shopNum).child("wholike").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    FirebaseDatabase.getInstance().getReference("name").child(uid).child("name").addValueEventListener(new ValueEventListener(){
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             String name = (String)snapshot.getValue();
                             //Log.d("asdf", "onDataChange: "+dataList.get(0));
                             Log.d("좋아요누른사람은", "onDataChange: "+name);
                         }
                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {
                         }
                    });
                    //Log.d("asdf", "onDataChange: "+snapshot.getKey());
                }
                //setListview();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Log.d("asdf", "onCreate: "+dataList.get(0));

    }
    /*public void setListview(){
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList);
        listview.setAdapter(adapter);
    }*/
}