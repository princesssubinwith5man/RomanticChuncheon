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
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WhoLActivity extends AppCompatActivity {
    String shopNum;
    ListView listview;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_lactivity);
        db = FirebaseFirestore.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT); }

        Intent intent = getIntent();
        shopNum = intent.getExtras().getString("num");
        Log.d("asdf", "onCreate: "+shopNum);
        db.collection("like").document(shopNum).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                Map<String, Object> map = document.getData();
                Object temp = map.keySet();
                String temp2 = temp.toString().replace("[","").replace("]","").replace(" ","");
                //temp2.replace("[","");
                Log.d("sadfsaf", "onComplete: "+temp2);
                String[] temp1 = temp2.split(",");
                for(int i=0;i<temp1.length;i++){
                    HashMap<String,String> item = new HashMap<String, String>();
                    FirebaseDatabase.getInstance().getReference("name").child(temp1[i]).child("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = snapshot.getValue(String.class);
                            item.put("item1",name);
                            item.put("item2","");
                            list.add(item);
                            Log.d("asdf", "onDataChange: "+name);
                            setListview();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Log.d("sadfsaf", "onComplete: "+temp1[i]);
                }
                //Log.d("sadfsaf", "onComplete: "+map.keySet());
            }
        });
        FirebaseDatabase.getInstance().getReference("like").child(shopNum).child("wholike").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String,String> item = new HashMap<String, String>();
                    String uid = snapshot.getKey();
                    Log.d("asdf", "onDataChange: "+uid);
                    FirebaseDatabase.getInstance().getReference("name").child(uid).child("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = snapshot.getValue(String.class);
                            item.put("item1",name);
                            item.put("item2","");
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