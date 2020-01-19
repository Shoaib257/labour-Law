package com.example.labourlaw.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.labourlaw.MainActivity;
import com.example.labourlaw.R;
import com.example.labourlaw.Sections;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    String[] no={"১","২","৩"};
    String[] laws={"অধ্যায় ১","অধ্যায় ২","অধ্যায় ৩"};
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        ListView listView= view.findViewById(R.id.chapterListView);
        CustomAdapter customAdapter=new CustomAdapter((Activity) getContext(),laws,no);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(getContext(), Sections.class));
            }
        });
        return view;
    }
}