package e.rosti.listery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class BilanzFragment extends Fragment {

    private TextView textGesamt;
    private TextView betragGesamt;
    private ListView mbListe;
    private ArrayList<Mitbewohner> listeMbArray;
   // private MitbewohnerAdapter mbAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.fragment_bilanz, container, false);

        setUpView(v);
//        addMB();

        return v;



    }

    private void setUpView( View v ){
        textGesamt = (TextView) v.findViewById(R.id.Gesamtbetrag);
        betragGesamt = (TextView) v.findViewById(R.id.bilanz_gesamt_euro);
        mbListe = (ListView) v.findViewById(R.id.lv_mb_bilanz);
    }


    /*private void addMB(){
        listeMbArray = new ArrayList<>();

        mbAdapter = new MitbewohnerAdapter(listeMbArray, this.getContext());
        mbListe.setAdapter(mbAdapter);
    }*/


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Bilanz");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bilanz, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
