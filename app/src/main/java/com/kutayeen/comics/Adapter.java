package com.kutayeen.comics;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter{

    ArrayList<Item> bookList = new ArrayList<Item>();

    public Adapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        bookList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.grid_view_items, null);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        textView.setText(bookList.get(position).getbookName().substring(0,bookList.get(position).getbookName().length()-4));
        imageView.setImageBitmap(bookList.get(position).getbookimg());
        return v;

    }

}