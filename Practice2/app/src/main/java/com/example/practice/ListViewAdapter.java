package com.example.practice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.practice.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    public ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();


    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        final Context context = viewGroup.getContext();
        ImageView iconImageView;
        TextView titleTextView;
        TextView facNameTextView;
        TextView address;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_custom, viewGroup, false);
        }

        titleTextView = (TextView) view.findViewById(R.id.textViewCenterame);
        facNameTextView = (TextView) view.findViewById(R.id.Likecount);
        iconImageView = (ImageView) view.findViewById(R.id.iconImage);
        address = (TextView) view.findViewById(R.id.textViewaddress);

        ListViewItem listViewItem = listViewItemList.get(i);

        titleTextView.setText(listViewItem.getCenterNameStr());
        iconImageView.setImageResource(listViewItem.getIcon());
        facNameTextView.setText(listViewItem.getLike());
        address.setText(listViewItem.getAddressStr());

        return view;
    }

    public void addItem(int icon, String shopName, String like, String address, String key){
        ListViewItem item = new ListViewItem();
        item.setIcon(R.drawable.chuncheon);
        item.setCenterName(shopName);
        item.setLike(like);
        item.setAddress(address);
        item.setKey(key);
        listViewItemList.add(item);
    }

    public void clearItem(){
        listViewItemList.clear();
    }
}