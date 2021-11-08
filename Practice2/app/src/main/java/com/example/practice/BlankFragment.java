package com.example.practice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth firebaseAuth;
    TextView tv;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
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
        View v = inflater.inflate(R.layout.fragment_blank, container, false);

        // 로그인시 닉네임불러오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String e = user.getUid();
        FirebaseDatabase.getInstance().getReference("name").child(e).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    tv = v.findViewById(R.id.nickname);
                    String nick = snapshot.getValue(String.class) + "님 환영합니다.";
                    tv.setText(nick);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
        // button Event Listener (Super/Pyeoneuijeom)
        LinearLayout super1 = v.findViewById(R.id.super1);
        super1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("sector","슈퍼/편의점");
                startActivity(intent);
            }
        });
        // button Event Listener (GasStation)
        LinearLayout gas = v.findViewById(R.id.gasstation);
        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("sector","주유소");
                startActivity(intent);
            }
        });
        // button Event Listener (domae/somae)
        LinearLayout domae = v.findViewById(R.id.domae);
        domae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("sector","도매 및 소매업");
                startActivity(intent);
            }
        });
        // button Event Listener (food/sookbak)
        LinearLayout food = v.findViewById(R.id.food);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("sector","숙박 및 음식점");
                startActivity(intent);
            }
        });
        // button Event Listener (jaejo-up)
        LinearLayout jaejo = v.findViewById(R.id.jaejo);
        jaejo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("sector","제조업");
                startActivity(intent);;
            }
        });
        // button Event Listener (woonsoo-up)
        LinearLayout woonsoo = v.findViewById(R.id.woonsoo);
        woonsoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("sector","운수업");
                startActivity(intent);;
            }
        });
        // button Event Listener (choolpan)
        LinearLayout choolpan = v.findViewById(R.id.choolpan);
        choolpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("sector","출판 등 정보서비스업");
                startActivity(intent);
            }
        });
        // button Event Listener (health)
        LinearLayout health = v.findViewById(R.id.health);
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("sector","보건, 사회복지 서비스업");
                startActivity(intent);
            }
        });
        // button Event Listener (health)
        LinearLayout sports = v.findViewById(R.id.sports);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("sector","스포츠 등 기타 서비스업");
                startActivity(intent);
            }
        });
        // button Event Listener (health)
        LinearLayout etc = v.findViewById(R.id.etc);
        etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("sector","기타");
                startActivity(intent);
            }
        });
        //logout
        ImageView btn_logout = v.findViewById(R.id.logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }


}