package e.rosti.listery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MitbewohnerAdapter extends ArrayAdapter<Mitbewohner> {

    private final Context context;
    private String[] mitbewohner;


    public MitbewohnerAdapter( Context context, String[] mb){
        super(context,R.layout.layout_mb_eintrag_neu );
        this.mitbewohner = mb;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_mb_eintrag_neu, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.edittext_newMb);
        textView.setText(mitbewohner[position]);

        return rowView;
       // return super.getView(position, convertView, parent);
    }

}
