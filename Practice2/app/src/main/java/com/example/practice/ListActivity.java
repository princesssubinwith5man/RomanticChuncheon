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
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import static com.example.practice.R.layout.listview_custom;
public class ListActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, Shop>> shopList = new ArrayList<>();
    FirebaseFirestore db;
    ListView listview;
    static ProgressBar pb;
    String temp;
    static int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        pb = (ProgressBar)findViewById(R.id.progressbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT); }
        Intent intent = getIntent();
        temp = intent.getExtras().getString("sector");

        db = FirebaseFirestore.getInstance();

        listview = (ListView) findViewById(R.id.list);
        GetData();
    }

    public void GetData() {
        ListViewAdapter adapter = new ListViewAdapter();
        HashMap<String, Shop> shopItem = new HashMap<>();

        // like(좋아요) 기준으로 내림차순, temp와 동일한 것만 보이게, 100개만 보여줘
        CollectionReference shopRef = db.collection("shop");
        Query query = shopRef
                .orderBy("like", Query.Direction.DESCENDING)
                .whereEqualTo("sector", temp)
                .limit(100);
        pb.setVisibility(View.VISIBLE);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            // 쿼리 실행 결과 리스트뷰로 보이기
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {

                    Shop shop = document.toObject(Shop.class);
                    shopItem.put(shop.name, shop);

                    Log.i("TAG: value is ", shop.name + shop.sector + " : " + temp);

                    adapter.addItem(0, shop.name,  Integer.toString(shop.like), shop.address, document.getId());

                    cnt++;
                    String a = Integer.toString(cnt);
                    Log.i("TAG: Total Count ", a);
                    shopList.add(shopItem);
                    listview.setAdapter(adapter);
                }
                pb.setVisibility(View.INVISIBLE);
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