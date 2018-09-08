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
import java.util.List;

public class PreiseingabeActivity extends AppCompatActivity {

    private PreiseingabeAdapter adapter;
    private ListView listView;
    private Button button;
    private List<Item> gekaufteItems;
    private List<Item> itemsWithPrice;
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

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            gekaufteItems = extras.getParcelableArrayList(KEY);
        }
        /**Test**/
        Item item = gekaufteItems.get(0);
        if (item.getPrice() != 0) {
                Toast.makeText(this, "Test:" + item.getPrice(), Toast.LENGTH_SHORT).show();
        }
        /**---**/

        adapter = new PreiseingabeAdapter(new ArrayList<Item>(), this);

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
                for (Item item: gekaufteItems) {
                    if (item.getPrice() != 0) {
                    Log.i("TESTPREIS", item.getName() + item.getPrice());
                }}

                itemsWithPrice = new ArrayList<>();
                for (Item item: gekaufteItems) {
                    if (item.getPrice() != 0) {
                        itemsWithPrice.add(item);
                        Log.i("TESTGRÖßE", "Preisliste: " + itemsWithPrice.size());
                    }
                }
                /** TODO Items (itemsWithPrice) aus Einkaufsliste-Datenbank löschen und in Bilanz einfügen**/


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                if(itemsWithPrice.size() == gekaufteItems.size()) {
                    startActivity(intent);
                } else {
                    Toast.makeText(PreiseingabeActivity.this, "Bitte bei allen Produkten den Preis angeben!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
