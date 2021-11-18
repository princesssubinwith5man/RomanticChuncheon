package com.example.practice;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class MyListActivity extends AppCompatActivity {
    String uid1;
    FirebaseFirestore db;
    ListView listview;
    TextView tv;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        tv = (TextView) findViewById(R.id.title_mylist);
        Intent intent = getIntent();
        uid1 = intent.getExtras().getString("uid");
        id = intent.getExtras().getInt("id");
        Log.d(TAG, "onCreate: "+uid1);
        db = FirebaseFirestore.getInstance();
        listview = (ListView) findViewById(R.id.myList);
        if(id == 0) { //내가 죽이고 싶은 가게목록을 클릭했을 경우 리스트뷰 출력
            tv.setText("내가 죽이고 싶은 가게목록");
            ListViewAdapter adapter = new ListViewAdapter();
            FirebaseDatabase.getInstance().getReference("name").child(uid1).child("mylike").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String shopNum = snapshot.getKey();
                        int sn = Integer.parseInt(shopNum);
                        Log.d(TAG, "onDataChange: " + sn);
                        CollectionReference shopRef = db.collection("shop");
                        Query query = shopRef.whereEqualTo("num", sn);
                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            // 쿼리 실행 결과 리스트뷰로 보이기
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Shop shop = document.toObject(Shop.class);
                                    adapter.addItem(0, shop.name, Integer.toString(shop.like), shop.address, document.getId());
                                    listview.setAdapter(adapter);
                                }
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
                                        Intent intent = new Intent(MyListActivity.this, InformationActivity.class);
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

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if(id == 1){ // 내가 싸지른 게시글을 클릭했을 때
            tv.setText("내가 싸지른 게시글");
            BoardListViewAdapter adapter = new BoardListViewAdapter();
            FirebaseDatabase.getInstance().getReference("content").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    adapter.clearItem();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        HashMap<String, String> item = new HashMap<String, String>();
                        String title = snapshot.child("title").getValue(String.class);
                        String content = snapshot.child("content").getValue(String.class);
                        String uid = snapshot.child("uid").getValue(String.class);
                        String key = snapshot.getKey();
                        String time =snapshot.child("time").getValue(String.class);
                        String comment = Long.toString(snapshot.child("comment").getChildrenCount());
                        //Log.d("asdf", "onDataChange: " + uid + title + content+"  "+key+"asdfsfd"+comment);
                        if(uid.equals(uid1)) {
                            FirebaseDatabase.getInstance().getReference("name").child(uid).child("name").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String name = snapshot.getValue(String.class);
                                    adapter.addItem(title, content, name, time, "0", key, comment);
                                    listview.setAdapter(adapter);
                                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            BoardListViewItem listViewItem = adapter.listViewItemList.get(i);
                                            String title = listViewItem.getTitle();
                                            String name = listViewItem.getName();
                                            String key = listViewItem.getKey();
                                            String content = listViewItem.getContent();
                                            String Time = listViewItem.getTime();

                                            //Toastdd.makeText(getApplicationContext(), "위도 : " + centerName, Toast.LENGTH_LONG).show();
                                            //Log.i("TAG: value is ", centerName + " : " + address);
                                            Intent intent = new Intent(MyListActivity.this, SeeBoardActivity.class);
                                            intent.putExtra("title", title);
                                            intent.putExtra("name", name);
                                            intent.putExtra("key", key);
                                            intent.putExtra("content", content);
                                            intent.putExtra("time", Time);
                                            startActivity(intent);
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if(id == 2) { //내가 죽이고 싶은 가게목록을 클릭했을 경우 리스트뷰 출력
            tv.setText("내가 써버린 댓글");
            ListViewAdapter adapter = new ListViewAdapter();
            FirebaseDatabase.getInstance().getReference("name").child(uid1).child("mycomment").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String shopNum = snapshot.getKey();
                        int sn = Integer.parseInt(shopNum);
                        Log.d(TAG, "onDataChange: " + sn);
                        CollectionReference shopRef = db.collection("shop");
                        Query query = shopRef.whereEqualTo("num", sn);
                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            // 쿼리 실행 결과 리스트뷰로 보이기
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Shop shop = document.toObject(Shop.class);
                                    adapter.addItem(0, shop.name, Integer.toString(shop.like), shop.address, document.getId());
                                    listview.setAdapter(adapter);
                                }
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
                                        Intent intent = new Intent(MyListActivity.this, InformationActivity.class);
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

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}