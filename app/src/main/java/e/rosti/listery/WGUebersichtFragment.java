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

public class WGUebersichtFragment extends Fragment {

    private ListView lvMitbewohner;
    private EditText et;


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

        lvMitbewohner = (ListView)v.findViewById(R.id.listview_mitbewohner);

        return v;
    }


    private void showAlertDialogMB(){

        final LayoutInflater inflater = this.getLayoutInflater();

        final Dialog dialogMb = new Dialog (getActivity());
        dialogMb.setTitle("Mitbewohner");
        //dialogMb.setMessage("Name");

        final View view = inflater.inflate(R.layout.layout_mb_eintrag_neu, null);

        dialogMb.setContentView(inflater.inflate(R.layout.layout_mb_eintrag_neu, null));

        et = (EditText) dialogMb.findViewById(R.id.edittext_newMb);
        Button btn = dialogMb.findViewById(R.id.button_newmb);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mb = et.getText().toString();
                Toast.makeText(getActivity().getApplicationContext(), "Name: " + mb, Toast.LENGTH_LONG).show();
                dialogMb.dismiss();

            }
        });




        dialogMb.show();
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