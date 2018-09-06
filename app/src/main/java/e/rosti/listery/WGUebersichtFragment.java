package e.rosti.listery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WGUebersichtFragment extends Fragment {

    private ListView lvMitbewohner;
    private EditText et;
    private BilanzAdapter adapter;
    private ArrayList<Roommates> listeMb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wg, container, false);
        setHasOptionsMenu(true);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               Toast.makeText(getActivity(), "Fab gedrückt", Toast.LENGTH_SHORT).show();
               showAlertDialogMB();

            }
        });

        adapter = new BilanzAdapter(listeMb, this.getContext());
        lvMitbewohner = (ListView)v.findViewById(R.id.listview_mitbewohner);
        lvMitbewohner.setAdapter(adapter);
        return v;
    }


    private void showAlertDialogMB(){
        AlertDialog.Builder ab  = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        ab.setTitle("Neuer Mitbewohner: ");

        ab.setView(inflater.inflate(R.layout.layout_mb_eintrag_neu, null));

        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog d = (Dialog) dialog;
                et = (EditText) d.findViewById(R.id.edittext_newMb);
                String name = et.getText().toString();
                Toast.makeText(getActivity().getApplicationContext(), "Name:" + name, Toast.LENGTH_LONG).show();
                addMB(name);
            }
        });
        AlertDialog mbD = ab.create();
        mbD.show();
    }

    private void addMB( String name) {
        Roommates mb1 = new Roommates( name, 0);
        Roommates max = new Roommates( name, 0);

        listeMb = new ArrayList<>();
        listeMb.add(mb1);
        listeMb.add(max);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("WG-Übersicht");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_wguebersicht, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



}