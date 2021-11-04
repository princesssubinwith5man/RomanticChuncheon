package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class InformationActivity extends AppCompatActivity {
    String centername;
    String address;
    String key;
    DatabaseReference mDatabase;
    int like;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        final TextView cn = findViewById(R.id.name);
        final TextView ad = findViewById(R.id.name1);
        TextView likeText = findViewById(R.id.likkk);

        Intent intent = getIntent();
        centername = intent.getExtras().getString("centername");
        address = intent.getExtras().getString("add");
        like = Integer.parseInt(intent.getExtras().getString("like"));
        key = intent.getExtras().getString("key");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("shop").child(key).child("like").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                likeText.setText(snapshot.getValue().toString());
                like = Integer.parseInt(snapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cn.setText(centername);
        ad.setText(address);
        likeText.setText(Integer.toString(like));
    }

    public void like(View view) {
        final TextView li = (TextView)findViewById(R.id.likkk);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query query = mDatabase.child("like").child(key).child("wholike").equalTo(user.getUid());



        if(mDatabase.child("like").child(key).child("wholike").get().getResult().getValue().toString())

        like++;
        mDatabase.child("like").setValue(like);
    }

    public void map(View view) {
        Intent intent = new Intent(InformationActivity.this, MapActivity.class);
        intent.putExtra("centername", centername);
        intent.putExtra("add", address);
        startActivity(intent);
    }
}