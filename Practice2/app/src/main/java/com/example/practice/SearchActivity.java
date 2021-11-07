package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;


public class SearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


    }
    public void search_btn(View view) {
        EditText et = findViewById(R.id.search_txt);
        String search = et.getText().toString();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("shop");
        DatabaseReference nameRef = rootRef.child("name");
        nameRef.startAt(search)
                .endAt(search+"\uf8ff");
       /*
        EditText et = findViewById(R.id.search_txt);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("shop");
        DatabaseReference nameRef = rootRef.child("name");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean result;
                String search = et.getText().toString();
                Log.i("TAG: hihi", search);
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.i("TAG: hihi", "돌아가나");
                    String shopName = ds.child("Name").getValue(String.class);
                    result = shopName.contains(search);
                    Log.i("TAG: hihi", shopName + " : " + result);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        nameRef.addListenerForSingleValueEvent(eventListener);*/
    }
}




