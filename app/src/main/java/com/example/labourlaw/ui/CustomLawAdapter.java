package com.example.labourlaw.ui;

        import android.app.Activity;
        import android.graphics.Color;
        import android.provider.ContactsContract;
        import android.text.Layout;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;

        import com.example.labourlaw.LawDataSample;
        import com.example.labourlaw.QueueHridoy;
        import com.example.labourlaw.R;
        import com.example.labourlaw.TreeDataStructure;

        import org.w3c.dom.Text;
        import java.util.ArrayList;
        import java.util.Stack;

public class CustomLawAdapter extends BaseAdapter {

    boolean proceedBasics=true;
    private final Activity context;
    private ArrayList<String> laws=new ArrayList<String>();
    private ArrayList<String> no=new ArrayList<String>();
    int callsCount=0;
    TreeDataStructure tree;
    QueueHridoy<NodeModel> ruleQueue=new QueueHridoy<NodeModel>();
    QueueHridoy<NodeModel> subRuleQueue=new QueueHridoy<NodeModel>();
    QueueHridoy<NodeModel> subsubRuleQueue=new QueueHridoy<NodeModel>();
    int totalCall=0;
    public CustomLawAdapter(Activity context, TreeDataStructure tree) {
        this.tree=tree;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return tree.getTotalNodesofParent();
    }

    @Override
    public Object getItem(int positsion) {
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
        View view=null;


        if(proceedBasics){
            Log.w("RuleInserting","Inserting rules into queue");
            ruleQueue.clear();
            subRuleQueue.clear();
            subsubRuleQueue.clear();
            String data="";
            for(TreeDataStructure.Node node:tree.getRootNode().getChildren()){

                NodeModel model=new NodeModel();
                model.node=node;
                model.childCount=tree.getChildrenCount(node);
                model.tag=NodeModel.PENDING;
                ruleQueue.insertNew(model);
                data+=" Pending";
            }
            proceedBasics=false;
            Log.w("Inserted",data);
        }
        /*callsCount++;
        if(callsCount==totalCall+1){

            proceedBasics=true;
        }*/

        if(ruleQueue.getFirst().tag==NodeModel.PROCESSING){
            if(subRuleQueue.getFirst().tag==NodeModel.PROCESSING){
                    view=inflater.inflate(R.layout.customlist_for_rule_level_3,null,true);
                    LawDataSample level3=(LawDataSample)subsubRuleQueue.getFirst().node.getData();
                    TextView txt=view.findViewById(R.id.lawId);
                    txt.setText(level3.getId());
                    TextView data=view.findViewById(R.id.lawData);
                    data.setText(level3.getLawData());
                    subsubRuleQueue.getFirstAndPop();

                    if(subsubRuleQueue.isEmpty()){
                        subRuleQueue.getFirstAndPop();
                        ruleQueue.getFirstAndPop();

                    }

                    //problem occured here. problem occuring while trying to change rule on ruleQueue
                    return view;

            }else if(subRuleQueue.getFirst().tag==NodeModel.PENDING){
                //develop row with subRule data.
                view=inflater.inflate(R.layout.customlist_for_rule_level_2,null,true);
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
                return view;
            }

        }else if(ruleQueue.getFirst().tag==NodeModel.PENDING){
/*
            if ruleQueue has child then set is as processing otherwise
            remove the rule from the queue after returning the queue row
*/

            //develop row with rule data.
            view=inflater.inflate(R.layout.customlist_for_rule_level_1,null,true);
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

            return view;

        }


        return view;
    }

}

