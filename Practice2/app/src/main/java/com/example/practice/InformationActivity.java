package com.example.practice;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class InformationActivity extends AppCompatActivity {
    String centername;
    String address;
    String shopNum;
    DocumentReference shopRef;
    FirebaseFirestore db;
    int like;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT); }
        final TextView cn = findViewById(R.id.name);
        final TextView ad = findViewById(R.id.name1);
        TextView likeText = findViewById(R.id.likkk);

        Intent intent = getIntent();
        centername = intent.getExtras().getString("centername");
        address = intent.getExtras().getString("add");
        like = Integer.parseInt(intent.getExtras().getString("like"));
        shopNum = intent.getExtras().getString("key");

        db = FirebaseFirestore.getInstance();
        shopRef = db.collection("shop").document(shopNum);

        shopRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    
                    String updateLike = snapshot.getString("like");
                    likeText.setText(updateLike);
                    like = Integer.parseInt(updateLike);
                    
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

        cn.setText(centername);
        ad.setText(address);
        likeText.setText(Integer.toString(like));
    }

    public void like(View view) {
        final TextView li = (TextView)findViewById(R.id.likkk);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        
        db.collection("like").

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