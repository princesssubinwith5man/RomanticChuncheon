package com.example.practice;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyInformationFragment extends Fragment {
    String name;
    String Uid;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyInformationFragment newInstance(String param1, String param2) {
        MyInformationFragment fragment = new MyInformationFragment();
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
        View v = inflater.inflate(R.layout.fragment_my_information, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String e = user.getUid();
        String id = user.getEmail();
        long signin = user.getMetadata().getLastSignInTimestamp();
        Date date = new Date(signin);
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String last_login = format.format(date);


        TextView tvname = (TextView) v.findViewById(R.id.name22);
        TextView tvid = (TextView) v.findViewById(R.id.id_tv);
        TextView tvlogin = (TextView) v.findViewById(R.id.login_tv);
        FirebaseDatabase.getInstance().getReference("name").child(e).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.getValue(String.class);

                tvname.setText(name);
                tvid.setText(id);
                String login_str = "????????? ????????? ?????? : " + last_login;
                tvlogin.setText(login_str);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TextView mylike_bt = v.findViewById(R.id.mylike_tv);
        mylike_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Uid = user.getUid();
                Intent intent = new Intent(getActivity(), MyListActivity.class);
                intent.putExtra("uid", Uid);
                intent.putExtra("id",0);
                startActivity(intent);
            }
        });
        TextView mypost_bt = v.findViewById(R.id.mypost_tv);
        mypost_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Uid = user.getUid();
                Intent intent = new Intent(getActivity(), MyListActivity.class);
                intent.putExtra("uid", Uid);
                intent.putExtra("id",1);
                startActivity(intent);
            }
        });
        TextView myreview_bt = v.findViewById(R.id.myreview_tv);
        myreview_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Uid = user.getUid();
                Intent intent = new Intent(getActivity(), MyListActivity.class);
                intent.putExtra("uid", Uid);
                intent.putExtra("id",2);
                startActivity(intent);
            }
        });
        return v;
    }
}

