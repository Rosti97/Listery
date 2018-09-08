package e.rosti.listery;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;


public class BilanzFragment extends Fragment {

    private MateViewModel mMateViewModel;

    private TextView textGesamt;
    private TextView betragGesamt;
    private ListView listView;
    private BilanzAdapter adapter;

    private EditText et;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.fragment_bilanz, container, false);

        setUpView(v);

        mMateViewModel = ViewModelProviders.of(this).get(MateViewModel.class);
        mMateViewModel.excludeYourself();

        mMateViewModel.getmCurrentMate().observe(this, new Observer<List<Mate>>() {
            @Override
            public void onChanged(@Nullable List<Mate> mates) {
                adapter.addItem(mates);
            }
        });

        mMateViewModel.getCompleteBalance().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float aFloat) {
                betragGesamt.setText(aFloat.toString()+"€");
            }
        });

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
                                Mate[] mates = {selectedMate};
                                mMateViewModel.updateMate(mates);
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

    private void editBalance(final Mate selectedMate) {
        AlertDialog.Builder ab  = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        ab.setView(inflater.inflate(R.layout.layout_bilanz_bearbeiten, null));

        ab.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog d = (Dialog) dialog;
                et = (EditText) d.findViewById(R.id.edittext_editbalance);
                float edit = Float.parseFloat(et.getText().toString()); //muss wsl Int oder Double werden
                selectedMate.setBalance(edit);
                Mate[] mates = {selectedMate};
                mMateViewModel.updateMate(mates);
            }
        });

        ab.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog mbD = ab.create();
        mbD.show();
    }

    private void payment(final Mate selectedMate) {
        AlertDialog.Builder ab  = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        ab.setView(inflater.inflate(R.layout.layout_bilanz_tilgen, null));

        ab.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog d = (Dialog) dialog;
                et = (EditText) d.findViewById(R.id.edittext_payment);
                float edit = Float.parseFloat(et.getText().toString()); //muss wsl Int oder Double werden
                if(edit>=selectedMate.getBalance()){
                    selectedMate.setBalance(0);
                }
                else{
                    selectedMate.setBalance(selectedMate.getBalance()-edit);
                }
                Mate[] mates = {selectedMate};
                mMateViewModel.updateMate(mates);
            }
        });

        ab.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog mbD = ab.create();
        mbD.show();
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
        getActivity().setTitle(R.string.fragment_title_bilanz);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bilanz, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
