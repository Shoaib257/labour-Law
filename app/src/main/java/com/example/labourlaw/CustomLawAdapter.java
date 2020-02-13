package com.example.labourlaw;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class CustomLawAdapter extends BaseAdapter {

    //this adapter receives a list of created views from another class and will display them using getView.
    private  Activity context;
    private ArrayList<View> views;
    public CustomLawAdapter(Activity context, ArrayList<View> views){
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
        final View view=views.get(position);
        final LinearLayout   moreOption=view.findViewById(R.id.moreButtonUX);
        //final LinearLayout layout=view.findViewById(R.id.linearLayout);
        moreOption.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"More clicked",Toast.LENGTH_SHORT).show();
                Context con=new ContextThemeWrapper(context,R.style.popupStyle);
                final PopupMenu popupMenu=new PopupMenu(con,moreOption);
                popupMenu.getMenuInflater().inflate(R.menu.listmenu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popupMenu.show();


            }
        });
        return view;

    }

}
