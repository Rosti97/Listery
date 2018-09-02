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

    private static class ViewBox {
        TextView productName;
        TextView mbAnzeige;
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

        ViewBox viewBox;
        final View result;

        if (convertView == null) {
            viewBox = new ViewBox();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_einkaufsliste_eintrag, parent, false);
            viewBox.productName = (TextView) convertView.findViewById(R.id.textview_produkt_einkaufsliste);
            viewBox.mbAnzeige = (TextView) convertView.findViewById(R.id.textview_einkaufsliste_mb);
            viewBox.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_einkaufsliste);

            result=convertView;
            convertView.setTag(viewBox);

        } else {
            viewBox = (ViewBox) convertView.getTag();
            result=convertView;
        }

        Einkaufsitem item = getItem(position);


        viewBox.productName.setText(item.getProduct());
        viewBox.mbAnzeige.setText(item.getMitbewohner());
        viewBox.checkBox.setChecked(item.isChecked());


        return result;
    }
}