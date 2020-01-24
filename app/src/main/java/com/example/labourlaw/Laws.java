package com.example.labourlaw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.labourlaw.ui.CustomLawAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class Laws extends AppCompatActivity {


    String[] no={"1","2","3"};
    String[] laws={};
    ArrayList<String> lw=new ArrayList<String>();
    ArrayList<String> nm=new ArrayList<String>();
    CustomLawAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laws);
        getSupportActionBar().hide();
        listView=findViewById(R.id.lawsListView);
        nm.add("1");
        nm.add("2");
        nm.add("3");

        //rule,subrule,subsubbrule
        lw.add("ধারা ৭৯ এর উদ্দেশ্য পূরণকল্পে নিম্নবর্ণিত কাজসমূহ বিপজ্জনক কাজ বলিয়া বিবেচিত হইবে, যথা:-");
        lw.add("মহাপরিদর্শক প্রয়োজনে উপ-বিধি (১) এ বর্ণিত কাজ ব্যতীত অন্যান্য উৎপাদন প্রক্রিয়া বা উক্ত প্রক্রিয়ার আনুষঙ্গিক প্রক্রিয়াকে বিপজ্জনক কাজ হিসাবে ঘোষণা করিতে পারিবেন।");
        lw.add("");

        adapter=new CustomLawAdapter(this,lw,nm);
        listView.setAdapter(adapter);
    }

    //manually developed tree will be returned from this method
    TreeDataStructureForLaw constructTreeFromFireBase(){
        ArrayList<Object> samples=new ArrayList<>();
        samples.add(new LawDataSample("1","ধারা ৭৯ এর উদ্দেশ্য পূরণকল্পে নিম্নবর্ণিত কাজসমূহ বিপজ্জনক কাজ বলিয়া বিবেচিত হইবে, যথা:-","i love this",true));
        samples.add(new LawDataSample("2","মহাপরিদর্শক প্রয়োজনে উপ-বিধি (১) এ বর্ণিত কাজ ব্যতীত অন্যান্য উৎপাদন প্রক্রিয়া বা উক্ত প্রক্রিয়ার আনুষঙ্গিক প্রক্রিয়াকে বিপজ্জনক কাজ হিসাবে ঘোষণা করিতে পারিবেন।","",false));
        samples.add(new LawDataSample("3","উপ-বিধি (১) এ উলিস্নখিত তালিকা ও উপ-বিধি (২) এর অধীন মহাপরিদর্শক বা তৎকর্তৃক ক্ষমতাপ্রাপ্ত পরিদর্শক কর্তৃক, সময়ে সময়ে, ঘোষিত বিপজ্জনক কাজের সকল ক্ষেত্রে শিশু ও কিশোর শ্রমিকদের নিয়োগ নিষিদ্ধ থাকিবে এবং উক্ত প্রক্রিয়াসমূহের মধ্যে যে কাজ মহিলা বা সন্ত্মান-সম্ভবা মহিলাদের জন্য নিষিদ্ধ উহা মহাপরিদর্শক, সময়ে সময়ে, নোটিস দ্বারা ঘোষণা করিতে পারিবেন।","too long",false));
        LawDataSample root= new LawDataSample("3","উপ-বিধি (১) এ উলিস্নখিত তালিকা ও উপ-বিধি (২) এর অধীন মহাপরিদর্শক বা তৎকর্তৃক ক্ষমতাপ্রাপ্ত পরিদর্শক কর্তৃক, সময়ে সময়ে, ঘোষিত বিপজ্জনক কাজের সকল ক্ষেত্রে শিশু ও কিশোর শ্রমিকদের নিয়োগ নিষিদ্ধ থাকিবে এবং উক্ত প্রক্রিয়াসমূহের মধ্যে যে কাজ মহিলা বা সন্ত্মান-সম্ভবা মহিলাদের জন্য নিষিদ্ধ উহা মহাপরিদর্শক, সময়ে সময়ে, নোটিস দ্বারা ঘোষণা করিতে পারিবেন।","too long",false);
        TreeDataStructureForLaw tree=new TreeDataStructureForLaw();
        tree.addRootNode(root).addListOfChild(samples);
        return tree;

    }
}
