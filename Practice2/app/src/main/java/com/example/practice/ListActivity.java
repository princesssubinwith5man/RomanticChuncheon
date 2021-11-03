package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import static com.example.practice.R.layout.listview_custom;
public class ListActivity extends AppCompatActivity {
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, Shop>> shopList = new ArrayList<>();
    DatabaseReference mDatabase;
    ListView listview;
    String address;
    String name;
    String sector;
    String temp;
    static int cnt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        temp = intent.getExtras().getString("sector");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        listview = (ListView) findViewById(R.id.list);
        GetData();
    }
    public void GetData(){
        ListViewAdapter adapter = new ListViewAdapter();
        for(int i = 0;i<=40;i++) {
            String dong = Integer.toString(i);
            HashMap<String, Shop> shopItem = new HashMap<>();
            mDatabase.child("춘천시").child(dong).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                        HashMap<String,String> item = new HashMap<String, String>();
                        Shop shop = fileSnapshot.getValue(Shop.class);
                        shopItem.put(shop.name, shop);
                        address = fileSnapshot.child("address").getValue(String.class);
                        name = fileSnapshot.child("name").getValue(String.class);
                        sector = fileSnapshot.child("sector").getValue(String.class);
                        Log.i("TAG: value is ", name + sector + " : " + temp);
                        if(name==null || name.isEmpty())continue;
                        if (sector.equals(temp)) {
                            Log.i("TAG: value is ", name + " : " + address+", "+sector);
                            adapter.addItem(0,name,"0",address);
                        }
                        cnt++;
                        String a = Integer.toString(cnt);
                        Log.i("TAG: Total Count ", a);
                    }
                    shopList.add(shopItem);
                    listview.setAdapter(adapter);
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            ListViewItem listViewItem = adapter.listViewItemList.get(i);
                            String centerName = listViewItem.getCenterNameStr();
                            String address = listViewItem.getAddressStr();
                            //Toast.makeText(getApplicationContext(), "위도 : " + centerName, Toast.LENGTH_LONG).show();
                            //Log.i("TAG: value is ", centerName + " : " + address);
                            Intent intent = new Intent(ListActivity.this, InformationActivity.class);
                            intent.putExtra("centername", centerName);
                            intent.putExtra("add", address);
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
                }
            });
            mDatabase.removeEventListener(new ValueEventListener() { // 밸류 이벤트 리스너 삭제
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
}