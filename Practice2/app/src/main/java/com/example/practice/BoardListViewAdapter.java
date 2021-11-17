package com.example.practice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BoardListViewAdapter extends BaseAdapter {

    public ArrayList<BoardListViewItem> listViewItemList = new ArrayList<BoardListViewItem>();


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

        TextView Title;
        TextView Content;
        TextView Name;
        TextView like;
        TextView Comment1;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.board_listview_custom, viewGroup, false);
        }

        Title = (TextView) view.findViewById(R.id.Title);
        Content = (TextView) view.findViewById(R.id.Content);
        Name = (TextView) view.findViewById(R.id.Name);
        like = (TextView) view.findViewById(R.id.like);
        Comment1 = (TextView) view.findViewById(R.id.comment1234);

        BoardListViewItem listViewItem = listViewItemList.get(i);

        Title.setText(listViewItem.getTitle());
        Content.setText(listViewItem.getContent());
        Name.setText(listViewItem.getName());
        like.setText(listViewItem.getLike());
        Comment1.setText(listViewItem.getComment());

        return view;
    }

    public void addItem(String Title, String Content, String Name, String Time, String Like, String key, String Comment){
        BoardListViewItem item = new BoardListViewItem();
        item.setTitle(Title);
        item.setContent(Content);
        item.setName(Name);
        item.setTime(Time);
        item.setKey(key);
        item.setLike(Like);
        item.setComment(Comment);
        listViewItemList.add(item);
    }

    public void clearItem(){
        listViewItemList.clear();
    }
}