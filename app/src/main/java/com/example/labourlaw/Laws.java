package com.example.labourlaw;

import androidx.annotation.NonNull;
import androidx.annotation.TransitionRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.labourlaw.ui.CustomLawAdapter;
import com.example.labourlaw.ui.NodeModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Laws extends AppCompatActivity {
    ArrayList<String> law =new ArrayList<String>();
    ArrayList<String> number =new ArrayList<String>();
    CustomLawAdapterForTree adapter;
    ListView listView;
    TreeDataStructure tree=new TreeDataStructure();
    ArrayList<View> views=new ArrayList<>();
    DatabaseReference ref;
    Bundle bundle;
    String sectionName;
    int dbRefPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bundle=getIntent().getExtras();
        sectionName=bundle.getString("sectionName");
        ref=DatabaseReferencesStorage.ruleRefs.get(bundle.getInt("dbRefPos"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laws);
        getSupportActionBar().hide();
        TextView toolbarText=findViewById(R.id.sectionName);
        toolbarText.setText(sectionName);

        listView=findViewById(R.id.lawsListView);
        adapter=new CustomLawAdapterForTree(Laws.this, views);
        listView.setAdapter(adapter);
        //adapter=new CustomLawAdapter(this, tree);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //(tree)only contain clicked section laws and its childs and childs of childs.
                tree.clearAll();
                //DatabaseReferencesStorage.ruleRefs.clear();
                LawDataSample data=new LawDataSample("0","Section","Root node of this tree",true);
                TreeDataStructure.Node rootNode=tree.addRootNode(data);  //root node added
                Log.w("Data found","onDataChanged called");
                //add childs of the root node
               for(DataSnapshot level1:dataSnapshot.getChildren()){

                   //NOde start
                   String id=level1.getKey();

                   String ruleName=level1.child("Name").getValue().toString();
                   String note="I got a note";
                   boolean fav=false;
                   LawDataSample child=new LawDataSample(id,ruleName,note,fav);  //node created
                   //Node end
                   TreeDataStructure.Node leaf=rootNode.addChild(child);
                   if(level1.hasChild("subrules")){
                       Log.w("Found","subrules exists");
                        for(DataSnapshot level2:level1.child("subrules").getChildren()){
                            String id2=level2.getKey();
                            String subRule=level2.child("Name").getValue().toString();
                            String note2="sub rule note";
                            boolean fav2=true;
                            LawDataSample childLevel2=new LawDataSample(id2,subRule,note2,fav2);
                            TreeDataStructure.Node leaf2=leaf.addChild(childLevel2);
                            if(level2.hasChild("subsubrules")){
                                for(DataSnapshot level3:level2.child("subsubrules").getChildren()){
                                    String id3=level3.getKey();
                                    String subsubRule=level3.child("Name").getValue().toString();
                                    String note3="sub sub rule note";
                                    boolean fav3=true;
                                    LawDataSample childLevel3=new LawDataSample(id3,subsubRule,note3,fav3);
                                    leaf2.addChild(childLevel3);
                                }
                            }
                       }
                   }



               }
               constructArralistOfViewFromTree(Laws.this,views,tree);

               //int childrenCount=tree.getRootNode().getChildrenCount();

               adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


        adapter.notifyDataSetChanged();
    }

    private void constructArralistOfViewFromTree(Activity context,ArrayList<View> addViewTo, TreeDataStructure tree) {
        LayoutInflater inflater=context.getLayoutInflater();
        int totalNode=tree.getTotalNodesofParent();
        addViewTo.clear();

        //trackers of 3 level data
        QueueHridoy<NodeModel> ruleQueue=new QueueHridoy<NodeModel>();
        QueueHridoy<NodeModel> subRuleQueue=new QueueHridoy<NodeModel>();
        QueueHridoy<NodeModel> subsubRuleQueue=new QueueHridoy<NodeModel>();

        //initializing rule nodes
        for(TreeDataStructure.Node node:tree.getRootNode().getChildren()){
            NodeModel model=new NodeModel();
            model.node=node;
            model.childCount=tree.getChildrenCount(node);
            model.tag=NodeModel.PENDING;
            ruleQueue.insertNew(model);
        }
        //add rules with their correnponding sub and subsub rule with the arraylist as view
        for(int i=0;i<totalNode;i++){
            if(ruleQueue.getFirst().tag==NodeModel.PROCESSING){
                if(subRuleQueue.getFirst().tag==NodeModel.PROCESSING){
                    View view=inflater.inflate(R.layout.customlist_for_rule_level_3,null,true);
                    LawDataSample level3=(LawDataSample)subsubRuleQueue.getFirst().node.getData();
                    TextView txt=view.findViewById(R.id.lawId);
                    txt.setText(level3.getId());
                    TextView data=view.findViewById(R.id.lawData);
                    data.setText(level3.getLawData());
                    subsubRuleQueue.getFirstAndPop();

                    if(subsubRuleQueue.isEmpty()){
                        subRuleQueue.getFirstAndPop();
                        if(subRuleQueue.isEmpty()){
                            ruleQueue.getFirstAndPop();
                        }
                        //ruleQueue.getFirstAndPop();

                    }

                    //problem occured here. problem occuring while trying to change rule on ruleQueue
                    addViewTo.add(view);
                    continue;

                }else if(subRuleQueue.getFirst().tag==NodeModel.PENDING){
                    //develop row with subRule data.
                    View view=inflater.inflate(R.layout.customlist_for_rule_level_2,null,true);
                    LawDataSample level2=(LawDataSample)subRuleQueue.getFirst().node.getData();
                    TextView txt=view.findViewById(R.id.lawId);
                    txt.setText(level2.getId());
                    TextView data=view.findViewById(R.id.lawData);
                    data.setText(level2.getLawData());
                    //data developed

                    if(subRuleQueue.getFirst().childCount!=0){   //check if the subrulenode haschild
                        for(TreeDataStructure.Node node:tree.getChildren(subRuleQueue.getFirst().node)){
                            //construct subsubrule queue
                            NodeModel model=new NodeModel();
                            model.node=node;
                            model.childCount=tree.getChildrenCount(node);
                            model.tag=NodeModel.PENDING;
                            subsubRuleQueue.insertNew(model);
                        }
                        //set the subrule as processing rule
                        subRuleQueue.getFirst().tag=NodeModel.PROCESSING;
                    }else{
                        //subRule has no child so remove it.
                        subRuleQueue.getFirstAndPop();
                        if(subRuleQueue.isEmpty()){
                            ruleQueue.getFirstAndPop();
                        }
                    }
                    addViewTo.add(view);
                    continue;
                }

            }else if(ruleQueue.getFirst().tag==NodeModel.PENDING){
/*
            if ruleQueue has child then set is as processing otherwise
            remove the rule from the queue after returning the queue row
*/

                //develop row with rule data.
                View view=inflater.inflate(R.layout.customlist_for_rule_level_1,null,true);
                LawDataSample level1=(LawDataSample)ruleQueue.getFirst().node.getData();
                TextView txt=view.findViewById(R.id.lawId);
                txt.setText(level1.getId());
                TextView data=view.findViewById(R.id.lawData);
                data.setText(level1.getLawData());
                //data developed
                if(ruleQueue.getFirst().childCount!=0){   //check if the rulenode haschild
                    for(TreeDataStructure.Node subRuleNode:tree.getChildren(ruleQueue.getFirst().node)){
                        //construct subrule queue
                        NodeModel model=new NodeModel();
                        model.node=subRuleNode;
                        model.childCount=tree.getChildrenCount(subRuleNode);
                        model.tag=NodeModel.PENDING;
                        subRuleQueue.insertNew(model);
                    }
                    //set the rule as processing rule
                    ruleQueue.getFirst().tag=NodeModel.PROCESSING;

                }else{
                    //rulenode has no child so remove it.
                    ruleQueue.getFirstAndPop();

                }

                addViewTo.add(view);
                continue;

            }
        }

    }

    //manually developed tree will be returned from this method
}
