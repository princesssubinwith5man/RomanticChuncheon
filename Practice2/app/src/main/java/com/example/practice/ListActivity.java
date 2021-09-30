package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
    DatabaseReference mDatabase;
    static int cnt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        GetData();
    }
    public void GetData(){

        for(int i = 0;i<=40;i++) {
            String dong = Integer.toString(i);
            mDatabase.child("춘천시").child(dong).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                        HashMap<String,String> item = new HashMap<String, String>();
                        String address = fileSnapshot.child("address").getValue(String.class);
                        String name = fileSnapshot.child("name").getValue(String.class);
                        String sector = fileSnapshot.child("sector").getValue(String.class);
                        //if (sector.equals("숙박 및 음식점")) {
                            Log.i("TAG: value is ", name + " : " + address);
                            item.put("item1",name);
                            item.put("item2",address);
                            list.add(item);
                        //}
                        cnt++;
                        String a = Integer.toString(cnt);
                        Log.i("TAG: Total Count ", a);
                    }
                    PrintListView();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
                }
            });
        }
    }
    private void PrintListView() {
        ListView listView =(ListView)findViewById(R.id.list);
        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,new String[]{"item1","item2"}, new int[] {android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);
    }
}