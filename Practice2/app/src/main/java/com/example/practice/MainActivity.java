package com.example.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        /*
        mDatabase.child("춘천시").child("1").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {

                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    int a;
                }
            }
        });*/
        mDatabase.child("춘천시").child("23").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    //MyFiles filename = (MyFiles) fileSnapshot.getValue(MyFiles.class);
                    //하위키들의 value를 어떻게 가져오느냐???
                    String str = fileSnapshot.child("address").getValue(String.class);
                    String name = fileSnapshot.child("name").getValue(String.class);
                    String sector = fileSnapshot.child("sector").getValue(String.class);
                    if(sector.equals("슈퍼/편의점"))
                        Log.i("TAG: value is ", name + " : " +str);

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
        /*
        mDatabase.child("춘천시").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.getValue(User.class) != null){
                    User post = dataSnapshot.getValue(User.class);
                    Log.w("FireBaseData", "getData" + post.toString());
                } else {
                    Toast.makeText(MainActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
        */

        myRef.setValue("Hello, World!");
    }
    // 자기 것만 건드리세요!!
    // 버튼을 눌렀을 때 toast를 이용해 학번과 이름이 뜨게 하기
    public void jeonghyeop(View view) {
        Toast.makeText(getApplicationContext(), "정협정협", Toast.LENGTH_LONG).show();
    }

    public void seungmin(View view) {
        Toast.makeText(getApplicationContext(), "201714225 이승민", Toast.LENGTH_LONG).show();
    }

    public void yisak(View view) {
        Toast.makeText(getApplicationContext(), "이삭 ㅎㅇㅎㅇ", Toast.LENGTH_LONG).show();
    }

    public void subin(View view) {
        Toast.makeText(getApplicationContext(), "공주 등장", Toast.LENGTH_LONG).show();
    }

    public void bongkyu(View view) {
        Toast.makeText(getApplicationContext(), "춘천의 자랑 홍이삭 춘천의 자랑 홍이삭 춘천의 자랑 홍이삭 춘천의 자랑 홍이삭", Toast.LENGTH_LONG).show();
    }

    public void woojin(View view) {
        Toast.makeText(getApplicationContext(), "응애 커밋해줘", Toast.LENGTH_LONG).show();
    }

    public void laewon(View view) {
        Toast.makeText(getApplicationContext(),"201714198 정래원", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        startActivity(intent);
    }
}