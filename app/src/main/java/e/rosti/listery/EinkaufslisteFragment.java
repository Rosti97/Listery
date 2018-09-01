package e.rosti.listery;

        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.DialogFragment;
        import android.support.v4.app.Fragment;
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

        import static android.content.ContentValues.TAG;

/** TODO
 *  1. Datenbank-Einbindung
 *  2. Kontextmenü für Mitbewohner
 */
public class EinkaufslisteFragment extends Fragment {

    private ArrayList<Einkaufsitem> einkaufsliste;
    private ListView listView;
    private EinkaufsAdapter adapter;

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
        tvMitbewohner = (TextView) v.findViewById(R.id.textview_einkaufsliste_fuer_mb);
        listView = v.findViewById(R.id.listview_einkaufsliste);

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

        Einkaufsitem item1 = new Einkaufsitem(false, "Eier");
        Einkaufsitem item2 = new Einkaufsitem(true, "deine Mudda");
        einkaufsliste.add(item1);
        einkaufsliste.add(item2);

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
                Log.i(TAG, "Button gedrückt");
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

            }
        });
    }

    private void addProduct() {
        String newProduct = editProdct.getText().toString();
        if (!newProduct.equals("")) {
            einkaufsliste.add(new Einkaufsitem(false, newProduct));
            editProdct.setText("");
            Toast.makeText(getActivity().getApplicationContext(),"add", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Bitte Produkt eingeben", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Einkaufsliste");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_einkaufsliste, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}