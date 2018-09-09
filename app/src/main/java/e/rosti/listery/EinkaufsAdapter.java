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
import java.util.List;

public class EinkaufsAdapter extends ArrayAdapter<Item> {

    Context mContext;
    private List<Item> einkaufSet;

    public EinkaufsAdapter(List<Item> data, Context context) {
        super(context, R.layout.layout_einkaufsliste_eintrag, data);
        if (data != null) {
            einkaufSet = data;
        } else {
            einkaufSet = new ArrayList<>();
        }
        mContext = context;
    }

    @Override
    public int getCount() {
        return einkaufSet.size();
    }

    @Override
    public Item getItem(int position) {
        return einkaufSet.get(position);
    }

    public void addItems(List<Item> items) {
        this.einkaufSet = items;
        notifyDataSetChanged();
    }

    @NonNull
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

            result = convertView;
            convertView.setTag(viewBox);

        } else {
            viewBox = (ViewBox) convertView.getTag();
            result = convertView;
        }

        if (!einkaufSet.isEmpty()) {
            Item currentItem = getItem(position);
            List<Mate> allMates = currentItem.getMates();
            String text = allMates.get(0).getName();
            if (allMates.size() > 1) {
                for (int i = 1; i < allMates.size(); i++) {
                    text += ", " + allMates.get(i).getName();
                }
            }
            viewBox.productName.setText(currentItem.getName());
            viewBox.mbAnzeige.setText(text);
            viewBox.checkBox.setChecked(currentItem.isChecked());
        } else {
            viewBox.productName.setText("");
            viewBox.mbAnzeige.setText("");
            viewBox.checkBox.setChecked(false);
        }


        return result;
    }

    private static class ViewBox {
        TextView productName;
        TextView mbAnzeige;
        CheckBox checkBox;
    }
}