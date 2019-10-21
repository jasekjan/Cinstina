package com.edu.cinstina.db;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.edu.cinstina.R;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Category> categoryArrayList;
    private LayoutInflater layoutInflater;

    public CategoryAdapter(Activity context, ArrayList<Category> arrayList){
        this.context = context;
        this.categoryArrayList = arrayList;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categoryArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder mvh;
        View v;
        v = layoutInflater.inflate(R.layout.row_categories_layout, null);
        mvh = new MyViewHolder(v);

        mvh.cat.setText(categoryArrayList.get(i).getName());
        mvh.cat_id.setText(String.valueOf(categoryArrayList.get(i).getId()));

        return v;
    }

    class MyViewHolder {
        public TextView cat;
        public TextView cat_id;

        public MyViewHolder(View view) {
            cat = (TextView)view.findViewById(R.id.tv_cat_name);
            cat_id = (TextView)view.findViewById(R.id.tv_cat_id);
        }



    }

    public void reloadData(ArrayList<Category> al) {
        categoryArrayList.clear();
        categoryArrayList.addAll(al);
        this.notifyDataSetChanged();
    }
}
