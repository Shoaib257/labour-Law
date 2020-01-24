package com.example.labourlaw.ui;

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

public class CustomLawAdapter extends BaseAdapter {
    private final Activity context;
    private ArrayList<String> laws=new ArrayList<String>();
    private ArrayList<String> no=new ArrayList<String>();

    public CustomLawAdapter(Activity context, ArrayList<String> laws, ArrayList<String> no) {

        this.context = context;
        this.laws = laws;
        this.no = no;
    }

    @Override
    public int getCount() {
        return no.size();
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
        View view;
        LayoutInflater inflater=context.getLayoutInflater();
        if(position==0){
            view=inflater.inflate(R.layout.customlist_for_rule_level_1,null,true);

        }else if(position==1){
            view=inflater.inflate(R.layout.customlist_for_rule_level_2,null,true);
/*            //LinearLayout layout=view.findViewById(R.id.topLayout);
            //layout.setLeft(8);
            //ViewGroup.LayoutParams params= layout.getLayoutParams();
           // params.
            //params.setMargins(8,0,0,0);

            //LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(view.getLayoutParams());
            //params.setMargins(8,0,0,0);

            //view.setPadding(8,0,0,0);
                //p.setMargins(8, 0, 0, 0);
                //view.requestLayout();


            //view.setLayoutParams(new ViewGroup.LayoutParams());
            //view.setLeft();*/
        }else{
            view=inflater.inflate(R.layout.customlist_for_rule_level_3,null,true);
        }

        TextView no=(TextView)view.findViewById(R.id.lawId);
        TextView lawData=(TextView)view.findViewById(R.id.lawData);
        no.setText(this.no.get(position));
        lawData.setText(this.laws.get(position));
        return view;
    }
}

