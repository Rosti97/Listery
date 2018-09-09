package e.rosti.listery;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class WGUebersichtFragment extends Fragment {

    private MateViewModel mMateViewModel;

    private ListView lvMitbewohner;
    private EditText et;
    private UebersichtsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wg, container, false);
        setHasOptionsMenu(true);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showAlertDialogMB();
            }
        });

        mMateViewModel = ViewModelProviders.of(this).get(MateViewModel.class);
        mMateViewModel.excludeYourself();

        mMateViewModel.getmCurrentMate().observe(this, new Observer<List<Mate>>() {
            @Override
            public void onChanged(@Nullable List<Mate> mates) {
                adapter.addItem(mates);
            }
        });

        setupViewAdapter(v);

        return v;
    }

    private void setupViewAdapter(View v) {
        adapter = new UebersichtsAdapter(new ArrayList<Mate>(), this.getContext());
        lvMitbewohner = (ListView) v.findViewById(R.id.listview_mitbewohner);
        lvOnClick();

        lvMitbewohner.setAdapter(adapter);
    }

    /**
     * Hier wird der Dialog angezeigt, um die Optionen für einen Mitbewohner auszuwählen
     **/
    private void lvOnClick() {
        lvMitbewohner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                ab.setTitle(R.string.title_onclick);

                ab.setItems(R.array.wg_menu, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //0=Bearbeiten
                        if (which == 0) {
                            changeName(position);
                        }
                        //1=Löschen
                        if (which == 1) {
                            delete(position);
                        }
                    }
                });
                AlertDialog mbD = ab.create();
                mbD.show();
            }
        });

    }

    /**
     * Dialog, wenn Mitbewohner gelöscht werden soll
     **/
    private void delete(int position) {
        final Mate selectedMate = adapter.getItem(position);
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        ab.setTitle(selectedMate.getName() + " " + getContext().getString(R.string.text_löschen));

        ab.setMessage(R.string.message_löschen);

        ab.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mMateViewModel.deleteMate(selectedMate);
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

    /**
     * Hier wird der Dialog angezeigt, um den Namen eines Mitbewohners zu bearbeiten
     **/
    private void changeName(int position) {
        final Mate selectedMate = adapter.getItem(position);
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        ab.setTitle(selectedMate.getName() + " " + getContext().getString(R.string.title_change_name));
        ab.setView(inflater.inflate(R.layout.layout_mb_eintrag_neu, null));

        ab.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog d = (Dialog) dialog;
                et = (EditText) d.findViewById(R.id.edittext_newMb);
                String name = et.getText().toString();
                selectedMate.setName(name);
                Mate[] data = {selectedMate};
                mMateViewModel.updateMate(data);
            }
        });
        AlertDialog mbD = ab.create();
        mbD.show();
    }

    /**
     * Hier wird der Dialog angezeigt, um einen neuen Mitbewohner zu erstellen
     **/
    private void showAlertDialogMB() {
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        ab.setTitle(R.string.title_new_roommate);

        ab.setView(inflater.inflate(R.layout.layout_mb_eintrag_neu, null));

        ab.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog d = (Dialog) dialog;
                et = (EditText) d.findViewById(R.id.edittext_newMb);
                String name = et.getText().toString();
                Mate mate = new Mate(name, 0);
                mMateViewModel.insertMate(mate);
            }
        });
        AlertDialog mbD = ab.create();
        mbD.show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.title_fragment_wg);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_wguebersicht, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}