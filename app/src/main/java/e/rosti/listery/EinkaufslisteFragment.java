package e.rosti.listery;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/** TODO
 *  2. Intent für Preisangabe
 */
public class EinkaufslisteFragment extends Fragment {

    private MateViewModel mMateViewModel;
    private ItemViewModel mItemViewModel;

    private ArrayList<Item> checkedItems;
    private ListView listView;
    private EinkaufsAdapter adapter;

    private boolean[] checkedPerson;
    private List<Mate> allMates;
    private String[] mateNames;
    private List<Mate> selectedMates;

    private EditText editProdct;
    private TextView tvMitbewohner;
    private ImageButton ibMitbewohner;
    private ImageButton ibAdd;

    private static final String KEY = "Gekauft";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.fragment_einkaufsliste, container, false);

        setupViews(v);
        setupButtons(v);

        checkedItems = new ArrayList<>();

        adapter = new EinkaufsAdapter(new ArrayList<Item>(), this.getContext());

        listView.setAdapter(adapter);

        mMateViewModel = ViewModelProviders.of(this).get(MateViewModel.class);

        mMateViewModel.getmCurrentMate().observe(this, new Observer<List<Mate>>() {
            @Override
            public void onChanged(@Nullable List<Mate> mates) {
                allMates = mates;
                List<String> tempNameList = new ArrayList<String>();
                for(Mate temp : mates){
                    tempNameList.add(temp.getName());
                }
                mateNames = new String[tempNameList.size()];
                mateNames = tempNameList.toArray(mateNames);
            }
        });

        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        mItemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                adapter.addItems(items);
            }
        });

        addItems();

        return v;
    }

    private void setupViews(View v) {
        editProdct = (EditText) v.findViewById(R.id.edittext_einkaufsliste);
        listView = v.findViewById(R.id.listview_einkaufsliste);
        tvMitbewohner = (TextView) v.findViewById(R.id.textview_einkaufsliste_fuer_mb);


        /**OnKeyListener for handling "Enter"-Event**/
        editProdct.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    addProduct();
                    return true;
                }
                return false;
            }
        });
    }

    private void addItems() {
        /**OnItemClick for handling checkbox**/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item clickedItem= adapter.getItem(position);

                if (clickedItem.isChecked()) {
                    clickedItem.setChecked(false);
                    checkedItems.remove(clickedItem);
                    mItemViewModel.updateItem(clickedItem);
                    updateWidgets(getContext());

                } else {
                    clickedItem.setChecked(true);
                    checkedItems.add(clickedItem);
                    mItemViewModel.updateItem(clickedItem);
                    updateWidgets(getContext());
                }
                adapter.notifyDataSetChanged();
            }
        });

        /**OnLongClick for handling delete**/
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Item longClickedItem = adapter.getItem(position);

                //Deletes item from database
                mItemViewModel.deleteItem(longClickedItem);
                updateWidgets(getContext());
                Snackbar mySnackbar = Snackbar.make(view,
                        R.string.snackbar_gelöscht, Snackbar.LENGTH_SHORT);
                mySnackbar.setAction(R.string.undo_button, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemViewModel.insertItem(longClickedItem);
                        updateWidgets(getContext());
                    }
                });
                mySnackbar.show();
                return true;
            }
        });
    }

    private void setupButtons(View v) {
        Button b = (Button) v.findViewById(R.id.button_einkauffertig);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Button geklickt", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), PreiseingabeActivity.class);
                if(!checkedItems.isEmpty()){
                    intent.putExtra(KEY, checkedItems);
                    Item[] items = new Item[checkedItems.size()];
                    items = checkedItems.toArray(items);
                    mItemViewModel.deleteItems(items);
                    updateWidgets(getContext());
                    startActivity(intent);
                }
            }
        });

        ibAdd = (ImageButton) v.findViewById(R.id.addbutton_einkaufsliste);
        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        ibMitbewohner = (ImageButton) v.findViewById(R.id.imagebutton_einkaufsliste);
        ibMitbewohner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }


    /**zeigt Dialog an, um Mitbewohner dem Einkauf zuzuordnen**/
    private void showAlertDialog() {
        final AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());

        checkedPerson = new boolean[mateNames.length];
        selectedMates = new ArrayList<Mate>();
        adBuilder.setTitle(R.string.dialog_title);
        adBuilder.setMultiChoiceItems(mateNames, checkedPerson, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                if (isChecked){
                    if(!selectedMates.contains(allMates.get(position))){
                        selectedMates.add(allMates.get(position));
                    }
                    else
                        selectedMates.remove(position);
                }
            }
        });

        adBuilder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(!selectedMates.isEmpty()) {
                    String text = selectedMates.get(0).getName();
                        for (int i = 1; i < selectedMates.size(); i++) {
                            text += ", " + selectedMates.get(i).getName();
                        }
                    tvMitbewohner.setText(text);
                    addProduct();
                }
            }
        });

        AlertDialog mbDialog = adBuilder.create();
        mbDialog.show();
    }

    /**wird der Liste hinzugefügt, entweder durch Enter oder durch Plus-Button**/
    private void addProduct() {
        if(tvMitbewohner.getText() != "") {
            String newProduct = editProdct.getText().toString();
            if (!newProduct.equals("")) {
                Item item = new Item(newProduct,0,selectedMates);
                mItemViewModel.insertItem(item);
                updateWidgets(getContext());
                editProdct.setText("");
            } else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.toast_produkteingabe, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.toast_mitbewohner_auswahl,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static void updateWidgets(Context context){
        Intent intent = new Intent(context.getApplicationContext(),EinkaufslisteWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        int[] ids = widgetManager.getAppWidgetIds(new ComponentName(context,EinkaufslisteWidget.class));
        widgetManager.notifyAppWidgetViewDataChanged(ids,android.R.id.list);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.fragment_title_einkaufsliste);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_einkaufsliste, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}