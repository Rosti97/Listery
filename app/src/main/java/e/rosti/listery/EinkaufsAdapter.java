package e.rosti.listery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class EinkaufsAdapter extends ArrayAdapter<Einkaufsitem> {

    private final Context context;
    private final ArrayList<Einkaufsitem> itemsArrayList;


    public EinkaufsAdapter(@NonNull Context context, ArrayList<Einkaufsitem> itemsArrayList) {
        super(context, R.layout.layout_einkaufsliste_eintrag, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public int getCount() {
        return itemsArrayList.size();
    }

    @Override
    public Einkaufsitem getItem(int position) {
        return itemsArrayList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = inflater.inflate(R.layout.layout_einkaufsliste_eintrag, null);

            CheckBox checkboxEinkauf = (CheckBox) v.findViewById(R.id.checkbox_einkaufsliste);
            EditText produktEingabe = (EditText) v.findViewById(R.id.edittext_einkaufsliste);
            ImageButton imgButton = (ImageButton) v.findViewById(R.id.imagebutton_einkaufsliste);
            TextView mbAuswahl = (TextView) v.findViewById(R.id.textview_einkaufsliste);

            Einkaufsitem item = getItem(position);

            checkboxEinkauf.setChecked(item.isChecked());
            produktEingabe.setText(produktEingabe.getText());
            mbAuswahl.setText(produktEingabe.getText());


        return v;
    }
    
}
