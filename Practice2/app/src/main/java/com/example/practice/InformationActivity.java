package com.example.practice;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class InformationActivity extends AppCompatActivity {
    String centername;
    String address;
    String shopNum;
    String nick_name;
    DatabaseReference mDatabase;
    static int check = 0;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    List<Object> Array = new ArrayList<Object>();
    //ArrayList<HashMap<String,String>> list1 = new ArrayList<HashMap<String, String>>();
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

        listView =(ListView)findViewById(R.id.comment);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        listView.setAdapter(adapter);

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

                    String updateLike = Long.toString(snapshot.getLong("like"));
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
        ImageView button = (ImageView) findViewById(R.id.like_button);
        BitmapDrawable img = (BitmapDrawable)getResources().getDrawable(R.drawable.like1);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("like").document(shopNum).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Map<String, Object> map = document.getData();
                        if(map.containsKey(user.getUid().toString()) && map.get(user.getUid()).toString().equals("true"))
                            button.setImageDrawable(img);
                    }
                }
            }
        });

        if(check == 0) {
            getComment();
        }
    }
    public void getComment(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String uid = user.getUid();
        FirebaseDatabase.getInstance().getReference("comment").child(shopNum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        HashMap<String,String> item = new HashMap<String, String>();
                        String temp = snapshot.child("dat").getValue(String.class);
                        String name = snapshot.child("uid").getValue(String.class);
                        Log.d("asdfsafd", "onDataChange: " + temp+" "+name);
                        FirebaseDatabase.getInstance().getReference("name").child(name).child("name").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                nick_name =snapshot.getValue(String.class);
                                Log.d(TAG, "nickname is "+nick_name);
                                String cmt = nick_name+": "+temp;
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
    public void like(View view) {
        final TextView li = (TextView)findViewById(R.id.likkk);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String e = user.getUid();



        db.collection("like").document(shopNum).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ImageView button = (ImageView) findViewById(R.id.like_button);
                BitmapDrawable img = (BitmapDrawable)getResources().getDrawable(R.drawable.like1);
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Map<String, Object> map = document.getData();
                        if(map.containsKey(user.getUid().toString()) && map.get(user.getUid()).toString().equals("true"))
                            Toast.makeText(InformationActivity.this, "좋아요는 한번만 누를 수 있습니다", Toast.LENGTH_LONG).show();
                        else{
                            map.put(user.getUid(), true);
                            db.collection("like").document(shopNum).set(map);
                            db.collection("shop").document(shopNum).update("like", FieldValue.increment(1));
                            button.setImageDrawable(img);
                            mylike(shopNum);
                        }
                    }
                    else{
                        Map<String, Boolean> map = new HashMap<>();
                        map.put(user.getUid(), true);
                        db.collection("like").document(shopNum).set(map);
                        db.collection("shop").document(shopNum).update("like", FieldValue.increment(1));

                        button.setImageDrawable(img);
                        mylike(shopNum);
                    }
                }
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void comment1(View view) {
        HashMap<String,String> item = new HashMap<String, String>();
        EditText cmt = (EditText) findViewById(R.id.editText_comment);
        String dat = cmt.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String e = user.getUid();
        Log.d("dafsadf", "onComplete: "+dat);
        Comment comment = new Comment(dat, e ,LocalDateTime.now().toString());
        Log.d("dafsadf", "onComplete: "+comment);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("comment").child(shopNum);
        mDatabase.push().setValue(comment);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(cmt.getWindowToken(), 0);
        cmt.setText("");
        //setListview();
    }
    private void setListview(){
        //ListView listView =(ListView)findViewById(R.id.comment);
        //SimpleAdapter adapter = new SimpleAdapter(this, list1, android.R.layout.simple_list_item_2,new String[]{"item1","item2"}, new int[] {android.R.id.text1, android.R.id.text2});
        //listView.setAdapter(adapter);
    }
    public void mylike(String shopnum) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase.getInstance().getReference("name").child(uid).child("mylike").child(shopnum).setValue(true);
    }
}

