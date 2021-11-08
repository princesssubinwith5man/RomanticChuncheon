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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, Shop>> shopList = new ArrayList<>();
    ListView listview;
    static ProgressBar pb;
    String name;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        pb = (ProgressBar) v.findViewById(R.id.progressbar);
        pb.setVisibility(View.INVISIBLE);

        ListViewAdapter adapter = new ListViewAdapter();
        HashMap<String, Shop> shopItem = new HashMap<>();
        listview = (ListView) v.findViewById(R.id.list3);
        SearchView sv = (SearchView)v.findViewById(R.id.search_view);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!s.equals(""))
                    pb.setVisibility(View.VISIBLE);
                    SearchListview(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //durlsms Tmfdlf djqtdmfemt.
                return false;
            }
        });

        Button button = v.findViewById(R.id.search_btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = sv.getQuery().toString();
                //Log.d(TAG, "onClick: "+s);
                if(!s.equals("")) {
                    pb.setVisibility(View.VISIBLE);
                    SearchListview(s);
                }
                else{
                    Toast.makeText(getActivity(), "not enough search", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onClick: not enough search");
                }
            }
        });
        return v;
    }
    public void SearchListview(String s){
        ListViewAdapter adapter = new ListViewAdapter();
        CollectionReference shopRef = db.collection("shop");
        Query query = shopRef
                .orderBy("like", Query.Direction.DESCENDING);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {

                    Shop shop = document.toObject(Shop.class);
                    //Log.i("TAG: value is ", shop.name + shop.sector + " : " + temp);
                    if((shop.name).contains(s)) {
                        adapter.addItem(0, shop.name, Integer.toString(shop.like), shop.address, document.getId());
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
                                Intent intent = new Intent(getActivity(), InformationActivity.class);
                                intent.putExtra("centername", centerName);
                                intent.putExtra("add", address);
                                intent.putExtra("key", key);
                                intent.putExtra("like", like);
                                startActivity(intent);
                            }
                        });
                    }
                }
                pb.setVisibility(View.INVISIBLE);
            }
        });
    }
}