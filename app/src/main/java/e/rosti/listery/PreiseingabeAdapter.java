package e.rosti.listery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class PreiseingabeAdapter extends ArrayAdapter<Einkaufsitem> {

    private ArrayList<Einkaufsitem> einkaufSet;
    Context mContext;

    private static class ViewBox {
        TextView tvProduct;
        EditText etPreis;
    }

    public PreiseingabeAdapter(ArrayList<Einkaufsitem> data, Context context) {
        super(context, R.layout.layout_preiseingabe_eintrag, data);
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

        final ViewBox viewBox;
        final View result;

        if (convertView == null) {
            viewBox = new ViewBox();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_preiseingabe_eintrag, parent, false);
            viewBox.tvProduct = (TextView) convertView.findViewById(R.id.textview_preiseingabe);
            viewBox.etPreis = (EditText) convertView.findViewById(R.id.edittext_preiseingabe);

            result=convertView;
            convertView.setTag(viewBox);

        } else {
            viewBox = (ViewBox) convertView.getTag();
            result=convertView;
        }

        Einkaufsitem item = getItem(position);

        viewBox.tvProduct.setText(item.getProduct());

        return result;
    }
}
