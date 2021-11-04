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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import static com.example.practice.R.layout.listview_custom;
public class ListActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, Shop>> shopList = new ArrayList<>();
    DatabaseReference mDatabase;
    ListView listview;

    String temp;
    static int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        temp = intent.getExtras().getString("sector");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        listview = (ListView) findViewById(R.id.list);
        GetData();
    }

    public void GetData() {
        ListViewAdapter adapter = new ListViewAdapter();
        HashMap<String, Shop> shopItem = new HashMap<>();

        //mDatabase.child("춘천시").child(dong).orderByValue().equalTo(temp, "sector");
        Query mQuery = mDatabase.child("shop").equalTo(temp, "sector");
        mDatabase.child("shop").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = (DataSnapshot) task.getResult();
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    //HashMap<String,String> item = new HashMap<String, String>();


                    //sfksdlfjsd;lfjasdlfjasdl;fjasdl;fjasdl;fjasdl;fsdjla

                    Shop shop = fileSnapshot.getValue(Shop.class);
                    shopItem.put(shop.name, shop);

                    Log.i("TAG: value is ", shop.name + shop.sector + " : " + temp);
                    if (shop.name == null || shop.name.isEmpty()) continue;
                    if (shop.sector.equals(temp)) {
                        Log.i("TAG: value is ", shop.name + " : " + shop.address + ", " + shop.sector);
                        adapter.addItem(0, shop.name,  Integer.toString(shop.like), shop.address, fileSnapshot.getKey());
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
                        String key = listViewItem.getKey();
                        String like = listViewItem.getLike();

                        //Toastdd.makeText(getApplicationContext(), "위도 : " + centerName, Toast.LENGTH_LONG).show();
                        //Log.i("TAG: value is ", centerName + " : " + address);
                        Intent intent = new Intent(ListActivity.this, InformationActivity.class);
                        intent.putExtra("centername", centerName);
                        intent.putExtra("add", address);
                        intent.putExtra("key", key);
                        intent.putExtra("like", like);
                        startActivity(intent);
                    }
                });
            }
        });
    }

}