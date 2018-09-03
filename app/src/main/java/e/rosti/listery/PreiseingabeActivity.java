package e.rosti.listery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PreiseingabeActivity extends AppCompatActivity {

    private PreiseingabeAdapter adapter;
    private ListView listView;
    private Button button;
    private ArrayList<Einkaufsitem> gekaufteItems;
    private ArrayList<Einkaufsitem> itemsWithPrice;
    private static final String KEY = "Gekauft";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preiseingabe);

        setupView();
        setupButton();
    }


    private void setupView() {
        listView = findViewById(R.id.listview_preiseingabe);

        gekaufteItems = new ArrayList<>();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            gekaufteItems = extras.getParcelableArrayList(KEY);
        }
        /**Test**/
        Einkaufsitem item = gekaufteItems.get(0);
        if (item.getPrice() != null) {
                Toast.makeText(this, "Test:" + item.getPrice(), Toast.LENGTH_SHORT).show();
        }
        /**---**/

        adapter = new PreiseingabeAdapter(gekaufteItems, this);

        listView.setAdapter(adapter);
    }

    private void setupButton() {
        button = findViewById(R.id.button_preiseingabe);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**TODO Datenbank einbindung
                 *
                 */

                /** TEST für Preisanzeige :D **/
                for (Einkaufsitem item: gekaufteItems) {
                    if (item.getPrice() != null) {
                    Log.i("TESTPREIS", item.getProduct() + item.getPrice());
                }}

                itemsWithPrice = new ArrayList<>();
                for (Einkaufsitem item: gekaufteItems) {
                    if (item.getPrice() != null) {
                        itemsWithPrice.add(item);
                    }
                }
                /** TODO Items (itemsWithPrice) aus Einkaufsliste-Datenbank löschen und in Bilanz einfügen**/


            }
        });
    }
}
