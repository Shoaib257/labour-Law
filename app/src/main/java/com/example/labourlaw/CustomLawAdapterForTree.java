package com.example.labourlaw;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.labourlaw.ui.CustomLawAdapter;
import com.example.labourlaw.ui.NodeModel;

import java.util.ArrayList;

public class CustomLawAdapterForTree extends BaseAdapter {

    private final Activity context;
    private ArrayList<View> views;
    public CustomLawAdapterForTree(Activity context, ArrayList<View> views){
    this.context=context;
    this.views=views;
    }
    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return views.get(position);

    }

}
