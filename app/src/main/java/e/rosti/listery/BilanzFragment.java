package e.rosti.listery;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class BilanzFragment extends Fragment {

    private MateViewModel mMateViewModel;

    private TextView textGesamt;
    private TextView betragGesamt;
    private ListView listView;
    private BilanzAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.fragment_bilanz, container, false);

        mMateViewModel = ViewModelProviders.of(this).get(MateViewModel.class);
        mMateViewModel.excludeYourself();

        mMateViewModel.getmCurrentMate().observe(this, new Observer<List<Mate>>() {
            @Override
            public void onChanged(@Nullable List<Mate> mates) {
                adapter.addItem(mates);
            }
        });

        setUpView(v);

        initListView();

        return v;
    }

    private void initListView() {
        adapter = new BilanzAdapter(new ArrayList<Mate>(), this.getContext());

        listView.setAdapter(adapter);

        /**Dialog wird angezeigt (Komplettzahlung/Teilzahlung/Bearbeiten)**/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Mate selectedMate = adapter.getItem(position);

                AlertDialog.Builder ab  = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                ab.setTitle(R.string.title_onclick);

                ab.setItems(R.array.bilanz_menu, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                selectedMate.setBalance(0);
                                break;
                            case 1:
                                payment(selectedMate);
                                break;
                            case 2:
                                editBalance(selectedMate);
                                break;
                        }
                    }
                });

                AlertDialog mbD = ab.create();
                mbD.show();
            }
        });

    }

    private void editBalance(Mate mate) {
        AlertDialog.Builder abEditBalance = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        abEditBalance.setTitle("Bearbeiten");

        abEditBalance.setView(inflater.inflate(R.layout.layout_bilanz_bearbeiten, null));

        abEditBalance.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog bearbeiten = abEditBalance.create();
        bearbeiten.show();
    }

    private void payment(Mate mate) {
        AlertDialog.Builder abpayment = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        abpayment.setTitle("Tilgen");

        abpayment.setView(inflater.inflate(R.layout.layout_bilanz_tilgen, null));

        abpayment.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog tilgen = abpayment.create();
        tilgen.show();
    }

    private void setUpView( View v ){
        textGesamt = (TextView) v.findViewById(R.id.Gesamtbetrag);
        betragGesamt = (TextView) v.findViewById(R.id.bilanz_gesamt_euro);
        listView = (ListView) v.findViewById(R.id.lv_mb_bilanz);
    }


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
