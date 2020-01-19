package com.example.labourlaw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static java.security.AccessController.getContext;


public class Sections extends AppCompatActivity {
    String[] no={"১","২","৩"};
    String[] laws={"অধ্যায় ১","অধ্যায় ২","অধ্যায় ৩"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);
        getSupportActionBar().hide();
        Toolbar toolbar=findViewById(R.id.toolbar);
        TextView toolbarText=findViewById(R.id.sectionName);
        toolbarText.setText("অধ্যায় ১");

        //initialize the list with custom adapter with section names
        ListView listView=findViewById(R.id.sectionListView);
        CustomSectionAdapter customAdapter=new CustomSectionAdapter(this,laws,no);
        listView.setAdapter(customAdapter);


    }
}
