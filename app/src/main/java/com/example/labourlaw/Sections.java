package com.example.labourlaw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static java.security.AccessController.getContext;


public class Sections extends AppCompatActivity {
    String[] bnNumbers={"০","১","২","৩","৪","৫","৬","৭","৮","৯","১০","১১","১২","১৩","১৪","১৫","১৬","১৭","১৮","১৯","২০","২১","২২","২৩","২৪","২৫","২৬","২৭","২৮","২৯","৩০","৩১"};
    ArrayList<String> no=new ArrayList<String>();
    ArrayList<String> sections=new ArrayList<String>();
    DatabaseReference ref;
    Bundle bundle;
    String chapterName;
    int dbRefPos;
    CustomSectionAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bundle=getIntent().getExtras();
        chapterName=bundle.getString("chapterName");
        ref=DatabaseReferencesStorage.sectionsRefs.get(bundle.getInt("dbRefPos"));
        //doesnt work
        //databaseReference=FirebaseDatabase.getInstance().getReference(dbRef);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);
        getSupportActionBar().hide();
        Toolbar toolbar=findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        TextView toolbarText=findViewById(R.id.sectionName);
        toolbarText.setText(chapterName);

        //initialize the list with custom adapter with section names
        ListView listView=findViewById(R.id.sectionListView);
        customAdapter=new CustomSectionAdapter(this,sections,no);
        listView.setAdapter(customAdapter);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                sections.clear();
                no.clear();
                DatabaseReferencesStorage.ruleRefs.clear();

                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {

                    //reference adding in the list for this section to identify rule references for that section.
                    DatabaseReferencesStorage.ruleRefs.add(snapshot.child("Rules").getRef());
                    no.add(snapshot.getKey());
                    sections.add(snapshot.child("Name").getValue().toString());
                    customAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        customAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),Laws.class);
                TextView t=view.findViewById(R.id.sectionName);
                intent.putExtra("sectionName", t.getText());
                intent.putExtra("dbRefPos", position);
                startActivity(intent);
            }
        });

    }
}
