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

public class MyListActivity extends AppCompatActivity {
    String uid;
    FirebaseFirestore db;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        Intent intent = getIntent();
        uid = intent.getExtras().getString("uid");
        Log.d(TAG, "onCreate: "+uid);
        db = FirebaseFirestore.getInstance();
        listview = (ListView) findViewById(R.id.myList);
        ListViewAdapter adapter = new ListViewAdapter();
        FirebaseDatabase.getInstance().getReference("name").child(uid).child("mylike").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String shopNum = snapshot.getKey();
                    int sn = Integer.parseInt(shopNum);
                    Log.d(TAG, "onDataChange: "+sn);
                    CollectionReference shopRef = db.collection("shop");
                    Query query = shopRef.whereEqualTo("num", sn);
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        // 쿼리 실행 결과 리스트뷰로 보이기
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Shop shop = document.toObject(Shop.class);
                                adapter.addItem(0, shop.name,  Integer.toString(shop.like), shop.address, document.getId());
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