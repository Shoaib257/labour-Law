package com.example.labourlaw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.labourlaw.ui.NodeModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Laws extends AppCompatActivity {
    CustomLawAdapter adapter;
    ListView listView;
    TreeDataStructure tree=new TreeDataStructure();
    ArrayList<View> views=new ArrayList<>();  //may data removed from here
    ArrayList<View> rawViews=new ArrayList<>();  //stores all view of that section
    DatabaseReference ref;
    Bundle bundle;
    String sectionName;
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
        adapter=new CustomLawAdapter(Laws.this, views);
        listView.setAdapter(adapter);
        //adding listner of dataChange in firebase
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //(tree)only contain clicked section laws and its childs and childs of childs.
                //created tree will be converted into List of view using the method
                //constructArralistOfViewFromTree(Laws.this,views,tree) which takes context,reference of viewHolder(Arraylist<View> and constructed tree as param)
                tree.clearAll();
                LawDataSample data=new LawDataSample("0","Section","Root node of this tree",true);
                TreeDataStructure.Node rootNode=tree.addRootNode(data);  //root node added

                //add childs of the root node
               for(DataSnapshot level1:dataSnapshot.getChildren()){

                   //NOde start
                   String id=level1.getKey();

                   String ruleName=level1.child("Name").getValue().toString();
                   Log.w(ruleName,id);
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
                rawViews.clear();
                rawViews=views;
               adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


        adapter.notifyDataSetChanged();
    }



    //tree to view converter. this method is specific to this project

    /**
     * How this method works.
     * Input: tree with three level nodes.
     * First level: RootNode(Section)  i.e. only one root node
     * Second Level:SubNodes(SubLaws)  i.e. it may contain anynumber of node
     * Third Level: SubSubNodes(subsubLaws)  i.e. multiple node
     *
     * We have three trackers: ruleQueue,subRuleQueue,subsubRuleQueue
     * Step 1:
     * ruleQueue: its gets loaded with children of RootNode when the method gets called. Its first procedure of this converting process
     * then we start processing the first element of the ruleQueue to generate a view and store in list. After generating we check if it has any childrenn.
     * if it doesnt have any children, it will remove the first element from ruleQueue and will start from Step 1:
     * else it will load all children in the subRuleQueue. then generate a view and store it. If it has no child then
     *
     * Step 2:
     * remove the first element of the
     * subRuleQueue and start processing next. else it will load the children of subRuleQueue in the subsubRuleQueue and start processing the first element
     * to generate new view and store it.
     * Because we have no only three level, so we will remove the first element of subsubRuleQueue and start processing next and so on.
     * If subsubRuleQueue is empty then step 2. And follow the same procedure.
     * @param context
     * @param addViewTo
     * @param tree
     */
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
            if(ruleQueue.getFirst().tag==NodeModel.PROCESSING){ //its processing because it has subRule
                if(subRuleQueue.getFirst().tag==NodeModel.PROCESSING){  //its processing because it has subsubrule
                    View view=inflater.inflate(R.layout.customlist_for_rule_level_3,null,true);
                    LawDataSample level3=(LawDataSample)subsubRuleQueue.getFirst().node.getData();
                    TextView txt=view.findViewById(R.id.lawId);
                    txt.setText(level3.getId());
                    TextView data=view.findViewById(R.id.lawData);
                    data.setText(level3.getLawData());
                    subsubRuleQueue.getFirstAndPop();

                    //maintaining flow of tree to view chosing mechanism.
                    if(subsubRuleQueue.isEmpty()){  //subsubRuleQueue will be empty if there is no more to add as view.
                        subRuleQueue.getFirstAndPop(); //if subsubRuleQueue is empty that means processing element of subRuleQueue must be removed
                        if(subRuleQueue.isEmpty()){   //after removing subRule, it subRuleQueue is also empty that means processing element of ruleQueue must also be removed
                            ruleQueue.getFirstAndPop();
                        }
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
}
