package com.example.labourlaw.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.labourlaw.DatabaseReferencesStorage;
import com.example.labourlaw.MainActivity;
import com.example.labourlaw.R;
import com.example.labourlaw.Sections;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Labour Law");
    //DatabaseReference ref=db.child("Labour Law");

    CustomAdapter customAdapter;
    //String[] bnNumbers={"০","১","২","৩","৪","৫","৬","৭","৮","৯","১০","১১","১২","১৩","১৪","১৫","১৬","১৭","১৮","১৯","২০","২১","২২","২৩","২৪","২৫","২৬","২৭","২৮","২৯","৩০","৩১","৩২","৩৩"};
    //String[] no={"১","২","৩"};
    //String[] laws={"অধ্যায় ১","অধ্যায় ২","অধ্যায় ৩"};
    ArrayList<String> no=new ArrayList<String>();
    ArrayList<String> chapters=new ArrayList<String>();
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        final ListView listView= view.findViewById(R.id.chapterListView);
        customAdapter=new CustomAdapter((Activity) getContext(),chapters,no);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(),Sections.class);
                TextView t=view.findViewById(R.id.chapterName);
                intent.putExtra("chapterName", t.getText());
                intent.putExtra("dbRefPos", position);
                startActivity(intent);
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                chapters.clear();
                no.clear();
                DatabaseReferencesStorage.sectionsRefs.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    DatabaseReferencesStorage.sectionsRefs.add(snapshot.child("Sections").getRef());

                    chapters.add(snapshot.child("Name").getValue().toString());
                    no.add(snapshot.getKey().replace("অধ্যায় ",""));
                    //no.add(bnNumbers[no.size()+1]);
                    customAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });




        return view;
    }
}