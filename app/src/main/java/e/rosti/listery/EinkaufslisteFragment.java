package e.rosti.listery;

        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.DialogFragment;
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
        import java.util.Arrays;
        import java.util.List;

        import static android.content.ContentValues.TAG;

/** TODO
 *  1. Datenbank-Einbindung
 *  2. Intent für Preisangabe
 */
public class EinkaufslisteFragment extends Fragment {

    private ArrayList<Einkaufsitem> einkaufsliste;
    private ListView listView;
    private EinkaufsAdapter adapter;

    private boolean[]checkedPerson;
    private List<String> mbList;

    private EditText editProdct;
    private TextView tvMitbewohner;
    private ImageButton ibMitbewohner;
    private ImageButton ibAdd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.fragment_einkaufsliste, container, false);

        setupViews(v);
        setupButtons(v);

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
        einkaufsliste = new ArrayList<>();

        /**Zwei Tests**/
        /**-------------------------------------------------------------**/
        Einkaufsitem item1 = new Einkaufsitem(false, "Eier", "Für die WG");
        Einkaufsitem item2 = new Einkaufsitem(true, "deine Mudda", "Für: mich");
        einkaufsliste.add(item1);
        einkaufsliste.add(item2);
        /**-------------------------------------------------------------**/

        adapter = new EinkaufsAdapter(einkaufsliste, this.getContext());

        listView.setAdapter(adapter);

        /**OnItemClick for handling checkbox**/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Einkaufsitem clickedItem= einkaufsliste.get(position);

                if (clickedItem.isChecked()) {
                    clickedItem.setChecked(false);

                } else {
                    clickedItem.setChecked(true);
                }
                adapter.notifyDataSetChanged();
            }
        });

        /**OnLongClick for handling delete**/
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Einkaufsitem longClickedItem = einkaufsliste.get(position);
                einkaufsliste.remove(longClickedItem);
                adapter.notifyDataSetChanged();
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
                Toast.makeText(getActivity().getApplicationContext(),"mitbewohner", Toast.LENGTH_SHORT).show();
                showAlertDialog();
            }
        });
    }


    /**zeigt Dialog an, um Mitbewohner dem Einkauf zuzuordnen**/
    private void showAlertDialog() {
        final AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());

        //Test String-Array -> hier sollte Datenbank einbindung stattfinden TODO
        /**----------------------------------------------------------------------**/
        String[] mitbewohner = new String[]{"mich", "wir", "max", "adam", "eva"};
        /**----------------------------------------------------------------------**/

        checkedPerson = new boolean[mitbewohner.length];
        mbList = Arrays.asList(mitbewohner);

        adBuilder.setTitle(R.string.dialog_title);
        adBuilder.setMultiChoiceItems(mitbewohner, checkedPerson, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedPerson[which] = isChecked;
            }
        });

        adBuilder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvMitbewohner.setText("");
                for (int i = 0; i<checkedPerson.length; i++) {
                    boolean checked = checkedPerson[i];
                    //je nach Angabe, wird ein anderer Text gesetzt
                    if (checked) {
                        if(checkedPerson[i] == checkedPerson[1]) {
                            tvMitbewohner.setText("Für die WG");
                            break;
                        }else if(tvMitbewohner.getText().equals("")) {
                            tvMitbewohner.setText("Für: " +tvMitbewohner.getText() + mbList.get(i));
                        }else{
                            tvMitbewohner.setText(tvMitbewohner.getText()+ ", " + mbList.get(i));
                        }
                    }
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
            String mbProduct = tvMitbewohner.getText().toString();
            if (!newProduct.equals("")) {
                einkaufsliste.add(new Einkaufsitem(false, newProduct, mbProduct));
                editProdct.setText("");
                Toast.makeText(getActivity().getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Bitte Produkt eingeben", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Bitte angeben, für wen eingekauft wird!",
                    Toast.LENGTH_SHORT).show();
        }
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