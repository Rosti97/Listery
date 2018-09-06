package e.rosti.listery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BilanzAdapter extends ArrayAdapter<Roommates> {

    private Context context;
    private ArrayList<Roommates> data;

    public BilanzAdapter(ArrayList<Roommates> data, Context context) {
        super(context, R.layout.layout_bilanz_mbeintrag, data);
        this.context = context;
        this.data = data;
    }

    private static class ViewBox{
        TextView tvName;
        TextView tvBilanz;
        ImageView ivDots;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Roommates getItem(int position) {
        return data.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        BilanzAdapter.ViewBox viewBox;
        final View result;

        if (convertView == null) {
            viewBox = new BilanzAdapter.ViewBox();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bilanz_mbeintrag, parent, false);
            viewBox.tvName = (TextView) convertView.findViewById(R.id.name_mb1);
            viewBox.tvBilanz = (TextView) convertView.findViewById(R.id.Betrag_mb1);
            viewBox.ivDots = (ImageView) convertView.findViewById(R.id.bilanz_dots);


            result=convertView;
            convertView.setTag(viewBox);

        } else {
            viewBox = (BilanzAdapter.ViewBox) convertView.getTag();
            result=convertView;
        }

       Roommates item = getItem(position);


        viewBox.tvName.setText(item.getMateName());
        viewBox.tvBilanz.setText(item.getMateBalance() + "€");

        return result;
    }




}
