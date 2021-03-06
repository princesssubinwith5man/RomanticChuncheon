package com.example.practice;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.ActionMenuView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends Fragment {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_board, container, false);

        Button button = (Button) v.findViewById(R.id.reg_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),WritingActivity.class);
                startActivity(intent);
            }
        });
        ListView listView = (ListView) v.findViewById(R.id.listView12);
        BoardListViewAdapter adapter = new BoardListViewAdapter();
        //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("content");
        //Query a = mDatabase.orderByChild("like");                                             ????????? ????????? ???????????? ?????????????????? ????????? ??????
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
                    String like = Long.toString(snapshot.child("like").getChildrenCount());
                    //.d("asdf", "onDataChange: " + uid + title + content+"  "+key+"asdfsfd"+comment);
                    FirebaseDatabase.getInstance().getReference("name").child(uid).child("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = snapshot.getValue(String.class);
                            adapter.addItem(title, content, name,time,like, key,comment);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    BoardListViewItem listViewItem = adapter.listViewItemList.get(i);
                                    String title = listViewItem.getTitle();
                                    String name = listViewItem.getName();
                                    String key = listViewItem.getKey();
                                    String content = listViewItem.getContent();
                                    String Time = listViewItem.getTime();

                                    //Toastdd.makeText(getApplicationContext(), "?????? : " + centerName, Toast.LENGTH_LONG).show();
                                    //Log.i("TAG: value is ", centerName + " : " + address);
                                    Intent intent = new Intent(getActivity(), SeeBoardActivity.class);
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }


}