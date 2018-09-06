package e.rosti.listery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UebersichtsAdapter extends ArrayAdapter<Roommates> {

    private Context context;
    private ArrayList<Roommates> data;

    public UebersichtsAdapter(ArrayList<Roommates> data, Context context) {
        super(context, R.layout.layout_wg_row, data);
        this.context = context;
        this.data = data;
    }

    private static class ViewBox{
        TextView tvName;
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

        UebersichtsAdapter.ViewBox viewBox;
        final View result;

        if (convertView == null) {
            viewBox = new UebersichtsAdapter.ViewBox();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wg_row, parent, false);
            viewBox.tvName = (TextView) convertView.findViewById(R.id.tv_wg_row);
            viewBox.ivDots = (ImageView) convertView.findViewById(R.id.iv_wg_dots);


            result=convertView;
            convertView.setTag(viewBox);

        } else {
            viewBox = (UebersichtsAdapter.ViewBox) convertView.getTag();
            result=convertView;
        }

        Roommates item = getItem(position);

        viewBox.tvName.setText(item.getMateName());

        return result;
    }

}