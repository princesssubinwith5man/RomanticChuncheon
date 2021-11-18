package com.example.practice;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SeeBoardActivity extends AppCompatActivity {
    String title;
    String content;
    String time;
    String name;
    String key;
    int check = 1;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private AdView mAdView;
    List<Object> Array = new ArrayList<Object>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_board);
        MobileAds.initialize(this, new OnInitializationCompleteListener() { //광고 초기화
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView); //배너광고 레이아웃 가져오기
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER); //광고 사이즈는 배너 사이즈로 설정
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT); }

        listView =(ListView)findViewById(R.id.comment123);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        listView.setAdapter(adapter);
        Intent intent = getIntent();
        title = intent.getExtras().getString("title");
        content = intent.getExtras().getString("content");
        time = intent.getExtras().getString("time");
        name = intent.getExtras().getString("name");
        key = intent.getExtras().getString("key");

        TextView Ttv = (TextView) findViewById(R.id.title_tv);
        TextView Dtv = (TextView) findViewById(R.id.date_tv);
        TextView Ctv = (TextView) findViewById(R.id.content_tv);
        TextView Ntv = (TextView) findViewById(R.id.name_tv);
        TextView Cntv = (TextView) findViewById(R.id.comment12);
        TextView Ltv = (TextView) findViewById(R.id.like111);
        Ttv.setText(title);
        Dtv.setText(time);
        Ctv.setText(content);
        Ntv.setText(name);
        FirebaseDatabase.getInstance().getReference("content").child(key).child("comment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: "+dataSnapshot.getChildrenCount());
                long cnt = dataSnapshot.getChildrenCount();
                String s = Long.toString(cnt);
                Cntv.setText(s);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference("content").child(key).child("like").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: "+dataSnapshot.getChildrenCount());
                long cnt = dataSnapshot.getChildrenCount();
                String s = Long.toString(cnt);
                Ltv.setText(s);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("content").child(key).child("like");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String e = user.getUid();
        ImageView button = (ImageView) findViewById(R.id.like_btn);
        BitmapDrawable img = (BitmapDrawable)getResources().getDrawable(R.drawable.like1);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: "+snapshot.child("like").getValue());
                    if(e.equals(snapshot.child("like").getValue())){
                        button.setImageDrawable(img);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getComment();
    }
    public void getComment(){
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String uid = user.getUid();
        FirebaseDatabase.getInstance().getReference("content").child(key).child("comment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: "+snapshot.getValue());
                    String dat = snapshot.child("dat").getValue(String.class);
                    String uid = snapshot.child("uid").getValue(String.class);
                    FirebaseDatabase.getInstance().getReference("name").child(uid).child("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String nick_name =snapshot.getValue(String.class);
                            Log.d(TAG, "nickname is "+nick_name);
                            String cmt = nick_name+": "+dat;
                            Array.add(cmt);
                            adapter.add(cmt);
                            adapter.notifyDataSetChanged();
                            listView.setSelection(adapter.getCount() - 1);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void comment123(View view) {
        HashMap<String,String> item = new HashMap<String, String>();
        EditText cmt = (EditText) findViewById(R.id.editText_comment123);
        String dat = cmt.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String e = user.getUid();
        Log.d("dafsadf", "onComplete: "+dat);
        Comment comment = new Comment(dat, e , LocalDateTime.now().toString());
        Log.d("dafsadf", "onComplete: "+comment);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("content").child(key).child("comment");
        mDatabase.push().setValue(comment);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(cmt.getWindowToken(), 0);
        cmt.setText("");
        //setListview();
    }

    public void like_btn(View view) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("content").child(key).child("like");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String e = user.getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: "+snapshot.child("like").getValue());
                    if(e.equals(snapshot.child("like").getValue())){
                        check = 0;
                    }
                }
                if(check == 1) {
                    Like like1 = new Like(e);
                    mDatabase.push().setValue(like1);
                }
                else{
                    Log.d(TAG, "like_btn: 좋아요는 한번만 돼요");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}