package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    String shopNum;
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
        shopNum = intent.getExtras().getString("key");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("shop").child(shopNum).child("like").addValueEventListener(new ValueEventListener() {
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

        mDatabase.child("like").child(shopNum).child("wholike").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                    Toast.makeText(InformationActivity.this, "좋아요는 한 번만 누를 수 있습니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    mDatabase.child("like").child(shopNum).child("wholike").child(user.getUid()).setValue(true);
                    like++;
                    mDatabase.child("shop").child(shopNum).child("like").setValue(like);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    public void map(View view) {
        Intent intent = new Intent(InformationActivity.this, MapActivity.class);
        intent.putExtra("centername", centername);
        intent.putExtra("add", address);
        startActivity(intent);
    }

    public void who_like(View view) {
        Intent intent = new Intent(InformationActivity.this, WhoLActivity.class);
        intent.putExtra("num", shopNum);
        startActivity(intent);
    }
}