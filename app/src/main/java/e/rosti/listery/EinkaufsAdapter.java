package e.rosti.listery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class EinkaufsAdapter extends ArrayAdapter<Einkaufsitem> {

    private ArrayList<Einkaufsitem> einkaufSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView productName;
        TextView mbAnzeige;
        View divider_eintrag;
        CheckBox checkBox;
    }

    public EinkaufsAdapter(ArrayList<Einkaufsitem> data, Context context) {
        super(context, R.layout.layout_einkaufsliste_eintrag, data);
        this.einkaufSet = data;
        this.mContext = context;

    }
    @Override
    public int getCount() {
        return einkaufSet.size();
    }

    @Override
    public Einkaufsitem getItem(int position) {
        return einkaufSet.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_einkaufsliste_eintrag, parent, false);
            viewHolder.productName = (TextView) convertView.findViewById(R.id.textview_produkt_einkaufsliste);
            viewHolder.mbAnzeige = (TextView) convertView.findViewById(R.id.textview_einkaufsliste_mb);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_einkaufsliste);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Einkaufsitem item = getItem(position);


        viewHolder.productName.setText(item.getProduct());
        viewHolder.checkBox.setChecked(item.isChecked());


        return result;
    }
}