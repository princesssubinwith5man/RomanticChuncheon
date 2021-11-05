package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;

public class InformationActivity extends AppCompatActivity {
    String centername;
    String address;
    String shopNum;
    DatabaseReference mDatabase;
    static int check = 0;
    final ArrayList<HashMap<String,String>> list1 = new ArrayList<HashMap<String, String>>();
    final HashMap<String,String> item = new HashMap<String, String>();
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
        if(check == 0) {
            getComment();
        }
    }
    public void getComment(){
        check = 1;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase.getInstance().getReference("comment").child(shopNum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Object temp = snapshot.getValue(Object.class);
                   /* String[] temp1 = temp.toString().split("=|\\}|,");
                    String name = snapshot.getKey();
                    for(int i=0;i<temp1.length;i++){
                        if(i%2 != 0) {
                            //Log.d("asdfsafd", "onDataChange: " + temp1[i]);
                            item.put("item1", name);
                            item.put("item2", temp1[i]);
                            list1.add(item);
                            for(int j =0;j<list1.size();j++) {
                                Log.d("asdfsafd", "onDataChange: " + list1.get(j));
                            }
                            setListview();
                        }
                    }*/

                    //Log.d("asdf", "onDataChange: "+name);
                    //Log.d("asdfsafd", "onDataChange: "+temp1[1]);

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

    public void comment1(View view) {
        EditText cmt = (EditText) findViewById(R.id.editText_comment);
        String dat = cmt.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String e = user.getUid();
        Log.d("dafsadf", "onComplete: "+dat);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("comment").child(shopNum);
        mDatabase.child(e).push().setValue(dat);
        item.put("item1", e);
        item.put("item2", dat);
        list1.add(item);
        setListview();
    }
    private void setListview(){
        ListView listView =(ListView)findViewById(R.id.comment);
        SimpleAdapter adapter = new SimpleAdapter(this, list1, android.R.layout.simple_list_item_2,new String[]{"item1","item2"}, new int[] {android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);
    }
}