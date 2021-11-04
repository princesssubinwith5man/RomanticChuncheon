package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class WhoLActivity extends AppCompatActivity {
    String shopNum;
    ListView listview;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_lactivity);
        Intent intent = getIntent();
        shopNum = intent.getExtras().getString("num");
        Log.d("asdf", "onCreate: "+shopNum);

        FirebaseDatabase.getInstance().getReference("like").child(shopNum).child("wholike").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String,String> item = new HashMap<String, String>();
                    String uid = snapshot.getKey();
                    FirebaseDatabase.getInstance().getReference("name").child(uid).child("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = snapshot.getValue(String.class);
                            item.put("item1",name);
                            item.put("item2","이게 되네?");
                            list.add(item);
                            Log.d("asdf", "onDataChange: "+name);
                            setListview();
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

        //Log.d("asdf", "onCreate: "+dataList.get(0));

    }
    private void setListview(){
        ListView listView =(ListView)findViewById(R.id.list1);
        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,new String[]{"item1","item2"}, new int[] {android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);
    }
}