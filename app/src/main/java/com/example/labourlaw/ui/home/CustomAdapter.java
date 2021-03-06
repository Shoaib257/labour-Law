package com.example.labourlaw.ui.home;


import android.app.Activity;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.labourlaw.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

class CustomAdapter extends BaseAdapter {
    private final Activity context;
   /* private final String[] laws;
    String[] no;*/
   private ArrayList<String> laws=new ArrayList<String>();
   private ArrayList<String> no=new ArrayList<String>();

    public CustomAdapter(Activity context, ArrayList<String> laws, ArrayList<String> no) {
        this.context = context;
        this.laws = laws;
        this.no = no;
    }

    @Override
    public int getCount() {
        return no.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View view=inflater.inflate(R.layout.customlist_for_chapters,null,true);
        TextView no=(TextView)view.findViewById(R.id.chapterId);
        TextView chapterName=(TextView)view.findViewById(R.id.chapterName);
        no.setText(this.no.get(position));
        chapterName.setText(this.laws.get(position));
        return view;
    }
}
